package com.example.aleksei.repoinfo.view

import android.view.View

interface RepoListInterface {

    fun initializeUI()

    fun showLoading()

    fun hideLoading()

    fun showError(errorCode: String)

    fun showItemOnClickedPosition(clickedView: View)
}