/*
 * Java Terrain and Stellar System Ports
 *
 * Copyright (C) 2006 Martin H. Smith based on work by original
 * authors.
 *
 * Released under the terms of the GNU General Public License
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 *
 * Linking TerraJ statically or dynamically with other modules is making a
 * combined work based on TerraJ. Thus, the terms and conditions of the
 * GNU General Public License cover the whole combination.
 *
 * In addition, as a special exception, the copyright holders of TerraJ
 * give you permission to combine this program with free software programs
 * or libraries that are released under the GNU LGPL and with code included
 * in the standard release of JOGL, Java Getopt and FreeMarker under the BSD
 * license (or modified versions of such code, with unchanged license) and with
 * Apache Commons and Log4J libraries under the Apache license (or modified versions
 * of such code. You may copy and distribute such a system following the terms
 * of the GNU GPL for TerraJ and the licenses of the other code concerned,
 * provided that you include the source code of that other code when and as the
 * GNU GPL requires distribution of source code.
 *
 * Note that people who make modified versions of TerraJ are not obligated to grant
 * this special exception for their modified versions; it is their choice whether
 * to do so. The GNU General Public License gives permission to release a modified
 * version without this exception; this exception also makes it possible to release
 * a modified version which carries forward this exception.
 */

/*
 * DoleAccrete.java
 *
 * Created on December 26, 2005, 5:09 PM
 *
 */
package dole

import iburrell.accrete.nextDouble
import java.util.*
import kotlin.collections.ArrayList

/**
 * Dole.c - Planetary accretion algorithm, Icarus V 13 (1978)
 *
 *
 * Ported from Public Domain C code originally written by Andrew Folkins.
 * Note the following comment is from the original C code and computer
 * performance has increased substantially since then:
 *
 *
 * This program implements the accretion algorithm given by Dole.  It's
 * major flaw for simulation on a microcomputer is the amount of
 * computation which needs to be done.  I've tried to simplify the
 * computations a bit, these instances are noted in the code.
 * What I'd like to do is use integer arithmetic, but I don't think that's
 * possible.
 *
 * @author martin
 * @version $Id: DoleAccrete.java,v 1.3 2006-07-06 06:58:35 martin Exp $
 */
open class DoleAccrete
/**
 * Creates a new instance of DoleAccrete
 *
 * @param random The math utility object to use for random numbers
 */
