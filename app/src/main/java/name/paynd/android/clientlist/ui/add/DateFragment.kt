package name.paynd.android.clientlist.ui.add

import android.content.Context
import android.os.Bundle
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
import name.paynd.android.clientlist.util.toDOB
import javax.inject.Inject


class DateFragment : Fragment(R.layout.fragment_date) {
    @Inject
    lateinit var vmFactory: VMFactory

    private val viewBinding by viewBinding(FragmentDateBinding::bind)
    private val viewModel: FatViewModel by viewModels { vmFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(viewBinding) {
            datePicker.setOnDateChangedListener { _, year, month, day ->
                viewModel.updateDOB(getFormattedString(year, month, day))
            }

            next.setOnClickListener {
                // probably we need more validation here
                with(datePicker) {
                    viewModel.updateDOB(getFormattedString(year, month, dayOfMonth))
                }

                Navigation.findNavController(viewBinding.root)
                    .navigate(R.id.action_dateFragment_to_photoFragment)
            }
            back.setOnClickListener {
                Navigation.findNavController(viewBinding.root).popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //yea, it's ugly
        viewModel.currentClient?.let {
            it.dob?.let {
                toDOB(it)?.also {
                    with(it) {
                        viewBinding.datePicker.updateDate(year, month, month)
                    }
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent?.inject(this)
        super.onAttach(context)
    }
}