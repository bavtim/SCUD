package com.colin.skud

import android.app.Service
import android.content.Intent

import android.content.Intent.*
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.nfc.cardemulation.HostApduService
import android.os.Bundle
import android.util.Log
import com.example.scud.NfcCardEmulationMenu


class NfcCardEmulation : HostApduService() {
    var NFC_TOKEN:String="9000"
    override fun onCreate() {
        super.onCreate()




    }
    companion object {
        //val TAG = "Host Card Emulator"a
        val STATUS_FAILED = "6F00"
        val STATUS_SUCCESS = "9000"
        //val CLA_NOT_SUPPORTED = "6E00"
        //val INS_NOT_SUPPORTED = "6D00"
        val AID = "F0010203040506"
        //val SELECT_INS = "A4"
        //val DEFAULT_CLA = "00"
        //val MIN_APDU_LENGTH = 1
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        NFC_TOKEN += intent!!.extras!!.getString("NFC_TOKEN")
        Log.d("SERVICE111", NFC_TOKEN)
        return Service.START_STICKY
    }

    override fun processCommandApdu(commandApdu: ByteArray, extras: Bundle?): ByteArray {
        val hexCommandApdu = Utils.toHex(commandApdu)
        val test = hexCommandApdu.substring(10, 24);
        Log.d("NFC", "FOUND: $test")

        if (hexCommandApdu.substring(10, 24) == AID)  {
            return Utils.hexStringToByteArray(NFC_TOKEN)
        } else {
            return Utils.hexStringToByteArray(STATUS_FAILED)
        }

    }

    override fun onDeactivated(reason: Int) {
        Log.d("NFC", "Deactivated: " + reason)
    }
}