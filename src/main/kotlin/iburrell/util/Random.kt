package iburrell.util

import java.util.*

fun Random.nextDouble(min: Double) = nextDouble() * min
fun Random.nextDouble(min: Double, max: Double) = nextDouble(max - min) + min
