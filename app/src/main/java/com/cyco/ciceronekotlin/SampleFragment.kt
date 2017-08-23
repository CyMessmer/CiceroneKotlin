package com.cyco.ciceronekotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by cy on 8/23/17.
 */
class SampleFragment : Fragment(), SampleView, BackButtonListener {

    private lateinit var toolbar: Toolbar
    private lateinit var backCommandBt: TextView
    private lateinit var forwardCommandBt: TextView
    private lateinit var replaceCommandBt: TextView
    private lateinit var newChainCommandBt: TextView
    private lateinit var newRootCommandBt: TextView
    private lateinit var backWithMessageCommandBt: TextView
    private lateinit var backToCommandBt: TextView


    private var router = SampleApplication.instance.router

    internal var presenter = SamplePresenter(router, number++)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view!!.findViewById(R.id.toolbar)
        backCommandBt = view.findViewById(R.id.back_command)
        forwardCommandBt = view.findViewById(R.id.forward_command)
        replaceCommandBt = view.findViewById(R.id.replace_command)
        newChainCommandBt = view.findViewById(R.id.new_chain_command)
        newRootCommandBt = view.findViewById(R.id.new_root_command)
        backWithMessageCommandBt = view.findViewById(R.id.back_with_message_command)
        backToCommandBt = view.findViewById(R.id.back_to_command)
    }

    /**
     * Set up presenter calls
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { presenter.onBackCommandClick() }
        backCommandBt.setOnClickListener { presenter.onBackCommandClick() }
        forwardCommandBt.setOnClickListener { presenter.onForwardCommandClick() }
        replaceCommandBt.setOnClickListener { presenter.onReplaceCommandClick() }
        newChainCommandBt.setOnClickListener { presenter.onNewChainCommandClick() }
        newRootCommandBt.setOnClickListener { presenter.onNewRootCommandClick() }
        backWithMessageCommandBt.setOnClickListener { presenter.onBackWithMessageCommandClick() }
        backToCommandBt.setOnClickListener { presenter.onBackToCommandClick() }
    }

    override fun setTitle(title: String) {
        toolbar.title = title
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackCommandClick()
        return true
    }

    companion object {
        // track fragment navigation
        private var number = 1

        // new instance to be added to activity.
        val newInstance: SampleFragment
            get() = SampleFragment()
    }
}
