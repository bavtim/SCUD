package com.example.scud

import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


data class LogclassInfo(
    @SerializedName("token") val token: String?,
    @SerializedName("userid") val userid: String?
    )

data class ClassInfo (

    @SerializedName("_id") val _id: String?,
    @SerializedName("user") val user: String?,
    @SerializedName("classroom") val classroom: String?,
    @SerializedName("classroomName") val classroomName: String?,
    @SerializedName("classroomType") val classroomType: String?,
    @SerializedName("classroomKey") val classroomKey: String?,
    @SerializedName("__v") val __v: String?

)
interface RestApiGetClass {

    @Headers("Content-Type: application/json")
    @POST("reservation/available")
    fun ClassUser(): Call<ArrayList<ClassInfo>>
}


class MainActivity : AppCompatActivity() {
    private val recyclerDataArrayList: ArrayList<ClassInfo>? = null
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var login: String? = intent.getStringExtra("login")
        var password: String? = intent.getStringExtra("password") // 2
        var token: String? = intent.getStringExtra("token") // 2
        var userid: String? = intent.getStringExtra("userid") // 2
        Log.d("SERVICE222", login+password+token+"   " +userid)
        ClassUser()
        Log.d("SERVICE", login+password+token+"   " +userid)
        //startActivity(Intent(this,NfcCardEmulationMenu::class.java))
    }
    private fun ClassUser() {
        // on below line we are creating a retrofit
        // builder and passing our base url
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonkeeper.com/b/") // on below line we are calling add
            // Converter factory as Gson converter factory.
            .addConverterFactory(GsonConverterFactory.create()) // at last we are building our retrofit builder.
            .build()
        // below line is to create an instance for our retrofit api class.
        val retrofitAPI: RestApiGetClass = retrofit.create(RestApiGetClass::class.java)

        // on below line we are calling a method to get all the courses from API.
        val call: Call<ArrayList<ClassInfo>> = RestApiGetClass.ClassUser()

        // on below line we are calling method to enqueue and calling
        // all the data from array list.
        call.enqueue(object : Callback<ArrayList<ClassInfo?>> {
            override fun onResponse(
                call: Call<ArrayList<ClassInfo?>>,
                response: Response<ArrayList<ClassInfo?>>
            ) {
                // inside on response method we are checking
                // if the response is success or not.
                if (response.isSuccessful) {

                    // below line is to add our data from api to our array list.
                    val recyclerDataArrayList = response.body()

                    // below line we are running a loop to add data to our adapter class.

                    Log.i("SERVICE1231", recyclerDataArrayList.toString())

                }
            }

            override fun onFailure(call: Call<ArrayList<ClassInfo?>>, t: Throwable) {
                // in the method of on failure we are displaying a
                // toast message for fail to get data.
                Toast.makeText(this@MainActivity, "Fail to get data", Toast.LENGTH_SHORT).show()
            }
        })
    }

}




