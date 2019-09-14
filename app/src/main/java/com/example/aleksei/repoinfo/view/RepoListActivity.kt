package com.example.aleksei.repoinfo.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.aleksei.repoinfo.R
import com.example.aleksei.repoinfo.model.DataWorker
import com.example.aleksei.repoinfo.presenter.ChiefPresenter


class RepoListActivity : FragmentActivity(), RepoListInterface, RecyclerViewAdapter.ItemClickedCallback {

    companion object {
        const val DETAILED_FRAGMENT_KEY: String = "detailedInfoFragment"
    }

    lateinit var progressBar: ProgressBar          //todo убрать findViewById
    lateinit var fragmentsHolder: LinearLayout
    private lateinit var chiefPresenter: ChiefPresenter
    private lateinit var detailedInfoFragment: DetailedInfoFragment
    private lateinit var repositoriesFragment: RepositoriesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        initializeUI()
        if (savedInstanceState != null) {
            detailedInfoFragment = supportFragmentManager.getFragment(savedInstanceState, DETAILED_FRAGMENT_KEY) as DetailedInfoFragment
        } else detailedInfoFragment = DetailedInfoFragment()
        repositoriesFragment = RepositoriesFragment()
        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_view_fl_fragment_repo, repositoriesFragment)
        fragmentTransaction.replace(R.id.activity_view_fl_fragment_detailed, detailedInfoFragment)
        fragmentTransaction.commit()
    }

    override fun initializeUI() {
        progressBar = findViewById(R.id.activity_view_pb)
        fragmentsHolder = findViewById(R.id.activity_view_ll)
        chiefPresenter = ChiefPresenter(DataWorker.getInstance(this), this, this)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        fragmentsHolder.visibility = View.INVISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
        fragmentsHolder.visibility = View.VISIBLE
    }

    override fun showError(errorCode: String) {
        val listener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { dialog: DialogInterface, which: Int ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> chiefPresenter.onUIReady()
            }
        }

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
                .setPositiveButton(R.string.btn_error_retry, listener)
        when (errorCode) {
            ChiefPresenter.INTERNET_AVAILABILITY_ERROR_CASE -> builder.setMessage(R.string.msg_error_internet)
            DataWorker.INTERNET_DATA_ERROR_CASE -> builder.setMessage(R.string.msg_error_data)
        }
        builder.show()
    }

    override fun showItemOnClickedPosition(clickedView: View) {
        val itemPosition: Int = repositoriesFragment.repoFragmentRecyclerView.getChildAdapterPosition(clickedView)
        detailedInfoFragment.setDetailedData(RecyclerViewAdapter.listDataRepositories.get(itemPosition))
    }

    override fun onStart() {
        super.onStart()
        RepositoriesFragment.recyclerViewAdapter.registerForListCallback(this)
        chiefPresenter.onUIReady()
    }

    override fun onStop() {
        super.onStop()
        chiefPresenter.disposeDisposable()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        supportFragmentManager.putFragment(outState, DETAILED_FRAGMENT_KEY, detailedInfoFragment)
    }

    override fun onItemClicked(view: View) {
        chiefPresenter.prepareClickedView(view)
    }
}