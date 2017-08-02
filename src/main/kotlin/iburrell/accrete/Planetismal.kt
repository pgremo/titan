// Author: Ian Burrell  <iburrell@leland.stanford.edu>
// Created: 1997/02/09
// Modified:

// Copyright 1997 Ian Burrell

package iburrell.accrete

import java.io.PrintStream

/**

 * This class stores the data needed for a planetismal, which is
 * basically mass and the orbit parameters.  It also includes a
 * pointer to the next in the list.  Also, has some functions that
 * calls the main calculators in the DoleParams class with the proper
 * values for the nucleus.

 * The Accrete class manipulates the internals of this class directly.
 * Outside clients use the accessors.

 */
class Planetismal @JvmOverloads internal constructor(a: Double, e: Double, m: Double = PROTOPLANET_MASS, giant: Boolean = false) {

    // Accessors

    var orbitalAxis: Double = 0.toDouble()
        internal set        // semi-major axis (AU)
    var eccentricity: Double = 0.toDouble()
        internal set        // eccentricity
    var massSolar: Double = 0.toDouble()
        internal set        // mass (solar mass)
    var isGasGiant: Boolean = false
        internal set
    internal var next: Planetismal? = null
    val massEarth: Double
        get() = massSolar * Astro.SOLAR_MASS_EARTH_MASS

    init {
        orbitalAxis = a
        eccentricity = e
        massSolar = m
        isGasGiant = giant
        next = null
    }


    internal fun PerihelionDistance(): Double {
        return DoleParams.PerihelionDistance(orbitalAxis, eccentricity)
    }

    internal fun AphelionDistance(): Double {
        return DoleParams.AphelionDistance(orbitalAxis, eccentricity)
    }

    internal fun ReducedMass(): Double {
        return DoleParams.ReducedMass(massSolar)
    }

    internal fun ReducedMargin(): Double {
        return DoleParams.ReducedMargin(massSolar)
    }

    internal fun InnerEffectLimit(): Double {
        return DoleParams.InnerEffectLimit(orbitalAxis, eccentricity,
                DoleParams.ReducedMargin(massSolar))
    }

    internal fun OuterEffectLimit(): Double {
        return DoleParams.OuterEffectLimit(orbitalAxis, eccentricity,
                DoleParams.ReducedMargin(massSolar))
    }

    internal fun InnerSweptLimit(): Double {
        return DoleParams.InnerSweptLimit(orbitalAxis, eccentricity,
                DoleParams.ReducedMargin(massSolar))
    }

    internal fun OuterSweptLimit(): Double {
        return DoleParams.OuterSweptLimit(orbitalAxis, eccentricity,
                DoleParams.ReducedMargin(massSolar))
    }

    internal fun CriticalMass(luminosity: Double): Double {
        return DoleParams.CriticalMass(orbitalAxis, eccentricity, luminosity)
    }


    fun Print(out: PrintStream) {
        out.print(orbitalAxis.toString() + " " + eccentricity + " " + massSolar)
        if (massSolar > 2e-15)
            out.print(" (" + massSolar * Astro.SOLAR_MASS_EARTH_MASS + ")")
        if (isGasGiant)
            out.print(" giant")
        out.println()
    }

    companion object {

        internal val PROTOPLANET_MASS = 1.0E-15 // units of solar masses

        internal fun RandomPlanetismal(inner: Double, outer: Double): Planetismal {
            return Planetismal(DoleParams.RandomDouble(inner, outer), DoleParams.RandomEccentricity())
        }
    }

}
