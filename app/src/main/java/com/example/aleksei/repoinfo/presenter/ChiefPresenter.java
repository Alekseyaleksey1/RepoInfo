package com.example.aleksei.repoinfo.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import com.example.aleksei.repoinfo.model.DataWorker;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import com.example.aleksei.repoinfo.view.RepositoriesFragment;
import com.example.aleksei.repoinfo.view.ViewInterface;
import java.io.File;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ChiefPresenter {

    public static final String INTERNET_AVAILABILITY_ERROR_CASE = "internetAvailabilityError";
    private ViewInterface viewInterfaceInstance;
    private DataWorker dataWorkerInstance;
    private Context appContext;
    private CompositeDisposable disposable;

    public ChiefPresenter(DataWorker dataWorkerInstance, ViewInterface viewInterface, Context appContext) {
        this.dataWorkerInstance = dataWorkerInstance;
        this.viewInterfaceInstance = viewInterface;
        this.appContext = appContext;
    }

    private boolean checkDBExists(Context appContext) {
        File dbFile = appContext.getDatabasePath(DataWorker.DB_NAME);
        return dbFile.exists();
    }

    private boolean checkInternetAvailability(Context appContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void onUIReady() {
        viewInterfaceInstance.showLoading();
        disposable = new CompositeDisposable();
        if (checkDBExists(appContext)) {
            onDataInDBPresent();
        } else {
            if (checkInternetAvailability(appContext)) {
                loadDataFromInternet();
            } else {
                viewInterfaceInstance.showError(INTERNET_AVAILABILITY_ERROR_CASE);
            }
        }
    }

    private void loadDataFromInternet() {
        disposable.add(dataWorkerInstance.getDataFromInternet()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<RepositoryModel>>() {
                    @Override
                    public void accept(ArrayList<RepositoryModel> repositoryModelArrayList) {
                        dataWorkerInstance.setListOfRepositoriesResponse(repositoryModelArrayList);
                        onDataFromInternetLoaded();
                    }
                }));
    }

    private void onDataFromInternetLoaded() {
        disposable.add(dataWorkerInstance.saveDataToDatabase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        onDataInDBPresent();
                    }
                }));
    }

    private void onDataInDBPresent() {
        disposable.add(dataWorkerInstance.getDataFromDatabase()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() {
                        onDataFromDBRetrieved();
                    }
                }));
    }


    private void onDataFromDBRetrieved() {
        RecyclerViewAdapter.setDataToAdapter(dataWorkerInstance.getDataToRetrieve());
        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
        viewInterfaceInstance.hideLoading();
    }

    public void prepareClickedView(View clickedView) {
        viewInterfaceInstance.showItemOnClickedPosition(clickedView);
    }

    public void disposeDisposable() {
        disposable.dispose();
    }
}

