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

public class RepositoriesFragment extends Fragment {

    public static RecyclerViewAdapter recyclerViewAdapter;
    static int currentVisiblePosition = 0;
    private RecyclerView repoFragmentRecyclerView;

    public RecyclerView getRepoFragmentRecyclerView() {
        return repoFragmentRecyclerView;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repositories, null);
        repoFragmentRecyclerView = view.findViewById(R.id.fragment_repositories_rv);
        repoFragmentRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter();
        repoFragmentRecyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        repoFragmentRecyclerView.scrollToPosition(currentVisiblePosition);
        currentVisiblePosition = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) repoFragmentRecyclerView.getLayoutManager();
        if (linearLayoutManager != null) {
            currentVisiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
