package com.example.scud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts

class SplashActivityOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Создаем заставку на полный экран,
        makeFullScreen();
        //Подгружаем наш созданный макет в Activity
        setContentView(R.layout.activity_splash_one)
        //Планируем действие, а именно переход на MainActivity спустя 2 секунды
        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        },2000)
    }
    private fun makeFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //Делаем Activity на полный экран
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        //Скрываем actionbar
        supportActionBar?.hide()
    }
}