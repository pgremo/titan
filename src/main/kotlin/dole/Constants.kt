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
 * Constants.java
 *
 * Created on December 21, 2005, 1:19 PM
 */
package dole


/**
 * Various constant values needed by the calculations
 *
 * @author martin
 * @version $Id: Constants.java,v 1.8 2006-07-06 06:58:33 martin Exp $
 */
/** Creates a new instance of Constants  */
class Constants {
    companion object {
        /** Number of radians in 360 degrees  */
        val RADIANS_PER_ROTATION = 2.0 * Math.PI

        /** Eccentricity used. Dole's was 0.077  */
        val ECCENTRICITY_COEFF = 0.077

        /** Protoplanet mas in units of solar masses         */
        val PROTOPLANET_MASS = 1.0E-15

        /** Angular velocity change in units of radians/sec/year */
        val CHANGE_IN_EARTH_ANG_VEL = -1.3E-15

        /** Mass of Sun in grams  */
        val SOLAR_MASS_IN_GRAMS = 1.989E33

        /** Mass of Sun in units of kilograms  */
        val SOLAR_MASS_IN_KILOGRAMS = 1.989E30

        /** Mass of Earth in grams  */
        val EARTH_MASS_IN_GRAMS = 5.977E27

        /** Earth radius in centimeters  */
        val EARTH_RADIUS = 6.378E8

        /** Earth density in grams per cubic centimetre  */
        val EARTH_DENSITY = 5.52

        /** Earth radius in kilometres  */
        val KM_EARTH_RADIUS = 6378.0

        /** Earth acceleration in units of centimetres per second squared  */
        val EARTH_ACCELERATION = 980.7

        /** The axial tilt of the Earth in degrees  */
        val EARTH_AXIAL_TILT = 23.4

        /** Exospheric temperature of Earth in degrees Kelvin  */
        val EARTH_EXOSPHERE_TEMP = 1273.0

        /** The mass of the Sun as a multiple of Earth masses  */
        val SUN_MASS_IN_EARTH_MASSES = 332775.64

        /** The size limit for asteroids in terms of Earth masses  */
        val ASTEROID_MASS_LIMIT = 0.001

        /** Earth temperature in units of degrees Kelvin - was 255;         */
        val EARTH_EFFECTIVE_TEMP = 250.0

        /** Factor for cloud coverage in units of square kilometres per kilogram  */
        val CLOUD_COVERAGE_FACTOR = 1.839E-8

        /** Mass of water for Earth for an area. In units of grams per square kilometer  */
        val EARTH_WATER_MASS_PER_AREA = 3.83E15

        /** Surface atomspheric pressure of Earth in millibars  */
        val EARTH_SURF_PRES_IN_MILLIBARS = 1013.25

        /** Surface atomspheric pressure of Earth in millimetres of mercury (Dole p. 15)  */
        val EARTH_SURF_PRES_IN_MMHG = 760.0

        /** Surface atomspheric pressure of Earth in pounds per square inch  */
        val EARTH_SURF_PRES_IN_PSI = 14.696

        // RequireThis OFF: EARTH_SURF_PRES_IN_MILLIBARS
        // RequireThis OFF: EARTH_SURF_PRES_IN_MMHG
        // RequireThis OFF: MMHG_TO_MILLIBARS

        /** Conversion factor from millimetres of mercury to millibars  */
        val MMHG_TO_MILLIBARS = EARTH_SURF_PRES_IN_MILLIBARS / EARTH_SURF_PRES_IN_MMHG

        /** Conversion factor from pounds per square inch to millibars  */
        val PSI_TO_MILLIBARS = EARTH_SURF_PRES_IN_MILLIBARS / EARTH_SURF_PRES_IN_PSI

        /** Assumed pressure of water (Dole p. 15)  */
        val H20_ASSUMED_PRESSURE = 47.0 * MMHG_TO_MILLIBARS

        /** Min partial pressure of oxygen for a breathable atmosphere (Dole, p. 15)  */
        val MIN_O2_IPP = 72.0 * MMHG_TO_MILLIBARS

        /** Max partial pressure of oxygen for a breathable atmosphere (Dole, p. 15)  */
        val MAX_O2_IPP = 400.0 * MMHG_TO_MILLIBARS

        /** Max partial pressure of helium for a breathable atmosphere (Dole, p. 16)  */
        val MAX_HE_IPP = 61000.0 * MMHG_TO_MILLIBARS

        /** Max partial pressure of neon for a breathable atmosphere (Dole, p. 16)  */
        val MAX_NE_IPP = 3900.0 * MMHG_TO_MILLIBARS

        /** Max partial pressure of nitrogen for a breathable atmosphere (Dole, p. 16)  */
        val MAX_N2_IPP = 2330.0 * MMHG_TO_MILLIBARS

        /** Max partial pressure of argon for a breathable atmosphere (Dole, p. 16)  */
        val MAX_AR_IPP = 1220.0 * MMHG_TO_MILLIBARS

        /** Max partial pressure of krytpon for a breathable atmosphere (Dole, p. 16)  */
        val MAX_KR_IPP = 350.0 * MMHG_TO_MILLIBARS

        /** Max partial pressure of xenon for a breathable atmosphere (Dole, p. 16)  */
        val MAX_XE_IPP = 160.0 * MMHG_TO_MILLIBARS

        /** Max partial pressure of carbon dioxode for a breathable atmosphere
         * (Dole, p. 16)  */
        val MAX_CO2_IPP = 7.0 * MMHG_TO_MILLIBARS

        /** Max pressure for a breathable atmosphere (Dole, p. 16)  */
        val MAX_HABITABLE_PRESSURE = 118 * PSI_TO_MILLIBARS

        // The next gases are listed as poisonous in parts per million by volume at 1 atm:

        // MagicNumber OFF

        /** Conversion factor for parts per million of Earth surface atmospheric pressure  */
        val PPM_PRSSURE = EARTH_SURF_PRES_IN_MILLIBARS / 1000000.0

        // MagicNumber ON

        /** Max parts per milion of fluorine for a breathable atmosphere (Dole, p. 18)  */
        val MAX_F_IPP = 0.1 * PPM_PRSSURE

        /** Max parts per milion of chlorine for a breathable atmosphere (Dole, p. 18)  */
        val MAX_CL_IPP = 1.0 * PPM_PRSSURE

        /** Max parts per milion of ammonia for a breathable atmosphere (Dole, p. 18)  */
        val MAX_NH3_IPP = 100.0 * PPM_PRSSURE

        /** Max parts per milion of ozone for a breathable atmosphere (Dole, p. 18)  */
        val MAX_O3_IPP = 0.1 * PPM_PRSSURE

        /** Max parts per milion of methane for a breathable atmosphere (Dole, p. 18)  */
        val MAX_CH4_IPP = 50000.0 * PPM_PRSSURE

        /** Earth convection factor from Hart, eq.20         */
        val EARTH_CONVECTION_FACTOR = 0.43

        //      FREEZING_POINT_OF_WATER =273.0;
        /** Freezing point of water in units of degrees Kelvin         */
        val FREEZING_POINT_OF_WATER = 273.15

        //      EARTH_AVERAGE_CELSIUS   =15.5;
        /** Average Earth Temperature in degrees Celsius  */
        val EARTH_AVERAGE_CELSIUS = 14.0

        /** Average Earth Temperature in degrees Kelvin  */
        val EARTH_AVERAGE_KELVIN = EARTH_AVERAGE_CELSIUS + FREEZING_POINT_OF_WATER

        /** The number of days in an Earth year  */
        val DAYS_IN_A_YEAR = 365.256

        // gas_retention_threshold = 5.0;
        /** The retention threshold for gas as a ratio of esc vel to RMS vel  */
        val GAS_RETENTION_THRESHOLD = 6.0

        /** The albedo value used for ice  */
        val ICE_ALBEDO = 0.7

        /** The albedo value used for cloud  */
        val CLOUD_ALBEDO = 0.52

        /** The albedo value used for a gas giant  */
        val GAS_GIANT_ALBEDO = 0.5

        /** The albedo value used for an airless ice world  */
        val AIRLESS_ICE_ALBEDO = 0.5

        /** The albedo value used for Earth (was .33 for a while)  */
        val EARTH_ALBEDO = 0.3

        /** The albedo value used for greenhouse effect calculations  */
        val GREENHOUSE_TRIGGER_ALBEDO = 0.20

        /** The albedo value used for a rocky planet  */
        val ROCKY_ALBEDO = 0.15

        /** The albedo value used for a rocky airless planet  */
        val ROCKY_AIRLESS_ALBEDO = 0.07

        /** The albedo value used for water  */
        val WATER_ALBEDO = 0.04

        /** The number of seconds in an hour  */
        val SECONDS_PER_HOUR = 3600.0

        /** The number of centimetres in an astronomical unit  */
        val CM_PER_AU = 1.495978707E13

        /** The number of centimetres in a kilometre  */
        val CM_PER_KM = 1.0E5

        /** The number of kilometres in an astronomical unit  */
        val KM_PER_AU = CM_PER_AU / CM_PER_KM

        /** The number of centimetres in a meter  */
        val CM_PER_METER = 100.0

        //public static final double MILLIBARS_PER_BAR = 1013.25;
        /** The number of millibars in a bar  */
        val MILLIBARS_PER_BAR = 1000.00

        /** The gravitational constant in units of dyne cm2/gram2  */
        val GRAV_CONSTANT = 6.672E-8

        /** The molar gas constant in units of g*m2/=sec2*K*mol;  */
        val MOLAR_GAS_CONST = 8314.41

        /** A constant representing the gas/dust ratio  */
        val K = 50.0

        /** Constant used in Crit_mass calc         */
        val B = 1.2E-5

        /** Coefficient of dust density (A in Dole's paper)  */
        val DUST_DENSITY_COEFF = 2.0E-3

        /** Constant used in density calcs         */
        val ALPHA = 5.0

        /** Constant used in density calcs         */
        val N = 3.0

        /** Constant used in density calcs         */
        val J = 1.46E-19

        /** Constant used in day-length calcs =cm2/sec2 g;  */
        val INCREDIBLY_LARGE_NUMBER = 9.9999E37

        /*        Now for a few molecular weights =used for RMS velocity calcs;:           */
        /*        This table is from Dole's book "Habitable Planets for Man", p. 38  */

        /** Atomic weight of hydrogen atom  */
        val ATOMIC_HYDROGEN = 1.0

        /** Atomic weight of hydrogen molecule  */
        val MOL_HYDROGEN = 2.0

        /** Atomic weight of helium  */
        val HELIUM = 4.0

        /** Atomic weight of nitrogen  */
        val ATOMIC_NITROGEN = 14.0

        /** Atomic weight of oxygen  */
        val ATOMIC_OXYGEN = 16.0

        /** Atomic weight of methane  */
        val METHANE = 16.0

        /** Atomic weight of ammonia  */
        val AMMONIA = 17.0

        /** Atomic weight of water vapour  */
        val WATER_VAPOR = 18.0

        /** Atomic weight of neon  */
        val NEON = 20.2

        /** Atomic weight of nitrogen molecule  */
        val MOL_NITROGEN = 28.0

        /** Atomic weight of carbond monoxide  */
        val CARBON_MONOXIDE = 28.0

        /** Atomic weight of nitric oxide  */
        val NITRIC_OXIDE = 30.0

        /** Atomic weight of oxygen molecule  */
        val MOL_OXYGEN = 32.0

        /** Atomic weight of hydrogen sulphide  */
        val HYDROGEN_SULPHIDE = 34.1

        /** Atomic weight of argon  */
        val ARGON = 39.9

        /** Atomic weight of carbon dioxide  */
        val CARBON_DIOXIDE = 44.0

        /** Atomic weight of nitrous oxide  */
        val NITROUS_OXIDE = 44.0

        /** Atomic weight of nitrogen dioxide  */
        val NITROGEN_DIOXIDE = 46.0

        /** Atomic weight of ozone  */
        val OZONE = 48.0

        /** Atomic weight of sulphur dioxide  */
        val SULPH_DIOXIDE = 64.1

        /** Atomic weight of sulphur trioxide  */
        val SULPH_TRIOXIDE = 80.1

        /** Atomic weight of kyrypton  */
        val KRYPTON = 83.8

        /** Atomic weight of xenon  */
        val XENON = 131.3

        // And atomic numbers, for use in ChemTable indexes

        /** Atomic number of hydrogen  */
        val AN_H = 1

        /** Atomic number of helium  */
        val AN_HE = 2

        /** Atomic number of nitrogen  */
        val AN_N = 7

        /** Atomic number of oxygen  */
        val AN_O = 8

        /** Atomic number of fluorine  */
        val AN_F = 9

        /** Atomic number of neon  */
        val AN_NE = 10

        /** Atomic number of phosphorus  */
        val AN_P = 15

        /** Atomic number of chlorine  */
        val AN_CL = 17

        /** Atomic number of argon  */
        val AN_AR = 18

        /** Atomic number of bromine  */
        val AN_BR = 35

        /** Atomic number of krypton  */
        val AN_KR = 36

        /** Atomic number of iodine  */
        val AN_I = 53

        /** Atomic number of xenon  */
        val AN_XE = 54

        /** Atomic number of mercury  */
        val AN_HG = 80

        /** Atomic number of astatine  */
        val AN_AT = 85

        /** Atomic number of radon  */
        val AN_RN = 86

        /** Atomic number of francium  */
        val AN_FR = 87

        /** Atomic 'number' of nh3  */
        val AN_NH3 = 900

        /** Atomic 'number' of h20  */
        val AN_H2O = 901

        /** Atomic 'number' of c02  */
        val AN_CO2 = 902

        /** Atomic 'number' of ozone  */
        val AN_O3 = 903

        /** Atomic 'number' of methan3  */
        val AN_CH4 = 904

        /** Atomic 'number' of ch3ch2oh  */
        val AN_CH3CH2OH = 905

        /* The following defines are used in the kothari_radius function in */
        /* file EnviroUtils.java. All units are in cgs system. ie: cm, g, dynes, etc. */

        /** Constant used in kothari_radius calculation  */
        val A1_20 = 6.485E12

        /** Constant used in kothari_radius calculation  */
        val A2_20 = 4.0032E-8

        /** Constant used in kothari_radius calculation  */
        val BETA_20 = 5.71E12

        /** Correction factor to make the atmospheric calculation match the result
         * for Earth  */
        val JIMS_FUDGE = 1.004

        /* The following defines are used in determining the fraction of a planet  */
        /* covered with clouds in function cloud_fraction in file EnviroUtils.java. */

        /** Constant used in cloud_fraction calculations in grams  */
        val Q1_36 = 1.258E19

        /** Constant used in cloud_fraction calculations in 1/Kelvin  */
        val Q2_36 = 0.0698
    }
}
