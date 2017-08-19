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
 * Created on 23 April 2006, 14:46
 */

package dole

/**
 * An interface to be implemented by objects that hold details of planets.
 *
 * @author  martin
 * @version $Id: Planet.java,v 1.3 2006-07-06 06:58:33 martin Exp $
 */
interface Planet {
    /**
     * Getter for property a.
     *
     * @return Value of property a.
     */
    /**
     * Setter for property a.
     *
     * @param a New value of property a.
     */
    var a: Double

    /**
     * Getter for property albedo.
     *
     * @return Value of property albedo.
     */
    /**
     * Setter for property albedo.
     *
     * @param albedo New value of property albedo.
     */
    var albedo: Double

    /**
     * Getter for property axialTilt.
     *
     * @return Value of property axialTilt.
     */
    /**
     * Setter for property axialTilt.
     *
     * @param axialTilt New value of property axialTilt.
     */
    var axialTilt: Double

    /**
     * Getter for property boilingPoint.
     *
     * @return Value of property boilingPoint.
     */
    /**
     * Setter for property boilingPoint.
     *
     * @param boilingPoint New value of property boilingPoint.
     */
    var boilingPoint: Double

    /**
     * Getter for property breathability.
     *
     * @return Value of property breathability.
     */
    /**
     * Setter for property breathability.
     *
     * @param breathability New value of property breathability.
     */
    var breathability: Breathability?

    /**
     * Getter for property cloudCover.
     *
     * @return Value of property cloudCover.
     */
    /**
     * Setter for property cloudCover.
     *
     * @param cloudCover New value of property cloudCover.
     */
    var cloudCover: Double

    /**
     * Getter for property coreRadius.
     *
     * @return Value of property coreRadius.
     */
    /**
     * Setter for property coreRadius.
     *
     * @param coreRadius New value of property coreRadius.
     */
    var coreRadius: Double

    /**
     * Getter for property day.
     *
     * @return Value of property day.
     */
    /**
     * Setter for property day.
     *
     * @param day New value of property day.
     */
    var day: Double

    /**
     * Getter for property density.
     *
     * @return Value of property density.
     */
    /**
     * Setter for property density.
     *
     * @param density New value of property density.
     */
    var density: Double

    /**
     * Getter for property dustMass.
     *
     * @return Value of property dustMass.
     */
    /**
     * Setter for property dustMass.
     *
     * @param dustMass New value of property dustMass.
     */
    var dustMass: Double

    /**
     * Getter for property e.
     *
     * @return Value of property e.
     */
    /**
     * Setter for property e.
     *
     * @param e New value of property e.
     */
    var e: Double

    /**
     * Getter for property escapeVelocity.
     *
     * @return Value of property escapeVelocity.
     */
    /**
     * Setter for property escapeVelocity.
     *
     * @param escapeVelocity New value of property escapeVelocity.
     */
    var escapeVelocity: Double

    /**
     * Getter for property exosphericTemperature.
     *
     * @return Value of property exosphericTemperature.
     */
    /**
     * Setter for property exosphericTemperature.
     *
     * @param exosphericTemperature New value of property exosphericTemperature.
     */
    var exosphericTemperature: Double

    /**
     * Getter for property firstMoon.
     *
     * @return Value of property firstMoon.
     */
    /**
     * Setter for property firstMoon.
     *
     * @param firstMoon New value of property firstMoon.
     */
    var firstMoon: Planet?

    /**
     * Getter for property gasMass.
     *
     * @return Value of property gasMass.
     */
    /**
     * Setter for property gasMass.
     *
     * @param gasMass New value of property gasMass.
     */
    var gasMass: Double

    /**
     * Getter for property gases.
     *
     * @return Value of property gases.
     */
    /**
     * Setter for property gases.
     *
     * @param gases New value of property gases.
     */
    var gases: List<Gas>

    /**
     * Getter for property greenhouseRise.
     *
     * @return Value of property greenhouseRise.
     */
    /**
     * Setter for property greenhouseRise.
     *
     * @param greenhouseRise New value of property greenhouseRise.
     */
    var greenhouseRise: Double

    /**
     * Getter for property highTemperature.
     *
     * @return Value of property highTemperature.
     */
    /**
     * Setter for property highTemperature.
     *
     * @param highTemperature New value of property highTemperature.
     */
    var highTemperature: Double

    /**
     * Getter for property hydrosphere.
     *
     * @return Value of property hydrosphere.
     */
    /**
     * Setter for property hydrosphere.
     *
     * @param hydrosphere New value of property hydrosphere.
     */
    var hydrosphere: Double

    /**
     * Getter for property iceCover.
     *
     * @return Value of property iceCover.
     */
    /**
     * Setter for property iceCover.
     *
     * @param iceCover New value of property iceCover.
     */
    var iceCover: Double

    /**
     * Getter for property lowTemperature.
     *
     * @return Value of property lowTemperature.
     */
    /**
     * Setter for property lowTemperature.
     *
     * @param lowTemperature New value of property lowTemperature.
     */
    var lowTemperature: Double

