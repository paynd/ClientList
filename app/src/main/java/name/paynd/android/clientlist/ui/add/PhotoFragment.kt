package name.paynd.android.clientlist.ui.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.databinding.FragmentPhotoBinding

class PhotoFragment : Fragment(R.layout.fragment_photo) {

    private val viewBinding by viewBinding(FragmentPhotoBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewBinding) {
            next.setOnClickListener {
                Navigation.findNavController(viewBinding.root)
                    .navigate(
                        R.id.action_photoFragment_to_clientsListFragment,
                        null,
                        NavOptions.Builder().setPopUpTo(
                            R.id.clientsListFragment,
                            true
                        ).build()
                    )

            }
            back.setOnClickListener {
                Navigation.findNavController(viewBinding.root).popBackStack()
            }
        }
    }
}