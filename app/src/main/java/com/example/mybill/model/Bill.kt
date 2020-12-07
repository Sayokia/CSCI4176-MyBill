package com.example.mybill.model

import cn.bmob.v3.BmobObject
import java.util.*

class Bill : BmobObject() {
    var amount:Double ?=null
    var user:MyUser ?= null
    var typeName:String ?=null
    var typeId :Int ?= null
    var typeImg:String ?=null
    var isIncome:Boolean = false
    var Date:String ?=null

}