package iburrell.accrete

import java.util.*

fun Random.nextDouble(scale: Double) = nextDouble() * scale

fun Random.nextDouble(min: Double, max: Double) = nextDouble(max - min) + min

fun Random.nextDouble(range: ClosedRange<Double>) = nextDouble(range.start, range.endInclusive)
