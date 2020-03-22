package com.project.smartlog.ui

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.smartlog.R
import com.project.smartlog.viewmodel.MainActivityViewModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_book.*

class AddLogBookDialogFragment : AppCompatDialogFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)


        mainActivityViewModel.onInsertLogBook().observe(this, android.arch.lifecycle.Observer {

            val intent = Intent()
            intent.putExtra("logBook", it)
            targetFragment!!.onActivityResult(1201, Activity.RESULT_OK, intent)
            dismiss()
        })

        var createdTime = ""


        mainActivityViewModel.getDateTime().observe(this, Observer {
            val list = it as ArrayList<String>
            createdTime = list[0].plus(" ").plus(list[1])

        })



        create_book_button.setOnClickListener {

            if(title_editText.text!!.isNotEmpty()) {
                Completable.fromAction {
                    mainActivityViewModel.insertLogBook(title_editText.text.toString(), createdTime)
                }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : CompletableObserver {
                        override fun onSubscribe(d: Disposable) {}

                        override fun onComplete() {


                        }

                        override fun onError(e: Throwable) {

                            title_textLayout.error = "Choose a different title"
                        }
                    })


            }else{
                title_textLayout.error = "Enter title"
            }
        }
    }

}