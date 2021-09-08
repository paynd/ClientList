package name.paynd.android.clientlist.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class InMemoryDataSource : DataSource {
    private val dataStorage: MutableList<Client> = mutableListOf()
    private val _list = MutableSharedFlow<List<Client>>()
    override val list: SharedFlow<List<Client>> = _list

    override fun add(client: Client) {
        dataStorage.add(client)
        updateFlow()
    }

    override fun update(client: Client): Boolean {
        val index = dataStorage.indexOf(client)
        return if (index >= 0) {
            dataStorage[index] = client
            updateFlow()
            true
        } else {
            false
        }

    }

    override fun delete(client: Client) {
        dataStorage.remove(client)
        updateFlow()
    }

    private fun updateFlow() = _list.tryEmit(dataStorage)
}