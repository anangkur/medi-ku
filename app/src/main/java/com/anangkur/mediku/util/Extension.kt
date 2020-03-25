package com.anangkur.mediku.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseSpinnerListener
import com.anangkur.mediku.data.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.esafirm.imagepicker.features.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun Activity.showSnackbarLong(message: String){
    Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
}

fun Activity.showSnackbarShort(message: String){
    Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
}

fun Context.showToastShort(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.setImageUrl(url: String){
    Log.d("SET_IMAGE_URL", url)
    Glide.with(this)
        .load(url)
        .apply(RequestOptions().error(R.color.gray))
        .apply(RequestOptions().placeholder(createCircularProgressDrawable(this.context)))
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun ImageView.setImageUrlDarkBg(url: String){
    Log.d("SET_IMAGE_URL", url)
    Glide.with(this)
        .load(url)
        .apply(RequestOptions().error(R.color.gray))
        .apply(RequestOptions().placeholder(createCircularProgressDrawableLight(this.context)))
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun hideSoftKeyboard(activity: Activity) {
    val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(
        activity.currentFocus!!.windowToken, 0
    )
}

fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(viewModelClass)

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProviders.of(this, ViewModelFactory.getInstance(this.requireContext())).get(viewModelClass)

fun RecyclerView.setupRecyclerViewGridEndlessScroll(context: Context, spanCount: Int, adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, progressView: Int, itemView: Int){
    val mLayoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
    mLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return when (adapter.getItemViewType(position)){
                progressView -> 1
                itemView -> spanCount
                else -> -1
            }
        }
    }
    this.apply {
        itemAnimator = DefaultItemAnimator()
        layoutManager = mLayoutManager
    }
}

fun RecyclerView.setupRecyclerViewGrid(context: Context, spanCount: Int){
    this.apply {
        itemAnimator = DefaultItemAnimator()
        layoutManager = GridLayoutManager(context, spanCount, GridLayoutManager.VERTICAL, false)
    }
}

fun RecyclerView.setupRecyclerViewLinear(context: Context, orientation: Int){
    this.apply {
        itemAnimator = DefaultItemAnimator()
        layoutManager = LinearLayoutManager(context, orientation, false)
    }
}

fun TabLayout.disableClickTablayout(){
    for (i in 0 until this.tabCount){
        (this.getChildAt(0) as ViewGroup).getChildAt(i).isEnabled = false
    }
}

fun createCircularProgressDrawable(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 4f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun createCircularProgressDrawableLight(context: Context): CircularProgressDrawable {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.setColorSchemeColors(Color.WHITE)
    circularProgressDrawable.strokeWidth = 4f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.enable(){
    this.isEnabled = true
}

fun View.disable(){
    this.isEnabled = false
}

fun String.validateEmail(): Boolean{
    val emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    return emailPattern.matcher(this).matches()
}

fun String.validatePassword(): Boolean{
    return this.length >= 6
}

fun String.validateName(): Boolean{
    return this.isNotEmpty()
}

fun String.validatePasswordConfirm(password: String): Boolean{
    return this == password
}

fun Double.currencyFormatToRupiah(): String {
    val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp. "
    formatRp.monetaryDecimalSeparator = '.'
    formatRp.groupingSeparator = ','

    kursIndonesia.decimalFormatSymbols = formatRp
    return kursIndonesia.format(this)
}

fun SwipeRefreshLayout.startLoading(){
    this.isRefreshing = true
}

fun SwipeRefreshLayout.stopLoading(){
    this.isRefreshing = false
}

fun Activity.cropImage(data: Intent?, fixAspectRatio: Boolean) {
    val image = ImagePicker.getFirstImageOrNull(data)
    CropImage.activity(Uri.fromFile(File(image.path)))
        .setGuidelines(CropImageView.Guidelines.OFF)
        .setFixAspectRatio(fixAspectRatio)
        .start(this)
}

fun Activity.handleImageCropperResult(data: Intent?, resultCode: Int, listener: CompressImageListener) {
    val image = CropImage.getActivityResult(data)
    if (resultCode == Activity.RESULT_OK) {
        compressFileImage(File(image.uri.path?:""), listener)
    } else {
        this.showToastShort(image.error.message ?: "")
    }
}

fun compressFileImage(imageFile: File, listener: CompressImageListener) {
    CoroutineScope(Dispatchers.Main).launch{
        try {
            listener.progress(true)
            listener.success(
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    suspendCompressFileImage(imageFile)
                }
            )
        } catch (e: Exception) {
            listener.error(e.message?:"")
        } finally {
            listener.progress(false)
        }
    }
}

suspend fun suspendCompressFileImage(imageFile: File): File {
    Log.d("IMAGE_COMPRESS", "IMAGE_COMPRESS: size: ${imageFile.fileSizeInKB}, sampleSize: ${Const.SAMPLE_SIZE}, quality: ${Const.COMPRESS_QUALITY}")
    return if (imageFile.fileSizeInKB > Const.MAX_IMAGE_SIZE) {
        val options = BitmapFactory.Options()
        options.inSampleSize = Const.SAMPLE_SIZE
        val bitmap = BitmapFactory.decodeFile(imageFile.path, options)
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            Const.COMPRESS_QUALITY, FileOutputStream(imageFile)
        )
        suspendCompressFileImage(imageFile)
    } else {
        imageFile
    }
}

// Get length of file in bytes
val File.fileSizeInBytes: Long
    get() = length()

// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
val File.fileSizeInKB: Long
    get() = fileSizeInBytes / 1024

// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
val File.fileSizeInMB: Long
    get() = fileSizeInKB / 1024

// Get length of file in bytes
val ByteArray.fileSizeInBytes: Long
    get() = size.toLong()

// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
val ByteArray.fileSizeInKB: Long
    get() = fileSizeInBytes / 1024

// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
val ByteArray.fileSizeInMB: Long
    get() = fileSizeInKB / 1024

fun Context.showDialogImagePicker(listener: DialogImagePickerActionListener) {
    val alertDialog = AlertDialog.Builder(this).create()
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_image_picker, null)

    dialogView.apply {
        val btnCamera = findViewById<LinearLayout>(R.id.btn_camera)
        val btnGallery = findViewById<LinearLayout>(R.id.btn_gallery)
        btnCamera.setOnClickListener {
            listener.onClickCamera()
            alertDialog.dismiss()
        }
        btnGallery.setOnClickListener {
            listener.onClickGallery()
            alertDialog.dismiss()
        }
    }

    alertDialog.setCancelable(true)
    alertDialog.setView(dialogView)
    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    alertDialog.show()
}

