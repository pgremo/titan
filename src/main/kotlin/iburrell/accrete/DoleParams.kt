// Author: Ian Burrell  <iburrell@leland.stanford.edu>
// Created: 1997/02/09
// Modified:

// Copyright 1997 Ian Burrell

package iburrell.accrete

/**

 * Static class that contains many for formulas and constants.  It
 * should be later modified to allow different parameters to be used to
 * vary the star systems created.  Unless specified, all masses are in
 * solar masses and all distances in AUs.
 */
internal object DoleParams {

    val B = 1.2E-5     // Used in critical mass calc

    /**
     * Determines the critical mass limit, where the planet begins to
     * accrete gas as well as dust.
     */
    fun CriticalMass(radius: Double, eccentricity: Double, luminosity: Double) = B * Math.pow(PerihelionDistance(radius, eccentricity) * Math.sqrt(luminosity), -0.75)

    fun PerihelionDistance(radius: Double, eccentricity: Double) = radius * (1.0 - eccentricity)

    fun AphelionDistance(radius: Double, eccentricity: Double) = radius * (1.0 + eccentricity)

    fun ReducedMass(mass: Double) = mass / (1.0 + mass)

    fun ReducedMargin(mass: Double) = Math.pow(ReducedMass(mass), 1.0 / 4.0)

    val CLOUD_ECCENTRICITY = 0.25

    fun LowBound(radius: Double, margin: Double) = LowBound(radius - margin)

    fun LowBound(inner: Double) = inner / (1.0 + CLOUD_ECCENTRICITY)

    fun HighBound(radius: Double, margin: Double) = HighBound(radius + margin)

    fun HighBound(outer: Double) = outer / (1.0 - CLOUD_ECCENTRICITY)

    fun InnerEffectLimit(a: Double, e: Double, m: Double) = PerihelionDistance(a, e) * (1.0 - m)

    fun OuterEffectLimit(a: Double, e: Double, m: Double) = AphelionDistance(a, e) * (1.0 + m)

    fun InnerSweptLimit(a: Double, e: Double, m: Double) = LowBound(PerihelionDistance(a, e) * (1.0 - m))

    fun OuterSweptLimit(a: Double, e: Double, m: Double) = HighBound(AphelionDistance(a, e) * (1.0 + m))

    val K = 50.0       // gas/dust ratio
    val DUST_DENSITY_COEFF = 1.5E-3 // A in Dole's paper
    val ALPHA = 5.0    // Used in density calcs
    val N = 3.0        // Used in density calcs

    /**
     * Calculates the density of dust at the given radius from the
     * star.
     */
    fun DustDensity(stellar_mass: Double, orbital_radius: Double) = DUST_DENSITY_COEFF * Math.sqrt(stellar_mass) * Math.exp(-ALPHA * Math.pow(orbital_radius, 1.0 / N))

    /**
     * Calculates the total density of dust and gas.  Used for planets
     * larger than the critical mass which accrete gas as well as dust.
     */
    fun MassDensity(stellar_mass: Double, orbital_radius: Double, critical_mass: Double, mass: Double) = MassDensity(DustDensity(stellar_mass, orbital_radius), critical_mass, mass)

    fun MassDensity(dust_density: Double, critical_mass: Double, mass: Double) = K * dust_density / (1.0 + Math.sqrt(critical_mass / mass) * (K - 1.0))

    val ECCENTRICITY_COEFF = 0.077

    fun ScaleCubeRootMass(scale: Double, mass: Double) = scale * Math.pow(mass, 1.0 / 3.0)

    fun InnerDustLimit(stellar_mass: Double) = 0.0

    fun OuterDustLimit(stellar_mass: Double) = ScaleCubeRootMass(200.0, stellar_mass)

    fun InnermostPlanet(stellar_mass: Double) = ScaleCubeRootMass(0.3, stellar_mass)

    fun OutermostPlanet(stellar_mass: Double) = ScaleCubeRootMass(50.0, stellar_mass)

}


