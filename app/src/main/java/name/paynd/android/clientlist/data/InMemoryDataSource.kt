package name.paynd.android.clientlist.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class InMemoryDataSource : DataSource {
    private val dataMap: MutableMap<Long, Client> = mutableMapOf()
    private val _list = MutableSharedFlow<List<Client>>(
        replay = 1,
        extraBufferCapacity = 1
    )
    override val list: SharedFlow<List<Client>> = _list

    //or update
    override fun add(client: Client) {
        dataMap[client.id] = client
        updateFlow()
    }

    override fun update(client: Client) {
        dataMap[client.id] = client
    }

    override fun delete(client: Client) {
        dataMap.remove(client.id)
        updateFlow()
    }

    override fun get(id: Long): Client? {
        return dataMap[id]
    }

    private fun updateFlow() {
        _list.tryEmit(dataMap.values.toList())
    }
}
