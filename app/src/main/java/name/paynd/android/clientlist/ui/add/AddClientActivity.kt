package name.paynd.android.clientlist.ui.add

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import by.kirich1409.viewbindingdelegate.viewBinding
import name.paynd.android.clientlist.App
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.databinding.ActivityAddClientBinding
import name.paynd.android.clientlist.di.vm.VMFactory
import javax.inject.Inject

class AddClientActivity : AppCompatActivity(R.layout.activity_add_client), AddClientNavigator {
    @Inject
    lateinit var vmFactory: VMFactory

    private val viewBinding: ActivityAddClientBinding by viewBinding(ActivityAddClientBinding::bind)
    private val viewModel: AddClientViewModel by viewModels { vmFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent?.inject(this)
        super.onCreate(savedInstanceState)

        with(viewBinding) {
            next.setOnClickListener {
//                throttleFirst<Unit>(lifecycleScope) {
                navigateNext()
//                }
            }
            back.setOnClickListener {
                navigateBack()
            }
        }
    }

    override fun navigateNext() {
        if (viewModel.next()) {
            when (viewModel.state) {
                State.WEIGHT -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<DateFragment>(R.id.container)
                        addToBackStack(DateFragment.TAG)
                    }
                }
                State.DATE -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        replace<PhotoFragment>(R.id.container)
                        addToBackStack(PhotoFragment.TAG)
                    }
                }
                State.PHOTO -> {
                    viewModel.saveClient()
                    finish()
                }
            }
        }
    }

    override fun navigateBack() {
        viewModel.back()
        when (viewModel.state) {
            State.PHOTO, State.DATE -> {
                supportFragmentManager.popBackStack()
            }
            State.WEIGHT -> {
                finish()
            }
        }

    }

    companion object {
        fun newStartIntent(context: Context) = Intent(context, AddClientActivity::class.java)
    }

}