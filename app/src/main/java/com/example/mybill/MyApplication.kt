package com.example.mybill


import android.app.Application
import android.content.Context
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import com.example.mybill.model.MyUser


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        context = applicationContext

        Bmob.initialize(this, "ea3394541db389da987ae0077558e3fa")
        currentUser = BmobUser.getCurrentUser<MyUser>(MyUser::class.java)
    }

    companion object {
        var application: MyApplication? = null


        var context: Context? = null
            private set
        private var currentUser: MyUser? = null


        val currentUserId: String?
            get() {
                currentUser = BmobUser.getCurrentUser<MyUser>(MyUser::class.java)
                return if (currentUser == null) null else BmobUser.getCurrentUser<MyUser>(MyUser::class.java)
                    .objectId
            }
    }
}
