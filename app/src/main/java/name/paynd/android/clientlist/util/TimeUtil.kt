package name.paynd.android.clientlist.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.ResolverStyle

const val parsePatternWithDefaults = "dd.MM.uuuu"
const val parsePatternShort = "dd.MM.uuuu"

data class DOB(val year: Int, val month: Int, val day: Int)

private val dateFormat: DateTimeFormatter by lazy {
    DateTimeFormatterBuilder()
        .parseStrict()
        .appendPattern(parsePatternShort)
        .toFormatter()
        .withResolverStyle(ResolverStyle.STRICT)
}

fun getFormattedString(year: Int, month: Int, day: Int): String {
    return LocalDateTime.of(year, month, day, 0, 0)
        .format(dateFormat)
}

fun toDOB(string: String?): DOB? {
    runCatching {
        LocalDate.parse(string, dateFormat).atStartOfDay()
    }.onSuccess { time ->
        return DOB(time.year, time.monthValue, time.dayOfMonth)
    }
    return null
}