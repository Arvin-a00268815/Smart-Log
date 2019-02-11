package com.freshworks.smartlog.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.freshworks.smartlog.R
import kotlinx.android.synthetic.main.fragment_link.*

/**
 * Created by arvin-2009 on Feb 2019.
 */
class LinkFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_link, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_title.text = "About Us"
        webview.loadUrl("https://freshworks.io/our-work/")

        toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }

        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress < 100) {
                    webview.visibility = View.INVISIBLE
                    progress_bar.visibility = View.VISIBLE
                } else {
                    webview.visibility = View.VISIBLE
                    progress_bar.visibility = View.INVISIBLE
                }
            }
        }
    }
}