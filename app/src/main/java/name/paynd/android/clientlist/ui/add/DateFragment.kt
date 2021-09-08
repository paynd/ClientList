package name.paynd.android.clientlist.ui.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.databinding.FragmentDateBinding


class DateFragment : Fragment(R.layout.fragment_date) {

    private val viewBinding by viewBinding(FragmentDateBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(viewBinding) {
            next.setOnClickListener {
                Navigation.findNavController(viewBinding.root)
                    .navigate(R.id.action_dateFragment_to_photoFragment)
            }
            back.setOnClickListener {
                Navigation.findNavController(viewBinding.root).popBackStack()
            }
        }
    }
}