package com.freshworks.smartlog.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.freshworks.smartlog.R
import com.freshworks.smartlog.Util

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Util.permissionCheck(this)
        val fragment = LogBooksFragment()
        val bundle = Bundle()
        bundle.putString("type", "Log Books")
        fragment.arguments =  bundle
        loadFragment(fragment, "books", false)

    }

    public fun loadFragment(fragment : Fragment, flag : String, isAddToStack : Boolean = true){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.content_frame, fragment)
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
