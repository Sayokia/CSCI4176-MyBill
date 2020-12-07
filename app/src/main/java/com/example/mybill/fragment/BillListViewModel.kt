package com.example.mybill.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.UpdateListener
import com.example.mybill.adapter.BillListAdapter
import com.example.mybill.model.Bill
import com.example.mybill.model.MyUser
import com.google.android.material.snackbar.Snackbar


class BillListViewModel:ViewModel() {
//    var billListAdapter:BillListAdapter = BillListAdapter()
//    var billListFragment:BillListFragment = BillListFragment()

    val bills = MutableLiveData<ArrayList<Bill>>()
    val expenseResult = MutableLiveData<Double>()
    val incomeResult = MutableLiveData<Double>()
    val balanceResult = MutableLiveData<Double>()


    init{
        if (BmobUser.isLogin()){

            // set list item
            val query :BmobQuery<Bill> = BmobQuery()
            query.order("-Date");
            query.addWhereEqualTo("user", BmobUser.getCurrentUser(MyUser::class.java))
            query.findObjects(object : FindListener<Bill?>() {
                override fun done(Bills: List<Bill?>?, e: BmobException?) {
                    if (e == null) {
                        val dataList = arrayListOf<Bill>()
                        if (Bills != null) {
                            for (data: Bill? in Bills) {
                                if (data != null) {
                                    dataList.add(data)
                                    Log.i("BMOB", data.objectId)
                                }
                            }
                        }
                        bills.postValue(dataList)

                    } else {
                        Log.e("BMOB", e.toString())
                    }
                }
            })

            doUpdate()



        }


    }

    fun doDelete(objectId: String, position: Int){
        val bill:Bill = Bill()
        bill.objectId= objectId
        bill.delete(objectId, object : UpdateListener() {
            override fun done(e: BmobException?) {
                if (e == null) {
                    Log.i("BMOB","delete success")



                } else {

                    Log.e("BMOB", e.toString())

                }
            }
        })
    }

    fun doUpdate(){


//        var balance : Double = 0.00
//
//        // cal expense amount
//        val expenseQuery : BmobQuery<Bill> = BmobQuery()
//        expenseQuery.addWhereEqualTo("user", BmobUser.getCurrentUser(MyUser::class.java))
//        expenseQuery.addWhereEqualTo("isIncome", false)
//        expenseQuery.findObjects(object : FindListener<Bill?>() {
//            override fun done(Bills: List<Bill?>?, e: BmobException?) {
//                if (e == null) {
//                    val dataList = arrayListOf<Double>()
//                    if (Bills != null) {
//                        for (data: Bill? in Bills) {
//                            if (data != null) {
//                                data.amount?.let { dataList.add(it) }
//                                Log.i("BMOB", data.objectId)
//                            }
//                        }
//                    }
//                    var expense:Double = 0.00
//
//                    for (amount in dataList){
//                        expense += amount
//                    }
//
//
//
//                    expenseResult.postValue(expense)
//
//
//
//
//                } else {
//                    Log.e("BMOB", e.toString())
//                }
//            }
//        })
//
//
//        // cal income
//
//        val incomeQuery : BmobQuery<Bill> = BmobQuery()
//        incomeQuery.addWhereEqualTo("user", BmobUser.getCurrentUser(MyUser::class.java))
//        incomeQuery.addWhereEqualTo("isIncome", true)
//        incomeQuery.findObjects(object : FindListener<Bill?>() {
//            override fun done(Bills: List<Bill?>?, e: BmobException?) {
//                if (e == null) {
//                    val dataList = arrayListOf<Double>()
//                    if (Bills != null) {
//                        for (data: Bill? in Bills) {
//                            if (data != null) {
//                                data.amount?.let { dataList.add(it) }
//                                Log.i("BMOB", data.objectId)
//                            }
//                        }
//                    }
//                    var income:Double = 0.00
//
//                    for (amount in dataList){
//                        income += amount
//                    }
//
//                    incomeResult.postValue(income)
//
//
//
//
//                } else {
//                    Log.e("BMOB", e.toString())
//                }
//            }
//        })


        val balanceQuery : BmobQuery<Bill> = BmobQuery()
        balanceQuery.addWhereEqualTo("user", BmobUser.getCurrentUser(MyUser::class.java))
        balanceQuery.findObjects(object : FindListener<Bill?>() {
            override fun done(Bills: List<Bill?>?, e: BmobException?) {
                if (e == null) {
                    val incomeList = arrayListOf<Double>()
                    val expenseList = arrayListOf<Double>()
                    if (Bills != null) {
                        for (data: Bill? in Bills) {
                            if (data != null) {
                                if (data.isIncome){
                                    data.amount?.let { incomeList.add(it) }
                                }
                                else{
                                    data.amount?.let { expenseList.add(it) }
                                }
                            }
                        }
                    }
                    var income:Double = 0.00
                    var expense:Double = 0.0
                    var balance:Double = 0.0
                    for (amount in incomeList){
                        income += amount
                    }
                    for (amount in expenseList){
                        expense += amount
                    }
                    balance = income - expense

                    incomeResult.postValue(income)
                    expenseResult.postValue(expense)
                    balanceResult.postValue(balance)



                } else {
                    Log.e("BMOB", e.toString())
                }
            }
        })


    }









}