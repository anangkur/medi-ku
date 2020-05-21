package com.anangkur.mediku.feature.covid.covid19.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.databinding.ItemCovidHorizontalBinding
import com.anangkur.mediku.util.formatThousandNumber
import com.anangkur.mediku.util.getCountryCode
import com.anangkur.mediku.util.setImageUrl

class CovidHorizontalAdapter: BaseAdapter<ItemCovidHorizontalBinding, NewCovid19Summary>() {

    override fun bindView(parent: ViewGroup): ItemCovidHorizontalBinding {
        return ItemCovidHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: NewCovid19Summary, itemView: ItemCovidHorizontalBinding, position: Int) {
        itemView.ivCountry.setImageUrl(itemView.root.context.getString(R.string.urlImageCountryFlag, data.country.getCountryCode()))
        itemView.tvCountry.text = data.country
        itemView.tvConfirmed.text = data.totalConfirmed?.formatThousandNumber()
        itemView.tvDeath.text = data.totalDeaths?.formatThousandNumber()
        itemView.tvRecovered.text = data.totalRecovered?.formatThousandNumber()

        if (data.newConfirmed?:0 > 0){
            itemView.tvConfirmed.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_red, 0)
            itemView.tvConfirmed.compoundDrawablePadding = 0
        }else{
            itemView.tvConfirmed.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        if (data.newDeaths?:0 > 0){
            itemView.tvDeath.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_red, 0)
            itemView.tvDeath.compoundDrawablePadding = 0
        }else{
            itemView.tvDeath.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        if (data.newRecovered?:0 > 0){
            itemView.tvRecovered.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_green, 0)
            itemView.tvRecovered.compoundDrawablePadding = 0
        }else{
            itemView.tvRecovered.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }
}