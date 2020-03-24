package com.project.smartlog.ui

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.project.smartlog.R
import com.project.smartlog.Util
import com.project.smartlog.di.DaggerAppComponent
import com.project.smartlog.repository.IRepository
import com.project.smartlog.repository.Repository
import com.project.smartlog.repository.database.DatabaseAccess
import com.project.smartlog.viewmodel.MainActivityViewModel
import com.project.smartlog.viewmodel.MainViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object{
        private lateinit var context: Context
        fun getInstance() : Context {
            return context
        }
    }


    @Inject
    lateinit var repository : Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setContentView(R.layout.activity_main)


        val component = DaggerAppComponent
            .builder()
            .build()
        component.inject(this)


        val factory = MainViewModelFactory(repository)

        val viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel::class.java)

        Util.permissionCheck(this)
        if(savedInstanceState == null) {
            val fragment = LogBooksFragment()
            val bundle = Bundle()
            bundle.putString("type", "Log Books")
            fragment.arguments = bundle
            loadFragment(fragment, "books", false)
        }

    }

     fun loadFragment(fragment : Fragment, flag : String, isAddToStack : Boolean = true){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.content_frame, fragment, flag)
        if(isAddToStack) {
            fragmentTransaction?.addToBackStack(flag)
        }
        fragmentTransaction?.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return false
    }

}
