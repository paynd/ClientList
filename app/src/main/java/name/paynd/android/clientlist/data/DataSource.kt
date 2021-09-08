package name.paynd.android.clientlist.data

import kotlinx.coroutines.flow.SharedFlow

interface DataSource {
    fun add(client: Client)
    fun update(client: Client): Boolean
    fun delete(client: Client)

    val list: SharedFlow<List<Client>>
}