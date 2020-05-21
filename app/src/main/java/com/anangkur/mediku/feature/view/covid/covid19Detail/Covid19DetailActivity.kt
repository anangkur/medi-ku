package com.anangkur.mediku.feature.view.covid.covid19Detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.databinding.ActivityCovid19DetailBinding
import com.anangkur.mediku.util.obtainViewModel

class Covid19DetailActivity: BaseActivity<ActivityCovid19DetailBinding, Covid19DetailViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, Covid19DetailActivity::class.java))
        }
    }

    override val mViewModel: Covid19DetailViewModel
        get() = obtainViewModel(Covid19DetailViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    override fun setupView(): ActivityCovid19DetailBinding {
        return ActivityCovid19DetailBinding.inflate(layoutInflater)
    }
}
