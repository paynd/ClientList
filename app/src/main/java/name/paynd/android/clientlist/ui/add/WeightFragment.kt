package name.paynd.android.clientlist.ui.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.databinding.FragmentWeightBinding

class WeightFragment : Fragment(R.layout.fragment_weight) {

    private val viewBinding  by viewBinding(FragmentWeightBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(viewBinding){
            next.setOnClickListener {
                Navigation.findNavController(viewBinding.root).navigate(R.id.action_weightFragment_to_dateFragment)
            }
            back.setOnClickListener {
                Navigation.findNavController(viewBinding.root).popBackStack()
            }
        }
    }
}