package com.example.aleksei.repoinfo.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.aleksei.repoinfo.R
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(), View.OnClickListener {

    private lateinit var callback: ItemClickedCallback

    companion object {
        var listDataRepositories: ArrayList<RepositoryModel> = ArrayList()

        fun setDataToAdapter(arrayList: ArrayList<RepositoryModel>) {
            listDataRepositories = arrayList
        }
    }

    fun registerForListCallback(callback: ItemClickedCallback) {
        this.callback = callback
    }

    interface ItemClickedCallback {
        fun onItemClicked(view: View)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerViewHolder {
        val inflater: LayoutInflater = viewGroup.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.list_item_repositories, viewGroup, false)
        view.setOnClickListener(this)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(recyclerViewHolder: RecyclerViewHolder, index: Int) {
        val repository: RepositoryModel = listDataRepositories.get(index)
        recyclerViewHolder.tvRepositoryName.text = repository.name
        recyclerViewHolder.tvStarsNumber.text = repository.stargazersCount.toString()
        recyclerViewHolder.tvForksNumber.text = repository.forks.toString()
        recyclerViewHolder.tvWatchesNumber.text = repository.watchersCount.toString()
    }

    override fun onClick(v: View) {
        callback.onItemClicked(v)
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvRepositoryName: TextView
        var tvStarsNumber: TextView
        var tvForksNumber: TextView
        var tvWatchesNumber: TextView

        init {
            tvRepositoryName = itemView.findViewById(R.id.list_item_tv_repositoryname_text)
            tvStarsNumber = itemView.findViewById(R.id.list_item_tv_starsnumber_text)
            tvForksNumber = itemView.findViewById(R.id.list_item_tv_forksnumber_text)
            tvWatchesNumber = itemView.findViewById(R.id.list_item_tv_watchesnumber_text)

        }
    }

    override fun getItemCount(): Int {
        return listDataRepositories.size
    }
}
