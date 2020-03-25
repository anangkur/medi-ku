package com.anangkur.mediku.feature.home

import android.view.View
import androidx.core.content.ContextCompat
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.item_home.view.*

class HomeAdapter(private val listener: HomeActionListener): BaseAdapter<MedicalRecord>(){

    override val layout: Int
        get() = R.layout.item_home

    override fun bind(data: MedicalRecord, itemView: View, position: Int) {
        val resource = when (data.category){
            Const.CATEGORY_SICK -> {
                Pair(R.drawable.ic_pills, R.drawable.rect_rounded_4dp_gradient_blue)
            }
            Const.CATEGORY_CHECKUP -> {
                Pair(R.drawable.ic_healthy, R.drawable.rect_rounded_4dp_gradient_green)
            }
            Const.CATEGORY_HOSPITAL -> {
                Pair(R.drawable.ic_first_aid_kit, R.drawable.rect_rounded_4dp_gradient_purple)
            }
            else -> Pair(0,0)
        }
        if (data.document != null){
            itemView.iv_document.visible()
            itemView.iv_document.setImageUrl(data.document)
        }else{
            itemView.iv_document.gone()
        }
        itemView.iv_item_home.setImageResource(resource.first)
        itemView.background = ContextCompat.getDrawable(itemView.context, resource.second)
        itemView.tv_item_home.text = data.category
        itemView.tv_diagnosis_home.text = data.diagnosis
        itemView.setOnClickListener { listener.onClickItem(data) }
        itemView.tv_date_home.text = data.createdAt.formatDate()
    }

}