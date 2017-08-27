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
data class ChemTable
/**
 * Creates a new instance of ChemTable
 *
 * @param number The atomic number of the element
 * @param symbol The chemical symbol of the element
 * @param htmlSymbol An HTML symbol for the element
 * @param name The name of the element
 * @param weight The atomic weight of the element
 * @param meltingPoint The melting point of the element (degrees Kelvin)
 * @param boilingPoint The boiling point of the element (degrees Kelvin)
 * @param density The density of the element
 * @param abunde Element abundance data
 * @param abunds Element abundance data
 * @param reactivity The reactivity of the element
 * @param maxIpp The max allowed inspired partial pressure for this element
 * for a habitable planet
 */(
        /**
         * Setter for property number.
         * @param number New value of property number.
         */
        var number: Int,
        /**
         * Setter for property symbol.
         * @param symbol New value of property symbol.
         */
        var symbol: String?,
        /**
         * Setter for property htmlSymbol.
         * @param htmlSymbol New value of property htmlSymbol.
         */
        var htmlSymbol: String?,
        /**
         * Setter for property name.
         * @param name New value of property name.
         */
        var name: String?,
        /**
         * Setter for property weight.
         * @param weight New value of property weight.
         */
        var weight: Double,
        /**
         * Setter for property meltingPoint.
         * @param meltingPoint New value of property meltingPoint.
         */
        var meltingPoint: Double,
        /**
         * Setter for property boilingPoint.
         * @param boilingPoint New value of property boilingPoint.
         */
        var boilingPoint: Double,
        /**
         * Setter for property density.
         * @param density New value of property density.
         */
        var density: Double,
        /**
         * Setter for property abunde.
         * @param abunde New value of property abunde.
         */
        var abunde: Double,
        /**
         * Setter for property abunds.
         * @param abunds New value of property abunds.
         */
        var abunds: Double,
        /**
         * Setter for property reactivity.
         * @param reactivity New value of property reactivity.
         */
        var reactivity: Double,
        /**
         * Setter for property maxIpp.
         * @param maxIpp New value of property maxIpp.
         */
        var maxIpp: Double) {
}
