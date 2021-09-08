package name.paynd.android.clientlist.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import name.paynd.android.clientlist.data.Client
import name.paynd.android.clientlist.data.DataSource
import javax.inject.Inject

class ClientListViewModel @Inject constructor(
    private val dataSource: DataSource
) : ViewModel() {
    val list = dataSource.list
//        flow {
//        emit(dataSource.list)
//    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList<Client>())
}