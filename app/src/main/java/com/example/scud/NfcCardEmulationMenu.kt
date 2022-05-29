
package com.example.scud

import android.R
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.colin.skud.NfcCardEmulation



class NfcCardEmulationMenu : AppCompatActivity() {

    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }
    var login: String? = null
    var password: String? = null
    var token: String?  = null
    var userid: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login= intent.getStringExtra("login")
        password= intent.getStringExtra("password") // 2
        token = intent.getStringExtra("token") // 2
        userid = intent.getStringExtra("userid") // 2
        Log.d("SERVICE", "ServiceStarted")
        val intentNfc = Intent(this, NfcCardEmulation::class.java)
        intentNfc.putExtra("NFC_TOKEN", intent.getStringExtra("classroomKey"));
        Log.d("btn", intent.getStringExtra("classroomKey").toString())
        startService(intentNfc)
        setContentView(com.example.scud.R.layout.activity_nfc_card_emulation_menu)
    }

    fun NFCsend(view: View) {
        val intent = Intent( this, MainActivity::class.java)
        intent.putExtra("login", login);
        intent.putExtra("password", password);
        intent.putExtra("token", token);
        intent.putExtra("userid", userid);
        startActivity(intent)
        }
}


