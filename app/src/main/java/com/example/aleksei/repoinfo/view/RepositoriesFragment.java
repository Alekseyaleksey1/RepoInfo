package com.example.aleksei.repoinfo.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleksei.repoinfo.R;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import com.example.aleksei.repoinfo.view.ViewActivity;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RepositoriesFragment extends Fragment {

    @BindView(R.id.fragment_repositories_rv)
    public RecyclerView repoFragmentRecyclerView;

    static public RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList placeHolderData;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repositories, null);
        unbinder = ButterKnife.bind(this, view);
        //repoFragmentRecyclerView = view.findViewById(R.id.fragment_repositories_rv);
        repoFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        placeHolderData = new ArrayList();
        recyclerViewAdapter = new RecyclerViewAdapter();
        repoFragmentRecyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ViewActivity.chiefPresenter.onUIReady(getActivity());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
