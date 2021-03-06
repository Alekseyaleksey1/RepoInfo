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
import com.example.aleksei.repoinfo.model.DataWorker;
import com.example.aleksei.repoinfo.presenter.ChiefPresenter;
import com.example.aleksei.repoinfo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListActivity extends FragmentActivity implements RepoListInterface, RecyclerViewAdapter.ItemClickedCallback {

    private static final String DETAILED_FRAGMENT_KEY = "detailedInfoFragment";
    @BindView(R.id.activity_view_pb)
    ProgressBar progressBar;
    @BindView(R.id.activity_view_ll)
    LinearLayout fragmentsHolder;
    private ChiefPresenter chiefPresenter;
    private DetailedInfoFragment detailedInfoFragment;
    private RepositoriesFragment repositoriesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        initializeUI();
        if (savedInstanceState != null) {
            detailedInfoFragment = (DetailedInfoFragment) getSupportFragmentManager().getFragment(savedInstanceState, DETAILED_FRAGMENT_KEY);
        } else
            detailedInfoFragment = new DetailedInfoFragment();
        repositoriesFragment = new RepositoriesFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_view_fl_fragment_repo, repositoriesFragment);
        fragmentTransaction.replace(R.id.activity_view_fl_fragment_detailed, detailedInfoFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void initializeUI() {
        ButterKnife.bind(this);
        chiefPresenter = new ChiefPresenter(DataWorker.getInstance(this), this, this);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        fragmentsHolder.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.INVISIBLE);
        fragmentsHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorCode) {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case (DialogInterface.BUTTON_POSITIVE): {
                        chiefPresenter.onUIReady();
                        break;
                    }
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setPositiveButton(R.string.btn_error_retry, listener);
        switch (errorCode) {
            case (ChiefPresenter.INTERNET_AVAILABILITY_ERROR_CASE): {
                builder.setMessage(R.string.msg_error_internet);
                break;
            }
            case (DataWorker.INTERNET_DATA_ERROR_CASE): {
                builder.setMessage(R.string.msg_error_data);
                break;
            }
        }
        builder.show();
    }

    @Override
    public void showItemOnClickedPosition(View clickedView) {
        int itemPosition = repositoriesFragment.getRepoFragmentRecyclerView().getChildAdapterPosition(clickedView);
        detailedInfoFragment.setDetailedData(RecyclerViewAdapter.getListDataRepositories().get(itemPosition));
    }

    @Override
    protected void onStart() {
        super.onStart();
        RepositoriesFragment.recyclerViewAdapter.registerForListCallback(this);
        chiefPresenter.onUIReady();
    }

    @Override
    protected void onStop() {
        super.onStop();
        chiefPresenter.disposeDisposable();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, DETAILED_FRAGMENT_KEY, detailedInfoFragment);
    }

    @Override
    public void onItemClicked(View clickedView) {
        chiefPresenter.prepareClickedView(clickedView);
    }
}
