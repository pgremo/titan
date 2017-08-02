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

import java.io.PrintStream

/**

 * This class does the accretion process and returns a list of
 * Planetismals with the results.  It is constructed for a given star
 * size.  Multiple random systems can be created for the given star
 * using DistributePlanets.

 */
class Accrete @JvmOverloads constructor(internal var stellar_mass: Double, // in Solar masses
                                        internal var stellar_luminosity: Double = Astro.Luminosity(stellar_mass)  // in Solar luminsoities
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
    internal var planet_head: Planetismal? = null     // head of the list planetismals


    /**

     * This function does the main work of creating the planets.  It
     * calls all of the other functions to create an entire system of
     * planetismals.  It returns a list of the Planetismals.

     * @return Vector containing all of the planets written out.
     */
    fun DistributePlanets(): List<Planetismal> {
        dust_head = DustBand(inner_dust, outer_dust)
        planet_head = null

        var dust_left = true
        while (dust_left) {
            val tsml = Planetismal.RandomPlanetismal(inner_bound,
                    outer_bound)

            dust_density = DoleParams.DustDensity(stellar_mass, tsml.orbitalAxis)
            crit_mass = tsml.CriticalMass(stellar_luminosity)

            val mass = AccreteDust(tsml)

            if (mass != 0.0 && mass != Planetismal.PROTOPLANET_MASS) {
                if (mass >= crit_mass)
                    tsml.isGasGiant = true
                UpdateDustLanes(tsml.InnerSweptLimit(),
                        tsml.OuterSweptLimit(), tsml.isGasGiant)
                dust_left = CheckDustLeft()
                CompressDustLanes()

                if (!CoalescePlanetismals(tsml))
                    InsertPlanet(tsml)

            }

        }

        return PlanetArray(planet_head)

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
            new_mass = 0.0
            var curr = dust_head
            while (curr != null) {
                new_mass += CollectDust(nucleus, curr)
                curr = curr.next
            }
        } while (new_mass - nucleus.massSolar > 0.0001 * nucleus.massSolar)
        nucleus.massSolar = new_mass
        return nucleus.massSolar
    }


    /**
     * Returns the amount of dust and gas collected from the single
     * dust band by the nucleus.  Returns 0.0 if no dust can be swept
     * from the band
     */
    internal fun CollectDust(nucleus: Planetismal, band: DustBand?): Double {
        if (band == null)
            return 0.0

        var swept_inner = nucleus.InnerSweptLimit()
        val swept_outer = nucleus.OuterSweptLimit()

        if (swept_inner < 0.0)
            swept_inner = 0.0
        if (band.outer <= swept_inner || band.inner >= swept_outer)
            return 0.0
        if (!band.dust)
            return 0.0

        val dust_density = this.dust_density
        val mass_density = DoleParams.MassDensity(dust_density, crit_mass,
                nucleus.massSolar)
        val density = if (!band.gas || nucleus.massSolar < crit_mass)
            dust_density
        else
            mass_density

        val swept_width = swept_outer - swept_inner
        var outside = swept_outer - band.outer
        if (outside < 0.0)
            outside = 0.0
        var inside = band.inner - swept_inner
        if (inside < 0.0)
            inside = 0.0

        val width = swept_width - outside - inside
        val term1 = 4.0 * Math.PI * nucleus.orbitalAxis * nucleus.orbitalAxis
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
            val first: DustBand
            val second: DustBand
            var next: DustBand = curr

            // Case 1: Wide
            if (curr.inner < min && curr.outer > max) {
                first = DustBand(min, max, false, new_gas)
                second = DustBand(max, curr.outer, curr.dust, curr.gas)
                first.next = second
                second.next = curr.next
                curr.next = first
                curr.outer = min
                next = second
            } else if (curr.inner < max && curr.outer > max) {
                first = DustBand(max, curr.outer, curr.dust, curr.gas)
                first.next = curr.next
                curr.next = first
                curr.outer = max
                curr.dust = false
                curr.gas = new_gas
                next = first
            } else if (curr.inner < min && curr.outer > min) {
                first = DustBand(min, curr.outer, false, new_gas)
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
            }// Case 5: Not
            // Case 4: Narrow
            // Case 3: In
            // Case 2: Out
            curr = next
            curr = curr.next
        }
    }


    /**
     * Checks if there is any dust remaining in any bands inside the
     * bounds where planets can form.
     */
    internal fun CheckDustLeft(): Boolean {
        var dust_left = false
        var curr = dust_head
        while (curr != null) {
            // check if band has dust left
            if (curr.dust && curr.outer >= inner_bound && curr.inner <= outer_bound) {
                dust_left = true
            }
            curr = curr.next
        }
        return dust_left
    }


    /**
     * Compresses adjacent lanes that have the same status.
     */
    internal fun CompressDustLanes() {
        var next: DustBand?
        var curr = dust_head
        while (curr != null) {
            next = curr.next
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
        var curr = planet_head
        while (curr != null) {
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
            curr = curr.next
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
        //AccreteDust(a);
    }


    /**
     * Inserts the given planetismal into the list of planets.  The
     * list is kept in sorted order based on the semi-major axis.
     */
    internal fun InsertPlanet(tsml: Planetismal) {
        if (planet_head == null)
            planet_head = tsml
        else {
            if (tsml.orbitalAxis < planet_head!!.orbitalAxis) {
                tsml.next = planet_head
                planet_head = tsml
            } else {
                var prev: Planetismal = planet_head!!
                var curr = planet_head!!.next
                while (curr != null && curr.orbitalAxis < tsml.orbitalAxis) {
                    prev = curr
                    curr = curr.next
                }
                tsml.next = curr
                prev.next = tsml
            }
        }
    }

    fun PrintPlanets(out: PrintStream, planets: List<Planetismal>) {
        planets.forEach { it.Print(out) }
    }

    companion object {

        // Converts a list of planets into a Vector.  The planets in the Vector
        // still contain the links but they aren't accessible to outside classes.
        internal fun PlanetArray(head: Planetismal?) = generateSequence(head, { it.next }).toList()

        @JvmStatic fun main(args: Array<String>) {
            val gen = Accrete()
            val pl = gen.DistributePlanets()
            gen.PrintPlanets(System.out, pl)
        }
    }

}

