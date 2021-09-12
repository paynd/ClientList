package name.paynd.android.clientlist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import name.paynd.android.clientlist.data.DataSource
import name.paynd.android.clientlist.data.InMemoryDataSource
import name.paynd.android.clientlist.di.vm.VMBindsModule
import name.paynd.android.clientlist.ui.add.DateFragment
import name.paynd.android.clientlist.ui.add.PhotoFragment
import name.paynd.android.clientlist.ui.add.WeightFragment
import name.paynd.android.clientlist.ui.list.ClientsListFragment
import name.paynd.android.clientlist.ui.MainActivity

@[AppScope Component(
    modules = [
        AppModule::class,
        VMBindsModule::class
    ]
)]
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(clientsListFragment: ClientsListFragment)
    fun inject(weightFragment: WeightFragment)
    fun inject(photoFragment: PhotoFragment)
    fun inject(dateFragment: DateFragment)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}

@Module
class AppModule {
    @Provides
    @AppScope
    fun provideDataSource(): DataSource = InMemoryDataSource()
}