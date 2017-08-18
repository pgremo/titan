package iburrell.accrete

import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.*


object AccreteSpek : Spek({
    given("Accrete with a specific random seed") {
        val accrete = Accrete(1.0, 1.0, Random(4))
        on("should generate planets") {
            val actual: List<Planetismal> = accrete.generate()
            it("that are consistent") {
                val expected = listOf(
                        Planetismal(orbitalAxis = 0.4463836482882333, eccentricity = 0.17002476784585605, massSolar = 1.0945498711692779E-7, isGasGiant = false),
                        Planetismal(orbitalAxis = 0.709303112342481, eccentricity = 0.26499816882217464, massSolar = 4.78241419097157E-7, isGasGiant = false),
                        Planetismal(orbitalAxis = 1.515888157324751, eccentricity = 0.34791906743121304, massSolar = 0.0023397461752589933, isGasGiant = true),
                        Planetismal(orbitalAxis = 5.474351790602069, eccentricity = 0.23235606633715436, massSolar = 0.015090537956538766, isGasGiant = true),
                        Planetismal(orbitalAxis = 16.507109168232027, eccentricity = 0.10167472963671309, massSolar = 5.130515383723757E-4, isGasGiant = true),
                        Planetismal(orbitalAxis = 36.613965194616426, eccentricity = 0.1756979165537415, massSolar = 5.428377960825529E-4, isGasGiant = true)
                )
                actual `should equal` expected
            }
        }
    }
})