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

public class DetailedInfoFragment extends Fragment {

    TextView tvFullName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detailed, null);

        tvFullName = view.findViewById(R.id.fragment_detailed_tv_fullname);

        return view;
    }

    public void setFullName(String fullName) {
        tvFullName.setText(fullName);
    }
}
