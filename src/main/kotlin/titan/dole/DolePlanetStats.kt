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
 * DolePlanetStats.java
 *
 * Created on December 27, 2005, 9:33 AM
 *
 */
package titan.dole

import java.util.*

/**
 * Planetstat : Compute the physical properties of a planet.
 *
 * Converted to Java from the public domain C code by Andrew Folkins
 *
 * @author martin
 * @version $Id: DolePlanetStats.java,v 1.3 2006-07-06 06:58:35 martin Exp $
 */
class DolePlanetStats(utils: Random) {

    /** The math utility object in use  */
    private var utils: Random? = utils

    // MagicNumber OFF

    /**
     * Compute the stellar flux at the top of the planet's atmosphere.  This
     * is scaled from the solar constant at Earth of 1400 Watts / square metre
     * by a linear increase of luminosity and an inverse square relation to
     * the distance from the star.  Luminosity is given in Suns, radius in AU's.
     *
     * @param sunLum The stellar luminosity (Sol = 1.0)
     * @param radius The distance from the star in AU
     * @return The stellar flux in watts per square metre
     */
    private fun cFlux(sunLum: Double, radius: Double): Double {
        return 1400.0 * sunLum / (radius * radius)
    }

    /**
     * Compute the temperature at the planet, given the luminosity of it's
     * star and orbital distance.  This is the black body temperature, and
     * does not take into account greenhouse effects or radiation from the
     * planet.
     *
     * @param sunLum The stellar luminosity (Sol = 1.0)
     * @param radius The distance from the star in AU
     * @return The planetary black body temperature
     */
    private fun cTemp(sunLum: Double, radius: Double): Double {
        return Math.pow(cFlux(sunLum, radius) / (4.0 * DoleConstants.R), 0.25)
    }

    /**
     * compute the surface gravity of a planet given it's mass in kg and
     * radius in kilometres, return metres / second ** 2.
     *
     * @param mass The planetary mass in KG
     * @param radius The planetary radius in KM
     * @return The surface gravity in meters per second squared
     */
    fun surfaceGravity1(mass: Double, radius: Double): Double {
        /* convert to metres */
        val radiusM = radius * 1000.0

        return DoleConstants.G * mass / (radiusM * radiusM)
    }

    /**
     * compute the surface gravity of a planet given it's density in grams
     * cubic centimetres and radius in kilometres, returns metres / second ** 2.
     *
     * @param density The density of the planet in grams per cubic centimetre
     * @param radius The planetary radius in KM
     * @return The surface gravity in meters per second squared
     */
    private fun surfaceGravity2(density: Double, radius: Double): Double {
        return 4000000.0 * Math.PI * DoleConstants.G * radius * density / 3.0
    }

    /**
     * Compute the escape velocity in km/sec of a planet given it's mass in
     * kg and radius in km
     *
     * @param mass The planetary mass in KG
     * @param radius The planetary radius in KM
     * @return The escape velocity in km per second
     */
    private fun escapeVelocity(mass: Double, radius: Double): Double {
        return Math.sqrt(2.0 * DoleConstants.G * mass / (radius * 1000)) / 1000.0
    }

    /**
     * Compute a whole bunch of other stuff.  Most of this is from Fogg.
     *
     * @param star The star in this sytem
     * @param p The planet to be processed
     */
    fun computePlanetStats(star: Primary, p: Planet): Planet {
        val k2: Double
        var dw: Double = 1.0 * /* matter/mass distribution == density??? */
                (p.radius / 6422.0) * /* in km */
                (1.0 / p.mass) * /* in Earth masses */
                Math.pow(star.mass, 2.0) * Math.pow(1.0 / p.a, 6.0) * -1.3E-6

        p.highTemperature = cTemp(star.luminosity, p.a)
        p.lowTemperature = cTemp(star.luminosity, p.a)

        p.mass = p.dustMass + p.gasMass

        /* convert solar-relative masses to Earth-relative masses */
        p.mass = p.mass * 329390.0
        p.dustMass = p.dustMass * 329390.0
        p.gasMass = p.gasMass * 329390.0

        /* if less than a tenth of the mass is gas, it ain't a gas giant! */
        if (p.gasMass / p.mass < 0.1) {
            p.isGasGiant = false
        }

        /* fiddle densities to likely values */
        if (p.isGasGiant) {
            val dm = Math.pow(p.dustMass, 0.125) * Math.pow(
                    star.ecosphere / p.a, 0.25) * 5.5

            val dg = (0.5 + 0.5 * this.utils!!.nextDouble()) * Math.sqrt(
                    273 / p.highTemperature)

            val vd = p.dustMass / dm
            val vg = p.gasMass / dg

            p.density = p.mass / (vd + vg)

            k2 = 0.24
        } else {
            /* tonnes / m**3 */
            p.density = Math.pow(p.mass, 0.125) * Math.pow(
                    star.ecosphere / p.a, 0.25) * 5.5

            k2 = 0.33
        }

        p.orbitalPeriod = Math.sqrt(Math.pow(p.a, 3.0) / star.mass)
        p.radius = Math.pow(p.mass * 6.0E21 / (p.density * 4.2), 0.333) / 1000.0

        // radians / sec
        p.day = Math.sqrt(
                8.73E-2 * p.mass / (0.5 * k2 * p.radius * p.radius))

        /* Tidal braking forces from central star */

        dw *= 4.0 /* star->age */ /* 10E9 years */

        p.day = p.day + dw

        if (p.day < 0.0) {
            // TODO: should set the resonant flag?
            p.day = 0.0
        } else {
            p.day = 2 * Math.PI / (3600.0 * p.day) // hours
        }

        /* check for spin resonance period */
        if (p.e > 0.1 && p.day == 0.0) {
            p.day = p.orbitalPeriod * (1.0 - p.e) / (1.0 + p.e) * 24.0
        }

        p.surfaceGravity = surfaceGravity2(p.density, p.radius)
        p.escapeVelocity = escapeVelocity(
                p.mass * DoleConstants.MASS_OF_EARTH, p.radius)

        /* now put masses back into solar units */
        p.mass = p.mass / 329390.0
        p.dustMass = p.dustMass / 329390.0
        p.gasMass = p.gasMass / 329390.0

        return p
    }

}
