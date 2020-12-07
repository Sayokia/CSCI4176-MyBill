package com.example.mybill.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobUser
import com.example.mybill.addBill.AddBillActivity
import com.example.mybill.R
import com.example.mybill.adapter.BillListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_top_data.view.*


class BillListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvList : RecyclerView = view.findViewById(R.id.rv_list)
        val floatBtn: FloatingActionButton = view.findViewById(R.id.float_btn)
        val expenseTv = requireActivity().findViewById<TextView>(R.id.t_expend)
        val incomeTv = requireActivity().findViewById<TextView>(R.id.t_income)
        val balanceTv = requireActivity().findViewById<TextView>(R.id.t_balance)

        rvList.layoutManager = LinearLayoutManager(activity)
        val adapter = BillListAdapter()
        rvList.adapter = adapter

        val listViewModel = BillListViewModel()

//        listViewModel.getData()

        listViewModel.bills.observe(viewLifecycleOwner, Observer { bills -> bills?.let { adapter.setBills(it) } })

        listViewModel.expenseResult.observe(viewLifecycleOwner, Observer { expense-> expense?.let { expenseTv.text = expense.toString() } })

        listViewModel.incomeResult.observe(viewLifecycleOwner, Observer { income-> income?.let { incomeTv.text = income.toString() } })

        listViewModel.balanceResult.observe(viewLifecycleOwner, Observer { balance-> balance?.let { balanceTv.text = balance.toString() } })




        floatBtn.setOnClickListener {
            if (BmobUser.isLogin()){
                val intent = Intent(context, AddBillActivity::class.java)
                startActivityForResult(intent, 0)
            }
            else{
                activity?.window?.decorView?.let { it1 -> Snackbar.make(it1,"Please Login first! You can Login by Click the drawer header",Snackbar.LENGTH_SHORT).show() }
            }

        }


    }


}