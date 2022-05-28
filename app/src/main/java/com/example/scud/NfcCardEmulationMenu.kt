
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SERVICE", "ServiceStarted")
        val intent = Intent(this, NfcCardEmulation::class.java)
        intent.putExtra("NFC_TOKEN", "AFAFAF");
        startService(intent)
        setContentView(com.example.scud.R.layout.activity_nfc_card_emulation_menu)
    }

    fun NFCsend(view: View) {
        startActivity(Intent(this,MainActivity::class.java))
        }
}


