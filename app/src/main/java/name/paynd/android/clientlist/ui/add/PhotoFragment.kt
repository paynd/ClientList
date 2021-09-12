package name.paynd.android.clientlist.ui.add

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.features.*
import com.esafirm.imagepicker.model.Image
import name.paynd.android.clientlist.App
import name.paynd.android.clientlist.R
import name.paynd.android.clientlist.databinding.FragmentPhotoBinding
import name.paynd.android.clientlist.di.vm.VMFactory
import javax.inject.Inject

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    @Inject
    lateinit var vmFactory: VMFactory
    private val viewModel: AddClientViewModel by viewModels { vmFactory }

    private val viewBinding by viewBinding(FragmentPhotoBinding::bind)

    private var launcher: ImagePickerLauncher? = null
    private val config = ImagePickerConfig {
        mode = ImagePickerMode.SINGLE // default is multi image mode
        language = "en" // Set image picker language
        returnMode = ReturnMode.NONE
        arrowColor = Color.WHITE // set toolbar arrow up color
        folderTitle = "Folder" // folder selection title
        imageTitle = "Tap to select" // image selection title
        doneButtonText = "DONE" // done button text
        limit = 1 // max images can be selected (99 by default)
        isShowCamera = true // show camera or not (true by default)
        savePath =
            ImagePickerSavePath("Camera") // captured image directory name ("Camera" folder by default)
        savePath = ImagePickerSavePath(
            Environment.getExternalStorageDirectory().path,
            isRelative = false
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.currentClient?.let {
            it.uri?.let { uri ->
                updatePhoto(uri)
            }
        }
    }

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent?.inject(this)
        super.onAttach(context)

        launcher = registerImagePicker { result: List<Image> ->
            result.forEachIndexed { index, image ->
                if (index == 0) { // first one only
                    viewModel.updatePhoto(image.uri)
                    updatePhoto(image.uri)
                }
            }
        }
    }

    private fun updatePhoto(uri: Uri) {
        Glide
            .with(this)
            .load(uri)
            .centerCrop()
            .placeholder(R.drawable.img)
            .into(viewBinding.photoPreview)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewBinding) {
            takePhoto.setOnClickListener {
                pickPhoto()
            }
            photoPreview.setOnClickListener {
                pickPhoto()
            }

            with(bottomBar) {
                next.apply {
                    setOnClickListener {
                        when (val result = viewModel.checkPhoto()) {
                            is ValidationResult.Success -> {
                                viewModel.checkAndSave()
                                navigateToNext()
                            }
                            is ValidationResult.Error -> showError(result)
                        }
                    }
                    text = resources.getText(R.string.done)
                }
                back.setOnClickListener {
                    Navigation.findNavController(viewBinding.root).popBackStack()
                }
                indicator3.setBackgroundResource(R.drawable.dot_selected)
            }
        }
    }

    private fun navigateToNext() {
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

    private fun showError(err: ValidationResult.Error) {
        Toast.makeText(
            context,
            err.message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun pickPhoto() {
        launcher?.launch(config)
    }
}