package name.paynd.android.clientlist.ui.add


import android.content.res.Resources
import name.paynd.android.clientlist.R
import javax.inject.Inject

class ClientValidator @Inject constructor(private val resources: Resources) : Validator {
    override fun checkDate(client: ClientTransferObject?): ValidationResult {
        return if (client?.dobString != null) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(resources.getString(R.string.warn_age))
        }
    }

    override fun checkPhoto(client: ClientTransferObject?): ValidationResult {
        return if (client?.uri != null) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(resources.getString(R.string.warn_photo))
        }
    }
}