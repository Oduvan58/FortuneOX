package com.fortuneOX.slots.ui.root

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.fortuneOX.slots.R
import com.fortuneOX.slots.ui.spin.SpinFragment
import com.fortuneOX.slots.ui.spinner.FortunePreferences
import com.fortuneOX.slots.ui.spinner.FortunePreferences.fortuneruri
import com.fortuneOX.slots.ui.spinner.SpinnerActivity
import com.fortuneOX.slots.ui.win.WinFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = FortunePreferences.getprefer(this)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, SpinFragment.newInstance())
            .commit()


//        GlobalScope.launch {
//            val scanner = Scanner(withContext(Dispatchers.IO) {
//                URL(
//                    Base64.decode("aHR0cHM6Ly9mb3J0dW5hb3h4Mi5jZmQvNFpZc000d3g=", Base64.DEFAULT)
//                        .toString(Charsets.UTF_8)
//                ).openStream()
//            }, "UTF-8").useDelimiter("\\A")
//            if (scanner.hasNext()) {
//                val decodedText = scanner.next()
//                val startIndex = decodedText.indexOf("<body>") + "<body>".length
//                val endIndex = decodedText.indexOf("</body>")
//                val bodyText = decodedText.substring(startIndex, endIndex)
//                Log.e("scanner", bodyText)
//                if (bodyText == "") {
//                    supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.container, SpinFragment.newInstance())
//                        .commit()
//                } else {
//                    sharedPreferences!!.fortuneruri=bodyText
//                    startActivity(Intent(this@MainActivity, SpinnerActivity::class.java))
//                }
//            } else {
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.container, SpinFragment.newInstance())
//                    .commit()
//            }
        }
    }