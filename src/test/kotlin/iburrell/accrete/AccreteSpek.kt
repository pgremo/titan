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
                        Planetismal(orbitalAxis = 0.3346366573668492, eccentricity = 0.14332718614182327, massSolar = 1.0707205393334508E-7, isGasGiant = false),
                        Planetismal(orbitalAxis = 0.42982771655091934, eccentricity = 0.03842074164406584, massSolar = 9.309289377425137E-8, isGasGiant = false),
                        Planetismal(orbitalAxis = 0.5148369396600687, eccentricity = 0.029394920756592113, massSolar = 6.087609463130495E-8, isGasGiant = false),
                        Planetismal(orbitalAxis = 0.7192893596341622, eccentricity = 0.1883712944451603, massSolar = 3.305219783370488E-6, isGasGiant = false),
                        Planetismal(orbitalAxis = 1.515888157324751, eccentricity = 2.9895092978937665E-4, massSolar = 5.567364484664794E-6, isGasGiant = false),
                        Planetismal(orbitalAxis = 4.195872392776982, eccentricity = 0.24770000399717818, massSolar = 0.0021836540205658235, isGasGiant = true),
                        Planetismal(orbitalAxis = 7.81623547606845, eccentricity = 0.006019730377180288, massSolar = 8.791987213776919E-5, isGasGiant = true),
                        Planetismal(orbitalAxis = 16.500329298482896, eccentricity = 0.0213595484466494, massSolar = 4.8208147707352795E-5, isGasGiant = true),
                        Planetismal(orbitalAxis = 37.56747530273328, eccentricity = 0.18987173644537275, massSolar = 3.2304234197568315E-6, isGasGiant = true),
                        Planetismal(orbitalAxis = 45.95477381384603, eccentricity = 0.029308108983233994, massSolar = 1.865828763012627E-7, isGasGiant = false)
                )
                actual `should equal` expected
            }
        }
    }
})