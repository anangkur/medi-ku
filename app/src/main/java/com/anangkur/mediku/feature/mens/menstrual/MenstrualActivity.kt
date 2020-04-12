package com.anangkur.mediku.feature.mens.menstrual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.util.obtainViewModel

class MenstrualActivity: BaseActivity<MenstrualViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MenstrualActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_menstrual
    override val mViewModel: MenstrualViewModel
        get() = obtainViewModel(MenstrualViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
