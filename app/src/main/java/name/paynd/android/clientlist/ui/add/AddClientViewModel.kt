package name.paynd.android.clientlist.ui.add

import android.net.Uri
import androidx.lifecycle.ViewModel
import name.paynd.android.clientlist.data.Client
import name.paynd.android.clientlist.data.DataSource
import name.paynd.android.clientlist.data.WeightUnit
import name.paynd.android.clientlist.util.Mode
import javax.inject.Inject

class AddClientViewModel @Inject constructor(
    private val dataSource: DataSource,
    private val validator: ClientValidator
) : ViewModel() {
    var currentClient: ClientTransferObject? = null

    var mode: Mode = Mode.CREATE

    fun loadClient(id: Long) {
        currentClient = dataSource.get(id)?.toTransferObject()
    }

    fun updateWeight(value: Int, unit: WeightUnit) {
        checkClient()
        currentClient = currentClient?.copy(
            weight = value,
            weightUnit = unit
        )
    }

    fun updateDOB(date: String) {
        checkClient()
        currentClient = currentClient?.copy(dobString = date)
    }

    fun updatePhoto(uri: Uri) {
        checkClient()
        currentClient = currentClient?.copy(uri = uri)
    }

    fun checkAndSave(): Boolean {
        val result = currentClient?.toClient()

        return if (result != null) {
            dataSource.add(result)
            currentClient = null
            true
        } else {
            false
        }
    }

    fun checkPhoto() = validator.checkPhoto(currentClient)

    private fun checkClient() {
        if (currentClient == null) {
            currentClient = ClientTransferObject(System.currentTimeMillis())
        }
    }
}

data class ClientTransferObject(
    val id: Long,
    val dobString: String? = null,
    val weight: Int? = null,
    val weightUnit: WeightUnit? = null,
    val uri: Uri? = null
)

fun ClientTransferObject.toClient(): Client? {
    return if (dobString != null && weight != null && weightUnit != null && uri != null) {
        Client(id, dobString, weight, weightUnit, uri)
    } else null
}

fun Client.toTransferObject() = ClientTransferObject(id, dob, weight, weightUnit, uri)