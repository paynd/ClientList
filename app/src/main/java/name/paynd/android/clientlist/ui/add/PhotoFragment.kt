package name.paynd.android.clientlist.ui.add

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
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
import name.paynd.android.clientlist.ui.main.FatViewModel
import javax.inject.Inject

class PhotoFragment : Fragment(R.layout.fragment_photo) {
    @Inject
    lateinit var vmFactory: VMFactory
    private val viewModel: FatViewModel by viewModels { vmFactory }

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

    override fun onAttach(context: Context) {
        (activity?.application as App).appComponent?.inject(this)
        super.onAttach(context)
        launcher = registerImagePicker { result: List<Image> ->
            result.forEachIndexed { index, image ->
                if (index == 0) { // first one only
                    Log.d("####", "${image.id} \n${image.name} \n${image.uri} \n${image.path}")

                    viewModel.updatePhoto(image.uri)

                    Glide
                        .with(this)
                        .load(image.uri)
                        .centerCrop()
                        .placeholder(R.drawable.img)
                        .into(viewBinding.photo)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(viewBinding) {
            takePhoto.setOnClickListener {
                pickPhoto()
            }
            next.setOnClickListener {
                if (viewModel.checkAndSave()) {
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
            }
            back.setOnClickListener {
                Navigation.findNavController(viewBinding.root).popBackStack()
            }
        }
    }

    private fun pickPhoto() {
        launcher?.launch(config)
    }
}