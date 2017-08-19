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
        val utils = MathUtils(Random(1))

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
                        DolePlanetRecord().apply { a = 0.3113751815174137; e = 0.03999828648267956; mass = 1.643651194286459E-7; isGasGiant = false },
                        DolePlanetRecord().apply { a = 0.42386250483222354; e = 0.018198213550807085; mass = 4.763165900327659E-7; isGasGiant = false },
                        DolePlanetRecord().apply { a = 0.5952289632842835; e = 0.024804932956769665; mass = 1.5480174565079395E-6; isGasGiant = false },
                        DolePlanetRecord().apply { a = 1.2010286878417895; e = 0.0014835686180887375; mass = 1.2066269240540442E-5; isGasGiant = true },
                        DolePlanetRecord().apply { a = 1.8344924182083255; e = 0.02856363405524398; mass = 1.0789875794951952E-5; isGasGiant = true },
                        DolePlanetRecord().apply { a = 3.7083934832989147; e = 0.005686561589768269; mass = 1.6930478692795537E-4; isGasGiant = true },
                        DolePlanetRecord().apply { a = 8.281859692657306; e = 0.009900058459724637; mass = 1.711609257961245E-4; isGasGiant = true },
                        DolePlanetRecord().apply { a = 18.395001217667495; e = 4.8144185563087083E-4; mass = 7.159565830198185E-5; isGasGiant = true },
                        DolePlanetRecord().apply { a = 43.11940427104446; e = 0.0075607994621553765; mass = 1.8009632755850223E-7; isGasGiant = false }
                ))
                actual `should equal` expected
            }
        }
    }
})