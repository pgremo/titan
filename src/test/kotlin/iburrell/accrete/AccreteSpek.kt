package iburrell.accrete

import org.amshove.kluent.`should equal`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import java.util.*


object ChannelDeserializerSpek : Spek({
    given("Accrete with a specific random seed") {
        val accrete = Accrete(1.0, 1.0, Random(4))
        on("should generate planets") {
            val planets: Sequence<Planetismal> = accrete.DistributePlanets()
            planets.forEach { println(it) }
            it("that are consistent") {
                val expected = sequenceOf(
                        Planetismal(orbitalAxis = 0.3346366573668492, eccentricity = 0.14332718614182327, massSolar = 1.0707205393334508E-7, isGasGiant = false),
                        Planetismal(orbitalAxis = 0.42982771655091934, eccentricity = 0.03842074164406584, massSolar = 9.309289377425137E-8, isGasGiant = false),
                        Planetismal(orbitalAxis = 0.5148369396600687, eccentricity = 0.029394920756592113, massSolar = 6.087609463130495E-8, isGasGiant = false),
                        Planetismal(orbitalAxis = 0.7192893596341622, eccentricity = 0.1883712944451603, massSolar = 3.305219783370488E-6, isGasGiant = false),
                        Planetismal(orbitalAxis = 1.515888157324751, eccentricity = 2.9895092978937665E-4, massSolar = 5.567364484664794E-6, isGasGiant = false),
                        Planetismal(orbitalAxis = 4.195872392776982, eccentricity = 0.24770000399717818, massSolar = 0.0021836540205658235, isGasGiant = true),
                        Planetismal(orbitalAxis = 7.81623547606845, eccentricity = 0.006019730377180288, massSolar = 8.791987213776919E-5, isGasGiant = true),
                        Planetismal(orbitalAxis = 14.236850899757643, eccentricity = 0.10696270613599701, massSolar = 1.2421895021042976E-7, isGasGiant = false),
                        Planetismal(orbitalAxis = 16.507109168232027, eccentricity = 0.021752636610588905, massSolar = 4.808392875714237E-5, isGasGiant = true),
                        Planetismal(orbitalAxis = 37.36765010562492, eccentricity = 0.19297749374197157, massSolar = 3.1567043252198183E-6, isGasGiant = true),
                        Planetismal(orbitalAxis = 46.70675680698262, eccentricity = 0.09914286361116732, massSolar = 2.603019708382757E-7, isGasGiant = false)
                )
                planets.toList() `should equal` expected.toList()
            }
        }
    }
})