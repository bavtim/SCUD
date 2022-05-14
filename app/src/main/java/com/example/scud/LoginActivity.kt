package com.example.scud

import android.util.Log
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Headers

data class UserInfo (
    @SerializedName("token") val token: String?,
    @SerializedName("login") val login: String?,
    @SerializedName("password") val password: String?
)
interface RestApi {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun LoginUser(@Body userData: UserInfo): Call<UserInfo>
}

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://45.90.217.136/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}
class RestApiService {
    fun LoginUser(userData: UserInfo, onResult: (UserInfo?) -> Unit){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.LoginUser(userData).enqueue(
            object : Callback<UserInfo> {
                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    //Log.i("pizdanull", t.toString())
                    onResult(null)
                }
                override fun onResponse( call: Call<UserInfo>, response: Response<UserInfo>) {
                    val addedUser = response.body()
                    //Log.i("pizda1231", response.body().toString())
                    onResult(addedUser)
                }
            }
        )
    }
}

class LoginActivity : AppCompatActivity() {

    // Объявляем об использовании следующих объектов:
    private var username: EditText? = null
    private var password: EditText? = null
    private var login: Button? = null
    private var loginLocked: TextView? = null
    private var attempts: TextView? = null
    private var numberOfAttempts: TextView? = null

    // Число для подсчета попыток залогиниться:
    var numberOfRemainingLoginAttempts = 999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.scud.R.layout.activity_login)

        // Связываемся с элементами нашего интерфейса:
        username = findViewById<View>(com.example.scud.R.id.edit_user) as EditText
        password = findViewById<View>(com.example.scud.R.id.edit_password) as EditText
        login = findViewById<View>(com.example.scud.R.id.button_login) as Button
        loginLocked = findViewById<View>(com.example.scud.R.id.login_locked) as TextView
        attempts = findViewById<View>(com.example.scud.R.id.attempts) as TextView
        numberOfAttempts = findViewById<View>(com.example.scud.R.id.number_of_attempts) as TextView
        numberOfAttempts!!.text = Integer.toString(numberOfRemainingLoginAttempts)
    }

    // Обрабатываем нажатие кнопки "Войти":
    fun Login(view: View?) {
        val apiService = RestApiService()
        val userInfo = UserInfo(
            token = null,
            login=username!!.text.toString(),
            password = password!!.text.toString())
        var token ="-1"

        apiService.LoginUser(userInfo) {

            if (it?.token != null) {
                Log.i("pizda",token)
                Toast.makeText(applicationContext, "Вход выполнен!", Toast.LENGTH_SHORT).show()

                // Выполняем переход на другой экран:
                val intent = Intent( this, MainActivity::class.java)
                startActivity(intent)

            } else {
                Log.e("pizda","pizda")
                Toast.makeText(applicationContext, "Неправильные данные!", Toast.LENGTH_SHORT).show()
                numberOfRemainingLoginAttempts--

                // Делаем видимыми текстовые поля, указывающие на количество оставшихся попыток:
                attempts!!.visibility = View.VISIBLE
                numberOfAttempts!!.visibility = View.VISIBLE
                numberOfAttempts!!.text = Integer.toString(numberOfRemainingLoginAttempts)

                // Когда выполнено 3 безуспешных попытки залогиниться,
                // делаем видимым текстовое поле с надписью, что все пропало и выставляем
                // кнопке настройку невозможности нажатия setEnabled(false):
                if (numberOfRemainingLoginAttempts == 0) {
                    login!!.isClickable = false
                    loginLocked!!.visibility = View.VISIBLE
                    loginLocked!!.setBackgroundColor(Color.RED)
                    loginLocked!!.text = "Вход заблокирован!!!"
                }
            }
        }
        // Если введенные логин и пароль будут словом "admin",
        // показываем Toast сообщение об успешном входе:


    }
}