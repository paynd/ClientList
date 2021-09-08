package name.paynd.android.clientlist.ui.add

import androidx.lifecycle.ViewModel
import name.paynd.android.clientlist.data.DataSource
import javax.inject.Inject

class AddClientViewModel @Inject constructor(
    private val dataSource: DataSource
) : ViewModel() {
    var state: State = State.WEIGHT

    fun next(): Boolean {
        return when (state) {
            State.WEIGHT -> {
                state = State.DATE
                true
            }
            State.DATE -> {
                state = State.PHOTO
                true
            }
            State.PHOTO -> {
                // check if data valid
                true
            }
        }
    }

    fun back() {
        when (state) {
            State.WEIGHT -> {
            }
            State.DATE -> {
                state = State.WEIGHT
            }
            State.PHOTO -> {
                state = State.DATE
            }
        }
    }

    fun saveClient() {
    }
}