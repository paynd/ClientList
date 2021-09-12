package name.paynd.android.clientlist.ui.list

import androidx.lifecycle.ViewModel
import name.paynd.android.clientlist.data.DataSource
import javax.inject.Inject

class ClientListViewModel @Inject constructor(
    private val dataSource: DataSource
) : ViewModel() {
    val list
        get() = dataSource.list
}