package com.example.mybill.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.bmob.v3.BmobUser.loginByAccount
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import com.example.mybill.model.MyUser

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult


    fun login(username: String, password: String){



        loginByAccount(username, password, object : LogInListener<MyUser?>() {
            override fun done(myUser: MyUser?, e: BmobException?) {
                if (e == null) {
                    _loginResult.value= LoginResult(login_success = myUser!!.username)

                } else {
                    _loginResult.value= LoginResult(login_error = e.message)
                }
            }
        })
    }


    fun sign(username: String, password: String, email:String){
        val user:MyUser = MyUser();
        user.username = username
        user.email = email
        user.setPassword(password)
        user.image = "https://img.icons8.com/doodle/48/000000/user-male--v1.png"

        user.signUp(object : SaveListener<MyUser?>() {
            override fun done(myUser: MyUser?, e: BmobException?) {
                if (e == null) {
                    _loginResult.value= LoginResult(sign_success = myUser!!.username)
                } else {
                    _loginResult.value= LoginResult(sign_error = e.message)
                }
            }
        })
    }
}