package com.anangkur.mediku.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.telephony.TelephonyManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
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
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.covid19.Covid19Data
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.model.newCovid19.NewCovid19SummaryResponse
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
import kotlin.collections.ArrayList

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

fun Int.formatThousandNumber(): String{
    val decim = DecimalFormat("#,###.##")
    return decim.format(this)
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

fun String.getCountryCode(): String {
    var result = ""
    result = if (this == "US"){
        this
    }else{
        Locale.getISOCountries().find { Locale("", it).displayCountry == this }?:""
    }
    Log.d("getCountryCode", "$this: $result")
    return result
}

fun getUserCountry(context: Context): String? {
    try {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val simCountry = tm.simCountryIso
        if (simCountry != null && simCountry.length == 2) { // SIM country code is available
            return simCountry.toLowerCase(Locale.US)
        } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
            val networkCountry = tm.networkCountryIso
            if (networkCountry != null && networkCountry.length == 2) { // network country code is available
                return networkCountry.toLowerCase(Locale.US)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun String.convertCoutryCodeIsoToCountryName(): String{
    val loc = Locale("", this)
    return loc.displayCountry
}

fun getTime(): Date {
    val c = Calendar.getInstance()
    c.timeZone = TimeZone.getTimeZone("GMT+07:00")
    return c.time
}

fun getTimeYesterday(): Date {
    val c = Calendar.getInstance()
    c.add(Calendar.DATE, -1)
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
                dayNow == day -> "Today"
                Integer.parseInt(dayNow) - Integer.parseInt(day!!) == 1 -> "Yesterday"
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

fun Context.showPreviewImage(url: String){
    val nagDialog = Dialog(this, android.R.style.Theme_Translucent)
    nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    nagDialog.setCancelable(true)
    nagDialog.setContentView(R.layout.preview_image_popup)
    val imageView = nagDialog.findViewById<ImageView>(R.id.iv_preview)
    imageView.setImageUrl(url)
    nagDialog.findViewById<RelativeLayout>(R.id.rl_main).setOnClickListener {
        nagDialog.dismiss()
    }
    nagDialog.show()
}

fun List<NewCovid19DataCountry>.createCompleteData(): List<NewCovid19DataCountry>{
    val listNewCovid19DataCountry = this
    listNewCovid19DataCountry.forEach { newCovid19DataCountry ->
        newCovid19DataCountry.apply {
            id = "${this.country}_${this.date}_${this.status}"
        }
    }
    return listNewCovid19DataCountry
}

fun NewCovid19SummaryResponse.createCompleteData(): List<NewCovid19Summary>{
    val listNewCovid19Summary = this.countries
    val date = this.date
    listNewCovid19Summary?.forEach {newCovid19Summary ->
        newCovid19Summary.date = date
    }
    return listNewCovid19Summary?: listOf()
}

fun Covid19ApiResponse.extractAllData(): List<Covid19Data> {
    val listCovid19Data = ArrayList<Covid19Data>()

    // 1 - 10
    listCovid19Data.addAll(this.afghanistan.createListCovid19DataByCountry("Afghanistan"))
    listCovid19Data.addAll(this.albania.createListCovid19DataByCountry("Albania"))
    listCovid19Data.addAll(this.algeria.createListCovid19DataByCountry("Algeria"))
    listCovid19Data.addAll(this.andorra.createListCovid19DataByCountry("Andorra"))
    listCovid19Data.addAll(this.angola.createListCovid19DataByCountry("Angola"))
    listCovid19Data.addAll(this.antiguaAndBarbuda.createListCovid19DataByCountry("Antigua and Barbuda"))
    listCovid19Data.addAll(this.argentina.createListCovid19DataByCountry("Argentina"))
    listCovid19Data.addAll(this.armenia.createListCovid19DataByCountry("Armenia"))
    listCovid19Data.addAll(this.australia.createListCovid19DataByCountry("Australia"))
    listCovid19Data.addAll(this.austria.createListCovid19DataByCountry("Austria"))
    listCovid19Data.addAll(this.azerbaijan.createListCovid19DataByCountry("Azerbaijan"))

    // 10 - 20
    listCovid19Data.addAll(this.bahamas.createListCovid19DataByCountry("Bahamas"))
    listCovid19Data.addAll(this.bahrain.createListCovid19DataByCountry("Bahrain"))
    listCovid19Data.addAll(this.bangladesh.createListCovid19DataByCountry("Bangladesh"))
    listCovid19Data.addAll(this.barbados.createListCovid19DataByCountry("Barbados"))
    listCovid19Data.addAll(this.belarus.createListCovid19DataByCountry("Belarus"))
    listCovid19Data.addAll(this.belgium.createListCovid19DataByCountry("Belgium"))
    listCovid19Data.addAll(this.benin.createListCovid19DataByCountry("Benin"))
    listCovid19Data.addAll(this.bhutan.createListCovid19DataByCountry("Bhutan"))
    listCovid19Data.addAll(this.bolivia.createListCovid19DataByCountry("Bolivia"))
    listCovid19Data.addAll(this.bosniaAndHerzegovina.createListCovid19DataByCountry("Bosnia and Herzegovina"))

    // 20 - 30
    listCovid19Data.addAll(this.brazil.createListCovid19DataByCountry("Brazil"))
    listCovid19Data.addAll(this.brunei.createListCovid19DataByCountry("Brunei"))
    listCovid19Data.addAll(this.bulgaria.createListCovid19DataByCountry("Bulgaria"))
    listCovid19Data.addAll(this.burkinaFaso.createListCovid19DataByCountry("Burkina Faso"))
    listCovid19Data.addAll(this.caboVerde.createListCovid19DataByCountry("Cabo Verde"))
    listCovid19Data.addAll(this.cambodia.createListCovid19DataByCountry("Cambodia"))
    listCovid19Data.addAll(this.cameroon.createListCovid19DataByCountry("Cameroon"))
    listCovid19Data.addAll(this.canada.createListCovid19DataByCountry("Canada"))
    listCovid19Data.addAll(this.centralAfricanRepublic.createListCovid19DataByCountry("Central African Republic"))
    listCovid19Data.addAll(this.chad.createListCovid19DataByCountry("Chad"))

    // 30 - 40
    listCovid19Data.addAll(this.chile.createListCovid19DataByCountry("Chile"))
    listCovid19Data.addAll(this.china.createListCovid19DataByCountry("China"))
    listCovid19Data.addAll(this.colombia.createListCovid19DataByCountry("Colombia"))
    listCovid19Data.addAll(this.congoBrazzaville.createListCovid19DataByCountry("Congo (Brazzaville)"))
    listCovid19Data.addAll(this.congoKinshasa.createListCovid19DataByCountry("Congo (Kinshasa)"))
    listCovid19Data.addAll(this.costaRica.createListCovid19DataByCountry("Costa Rica"))
    listCovid19Data.addAll(this.coteDIvoire.createListCovid19DataByCountry("Cote d'Ivoire"))
    listCovid19Data.addAll(this.croatia.createListCovid19DataByCountry("Croatia"))
    listCovid19Data.addAll(this.diamondPrincess.createListCovid19DataByCountry("Diamond Princess"))
    listCovid19Data.addAll(this.cuba.createListCovid19DataByCountry("Cuba"))

    // 40 - 50
    listCovid19Data.addAll(this.cyprus.createListCovid19DataByCountry("Cyprus"))
    listCovid19Data.addAll(this.czechia.createListCovid19DataByCountry("Czechia"))
    listCovid19Data.addAll(this.denmark.createListCovid19DataByCountry("Denmark"))
    listCovid19Data.addAll(this.djibouti.createListCovid19DataByCountry("Djibouti"))
    listCovid19Data.addAll(this.dominicanRepublic.createListCovid19DataByCountry("Dominican Republic"))
    listCovid19Data.addAll(this.ecuador.createListCovid19DataByCountry("Ecuador"))
    listCovid19Data.addAll(this.egypt.createListCovid19DataByCountry("Egypt"))
    listCovid19Data.addAll(this.elSalvador.createListCovid19DataByCountry("El Salvador"))
    listCovid19Data.addAll(this.equatorialGuinea.createListCovid19DataByCountry("Equatorial Guinea"))
    listCovid19Data.addAll(this.eritrea.createListCovid19DataByCountry("Eritrea"))

    // 50 - 60
    listCovid19Data.addAll(this.estonia.createListCovid19DataByCountry("Estonia"))
    listCovid19Data.addAll(this.eswatini.createListCovid19DataByCountry("Eswatini"))
    listCovid19Data.addAll(this.ethiopia.createListCovid19DataByCountry("Ethiopia"))
    listCovid19Data.addAll(this.fiji.createListCovid19DataByCountry("Fiji"))
    listCovid19Data.addAll(this.finland.createListCovid19DataByCountry("Finland"))
    listCovid19Data.addAll(this.france.createListCovid19DataByCountry("France"))
    listCovid19Data.addAll(this.gabon.createListCovid19DataByCountry("Gabon"))
    listCovid19Data.addAll(this.gambia.createListCovid19DataByCountry("Gambia"))
    listCovid19Data.addAll(this.georgia.createListCovid19DataByCountry("Georgia"))
    listCovid19Data.addAll(this.germany.createListCovid19DataByCountry("Germany"))

    // 60 - 70
    listCovid19Data.addAll(this.ghana.createListCovid19DataByCountry("Ghana"))
    listCovid19Data.addAll(this.greece.createListCovid19DataByCountry("Greece"))
    listCovid19Data.addAll(this.guatemala.createListCovid19DataByCountry("Guatemala"))
    listCovid19Data.addAll(this.guinea.createListCovid19DataByCountry("Guinea"))
    listCovid19Data.addAll(this.guyana.createListCovid19DataByCountry("Guyana"))
    listCovid19Data.addAll(this.haiti.createListCovid19DataByCountry("Haiti"))
    listCovid19Data.addAll(this.holySee.createListCovid19DataByCountry("Holy See"))
    listCovid19Data.addAll(this.honduras.createListCovid19DataByCountry("Honduras"))
    listCovid19Data.addAll(this.hungary.createListCovid19DataByCountry("Hungary"))
    listCovid19Data.addAll(this.iceland.createListCovid19DataByCountry("Iceland"))

    // 70 - 80
    listCovid19Data.addAll(this.india.createListCovid19DataByCountry("India"))
    listCovid19Data.addAll(this.indonesia.createListCovid19DataByCountry("Indonesia"))
    listCovid19Data.addAll(this.iran.createListCovid19DataByCountry("Iran"))
    listCovid19Data.addAll(this.iraq.createListCovid19DataByCountry("Iraq"))
    listCovid19Data.addAll(this.ireland.createListCovid19DataByCountry("Ireland"))
    listCovid19Data.addAll(this.israel.createListCovid19DataByCountry("Israel"))
    listCovid19Data.addAll(this.italy.createListCovid19DataByCountry("Italy"))
    listCovid19Data.addAll(this.jamaica.createListCovid19DataByCountry("Jamaica"))
    listCovid19Data.addAll(this.japan.createListCovid19DataByCountry("Japan"))
    listCovid19Data.addAll(this.jordan.createListCovid19DataByCountry("Jordan"))

    // 90 - 100
    listCovid19Data.addAll(this.kazakhstan.createListCovid19DataByCountry("Kazakhstan"))
    listCovid19Data.addAll(this.kenya.createListCovid19DataByCountry("Kenya"))
    listCovid19Data.addAll(this.koreaSouth.createListCovid19DataByCountry("Korea, South"))
    listCovid19Data.addAll(this.kuwait.createListCovid19DataByCountry("Kuwait"))
    listCovid19Data.addAll(this.kyrgyzstan.createListCovid19DataByCountry("Kyrgyzstan"))
    listCovid19Data.addAll(this.latvia.createListCovid19DataByCountry("Latvia"))
    listCovid19Data.addAll(this.lebanon.createListCovid19DataByCountry("Lebanon"))
    listCovid19Data.addAll(this.liberia.createListCovid19DataByCountry("Liberia"))
    listCovid19Data.addAll(this.liechtenstein.createListCovid19DataByCountry("Liechtenstein"))
    listCovid19Data.addAll(this.lithuania.createListCovid19DataByCountry("Lithuania"))

    // 100 - 110
    listCovid19Data.addAll(this.luxembourg.createListCovid19DataByCountry("Luxembourg"))
    listCovid19Data.addAll(this.madagascar.createListCovid19DataByCountry("Madagascar"))
    listCovid19Data.addAll(this.malaysia.createListCovid19DataByCountry("Malaysia"))
    listCovid19Data.addAll(this.maldives.createListCovid19DataByCountry("Maldives"))
    listCovid19Data.addAll(this.malta.createListCovid19DataByCountry("Malta"))
    listCovid19Data.addAll(this.mauritania.createListCovid19DataByCountry("Mauritania"))
    listCovid19Data.addAll(this.mauritius.createListCovid19DataByCountry("Mauritius"))
    listCovid19Data.addAll(this.mexico.createListCovid19DataByCountry("Mexico"))
    listCovid19Data.addAll(this.moldova.createListCovid19DataByCountry("Moldova"))
    listCovid19Data.addAll(this.monaco.createListCovid19DataByCountry("Monaco"))

    // 110 - 120
    listCovid19Data.addAll(this.mongolia.createListCovid19DataByCountry("Mongolia"))
    listCovid19Data.addAll(this.montenegro.createListCovid19DataByCountry("Montenegro"))
    listCovid19Data.addAll(this.morocco.createListCovid19DataByCountry("Morocco"))
    listCovid19Data.addAll(this.namibia.createListCovid19DataByCountry("Namibia"))
    listCovid19Data.addAll(this.nepal.createListCovid19DataByCountry("Nepal"))
    listCovid19Data.addAll(this.netherlands.createListCovid19DataByCountry("Netherlands"))
    listCovid19Data.addAll(this.newZealand.createListCovid19DataByCountry("New Zealand"))
    listCovid19Data.addAll(this.nicaragua.createListCovid19DataByCountry("Nicaragua"))
    listCovid19Data.addAll(this.niger.createListCovid19DataByCountry("Niger"))
    listCovid19Data.addAll(this.nigeria.createListCovid19DataByCountry("Nigeria"))

    // 120 - 130
    listCovid19Data.addAll(this.northMacedonia.createListCovid19DataByCountry("North Macedonia"))
    listCovid19Data.addAll(this.norway.createListCovid19DataByCountry("Norway"))
    listCovid19Data.addAll(this.oman.createListCovid19DataByCountry("Oman"))
    listCovid19Data.addAll(this.pakistan.createListCovid19DataByCountry("Pakistan"))
    listCovid19Data.addAll(this.panama.createListCovid19DataByCountry("Panama"))
    listCovid19Data.addAll(this.papuaNewGuinea.createListCovid19DataByCountry("Papua New Guinea"))
    listCovid19Data.addAll(this.paraguay.createListCovid19DataByCountry("Paraguay"))
    listCovid19Data.addAll(this.peru.createListCovid19DataByCountry("Peru"))
    listCovid19Data.addAll(this.philippines.createListCovid19DataByCountry("Philippines"))
    listCovid19Data.addAll(this.poland.createListCovid19DataByCountry("Poland"))

    // 130 - 140
    listCovid19Data.addAll(this.portugal.createListCovid19DataByCountry("Portugal"))
    listCovid19Data.addAll(this.qatar.createListCovid19DataByCountry("Qatar"))
    listCovid19Data.addAll(this.romania.createListCovid19DataByCountry("Romania"))
    listCovid19Data.addAll(this.russia.createListCovid19DataByCountry("Russia"))
    listCovid19Data.addAll(this.rwanda.createListCovid19DataByCountry("Rwanda"))
    listCovid19Data.addAll(this.saintLucia.createListCovid19DataByCountry("Saint Lucia"))
    listCovid19Data.addAll(this.saintVincentAndTheGrenadines.createListCovid19DataByCountry("Saint Vincent and the Grenadines"))
    listCovid19Data.addAll(this.sanMarino.createListCovid19DataByCountry("San Marino"))
    listCovid19Data.addAll(this.saudiArabia.createListCovid19DataByCountry("Saudi Arabia"))
    listCovid19Data.addAll(this.senegal.createListCovid19DataByCountry("Senegal"))

    // 140 - 150
    listCovid19Data.addAll(this.serbia.createListCovid19DataByCountry("Serbia"))
    listCovid19Data.addAll(this.seychelles.createListCovid19DataByCountry("Seychelles"))
    listCovid19Data.addAll(this.singapore.createListCovid19DataByCountry("Singapore"))
    listCovid19Data.addAll(this.slovakia.createListCovid19DataByCountry("Slovakia"))
    listCovid19Data.addAll(this.slovenia.createListCovid19DataByCountry("Slovenia"))
    listCovid19Data.addAll(this.somalia.createListCovid19DataByCountry("Somalia"))
    listCovid19Data.addAll(this.southAfrica.createListCovid19DataByCountry("South Africa"))
    listCovid19Data.addAll(this.spain.createListCovid19DataByCountry("Spain"))
    listCovid19Data.addAll(this.sriLanka.createListCovid19DataByCountry("Sri Lanka"))
    listCovid19Data.addAll(this.sudan.createListCovid19DataByCountry("Sudan"))

    // 150 - 160
    listCovid19Data.addAll(this.suriname.createListCovid19DataByCountry("Suriname"))
    listCovid19Data.addAll(this.sweden.createListCovid19DataByCountry("Sweden"))
    listCovid19Data.addAll(this.switzerland.createListCovid19DataByCountry("Switzerland"))
    listCovid19Data.addAll(this.taiwan.createListCovid19DataByCountry("Taiwan"))
    listCovid19Data.addAll(this.tanzania.createListCovid19DataByCountry("Tanzania"))
    listCovid19Data.addAll(this.thailand.createListCovid19DataByCountry("Thailand"))
    listCovid19Data.addAll(this.togo.createListCovid19DataByCountry("Togo"))
    listCovid19Data.addAll(this.trinidadAndTobago.createListCovid19DataByCountry("Trinidad and Tobago"))
    listCovid19Data.addAll(this.tunisia.createListCovid19DataByCountry("Tunisia"))
    listCovid19Data.addAll(this.turkey.createListCovid19DataByCountry("Turkey"))

    // 160 - 170
    listCovid19Data.addAll(this.uganda.createListCovid19DataByCountry("Uganda"))
    listCovid19Data.addAll(this.ukraine.createListCovid19DataByCountry("Ukraine"))
    listCovid19Data.addAll(this.unitedArabEmirates.createListCovid19DataByCountry("United Arab Emirates"))
    listCovid19Data.addAll(this.unitedKingdom.createListCovid19DataByCountry("United Kingdom"))
    listCovid19Data.addAll(this.uruguay.createListCovid19DataByCountry("Uruguay"))
    listCovid19Data.addAll(this.uS.createListCovid19DataByCountry("US"))
    listCovid19Data.addAll(this.uzbekistan.createListCovid19DataByCountry("Uzbekistan"))
    listCovid19Data.addAll(this.venezuela.createListCovid19DataByCountry("Venezuela"))
    listCovid19Data.addAll(this.vietnam.createListCovid19DataByCountry("Vietnam"))
    listCovid19Data.addAll(this.zambia.createListCovid19DataByCountry("Zambia"))

    // 170 - 180
    listCovid19Data.addAll(this.zimbabwe.createListCovid19DataByCountry("Zimbabwe"))
    listCovid19Data.addAll(this.dominica.createListCovid19DataByCountry("Dominica"))
    listCovid19Data.addAll(this.grenada.createListCovid19DataByCountry("Grenada"))
    listCovid19Data.addAll(this.mozambique.createListCovid19DataByCountry("Mozambique"))
    listCovid19Data.addAll(this.syria.createListCovid19DataByCountry("Syria"))
    listCovid19Data.addAll(this.timorLeste.createListCovid19DataByCountry("Timor-Leste"))
    listCovid19Data.addAll(this.belize.createListCovid19DataByCountry("Belize"))
    listCovid19Data.addAll(this.laos.createListCovid19DataByCountry("Laos"))
    listCovid19Data.addAll(this.libya.createListCovid19DataByCountry("Libya"))
    listCovid19Data.addAll(this.guineaBissau.createListCovid19DataByCountry("Guinea-Bissau"))
    listCovid19Data.addAll(this.mali.createListCovid19DataByCountry("Mali"))
    listCovid19Data.addAll(this.saintKittsAndNevis.createListCovid19DataByCountry("Saint Kitts and Nevis"))
    listCovid19Data.addAll(this.kosovo.createListCovid19DataByCountry("Kosovo"))

    // 180 - 182
    listCovid19Data.addAll(this.burma.createListCovid19DataByCountry("Burma"))
    listCovid19Data.addAll(this.msZaandam.createListCovid19DataByCountry("MS Zaandam"))

    return listCovid19Data
}

fun List<Covid19Data>.createListCovid19DataByCountry(
    country: String
): List<Covid19Data>{
    this.forEach { covid19Data ->
        covid19Data.apply {
            this.country = country
            this.id = "${country}_${this.date}"
        }
    }
    return this
}