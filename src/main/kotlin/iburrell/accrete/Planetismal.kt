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

    internal fun PerihelionDistance() = DoleParams.PerihelionDistance(orbitalAxis, eccentricity)

    internal fun AphelionDistance() = DoleParams.AphelionDistance(orbitalAxis, eccentricity)

    internal fun ReducedMass() = DoleParams.ReducedMass(massSolar)

    internal fun ReducedMargin() = DoleParams.ReducedMargin(massSolar)

    internal fun InnerEffectLimit() = DoleParams.InnerEffectLimit(orbitalAxis, eccentricity, ReducedMargin())

    internal fun OuterEffectLimit() = DoleParams.OuterEffectLimit(orbitalAxis, eccentricity, ReducedMargin())

    internal fun InnerSweptLimit() = DoleParams.InnerSweptLimit(orbitalAxis, eccentricity, ReducedMargin())

    internal fun OuterSweptLimit() = DoleParams.OuterSweptLimit(orbitalAxis, eccentricity, ReducedMargin())

    internal fun CriticalMass(luminosity: Double) = DoleParams.CriticalMass(orbitalAxis, eccentricity, luminosity)

    companion object {

        internal val PROTOPLANET_MASS = 1.0E-15 // units of solar masses

    }
}
