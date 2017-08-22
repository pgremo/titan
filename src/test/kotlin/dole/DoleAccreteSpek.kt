package dole

import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.*
import kotlin.collections.ArrayList

object DoleAccreteSpek : Spek({
    given("Accrete with a specific random seed") {
        val utils = Random(1)

        val accrete = DoleAccrete(utils)

        val gen = GenStar(utils)

        val sun = BasicPrimary()

        sun.mass = 1.0
        //      sun.setMass(0.69999999);
        sun.age = 5e9
        //      final Primary sun = gen.generateStar();

        accrete.createSystem(sun)

        on("should generate planets") {
            val actual: List<Planet> = sun.planets
            it("that are consistent") {
                println(actual.joinToString(separator = ",\n"))
                val expected = ArrayList(listOf(
                        DolePlanetRecord().apply{a=0.48067389900854046; e=0.07227103187093104; mass=6.549996654744759E-7; isGasGiant=false},
                        DolePlanetRecord().apply{a=0.9227851567437221; e=0.098607437230606; mass=6.587257451549455E-6; isGasGiant=false},
                        DolePlanetRecord().apply{a=1.5778570484296393; e=0.037714255638814254; mass=5.979341374048749E-6; isGasGiant=false},
                        DolePlanetRecord().apply{a=1.9343360229984288; e=0.056549263435731356; mass=2.1365078707218066E-7; isGasGiant=false},
                        DolePlanetRecord().apply{a=6.089218633835888; e=0.007362495193799834; mass=0.0015173021049531732; isGasGiant=true},
                        DolePlanetRecord().apply{a=21.367269571747958; e=0.007294786184922686; mass=1.8696587467490485E-5; isGasGiant=true},
                        DolePlanetRecord().apply{a=40.208914751318524; e=0.07502715468915316; mass=2.30140016083613E-6; isGasGiant=true}
                ))
                actual `should equal` expected
            }
        }
    }
})