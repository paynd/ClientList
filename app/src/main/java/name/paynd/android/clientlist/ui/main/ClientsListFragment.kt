package name.paynd.android.clientlist.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import name.paynd.android.clientlist.App
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.databinding.FragmentClientsListBinding
import name.paynd.android.clientlist.di.vm.VMFactory
import javax.inject.Inject


class ClientsListFragment : Fragment(R.layout.fragment_clients_list) {
    @Inject
    lateinit var vmFactory: VMFactory

    private val viewModel: ClientListViewModel by viewModels { vmFactory }

    private val viewBinding by viewBinding(FragmentClientsListBinding::bind)
    private var adapter: ClientAdapter? = null

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent?.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ClientAdapter {
            Navigation.findNavController(viewBinding.root)
                .navigate(R.id.action_clientsListFragment_to_weightFragment)
        }


        with(viewBinding.list) {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        viewBinding.addClient.setOnClickListener {
            Navigation.findNavController(viewBinding.root)
                .navigate(R.id.action_clientsListFragment_to_weightFragment)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect { clientList ->
                    with(viewBinding) {
                        if (clientList.isEmpty()) {
                            tvNoClients.visibility = View.VISIBLE
                            list.visibility = View.INVISIBLE
                        } else {
                            tvNoClients.visibility = View.INVISIBLE
                            list.visibility = View.VISIBLE
                            adapter?.submitList(clientList)
                        }
                    }
                }
            }
        }
    }
}