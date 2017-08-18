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
        val utils = MathUtils(Random(4))

        val accrete = DoleAccrete(utils)

        val gen = GenStar(utils)

        val sun = BasicPrimary()

        sun.mass = 1.0
        //      sun.setMass(0.69999999);
        sun.age = 5e9
        //      final Primary sun = gen.generateStar();

        accrete.createSystem(sun)

        on("should generate planets") {
            val actual: List<Planet> = sun.planets!!
            it("that are consistent") {
                val expected = ArrayList(listOf(
                        DolePlanetRecord().apply { a = 0.6572224954434809; e = 0.04329162685093463; mass = 3.859717928719281E-6; isGasGiant = false },
                        DolePlanetRecord().apply { a = 1.0240937233260297; e = 0.031843527533283233; mass = 1.7964491923215231E-6; isGasGiant = false },
                        DolePlanetRecord().apply { a = 1.3510303471528042; e = 0.03490004324098045; mass = 1.956061209308036E-7; isGasGiant = false },
                        DolePlanetRecord().apply { a = 1.5259091636154962; e = 0.002965138935140499; mass = 5.710473806946889E-6; isGasGiant = false },
                        DolePlanetRecord().apply { a = 3.337205573953363; e = 0.0044956244982390925; mass = 6.444052162711049E-4; isGasGiant = true },
                        DolePlanetRecord().apply { a = 8.117560008743382; e = 0.01821686514301929; mass = 1.7377688367254667E-4; isGasGiant = true },
                        DolePlanetRecord().apply { a = 12.760707882655453; e = 0.003687757400670122; mass = 5.582708220267491E-5; isGasGiant = true },
                        DolePlanetRecord().apply { a = 23.10577740897831; e = 0.006764103222307916; mass = 1.2764452493596015E-4; isGasGiant = true },
                        DolePlanetRecord().apply { a = 41.24869655970253; e = 0.017583557283208262; mass = 1.564471299689972E-6; isGasGiant = true },
                        DolePlanetRecord().apply { a = 45.85680700022379; e = 4.840385508425582E-5; mass = 5.654622838249816E-8; isGasGiant = false }
                ))
                actual `should equal` expected
            }
        }
    }
})