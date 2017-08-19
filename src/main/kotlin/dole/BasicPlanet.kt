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
 * Planet.java
 *
 * Created on December 21, 2005, 10:26 AM
 */
package dole

import java.util.Objects

/**
 * Class representing a planet
 *
 *
 * @author martin
 * @version $Id: BasicPlanet.java,v 1.3 2006-07-06 06:58:33 martin Exp $
 */
open class BasicPlanet
/**
 * Creates a new instance of Planet
 */
internal constructor() : Planet {
    /**
     * Holds value of property number.
     */
    /**
     * Getter for property number.
     * @return Value of property number.
     */
    /**
     * Setter for property number.
     * @param number New value of property number.
     */
    override var number: Int = 0

    /**
     * Holds value of property a.
     */
    /**
     * Getter for property a.
     * @return Value of property a.
     */
    /**
     * Setter for property a.
     * @param a New value of property a.
     */
    override var a: Double = 0.toDouble()

    /**
     * Holds value of property e.
     */
    /**
     * Getter for property e.
     * @return Value of property e.
     */
    /**
     * Setter for property e.
     * @param e New value of property e.
     */
    override var e: Double = 0.toDouble()

    /**
     * Holds value of property axialTilt.
     */
    /**
     * Getter for property axialTilt.
     * @return Value of property axialTilt.
     */
    /**
     * Setter for property axialTilt.
     * @param axialTilt New value of property axialTilt.
     */
    override var axialTilt: Double = 0.toDouble()

    /**
     * Holds value of property mass.
     */
    /**
     * Getter for property mass.
     * @return Value of property mass.
     */
    /**
     * Setter for property mass.
     * @param mass New value of property mass.
     */
    override var mass: Double = 0.toDouble()

    /**
     * Holds value of property gasGiant.
     */
    /**
     * Getter for property gasGiant.
     * @return Value of property gasGiant.
     */
    /**
     * Setter for property gasGiant.
     * @param gasGiant New value of property gasGiant.
     */
    override var isGasGiant: Boolean = false

    /**
     * Holds value of property dustMass.
     */
    /**
     * Getter for property dustMass.
     * @return Value of property dustMass.
     */
    /**
     * Setter for property dustMass.
     * @param dustMass New value of property dustMass.
     */
    override var dustMass: Double = 0.toDouble()

    /**
     * Holds value of property gasMass.
     */
    /**
     * Getter for property gasMass.
     * @return Value of property gasMass.
     */
    /**
     * Setter for property gasMass.
     * @param gasMass New value of property gasMass.
     */
    override var gasMass: Double = 0.toDouble()

    /**
     * Holds value of property coreRadius.
     */
    /**
     * Getter for property coreRadius.
     * @return Value of property coreRadius.
     */
    /**
     * Setter for property coreRadius.
     * @param coreRadius New value of property coreRadius.
     */
    override var coreRadius: Double = 0.toDouble()

    /**
     * Holds value of property radius.
     */
    /**
     * Getter for property radius.
     * @return Value of property radius.
     */
    /**
     * Setter for property radius.
     * @param radius New value of property radius.
     */
    override var radius: Double = 0.toDouble()

    /**
     * Holds value of property orbitZone.
     */
    /**
     * Getter for property orbitZone.
     * @return Value of property orbitZone.
     */
    /**
     * Setter for property orbitZone.
     * @param orbitZone New value of property orbitZone.
     */
    override var orbitZone: Int = 0

    /**
     * Holds value of property density.
     */
    /**
     * Getter for property density.
     * @return Value of property density.
     */
    /**
     * Setter for property density.
     * @param density New value of property density.
     */
    override var density: Double = 0.toDouble()

    /**
     * Holds value of property orbitalPeriod.
     */
    /**
     * Getter for property orbPeriod.
     * @return Value of property orbPeriod.
     */
    /**
     * Setter for property orbPeriod.
     *
     * @param orbitalPeriod New value of property orbPeriod.
     */
    override var orbitalPeriod: Double = 0.toDouble()

    /**
     * Holds value of property day.
     */
    /**
     * Getter for property day.
     * @return Value of property day.
     */
    /**
     * Setter for property day.
     * @param day New value of property day.
     */
    override var day: Double = 0.toDouble()

    /**
     * Holds value of property resonantPeriod.
     */
    /**
     * Getter for property resonantPeriod.
     * @return Value of property resonantPeriod.
     */
    /**
     * Setter for property resonantPeriod.
     * @param resonantPeriod New value of property resonantPeriod.
     */
    override var isResonantPeriod: Boolean = false

    /**
     * Holds value of property escapeVelocity.
     */
    /**
     * Getter for property escapeVelocity.
     * @return Value of property escapeVelocity.
     */
    /**
     * Setter for property escapeVelocity.
     * @param escapeVelocity New value of property escapeVelocity.
     */
    override var escapeVelocity: Double = 0.toDouble()

    /**
     * Holds value of property surfaceAcceleration.
     */
    /**
     * Getter for property surfaceAcceleration.
     * @return Value of property surfaceAcceleration.
     */
    /**
     * Setter for property surfaceAcceleration.
     * @param surfaceAcceleration New value of property surfaceAcceleration.
     */
    override var surfaceAcceleration: Double = 0.toDouble()

    /**
     * Holds value of property surfaceGravity.
     */
    /**
     * Getter for property surfaceGravity.
     * @return Value of property surfaceGravity.
     */
    /**
     * Setter for property surfaceGravity.
     * @param surfaceGravity New value of property surfaceGravity.
     */
    override var surfaceGravity: Double = 0.toDouble()

    /**
     * Holds value of property rmsVelocity.
     */
    /**
     * Getter for property rmsVelocity.
     * @return Value of property rmsVelocity.
     */
    /**
     * Setter for property rmsVelocity.
     * @param rmsVelocity New value of property rmsVelocity.
     */
    override var rmsVelocity: Double = 0.toDouble()

    /**
     * Holds value of property molecularWeight.
     */
    /**
     * Getter for property molecularWeight.
     * @return Value of property molecularWeight.
     */
    /**
     * Setter for property molecularWeight.
     * @param molecularWeight New value of property molecularWeight.
     */
    override var molecularWeight: Double = 0.toDouble()

    /**
     * Holds value of property volatileGasInventory.
     */
    /**
     * Getter for property volatileGasInventory.
     * @return Value of property volatileGasInventory.
     */
    /**
     * Setter for property volatileGasInventory.
     * @param volatileGasInventory New value of property volatileGasInventory.
     */
    override var volatileGasInventory: Double = 0.toDouble()

    /**
     * Holds value of property surfacePressure.
     */
    /**
     * Getter for property surfacePressure.
     * @return Value of property surfacePressure.
     */
    /**
     * Setter for property surfacePressure.
     * @param surfacePressure New value of property surfacePressure.
     */
    override var surfacePressure: Double = 0.toDouble()

    /**
     * Holds value of property greenhouseEffect.
     */
    /**
     * Getter for property greenhouseEffect.
     * @return Value of property greenhouseEffect.
     */
    /**
     * Setter for property greenhouseEffect.
     * @param greenhouseEffect New value of property greenouseEffect.
     */
    override var isGreenhouseEffect: Boolean = false

    /**
     * Holds value of property boilingPoint.
     */
    /**
     * Getter for property boilingPoint.
     * @return Value of property boilingPoint.
     */
    /**
     * Setter for property boilingPoint.
     * @param boilingPoint New value of property boilingPoint.
     */
    override var boilingPoint: Double = 0.toDouble()

    /**
     * Holds value of property albedo.
     */
    /**
     * Getter for property albedo.
     * @return Value of property albedo.
     */
    /**
     * Setter for property albedo.
     * @param albedo New value of property albedo.
     */
    override var albedo: Double = 0.toDouble()

    /**
     * Holds value of property exosphericTemperature.
     */
    /**
     * Getter for property exosphericTemperature.
     * @return Value of property exosphericTemperature.
     */
    /**
     * Setter for property exosphericTemperature.
     * @param exosphericTemperature New value of property exosphericTemperature.
     */
    override var exosphericTemperature: Double = 0.toDouble()

    /**
     * Holds value of property surfaceTemperature.
     */
    /**
     * Getter for property surfaceTemperature.
     * @return Value of property surfaceTemperature.
     */
    /**
     * Setter for property surfaceTemperature.
     * @param surfaceTemperature New value of property surfaceTemperature.
     */
    override var surfaceTemperature: Double = 0.toDouble()

    /**
     * Holds value of property greenhouseRise.
     */
    /**
     * Getter for property greenhouseRise.
     * @return Value of property greenhouseRise.
     */
    /**
     * Setter for property greenhouseRise.
     * @param greenhouseRise New value of property greenhouseRise.
     */
    override var greenhouseRise: Double = 0.toDouble()

    /**
     * Holds value of property highTemperature.
     */
    /**
     * Getter for property highTemperature.
     * @return Value of property highTemperature.
     */
    /**
     * Setter for property highTemperature.
     * @param highTemperature New value of property highTemperature.
     */
    override var highTemperature: Double = 0.toDouble()

    /**
     * Holds value of property lowTemperature.
     */
    /**
     * Getter for property lowTemperature.
     * @return Value of property lowTemperature.
     */
    /**
     * Setter for property lowTemperature.
     * @param lowTemperature New value of property lowTemperature.
     */
    override var lowTemperature: Double = 0.toDouble()

    /**
     * Holds value of property maxTemperature.
     */
    /**
     * Getter for property maxTemperature.
     * @return Value of property maxTemperature.
     */
    /**
     * Setter for property maxTemperature.
     * @param maxTemperature New value of property maxTemperature.
     */
    override var maxTemperature: Double = 0.toDouble()

    /**
     * Holds value of property minTemperature.
     */
    /**
     * Getter for property minTemperature.
     * @return Value of property minTemperature.
     */
    /**
     * Setter for property minTemperature.
     * @param minTemperature New value of property minTemperature.
     */
    override var minTemperature: Double = 0.toDouble()

    /**
     * Holds value of property hydrosphere.
     */
    /**
     * Getter for property hydrosphere.
     * @return Value of property hydrosphere.
     */
    /**
     * Setter for property hydrosphere.
     * @param hydrosphere New value of property hydrosphere.
     */
    override var hydrosphere: Double = 0.toDouble()

    /**
     * Holds value of property cloudCover.
     */
    /**
     * Getter for property cloudCover.
     * @return Value of property cloudCover.
     */
    /**
     * Setter for property cloudCover.
     * @param cloudCover New value of property cloudCover.
     */
    override var cloudCover: Double = 0.toDouble()

    /**
     * Holds value of property iceCover.
     */
    /**
     * Getter for property iceCover.
     * @return Value of property iceCover.
     */
    /**
     * Setter for property iceCover.
     * @param iceCover New value of property iceCover.
     */
    override var iceCover: Double = 0.toDouble()

    /**
     * Holds value of property primary.
     */
    /**
     * Getter for property primary.
     * @return Value of property primary.
     */
    /**
     * Setter for property primary.
     * @param primary New value of property primary.
     */
    override var primary: Primary? = null

    /**
     * Holds value of property gases.
     */
    /**
     * Getter for property gases.
     * @return Value of property gases.
     */
    /**
     * Setter for property gases.
     * @param gases New value of property gases.
     */
    override var gases: List<Gas> = emptyList()

    /**
     * Holds value of property moons.
     */
    /**
     * Getter for property moons.
     * @return Value of property moons.
     */
    /**
     * Setter for property moons.
     * @param moons New value of property moons.
     */
    override var moons: List<Planet> = emptyList()

    /**
     * Holds value of property nextPlanet.
     */
    /**
     * Getter for property nextPlanet.
     * @return Value of property nextPlanet.
     */
    /**
     * Setter for property nextPlanet.
     * @param nextPlanet New value of property nextPlanet.
     */
    override var nextPlanet: Planet? = null

    /**
     * Holds value of property name.
     */
    /**
     * Getter for property name.
     * @return Value of property name.
     */
    /**
     * Setter for property name.
     * @param name New value of property name.
     */
    override var name: String? = null

    /**
     * Holds value of property breathability.
     */
    /**
     * Getter for property breathability.
     * @return Value of property breathability.
     */
    /**
     * Setter for property breathability.
     * @param breathability New value of property breathability.
     */
    override var breathability: Breathability? = null

    /**
     * Holds value of property firstMoon.
     */
    /**
     * Getter for property firstMoon.
     * @return Value of property firstMoon.
     */
    /**
     * Setter for property firstMoon.
     * @param firstMoon New value of property firstMoon.
     */
    override var firstMoon: Planet? = null

    override fun toString(): String {
        return "${javaClass.simpleName}.apply{a=$a; e=$e; mass=$mass; gasGiant=$isGasGiant}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as BasicPlanet?
        return java.lang.Double.compare(that!!.a, a) == 0 &&
                java.lang.Double.compare(that.e, e) == 0 &&
                java.lang.Double.compare(that.mass, mass) == 0 &&
                isGasGiant == that.isGasGiant
    }

    override fun hashCode(): Int {
        return Objects.hash(a, e, mass, isGasGiant)
    }
}
