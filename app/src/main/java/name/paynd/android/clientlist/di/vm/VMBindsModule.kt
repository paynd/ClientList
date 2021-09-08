package name.paynd.android.clientlist.di.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import name.paynd.android.clientlist.di.AppScope
import name.paynd.android.clientlist.ui.main.FatViewModel


@Module
interface VMBindsModule {
    @Binds
    abstract fun bindViewModelFactory(factory: VMFactory): ViewModelProvider.Factory

    @[AppScope Binds IntoMap VMKey(FatViewModel::class) ]
    fun bindClientListViewModel(sourcesViewModel: FatViewModel): ViewModel
}