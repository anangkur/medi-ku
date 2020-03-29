package com.anangkur.mediku.data.model.covid19

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Covid19ApiResponse(
    @SerializedName("Afghanistan")
    val afghanistan: List<Covid19Data> = listOf(),

    @SerializedName("Albania")
    val albania: List<Covid19Data> = listOf(),

    @SerializedName("Algeria")
    val algeria: List<Covid19Data> = listOf(),

    @SerializedName("Andorra")
    val andorra: List<Covid19Data> = listOf(),

    @SerializedName("Angola")
    val angola: List<Covid19Data> = listOf(),

    @SerializedName("Antigua and Barbuda")
    val antiguaAndBarbuda: List<Covid19Data> = listOf(),

    @SerializedName("Argentina")
    val argentina: List<Covid19Data> = listOf(),

    @SerializedName("Armenia")
    val armenia: List<Covid19Data> = listOf(),

    @SerializedName("Australia")
    val australia: List<Covid19Data> = listOf(),

    @SerializedName("Austria")
    val austria: List<Covid19Data> = listOf(),

    @SerializedName("Azerbaijan")
    val azerbaijan: List<Covid19Data> = listOf(),

    @SerializedName("Bahamas")
    val bahamas: List<Covid19Data> = listOf(),

    @SerializedName("Bahrain")
    val bahrain: List<Covid19Data> = listOf(),

    @SerializedName("Bangladesh")
    val bangladesh: List<Covid19Data> = listOf(),

    @SerializedName("Barbados")
    val barbados: List<Covid19Data> = listOf(),

    @SerializedName("Belarus")
    val belarus: List<Covid19Data> = listOf(),

    @SerializedName("Belgium")
    val belgium: List<Covid19Data> = listOf(),

    @SerializedName("Belize")
    val belize: List<Covid19Data> = listOf(),

    @SerializedName("Benin")
    val benin: List<Covid19Data> = listOf(),

    @SerializedName("Bhutan")
    val bhutan: List<Covid19Data> = listOf(),

    @SerializedName("Bolivia")
    val bolivia: List<Covid19Data> = listOf(),

    @SerializedName("Bosnia and Herzegovina")
    val bosniaAndHerzegovina: List<Covid19Data> = listOf(),

    @SerializedName("Brazil")
    val brazil: List<Covid19Data> = listOf(),

    @SerializedName("Brunei")
    val brunei: List<Covid19Data> = listOf(),

    @SerializedName("Bulgaria")
    val bulgaria: List<Covid19Data> = listOf(),

    @SerializedName("Burkina Faso")
    val burkinaFaso: List<Covid19Data> = listOf(),

    @SerializedName("Burma")
    val burma: List<Covid19Data> = listOf(),

    @SerializedName("Cabo Verde")
    val caboVerde: List<Covid19Data> = listOf(),

    @SerializedName("Cambodia")
    val cambodia: List<Covid19Data> = listOf(),

    @SerializedName("Cameroon")
    val cameroon: List<Covid19Data> = listOf(),

    @SerializedName("Canada")
    val canada: List<Covid19Data> = listOf(),

    @SerializedName("Central African Republic")
    val centralAfricanRepublic: List<Covid19Data> = listOf(),

    @SerializedName("Chad")
    val chad: List<Covid19Data> = listOf(),

    @SerializedName("Chile")
    val chile: List<Covid19Data> = listOf(),

    @SerializedName("China")
    val china: List<Covid19Data> = listOf(),

    @SerializedName("Colombia")
    val colombia: List<Covid19Data> = listOf(),

    @SerializedName("Congo (Brazzaville)")
    val congoBrazzaville: List<Covid19Data> = listOf(),

    @SerializedName("Congo (Kinshasa)")
    val congoKinshasa: List<Covid19Data> = listOf(),

    @SerializedName("Costa Rica")
    val costaRica: List<Covid19Data> = listOf(),

    @SerializedName("Cote d'Ivoire")
    val coteDIvoire: List<Covid19Data> = listOf(),

    @SerializedName("Croatia")
    val croatia: List<Covid19Data> = listOf(),

    @SerializedName("Cuba")
    val cuba: List<Covid19Data> = listOf(),

    @SerializedName("Cyprus")
    val cyprus: List<Covid19Data> = listOf(),

    @SerializedName("Czechia")
    val czechia: List<Covid19Data> = listOf(),

    @SerializedName("Denmark")
    val denmark: List<Covid19Data> = listOf(),

    @SerializedName("Diamond Princess")
    val diamondPrincess: List<Covid19Data> = listOf(),

    @SerializedName("Djibouti")
    val djibouti: List<Covid19Data> = listOf(),

    @SerializedName("Dominica")
    val dominica: List<Covid19Data> = listOf(),

    @SerializedName("Dominican Republic")
    val dominicanRepublic: List<Covid19Data> = listOf(),

    @SerializedName("Ecuador")
    val ecuador: List<Covid19Data> = listOf(),

    @SerializedName("Egypt")
    val egypt: List<Covid19Data> = listOf(),

    @SerializedName("El Salvador")
    val elSalvador: List<Covid19Data> = listOf(),

    @SerializedName("Equatorial Guinea")
    val equatorialGuinea: List<Covid19Data> = listOf(),

    @SerializedName("Eritrea")
    val eritrea: List<Covid19Data> = listOf(),

    @SerializedName("Estonia")
    val estonia: List<Covid19Data> = listOf(),

    @SerializedName("Eswatini")
    val eswatini: List<Covid19Data> = listOf(),

    @SerializedName("Ethiopia")
    val ethiopia: List<Covid19Data> = listOf(),

    @SerializedName("Fiji")
    val fiji: List<Covid19Data> = listOf(),

    @SerializedName("Finland")
    val finland: List<Covid19Data> = listOf(),

    @SerializedName("France")
    val france: List<Covid19Data> = listOf(),

    @SerializedName("Gabon")
    val gabon: List<Covid19Data> = listOf(),

    @SerializedName("Gambia")
    val gambia: List<Covid19Data> = listOf(),

    @SerializedName("Georgia")
    val georgia: List<Covid19Data> = listOf(),

    @SerializedName("Germany")
    val germany: List<Covid19Data> = listOf(),

    @SerializedName("Ghana")
    val ghana: List<Covid19Data> = listOf(),

    @SerializedName("Greece")
    val greece: List<Covid19Data> = listOf(),

    @SerializedName("Grenada")
    val grenada: List<Covid19Data> = listOf(),

    @SerializedName("Guatemala")
    val guatemala: List<Covid19Data> = listOf(),

    @SerializedName("Guinea")
    val guinea: List<Covid19Data> = listOf(),

    @SerializedName("Guinea-Bissau")
    val guineaBissau: List<Covid19Data> = listOf(),

    @SerializedName("Guyana")
    val guyana: List<Covid19Data> = listOf(),

    @SerializedName("Haiti")
    val haiti: List<Covid19Data> = listOf(),

    @SerializedName("Holy See")
    val holySee: List<Covid19Data> = listOf(),

    @SerializedName("Honduras")
    val honduras: List<Covid19Data> = listOf(),

    @SerializedName("Hungary")
    val hungary: List<Covid19Data> = listOf(),

    @SerializedName("Iceland")
    val iceland: List<Covid19Data> = listOf(),

    @SerializedName("India")
    val india: List<Covid19Data> = listOf(),

    @SerializedName("Indonesia")
    val indonesia: List<Covid19Data> = listOf(),

    @SerializedName("Iran")
    val iran: List<Covid19Data> = listOf(),

    @SerializedName("Iraq")
    val iraq: List<Covid19Data> = listOf(),

    @SerializedName("Ireland")
    val ireland: List<Covid19Data> = listOf(),

    @SerializedName("Israel")
    val israel: List<Covid19Data> = listOf(),

    @SerializedName("Italy")
    val italy: List<Covid19Data> = listOf(),

    @SerializedName("Jamaica")
    val jamaica: List<Covid19Data> = listOf(),

    @SerializedName("Japan")
    val japan: List<Covid19Data> = listOf(),

    @SerializedName("Jordan")
    val jordan: List<Covid19Data> = listOf(),

    @SerializedName("Kazakhstan")
    val kazakhstan: List<Covid19Data> = listOf(),

    @SerializedName("Kenya")
    val kenya: List<Covid19Data> = listOf(),

    @SerializedName("Korea, South")
    val koreaSouth: List<Covid19Data> = listOf(),

    @SerializedName("Kosovo")
    val kosovo: List<Covid19Data> = listOf(),

    @SerializedName("Kuwait")
    val kuwait: List<Covid19Data> = listOf(),

    @SerializedName("Kyrgyzstan")
    val kyrgyzstan: List<Covid19Data> = listOf(),

    @SerializedName("Laos")
    val laos: List<Covid19Data> = listOf(),

    @SerializedName("Latvia")
    val latvia: List<Covid19Data> = listOf(),

    @SerializedName("Lebanon")
    val lebanon: List<Covid19Data> = listOf(),

    @SerializedName("Liberia")
    val liberia: List<Covid19Data> = listOf(),

    @SerializedName("Libya")
    val libya: List<Covid19Data> = listOf(),

    @SerializedName("Liechtenstein")
    val liechtenstein: List<Covid19Data> = listOf(),

    @SerializedName("Lithuania")
    val lithuania: List<Covid19Data> = listOf(),

    @SerializedName("Luxembourg")
    val luxembourg: List<Covid19Data> = listOf(),

    @SerializedName("Madagascar")
    val madagascar: List<Covid19Data> = listOf(),

    @SerializedName("Malaysia")
    val malaysia: List<Covid19Data> = listOf(),

    @SerializedName("Maldives")
    val maldives: List<Covid19Data> = listOf(),

    @SerializedName("Mali")
    val mali: List<Covid19Data> = listOf(),

    @SerializedName("Malta")
    val malta: List<Covid19Data> = listOf(),

    @SerializedName("Mauritania")
    val mauritania: List<Covid19Data> = listOf(),

    @SerializedName("Mauritius")
    val mauritius: List<Covid19Data> = listOf(),

    @SerializedName("Mexico")
    val mexico: List<Covid19Data> = listOf(),

    @SerializedName("Moldova")
    val moldova: List<Covid19Data> = listOf(),

    @SerializedName("Monaco")
    val monaco: List<Covid19Data> = listOf(),

    @SerializedName("Mongolia")
    val mongolia: List<Covid19Data> = listOf(),

    @SerializedName("Montenegro")
    val montenegro: List<Covid19Data> = listOf(),

    @SerializedName("Morocco")
    val morocco: List<Covid19Data> = listOf(),

    @SerializedName("Mozambique")
    val mozambique: List<Covid19Data> = listOf(),

    @SerializedName("MS Zaandam")
    val msZaandam: List<Covid19Data> = listOf(),

    @SerializedName("Namibia")
    val namibia: List<Covid19Data> = listOf(),

    @SerializedName("Nepal")
    val nepal: List<Covid19Data> = listOf(),

    @SerializedName("Netherlands")
    val netherlands: List<Covid19Data> = listOf(),

    @SerializedName("New Zealand")
    val newZealand: List<Covid19Data> = listOf(),

    @SerializedName("Nicaragua")
    val nicaragua: List<Covid19Data> = listOf(),

    @SerializedName("Niger")
    val niger: List<Covid19Data> = listOf(),

    @SerializedName("Nigeria")
    val nigeria: List<Covid19Data> = listOf(),

    @SerializedName("North Macedonia")
    val northMacedonia: List<Covid19Data> = listOf(),

    @SerializedName("Norway")
    val norway: List<Covid19Data> = listOf(),

    @SerializedName("Oman")
    val oman: List<Covid19Data> = listOf(),

    @SerializedName("Pakistan")
    val pakistan: List<Covid19Data> = listOf(),

    @SerializedName("Panama")
    val panama: List<Covid19Data> = listOf(),

    @SerializedName("Papua New Guinea")
    val papuaNewGuinea: List<Covid19Data> = listOf(),

    @SerializedName("Paraguay")
    val paraguay: List<Covid19Data> = listOf(),

    @SerializedName("Peru")
    val peru: List<Covid19Data> = listOf(),

    @SerializedName("Philippines")
    val philippines: List<Covid19Data> = listOf(),

    @SerializedName("Poland")
    val poland: List<Covid19Data> = listOf(),

    @SerializedName("Portugal")
    val portugal: List<Covid19Data> = listOf(),

    @SerializedName("Qatar")
    val qatar: List<Covid19Data> = listOf(),

    @SerializedName("Romania")
    val romania: List<Covid19Data> = listOf(),

    @SerializedName("Russia")
    val russia: List<Covid19Data> = listOf(),

    @SerializedName("Rwanda")
    val rwanda: List<Covid19Data> = listOf(),

    @SerializedName("Saint Kitts and Nevis")
    val saintKittsAndNevis: List<Covid19Data> = listOf(),

    @SerializedName("Saint Lucia")
    val saintLucia: List<Covid19Data> = listOf(),

    @SerializedName("Saint Vincent and the Grenadines")
    val saintVincentAndTheGrenadines: List<Covid19Data> = listOf(),

    @SerializedName("San Marino")
    val sanMarino: List<Covid19Data> = listOf(),

    @SerializedName("Saudi Arabia")
    val saudiArabia: List<Covid19Data> = listOf(),

    @SerializedName("Senegal")
    val senegal: List<Covid19Data> = listOf(),

    @SerializedName("Serbia")
    val serbia: List<Covid19Data> = listOf(),

    @SerializedName("Seychelles")
    val seychelles: List<Covid19Data> = listOf(),

    @SerializedName("Singapore")
    val singapore: List<Covid19Data> = listOf(),

    @SerializedName("Slovakia")
    val slovakia: List<Covid19Data> = listOf(),

    @SerializedName("Slovenia")
    val slovenia: List<Covid19Data> = listOf(),

    @SerializedName("Somalia")
    val somalia: List<Covid19Data> = listOf(),

    @SerializedName("South Africa")
    val southAfrica: List<Covid19Data> = listOf(),

    @SerializedName("Spain")
    val spain: List<Covid19Data> = listOf(),

    @SerializedName("Sri Lanka")
    val sriLanka: List<Covid19Data> = listOf(),

    @SerializedName("Sudan")
    val sudan: List<Covid19Data> = listOf(),

    @SerializedName("Suriname")
    val suriname: List<Covid19Data> = listOf(),

    @SerializedName("Sweden")
    val sweden: List<Covid19Data> = listOf(),

    @SerializedName("Switzerland")
    val switzerland: List<Covid19Data> = listOf(),

    @SerializedName("Syria")
    val syria: List<Covid19Data> = listOf(),

    @SerializedName("Taiwan*")
    val taiwan: List<Covid19Data> = listOf(),

    @SerializedName("Tanzania")
    val tanzania: List<Covid19Data> = listOf(),

    @SerializedName("Thailand")
    val thailand: List<Covid19Data> = listOf(),

    @SerializedName("Timor-Leste")
    val timorLeste: List<Covid19Data> = listOf(),

    @SerializedName("Togo")
    val togo: List<Covid19Data> = listOf(),

    @SerializedName("Trinidad and Tobago")
    val trinidadAndTobago: List<Covid19Data> = listOf(),

    @SerializedName("Tunisia")
    val tunisia: List<Covid19Data> = listOf(),

    @SerializedName("Turkey")
    val turkey: List<Covid19Data> = listOf(),

    @SerializedName("US")
    val uS: List<Covid19Data> = listOf(),

    @SerializedName("Uganda")
    val uganda: List<Covid19Data> = listOf(),

    @SerializedName("Ukraine")
    val ukraine: List<Covid19Data> = listOf(),

    @SerializedName("United Arab Emirates")
    val unitedArabEmirates: List<Covid19Data> = listOf(),

    @SerializedName("United Kingdom")
    val unitedKingdom: List<Covid19Data> = listOf(),

    @SerializedName("Uruguay")
    val uruguay: List<Covid19Data> = listOf(),

    @SerializedName("Uzbekistan")
    val uzbekistan: List<Covid19Data> = listOf(),

    @SerializedName("Venezuela")
    val venezuela: List<Covid19Data> = listOf(),

    @SerializedName("Vietnam")
    val vietnam: List<Covid19Data> = listOf(),

    @SerializedName("West Bank and Gaza")
    val westBankAndGaza: List<Covid19Data> = listOf(),

    @SerializedName("Zambia")
    val zambia: List<Covid19Data> = listOf(),

    @SerializedName("Zimbabwe")
    val zimbabwe: List<Covid19Data> = listOf()
)