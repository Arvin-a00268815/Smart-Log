package com.freshworks.smartlog.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freshworks.smartlog.R
import kotlinx.android.synthetic.main.fragment_help_layout.*

/**
 * Created by arvin-2009 on Feb 2019.
 */
class HelpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_help_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_title.text = "Help"
        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
        link_view.setOnClickListener {
            (activity as MainActivity).loadFragment(LinkFragment(), "link", true)
        }
        send_mail.setOnClickListener {


            val emailIntent = Intent(Intent.ACTION_SENDTO)

            val emailId = arrayOf("\"contact@freshworks.io\"")//No i18n
            emailIntent.data = Uri.parse("mailto:")//no i18n

            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, emailId)
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "From Smart Log App") //No i18n
            startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"))//No i18n

        }
    }
}