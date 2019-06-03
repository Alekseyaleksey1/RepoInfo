package com.example.aleksei.repoinfo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.aleksei.repoinfo.R;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> implements View.OnClickListener {

    private static ArrayList<RepositoryModel> listDataRepositories = new ArrayList<>();
    private ItemClickedCallback callback;

    static ArrayList<RepositoryModel> getListDataRepositories() {
        return listDataRepositories;
    }

    private static void setListDataRepositories(ArrayList<RepositoryModel> listDataRepositories) {
        RecyclerViewAdapter.listDataRepositories = listDataRepositories;
    }

    void registerForListCallback(ItemClickedCallback callback) {
        this.callback = callback;
    }

    public interface ItemClickedCallback {
        void onItemClicked(View v);
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_repositories, viewGroup, false);
        v.setOnClickListener(this);
        return new RecycleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int i) {
        recycleViewHolder.tvRepositoryName.setText(getListDataRepositories().get(i).getName());
        recycleViewHolder.tvStarsNumber.setText(String.valueOf(getListDataRepositories().get(i).getStargazersCount()));
        recycleViewHolder.tvForksNumber.setText(String.valueOf(getListDataRepositories().get(i).getForks()));
        recycleViewHolder.tvWatchesNumber.setText(String.valueOf(getListDataRepositories().get(i).getWatchersCount()));
    }

    @Override
    public void onClick(View v) {
        callback.onItemClicked(v);
    }

    class RecycleViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRepositoryName;
        private TextView tvStarsNumber;
        private TextView tvForksNumber;
        private TextView tvWatchesNumber;

        RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRepositoryName = itemView.findViewById(R.id.list_item_tv_repositoryname_text);
            tvStarsNumber = itemView.findViewById(R.id.list_item_tv_starsnumber_text);
            tvForksNumber = itemView.findViewById(R.id.list_item_tv_forksnumber_text);
            tvWatchesNumber = itemView.findViewById(R.id.list_item_tv_watchesnumber_text);
        }
    }

    @Override
    public int getItemCount() {
        return getListDataRepositories().size();
    }

    public static void setDataToAdapter(ArrayList<RepositoryModel> arrayList) {
        RecyclerViewAdapter.setListDataRepositories(arrayList);
    }
}