fun TextInputLayout.setErrorMessage(errorMessage: String){
    this.isErrorEnabled = true
    this.error = errorMessage
}

fun TextInputLayout.setNotError(){
    this.isErrorEnabled = false
}

fun getCurrentTimeStamp(): String? {
    return try {
        val dateFormat = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT, Locale.US)
        dateFormat.format(Date())
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        null
    }
}

fun Spinner.setupSpinner(data: List<String>, listener: BaseSpinnerListener){
    val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, data)
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.apply {
        adapter = arrayAdapter
        onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                listener.onItemSelected(parent, view, position, id)
            }
        }
    }
}

fun getTime(): Date {
    val c = Calendar.getInstance()
    c.timeZone = TimeZone.getTimeZone("GMT+07:00")
    return c.time
}

fun String.formatDate(): String{

    val dateNow = getTime()

    val yearFormat = SimpleDateFormat("yyyy")
    val monthFormat = SimpleDateFormat("MM")
    val dayFormat = SimpleDateFormat("dd")
    val timeFormat = SimpleDateFormat(Const.TIME_GENERAL_HH_MM, Locale.US)

    val generalFormat = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT, Locale.US)

    val year = yearFormat.format(generalFormat.parse(this))
    val month = monthFormat.format(generalFormat.parse(this))
    val day = dayFormat.format(generalFormat.parse(this))
    val time = timeFormat.format(generalFormat.parse(this))

    val yearNow = yearFormat.format(dateNow)
    val monthNow = monthFormat.format(dateNow)
    val dayNow = dayFormat.format(dateNow)

    var timeReturn: String

    if (yearNow == year) {
        timeReturn = if (monthNow == month) {
            when {
                dayNow == day -> "Today, $time"
                Integer.parseInt(dayNow) - Integer.parseInt(day!!) == 1 -> "Yesterday, $time"
                else -> {
                    val monthFormatDisplay = SimpleDateFormat(Const.DAY_NAME_DATE_MONTH_NAME, Locale.US)
                    monthFormatDisplay.format(generalFormat.parse(this)) + " " + time
                }
            }
        } else {
            val monthFormatDisplay = SimpleDateFormat(Const.DAY_NAME_DATE_MONTH_NAME, Locale.US)
            monthFormatDisplay.format(generalFormat.parse(this)) + " " + time
        }
    } else {
        val yearFormatDisplay = SimpleDateFormat(Const.DAY_FULL_WITH_DATE_LOCALE, Locale.US)
        timeReturn = yearFormatDisplay.format(generalFormat.parse(this)) + " " + time
    }
    return timeReturn
}