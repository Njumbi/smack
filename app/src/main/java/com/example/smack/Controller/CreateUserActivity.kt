package com.example.smack.Controller

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.smack.R
import com.example.smack.services.AuthService
import com.example.smack.services.UserDataService
import com.example.smack.services.UserDataService.id
import com.example.smack.utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_creat_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {
    var userAvatar = "profileDefault"
    var avatarColor = "[0.5,0.5,0.5,1]"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_user)
        createSpinner.visibility = View.INVISIBLE
    }

    fun generateUserAvatarClicked(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)
        userAvatar = if (color == 0) {
            "light$avatar"
        } else {
            "dark$avatar"
        }
        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        generateUserAvatar.setImageResource(resourceId)
    }

    fun generateBackgroundColorBtnClicked(view: View) {
        val random = Random()
        val r = random.nextInt(255)
        val g = random.nextInt(255)
        val b = random.nextInt(255)

        generateUserAvatar.setBackgroundColor(Color.rgb(r, g, b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[savedR,savedG,savedB,1]"
        println(avatarColor)

    }

    fun createUserBtnClicked(view: View) {
        enableSpinner(true)
        val userName = createUserNameTxt.text.toString()
        val email = createUserNameEmail.text.toString()
        val password = createUserNamePassword.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
            AuthService.registerUser(this, email, password) { registerSuccess ->
                if (registerSuccess) {
                    AuthService.loginUser(this, email, password) { loginSuccess ->
                        if (loginSuccess) {
                            AuthService.createUser(
                                this,
                                userName,
                                email,
                                userAvatar,
                                avatarColor
                            ) { createSuccess ->
                                if (createSuccess) {
                                    val userDataChange= Intent(BROADCAST_USER_DATA_CHANGE)
                          LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    errorToast()
                                }

                            }
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }
        }else{
            Toast.makeText(this,"make sure user name, email and password are filled in",Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }

    }


   private fun errorToast() {
        Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    private fun enableSpinner(enable: Boolean) {
        if (enable) {
            createSpinner.visibility = View.VISIBLE

        } else {
            createSpinner.visibility = View.INVISIBLE

        }
        createUserBtn.isEnabled = !enable
        generateUserAvatar.isEnabled = !enable
        generateBackgroundColorBtn.isEnabled = !enable

    }
}
