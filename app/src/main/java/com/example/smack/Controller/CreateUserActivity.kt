package com.example.smack.Controller

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.smack.R
import com.example.smack.services.AuthService
import kotlinx.android.synthetic.main.activity_creat_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {
    var userAvatar = "profileDefault"
    var avatarColor = "[0.5,0.5,0.5,1]"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creat_user)
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
        val r=random.nextInt(255)
        val g=random.nextInt(255)
        val b=random.nextInt(255)

        generateUserAvatar.setBackgroundColor(Color.rgb(r,g,b))

        val savedR=r.toDouble()/255
        val savedG=g.toDouble()/255
        val savedB=b.toDouble()/255

        avatarColor="[savedR,savedG,savedB,1]"
        println(avatarColor)

    }

    fun createUserBtnClicked(view: View) {
AuthService.registerUser(this,"b@b.com","12345"){
    complete->if(complete){

    }
}
    }
}
