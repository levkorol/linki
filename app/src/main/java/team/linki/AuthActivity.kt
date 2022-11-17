package team.linki

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthActivity : AppCompatActivity() {

    private lateinit var activityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


        val authGoogle = findViewById<AppCompatButton>(R.id.auth_google)

        authGoogle.setOnClickListener {
            retrofit.authGoogle(AuthBody("google", 1, "", "", ""))
                .enqueue(object : Callback<AuthResp> {
                    override fun onResponse(call: Call<AuthResp>, response: Response<AuthResp>) {

                    }

                    override fun onFailure(call: Call<AuthResp>, t: Throwable) {
                      return Unit
                    }

                })

        }
    }

    private fun doAuthorization() {

        val serviceConfig =
            AuthorizationServiceConfiguration(
                Uri.parse("//todo"),
                Uri.parse("//todo")
            )

        val request = AuthorizationRequest.Builder(
            serviceConfig,
            " ",
            ResponseTypeValues.CODE,
            Uri.parse("://auth")
        ).setState(null)
            .setScopes(
                AuthorizationRequest.Scope.EMAIL,
                AuthorizationRequest.Scope.PROFILE,
                AuthorizationRequest.Scope.OPENID
            ).build()


        val authService = AuthorizationService(this)
        val authIntent = authService.getAuthorizationRequestIntent(
            request,
            authService.createCustomTabsIntentBuilder(request.toUri())
                .setUrlBarHidingEnabled(false)
                .setShowTitle(false)
                .setDefaultShareMenuItemEnabled(false)
                .setToolbarColor(
                    ContextCompat.getColor(this, R.color.black)
                ).build()
        )

        activityLauncher.launch(authIntent)
    }

}