package name.paynd.android.clientlist.ui.add

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import name.paynd.android.clientlist.App
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.databinding.FragmentDateBinding
import name.paynd.android.clientlist.di.vm.VMFactory
import name.paynd.android.clientlist.ui.main.FatViewModel
import name.paynd.android.clientlist.util.getFormattedString
import javax.inject.Inject


class DateFragment : Fragment(R.layout.fragment_date) {
    @Inject
    lateinit var vmFactory: VMFactory

    private val viewBinding by viewBinding(FragmentDateBinding::bind)
    private val viewModel: FatViewModel by viewModels { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(viewBinding) {
            datePicker.setOnDateChangedListener { _, year, month, day ->
                val formatted = getFormattedString(year, month, day)
                Log.d("####", formatted)
                viewModel.updateDOB(formatted)
            }

            next.setOnClickListener {
                Navigation.findNavController(viewBinding.root)
                    .navigate(R.id.action_dateFragment_to_photoFragment)
            }
            back.setOnClickListener {
                Navigation.findNavController(viewBinding.root).popBackStack()
            }
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent?.inject(this)
        super.onAttach(context)
    }
}