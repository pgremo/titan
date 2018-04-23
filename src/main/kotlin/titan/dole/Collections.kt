package titan.dole

import java.util.*
import java.util.concurrent.ThreadLocalRandom


fun <E, R> NavigableSet<E>.foldRight(initial: R, operation: (E, acc: R) -> R): R
        = fold(descendingIterator(), initial, operation)

tailrec fun <E, R> fold(xs: Iterator<E>, initial: R, operation: (E, acc: R) -> R): R
        = if (!xs.hasNext()) initial else fold(xs, operation(xs.next(), initial), operation)

fun <E> List<E>.random(random: Random = ThreadLocalRandom.current()): E?
        = if (isEmpty()) null else this[random.nextInt(size)]

class SortedList<E, in R : Comparable<R>>(private val b: MutableList<E>, private val selector: (E) -> R?) : MutableList<E> by b {
    override fun add(element: E)
            = b.add(element).also { if (it) b.sortWith(compareBy(selector)) }
}