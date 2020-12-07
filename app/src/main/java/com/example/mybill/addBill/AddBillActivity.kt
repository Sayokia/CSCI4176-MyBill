package com.example.mybill.addBill

import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import cn.bmob.v3.BmobUser
import com.example.mybill.MainActivity
import com.example.mybill.R
import com.example.mybill.adapter.AddBillPagerAdapter
import com.example.mybill.model.MyUser
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.lang.Double.parseDouble
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


open class AddBillActivity : AppCompatActivity() {



    var isIncome:Boolean = false
    lateinit var negative:ImageView
    val currentUser:MyUser = BmobUser.getCurrentUser(MyUser::class.java)
    // elements
    lateinit var typeName:TextView
    lateinit var typeIv:ImageView
    private lateinit var date:TextView
    private lateinit var amountTv:TextView

    //calculator
    private var isDot = false
    private var num = "0" //int part
    private var dotNum = ".00" //float part
    private val MAX_NUM = 9999999 //maximum int
    private val DOT_NUM = 2 //maximum
    private var count = 0

    private lateinit var cal1:TextView
    private lateinit var cal2:TextView
    private lateinit var cal3:TextView
    private lateinit var cal4:TextView
    private lateinit var cal5:TextView
    private lateinit var cal6:TextView
    private lateinit var cal7:TextView
    private lateinit var cal8:TextView
    private lateinit var cal9:TextView
    private lateinit var cal0:TextView
    private lateinit var clearIv:ImageView
    private lateinit var deleteRl:RelativeLayout
    private lateinit var dotTv:TextView
    private lateinit var confirm:TextView

    private lateinit var  addBillViewModel: AddBillViewModel






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bill)

        negative = findViewById(R.id.note_negative)

        addBillViewModel = ViewModelProvider(this).get(AddBillViewModel::class.java)
        val tabLayout: TabLayout = findViewById(R.id.addTabLayout)
        val viewPager: ViewPager = findViewById(R.id.viewpager_item)
        val addBillPagerAdapter = AddBillPagerAdapter(this, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = addBillPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                isIncome = tab.position != 0
                Log.i("1", isIncome.toString())

                // clear all selected resource
                doClear()
                typeIv.setImageResource(R.drawable.ic_baseline_add_box_24)
                typeIv.tag = "Default"
                typeName.text = "Default"

                if (tab.position == 0) {
                    negative.visibility = View.VISIBLE

                } else {
                    negative.visibility = View.GONE
                }


            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })




        //bind view
        typeIv = findViewById(R.id.item_type_iv)
        typeName = findViewById(R.id.note_cash)
        date = findViewById(R.id.note_date)
        amountTv = findViewById(R.id.note_money)
        cal1 = findViewById(R.id.calc_num_1)
        cal2 = findViewById(R.id.calc_num_2)
        cal3 = findViewById(R.id.calc_num_3)
        cal4 = findViewById(R.id.calc_num_4)
        cal5 = findViewById(R.id.calc_num_5)
        cal6 = findViewById(R.id.calc_num_6)
        cal7 = findViewById(R.id.calc_num_7)
        cal8 = findViewById(R.id.calc_num_8)
        cal9 = findViewById(R.id.calc_num_9)
        cal0 = findViewById(R.id.calc_num_0)
        clearIv = findViewById(R.id.note_clear)
        deleteRl = findViewById(R.id.calc_num_del)
        dotTv = findViewById(R.id.calc_num_dot)
        confirm = findViewById(R.id.calc_num_done)








        //set up initial date
        val myCalendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDateandTime: String = sdf.format(Date())
        date.text = currentDateandTime




        var dateData =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                date.text = sdf.format(myCalendar.time)
            }

        val clickListener = View.OnClickListener { v: View ->
            when(v.id) {
                R.id.note_date -> {
                    DatePickerDialog(
                        this, dateData, myCalendar[Calendar.YEAR],
                        myCalendar[Calendar.MONTH],
                        myCalendar[Calendar.DAY_OF_MONTH]


                    ).show()
                }

                R.id.calc_num_1 -> calcMoney(1)
                R.id.calc_num_2 -> calcMoney(2)
                R.id.calc_num_3 -> calcMoney(3)
                R.id.calc_num_4 -> calcMoney(4)
                R.id.calc_num_5 -> calcMoney(5)
                R.id.calc_num_6 -> calcMoney(6)
                R.id.calc_num_7 -> calcMoney(7)
                R.id.calc_num_8 -> calcMoney(8)
                R.id.calc_num_9 -> calcMoney(9)
                R.id.calc_num_0 -> calcMoney(0)
                R.id.calc_num_dot -> {
                    if (dotNum == ".00") {
                        isDot = true
                        dotNum = "."
                    }
                    amountTv.text = num + dotNum
                }
                R.id.note_clear -> doClear()
                R.id.calc_num_del -> doDelete()
                R.id.calc_num_done -> commitChange()



            }
            }


        date.setOnClickListener(clickListener)
        cal1.setOnClickListener(clickListener)
        cal2.setOnClickListener(clickListener)
        cal3.setOnClickListener(clickListener)
        cal4.setOnClickListener(clickListener)
        cal5.setOnClickListener(clickListener)
        cal6.setOnClickListener(clickListener)
        cal7.setOnClickListener(clickListener)
        cal8.setOnClickListener(clickListener)
        cal9.setOnClickListener(clickListener)
        cal0.setOnClickListener(clickListener)
        clearIv.setOnClickListener(clickListener)
        deleteRl.setOnClickListener(clickListener)
        dotTv.setOnClickListener(clickListener)
        confirm.setOnClickListener(clickListener)



