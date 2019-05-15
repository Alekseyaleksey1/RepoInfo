package com.example.aleksei.repoinfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailedInfoActivity extends Activity {

    TextView tvFullName;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvFullName = findViewById(R.id.tvFullName);
        tvFullName.setText(getIntent().getExtras().getString("fullName"));
    }
}
