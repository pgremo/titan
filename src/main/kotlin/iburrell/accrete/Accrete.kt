// Author: Ian Burrell  <iburrell@leland.stanford.edu>
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

package iburrell.accrete

import iburrell.accrete.DoleParams.ECCENTRICITY_COEFF
import iburrell.util.nextDouble
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**

 * This class does the accretion process and returns a list of
 * Planetismals with the results.  It is constructed for a given star
 * size.  Multiple random systems can be created for the given star
 * using DistributePlanets.

 */
class Accrete constructor(
        internal var stellar_mass: Double = 1.0, // in Solar masses
        internal var stellar_luminosity: Double = Astro.Luminosity(stellar_mass), // in Solar luminsoities
        var random: Random = ThreadLocalRandom.current()
) {
    internal var inner_bound: Double = 0.toDouble()
    internal var outer_bound: Double = 0.toDouble()    // in AU
    internal var inner_dust: Double = 0.toDouble()
    internal var outer_dust: Double = 0.toDouble()      // in AU

    /**
     * Creates a Accretion class for the star of the given size.
     * Pre-calculates all of those values that depend on the size of
     * the star.
     */
    constructor() : this(1.0, 1.0)

    init {
        inner_bound = DoleParams.InnermostPlanet(stellar_mass)
        outer_bound = DoleParams.OutermostPlanet(stellar_mass)
        inner_dust = DoleParams.InnerDustLimit(stellar_mass)
        outer_dust = DoleParams.OuterDustLimit(stellar_mass)
    }

    // values that only depend on radius and are cached for each
    // nucleus
    internal var crit_mass: Double = 0.toDouble()
    internal var dust_density: Double = 0.toDouble()

    internal var dust_head: DustBand? = null          // head of the list of dust bands

    internal val planets = java.util.TreeSet<Planetismal>(compareBy { it.orbitalAxis })

    /**

     * This function does the main work of creating the planets.  It
     * calls all of the other functions to create an entire system of
     * planetismals.  It returns a list of the Planetismals.

     * @return Vector containing all of the planets written out.
     */
    fun DistributePlanets(): Sequence<Planetismal> {
        dust_head = DustBand(inner_dust, outer_dust)

        while (CheckDustLeft()) {
            val tsml = Planetismal(random.nextDouble(inner_bound, outer_bound), 1.0 - Math.pow(random.nextDouble(), ECCENTRICITY_COEFF))

            dust_density = DoleParams.DustDensity(stellar_mass, tsml.orbitalAxis)
            crit_mass = tsml.CriticalMass(stellar_luminosity)

            val mass = AccreteDust(tsml)

            if (mass != 0.0 && mass != Planetismal.PROTOPLANET_MASS) {
                if (mass >= crit_mass)
                    tsml.isGasGiant = true
                UpdateDustLanes(tsml.InnerSweptLimit(), tsml.OuterSweptLimit(), tsml.isGasGiant)
                CompressDustLanes()

                if (!CoalescePlanetismals(tsml)) {
                    planets.add(tsml)
                }
            }
        }

        return planets.asSequence()
    }

    /**
     * Repeatedly accretes dust and gas onto the new planetismal by
     * sweeping out gas lanes until the planetismal doesn't grow any
     * more.  Returns the new mass for the planetismal; also changes
     * the planetismal's mass.
     */
    internal fun AccreteDust(nucleus: Planetismal): Double {
        var new_mass = nucleus.massSolar
        do {
            nucleus.massSolar = new_mass
            new_mass = generateSequence(dust_head, DustBand::next).fold(0.0) { acc, it -> acc + CollectDust(nucleus, it) }
        } while (new_mass - nucleus.massSolar > 0.0001 * nucleus.massSolar)
        nucleus.massSolar = new_mass
        return nucleus.massSolar
    }

    /**
     * Returns the amount of dust and gas collected from the single
     * dust band by the nucleus.  Returns 0.0 if no dust can be swept
     * from the band
     */
    internal fun CollectDust(nucleus: Planetismal, band: DustBand): Double {
        if (!band.dust)
            return 0.0

        val swept_inner = Math.max(0.0, nucleus.InnerSweptLimit())
        val swept_outer = nucleus.OuterSweptLimit()

        if (band.outer <= swept_inner || band.inner >= swept_outer)
            return 0.0

        val dust_density = this.dust_density
        val mass_density = DoleParams.MassDensity(dust_density, crit_mass, nucleus.massSolar)
        val density = if (!band.gas || nucleus.massSolar < crit_mass) dust_density else mass_density

        val swept_width = swept_outer - swept_inner
        val outside = Math.max(0.0, swept_outer - band.outer)
        val inside = Math.max(0.0, band.inner - swept_inner)

        val width = swept_width - outside - inside
        val term1 = 4.0 * Math.PI * Math.pow(nucleus.orbitalAxis, 2.0)
        val term2 = 1.0 - nucleus.eccentricity * (outside - inside) / swept_width
        val volume = term1 * nucleus.ReducedMargin() * width * term2

        return volume * density
    }

    /**
     * Updates the dust lanes covered by the given range by splitting
     * if necessary and updating the dust and gas present fields.
     */
    internal fun UpdateDustLanes(min: Double, max: Double, used_gas: Boolean) {
        var curr = dust_head
        while (curr != null) {
            val new_gas = curr.gas && !used_gas
            var next: DustBand = curr

            if (curr.inner < min && curr.outer > max) {
                val first = DustBand(min, max, false, new_gas)
                val second = DustBand(max, curr.outer, curr.dust, curr.gas)
                first.next = second
                second.next = curr.next
                curr.next = first
                curr.outer = min
                next = second
            } else if (curr.inner < max && curr.outer > max) {
                val first = DustBand(max, curr.outer, curr.dust, curr.gas)
                first.next = curr.next
                curr.next = first
                curr.outer = max
                curr.dust = false
                curr.gas = new_gas
                next = first
            } else if (curr.inner < min && curr.outer > min) {
                val first = DustBand(min, curr.outer, false, new_gas)
                first.next = curr.next
                curr.next = first
                curr.outer = min
                next = first
            } else if (curr.inner >= min && curr.outer <= max) {
                curr.dust = false
                curr.gas = new_gas
                next = curr
            } else if (curr.inner > max || curr.outer < min) {
                next = curr
            }
            curr = next
            curr = curr.next
        }
    }

    /**
     * Checks if there is any dust remaining in any bands inside the
     * bounds where planets can form.
     */
    internal fun CheckDustLeft() = generateSequence(dust_head, DustBand::next)
            .any { it.dust && it.outer >= inner_bound && it.inner <= outer_bound }

    /**
     * Compresses adjacent lanes that have the same status.
     */
    internal fun CompressDustLanes() {
        var curr = dust_head
        while (curr != null) {
            var next = curr.next
            if (next != null && curr.dust == next.dust && curr.gas == next.gas) {
                curr.outer = next.outer
                curr.next = next.next
                next = curr
            }
            curr = next
        }
    }

    /**
     * Searches the existing planet list for any that overlap with the
     * new planetismal.  If there is an overlap their effect radii,
     * the two planets are coalesced into one.
     */
    internal fun CoalescePlanetismals(tsml: Planetismal): Boolean {
        for (curr in planets) {
            val dist = curr.orbitalAxis - tsml.orbitalAxis
            val dist1: Double
            val dist2: Double
            if (dist > 0.0) {
                dist1 = tsml.OuterEffectLimit() - tsml.orbitalAxis
                dist2 = curr.orbitalAxis - curr.InnerEffectLimit()
            } else {
                dist1 = tsml.orbitalAxis - tsml.InnerEffectLimit()
                dist2 = curr.OuterEffectLimit() - curr.orbitalAxis
            }

            if (Math.abs(dist) <= dist1 || Math.abs(dist) <= dist2) {
                CoalesceTwoPlanets(curr, tsml)
                return true
            }
        }
        return false
    }

    /**
     * Coalesces two planet together.  The resulting planet is saved
     * back into the first one (which is assumed to be the one present
     * in the planet list).
     */
    internal fun CoalesceTwoPlanets(a: Planetismal, b: Planetismal) {
        val new_mass = a.massSolar + b.massSolar
        val new_axis = new_mass / (a.massSolar / a.orbitalAxis + b.massSolar / b.orbitalAxis)
        val term1 = a.massSolar * Math.sqrt(a.orbitalAxis * (1.0 - a.eccentricity * a.eccentricity))
        val term2 = b.massSolar * Math.sqrt(b.orbitalAxis * (1.0 - b.eccentricity * b.eccentricity))
        val term3 = (term1 + term2) / (new_mass * Math.sqrt(new_axis))
        val term4 = 1.0 - term3 * term3
        val new_eccn = Math.sqrt(Math.abs(term4))
        a.massSolar = new_mass
        a.orbitalAxis = new_axis
        a.eccentricity = new_eccn
        a.isGasGiant = a.isGasGiant || b.isGasGiant
    }
}

fun main(args: Array<String>) {
    Accrete().apply { DistributePlanets().forEach { println(it) } }
}

