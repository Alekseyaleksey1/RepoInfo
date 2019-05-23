package com.example.aleksei.repoinfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;

public class DetailedInfoActivity extends Activity {

    TextView tvFullName;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvFullName = findViewById(R.id.activity_detailed_tv_fullname);

        ModelPOJOShort pojoShort = getIntent().getExtras().getParcelable("modelPOJO");

        tvFullName.setText(pojoShort.getFull_name());
    }
}
