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
 * Primary.java
 *
 * Created on December 21, 2005, 10:24 AM
 *
 */
package dole

/**
 * Class that details a star
 *
 * @author martin
 * @version $Id: BasicPrimary.java,v 1.10 2006-07-06 06:58:33 martin Exp $
 */
/** Creates a new instance of Primary  */
class BasicPrimary : Primary {
    /**
     * Holds value of property luminosity.
     */
    /**
     * Getter for property luminosity.
     * @return Value of property luminosity.
     */
    /**
     * Setter for property luminosity.
     * @param luminosity New value of property luminosity.
     */
    override var luminosity: Double = 0.toDouble()

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
     * Holds value of property life.
     */
    /**
     * Getter for property life.
     * @return Value of property life.
     */
    /**
     * Setter for property life.
     * @param life New value of property life.
     */
    override var life: Double = 0.toDouble()

    /**
     * Holds value of property age.
     */
    /**
     * Getter for property age.
     * @return Value of property age.
     */
    /**
     * Setter for property age.
     * @param age New value of property age.
     */
    override var age: Double = 0.toDouble()

    /**
     * Holds value of property rEcosphere.
     */
    /**
     * Getter for property rEcosphere.
     *
     * @return Value of property rEcosphere.
     */
    /**
     * Setter for property rEcosphere.
     *
     * @param rEcosphere New value of property rEcosphere.
     */
    override var rEcosphere: Double = 0.toDouble()

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
     * Holds value of property innermostPlanet.
     */
    /**
     * Getter for property innermostPlanet.
     * @return Value of property innermostPlanet.
     */
    /**
     * Setter for property innermostPlanet.
     * @param innermostPlanet New value of property innermostPlanet.
     */
    override var innermostPlanet: Planet? = null

    /**
     * Holds value of property spectralClass.
     */
    /**
     * Getter for property spectralClass.
     * @return Value of property spectralClass.
     */
    /**
     * Setter for property spectralClass.
     * @param spectralClass New value of property spectralClass.
     */
    override var spectralClass: String? = null

    /**
     * Holds value of property spectralSubclass.
     */
    /**
     * Getter for property spectralSubclass.
     * @return Value of property spectralSubclass.
     */
    /**
     * Setter for property spectralSubclass.
     * @param spectralSubclass New value of property spectralSubclass.
     */
    override var spectralSubclass: Int = 0

    /**
     * Holds value of property absoluteMagnitude.
     */
    /**
     * Getter for property absoluteMagnitude.
     * @return Value of property absoluteMagnitude.
     */
    /**
     * Setter for property absoluteMagnitude.
     * @param absoluteMagnitude New value of property absoluteMagnitude.
     */
    override var absoluteMagnitude: Double = 0.toDouble()

    /**
     * Holds value of property luminosityClass.
     */
    /**
     * Getter for property luminosityClass.
     * @return Value of property luminosityClass.
     */
    /**
     * Setter for property luminosityClass.
     * @param luminosityClass New value of property luminosityClass.
     */
    override var luminosityClass: String? = null

    /**
     * Holds value of property r_ecosphereInner.
     */
    /**
     * Getter for property rEcosphereInner.
     * @return Value of property rEcosphereInner.
     */
    /**
     * Setter for property rEcosphereInner.
     *
     * @param rEcosphereInner The inner ecosphere radius
     */
    override var rEcosphereInner: Double = 0.toDouble()

    /**
     * Holds value of property r_ecosphereOuter.
     */
    /**
     * Getter for property rEcosphereOuter.
     * @return Value of property rEcosphereOuter.
     */
    /**
     * Setter for property r_ecosphere_outer.
     *
     * @param rEcosphereOuter The outer ecosphere radius
     */
    override var rEcosphereOuter: Double = 0.toDouble()

    /**
     * Holds value of property planets.
     */
    /**
     * Getter for property planets.
     * @return Value of property planets.
     */
    /**
     * Setter for property planets.
     * @param planets New value of property planets.
     */
    override var planets: MutableList<Planet> = ArrayList()

    /**
     * Holds value of property rightAscension.
     */
    /**
     * Getter for property rightAscension.
     * @return Value of property rightAscension.
     */
    /**
     * Setter for property rightAscension.
     * @param rightAscension New value of property rightAscension.
     */
    override var rightAscension: Double = 0.toDouble()

    /**
     * Holds value of property declination.
     */
    /**
     * Getter for property declination.
     * @return Value of property declination.
     */
    /**
     * Setter for property declination.
     * @param declination New value of property declination.
     */
    override var declination: Double = 0.toDouble()

    /**
     * Holds value of property distance.
     */
    /**
     * Getter for property distance.
     * @return Value of property distance.
     */
    /**
     * Setter for property distance.
     * @param distance New value of property distance.
     */
    override var distance: Double = 0.toDouble()

    /**
     * Holds value of property hipparcusNumber.
     */
    /**
     * Getter for property hipparcusNumber.
     * @return Value of property hipparcusNumber.
     */
    /**
     * Setter for property hipparcusNumber.
     * @param hipparcusNumber New value of property hipparcusNumber.
     */
    override var hipparcusNumber: Int = 0
}