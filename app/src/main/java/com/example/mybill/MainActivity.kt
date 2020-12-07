package com.example.mybill

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import cn.bmob.v3.BmobUser
import cn.bmob.v3.BmobUser.isLogin
import cn.bmob.v3.BmobUser.logOut
import com.bumptech.glide.Glide
import com.example.mybill.adapter.MainPagerAdapter
import com.example.mybill.fragment.BillListFragment
import com.example.mybill.fragment.BillListViewModel
import com.example.mybill.login.LoginActivity
import com.example.mybill.model.MyUser
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import java.util.*


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    val LOGINACTIVITY_CODE:Int = 1
    val USERINFOACTIVITY_CODE:Int = 0
    private lateinit var drawer: DrawerLayout
    private lateinit var drawerIv: ImageView
    private lateinit var drawerTvAccount: TextView
    private lateinit var drawerTvMail: TextView
    private lateinit var currentUser:MyUser
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this





        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer= findViewById(R.id.main_drawer)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)




        val viewPager: ViewPager = findViewById(R.id.viewpager)
        val mainPagerAdapter = MainPagerAdapter(this, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = mainPagerAdapter


        tabLayout.setupWithViewPager(viewPager)



        val navigationView: NavigationView = findViewById(R.id.main_nav_view)
        val drawerHeader: View = navigationView.inflateHeaderView(R.layout.nav_header_main)
        drawerIv = drawerHeader.findViewById(R.id.drawer_iv)
        drawerTvAccount = drawerHeader.findViewById(R.id.drawer_name)
        drawerTvMail = drawerHeader.findViewById(R.id.drawer_click)

        setDrawerHeaderAccount()


        navigationView.setNavigationItemSelectedListener(this)


        //val currentUser: MyUser = BmobUser.getCurrentUser(MyUser::class.java)
        drawerHeader.setOnClickListener {
            if (!isLogin()) {
                startActivityForResult(

                    Intent(this, LoginActivity::class.java),
                    this.LOGINACTIVITY_CODE
                )

            } else {
                startActivityForResult(
                    Intent(context, SettingActivity::class.java),
                    this.USERINFOACTIVITY_CODE
                )
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_toolbar, menu)
        return true
    }

    val myCalendar = Calendar.getInstance()
    var date =
        OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth

        }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toolbar_date ->
                DatePickerDialog(
                    this, date, myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                ).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_exit -> {
                if (isLogin()) {
                    logOut()
                    Snackbar.make(
                        this.window.decorView,
                        "Successfully Log out!",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                    setDrawerHeaderAccount()

                    startActivity( Intent(this, MainActivity::class.java))
                } else {
                    Snackbar.make(
                        this.window.decorView,
                        "Please Login first.",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }

            R.id.nav_sync ->{
                if (isLogin()){
                    startActivity( Intent(this, MainActivity::class.java))
                }
                else {
                    Snackbar.make(
                        this.window.decorView,
                        "Please Login first.",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }

            }

            R.id.nav_setting -> {
                if (isLogin()) {
                    startActivityForResult(
                        Intent(context, SettingActivity::class.java),
                        this.USERINFOACTIVITY_CODE
                    )
                }
                else {
                    Snackbar.make(
                        this.window.decorView,
                        "Please Login first.",
                        Snackbar.LENGTH_LONG
                    )
                        .show()
                }
            }





        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                USERINFOACTIVITY_CODE -> setDrawerHeaderAccount()
                LOGINACTIVITY_CODE -> setDrawerHeaderAccount()
            }
        }
    }

    fun setDrawerHeaderAccount() {


        if (isLogin()) {
            currentUser = BmobUser.getCurrentUser(MyUser::class.java)
            drawerTvAccount.setText(currentUser.getUsername())
            drawerTvMail.setText(currentUser.getEmail())
            Glide.with(context).load(currentUser.image).into(drawerIv)
        }
        else {
            drawerTvAccount.text = "Account"
            drawerTvMail.text = "Click to login"
            drawerIv.setImageResource(R.mipmap.ic_launcher_round)
        }
    }



}