//        iconViewModel.iconResult.observe(this@AddBillActivity, Observer {
//            val iconResult = it ?: return@Observer
//            val selectedName = iconResult.name
//            typeName.text = selectedName
//        })


//        val extras = intent.extras
//        if (extras != null) {
//            selectedImg = intent.getStringExtra("selectedImg")
//            selectedName = intent.getStringExtra("selectedName")
//            Log.i("111",selectedImg.toString())
//            val parseUri: Uri = Uri.parse(selectedImg)
//            Glide.with(this).load(parseUri).into(typeIv)
//            typeName.text = selectedName
//        }



//        val nameObserver = Observer<String> { newName ->
//            // Update the UI, in this case, a TextView.
//            typeName.text  = newName
//        }
//
//        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
//        iconViewModel.selectedName.observe(this, nameObserver)


//        val confirm:TextView = findViewById(R.id.calc_num_done)
//        val clickListener = View.OnClickListener { v: View ->
//            when(v.id) {
//                R.id.calc_num_done ->
//                    Log.i(" 123", selectedName.toString())
//            }
//        }
//
//        confirm.setOnClickListener(clickListener)



        val back_btn : ImageView = findViewById(R.id.back_btn)

        back_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        addBillViewModel.addBillResult.observe(this@AddBillActivity, androidx.lifecycle.Observer {
            val addBillResult = it ?: return@Observer


            if (addBillResult.success != null) {
                Snackbar.make(
                    this.window.decorView,
                    "Bill Added successfully!",
                    Snackbar.LENGTH_LONG
                ).show();
            } else {
                Snackbar.make(
                    this.window.decorView,
                    "Bill Add failed! " + addBillResult.error,
                    Snackbar.LENGTH_LONG
                ).show();
            }
        })




    }

     private fun calcMoney(money: Int) {
        if (num == "0" && money == 0) return
        if (isDot) {
            if (count < DOT_NUM) {
                count++
                dotNum += money
                amountTv.text = num + dotNum
            }
        } else if (num.toInt() < MAX_NUM) {
            if (num == "0") num = ""
            num += money
            amountTv.text = num + dotNum
        }
    }

    private fun doClear() {
        num = "0"
        count = 0
        dotNum = ".00"
        isDot = false
        amountTv.text = "0.00"
    }

    fun doDelete() {
        if (isDot) {
            if (count > 0) {
                dotNum = dotNum.substring(0, dotNum.length - 1)
                count--
            }
            if (count == 0) {
                isDot = false
                dotNum = ".00"
            }
            amountTv.text = num + dotNum
        } else {
            if (num.length > 0) num = num.substring(0, num.length - 1)
            if (num.length == 0) num = "0"
            amountTv.text = num + dotNum
        }
    }

    fun commitChange() {

        val billDateString:String = date.text.toString()


//        val formatter: DateTimeFormatter =
//            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
//        val date: LocalDateTime = LocalDateTime.parse(billDateString, formatter)
//        val format: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
//        val billDate : Date = format.parse(billDateString)
//        Log.i("123",billDate.toString())



        if (num + dotNum == "0.00") {
            Toast.makeText(this, "Emmm, Seems you did not enter the amount", Toast.LENGTH_SHORT).show()
            return
        }
        if (typeName.text.toString() == "Default" || typeIv.tag == "Defalt"){
            Toast.makeText(
                this,
                "Emmm, Seems you did not select a category of your bill type ",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        else{
            val img:Int = Integer.valueOf(typeIv.tag.toString())
            val typeName:String = typeName.text.toString()
            val amount:Double = parseDouble(amountTv.text.toString())


            // update bill
            addBillViewModel.uploadBill(billDateString, isIncome, typeName, img, currentUser, amount)
        }


    }




    private fun imageTranslateUri(resId: Int): String {
        val r: Resources = resources
        val uri: Uri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + r.getResourcePackageName(resId) + "/"
                    + r.getResourceTypeName(resId) + "/"
                    + r.getResourceEntryName(resId)
        )
        return uri.toString()
    }






}

