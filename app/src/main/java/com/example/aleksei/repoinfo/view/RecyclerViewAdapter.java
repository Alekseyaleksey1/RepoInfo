package com.example.aleksei.repoinfo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleksei.repoinfo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecycleViewHolder> implements RecyclerView.OnClickListener{

    ArrayList<HashMap> mapArrayList;
    Context context;

    public RecyclerViewAdapter(Context context, ArrayList<HashMap> mapArrayList) {
        this.mapArrayList = mapArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_item_repositories, viewGroup, false);
        return new RecycleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int i) {

        recycleViewHolder.list_item_tv_repositoryname.setText(mapArrayList.get(i).get("nameKey").toString());
        recycleViewHolder.list_item_tv_starsnumber.setText(mapArrayList.get(i).get("starsKey").toString());
        recycleViewHolder.list_item_tv_forksnumber.setText(mapArrayList.get(i).get("forksKey").toString());
        recycleViewHolder.list_item_tv_watchesnumber.setText(mapArrayList.get(i).get("watchesKey").toString());
    }

    @Override
    public void onClick(View v) {
        //todo rv item selected
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
        return mapArrayList.size();
    }
}
