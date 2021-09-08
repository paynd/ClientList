package name.paynd.android.clientlist.data

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class InMemoryDataSource : DataSource {
    private val dataSet: MutableSet<Client> = mutableSetOf<Client>()
    private val _list = MutableSharedFlow<List<Client>>(
        replay = 1,
        extraBufferCapacity = 1
    )
    override val list: SharedFlow<List<Client>> = _list

    //or update
    override fun add(client: Client) {
        dataSet.add(client)
        updateFlow()
    }

    override fun delete(client: Client) {
        dataSet.remove(client)
        updateFlow()
    }

    private fun updateFlow() {
        val success = _list.tryEmit(dataSet.toList())
    }
}