internal constructor(private val random: Random) {

    /**
     * AO * sqrt(star->mass)
     */
    private var A: Double = 0.toDouble()

    /**
     * Linked list of dust records
     */
    private lateinit var dust: List<DustRecord>

    /**
     * Linked list of gas records
     */
    private lateinit var gas: List<DustRecord>

    /**
     * The object used to get details about the generated plants
     */
    private val planetStats: DolePlanetStats = DolePlanetStats(random)

    /**
     * Get the density for a particular radius
     *
     * @param r The orbital radius to get the density for
     * @return The calculated density
     */
    private fun dustDensity(r: Double) = A * Math.exp(-DoleConstants.ALPHA * Math.pow(r, DoleConstants.GAMMA))

    /**
     * Get the gravitational reach for a particular mass at a given orbital radius
     *
     * @param radius The orbital radius to get the density for
     * @param mass   The mass of interest
     * @return The calculated reach
     */
    private fun getReach(radius: Double, mass: Double) = radius * Math.pow(mass / (1.0 + mass), 1.0 / 4.0)

    /**
     * Create a new planet around the star
     *
     * @return A planet record for the new planet
     */
    private fun createPlanet() = Planet(
            e = (1.0 - Math.pow(random.nextDouble(0.01..1.00), 0.077)) * 1.5,
            a = random.nextDouble(dust.random(random)!!)
    )

    /**
     * Compute the amount of mass (of dust or gas) which the planet p will
     * sweep from the available material in one iteration.
     *
     * @param list     A list of dust records
     * @param p        The planet record for the planet being constructed
     * @return The amount of mass swept from the dust or gas
     */
    private fun sweptMass(list: List<DustRecord>, p: Planet, density: (Double) -> Double, rangeF: (Planet) -> ClosedRange<Double>): Double {
        val range = rangeF(p)
        return 2.0 * Math.PI * 2.0 * p.reach * list
                .filter { (innerEdge, outerEdge) -> range.endInclusive >= innerEdge && range.start <= outerEdge }
                .fold(0.0) { acc, (innerEdge, outerEdge) ->
                    val max = Math.min(range.endInclusive, outerEdge)
                    val min = Math.max(range.start, innerEdge)
                    val r = (min + max) / 2.0
                    acc + r * (max - min) * density(r)
                }
    }

    /**
     * Update the band structure by removing or splitting bands from which
     * the planet would have accreted mass.
     *
     * @param list The dust list to be updated
     * @param p    The planet being constructed
     */
    private fun updateBands(list: List<DustRecord>, p: Planet): List<DustRecord> {
        val min = p.rMin
        val max = p.rMax

        return list.flatMap {
            // check for trivial rejection
            if (max <= it.start || min >= it.endInclusive) {
                listOf(it)
            } else if (max < it.endInclusive) {
                val added = mutableListOf<DustRecord>()
                if (min > it.start) {
                    // interval within band, so split it
                    added += it.copy(endInclusive = min)
                }
                it.start = max
                added + it
            } else if (min > it.start) {
                // interval overlaps outer edge
                it.endInclusive = min
                listOf(it)
            } else {
                emptyList()
            }
        }
    }

    /**
     * Sweep up all available dust and gas.
     *
     * @param star The star for this solar system
     * @param p    The planet being constructed
     */
    private fun evolvePlanet(star: Primary, p: Planet) {
        /* Our planetoid will accrete all matter within it's orbit . . . */
        val perihelion = p.perihelion
        val aphelion = p.aphelion
        val criticalMass = DoleConstants.B * Math.pow(Math.sqrt(star.luminosity) / perihelion, 0.75)

        // this construct always brings a sense of dread
        while (true) {

            /* . . . as well as within it's gravitational reach.  We should be
             * computing the reach at aphelion and at perihelion, but they
             * aren't that different so we'll cut out some computation.
             */
            p.reach = getReach(p.a, p.mass)
            /* Reach(aphelion, p->mass); */
            p.rMax = aphelion + p.reach
            /* Reach(perihelion, p->mass); */
            p.rMin = perihelion - p.reach

            val previousMass = p.mass

            /* accrete dust */
            var swept = sweptMass(dust, p, this::dustDensity, { it.rMin / (1 + DoleConstants.W)..it.rMax / (1 - DoleConstants.W) })

            p.dustMass = Math.max(p.dustMass, swept)
            p.gasMass = Math.max(p.gasMass, swept * random.nextDouble(p.mass))

            /* accrete gas */
            if (p.dustMass > criticalMass) {
                // it's a gas giant
                p.isGasGiant = true

                val gasEffect = DoleConstants.K / ((DoleConstants.K - 1) * Math.pow(criticalMass / p.mass, DoleConstants.BETA) + 1)
                swept = sweptMass(gas, p, { dustDensity(it) * gasEffect }, { it.rMin..it.rMax })
                p.gasMass = Math.max(p.gasMass, swept)
            }

            p.mass = p.dustMass + p.gasMass

            if ((p.mass - previousMass) / p.mass < 0.01) {
                break
            }
        }

        /* You'll notice we didn't modify the band structure at all while
         * accreting matter, we do that now.
         */
        dust = updateBands(dust, p)

        if (p.isGasGiant) {
            /* do something with the gas density */
            /* In this case, it's cheaper to just recompute the accreted gas
             * in each iteration as we only use the one gas band.
             */
            gas = updateBands(gas, p)
        }
    }

    /**
     * We've found a collision, so we just stick the two planets together.
     * No fragments or moon creation, no close passes which would eject one
     * planet from the system, nothing fancy like that.  We weight the
     * final orbital radius by the masses of the planets involved, and
     * use the smaller eccentricity.
     *
     * @param p1 The first planet in the collision
     * @param p2 The second planet in the collision
     * @return A planet record for the resulting merged body
     */
    private fun mergePlanets(p1: Planet, p2: Planet): Planet {
        val perihelion: Double = p2.perihelion
        val aphelion: Double = p2.aphelion

        p2.a = (p1.mass + p2.mass) / (p1.mass / p1.a + p2.mass / p2.a)
        p2.e = Math.min(p1.e, p2.e)

        p2.rMax = aphelion + getReach(aphelion, p2.mass)
        p2.rMin = perihelion - getReach(perihelion, p2.mass)

        p2.mass = p2.mass + p1.mass
        p2.dustMass = p2.dustMass + p1.dustMass
        p2.gasMass = p2.gasMass + p1.gasMass

        /* p2->density = Density(p2->r); */
        /* Modification #3 */
        return p2
    }

    /**
     * Let's see who 'p' will run into, if anyone.
     *
     * @param star The star for the solar system
     * @param p    The planet being considered
     */
    private fun checkCoalesence(planets: SortedList<Planet, Double>, star: Primary, p: Planet) {
        var merged = true

        while (merged) {
            merged = false

            var index = planets.indexOf(p)

            var pindex = index - 1

            while (pindex >= 0) {
                val p1 = planets[pindex]

                if (p1.rMax >= p.rMin) {
                    planets[pindex] = mergePlanets(p1, p)
                    planets.removeAt(pindex + 1)
                    merged = true

                    --pindex
                } else {
                    pindex = -1
                }
            }

            if (merged) {
                index = planets.indexOf(p)
            }

            pindex = index + 1

            while (pindex < planets.size) {
                val p1 = planets[pindex]

                if (p1.rMin <= p.rMax) {
                    planets[pindex] = mergePlanets(p1, p)
                    planets.removeAt(pindex - 1)
                    merged = true

                    ++pindex
                } else {
                    pindex = Integer.MAX_VALUE
                }
            }

            if (merged) {
                evolvePlanet(star, p)
            }
        }
    }

    /**
     * Creates a solar system. On return a list of
     * planets will be available that can be obtained with getPlanets()
     *
     * @param star The star in the solar system.
     */
    fun createSystem(star: Primary): List<Planet> {
        /* A little initialization . . . */
        A = DoleConstants.AO * Math.sqrt(star.mass)

        val record = Math.pow(star.mass, 0.33).let { DustRecord(DoleConstants.MINRADIUS * it, DoleConstants.MAXRADIUS * it) }
        dust = listOf(record)
        gas = listOf(record.copy())

        val planets = SortedList(ArrayList(), Planet::a)

        /* . . . and we're off to play God. */
        while (!dust.isEmpty()) {
            val p = createPlanet()

            evolvePlanet(star, p)

            planets.add(p)

            checkCoalesence(planets, star, p)
        }

        return planets.filter { it.mass > 2e-8 }.map { planetStats.computePlanetStats(star, it) }
    }
}
