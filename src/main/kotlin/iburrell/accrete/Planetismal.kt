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
data class Planetismal internal constructor(var orbitalAxis: Double,
                                            var eccentricity: Double,
                                            var massSolar: Double = PROTOPLANET_MASS,
                                            var isGasGiant: Boolean = false,
                                            var next: Planetismal? = null) {

    val massEarth: Double
        get() = massSolar * Astro.SOLAR_MASS_EARTH_MASS

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
        return DoleParams.InnerEffectLimit(orbitalAxis, eccentricity, ReducedMargin())
    }

    internal fun OuterEffectLimit(): Double {
        return DoleParams.OuterEffectLimit(orbitalAxis, eccentricity, ReducedMargin())
    }

    internal fun InnerSweptLimit(): Double {
        return DoleParams.InnerSweptLimit(orbitalAxis, eccentricity, ReducedMargin())
    }

    internal fun OuterSweptLimit(): Double {
        return DoleParams.OuterSweptLimit(orbitalAxis, eccentricity, ReducedMargin())
    }

    internal fun CriticalMass(luminosity: Double): Double {
        return DoleParams.CriticalMass(orbitalAxis, eccentricity, luminosity)
    }

    fun Print(out: PrintStream) {
        out.print("$orbitalAxis $eccentricity $massSolar")
        if (massSolar > 2e-15)
            out.print(" (${massSolar * Astro.SOLAR_MASS_EARTH_MASS})")
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
