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
 * MathUtils.java
 *
 * Created on December 21, 2005, 2:43 PM
 */
package dole

import java.util.Random

/**
 * General utility functions
 *
 * @author martin
 * @version $Id: MathUtils.java,v 1.8 2006-07-06 06:59:43 martin Exp $
 */
class MathUtils {
    /** The seed value for this random number generator  */
    /**
     * Getter for property seed.
     * @return Value of property seed.
     */
    /**
     * Set the seed on the random number generator being used for these operations
     *
     * @param seed The seed value to use
     */
    var seed: Long = 0
        set(seed) {
            field = seed
            this.random!!.setSeed(seed)
        }

    /** The random number generator in use by this object  */
    /**
     * Getter for property random.
     * @return Value of property random.
     */
    /**
     * Setter for property random
     *
     * @param random The new value of the random property
     */
    var random: Random? = null

    /**
     * Creates a new instance of MathUtils
     */
    constructor() {
        this.random = Random()
    }

    /**
     * Creates a new instance of MathUtils
     *
     * @param random The random number generator to use
     */
    constructor(random: Random) {
        this.random = random
    }

    /**
     * Delegate this to the random number generator and avoid the temptation
     * to access the random object directly
     *
     * @return the next random number as a double between 0 and 1
     */
    fun nextDouble(): Double {
        return this.random!!.nextDouble()
    }

    /**
     * Delegate this to the random number generator and avoid the temptation
     * to access the random object directly
     *
     * @return the next random number as a float between 0 and 1
     */
    fun nextFloat(): Float {
        return this.random!!.nextFloat()
    }

    /**
     * Delegate this to the random number generator and avoid the temptation
     * to access the random object directly
     *
     * @return the next random number as an integer
     */
    fun nextInt(): Int {
        return this.random!!.nextInt()
    }

    /**
     * Delegate this to the random number generator and avoid the temptation
     * to access the random object directly
     *
     * @param n The limit for the number to be generated
     * @return the next random number as an integer between 0 and n-1
     */
    fun nextInt(n: Int): Int {
        return this.random!!.nextInt(n)
    }

    /**
     * This function returns a random real number between the specified
     * inner and outer bounds.
     *
     * @param inner The lower bound for the random number
     * @param outer The upper bound for the random number
     * @return A random number in the range between the specified bounds (inclusive)
     */
    fun randomNumber(inner: Double, outer: Double): Double {
        val range = outer - inner

        return this.random!!.nextDouble() * range + inner
    }

    /**
     * This function returns a value within a certain variation of the
     * exact value given it in 'value'.
     *
     * @param value The central value to be used
     * @param variation The allowed variation about the central value
     * @return A random number in the range value-variation to value+variation
     */
    fun about(value: Double, variation: Double): Double {
        return value + value * randomNumber(-variation, variation)
    }

    // MagicNumber OFF

    /**
     * Calculate a random planetary eccentricity value in a sensible range
     *
     * @return A randomly chosen eccentricity value
     */
    fun randomEccentricity(): Double {
        var e: Double

        e = 1.0 - Math.pow(randomNumber(0.0, 1.0), Constants.ECCENTRICITY_COEFF)

        // Note that this corresponds to a random number less than 10E-26
        if (e > .99) {
            e = .99
        }

        return e
    }

    companion object {

        // MagicNumber ON

        /**
         * Convenience function to compute the second power of a value
         *
         * @param val The input value
         * @return The second power of the input value
         */
        fun pow2(`val`: Double): Double {
            return `val` * `val`
        }

        /**
         * Convenience function to compute the third power of a value
         *
         * @param val The input value
         * @return The third power of the input value
         */
        fun pow3(`val`: Double): Double {
            return `val` * `val` * `val`
        }

        /**
         * Convenience function to compute the fourth power of a value
         *
         * @param val The input value
         * @return The fourth power of the input value
         */
        fun pow4(`val`: Double): Double {
            return `val` * `val` * `val` * `val`
        }

        /**
         * Convenience function to compute the fourth root of a value
         *
         * @param val The input value
         * @return The fourth root of the input value
         */
        fun fourthRoot(`val`: Double): Double {
            return Math.sqrt(Math.sqrt(`val`))
        }

        /**
         * Convenience function to compute the cube root of a value
         *
         * @param val The input value
         * @return The cube root of the input value
         */
        fun cubeRoot(`val`: Double): Double {
            return Math.pow(`val`, 1.0 / 3.0)
        }
    }
}
