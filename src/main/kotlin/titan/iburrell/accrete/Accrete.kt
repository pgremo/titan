// Author: Ian Burrell  <titan.iburrell@leland.stanford.edu>
// Created: 1997/01/14
// Modified: 1997/02/09

// Copyright 1997 Ian Burrell

/*
 *
 * Simulates the creation of planetary systems using the Dole
 * accretion model.
 *
 * This program simulates the creation of a planetary system by
 * accretion of planetismals.  Individual planets sweep out dust and
 * gas until all of the dust is swept up.  Planets whose orbits are
 * close are coalesced.
 *
 * See http://www-leland.stanford.edu/~iburrell/create/accrete.html
 * for more history of this model and programs.
 *
 * References:
 *      Dole, Stephen H.  "Computer Simulation of the Formation of
 *          Planetary Systems."  _Icarus_.  13 (1970), pg 494-508.
 *      Isaacman & Sagan.  "Computer Simulations of Planetary Accretion
 *          Dynamics."  _Icarus_.  31 (1997), pg 510-533.
 *      Fogg, Martyn J.  "Extra-Solar Planetary Systems: A Microcomputer
 *          Simulation".  Journal of the British Interplanetary
 *          Society, vol 38, 501-514, 1985.
 *
 *
 */

package titan.iburrell.accrete

import titan.iburrell.accrete.DoleParams.Q
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**

 * This class does the accretion process and returns a list of
 * Planetismals with the results.  It is constructed for a given star
 * size.  Multiple random systems can be created for the given star
 * using DistributePlanets.

 */
class Accrete constructor(
        private var stellar_mass: Double = 1.0, // in Solar masses
        private var stellar_luminosity: Double = Astro.Luminosity(stellar_mass), // in Solar luminsoities
        private var random: Random = ThreadLocalRandom.current()
) {

    private val bounds: ClosedRange<Double> = 0.3..50.0

    /**
     * Creates a Accretion class for the star of the given size.
     * Pre-calculates all of those values that depend on the size of
     * the star.
     */
    constructor() : this(1.0, 1.0)

    // values that only depend on radius and are cached for each
    // nucleus
    private var crit_mass: Double = 0.toDouble()
    private var dust_density: Double = 0.toDouble()

    /**

     * This function does the main work of creating the planets.  It
     * calls all of the other functions to create an entire system of
     * planetismals.  It returns a list of the Planetismals.

     * @return Vector containing all of the planets written out.
     */
    fun generate(): List<Planetismal> {
        val planets = TreeSet<Planetismal>(compareBy(Planetismal::orbitalAxis))
        val dust_head = DustBand(DoleParams.InnerDustLimit(stellar_mass), DoleParams.OuterDustLimit(stellar_mass))

        while (isDustLeft(dust_head)) {
            val orbitalAxis = random.nextDouble(bounds)
            val tsml = Planetismal(orbitalAxis, 1.0 - Math.pow(1.0 - random.nextDouble(), Q))

            dust_density = DoleParams.DustDensity(stellar_mass, orbitalAxis)
            crit_mass = tsml.criticalMass(stellar_luminosity)

            val mass = accrete(dust_head, tsml)

            if (mass !in listOf(0.0, Planetismal.PROTOPLANET_MASS)) {
                tsml.isGasGiant = mass >= crit_mass
                updateCloud(dust_head, tsml.innerSweptLimit, tsml.outerSweptLimit, tsml.isGasGiant)
                compressCloud(dust_head)
                if (!coalesce(planets, tsml)) planets.add(tsml)
            }
        }

        return planets.toList()
    }

    /**
     * Repeatedly accretes dust and gas onto the new planetismal by
     * sweeping out gas lanes until the planetismal doesn't grow any
     * more.  Returns the new mass for the planetismal; also changes
     * the planetismal's mass.
     */
    internal fun accrete(band: DustBand, nucleus: Planetismal): Double {
        var mass = nucleus.massSolar
        do {
            nucleus.massSolar = mass
            mass = generateSequence(band, DustBand::next)
                    .map { collect(nucleus, it) }
                    .reduce(Double::plus)
        } while (mass - nucleus.massSolar > 0.0001 * nucleus.massSolar)
        nucleus.massSolar = mass
        return mass
    }

    /**
     * Returns the amount of dust and gas collected from the single
     * dust band by the nucleus.  Returns 0.0 if no dust can be swept
     * from the band
     */
    private fun collect(nucleus: Planetismal, band: DustBand): Double {
        if (!band.dust)
            return 0.0

        if (!band.intersects(nucleus.sweptLimit))
            return 0.0

        var density = dust_density
        if (band.gas && nucleus.massSolar >= crit_mass)
            density *= DoleParams.K

        val swept_inner = Math.max(0.0, nucleus.innerSweptLimit)
        val swept_outer = nucleus.outerSweptLimit
        val swept_width = swept_outer - swept_inner
        val outside = Math.max(0.0, swept_outer - band.endInclusive)
        val inside = Math.max(0.0, band.start - swept_inner)

        val width = swept_width - outside - inside
        val term1 = 4.0 * Math.PI * Math.pow(nucleus.orbitalAxis, 2.0)
        val term2 = 1.0 - nucleus.eccentricity * (outside - inside) / swept_width
        val volume = term1 * nucleus.reducedMargin * width * term2

        return volume * density
    }

    /**
     * Updates the dust lanes covered by the given range by splitting
     * if necessary and updating the dust and gas present fields.
     */
    private fun updateCloud(head: DustBand, min: Double, max: Double, used_gas: Boolean) {
        var band: DustBand? = head
        while (band != null) {
            val hasGas = band.gas && !used_gas
            band = when {
                band.start < min && band.endInclusive > max -> {
                    val right = band.copy(start = max)
                    val middle = DustBand(min, max, false, hasGas, right)
                    band.apply { next = middle; endInclusive = min }
                    null
                }
                band.start < max && band.endInclusive > max -> {
                    val right = band.copy(start = max)
                    band.apply { next = right; endInclusive = max; dust = false; gas = hasGas }
                    null
                }
                band.start < min && band.endInclusive > min -> {
                    val right = band.copy(start = min, dust = false, gas = hasGas)
                    band.apply { next = right; endInclusive = min }
                    right
                }
                band.start >= min && band.endInclusive <= max -> {
                    band.apply { dust = false; gas = hasGas }
                }
                else -> band
            }?.next
        }
    }

    /**
     * Checks if there is any dust remaining in any bands inside the
     * bounds where planets can form.
     */
    private fun isDustLeft(head: DustBand) = generateSequence(head, DustBand::next)
            .any { it.dust && it.intersects(bounds) }

    /**
     * Compresses adjacent lanes that have the same status.
     */
    private fun compressCloud(head: DustBand) {
        var band: DustBand? = head
        while (band != null) {
            var next = band.next
            if (next != null && band.dust == next.dust && band.gas == next.gas) {
                band.endInclusive = next.endInclusive
                band.next = next.next
                next = band
            }
            band = next
        }
    }

    /**
     * Searches the existing planet list for any that overlap with the
     * new planetismal.  If there is an overlap their effect radii,
     * the two planets are coalesced into one.
     */
    private fun coalesce(planets: SortedSet<Planetismal>, nucleus: Planetismal): Boolean {
        val x = nucleus.effectLimit
        return planets
                .firstOrNull {
                    x.intersects(it.effectLimit)
                }
                ?.apply {
                    merge(nucleus)
                } != null
    }

}

fun main(vararg args: String) {
    Accrete().apply { generate().forEach { println(it) } }
}

