package com.anangkur.mediku.feature.covid19.adapter

import android.view.View
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.covid19.Covid19Data
import com.anangkur.mediku.util.formatThousandNumber
import com.anangkur.mediku.util.getCountryCode
import com.anangkur.mediku.util.setImageUrl
import kotlinx.android.synthetic.main.item_covid_horizontal.view.*

class CovidHorizontalAdapter: BaseAdapter<Covid19Data>() {

    override val layout: Int
        get() = R.layout.item_covid_horizontal

    override fun bind(data: Covid19Data, itemView: View, position: Int) {
        itemView.iv_country.setImageUrl(itemView.context.getString(R.string.urlImageCountryFlag, data.country.getCountryCode()))
        itemView.tv_country.text = data.country
        itemView.tv_confirmed.text = data.confirmed.formatThousandNumber()
        itemView.tv_death.text = data.deaths.formatThousandNumber()
        itemView.tv_recovered.text = data.recovered.formatThousandNumber()
    }
}