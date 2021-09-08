package name.paynd.android.clientlist.ui.main

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import name.paynd.android.clientlist.App
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.databinding.ActivityMainBinding
import name.paynd.android.clientlist.di.vm.VMFactory
import name.paynd.android.clientlist.ui.add.AddClientActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    @Inject
    lateinit var vmFactory: VMFactory

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: ClientListViewModel by viewModels { vmFactory }

    private var adapter: ClientAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent?.inject(this)
        super.onCreate(savedInstanceState)

        adapter = ClientAdapter {
            // edit client
        }

        with(viewBinding.list) {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        viewBinding.addClient.setOnClickListener {
            startActivity(AddClientActivity.newStartIntent(this))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect { clientList ->
                    with(viewBinding) {
                        if (clientList.isEmpty()) {
                            tvNoClients.visibility = VISIBLE
                            list.visibility = INVISIBLE
                        } else {
                            tvNoClients.visibility = INVISIBLE
                            list.visibility = VISIBLE
                            adapter?.submitList(clientList)
                        }
                    }
                }
            }
        }
    }
}