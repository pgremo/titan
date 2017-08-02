//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package iburrell.util

import java.util.*

fun Random.nextDouble(min: Double) = nextDouble() * min
fun Random.nextDouble(min: Double, max: Double) = nextDouble(max - min) + min
