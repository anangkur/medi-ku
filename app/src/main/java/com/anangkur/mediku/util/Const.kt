package com.anangkur.mediku.util

object Const {
    // url
    const val URL_COVID19_CHECKUP = "https://www.halodoc.com/tanya-jawab-seputar-virus-corona/"
    const val URL_COVID19_API = "https://pomber.github.io/"
    const val URL_NEW_COVID19_API = "https://api.covid19api.com/"
    const val URL_NEWS_API = "https://newsapi.org/v2/"

    // database
    const val PREF_NAME = "PREF_NAME"
    const val DATABASE_NAME = "mediku_database"

    // provider
    const val PROVIDER_GOOGLE = "google.com"
    const val PROVIDER_FIREBASE = "firebase"
    const val PROVIDER_PASSWORD = "password"

    // image compress
    const val MAX_IMAGE_SIZE = 500.0
    const val COMPRESS_QUALITY = 95
    const val SAMPLE_SIZE = 2

    // fireStore
    const val COLLECTION_USER = "users"
    const val COLLECTION_MEDICAL_RECORD = "medical_records"
    const val COLLECTION_MENSTRUAL_PERIOD = "menstrual_period"

    // medical record
    const val CATEGORY_SICK = "Sick"
    const val CATEGORY_CHECKUP = "Checkup"
    const val CATEGORY_HOSPITAL = "Hospital"

    // date format
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DEFAULT_DATE_FORMAT_NO_TIME = "yyyy-MM-dd"
    const val TIME_GENERAL_HH_MM = "HH:mm"
    const val DAY_NAME_DATE_MONTH_NAME = "EEE, dd MMM"
    const val DAY_FULL_WITH_DATE_LOCALE = "EEE, dd MMM yyyy"
    const val DATE_ENGLISH_YYYY_MM_DD = "yyyy-M-d"
    const val DATE_FORMAT_NEW_COVID19 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSS'Z'"
    const val DATE_FORMAT_NEW_COVID19_2 = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val DATE_FORMAT_SHOWN_COVID19 = "EEE, dd MMM yyyy - hh:mm"
    const val DATE_NEWS_SHOWN = "dd/MM/yyyy - hh:mm"

    // notification
    const val NOTIFICATION_CHANNEL_ID = "10001"
    const val NOTIFICATION_TYPE_COVID = "covid"

    // storage
    const val STORAGE_PROFILE_PHOTO = "profile_photos"

    // status new covid 19 data
    const val STATUS_NEW_COVID19_CONFIRMED = "confirmed"
    const val STATUS_NEW_COVID19_RECOVERED = "recovered"
    const val STATUS_NEW_COVID19_DEATHS = "deaths"

    // news
    const val API_KEY = "261d82dd7e26494e841fb1039a4fdaf7"
    const val NEWS_HEALTH = "health"

    // month key
    const val KEY_JAN = "January"
    const val KEY_FEB = "February"
    const val KEY_MAR = "March"
    const val KEY_APR = "April"
    const val KEY_MAY = "May"
    const val KEY_JUN = "June"
    const val KEY_JUL = "July"
    const val KEY_AUG = "August"
    const val KEY_SEP = "September"
    const val KEY_OCT = "October"
    const val KEY_NOV = "November"
    const val KEY_DEC = "December"

    // preferences
    const val PREF_FIREBASE_TOKEN = "PREF_FIREBASE_TOKEN"
    const val PREF_COUNTRY = "EXTRA_COUNTRY"
}