package com.example.scud

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
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("http://45.90.217.136/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        return retrofit!!
    }
}

class LoginActivity : AppCompatActivity() {
    public interface SCUDservice {
        @POST("login")
        fun login(@Body loginRequest: LoginRequest?): Call<LoginResponse?>?
    }
    class LoginRequest(
        @field:SerializedName("login") var login: String,
        @field:SerializedName("password") var password: String
    )
    class LoginResponse {
        @SerializedName("data")
        var data: Data? = null

        inner class Data {
            @SerializedName("token")
            var token: String? = null
        }

        @SerializedName("error")
        var error: String? = null

        @SerializedName("system")
        var system: System? = null

        inner class System {
            @SerializedName("time")
            var time = 0.0
        }
    }



    // Объявляем об использовании следующих объектов:
    private var username: EditText? = null
    private var password: EditText? = null
    private var login: Button? = null
    private var loginLocked: TextView? = null
    private var attempts: TextView? = null
    private var numberOfAttempts: TextView? = null

    // Число для подсчета попыток залогиниться:
    var numberOfRemainingLoginAttempts = 3

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

        // Если введенные логин и пароль будут словом "admin",
        // показываем Toast сообщение об успешном входе:
        val loginRequest = LoginRequest(login=username!!.text.toString(), password = password!!.text.toString())

        if (username!!.text.toString() == "admin" && password!!.text.toString() == "admin") {
            Toast.makeText(applicationContext, "Вход выполнен!", Toast.LENGTH_SHORT).show()

            // Выполняем переход на другой экран:
            val intent = Intent( this, MainActivity::class.java)
            startActivity(intent)
        } else {
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
                login!!.setClickable(false)
                loginLocked!!.visibility = View.VISIBLE
                loginLocked!!.setBackgroundColor(Color.RED)
                loginLocked!!.text = "Вход заблокирован!!!"
            }

        }
    }
}