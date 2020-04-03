package com.anangkur.mediku.feature.covid19.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.covid19.Covid19Data
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.util.formatThousandNumber
import com.anangkur.mediku.util.getCountryCode
import com.anangkur.mediku.util.setImageUrl
import kotlinx.android.synthetic.main.item_covid_horizontal.view.*

class CovidHorizontalAdapter: BaseAdapter<NewCovid19Summary>() {

    override val layout: Int
        get() = R.layout.item_covid_horizontal

    override fun bind(data: NewCovid19Summary, itemView: View, position: Int) {
        itemView.iv_country.setImageUrl(itemView.context.getString(R.string.urlImageCountryFlag, data.country?.getCountryCode()))
        itemView.tv_country.text = data.country
        itemView.tv_confirmed.text = data.totalConfirmed?.formatThousandNumber()
        itemView.tv_death.text = data.totalDeaths?.formatThousandNumber()
        itemView.tv_recovered.text = data.totalRecovered?.formatThousandNumber()

        if (data.newConfirmed?:0 > 0){
            itemView.tv_confirmed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_red, 0)
            itemView.tv_confirmed.compoundDrawablePadding = 0
        }else{
            itemView.tv_confirmed.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        if (data.newDeaths?:0 > 0){
            itemView.tv_death.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_red, 0)
            itemView.tv_death.compoundDrawablePadding = 0
        }else{
            itemView.tv_death.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        if (data.newRecovered?:0 > 0){
            itemView.tv_recovered.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_green, 0)
            itemView.tv_recovered.compoundDrawablePadding = 0
        }else{
            itemView.tv_recovered.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }
}