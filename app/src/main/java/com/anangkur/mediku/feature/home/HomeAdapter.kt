package com.anangkur.mediku.feature.home

import android.view.View
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.util.Const
import kotlinx.android.synthetic.main.item_home.view.*

class HomeAdapter(private val listener: HomeActionListener): BaseAdapter<MedicalRecord>(){

    override val layout: Int
        get() = R.layout.item_home

    override fun bind(data: MedicalRecord, itemView: View, position: Int) {
        val resource = when (data.category){
            Const.CATEGORY_SICK -> {
                R.drawable.ic_pills
            }
            Const.CATEGORY_CHECKUP -> {
                R.drawable.ic_healthy
            }
            Const.CATEGORY_HOSPITAL -> {
                R.drawable.ic_first_aid_kit
            }
            else -> 0
        }
        itemView.iv_item_home.setImageResource(resource)
        itemView.tv_item_home.text = data.category
        itemView.tv_diagnosis_home.text = data.diagnosis
        itemView.setOnClickListener { listener.onClickItem(data) }
        itemView.tv_date_home.text = data.updateAt?:data.createdAt
    }

}