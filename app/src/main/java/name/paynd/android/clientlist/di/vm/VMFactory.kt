package name.paynd.android.clientlist.di.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import name.paynd.android.clientlist.di.AppScope
import javax.inject.Inject
import javax.inject.Provider

/**
 * See for [details](https://betterprogramming.pub/injecting-android-viewmodels-with-dagger2-in-clean-architecture-744c1fe81530)
 */
@AppScope
class VMFactory @Inject constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    @SuppressWarnings("Unchecked")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]

        if (creator == null) {
            for (entry in creators) {
                if (modelClass.isAssignableFrom(entry.key)) {
                    creator = entry.value
                    break
                }
            }
        }

        if (creator == null) throw IllegalArgumentException("Unknown model class $modelClass")

        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}