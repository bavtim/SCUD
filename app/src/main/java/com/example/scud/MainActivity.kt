package com.example.scud

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scud.databinding.ActivityMainBinding
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Headers
import retrofit2.http.POST



data class LogclassInfo(
    @SerializedName("token") val token: String?,
    @SerializedName("userid") val userid: String?
    )


interface RestApiGetClass {

    @Headers("Content-Type: application/json")
    @POST("reservation/available")
    fun getUserData(): Call<MutableList<ClassInfo>>
}


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //startActivity(Intent(this,NfcCardEmulationMenu::class.java))
        setContentView(binding.root)
        var login: String? = intent.getStringExtra("login")
        var password: String? = intent.getStringExtra("password") // 2
        var token: String? = intent.getStringExtra("token") // 2
        var userid: String? = intent.getStringExtra("userid") // 2
        Log.d("SERVICE222", login+password+token+"   " +userid)
        parseJSON(LogclassInfo(token,userid))
        Log.d("SERVICE", login+password+token+"   " +userid)

    }


    fun parseJSON(l:LogclassInfo) {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://45.90.217.136:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)
        val params = HashMap<String?, String?>()
        params["token"] = l.token
        params["userid"] = l.userid


        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = service.getEmployees(params )

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    val items = response.body()
                    if (items != null) {
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.recyclerView.adapter = ClassRoomRecyclerAdapter(items)
                    }

                } else {

                    Log.e("RETROFIT", response.message())
                    Log.e("RETROFIT", response.code().toString())
                    Log.e("RETROFIT", response.body().toString())
                }
            }
        }
    }

}




