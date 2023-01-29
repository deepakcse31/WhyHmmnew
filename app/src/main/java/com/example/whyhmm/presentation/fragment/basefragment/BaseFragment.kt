package com.example.whyhmm.presentation.fragment.basefragment

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.provider.MediaStore
import android.provider.SyncStateContract
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.example.whyhmm.BuildConfig
import com.example.whyhmm.databinding.BottomSheetLayoutBinding
import com.example.whyhmm.domain.utils.*
import com.example.whyhmm.presentation.MainActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import java.io.*
import java.lang.reflect.ParameterizedType
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

abstract class BaseFragment <VB : ViewBinding, VM : ViewModel> : Fragment() {

    protected lateinit var binding: VB private set
    protected lateinit var viewModel: VM private set
    protected lateinit var window: Window
    lateinit var asm_id: String
    lateinit var userType: String
    lateinit var token: String
    lateinit var walk_status : String
    lateinit var onboardStatus: String
    lateinit var projectStatus: String
    lateinit var pickimageFilepath : String
    lateinit var captureImagePath : String
    var FILENAME = "yyyy-MM-dd-HH-mm-ss"
    lateinit var file : File
    val PHOTO_EXTENSION = ".jpeg"
    private var receivedUriKey: Uri? = null
    lateinit var pickImages : ActivityResultLauncher<String>
    lateinit var pickPdf : ActivityResultLauncher<String>
    lateinit var capturePicture : ActivityResultLauncher<Uri>

    private var transferUtility: TransferUtility? = null
    private lateinit var bottomDialogBinding : BottomSheetLayoutBinding
    private var s3: AmazonS3? = null
    var AWSfilepath : MutableLiveData<String> = MutableLiveData()


    private val type = (javaClass.genericSuperclass as ParameterizedType)
    private val classVB = type.actualTypeArguments[0] as Class<VB>
    private val classVM = type.actualTypeArguments[1] as Class<VM>

    @Inject lateinit var sessionManager: SessionManager
    @Inject lateinit var progress: CShowProgress


    private var _binding : ViewBinding? = null
        get() = binding

