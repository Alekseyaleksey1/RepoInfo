package com.example.aleksei.repoinfo.presenter

import android.content.Context
import android.view.View
import com.example.aleksei.repoinfo.model.DataWorker
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter
import com.example.aleksei.repoinfo.view.RepoListInterface
import com.example.aleksei.repoinfo.view.RepositoriesFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class ChiefPresenter(dataWorkerInstance: DataWorker, repoListInterface: RepoListInterface, appContext: Context) {

    private var repoListInterfaceInstance: RepoListInterface
    private var dataWorkerInstance: DataWorker
    private var appContext: Context
    private var disposable: CompositeDisposable

    init {
        this.dataWorkerInstance = dataWorkerInstance
        this.repoListInterfaceInstance = repoListInterface
        this.appContext = appContext
        disposable = CompositeDisposable()
    }

    companion object {
        const val INTERNET_AVAILABILITY_ERROR_CASE: String = "internetAvailabilityError"
    }

    private fun checkDBExists(appContext: Context): Boolean {
        val dbFile = appContext.getDatabasePath(DataWorker.DB_NAME)
        return dbFile.exists()
    }

    fun onUIReady() {
        repoListInterfaceInstance.showLoading()
        if (checkDBExists(appContext)) {
            onDataInDBPresent()
        } else loadDataFromInternet()
    }

    private fun loadDataFromInternet() {
        disposable.add(dataWorkerInstance.getDataFromInternet()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repositoryModelList: List<RepositoryModel> -> onDataFromInternetLoaded(repositoryModelList) }
                        , { throwable: Throwable -> repoListInterfaceInstance.showError(INTERNET_AVAILABILITY_ERROR_CASE) }))
    }

    private fun onDataFromInternetLoaded(repositoryModelList: List<RepositoryModel>) {
        disposable.add(dataWorkerInstance.saveDataToDatabase(repositoryModelList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ onDataInDBPresent() }))
    }

    private fun onDataInDBPresent() {
        disposable.add(dataWorkerInstance.getDataFromDatabase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { repositoryModelList: List<RepositoryModel> ->
                    RecyclerViewAdapter.setDataToAdapter(repositoryModelList as ArrayList<RepositoryModel>)
                    RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged()
                    repoListInterfaceInstance.hideLoading()
                })
    }

    fun prepareClickedView(clickedView: View) {
        repoListInterfaceInstance.showItemOnClickedPosition(clickedView)
    }

    fun disposeDisposable() {
        disposable.dispose()
    }
}