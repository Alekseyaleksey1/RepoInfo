package com.example.aleksei.repoinfo.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aleksei.repoinfo.R
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel

class DetailedInfoFragment : Fragment() {

    lateinit var tvFullName: TextView
    lateinit var tvDescription: TextView
    lateinit var tvUrl: TextView
    lateinit var tvOpenIssues: TextView
    private var repository: RepositoryModel? = null

    companion object {
        const val REPOSITORY_KEY: String = "repository"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_detailed, container, false)
        tvFullName = view.findViewById(R.id.fragment_detailed_tv_fullname_text)
        tvDescription = view.findViewById(R.id.fragment_detailed_tv_description_text)
        tvUrl = view.findViewById(R.id.fragment_detailed_tv_url_text)
        tvOpenIssues = view.findViewById(R.id.fragment_detailed_tv_openissues_text)
        return view
    }

    fun setDetailedData(repository: RepositoryModel) {
        this.repository = repository
        tvFullName.text = repository.fullName
        tvDescription.text = repository.description
        tvUrl.text = repository.url
        tvOpenIssues.text = repository.openIssues
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(REPOSITORY_KEY, repository)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            this.repository = savedInstanceState.getParcelable(REPOSITORY_KEY)
            if (repository != null) setDetailedData(repository!!)
        }

    }

}