package com.anangkur.mediku.feature.view.medicalRecords.listMedicalRecords

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.databinding.ItemHomeBinding
import com.anangkur.mediku.util.*

class MedicalRecordsAdapter(private val listener: MedicalRecordsActionListener): BaseAdapter<ItemHomeBinding, MedicalRecord>(){

    override fun bindView(parent: ViewGroup): ItemHomeBinding {
        return ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: MedicalRecord, itemView: ItemHomeBinding, position: Int) {
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
            itemView.ivDocument.visible()
            itemView.ivDocument.setImageUrl(data.document)
        }else{
            itemView.ivDocument.gone()
        }
        itemView.ivItemHome.setImageResource(resource.first)
        itemView.root.background = ContextCompat.getDrawable(itemView.root.context, resource.second)
        itemView.tvItemHome.text = data.category
        itemView.tvDiagnosisHome.text = data.diagnosis
        itemView.root.setOnClickListener { listener.onClickItem(data) }
        itemView.tvDateHome.text = data.createdAt.formatDate()
    }

}