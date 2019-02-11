package com.freshworks.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.freshworks.smartlog.R
import com.freshworks.smartlog.database.entity.LogBook
import com.freshworks.smartlog.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by arvin-2009 on Feb 2019.
 */
open class LogBooksFragment : ParentFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val type = arguments?.getString("type")
        toolbar_title.text = type

        viewModel.getLogBooks().observe(this, android.arch.lifecycle.Observer {
            adapter.clear()
            adapter.addAll(it as ArrayList<LogBook>)
            adapter.notifyDataSetChanged()
        })


        val cal = Calendar.getInstance()


        fab.setOnClickListener {

            val fragment = AddLogBookDialogFragment()
            fragment.setTargetFragment(this@LogBooksFragment, 1201)
            fragment.show(fragmentManager, "createbook")
        }

        adapter.logBookListener = object : NAdapter.LogBookListener {

            override fun deleteBook(logBook: LogBook) {

            }



            override fun showLogEntries(logBookTitle : String) {
                val fragment = LogsFragment()
                fragment.setTargetFragment(this@LogBooksFragment, 1001)
                val bundle = Bundle()
                bundle.putString("logBookTitle", logBookTitle)
                fragment.arguments =  bundle
                loadFragment(fragment, "entries")
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == Activity.RESULT_OK) {

            if (requestCode == 1201) {
                val logBook = data?.getSerializableExtra("logBook") as LogBook
                adapter.add(0, logBook)
                adapter.notifyItemInserted(0)
                recycler_view.scrollToPosition(0)

            }
        }
    }
    private fun loadFragment(fragment : Fragment, flag : String){
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.content_frame, fragment)
        fragmentTransaction?.addToBackStack(flag)
        fragmentTransaction?.commit()
    }



}