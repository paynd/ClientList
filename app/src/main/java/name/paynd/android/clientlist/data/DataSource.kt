package name.paynd.android.clientlist.data

import kotlinx.coroutines.flow.SharedFlow

interface DataSource {
    fun add(client: Client)
    fun update(client: Client)
    fun delete(client: Client)
    fun get(id: Long): Client?
    val list: SharedFlow<List<Client>>
}