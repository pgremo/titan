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

import org.apache.commons.logging.LogFactory

import java.util.*

import java.util.Comparator.comparing

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
 * @param utils The math utility object to use for random numbers
 */
internal constructor(
        /**
         * The maths object used for random numbers
         */
        private val utils: MathUtils) {

    // RequireThis OFF: log
    // MagicNumber OFF

    /**
     * AO * sqrt(star->mass)
     */
    protected var A: Double = 0.toDouble()

    /**
     * Minimum radius of planetismal injection, = MINRADIUS, MAXRADIUS
     */
    private var minRadius: Double = 0.toDouble()

    /**
     * Max radius of injection . . . modified by luminosity of primary
     */
    private var maxRadius: Double = 0.toDouble()

    /**
     * Linked list of dust records
     */
    private var list0: LinkedList<DustRecord>? = LinkedList()

    /**
     * Linked list of dust records
     */
    private var list2: LinkedList<DustRecord>? = LinkedList()

    /**
     * Critical mass for gas accretion
     */
    private var criticalMass: Double = 0.toDouble() /* for accreting gas */

    /**
     * Profiling counter (from original code, not needed in Java but this one
     * is used for something else)
     */
    private var nNucleus: Short = 0

    /**
     * The object used to get details about the generated plants
     */
    private val planetStats: DolePlanetStats = DolePlanetStats(this.utils)

    /**
     * Get the density for a particular radius
     *
     * @param r The orbital radius to get the density for
     * @return The calculated density
     */
    private fun getDensity(r: Double): Double {
        return A * Math.exp(
                -DoleConstants.ALPHA * Math.pow(r, DoleConstants.GAMMA))
    }

    /**
     * Get the gravitational reach for a particular mass at a given orbital radius
     *
     * @param radius The orbital radius to get the density for
     * @param mass   The mass of interest
     * @return The calculated reach
     */
    private fun getReach(radius: Double, mass: Double): Double {
        return radius * Math.pow(mass / (1.0 + mass), 0.25)
    }

    /**
     * Adds a planet to the list and keeps it sorted into order by increasing
     * distance from the star.
     *
     * @param list The list of planets
     * @param newp The new planet to be added
     */
    private fun addPlanet(list: MutableList<Planet>, newp: Planet) {
        list.add(newp)
        list.sortWith(comparing<Planet, Double>(Planet::a))
    }

    /**
     * Initialize the dust band information
     */
    private fun initBands() {
        list0 = LinkedList()
        list2 = LinkedList()

        this.list0!!.add(DustRecord(minRadius, maxRadius))
        this.list2!!.add(DustRecord(minRadius, maxRadius))
    }

    /**
     * Release any dust information held in the lists
     */
    private fun freeBands() {
        this.list0!!.clear()
        this.list0 = null

        this.list2!!.clear()
        this.list2 = null
    }

    /**
     * Create a new planet around the star
     *
     * @param star The star of this solar system
     * @return A planet record for the new planet
     */
    private fun createPlanet(star: Primary): DolePlanetRecord {
        val newp = DolePlanetRecord()

        newp.primary = star

        newp.isGasGiant = false
        newp.mass = DoleConstants.M0
        newp.dustMass = DoleConstants.M0
        newp.gasMass = 0.0

        newp.e = 1.0 - Math.pow(this.utils.nextDouble() * 0.99 + 0.01, 0.077)

        newp.a = this.utils.nextDouble()
        newp.a = newp.a * newp.a

        /* Fire the first 20 nuclei in at random, then aim them at the
         * remaining dust bands.  We're just grabbing the first dust band
         * in the list, but the list isn't kept sorted so this is still
         * somewhat random.
         */
        if (nNucleus <= 20000) {
            newp.e = newp.e * 1.5
            newp.a = minRadius + (maxRadius - minRadius) * newp.a
        } else {
            val b = list0!!.peek()

            newp.radius = b.innerEdge + (b.outerEdge - b.innerEdge) * newp.a
            newp.e = newp.e * 2.0
        }

        newp.rMin = newp.a
        newp.rMax = newp.a
        /* newp->density = Density(newp->r); */
        /* Modification #3 */
        // planet number
        newp.number = 0
        ++nNucleus

        addPlanet(star.planets, newp)

        return newp
    }

    /**
     * Compute the amount of mass (of dust or gas) which the planet p will
     * sweep from the available material in one iteration.
     *
     * @param list     A list of dust records
     * @param listtype Which list this corresponds to (dust, gas)
     * @param p        The planet record for the planet being constructed
     * @return The amount of mass swept from the dust or gas
     */
    private fun sweptMass(
            list: LinkedList<DustRecord>?, listtype: Int, p: DolePlanetRecord): Double {
        var r: Double
        var mass = 0.0
        var min: Double
        var max: Double
        var density: Double
        val tGas: Double = DoleConstants.K / ((DoleConstants.K - 1) * Math.pow(
                criticalMass / p.mass, DoleConstants.BETA) + 1)

        min = p.rMin
        max = p.rMax

        /* Account for eccentricity of dust particles */
        if (listtype == 0) {
            min /= (1 + DoleConstants.W)
            max /= (1 - DoleConstants.W)
        }

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
         * if (listtype == 2) density *= tGas;
         */

        /* Traverse the list, looking at each existing band to see what we
         * would sweep up.
         */
        for (b in list!!) {
            /* check for trivial rejection */
            if (max < b.innerEdge || min > b.outerEdge) {
                continue
            }

            if (max > b.outerEdge) {
                max = b.outerEdge
            }

            if (min < b.innerEdge) {
                min = b.innerEdge
            }

            r = (min + max) / 2.0

            /* Modification #3
             * If we were really strict, we'd try to integrate the density
             * function over the range (min, max) of orbital distance, but
             * we'll use the average distance instead.
             */
            density = getDensity(r)

            if (listtype == 2) {
                density *= tGas
            }

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
            mass += r * (max - min) * density
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
    private fun updateBands(list: LinkedList<DustRecord>?, p: DolePlanetRecord) {
        val min = p.rMin /* minimum and maximum reach of the planet */
        val max = p.rMax

        val i = list!!.iterator()

        var pendingPrepend: MutableList<DustRecord>? = null

        while (i.hasNext()) {
            val b = i.next()

            /* check for trivial rejection */
            if (max < b.innerEdge || min > b.outerEdge) {
                continue
            }

            if (max < b.outerEdge) {
                if (min > b.innerEdge) {
                    /* interval within band, so split it */
                    val newband = DustRecord(b.innerEdge, min)
                    b.innerEdge = max

                    if (pendingPrepend == null) {
                        pendingPrepend = ArrayList()
                    }

                    pendingPrepend.add(newband)
                } else {
                    /* interval overlaps inner edge */
                    b.innerEdge = max
                }
            } else {
                if (min > b.innerEdge) {
                    /* interval overlaps outer edge */
                    b.outerEdge = min
                } else {
                    /* interval contains band, so kill it */
                    i.remove()
                }
            }
        }

        // now add the elements we couldn't because we'd have mucked up the iterator
        // java list/iterator semantics can be v. annoying sometimes
        if (pendingPrepend != null) {
            for (newband in pendingPrepend) {
                list.addFirst(newband)
            }
        }
    }

    /**
     * Sweep up all available dust and gas.
     *
     * @param star The star for this solar system
     * @param p    The planet being constructed
     */
    private fun evolvePlanet(star: Primary, p: DolePlanetRecord?) {
        var perihelion: Double
        var aphelion: Double
        var previousMass: Double
        var swept: Double

        if (p == null) {
            if (log.isDebugEnabled) {
                log.debug("Called evolvePlanet with null planet reccord")
            }

            return
        }

        /* Our planetoid will accrete all matter within it's orbit . . . */
        perihelion = p.a * (1 - p.e)
        aphelion = p.a * (1 + p.e)

        this.criticalMass = DoleConstants.B * Math.pow(
                Math.sqrt(star.luminosity) / perihelion, 0.75)

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

            previousMass = p.mass

            /* accrete dust */
            swept = sweptMass(this.list0, 0, p)

            p.dustMass = Math.max(p.dustMass, swept)
            p.gasMass = Math.max(
                    p.gasMass,
                    swept * this.utils.nextDouble() * p.mass)

            /* accrete gas */
            if (p.dustMass > this.criticalMass) {
                // it's a gas giant
                p.isGasGiant = true

                swept = sweptMass(this.list2, 2, p)
                p.gasMass = Math.max(p.gasMass, swept)
            }

            p.mass = p.dustMass + p.gasMass

            if ((p.mass - previousMass) / p.mass < 0.01) {
                break
            }
        }

        //        if (log.isDebugEnabled())
        //            log.debug("Iterations in evolvePlanet: " + evolveCount + " " +
        //                    ((NonRandomRandom) this.utils.getRandom()).getCallCount());

        /* You'll notice we didn't modify the band structure at all while
         * accreting matter, we do that now.
         */
        updateBands(this.list0, p)

        if (p.isGasGiant) {
            /* do something with the gas density */
            /* In this case, it's cheaper to just recompute the accreted gas
             * in each iteration as we only use the one gas band.
             */
            updateBands(this.list2, p)
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
    private fun mergePlanets(
            p1: DolePlanetRecord, p2: DolePlanetRecord): Planet {
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

                    --pindex

                    merged = true
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

                    ++pindex

                    merged = true
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
        var i = 1

        // fill in luminosity and ecosphere if not already set
        if (star.luminosity == 0.0) {
            star.luminosity = EnviroUtils.getLuminosity(star.mass)
        }

        star.rEcosphere = Math.sqrt(star.luminosity)
        star.rEcosphereInner = star.rEcosphere * 0.93
        star.rEcosphereOuter = star.rEcosphere * 1.1

        // done
        this.nNucleus = 0

        /* A little initialization . . . */
        this.A = DoleConstants.AO * Math.sqrt(star.mass)

        minRadius = DoleConstants.MINRADIUS * Math.pow(star.mass, 0.33)
        maxRadius = DoleConstants.MAXRADIUS * Math.pow(star.mass, 0.33)

        initBands()

        /* . . . and we're off to play God. */
        while (!list0!!.isEmpty()) {
            val p = createPlanet(star)

            evolvePlanet(star, p)

            checkCoalesence(star, p)
        }

        freeBands()

        val li = star.planets.listIterator()

        while (li.hasNext()) {
            val pl = li.next()

            if (pl.mass > 2e-8) {
                pl.number = ++i

                planetStats.computePlanetStats(star, pl)
            } else {
                li.remove()
            }
        }
    }

    companion object {
        /**
         * Our logging object
         */
        private val log = LogFactory.getLog(DoleAccrete::class.java)
    }

}
