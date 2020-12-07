package com.example.mybill.addBill

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.mybill.model.Bill
import com.example.mybill.model.MyUser
import java.time.LocalDateTime
import java.util.*


class AddBillViewModel : ViewModel() {
    private val _addResult = MutableLiveData<AddBillResult>()
    val addBillResult: LiveData<AddBillResult> = _addResult



    fun uploadBill(
        billDate: String,
        isIncome: Boolean,
        typeName: String,
        typeImg: Int,
        user: MyUser,
        amount: Double){
        val bill= Bill()

        bill.Date = billDate
        bill.amount = amount
        bill.isIncome = isIncome
        bill.typeId = typeImg
        bill.typeName = typeName
        bill.user = user


        bill.save(object : SaveListener<String>() {
            override fun done(s: String?, e: BmobException?){
                if (e == null) {
                    _addResult.value = AddBillResult(success = "Upload Bill success")
                } else {
                    Log.e("BMOB", e.toString())
                    _addResult.value = AddBillResult(error = e.message)
                }
            }
        })


    }

}