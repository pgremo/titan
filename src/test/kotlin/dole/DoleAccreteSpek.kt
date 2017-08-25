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

        val sun = Primary().apply {
            mass = 1.0
            age = 5e9
        }

        doleAccrete.createSystem(sun)

        on("should generate planets") {
            val actual: List<Planet> = sun.planets
            it("that are consistent") {
                println(actual.joinToString(separator = ",\n"))
                val expected = ArrayList(listOf(
                        Planet(rMin=0.4322606229073815, rMax=0.5290871751096994, reach=0.013674477426388092, a=0.48067389900854046, e=0.07227103187093104, mass=6.549996654744759E-7, isGasGiant=false, dustMass=6.549993137597841E-7, gasMass=3.5171469181491673E-13, radius=3779.947339457523, density=5.4531066366603795, orbitalPeriod=0.33325433719933145, day=19.5264018657163, escapeVelocity=6.747787171109415, surfaceGravity=5.75896160624544, highTemperature=404.2927712801158, lowTemperature=404.2927712801158),
                        Planet(rMin=0.7850422770027667, rMax=1.0605280364846776, reach=0.046749400320013866, a=0.9227851567437221, e=0.098607437230606, mass=6.587257451549455E-6, isGasGiant=false, dustMass=6.5872348001687834E-6, gasMass=2.2651380672273736E-11, radius=7819.142653759862, density=6.182148321602502, orbitalPeriod=0.8864431742639307, day=12.736902682851472, escapeVelocity=14.878407946902845, surfaceGravity=13.505572548382473, highTemperature=291.7903643398487, lowTemperature=291.7903643398487),
                        Planet(rMin=1.4403249614862603, rMax=1.7153891353730184, reach=0.07802438285739856, a=1.5778570484296393, e=0.037714255638814254, mass=5.979341374048749E-6, isGasGiant=false, dustMass=5.979324872431555E-6, gasMass=1.6501617194097376E-11, radius=7948.788648590815, density=5.341229584451119, orbitalPeriod=1.98199010201431, day=13.590370226488366, escapeVelocity=14.059177420707533, surfaceGravity=11.861965011436505, highTemperature=223.14513317680712, lowTemperature=223.14513317680712),
                        Planet(rMin=1.7833637046378092, rMax=2.085308341359048, reach=0.04158704102285624, a=1.9343360229984288, e=0.056549263435731356, mass=2.1365078707218066E-7, isGasGiant=false, dustMass=2.136507866748039E-7, gasMass=3.9737676107439685E-16, radius=3062.3926799419833, density=3.3470066325592023, orbitalPeriod=2.6902824565582586, day=27.699098980758333, escapeVelocity=4.281594377310638, surfaceGravity=2.8637288464099, highTemperature=201.53723061932345, lowTemperature=201.53723061932345),
                        Planet(rMin=4.843049471316721, rMax=7.335387796355054, reach=1.2013373195935535, a=6.089218633835888, e=0.007362495193799834, mass=0.0015173021049531732, isGasGiant=true, dustMass=1.2788533654416593E-4, gasMass=0.0013894167684090073, radius=81224.65626734686, density=1.2614439774718798, orbitalPeriod=15.025964261726058, day=7.434598429684109, escapeVelocity=70.06091436332314, surfaceGravity=28.62663449729233, highTemperature=113.59009493390364, lowTemperature=113.59009493390364),
                        Planet(rMin=19.806363476355678, rMax=22.928175667140245, reach=1.4050364325107816, a=21.36726957174796, e=0.007294786184922686, mass=1.8696587467490485E-5, isGasGiant=true, dustMass=9.004234232146215E-6, gasMass=9.692353235344268E-6, radius=15374.433061794376, density=2.3035381362661296, orbitalPeriod=98.76965657733928, day=12.677207555707398, escapeVelocity=17.875793526510996, surfaceGravity=9.894844186815364, highTemperature=60.63826013398314, lowTemperature=60.63826013398314),
                        Planet(rMin=35.626051951656294, rMax=44.791777550980754, reach=1.566102332732076, a=40.208914751318524, e=0.07502715468915316, mass=2.30140016083613E-6, isGasGiant=true, dustMass=1.2908130136882758E-6, gasMass=1.0105871471478544E-6, radius=8110.747582217976, density=1.934968598286435, orbitalPeriod=254.9667377704187, day=19.062088426797782, escapeVelocity=8.634741460902983, surfaceGravity=4.384794561721409, highTemperature=44.20384736102464, lowTemperature=44.20384736102464)
                ))
                actual `should equal` expected
            }
        }
    }
})