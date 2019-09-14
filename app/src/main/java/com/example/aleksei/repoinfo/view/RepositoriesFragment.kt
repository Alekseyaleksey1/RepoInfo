package com.example.aleksei.repoinfo.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.aleksei.repoinfo.R

class RepositoriesFragment : Fragment() {

    lateinit var repoFragmentRecyclerView: RecyclerView

    companion object {
        lateinit var recyclerViewAdapter: RecyclerViewAdapter
        var currentVisiblePosition: Int = 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_repositories, container, false)
        repoFragmentRecyclerView = view.findViewById(R.id.fragment_repositories_rv)
        repoFragmentRecyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerViewAdapter = RecyclerViewAdapter()
        repoFragmentRecyclerView.adapter = recyclerViewAdapter
        return view
    }

    override fun onResume() {
        super.onResume()
        repoFragmentRecyclerView.scrollToPosition(currentVisiblePosition)
        currentVisiblePosition = 0
    }

    override fun onPause() {
        super.onPause()
        val linearLayoutManager: LinearLayoutManager? = repoFragmentRecyclerView.layoutManager as LinearLayoutManager
        if (linearLayoutManager != null) {
            currentVisiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
        }
    }
}