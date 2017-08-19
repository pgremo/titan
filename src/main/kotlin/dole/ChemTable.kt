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
 * ChemTable.java
 *
 * Created on December 21, 2005, 10:44 AM
 *
 */
package dole


/**
 * Holds details of chemical elements
 *
 * @author martin
 * @version $Id: ChemTable.java,v 1.4 2006-07-06 06:58:33 martin Exp $
 */
class ChemTable {
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
    var number: Int = 0

    /**
     * Holds value of property symbol.
     */
    /**
     * Getter for property symbol.
     * @return Value of property symbol.
     */
    /**
     * Setter for property symbol.
     * @param symbol New value of property symbol.
     */
    var symbol: String? = null

    /**
     * Holds value of property htmlSymbol.
     */
    /**
     * Getter for property htmlSymbol.
     * @return Value of property htmlSymbol.
     */
    /**
     * Setter for property htmlSymbol.
     * @param htmlSymbol New value of property htmlSymbol.
     */
    var htmlSymbol: String? = null

    /**
     * Holds value of property weight.
     */
    /**
     * Getter for property weight.
     * @return Value of property weight.
     */
    /**
     * Setter for property weight.
     * @param weight New value of property weight.
     */
    var weight: Double = 0.toDouble()

    /**
     * Holds value of property meltingPoint.
     */
    /**
     * Getter for property meltingPoint.
     * @return Value of property meltingPoint.
     */
    /**
     * Setter for property meltingPoint.
     * @param meltingPoint New value of property meltingPoint.
     */
    var meltingPoint: Double = 0.toDouble()

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
    var boilingPoint: Double = 0.toDouble()

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
    var density: Double = 0.toDouble()

    /**
     * Holds value of property abunde.
     */
    /**
     * Getter for property abunde.
     * @return Value of property abunde.
     */
    /**
     * Setter for property abunde.
     * @param abunde New value of property abunde.
     */
    var abunde: Double = 0.toDouble()

    /**
     * Holds value of property abunds.
     */
    /**
     * Getter for property abunds.
     * @return Value of property abunds.
     */
    /**
     * Setter for property abunds.
     * @param abunds New value of property abunds.
     */
    var abunds: Double = 0.toDouble()

    /**
     * Holds value of property reactivity.
     */
    /**
     * Getter for property reactivity.
     * @return Value of property reactivity.
     */
    /**
     * Setter for property reactivity.
     * @param reactivity New value of property reactivity.
     */
    var reactivity: Double = 0.toDouble()

    /**
     * Holds value of property maxIpp.
     */
    /**
     * Getter for property maxIpp.
     * @return Value of property maxIpp.
     */
    /**
     * Setter for property maxIpp.
     * @param maxIpp New value of property maxIpp.
     */
    var maxIpp: Double = 0.toDouble()

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
    var name: String? = null

    /**
     * Creates a new instance of ChemTable
     */
    constructor() {}

    /**
     * Creates a new instance of ChemTable
     *
     * @param atomicNumber The atomic number of the element
     * @param symbol The chemical symbol of the element
     * @param html An HTML symbol for the element
     * @param name The name of the element
     * @param atomicWeight The atomic weight of the element
     * @param melt The melting point of the element (degrees Kelvin)
     * @param boil The boiling point of the element (degrees Kelvin)
     * @param density The density of the element
     * @param abunde Element abundance data
     * @param abunds Element abundance data
     * @param rea The reactivity of the element
     * @param maxIpp The max allowed inspired partial pressure for this element
     * for a habitable planet
     */
    constructor(
            atomicNumber: Int, symbol: String, html: String, name: String,
            atomicWeight: Double, melt: Double, boil: Double, density: Double,
            abunde: Double, abunds: Double, rea: Double, maxIpp: Double) {
        this.number = atomicNumber
        this.symbol = symbol
        this.htmlSymbol = html
        this.name = name
        this.weight = atomicWeight
        this.meltingPoint = melt
        this.boilingPoint = boil
        this.density = density
        this.abunde = abunde
        this.abunds = abunds
        this.reactivity = rea
        this.maxIpp = maxIpp
    }
}
