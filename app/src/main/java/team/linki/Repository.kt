package team.linki

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

val client = OkHttpClient
    .Builder()
    .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.linki.team")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
    .create(Endpoints::class.java)

interface Endpoints {

    @HTTP(method = "GET", path = "/api/user/social/google", hasBody = true)
    fun authGoogle(@Body body: AuthBody): Call<AuthResp>

    @POST("/api/user/social/facebook")
    fun authFacebook(@Body body: AuthBody): Call<Unit>

}

class AuthBody(
    val sProvide: String,
    val role: Int,
    val referal_code: String,
    val register_code: String,
    val manager_team_code: String
)

class AuthResp(
    val url: String
)


