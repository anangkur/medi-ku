package com.anangkur.mediku.feature.covid19Detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.util.obtainViewModel

class Covid19DetailActivity: BaseActivity<Covid19DetailViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, Covid19DetailActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_covid19_detail
    override val mViewModel: Covid19DetailViewModel
        get() = obtainViewModel(Covid19DetailViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null
}
