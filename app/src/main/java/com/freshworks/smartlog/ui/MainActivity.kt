package com.freshworks.smartlog.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import com.freshworks.smartlog.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
//
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)


        loadFragment()
        permissionCheck()

    }

    private fun loadFragment(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = LogBooksFragment()
        val bundle = Bundle()
        bundle.putString("type", "Log Books")
        fragment.arguments =  bundle
        fragmentTransaction.add(R.id.content_frame, fragment)
        //fragmentTransaction.addToBackStack("list")
        fragmentTransaction.commit()
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



    fun permissionCheck() : Boolean{
        if (ContextCompat.checkSelfPermission(
                this@MainActivity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this!!,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                103
            )
            return false
        }
        return true
    }
}
