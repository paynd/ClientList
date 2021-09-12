package name.paynd.android.clientlist.di.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import name.paynd.android.clientlist.di.AppScope
import name.paynd.android.clientlist.ui.add.AddClientViewModel
import name.paynd.android.clientlist.ui.list.ClientListViewModel


@Module
interface VMBindsModule {
    @Binds
    abstract fun bindViewModelFactory(factory: VMFactory): ViewModelProvider.Factory

    @[AppScope Binds IntoMap VMKey(ClientListViewModel::class)]
    fun bindClientListViewModel(sourcesViewModel: ClientListViewModel): ViewModel

    @[AppScope Binds IntoMap VMKey(AddClientViewModel::class)]
    fun bindAddClientViewModel(sourcesViewModel: AddClientViewModel): ViewModel
}