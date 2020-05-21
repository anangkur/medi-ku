package com.anangkur.mediku.feature.view.about

import com.anangkur.mediku.feature.model.about.ResourceIntent

interface AboutActionListener {
    fun onClickResourceParent(resourceParent: ResourceIntent)
    fun onClickResourceChild(resourceChild: ResourceIntent.ResourceChild)
    fun onClickCovid19Api(url: String)
    fun onClickNewsApi(url: String)
    fun onClickGiveRating(url: String)
}