    private val inflateMethod = classVB.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )

    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        permissions.entries.forEach {
            Log.d("DEBUG", "${it.key} = ${it.value}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupcontracts()
        PermissionCheck()
    }

    private fun PermissionCheck() {
        requestMultiplePermissions.launch(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = inflateMethod.invoke(null, inflater, container, false) as VB
        viewModel = createViewModelLazy(classVM.kotlin, { viewModelStore }).value
        window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

//        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
//        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
//        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
//        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)

        exitTransition = MaterialFadeThrough()
        reenterTransition = MaterialFadeThrough()
        enterTransition = MaterialFadeThrough()
        returnTransition = MaterialFadeThrough()

        asm_id = sessionManager.getStringData(Constants.ASM_ID).toString()
        userType = sessionManager.getStringData(Constants.USER_TYPE).toString()
        token = sessionManager.getStringData(Constants.TOKEN).toString()
        onboardStatus = sessionManager.getStringData(Constants.ONBOARD_STATUS).toString()
        projectStatus =  sessionManager.getStringData(Constants.PROJECT_STATUS).toString()

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //All Custom Extension Function From here

    private val clickTag = "__click__"
    fun View.singleClickListener(debounceTime: Long = 1200L, action: () -> Unit) {
        this.setOnClickListener(object : View.OnClickListener {
            private var lastClickTime: Long = 0
            override fun onClick(v: View) {
                val timeNow = SystemClock.elapsedRealtime()
                val elapsedTimeSinceLastClick = timeNow - lastClickTime
                showlog( """
                        DebounceTime: $debounceTime
                        Time Elapsed: $elapsedTimeSinceLastClick
                        Is within debounce time: ${elapsedTimeSinceLastClick < debounceTime}
                    """.trimIndent())

                if (elapsedTimeSinceLastClick < debounceTime) {
                    showlog("Double click shielded")
                    return
                } else {
                    showlog("Click happened")
                    action()
                }
                lastClickTime = SystemClock.elapsedRealtime()
            }
        })
    }



    fun showtoast(str : String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }


    fun View.background(drawable : Int){
        this.background = ContextCompat.getDrawable(requireContext(), drawable)
    }

    fun View.Color(color : Int){
        this.backgroundTintList = ContextCompat.getColorStateList(requireContext(), color);
    }


    fun TextView.Color(color : Int){
        this.setTextColor(ContextCompat.getColor(requireContext(), color))
    }

    fun MaterialButton.Color(color : Int){
        this.backgroundTintList = ContextCompat.getColorStateList(requireContext(), color);
    }

    fun getPdfFromStorage(){
        lifecycleScope.launchWhenStarted {
            pickPdf.launch("application/pdf")
        }
    }

    private fun getimagefromgallery() {
        lifecycleScope.launchWhenStarted {
            pickImages.launch("image/*")
        }
    }

    fun takeImage() {
        lifecycleScope.launchWhenStarted {
            try {
                getTmpFileUri().let { uri ->
                    receivedUriKey = uri
                    capturePicture.launch(uri)
                }
            }catch (e: java.lang.Exception){
                Log.d("LogTag", e.toString())
            }
        }
    }

    fun showprogress(){
        if(progress.mDialog?.isShowing == true){
            progress.hideProgress()
        }else{
            progress.showProgress(requireContext())
        }
    }

    fun hideProgress(){
        progress.hideProgress()
    }

    fun showShimmer(lay: ShimmerFrameLayout){
        try {
            lay.startShimmer()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun stopeshimmer(lay :ShimmerFrameLayout){
        try {
            lay.stopShimmer()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun showImagePickerDialog(){
        bottomDialogBinding = BottomSheetLayoutBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(bottomDialogBinding.root)
        dialog.show()

        bottomDialogBinding.apply {
            cameralay.setOnClickListener{
                dialog.dismiss()
                setupfileupload(1)
            }
            gellerylay.setOnClickListener{
                dialog.dismiss()
                setupfileupload(2)
            }
        }
    }


    fun setupfileupload(flag : Int) {
        when (flag) {
            1 -> {
                takeImage()
            }
            2 -> {
                getimagefromgallery()
            }
        }
    }

    fun logout(){
        sessionManager.removeAll()
        sessionManager.apply {
            putStringData(Constants.WALK_STATUS, "NO")
        }
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile(
            SimpleDateFormat(FILENAME, Locale.ENGLISH).format(System.currentTimeMillis()),
            PHOTO_EXTENSION, requireContext().cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(requireContext(), "${BuildConfig.APPLICATION_ID}.provider", tmpFile)
    }

    private fun compressBitmap(bitmap:Bitmap):Bitmap{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArray = stream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    @Throws(IOException::class)
    private fun compressImage(filepath: String): String? {
        var filePath: String? = null
        val f = File(requireContext().cacheDir, UUID.randomUUID().toString() + ".jpeg")
        f.createNewFile()
        val bmp = BitmapFactory.decodeFile(filepath)
        val bos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitmapdata = bos.toByteArray()
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(f)
            filePath = f.path
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos!!.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return filePath
    }

    @Throws(IOException::class)
    fun createFilePath(bitmap1: Bitmap): String? {
        var filePath: String? = null
        val f = File(requireContext().cacheDir, UUID.randomUUID().toString() + ".jpeg")
        f.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos)
        val bitmapdata = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(f)
            filePath = f.path
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos!!.write(bitmapdata)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return filePath
    }

    private fun setupcontracts(){
        pickImages = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                try {
                    uri.let { it ->
                        pickimageFilepath = RealPathUtil.getRealPath(requireContext(), it)?.let {
                                it1 -> compressImage(it1)
                        }.toString()
                        uploadImage(pickimageFilepath.toString())
                    }
                } catch (e: java.lang.Exception) {
                    Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No Image Selected!", Toast.LENGTH_SHORT).show()
            }
        }

        pickPdf = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                try {
                    uri.let {
                        file = Fileutil.from(requireContext(), it)
                        uploadImage(file.path)
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "No file Selected", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No file Selected!", Toast.LENGTH_SHORT).show()
            }
        }

        capturePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if(it) {
                try{
                    receivedUriKey.let { uri ->
                        receivedUriKey = uri
                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, receivedUriKey!!))
                        } else {
                            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, receivedUriKey)
                        }
                        val compressdbitmap = compressBitmap(bitmap)
                        captureImagePath = createFilePath(compressdbitmap).toString()
                        uploadImage(captureImagePath.toString())
                    }
                }catch (e: java.lang.Exception){
                    Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(), "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun uploadImage(filePath: String) {
        val file = File(filePath)
        val filename  = file.name.removeWhitespaces()
        Log.e("filename","filename"+filename)
        uploadToAwsBucket(file, filename)
    }

    fun uploadToAwsBucket(finalFile: File, imgFilename: String) {
        progress.showProgress(requireContext())
        credentialsProvider()
        setTransferUtility()
        val transferObsserver: TransferObserver? = transferUtility?.upload(Constants.S3_BUCKET_ID, imgFilename, finalFile)
        if (transferObsserver != null) {
            transferObserverListener(transferObsserver)
        }
    }

    fun setAmazonS3Client(credentialsProvider: CognitoCachingCredentialsProvider?) {
        s3 = AmazonS3Client(credentialsProvider, Region.getRegion(Regions.AP_SOUTH_1))
    }

    fun setTransferUtility() {
        transferUtility = TransferUtility(s3, requireContext())
    }

    fun credentialsProvider() {
        val credentialsProvider = CognitoCachingCredentialsProvider(
            requireContext(),
            Constants.S3_IDENTITY_POOL_ID_URL,  // Identity Pool ID
            Regions.AP_SOUTH_1 // Region
        )
        setAmazonS3Client(credentialsProvider)
    }

    private fun transferObserverListener(transferObserver: TransferObserver) {
        Log.e("Working","working")
        transferObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                println("onStateChanged")
                val state1 = state.toString()
                if (state == TransferState.CANCELED || state == TransferState.FAILED || state == TransferState.WAITING_FOR_NETWORK) {
                    Toast.makeText(
                        requireContext(),
                        "upload failed waiting for connected network",
                        Toast.LENGTH_SHORT
                    ).show()
                    progress.hideProgress()
                }
                if (state1 == "COMPLETED") {
                    val filepathfromaws = Constants.BASE_AWS_FILE_URL + transferObserver.key
                    try {
                        progress.hideProgress()
                        AWSfilepath.value = filepathfromaws
                        progress.hideProgress()
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                } else if (state == TransferState.IN_PROGRESS) {
                    //documentsBinding.progress.setVisibility(View.VISIBLE);
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                val percentage = (bytesCurrent * 100 / bytesTotal).toInt()
               Log.d("LogTag", "file uploading  percentage$percentage")
            }

            override fun onError(id: Int, ex: java.lang.Exception) {
                Log.e("Deepak","Deepak"+ex.toString())
                progress.hideProgress()
                ex.printStackTrace()
                Toast.makeText(requireContext(), "" + "Unable to save the file please try again", Toast.LENGTH_SHORT).show()
                Log.d("LogTag", "file not uploaded ", ex)
            }
        })
    }


}