package com.anangkur.mediku.feature.menstrualEdit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.util.obtainViewModel

class MenstrualEditActivity: BaseActivity<MenstrualEditViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MenstrualEditActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_menstrual_edit
    override val mViewModel: MenstrualEditViewModel
        get() = obtainViewModel(MenstrualEditViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
