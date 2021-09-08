package name.paynd.android.clientlist.data

import android.util.Log
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class InMemoryDataSource : DataSource {
//    private val dataStorage: MutableList<Client> = mutableListOf()
    private val dataSet: MutableSet<Client> = mutableSetOf<Client>()
    private val _list = MutableSharedFlow<List<Client>>(
        replay = 1,
        extraBufferCapacity = 1
    )
    override val list: SharedFlow<List<Client>> = _list

    override fun add(client: Client) {
//        dataStorage.add(client)
        dataSet.add(client)
        updateFlow()
    }

    override fun update(client: Client) {
//        val index = dataStorage.indexOf(client)
//        return if (index >= 0) {
//            dataStorage[index] = client
//            updateFlow()
//            true
//        } else {
//            false
//        }

        add(client)
    }

    override fun delete(client: Client) {
//        dataStorage.remove(client)
        dataSet.remove(client)
        updateFlow()
    }

    private fun updateFlow() {
        val success = _list.tryEmit(dataSet.toList())
        Log.d("####", "is success: $success")

    }
}