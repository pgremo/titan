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
        get() = DoleParams.InnerEffectLimit(orbitalAxis, eccentricity, reducedMargin)..DoleParams.OuterEffectLimit(orbitalAxis, eccentricity, reducedMargin)

    internal val sweptLimit: ClosedRange<Double>
        get() = innerSweptLimit..outerSweptLimit

    internal val innerSweptLimit: Double
        get() = DoleParams.InnerSweptLimit(orbitalAxis, eccentricity, reducedMargin)

    internal val outerSweptLimit: Double
        get() = DoleParams.OuterSweptLimit(orbitalAxis, eccentricity, reducedMargin)

    internal fun criticalMass(luminosity: Double) = DoleParams.CriticalMass(orbitalAxis, eccentricity, luminosity)

    /**
     * Coalesces two planet together.  The resulting planet is saved
     * back into the first one (which is assumed to be the one present
     * in the planet list).
     */
    internal fun merge(b: Planetismal): Planetismal {
        val new_mass = massSolar + b.massSolar
        val new_axis = new_mass / (massSolar / orbitalAxis + b.massSolar / b.orbitalAxis)
        val term1 = massSolar * Math.sqrt(orbitalAxis * (1.0 - eccentricity * eccentricity))
        val term2 = b.massSolar * Math.sqrt(b.orbitalAxis * (1.0 - b.eccentricity * b.eccentricity))
        val term3 = (term1 + term2) / (new_mass * Math.sqrt(new_axis))
        val term4 = 1.0 - term3 * term3
        val new_eccn = Math.sqrt(Math.abs(term4))
        massSolar = new_mass
        orbitalAxis = new_axis
        eccentricity = new_eccn
        isGasGiant = isGasGiant || b.isGasGiant
        return this
    }

    companion object {

        internal val PROTOPLANET_MASS = 1.0E-15 // units of solar masses

    }
}
