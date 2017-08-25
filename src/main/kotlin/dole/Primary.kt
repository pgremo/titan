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
data class Primary(
        var luminosity: Double = 0.toDouble(),
        var mass: Double = 0.toDouble(),
        var life: Double = 0.toDouble(),
        var age: Double = 0.toDouble(),
        var ecosphere: Double = 0.toDouble(),
        var name: String? = null,
        var spectralClass: String? = null,
        var spectralSubclass: Int = 0,
        var absoluteMagnitude: Double = 0.toDouble(),
        var luminosityClass: String? = null,
        var ecosphereInner: Double = 0.toDouble(),
        var ecosphereOuter: Double = 0.toDouble(),
        var planets: SortedList<Planet, Double> = SortedList(ArrayList(), Planet::a),
        var rightAscension: Double = 0.toDouble(),
        var declination: Double = 0.toDouble(),
        var distance: Double = 0.toDouble(),
        var hipparcusNumber: Int = 0
)
