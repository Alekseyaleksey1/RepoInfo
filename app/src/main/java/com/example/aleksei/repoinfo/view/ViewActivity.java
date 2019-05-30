package com.example.aleksei.repoinfo.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.example.aleksei.repoinfo.presenter.ChiefPresenter;
import com.example.aleksei.repoinfo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewActivity extends FragmentActivity {

    @BindView(R.id.activity_repositories_pb)
    ProgressBar progressBar;
    @BindView(R.id.activity_view_ll)
    LinearLayout ll;
    public ChiefPresenter chiefPresenter;
    public RepositoriesFragment repositoriesFragment;
    public DetailedInfoFragment detailedInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initializeUI();
        showLoading();

        if (savedInstanceState != null) {
            detailedInfoFragment = (DetailedInfoFragment) getSupportFragmentManager().getFragment(savedInstanceState, "detailedInfoFragment");
        } else
            detailedInfoFragment = new DetailedInfoFragment();
        repositoriesFragment = new RepositoriesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_repositories_fl_fragment_repo, repositoriesFragment);
        fragmentTransaction.replace(R.id.activity_repositories_fl_fragment_detailed, detailedInfoFragment);
        fragmentTransaction.commit();
    }

    private void initializeUI() {
        ButterKnife.bind(this);
        chiefPresenter = new ChiefPresenter();
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        ll.setVisibility(View.INVISIBLE);
    }

    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        ll.setVisibility(View.VISIBLE);
    }

    public void showInternetError(final ViewActivity activityInstance) {

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case (DialogInterface.BUTTON_POSITIVE): {
                        chiefPresenter.onUIReady(activityInstance);
                        break;
                    }
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Internet is not available")
                .setCancelable(false)
                .setPositiveButton("Retry", listener)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        chiefPresenter.setReceiver(this);
        chiefPresenter.onUIReady(this);
    }


    @Override
    protected void onStop() {
        super.onStop();
        chiefPresenter.removeReceiver(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "detailedInfoFragment", detailedInfoFragment);
    }
}
