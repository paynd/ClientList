package name.paynd.android.clientlist.ui.list

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
import name.paynd.android.clientlist.data.Client
import name.paynd.android.clientlist.databinding.FragmentClientsListBinding
import name.paynd.android.clientlist.di.vm.VMFactory
import name.paynd.android.clientlist.util.Mode
import javax.inject.Inject

class ClientsListFragment : Fragment(R.layout.fragment_clients_list) {
    @Inject
    lateinit var vmFactory: VMFactory

    private val viewModel: ClientListViewModel by viewModels { vmFactory }
    private val viewBinding by viewBinding(FragmentClientsListBinding::bind)
    private var clientAdapter: ClientAdapter? = null

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent?.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clientAdapter = ClientAdapter {
            navigateToEdit(it.id)
        }

        with(viewBinding) {
            list.layoutManager = LinearLayoutManager(context)
            list.adapter = clientAdapter
        }

        viewBinding.addClient.setOnClickListener {
            navigateToCreate()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.list.collect { clientList ->
                    updateUI(clientList)
                }
            }
        }
    }

    private fun navigateToCreate() {
        val action =
            ClientsListFragmentDirections.actionClientsListFragmentToWeightFragment().apply {
                actionType = Mode.CREATE
            }
        Navigation.findNavController(viewBinding.root).navigate(action)
    }

    private fun navigateToEdit(id: Long) {
        val action =
            ClientsListFragmentDirections.actionClientsListFragmentToWeightFragment().apply {
                actionType = Mode.EDIT
                clientId = id
            }
        Navigation.findNavController(viewBinding.root).navigate(action)
    }

    private fun updateUI(clientList: List<Client>) {
        with(viewBinding) {
            if (clientList.isEmpty()) {
                tvNoClients.visibility = View.VISIBLE
                list.visibility = View.INVISIBLE
            } else {
                clientAdapter?.submitList(clientList)
                tvNoClients.visibility = View.INVISIBLE
                list.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        clientAdapter = null
    }
}