    /**
     * Getter for property mass.
     *
     * @return Value of property mass.
     */
    /**
     * Setter for property mass.
     *
     * @param mass New value of property mass.
     */
    var mass: Double

    /**
     * Getter for property maxTemperature.
     *
     * @return Value of property maxTemperature.
     */
    /**
     * Setter for property maxTemperature.
     *
     * @param maxTemperature New value of property maxTemperature.
     */
    var maxTemperature: Double

    /**
     * Getter for property minTemperature.
     *
     * @return Value of property minTemperature.
     */
    /**
     * Setter for property minTemperature.
     *
     * @param minTemperature New value of property minTemperature.
     */
    var minTemperature: Double

    /**
     * Getter for property molecularWeight.
     *
     * @return Value of property molecularWeight.
     */
    /**
     * Setter for property molecularWeight.
     *
     * @param molecularWeight New value of property molecularWeight.
     */
    var molecularWeight: Double

    /**
     * Getter for property moons.
     *
     * @return Value of property moons.
     */
    /**
     * Setter for property moons.
     *
     * @param moons New value of property moons.
     */
    var moons: List<Planet>

    /**
     * Getter for property name.
     *
     * @return Value of property name.
     */
    /**
     * Setter for property name.
     *
     * @param name New value of property name.
     */
    var name: String?

    /**
     * Getter for property nextPlanet.
     *
     * @return Value of property nextPlanet.
     */
    /**
     * Setter for property nextPlanet.
     *
     * @param nextPlanet New value of property nextPlanet.
     */
    var nextPlanet: Planet?

    /**
     * Getter for property number.
     *
     * @return Value of property number.
     */
    /**
     * Setter for property number.
     *
     * @param number New value of property number.
     */
    var number: Int

    /**
     * Getter for property orbitZone.
     *
     * @return Value of property orbitZone.
     */
    /**
     * Setter for property orbitZone.
     *
     * @param orbitZone New value of property orbitZone.
     */
    var orbitZone: Int

    /**
     * Getter for property orbPeriod.
     *
     * @return Value of property orbPeriod.
     */
    /**
     * Setter for property orbPeriod.
     *
     *
     * @param orbitalPeriod New value of property orbPeriod.
     */
    var orbitalPeriod: Double

    /**
     * Getter for property primary.
     *
     * @return Value of property primary.
     */
    /**
     * Setter for property primary.
     *
     * @param primary New value of property primary.
     */
    var primary: Primary?

    /**
     * Getter for property radius.
     *
     * @return Value of property radius.
     */
    /**
     * Setter for property radius.
     *
     * @param radius New value of property radius.
     */
    var radius: Double

    /**
     * Getter for property rmsVelocity.
     *
     * @return Value of property rmsVelocity.
     */
    /**
     * Setter for property rmsVelocity.
     *
     * @param rmsVelocity New value of property rmsVelocity.
     */
    var rmsVelocity: Double

    /**
     * Getter for property surfaceAcceleration.
     *
     * @return Value of property surfaceAcceleration.
     */
    /**
     * Setter for property surfaceAcceleration.
     *
     * @param surfaceAcceleration New value of property surfaceAcceleration.
     */
    var surfaceAcceleration: Double

    /**
     * Getter for property surfaceGravity.
     *
     * @return Value of property surfaceGravity.
     */
    /**
     * Setter for property surfaceGravity.
     *
     * @param surfaceGravity New value of property surfaceGravity.
     */
    var surfaceGravity: Double

    /**
     * Getter for property surfacePressure.
     *
     * @return Value of property surfacePressure.
     */
    /**
     * Setter for property surfacePressure.
     *
     * @param surfacePressure New value of property surfacePressure.
     */
    var surfacePressure: Double

    /**
     * Getter for property surfaceTemperature.
     *
     * @return Value of property surfaceTemperature.
     */
    /**
     * Setter for property surfaceTemperature.
     *
     * @param surfaceTemperature New value of property surfaceTemperature.
     */
    var surfaceTemperature: Double

    /**
     * Getter for property volatileGasInventory.
     *
     * @return Value of property volatileGasInventory.
     */
    /**
     * Setter for property volatileGasInventory.
     *
     * @param volatileGasInventory New value of property volatileGasInventory.
     */
    var volatileGasInventory: Double

    /**
     * Getter for property gasGiant.
     *
     * @return Value of property gasGiant.
     */
    /**
     * Setter for property gasGiant.
     *
     * @param gasGiant New value of property gasGiant.
     */
    var isGasGiant: Boolean

    /**
     * Getter for property greenouseEffect.
     *
     * @return Value of property greenouseEffect.
     */
    /**
     * Setter for property greenhouseEffect.
     *
     * @param greenhouseEffect New value of property greenhouseEffect.
     */
    var isGreenhouseEffect: Boolean

    /**
     * Getter for property resonantPeriod.
     *
     * @return Value of property resonantPeriod.
     */
    /**
     * Setter for property resonantPeriod.
     *
     * @param resonantPeriod New value of property resonantPeriod.
     */
    var isResonantPeriod: Boolean
}
