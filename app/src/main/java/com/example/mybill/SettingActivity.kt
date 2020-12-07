package com.example.mybill

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.UpdateListener
import com.bumptech.glide.Glide
import com.example.mybill.model.MyUser
import com.google.android.material.snackbar.Snackbar
import com.wildma.pictureselector.PictureBean
import com.wildma.pictureselector.PictureSelector
import java.util.regex.Pattern


open class SettingActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var imagePath:String
    private lateinit var updateBtn:Button
    private lateinit var  currentUser: MyUser
    private lateinit var avatarIv: ImageView
    private lateinit var usernameTv:TextView
    private lateinit var emailEd:EditText
    private lateinit var avatarRL:RelativeLayout
    private lateinit var email:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        // bind view
        toolbar = findViewById(R.id.toolbar)
        avatarIv = findViewById(R.id.img_avatar)
        usernameTv = findViewById(R.id.username_tv_setting)
        emailEd = findViewById(R.id.emial_ed_setting)
        avatarRL = findViewById(R.id.rlt_update_avatar)
        updateBtn = findViewById(R.id.update_button)

        // initialize MyuUer
        currentUser = BmobUser.getCurrentUser(MyUser::class.java)

        //set up toolbar
        toolbar.title = "Account Setting"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            setResult(RESULT_OK, Intent())
            finish()
        }

        //set current avatar
        Glide.with(this).load(currentUser.image).into(avatarIv)
        //load user info
        usernameTv.text = currentUser.username
        emailEd.setText(currentUser.email)

        val clickListener = View.OnClickListener { v: View? ->
            if (v != null) {
                when(v.id){
                    R.id.rlt_update_avatar ->
                        PictureSelector
                            .create(this, PictureSelector.SELECT_REQUEST_CODE)
                            .selectPicture(false);
                    R.id.username_tv_setting ->
                        Snackbar.make(v, "You can not change your username", Snackbar.LENGTH_SHORT)
                            .show()



                }

            }
        }


        avatarRL.setOnClickListener(clickListener)
        usernameTv.setOnClickListener(clickListener)

        updateBtn.setOnClickListener(){
            update()
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                val pictureBean: PictureBean =
                    data.getParcelableExtra(PictureSelector.PICTURE_RESULT)
                    avatarIv.setImageURI(pictureBean.uri)
                    currentUser.image = pictureBean.uri.toString()

            }
        }
    }

    fun update(){
        val email = emailEd.text.toString()
        if(checkEmail(email)) {
            currentUser.email = emailEd.text.toString()
            currentUser.update(currentUser.getObjectId(), object : UpdateListener() {
                override fun done(e: BmobException?) {
                    if (e != null) {
                        Log.e("e", e.message)
                        Toast.makeText(applicationContext, "Update Failed", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(applicationContext, "Update Success", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            })
        }
        else{
            Toast.makeText(applicationContext, "Email Not Valid", Toast.LENGTH_SHORT)
                .show()
        }
    }



    companion object {
        protected const val CHOOSE_PICTURE = 1
    }

    fun checkEmail(email: String?): Boolean {
        var flag = false
        flag = try {
            // regex referred to https://stackoverflow.com/questions/8204680/java-regex-email
            val check =
                "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
            val regex = Pattern.compile(check)
            val matcher = regex.matcher(email)
            matcher.matches()
        } catch (e: Exception) {
            false
        }
        return flag
    }
}