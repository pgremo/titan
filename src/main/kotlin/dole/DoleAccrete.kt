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
     * @param star The star of this solar system
     * @return A planet record for the new planet
     */
    private fun createPlanet(star: Primary) = DolePlanetRecord().apply {
        primary = star
        isGasGiant = false
        mass = DoleConstants.M0
        dustMass = DoleConstants.M0
        gasMass = 0.0
        e = (1.0 - Math.pow(random.nextDouble(0.01..1.00), 0.077)) * 1.5

        val b = dust.random(random)!!
        a = (b.start + (b.endInclusive - b.start)) * Math.pow(random.nextDouble(), 2.0)
        rMin = a
        rMax = a
    }

    /**
     * Compute the amount of mass (of dust or gas) which the planet p will
     * sweep from the available material in one iteration.
     *
     * @param list     A list of dust records
     * @param p        The planet record for the planet being constructed
     * @return The amount of mass swept from the dust or gas
     */
    private fun sweptMass(list: List<DustRecord>, p: DolePlanetRecord, density: (Double) -> Double, rangeF: (DolePlanetRecord) -> ClosedRange<Double>): Double {
        var mass = 0.0

        val range = rangeF(p)
        var min = range.start
        var max = range.endInclusive

        /* Used in gas accretion, it's constant so we can move it out here.  */

        /*
         * Modification #3
         * Approximate density of material we're accreting.  This is actually
         * the density at the planet's orbit, but it's (hopefully) close enough.
         * It shouldn't matter for small planets, but large gas giants may
         * accrete too much.
         */

        /*
         * density = p->density;
         * if (type == 2) density *= tGas;
         */

        /* Traverse the list, looking at each existing band to see what we
         * would sweep up.
         */
        for ((innerEdge, outerEdge) in list) {
            /* check for trivial rejection */
            if (max < innerEdge || min > outerEdge) {
                continue
            }

            max = Math.min(max, outerEdge)
            min = Math.max(min, innerEdge)

            val r = (min + max) / 2.0

            /* Modification #3
             * If we were really strict, we'd try to integrate the density
             * function over the range (min, max) of orbital distance, but
             * we'll use the average distance instead.
             */

            /* The swept mass is supposed to be computed using the gravitational
             * reach and density at the minimum and maximum distances at which
             * dust is encountered.  I'm cheating here by using the reach at
             * the average orbital distance (computed in EvolvePlanet()) and
             * the density at the center of the band.  The total swept mass is
             * then 2 * reach        the height of the swept area
             *      * (max - min)    the width of the swept area
             *      2 * pi * r       revolve around center
             *      * density        the density of the swept volume
             * We can speed things up a bit by moving the constant values
             * outside the loop.
             */
            mass += r * (max - min) * density(r)
        }

        return 2.0 * Math.PI * 2.0 * p.reach * mass
    }

    /**
     * Update the band structure by removing or splitting bands from which
     * the planet would have accreted mass.
     *
     * @param list The dust list to be updated
     * @param p    The planet being constructed
     */
    private fun updateBands(list: List<DustRecord>, p: DolePlanetRecord): List<DustRecord> {
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
    private fun evolvePlanet(star: Primary, p: DolePlanetRecord) {
        /* Our planetoid will accrete all matter within it's orbit . . . */
        var perihelion = p.a * (1 - p.e)
        var aphelion = p.a * (1 + p.e)
        val criticalMass = DoleConstants.B * Math.pow(Math.sqrt(star.luminosity) / perihelion, 0.75)

        // this construct always brings a sense of dread
        while (true) {

            perihelion = p.a * (1 - p.e)
            aphelion = p.a * (1 + p.e)

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
    private fun mergePlanets(p1: DolePlanetRecord, p2: DolePlanetRecord): Planet {
        val perihelion: Double = p2.a * (1 - p2.e)
        val aphelion: Double = p2.a * (1 + p2.e)

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
    private fun checkCoalesence(star: Primary, p: DolePlanetRecord) {
        var merged = true

        while (merged) {
            merged = false

            val planets = star.planets

            var index = planets.indexOf(p)

            if (index < 0) {
                throw AssertionError("Did not find the planet in the list")
            }

            var pindex = index - 1

            while (pindex >= 0) {
                val p1 = planets[pindex] as DolePlanetRecord

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
                // then this could have changed
                index = planets.indexOf(p)
            }

            pindex = index + 1

            while (pindex < planets.size) {
                val p1 = planets[pindex] as DolePlanetRecord

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
    fun createSystem(star: Primary) {
        // fill in luminosity and ecosphere if not already set
        if (star.luminosity == 0.0) {
            star.luminosity = EnviroUtils.getLuminosity(star.mass)
        }

        star.rEcosphere = Math.sqrt(star.luminosity)
        star.rEcosphereInner = star.rEcosphere * 0.93
        star.rEcosphereOuter = star.rEcosphere * 1.1

        /* A little initialization . . . */
        A = DoleConstants.AO * Math.sqrt(star.mass)

        val radius = DoleConstants.MINRADIUS * Math.pow(star.mass, 0.33)..DoleConstants.MAXRADIUS * Math.pow(star.mass, 0.33)

        dust = listOf(DustRecord(radius.start, radius.endInclusive))
        gas = listOf(DustRecord(radius.start, radius.endInclusive))

        /* . . . and we're off to play God. */
        while (!dust.isEmpty()) {
            val p = createPlanet(star)

            evolvePlanet(star, p)

            star.planets.add(p)

            checkCoalesence(star, p)
        }

        val li = star.planets.iterator()

        while (li.hasNext()) {
            val pl = li.next()

            if (pl.mass > 2e-8) {
                planetStats.computePlanetStats(star, pl)
            } else {
                li.remove()
            }
        }
    }
}
