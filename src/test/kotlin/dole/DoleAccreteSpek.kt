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
                        DolePlanetRecord().apply{a=0.6303917344730557; e=0.005443979916138153; mass=4.104823500422755E-6; isGasGiant=false},
                        DolePlanetRecord().apply{a=0.9712121767623835; e=0.010796471805141372; mass=2.5706982445480604E-7; isGasGiant=false},
                        DolePlanetRecord().apply{a=1.3555402192461667; e=0.0020204429021313808; mass=1.8621572094689222E-5; isGasGiant=true},
                        DolePlanetRecord().apply{a=2.172843596908609; e=0.010569426321991704; mass=1.0789237351021604E-6; isGasGiant=false},
                        DolePlanetRecord().apply{a=4.522056786834336; e=3.9815875679888313E-4; mass=4.419461824501252E-4; isGasGiant=true},
                        DolePlanetRecord().apply{a=14.353802146015811; e=0.011948880976919651; mass=9.340614221827492E-5; isGasGiant=true},
                        DolePlanetRecord().apply{a=28.54042758070824; e=0.001173028001086207; mass=3.12928920549438E-7; isGasGiant=false},
                        DolePlanetRecord().apply{a=41.603671449658954; e=0.0028714884396017215; mass=1.506198521496348E-6; isGasGiant=true}
                ))
                actual `should equal` expected
            }
        }
    }
})