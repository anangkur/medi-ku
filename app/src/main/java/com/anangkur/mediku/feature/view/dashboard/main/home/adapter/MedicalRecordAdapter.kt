package com.anangkur.mediku.feature.view.dashboard.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.databinding.ItemMedicalRecordBinding
import com.anangkur.mediku.feature.view.dashboard.main.home.HomeActionListener
import com.anangkur.mediku.util.*

class MedicalRecordAdapter(private val listener: HomeActionListener): BaseAdapter<ItemMedicalRecordBinding, MedicalRecord>(){

    override fun bindView(parent: ViewGroup): ItemMedicalRecordBinding {
        return ItemMedicalRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: MedicalRecord, itemView: ItemMedicalRecordBinding, position: Int) {
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

        itemView.ivMedicalRecord.setImageResource(resource.first)
        itemView.root.background = ContextCompat.getDrawable(itemView.root.context, resource.second)
        itemView.tvTitleMedicalRecord.text = data.category
        itemView.tvDescMedicalRecord.text = data.diagnosis
        itemView.root.setOnClickListener { listener.onClickMedicalRecord(data) }
        itemView.tvDateMedicalRecord.text = data.createdAt.formatDate()
    }

}