package name.paynd.android.clientlist.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Constructs a function that processes input data and passes the first data to [destinationFunction] and skips all new data for the next [skipMs].
 */
fun <T> throttleFirst(
    coroutineScope: CoroutineScope,
    skipMs: Long = 300L,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var throttleJob: Job? = null
    return { param: T ->
        if (throttleJob?.isCompleted != false) {
            throttleJob = coroutineScope.launch {
                destinationFunction(param)
                delay(skipMs)
            }
        }
    }
}
