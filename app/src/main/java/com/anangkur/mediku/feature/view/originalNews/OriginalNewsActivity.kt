package com.anangkur.mediku.feature.view.originalNews

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.webkit.*
import androidx.appcompat.widget.Toolbar
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.base.BaseErrorView
import com.anangkur.mediku.databinding.ActivityOriginalNewsBinding
import com.anangkur.mediku.util.gone
import com.anangkur.mediku.util.openBrowser
import com.anangkur.mediku.util.visible

class OriginalNewsActivity: BaseActivity<ActivityOriginalNewsBinding, Nothing?>() {

    companion object {
        const val EXTRA_URL = "EXTRA_URL"
        fun startActivity(context: Context, url: String){
            context.startActivity(Intent(context, OriginalNewsActivity::class.java)
                .putExtra(EXTRA_URL, url))
        }
    }

    override val mViewModel: Nothing?
        get() = null
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = null

    private lateinit var url: String
    private var isSuccessLoadUrl = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        url = intent?.getStringExtra(EXTRA_URL)?:""
        setupWebView(url)
    }

    override fun setupView(): ActivityOriginalNewsBinding {
        return ActivityOriginalNewsBinding.inflate(layoutInflater)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_original_news, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_open_browser -> {
                openBrowser(url)
                true
            }
            else -> false
        }
    }

    private fun setupWebView(url: String){
        mLayout.wvOriginalNews.webChromeClient = WebChromeClient()
        mLayout.wvOriginalNews.clearCache(true)
        mLayout.wvOriginalNews.clearHistory()
        mLayout.wvOriginalNews.settings.javaScriptEnabled = true
        mLayout.wvOriginalNews.settings.javaScriptCanOpenWindowsAutomatically = true
        mLayout.wvOriginalNews.webViewClient = object: WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                isSuccessLoadUrl = true
                mLayout.wvOriginalNews.gone()
                mLayout.evOriginalNews.showProgress()
                mLayout.evOriginalNews.visible()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (isSuccessLoadUrl){
                    mLayout.wvOriginalNews.visible()
                    mLayout.evOriginalNews.gone()
                    mLayout.evOriginalNews.endProgress()
                }
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                isSuccessLoadUrl = false
                mLayout.wvOriginalNews.gone()
                mLayout.evOriginalNews.showError(
                    errorMessage = getString(R.string.error_default),
                    errorType = BaseErrorView.ERROR_GENERAL,
                    buttonErrorString = ""
                )
                mLayout.evOriginalNews.setRetryClickListener {
                    view?.reload()
                }
                mLayout.evOriginalNews.visible()
            }
        }
        mLayout.wvOriginalNews.loadUrl(url)
    }
}
