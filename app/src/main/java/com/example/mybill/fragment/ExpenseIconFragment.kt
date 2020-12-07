package com.example.mybill.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mybill.R


class ExpenseIconFragment : Fragment() {






    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expense_icon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //bind view
        val utilityIv:ImageView = view.findViewById(R.id.icon_utility)
        val entertainmentIv:ImageView= view.findViewById(R.id.icon_entertain)
        val gasIv:ImageView= view.findViewById(R.id.icon_gas)
        val hamburger:ImageView= view.findViewById(R.id.icon_hamburger)
        val cashIv:ImageView= view.findViewById(R.id.icon_money)
        val phone:ImageView= view.findViewById(R.id.icon_phone)
        val medicineIv:ImageView= view.findViewById(R.id.icon_medicine)
        val rentIv:ImageView= view.findViewById(R.id.icon_rent)
        val shoppingIv:ImageView= view.findViewById(R.id.icon_shopping)
        val studyIv:ImageView= view.findViewById(R.id.icon_study)
        val otherIv:ImageView= view.findViewById(R.id.icon_others)

        val textview = requireActivity().findViewById<View>(R.id.note_cash) as TextView
        val imgaeview = requireActivity().findViewById<View>(R.id.item_type_iv) as ImageView



        val clickListener = View.OnClickListener { v: View ->
            when(v.id){
                R.id.icon_utility ->{
                    textview.text = "Utility"
                    imgaeview.setImageResource(R.mipmap.icons8_electricity_50)
                    imgaeview.tag = R.mipmap.icons8_electricity_50
                }

                R.id.icon_entertain ->{
                    textview.text = "Entertainment"
                    imgaeview.setImageResource(R.mipmap.icons8_film_reel_50)
                    imgaeview.tag = R.mipmap.icons8_film_reel_50
                }

                R.id.icon_gas ->{
                    textview.text = "Gas"
                    imgaeview.setImageResource(R.mipmap.icons8_gas_50)
                    imgaeview.tag = R.mipmap.icons8_gas_50
                }

                R.id.icon_hamburger ->{
                    textview.text = "Food"
                    imgaeview.setImageResource(R.mipmap.icons8_hamburger_50)
                    imgaeview.tag = R.mipmap.icons8_hamburger_50
                }

                R.id.icon_money -> {
                    textview.text = "Cash"
                    imgaeview.setImageResource(R.mipmap.icons8_money_50)
                    imgaeview.tag = R.mipmap.icons8_money_50
                }

                R.id.icon_phone ->{
                    textview.text = "Phone"
                    imgaeview.setImageResource(R.mipmap.icons8_phone_50)
                    imgaeview.tag = R.mipmap.icons8_phone_50
                }

                R.id.icon_medicine ->{
                    textview.text = "Medicine"
                    imgaeview.setImageResource(R.mipmap.icons8_pills_50)
                    imgaeview.tag = R.mipmap.icons8_pills_50
                }

                R.id.icon_rent ->{
                    textview.text = "Rent"
                    imgaeview.setImageResource(R.mipmap.icons8_rent_50)
                    imgaeview.tag = R.mipmap.icons8_rent_50
                }
                R.id.icon_shopping ->{
                    textview.text = "Shopping"
                    imgaeview.setImageResource(R.mipmap.icons8_shopping_50)
                    imgaeview.tag = R.mipmap.icons8_shopping_50
                }
                R.id.icon_study ->{
                    textview.text = "Study"
                    imgaeview.setImageResource(R.mipmap.icons8_study_50)
                    imgaeview.tag = R.mipmap.icons8_study_50
                }
                R.id.icon_others ->{
                    textview.text = "others"
                    imgaeview.setImageResource(R.mipmap.icons8_others_50)
                    imgaeview.tag = R.mipmap.icons8_others_50
                }
            }
        }

        utilityIv.setOnClickListener(clickListener)
        entertainmentIv.setOnClickListener(clickListener)
        gasIv.setOnClickListener(clickListener)
        hamburger.setOnClickListener(clickListener)
        cashIv.setOnClickListener(clickListener)
        phone.setOnClickListener(clickListener)
        medicineIv.setOnClickListener(clickListener)
        rentIv.setOnClickListener(clickListener)
        shoppingIv.setOnClickListener(clickListener)
        studyIv.setOnClickListener(clickListener)
        otherIv.setOnClickListener(clickListener)

    }


//    private fun imageTranslateUri(resId: Int): String {
//        val r: Resources = resources
//        val uri: Uri = Uri.parse(
//            ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
//                    + r.getResourcePackageName(resId) + "/"
//                    + r.getResourceTypeName(resId) + "/"
//                    + r.getResourceEntryName(resId)
//        )
//        return uri.toString()
//    }
//
//    private fun iconViewModel.onSelected(id:Int,name:String){
//        val imageUri:String = imageTranslateUri(id)
//        val intent = Intent(context, AddBillActivity::class.java)
//        intent.putExtra("selectedImg", imageUri)
//        intent.putExtra("selectedName", name)
//        startActivity(intent)
//    }


}