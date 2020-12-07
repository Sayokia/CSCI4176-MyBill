package com.example.mybill.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.bmob.v3.BmobUser.getCurrentUser
import com.example.mybill.MainActivity
import com.example.mybill.R
import com.example.mybill.model.MyUser
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // get all elements
        val email:EditText = findViewById(R.id.login_et_email)
        val username:EditText = findViewById(R.id.login_et_username)
        val password:EditText = findViewById(R.id.login_et_password)
        val repeatedPassword:EditText = findViewById(R.id.login_et_rpassword)
        val signUp:TextView = findViewById(R.id.login_tv_sign)
        val loginBTN:Button = findViewById(R.id.login_btn_login)
        val layout:RelativeLayout = findViewById(R.id.rl_login_activity)



        val loginViewModel:LoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)


        // set up progress bar
        val params : RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, params)



        // check if is login or signup
        var isLogin = true;


        // set up onclick event to switch between signUp and login
        val clickListener = View.OnClickListener { v: View? ->

            if (v != null) {
                when(v.id){
                    R.id.login_btn_login -> if (isLogin) {
                        val username1 = username.text.toString()
                        val password1 = password.text.toString()
                        if (username1.isEmpty() || password1.isEmpty()) {
                            Snackbar.make(
                                v,
                                "Username or Password can not be empty",
                                Snackbar.LENGTH_SHORT
                            )
                        } else {
                            progressBar.visibility = View.VISIBLE

                            loginViewModel.login(username1, password1)
                        }


                    } else {
                        val email1 = email.text.toString()
                        val username1 = username.text.toString()
                        val password1 = password.text.toString()
                        val rpassword1 = repeatedPassword.text.toString()

                        if (email1.isEmpty() || username1.isEmpty() || password1.isEmpty() || rpassword1.isEmpty()) {
                            Snackbar.make(v, "Please fill all the fields", Snackbar.LENGTH_SHORT)
                        }
                        if (!checkEmail(email1)) {
                            Snackbar.make(
                                v,
                                "Please enter a correct email address",
                                Snackbar.LENGTH_SHORT
                            )
                        }
                        if (password1 != rpassword1) {
                            Snackbar.make(v, "Passwords do not match", Snackbar.LENGTH_SHORT)
                        } else {
                            progressBar.visibility = View.VISIBLE
                            loginViewModel.sign(username1, password1, email1)
                        }


                    }
                    R.id.login_tv_sign -> {
                        if (isLogin) {
                            //change to sign up page
                            signUp.text = "Login"
                            loginBTN.text = "Sign Up"
                            repeatedPassword.visibility = View.VISIBLE
                            email.visibility = View.VISIBLE
                        } else {
                            //change to login page
                            signUp.text = "Sign Up"
                            loginBTN.text = "Login"
                            repeatedPassword.visibility = View.GONE
                            email.visibility = View.GONE
                        }
                        isLogin = !isLogin
                    }


                }
            }

        }
        //bind onclick listener
        signUp.setOnClickListener(clickListener)
        loginBTN.setOnClickListener(clickListener)

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            progressBar.visibility = View.GONE
            if (loginResult.login_error != null) {
                Snackbar.make(
                    this.window.decorView,
                    "Login failed!" + loginResult.login_error,
                    Snackbar.LENGTH_LONG
                ).show();


            }
            if (loginResult.login_success != null) {
                val currentUser: MyUser = getCurrentUser(MyUser::class.java)
                Snackbar.make(
                    this.window.decorView,
                    "Login Success! Welcome " + currentUser.username + " !",
                    Snackbar.LENGTH_LONG
                ).show();
                val handler = Handler()
                handler.postDelayed(Runnable {
                    startActivity(Intent(this, MainActivity::class.java))
                }, 1500)


            }
            if (loginResult.sign_success != null) {

                Snackbar.make(
                    this.window.decorView,
                    "Sign up Success! Please Login!",
                    Snackbar.LENGTH_LONG
                ).show();


            }
            if (loginResult.sign_error != null) {
                Snackbar.make(
                    this.window.decorView,
                    "Sign up failed!" + loginResult.sign_error,
                    Snackbar.LENGTH_LONG
                ).show();
            }


        })

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