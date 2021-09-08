package name.paynd.android.clientlist.data

import android.net.Uri

data class Client(
    val id: Long,
    val dob: String,
    val weight: Int,
    val weightUnit: WeightUnit,
    val uri: Uri
)

enum class WeightUnit {
    KG, LB
}

fun Int.toUnit(): WeightUnit {
    return if (this == 0) {
        WeightUnit.LB
    } else {
        WeightUnit.KG
    }
}

fun WeightUnit.toInt(): Int {
    return when (this) {
        WeightUnit.LB -> 0
        WeightUnit.KG -> 1
    }
}