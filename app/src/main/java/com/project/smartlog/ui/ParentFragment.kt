package com.project.smartlog.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.smartlog.R
import com.project.smartlog.repository.Repository
import com.project.smartlog.repository.database.DatabaseAccess
import com.project.smartlog.viewmodel.MainActivityViewModel
import com.project.smartlog.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * Created by arvin-2009 on Feb 2019.
 */
open class ParentFragment : Fragment() {

    lateinit var adapter : NewListAdapter
    lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        //setHasOptionsMenu(true)
        return view

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val databaseAccess = DatabaseAccess.getAppDatabase(requireActivity())
        val repository = Repository(databaseAccess)
        val factory = MainViewModelFactory(repository)

        viewModel = ViewModelProviders.of(requireActivity(), factory).get(MainActivityViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        adapter = NewListAdapter()
        recycler_view.adapter = adapter

    }


    fun checkEmptyState(){
        if(adapter.itemCount == 0){
            empty_state_layout.visibility = View.VISIBLE
        }else{
            empty_state_layout.visibility = View.GONE
        }
    }
}
