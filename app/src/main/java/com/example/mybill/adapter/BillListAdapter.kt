package com.example.mybill.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mybill.MainActivity
import com.example.mybill.MyApplication.Companion.context
import com.example.mybill.R
import com.example.mybill.fragment.BillListViewModel
import com.example.mybill.model.Bill
import java.util.*
import kotlin.collections.ArrayList


class BillListAdapter() : RecyclerView.Adapter<BillListAdapter.ViewHolder>() {

    private var bills = arrayListOf<Bill>()
    private val billListViewModel:BillListViewModel = BillListViewModel()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_recycler_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill = bills[position]
        holder.date?.text = bill.Date
        if (bill.isIncome){
            holder.isIncome?.text = "Income"
        }
        else{
            holder.isIncome?.text = "Expense"
        }

        bill.typeId?.let { holder.typeImage?.setImageResource(it) }

        holder.typeName?.text = bill.typeName
        holder.amount?.text = bill.amount.toString()
        val clickListener = View.OnClickListener { v: View ->
            when(v.id) {
                R.id.item_delete -> {
                    billListViewModel.doDelete(bill.objectId, position)
                    Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show()
                    remove(position)
                    notifyDataSetChanged()
                    billListViewModel.doUpdate()

                }

            }

            }
        holder.delete?.setOnClickListener(clickListener)
        holder.billPosition = position

    }

    override fun getItemCount(): Int {
        return bills.size
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

        val date = itemView?.findViewById<TextView>(R.id.item_header_date)
        val isIncome = itemView?.findViewById<TextView>(R.id.item_header_money)
        val typeImage = itemView?.findViewById<ImageView>(R.id.item_img)
        val typeName = itemView?.findViewById<TextView>(R.id.item_type)
        val amount = itemView?.findViewById<TextView>(R.id.item_money)
        val delete = itemView?.findViewById<ImageView>(R.id.item_delete)
        var billPosition = 0

    }

    fun setBills(bills: ArrayList<Bill>){
        this.bills = bills
        notifyDataSetChanged()
    }

    fun remove(position: Int){
        bills.removeAt(position)
    }







}