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
import kotlinx.coroutines.flow.collect
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


class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding

    private val classinfoDatabase by lazy { ClassInfoDB.getDatabase(this).infoDAO() }
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        //startActivity(Intent(this,NfcCardEmulationMenu::class.java))
        setContentView(binding.root)
        parseJSON(LogclassInfo(token,userid))

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
                        items.forEach {
                            classinfoDatabase.addclassInfo(items)
                        }
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.recyclerView.adapter = ClassRoomRecyclerAdapter(items, this@MainActivity)
                    }
                } else {
                    Log.d("btn",response.message())
                    Log.d("btn", response.code().toString())
                    Log.d("btn", response.body().toString())
                    val temper:List<ClassInfo> = arrayListOf()
                    classinfoDatabase.getclassInfo().collect{classinfoList ->
                        if (classinfoList.isNotEmpty()) {
                            classinfoList.forEach {

                                temper.toMutableList().add(it)
                            }
                        }
                    }
                    binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.recyclerView.adapter = ClassRoomRecyclerAdapter(temper, this@MainActivity)
                }
            }
        }

    }
    override fun onItemClicked(classInfo: ClassInfo) {
        //Toast.makeText(this,"ClassRoomName ${classInfo.classroomName} \n ClassRoomType: ${classInfo.classroomType}",Toast.LENGTH_LONG)
         //   .show()
        Log.d("btn11",classInfo.classroomKey.toString())
        val intent = Intent( this, NfcCardEmulationMenu::class.java)
        intent.putExtra("classroomKey", classInfo.classroomKey.toString());
        intent.putExtra("login", login);
        intent.putExtra("password", password);
        intent.putExtra("token", token);
        intent.putExtra("userid", userid);
        startActivity(intent)
    }



}


