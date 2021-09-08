package name.paynd.android.clientlist.ui.main

import android.net.Uri
import androidx.lifecycle.ViewModel
import name.paynd.android.clientlist.data.Client
import name.paynd.android.clientlist.data.DataSource
import name.paynd.android.clientlist.data.WeightUnit
import javax.inject.Inject

class FatViewModel @Inject constructor(
    private val dataSource: DataSource
) : ViewModel() {
    val list
        get() = dataSource.list

    var currentClient: ClientTransferObject? = null

    fun updateWeight(value: Int, unit: WeightUnit) {
        checkClient()
        currentClient = currentClient?.copy(
            weight = value,
            weightUnit = unit
        )
    }

    fun updateDOB(date: String) {
        checkClient()
        currentClient = currentClient?.copy(dob = date)
    }

    fun updatePhoto(uri: Uri) {
        checkClient()
        currentClient = currentClient?.copy(uri = uri)
    }

    fun checkAndSave(): Boolean {
        val result = currentClient?.toClient()

        return if (result != null) {
            dataSource.update(result)
            true
        } else {
            false
        }
    }

    private fun checkClient() {
        if (currentClient == null) {
            currentClient = ClientTransferObject(System.currentTimeMillis())
        }
    }
}

data class ClientTransferObject(
    val id: Long,
    val dob: String? = null,
    val weight: Int? = null,
    val weightUnit: WeightUnit? = null,
    val uri: Uri? = null
)

fun ClientTransferObject.toClient(): Client? {
    return if (dob != null && weight != null && weightUnit != null && uri != null) {
        Client(id, dob, weight, weightUnit, uri)
    } else null
}