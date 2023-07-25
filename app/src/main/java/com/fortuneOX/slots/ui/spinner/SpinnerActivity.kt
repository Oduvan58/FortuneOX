package com.fortuneOX.slots.ui.spinner

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.fortuneOX.slots.databinding.ActivitySpinnerBinding
import com.fortuneOX.slots.ui.spinner.FortunePreferences.cokies
import com.fortuneOX.slots.ui.spinner.FortunePreferences.fortuneruri

class SpinnerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpinnerBinding

    private lateinit var webView: WebView

    companion object {
        private const val REQUEST_FILE_CHOOSER = 1
        private const val PERMISSION_REQUEST_CODE = 2
    }

    private val cookieManager: CookieManager by lazy { CookieManager.getInstance() }
    private var sharedPreferences: SharedPreferences? = null
    private var fileChooserCallback: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = FortunePreferences.getprefer(this)

        webView = binding.fortunerview

        webView.settings.apply {
            domStorageEnabled = true
            javaScriptEnabled = true
            useWideViewPort = true
            databaseEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }
        CookieManager.getInstance().setAcceptCookie(true)

        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(webView, true);

        webView.webViewClient = CustomWebViewClient()
        webView.webChromeClient = CustomWebChromeClient()

        savedInstanceState?.let { webView.restoreState(it) } ?: loadWebPage()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        webView.restoreState(savedInstanceState)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_FILE_CHOOSER) {
            val result = WebChromeClient.FileChooserParams.parseResult(resultCode, data)
            fileChooserCallback?.onReceiveValue(result)
            fileChooserCallback = null
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fileChooserCallback = fileChooserCallback
                    openFileChooser()
                } else {

                }
            }
        }
    }

    private fun loadWebPage() {
        webView.loadUrl(sharedPreferences!!.fortuneruri!!)
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_FILE_CHOOSER)
        }
    }

    inner class CustomWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            cookieManager.setCookie(url, sharedPreferences!!.cokies)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            Log.e("url",url!!)
            return super.shouldOverrideUrlLoading(view, url)
        }
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            CookieManager.getInstance().flush();
            sharedPreferences!!.cokies = cookieManager.getCookie(url)
        }
    }

    inner class CustomWebChromeClient : WebChromeClient() {
        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            fileChooserCallback = filePathCallback
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        this@SpinnerActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this@SpinnerActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        PERMISSION_REQUEST_CODE
                    )
                } else {
                    fileChooserCallback = filePathCallback
                    openFileChooser()
                }
            }
            return true
        }
    }
}