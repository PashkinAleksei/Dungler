package ru.lemonapes.dungler

import android.util.Log
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.system.measureTimeMillis

class Utils {
    companion object {
        var TAG = "myLogs"

        fun <T> log(obj: T?) {
            Log.d(TAG, obj.toString())
        }

        inline fun <T> logWithTimeTrack(func: (() -> T?)) {
            val res: T?
            val time = measureTimeMillis {
                res = func.invoke()
            }
            Log.d(TAG, "$res msWasted: $time")
        }

        inline fun logWithMiddleTimeTrack(func: (() -> Int)) {
            var sum = 0L
            var res = 0

            //for (i in 0..100) {
                sum += measureTimeMillis {
                    res = func.invoke()
                }
            //}
            Log.d(TAG, "$res msWasted: ${sum}")
        }

        fun TextView.setTextOrGone(text: String?) {
            setText(text)
            visibility = if (text.isNullOrEmpty()) {
                TextView.GONE
            } else {
                TextView.VISIBLE
            }
        }

        fun <F, S> Map<F, S>.find(predicate: (S) -> Boolean): S? {
            var target: S? = null
            forEach { (_, second) ->
                if (predicate(second)) {
                    target = second
                    return@forEach
                }
            }
            return target
        }

        fun Pair<Int, Int>.getRandomFromRange() = Random.nextInt(IntRange(first, second))

        fun Int.safeDivide(divisor: Int): BigDecimal {
            return BigDecimal(this)
                .divide(
                    BigDecimal(divisor),
                    2,
                    RoundingMode.HALF_UP
                )
        }

        fun BigDecimal.roundToInt(): Int = setScale(0, RoundingMode.HALF_UP).intValueExact()
    }
}