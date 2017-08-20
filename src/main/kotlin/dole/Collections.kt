package dole

import java.util.*


fun <E, R> NavigableSet<E>.foldRight(initial: R, operation: (E, acc: R) -> R): R {
    var accumulator = initial
    if (!isEmpty()) {
        val iterator = descendingIterator()
        while (iterator.hasNext()) {
            accumulator = operation(iterator.next(), accumulator)
        }
    }
    return accumulator
}

fun <E, R> Collection<E>.fold(initial: R, operation: (E, acc: R) -> R): R {
    var accumulator = initial
    if (!isEmpty()) {
        val iterator = iterator()
        while (iterator.hasNext()) {
            accumulator = operation(iterator.next(), accumulator)
        }
    }
    return accumulator
}