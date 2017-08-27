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
 * GenStar.java
 *
 * Created on December 22, 2005, 11:26 PM
 */
package dole

import java.util.*

/**
 * Random star generation with realistic, mass, luminosity and spectral class.
 * Converted to Java from public domain C code written by Andrew Folkins
 *
 * @author martin
 * @version $Id: GenStar.java,v 1.14 2006-07-06 06:58:33 martin Exp $
 */
class GenStar(utils: Random = Random()) {

    /** Map of classes to numbers  */
    private val classMap = mapOf(
            "O" to 0,
            "B" to 1,
            "A" to 2,
            "F" to 3,
            "G" to 4,
            "K" to 5,
            "M" to 6
    )

    /** Object used to obtain random numbers  */
    /**
     * Getter for property utils.
     * @return Value of property utils.
     */
    private var random: Random = utils

    // MagicNumber OFF

    /**
     * This table gives the approximate relative numbers of stars of the
     * various spectral types and luminosity classes, the units are stars
     * per million cubic parsecs. The program totals this information
     * and computes a cumulative distribution function from it for
     * actual use.  The spectral classes range from O on the left to M
     * on the right, the luminosities from an absolute magnitude of -7
     * at the top to +16 at the bottom.  Thus, the table looks roughly
     * like the traditional Hertzsprung-Russell diagram.
     *
     * One thing you'll notice about this, there's a *lot* of red dwarfs
     * in a realistic distribution, a star like the sun is in the top 10%
     * of the population.  This makes the occurance of habitable planets
     * pretty low.
     *
     * Most of this information is from a message I recived from John Carr
     * &lt;athena.mit.edu!jfc&gt; on April 19/88, he didn't mention his source
     * though he did make the comment that "the birthrate function is much
     * flatter at high luminosities than the luminosity function, due to
     * the short lifetime of high-mass stars.  This is important for young
     * areas."  I don't think that idea is accounted for here.
     */
    private val starCounts = arrayOf(
            /*        O        B        A        F        G        K        M       Mag */
            doubleArrayOf(0.0002, 0.0005, 0.0003, 0.0003, 0.00003, 0.0, 0.0), /*  -7 */
            doubleArrayOf(0.0005, 0.0025, 0.001, 0.001, 0.0001, 0.0004, 0.0004), /*  -6 */
            doubleArrayOf(0.001, 0.025, 0.01, 0.006, 0.008, 0.004, 0.01), /*  -5 */
            doubleArrayOf(0.003, 0.16, 0.01, 0.016, 0.025, 0.012, 0.012), /*  -4 */
            doubleArrayOf(0.01, 0.5, 0.05, 0.08, 0.08, 0.1, 0.06), /*  -3 */
            doubleArrayOf(0.01, 2.5, 0.08, 0.2, 0.3, 0.6, 0.4), /*  -2 */
            doubleArrayOf(0.01, 12.5, 1.0, 1.6, 1.0, 2.5, 3.0), /*  -1 */
            doubleArrayOf(0.001, 20.0, 20.0, 2.0, 8.0, 25.0, 10.0), /*  +0 */
            doubleArrayOf(0.0, 30.0, 100.0, 30.0, 30.0, 120.0, 10.0), /*  +1 */
            doubleArrayOf(0.0, 20.0, 200.0, 160.0, 50.0, 110.0, 0.0), /*  +2 */
            doubleArrayOf(0.0, 10.0, 80.0, 700.0, 150.0, 100.0, 0.0), /*  +3 */
            doubleArrayOf(0.0, 0.0, 30.0, 1200.0, 700.0, 100.0, 0.0), /*  +4 */
            doubleArrayOf(0.0, 0.0, 0.0, 600.0, 2000.0, 300.0, 0.0), /*  +5 */
            doubleArrayOf(0.0, 0.0, 0.0, 200.0, 1500.0, 1500.0, 10.0), /*  +6 */
            doubleArrayOf(0.0, 0.0, 0.0, 100.0, 800.0, 3000.0, 100.0), /*  +7 */
            doubleArrayOf(0.0, 0.0, 0.0, 10.0, 400.0, 2500.0, 1000.0), /*  +8 */
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 200.0, 1500.0, 3000.0), /*  +9 */
            doubleArrayOf(0.0, 10.0, 0.0, 0.0, 0.0, 400.0, 8000.0), /* +10 */
            doubleArrayOf(0.0, 100.0, 30.0, 10.0, 0.0, 200.0, 9000.0), /* +11 */
            doubleArrayOf(0.0, 200.0, 400.0, 100.0, 0.0, 100.0, 10000.0), /* +12 */
            doubleArrayOf(0.0, 400.0, 600.0, 300.0, 100.0, 400.0, 10000.0), /* +13 */
            doubleArrayOf(0.0, 800.0, 1000.0, 1000.0, 600.0, 800.0, 10000.0), /* +14 */
            doubleArrayOf(0.0, 1500.0, 2000.0, 1000.0, 1500.0, 1200.0, 8000.0), /* +15 */
            doubleArrayOf(0.0, 3000.0, 5000.0, 3000.0, 3000.0, 0.0, 6000.0))/* +16 */

    /** A table of star counts by magnitude class and spectral class  */
    private val starCountsClass = Array(N_MAG_CLASS) { DoubleArray(N_SPC_CLASS) }

    /** Absolute magnitude - anything of number or below is that class
     * e.g Class G, mag 5.0 is class V.  Most of this is guesstimates
     * from a H-R diagram.
     */
    private val lClassMag = arrayOf(
            /*   O      B       A      F      G      K      M */
            doubleArrayOf(-6.5, -6.5, -6.5, -6.5, -6.5, -6.5, -6.5), /* Ia */
            doubleArrayOf(-6.0, -6.0, -5.0, -5.0, -5.0, -5.0, -5.0), /* Ib */
            doubleArrayOf(-5.0, -3.5, -3.0, -2.0, -2.0, -2.5, -2.5), /* II */
            doubleArrayOf(-4.0, -3.0, -0.5, 1.5, 2.5, 3.0, 2.0), /* III */
            doubleArrayOf(-3.0, -2.0, 0.5, 2.5, 3.5, 5.5, 2.0), /* IV */
            doubleArrayOf(-1.0, 2.0, 2.5, 5.0, 7.0, 10.0, 14.0), /* V */
            doubleArrayOf(1.0, 4.0, 5.0, 9.0, 10.0, 20.0, 20.0), /* VI */
            doubleArrayOf(20.0, 20.0, 20.0, 20.0, 20.0, 20.0, 20.0))/* VII */

    /** Array of names of the spectral classes  */
    private val spectralClass = arrayOf("O", "B", "A", "F", "G", "K", "M")

    /** Array of names of the luminosity classes  */
    private val luminosityClass = arrayOf("Ia", "Ib", "II", "III", "IV", "V", "VI", "VII")

    /**
     * Compute the table of probabilities by class
     */
    private fun computeProbabilitiesByClass() {
        var i: Int
        var j = 0
        var t: Double
        var total: Double

        while (j < N_SPC_CLASS) {
            total = 0.0

            i = 0
            while (i < N_MAG_CLASS) {
                total += starCounts[i][j]
                ++i
            }

            t = 0.0

            i = 0
            while (i < N_MAG_CLASS) {
                t += this.starCounts[i][j]
                this.starCountsClass[i][j] = t / total
                ++i
            }
            ++j
        }
    }

    /**
     * Compute a cumulative distribution from the values in StarCounts[][]
     * by adding up all the values, then dividing each entry in the array
     * by the total.
     */
    private fun computeProbabilities() {
        var i = 0
        var j: Int
        var t = 0.0
        var total = 0.0

        while (i < N_MAG_CLASS) {
            j = 0
            while (j < N_SPC_CLASS) {
                total += this.starCounts[i][j]
                ++j
            }
            ++i
        }

        i = 0
        while (i < N_MAG_CLASS) {
            j = 0
            while (j < N_SPC_CLASS) {
                t += this.starCounts[i][j]
                this.starCounts[i][j] = t / total
                ++j
            }
            ++i
        }
    }

    /**
     * Generate and return a new random star of a particlar magnitude and
     * spectral class
     *
     * @param magClass The magnitude class of the desired star
     * @param specClass The spectral class of the desired star
     * @return A randomly generated star of the specified class
     */
    private fun generateStar(magClass: Int, specClass: Int): Primary {
        val sun = Primary()

        var mClass = magClass

        sun.spectralClass = spectralClass[specClass]

        var t = this.random.nextDouble()
        /* Give it a random subclass */
        sun.spectralSubclass = (t * 10.0).toInt()
        sun.absoluteMagnitude = MIN_MAGNITUDE.toDouble() + mClass.toDouble() + t

        // TODO: check whether needed, as luminosity set by stargen

        /* Compute luminosity relative to Sun */
        sun.luminosity = Math.pow(2.5118, 4.7 - sun.absoluteMagnitude)

        var loopI = 0

        mClass = -1
        while (loopI < N_LUM_CLASS && mClass < 0) {
            if (lClassMag[loopI][specClass] >= sun.absoluteMagnitude) {
                mClass = loopI
            }
            ++loopI
        }

        sun.luminosityClass = this.luminosityClass[mClass]

        sun.mass = when (mClass) {
            0, 1, 2, 3 -> {
                /* Supergiants & giants */
                t = Math.log(sun.luminosity) + this.random.nextDouble() / 5.0
                Math.exp(t / 3.0)
            }

            4, 5, 6 -> {
                /* subgiants, dwarfs, subdwarfs */
                t = Math.log(sun.luminosity) + 0.1 + (this.random.nextDouble() / 5.0 - 0.1)
                Math.exp(t / 4.1)
            }

            7 ->
                /* white dwarfs */
                0.7 * this.random.nextDouble() + 0.6

            else -> {
                throw AssertionError("Unexpected program state")
            }
        }

        sun.ecosphere = Math.sqrt(sun.luminosity)
        /* next calc is approximate */
        sun.ecosphereInner = 0.93 * sun.ecosphere
        sun.ecosphereOuter = 1.1 * sun.ecosphere

        // assign these parameters entirely randomly for now
        sun.rightAscension = this.random.nextDouble() * 360.0
        sun.declination = this.random.nextDouble() * 180.0 - 90.0
        sun.distance = this.random.nextDouble() * 500.0 + 10.0

        return sun
    }

    /**
     * Generate and return a new star according to the probability
     * distribution calculated from the data tables in this class
     *
     * @return A randomly generated star object
     */
    fun generateStar(): Primary {
        var i = -1
        var j = -1

        val rnd = random.nextDouble()

        var loopI = 0
        var loopJ: Int

        while (loopI < N_MAG_CLASS && i < 0) {
            loopJ = 0
            while (loopJ < N_SPC_CLASS && i < 0) {
                if (this.starCounts[loopI][loopJ] >= rnd) {
                    i = loopI
                    j = loopJ
                }
                ++loopJ
            }
            ++loopI
        }

        return generateStar(i, j)
    }

    /**
     * Generate a star given a text format spectral class
     *
     * @param specClass The desired spectral class of the star to be generated.
     * @return A randomly generated star of the specified spectral class. An
     * `IllegalArgumentException` is thrown if the spectral class is
     * not one of those known to this class.
     */
    fun generateStar(specClass: String): Primary {
        val theClass = classMap[specClass] ?: throw IllegalArgumentException("Unknown spectral class: $specClass")

        return generateStar(theClass)
    }

    /**
     * Generate and return a new random star of a particlar spectral class
     *
     * @param specClass The spectral class of the desired star
     * @return A randomly generated star of the specified class
     */
    private fun generateStar(specClass: Int): Primary {
        val rnd = random.nextDouble()

        var i = -1
        var loopI = 0
        while (loopI < N_MAG_CLASS && i < 0) {
            if (starCountsClass[loopI][specClass] >= rnd) {
                i = loopI
            }
            ++loopI
        }

        return generateStar(i, specClass)
    }

    companion object {
        /** Minimum generated magnitude  */
        val MIN_MAGNITUDE = -7

        /** Maximum generated magnitude  */
        val MAX_MAGNITUDE = 16

        /** Number of spectral classes in tables  */
        val N_SPC_CLASS = 7

        /** Number of magnitude classes in tables  */
        val N_MAG_CLASS = 24

        /** Number of luminosity classes in tables  */
        val N_LUM_CLASS = 8

        // RequireThis OFF: MIN_MAGNITUDE
        // RequireThis OFF: MAX_MAGNITUDE
        // RequireThis OFF: N_SPC_CLASS
        // RequireThis OFF: N_MAG_CLASS
        // RequireThis OFF: N_LUM_CLASS

        // MS: These should be enums but they were used to index arrays directly so
        // I left them as constants for now as they were in the original code

        /** Constant for spectral class O  */
        val SPECTRAL_CLASS_O = 0

        /** Constant for spectral class B  */
        val SPECTRAL_CLASS_B = 1

        /** Constant for spectral class A  */
        val SPECTRAL_CLASS_A = 2

        /** Constant for spectral class F  */
        val SPECTRAL_CLASS_F = 3

        /** Constant for spectral class G  */
        val SPECTRAL_CLASS_G = 4

        /** Constant for spectral class K  */
        val SPECTRAL_CLASS_K = 5

        /** Constant for spectral class M  */
        val SPECTRAL_CLASS_M = 6

        /** Constant for lumoinosity class Ia  */
        val LUMINOSITY_CLASS_IA = 0

        /** Constant for lumoinosity class Ib  */
        val LUMINOSITY_CLASS_IB = 1

        /** Constant for lumoinosity class II  */
        val LUMINOSITY_CLASS_II = 2

        /** Constant for lumoinosity class III  */
        val LUMINOSITY_CLASS_III = 3

        /** Constant for lumoinosity class IV  */
        val LUMINOSITY_CLASS_IV = 4

        /** Constant for lumoinosity class V  */
        val LUMINOSITY_CLASS_V = 5

        /** Constant for lumoinosity class VI  */
        val LUMINOSITY_CLASS_VI = 6

        /** Constant for lumoinosity class VII  */
        val LUMINOSITY_CLASS_VII = 7
    }

    init {
        computeProbabilitiesByClass()
        computeProbabilities()
    }
}
