package com.example.aleksei.repoinfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;

import java.util.ArrayList;


public class RepositoriesFragment extends Fragment {

    public static RecyclerView repoFragmentRecyclerView;
    ArrayList dataForShowing;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repositories, null);

        repoFragmentRecyclerView = view.findViewById(R.id.fragment_repositories_rv);
        repoFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        dataForShowing = new ArrayList();
        RepositoriesActivity.recyclerViewAdapter = new RecyclerViewAdapter();
        repoFragmentRecyclerView.setAdapter(RepositoriesActivity.recyclerViewAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        RepositoriesActivity.chiefPresenter.onUIReady(getActivity());
        super.onActivityCreated(savedInstanceState);
    }
}
