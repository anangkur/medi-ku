package com.anangkur.mediku.feature.view.about

import com.anangkur.mediku.data.model.about.Resource

interface AboutActionListener {
    fun onClickResourceParent(resourceParent: Resource)
    fun onClickResourceChild(resourceChild: Resource.ResourceChild)
    fun onClickCovid19Api(url: String)
    fun onClickNewsApi(url: String)
    fun onClickGiveRating(url: String)
}