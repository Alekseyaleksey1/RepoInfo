package com.example.aleksei.repoinfo.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.aleksei.repoinfo.R;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DetailedInfoFragment extends Fragment {

    public static final String REPOSITORY_KEY = "repository";
    @BindView(R.id.fragment_detailed_tv_fullname_text)
    TextView tvFullName;
    @BindView(R.id.fragment_detailed_tv_description_text)
    TextView tvDescription;
    @BindView(R.id.fragment_detailed_tv_url_text)
    TextView tvUrl;
    @BindView(R.id.fragment_detailed_tv_openissues_text)
    TextView tvOpenIssues;
    RepositoryModel repository;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void setDetailedData(RepositoryModel repository) {
        this.repository = repository;
        tvFullName.setText(repository.getFullName());
        tvDescription.setText(repository.getDescription());
        tvUrl.setText(repository.getUrl());
        tvOpenIssues.setText(String.valueOf(repository.getOpenIssues()));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(REPOSITORY_KEY, repository);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            this.repository = savedInstanceState.getParcelable(REPOSITORY_KEY);
            if (repository != null)
                setDetailedData(repository);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
