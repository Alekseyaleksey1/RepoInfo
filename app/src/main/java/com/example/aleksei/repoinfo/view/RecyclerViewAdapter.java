package com.example.aleksei.repoinfo.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleksei.repoinfo.ChiefPresenter;
import com.example.aleksei.repoinfo.DetailedInfoActivity;
import com.example.aleksei.repoinfo.R;
import com.example.aleksei.repoinfo.RepositoriesActivity;
import com.example.aleksei.repoinfo.model.ChiefModel;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> implements View.OnClickListener {

    ArrayList<ModelPOJOShort> arrayList;

    public RecyclerViewAdapter(ArrayList<ModelPOJOShort> arrayList) {
        this.arrayList = arrayList;
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
        recycleViewHolder.list_item_tv_repositoryname.setText(arrayList.get(i).getName());
        recycleViewHolder.list_item_tv_starsnumber.setText(String.valueOf(arrayList.get(i).getStargazers_count()));
        recycleViewHolder.list_item_tv_forksnumber.setText(String.valueOf(arrayList.get(i).getForks()));
        recycleViewHolder.list_item_tv_watchesnumber.setText(String.valueOf(arrayList.get(i).getWatchers_count()));
    }

    @Override
    public void onClick(View v) {
        int itemPosition = RepositoriesActivity.rvRepositories.getChildAdapterPosition(v);
        Intent intent = new Intent(v.getContext(), DetailedInfoActivity.class);
        intent.putExtra("modelPOJO", arrayList.get(itemPosition));
        v.getContext().startActivity(intent);

       // startDetailedInfoActivity(arrayList.get(itemPosition),v.getContext());
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        private TextView list_item_tv_repositoryname;
        private TextView list_item_tv_starsnumber;
        private TextView list_item_tv_forksnumber;
        private TextView list_item_tv_watchesnumber;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            list_item_tv_repositoryname = itemView.findViewById(R.id.list_item_tv_repositoryname_text);
            list_item_tv_starsnumber = itemView.findViewById(R.id.list_item_tv_starsnumber_text);
            list_item_tv_forksnumber = itemView.findViewById(R.id.list_item_tv_forksnumber_text);
            list_item_tv_watchesnumber = itemView.findViewById(R.id.list_item_tv_watchesnumber_text);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    /*public void startDetailedInfoActivity(ModelPOJOShort modelPOJO, Context context) {
        Intent intent = new Intent(context, DetailedInfoActivity.class);
        intent.putExtra("modelPOJO", modelPOJO);
        context.startActivity(intent);
    }*/
}
