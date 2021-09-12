package name.paynd.android.clientlist.ui.add

interface Validator {
    fun checkDate(client: ClientTransferObject?): ValidationResult
    fun checkPhoto(client: ClientTransferObject?) : ValidationResult
}

sealed class ValidationResult {
    object Success: ValidationResult()
    data class Error(val message: String) : ValidationResult()
}