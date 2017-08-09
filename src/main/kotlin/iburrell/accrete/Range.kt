package iburrell.accrete


fun <T : Comparable<T>> ClosedRange<T>.intersects(other: ClosedRange<T>) = start <= other.endInclusive && other.start <= endInclusive

fun ClosedRange<Double>.difference(other: ClosedRange<Double>) = Math.max(start, other.start).rangeTo(Math.min(endInclusive, other.endInclusive))

val ClosedFloatingPointRange<Double>.width: Double
    get() = endInclusive - start

