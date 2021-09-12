package name.paynd.android.clientlist.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val pattern = "dd.MM.yyyy"

data class DOB(val year: Int, val month: Int, val day: Int)

val formatter: DateTimeFormatter by lazy {
    DateTimeFormatter.ofPattern(pattern)
}

fun getFormattedString(year: Int, month: Int, day: Int): String {
    return LocalDateTime.of(year, month, day, 0, 0)
        .format(formatter)
}

fun toDOB(string: String?): DOB? {
    runCatching {
        LocalDateTime.from(formatter.parse(string))
    }.onSuccess { time ->
        return DOB(time.year, time.monthValue, time.dayOfMonth)
    }
    return null
}