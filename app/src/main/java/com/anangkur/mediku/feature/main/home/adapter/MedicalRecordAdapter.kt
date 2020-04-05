package com.anangkur.mediku.feature.main.home.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.main.home.HomeActionListener
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.item_medical_record.view.*

class MedicalRecordAdapter(private val listener: HomeActionListener): BaseAdapter<MedicalRecord>(){

    override val layout: Int
        get() = R.layout.item_medical_record

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

        itemView.iv_medical_record.setImageResource(resource.first)
        itemView.background = ContextCompat.getDrawable(itemView.context, resource.second)
        itemView.tv_title_medical_record.text = data.category
        itemView.tv_desc_medical_record.text = data.diagnosis
        itemView.setOnClickListener { listener.onClickMedicalRecord(data) }
        itemView.tv_date_medical_record.text = data.createdAt.formatDate()
    }

}