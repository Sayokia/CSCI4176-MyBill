package com.example.mybill.fragment

import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.mybill.R



class IncomeIconFragment : Fragment() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_income_icon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set up all variables
        val salaryIv: ImageView = view.findViewById(R.id.icon_salary)
        val bank_interestIv: ImageView= view.findViewById(R.id.icon_bank_interest)
        val BonusIv: ImageView= view.findViewById(R.id.bonus)
        val InvestmentIv: ImageView= view.findViewById(R.id.icon_investment)
        val taxReturnIv: ImageView= view.findViewById(R.id.icon_tax_return)
        val e_transferIv: ImageView= view.findViewById(R.id.icon_transfer)
        val refund: ImageView= view.findViewById(R.id.icon_refund)
        val giftIv: ImageView= view.findViewById(R.id.icon_gift)
        val familyIv: ImageView= view.findViewById(R.id.icon_family)
        val otherIv: ImageView= view.findViewById(R.id.icon_others)

        val textview = requireActivity().findViewById<View>(R.id.note_cash) as TextView
        val imgaeview = requireActivity().findViewById<View>(R.id.item_type_iv) as ImageView


        val clickListener = View.OnClickListener {v:View ->
            when(v.id){
                R.id.icon_salary ->{
                    textview.text = "Salary"
                    imgaeview.setImageResource(R.mipmap.icons8_salary_50)
                    imgaeview.tag = R.mipmap.icons8_salary_50
                }


                R.id.icon_bank_interest ->{
                    textview.text = "Bank Interest"
                    imgaeview.setImageResource(R.mipmap.icons8_bank_50)
                    imgaeview.tag = R.mipmap.icons8_salary_50
                }

                R.id.bonus ->{
                    textview.text = "Bonus"
                    imgaeview.setImageResource(R.mipmap.icons8_bonus_50)
                    imgaeview.tag = R.mipmap.icons8_bonus_50
                }
                R.id.icon_investment ->{
                    textview.text = "Investment"
                    imgaeview.setImageResource(R.mipmap.icons8_investment_50)
                    imgaeview.tag = R.mipmap.icons8_investment_50
                }

                R.id.icon_tax_return ->{
                    textview.text = "Tax Return"
                    imgaeview.setImageResource(R.mipmap.icons8_tax_return_50)
                    imgaeview.tag = R.mipmap.icons8_tax_return_50
                }

                R.id.icon_transfer ->{
                    textview.text = "E-transfer"
                    imgaeview.setImageResource(R.mipmap.icons8_etransfer_50)
                    imgaeview.tag = R.mipmap.icons8_etransfer_50
                }
                R.id.icon_refund->{
                    textview.text = "Refund"
                    imgaeview.setImageResource(R.mipmap.icons8_refund_50)
                    imgaeview.tag = R.mipmap.icons8_refund_50
                }

                R.id.icon_gift ->{
                    textview.text = "Gift"
                    imgaeview.setImageResource(R.mipmap.icons8_gift_50)
                    imgaeview.tag = R.mipmap.icons8_gift_50
                }

                R.id.icon_family ->{
                    textview.text = "Family Support"
                    imgaeview.setImageResource(R.mipmap.icons8_family_50)
                    imgaeview.tag = R.mipmap.icons8_family_50
                }

                R.id.icon_others ->{
                    textview.text = "Ohters"
                    imgaeview.setImageResource(R.mipmap.icons8_others_50)
                    imgaeview.tag = R.mipmap.icons8_others_50
                }

            }
        }

        salaryIv.setOnClickListener(clickListener)
        bank_interestIv.setOnClickListener(clickListener)
        BonusIv.setOnClickListener(clickListener)
        InvestmentIv.setOnClickListener(clickListener)
        taxReturnIv.setOnClickListener(clickListener)
        e_transferIv.setOnClickListener(clickListener)
        giftIv.setOnClickListener(clickListener)
        familyIv.setOnClickListener(clickListener)
        otherIv.setOnClickListener(clickListener)
        refund.setOnClickListener(clickListener)



    }


//        private fun imageTranslateUri(resId: Int): String {
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
//    private fun send(id:Int,name:String){
//        val imageUri:String = imageTranslateUri(id)
//        val intent = Intent(context, AddBillActivity::class.java)
//        intent.putExtra("selectedImg", imageUri)
//        intent.putExtra("selectedName", name)
//        startActivity(intent)
//    }

}