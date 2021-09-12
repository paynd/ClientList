package name.paynd.android.clientlist.ui.add

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import name.paynd.android.clientlist.App
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.data.toInt
import name.paynd.android.clientlist.data.toUnit
import name.paynd.android.clientlist.databinding.FragmentWeightBinding
import name.paynd.android.clientlist.di.vm.VMFactory
import name.paynd.android.clientlist.util.Mode
import javax.inject.Inject

class WeightFragment : Fragment(R.layout.fragment_weight) {
    private val viewBinding by viewBinding(FragmentWeightBinding::bind)

    @Inject
    lateinit var vmFactory: VMFactory
    private val viewModel: AddClientViewModel by viewModels { vmFactory }

    private val args: WeightFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handleArgs()
        with(viewBinding) {
            weightUnit.apply {
                minValue = 0
                maxValue = 1
                displayedValues = arrayOf(
                    context.resources.getString(R.string.lb),
                    context.resources.getString(R.string.kg)
                )
            }

            weightValue.apply {
                maxValue = 1000
                minValue = 1
                value = 75
            }

            next.setOnClickListener {
                viewModel.updateWeight(weightValue.value, weightUnit.value.toUnit())
                Navigation.findNavController(viewBinding.root)
                    .navigate(R.id.action_weightFragment_to_dateFragment)
            }
            back.setOnClickListener {
                Navigation.findNavController(viewBinding.root).popBackStack()
            }
        }
    }

    private fun handleArgs() {
        when (args.actionType) {
            Mode.EDIT -> {
                viewModel.mode = Mode.EDIT
                viewModel.loadClient(args.clientId)
            }
            Mode.CREATE -> {
                viewModel.mode = Mode.CREATE
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.currentClient?.let {
            it.weight?.let { value -> viewBinding.weightValue.value = value }
            it.weightUnit?.let { unit -> viewBinding.weightUnit.value = unit.toInt() }
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent?.inject(this)
        super.onAttach(context)
    }
}