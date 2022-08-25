package com.satya.movee.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.satya.movee.R
import com.satya.movee.constants.Constant.Companion.sharedPrefFile

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val actionBar = supportActionBar
        actionBar?.hide()


        //for now we are using only navigation

 //       val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        //val name = sharedPreferences.getString("name","")

//        if(name!!.isEmpty()) {
//            Handler(Looper.getMainLooper()).postDelayed({
//                val intent = Intent(this, LoginActivity::class.java)
//                startActivity(intent)
//                finish()
//            }, 3000)
//        } else {
//            Handler(Looper.getMainLooper()).postDelayed({
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }, 3000)
//        }

//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}