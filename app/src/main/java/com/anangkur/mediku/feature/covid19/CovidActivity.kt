package com.anangkur.mediku.feature.covid19

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.util.obtainViewModel

class CovidActivity : BaseActivity<CovidViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, CovidActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_covid
    override val mViewModel: CovidViewModel
        get() = obtainViewModel(CovidViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null


}
