package com.fortuneOX.slots.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fortuneOX.slots.R
import com.fortuneOX.slots.ui.spin.SpinFragment
import com.fortuneOX.slots.ui.win.WinFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, SpinFragment.newInstance())
                .commit()
        }
    }
}