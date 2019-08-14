package com.example.aleksei.repoinfo.presenter;

import android.content.Context;
import android.view.View;

import com.example.aleksei.repoinfo.model.DataWorker;
import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;
import com.example.aleksei.repoinfo.view.RecyclerViewAdapter;
import com.example.aleksei.repoinfo.view.RepositoriesFragment;
import com.example.aleksei.repoinfo.view.RepoListInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ChiefPresenter {

    public static final String INTERNET_AVAILABILITY_ERROR_CASE = "internetAvailabilityError";
    private RepoListInterface repoListInterfaceInstance;
    private DataWorker dataWorkerInstance;
    private Context appContext;
    private CompositeDisposable disposable;

    public ChiefPresenter(DataWorker dataWorkerInstance, RepoListInterface repoListInterface, Context appContext) {
        this.dataWorkerInstance = dataWorkerInstance;
        this.repoListInterfaceInstance = repoListInterface;
        this.appContext = appContext;
        disposable = new CompositeDisposable();
    }

    private boolean checkDBExists(Context appContext) {
        File dbFile = appContext.getDatabasePath(DataWorker.DB_NAME);
        return dbFile.exists();
    }

    public void onUIReady() {

        repoListInterfaceInstance.showLoading();
        if (checkDBExists(appContext)) {
            onDataInDBPresent();
        } else loadDataFromInternet();
    }

    private void loadDataFromInternet() {
        disposable.add(
                dataWorkerInstance.getDataFromInternet()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<RepositoryModel>>() {
                            @Override
                            public void accept(List<RepositoryModel> repositoryModelList) {
                                onDataFromInternetLoaded(repositoryModelList);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) {
                                repoListInterfaceInstance.showError(INTERNET_AVAILABILITY_ERROR_CASE);
                            }
                        }));
    }

    private void onDataFromInternetLoaded(List<RepositoryModel> repositoryModelList) {
        disposable.add(dataWorkerInstance.saveDataToDatabase(repositoryModelList)
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
                .subscribe(new Consumer<List<RepositoryModel>>() {
                    @Override
                    public void accept(List<RepositoryModel> repositoryModelList) {
                        RecyclerViewAdapter.setDataToAdapter((ArrayList<RepositoryModel>) repositoryModelList);
                        RepositoriesFragment.recyclerViewAdapter.notifyDataSetChanged();
                        repoListInterfaceInstance.hideLoading();
                    }
                }));
    }

    public void prepareClickedView(View clickedView) {
        repoListInterfaceInstance.showItemOnClickedPosition(clickedView);
    }

    public void disposeDisposable() {
        disposable.dispose();
    }
}

