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
 * DolePlanetRecord.java
 *
 * Created on December 26, 2005, 6:24 PM
 *
 */
package dole

/**
 * A bean class representing a planet for the Dole accretion code.
 *
 * @author martin
 * @version $Id: DolePlanetRecord.java,v 1.3 2006-07-06 06:58:35 martin Exp $
 */
data class Planet(

        /**
         * Holds value of property rMin.
         */
        /**
         * Getter for property rMin.
         * @return Value of property rMin.
         */
        /**
         * Setter for property rMin.
         * @param rMin New value of property rMin.
         */
        var rMin: Double = 0.toDouble(),

        /**
         * Holds value of property rMax.
         */
        /**
         * Getter for property rMax.
         * @return Value of property rMax.
         */
        /**
         * Setter for property rMax.
         * @param rMax New value of property rMax.
         */
        var rMax: Double = 0.toDouble(),

        /**
         * Holds value of property reach.
         */
        /**
         * Getter for property reach.
         * @return Value of property reach.
         */
        /**
         * Setter for property reach.
         * @param reach New value of property reach.
         */
        var reach: Double = 0.toDouble(),
        /**
         * Setter for property a.
         * @param a New value of property a.
         */
        var a: Double = 0.toDouble(),
        /**
         * Setter for property e.
         * @param e New value of property e.
         */
        var e: Double = 0.toDouble(),
        /**
         * Setter for property mass.
         * @param mass New value of property mass.
         */
        var mass: Double = 0.toDouble(),
        /**
         * Setter for property gasGiant.
         * @param gasGiant New value of property gasGiant.
         */
        var isGasGiant: Boolean = false,
        /**
         * Setter for property dustMass.
         * @param dustMass New value of property dustMass.
         */
        var dustMass: Double = 0.toDouble(),
        /**
         * Setter for property gasMass.
         * @param gasMass New value of property gasMass.
         */
        var gasMass: Double = 0.toDouble(),
        /**
         * Setter for property radius.
         * @param radius New value of property radius.
         */
        var radius: Double = 0.toDouble(),
        /**
         * Setter for property density.
         * @param density New value of property density.
         */
        var density: Double = 0.toDouble(),
        /**
         * Setter for property orbPeriod.
         *
         * @param orbitalPeriod New value of property orbPeriod.
         */
        var orbitalPeriod: Double = 0.toDouble(),
        /**
         * Setter for property day.
         * @param day New value of property day.
         */
        var day: Double = 0.toDouble(),
        /**
         * Setter for property escapeVelocity.
         * @param escapeVelocity New value of property escapeVelocity.
         */
        var escapeVelocity: Double = 0.toDouble(),
        /**
         * Setter for property surfaceGravity.
         * @param surfaceGravity New value of property surfaceGravity.
         */
        var surfaceGravity: Double = 0.toDouble(),
        /**
         * Setter for property highTemperature.
         * @param highTemperature New value of property highTemperature.
         */
        var highTemperature: Double = 0.toDouble(),
        /**
         * Setter for property lowTemperature.
         * @param lowTemperature New value of property lowTemperature.
         */
        var lowTemperature: Double = 0.toDouble()
)
