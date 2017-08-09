// Author: Ian Burrell  <iburrell@leland.stanford.edu>
// Created: 1997/02/09
// Modified:

// Copyright 1997 Ian Burrell

package iburrell.accrete

/**

 * This class stores the data needed for a planetismal, which is
 * basically mass and the orbit parameters.  It also includes a
 * pointer to the next in the list.  Also, has some functions that
 * calls the main calculators in the DoleParams class with the proper
 * values for the nucleus.

 * The Accrete class manipulates the internals of this class directly.
 * Outside clients use the accessors.

 */
data class Planetismal(
        var orbitalAxis: Double,
        var eccentricity: Double,
        var massSolar: Double = PROTOPLANET_MASS,
        var isGasGiant: Boolean = false
) {

    val massEarth: Double
        get() = massSolar * Astro.SOLAR_MASS_EARTH_MASS

    internal val PerihelionDistance: Double
        get() = DoleParams.PerihelionDistance(orbitalAxis, eccentricity)

    internal val AphelionDistance: Double
        get() = DoleParams.AphelionDistance(orbitalAxis, eccentricity)

    internal val ReducedMass: Double
        get() = DoleParams.ReducedMass(massSolar)

    internal val reducedMargin: Double
        get() = DoleParams.ReducedMargin(massSolar)

    internal val effectLimit: ClosedRange<Double>
        get() = innerEffectLimit.rangeTo(outerEffectLimit)

    internal val innerEffectLimit: Double
        get() = DoleParams.InnerEffectLimit(orbitalAxis, eccentricity, reducedMargin)

    internal val outerEffectLimit: Double
        get() = DoleParams.OuterEffectLimit(orbitalAxis, eccentricity, reducedMargin)

    internal val sweptLimit: ClosedRange<Double>
        get() = innerSweptLimit.rangeTo(outerSweptLimit)

    internal val innerSweptLimit: Double
        get() = DoleParams.InnerSweptLimit(orbitalAxis, eccentricity, reducedMargin)

    internal val outerSweptLimit: Double
        get() = DoleParams.OuterSweptLimit(orbitalAxis, eccentricity, reducedMargin)

    internal fun criticalMass(luminosity: Double) = DoleParams.CriticalMass(orbitalAxis, eccentricity, luminosity)

    companion object {

        internal val PROTOPLANET_MASS = 1.0E-15 // units of solar masses

    }
}
