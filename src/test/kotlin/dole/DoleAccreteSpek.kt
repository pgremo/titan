package dole

import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.*
import kotlin.collections.ArrayList

object DoleAccreteSpek : Spek({
    val doleAccrete = DoleAccrete(Random(1))
    given("Accrete with a specific random seed") {

        val sun = Primary(mass = 1.0, age = 5e9)

        doleAccrete.createSystem(sun)

        on("should generate planets") {
            val actual: List<Planet> = sun.planets
            it("that are consistent") {
                println(actual.joinToString(separator = ",\n"))
                val expected = ArrayList(listOf(
                        Planet(rMin = 0.33395878843307775, rMax = 0.36624277268865274, reach = 0.007236411238248507, a = 0.35010078056086524, e = 0.02543719232865488, mass = 1.8252431229627851E-7, isGasGiant = false, dustMass = 1.8252430398694052E-7, gasMass = 8.309338009728047E-15, radius = 2537.0898432954536, density = 5.031451736231006, orbitalPeriod = 0.20715223272197453, day = 24.827471900768362, escapeVelocity = 4.347865981566209, surfaceGravity = 3.5665112727279555, highTemperature = 473.72325734038037, lowTemperature = 473.72325734038037),
                        Planet(rMin = 0.446184254728272, rMax = 0.5057281749974103, reach = 0.014158426981056509, a = 0.4759562148628412, e = 0.032804557784820776, mass = 7.830551557443668E-7, isGasGiant = false, dustMass = 7.830548390666947E-7, gasMass = 3.16677671925883E-13, radius = 3978.557579353228, density = 5.589961083858029, orbitalPeriod = 0.32836019361956587, day = 18.796910942229168, escapeVelocity = 7.191459925930985, surfaceGravity = 6.213679881789296, highTemperature = 406.29150820028184, lowTemperature = 406.29150820028184),
                        Planet(rMin = 0.6788334864434072, rMax = 0.878243100348893, reach = 0.027713382877189798, a = 0.7785382933961501, e = 0.09246998469595008, mass = 1.605605879029259E-6, isGasGiant = false, dustMass = 1.6056044644669576E-6, gasMass = 1.4145623013040879E-12, radius = 5109.537078408854, density = 5.407062191202669, orbitalPeriod = 0.6869418385826336, day = 16.85850631453395, escapeVelocity = 9.086830126300407, surfaceGravity = 7.718934631065288, highTemperature = 317.6736968034768, lowTemperature = 317.6736968034768),
                        Planet(rMin = 1.495277849010478, rMax = 1.9686442326774105, reach = 0.1360584536853654, a = 1.7319610408439443, e = 0.058098730730726245, mass = 3.808607243493474E-5, isGasGiant = true, dustMass = 2.3304532058678595E-5, gasMass = 1.4781540376256147E-5, radius = 21812.084646840507, density = 1.6415303109446302, orbitalPeriod = 2.279329849769981, day = 12.60142745317242, escapeVelocity = 21.419938482798678, surfaceGravity = 10.00369688550496, highTemperature = 212.98655403059774, lowTemperature = 212.98655403059774),
                        Planet(rMin = 4.0429032760133925, rMax = 5.970306464716627, reach = 0.8169943387000598, a = 5.00660487036501, e = 0.029302742966584805, mass = 7.095957124651715E-4, isGasGiant = true, dustMass = 1.0711120420000104E-4, gasMass = 6.024845082651705E-4, radius = 71324.22706449557, density = 0.8716233109133261, orbitalPeriod = 11.20250061058676, day = 9.546347653796222, escapeVelocity = 51.12939646235681, surfaceGravity = 17.369221509953285, highTemperature = 125.2707240531366, lowTemperature = 125.2707240531366),
                        Planet(rMin = 12.374265407676882, rMax = 15.401507247308063, reach = 1.4320802949814488, a = 13.887886327492472, e = 0.005871348807969801, mass = 1.1307691967818448E-4, isGasGiant = true, dustMass = 3.483451693757938E-5, gasMass = 7.82424027406051E-5, radius = 34383.07645400089, density = 1.2425688866974025, orbitalPeriod = 51.755228425050525, day = 11.528250688693879, escapeVelocity = 29.396683063086606, surfaceGravity = 11.936572798751437, highTemperature = 75.21477657825056, lowTemperature = 75.21477657825056),
                        Planet(rMin = 22.341569613220997, rMax = 23.9886005553538, reach = 0.4761679417043007, a = 23.165085084287398, e = 0.014994442200331193, mass = 1.7914982646972725E-7, isGasGiant = false, dustMass = 1.7914980173460042E-7, gasMass = 2.473512682441411E-14, radius = 3577.1964918687636, density = 1.7600299770283316, orbitalPeriod = 111.4938338685628, day = 35.333903042803705, escapeVelocity = 3.627609325981649, surfaceGravity = 1.7590464357329598, highTemperature = 58.23771094339432, lowTemperature = 58.23771094339432),
                        Planet(rMin = 28.925135235999868, rMax = 31.76432943542239, reach = 1.4160591152173991, a = 30.34473233571113, e = 1.1659303679856237E-4, mass = 4.742358168400487E-6, isGasGiant = true, dustMass = 3.537885202819991E-6, gasMass = 1.204472965580496E-6, radius = 10109.733254174762, density = 2.057559374605748, orbitalPeriod = 167.15715332895147, day = 16.55191261937168, escapeVelocity = 11.102250933164433, surfaceGravity = 5.811744542993366, highTemperature = 50.883806315920474, lowTemperature = 50.883806315920474)
                ))
                actual `should equal` expected
            }
        }
    }
})