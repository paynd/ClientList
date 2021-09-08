package name.paynd.android.clientlist.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import name.paynd.android.clientlist.ui.main.MainActivity
import name.paynd.android.clientlist.data.DataSource
import name.paynd.android.clientlist.data.InMemoryDataSource
import name.paynd.android.clientlist.di.vm.VMBindsModule
import name.paynd.android.clientlist.ui.add.AddClientActivity

@[AppScope Component(
    modules = [
        AppModule::class,
        VMBindsModule::class
    ]
)]
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(addClientActivity: AddClientActivity)

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