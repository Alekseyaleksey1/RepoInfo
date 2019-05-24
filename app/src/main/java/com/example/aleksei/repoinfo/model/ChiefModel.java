package com.example.aleksei.repoinfo.model;

import android.content.Context;

import com.example.aleksei.repoinfo.model.pojo.RepositoryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiefModel {//todo combine ChiefModel and DatabaseWorker in one class ChiefModel

    static ChiefModel chiefModel;
    List<RepositoryModel> arrayListShortResponce;

    private ChiefModel() {
    }

    public static ChiefModel getInstance() {
        if (chiefModel == null) {
            chiefModel = new ChiefModel();
        }
        return chiefModel;
    }

    public void getDataFromInternet(final Context context) {

        RetrofitTuner.getInstance().getJSONApi().getData().enqueue(new Callback<ArrayList<RepositoryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RepositoryModel>> call, Response<ArrayList<RepositoryModel>> response) {
                arrayListShortResponce = response.body();
                DatabaseWorker.getInstance(context).saveDataToDatabase(arrayListShortResponce);
            }

            @Override
            public void onFailure(Call<ArrayList<RepositoryModel>> call, Throwable t) {

            }
        });
    }

    /*public  void getDetailedDataFromInternet(final Context context) {
        arrayListDetailedResponce = new ArrayList<>();

        for (int i = 0; i < arrayListShortResponce.size(); i++) {
            RetrofitTuner.getInstance().getJSONApi().getDetailedData(arrayListShortResponce.get(i).getFullName()).enqueue(new Callback<POJOModelDetailed>() {//todo adequate naming Full_name
                @Override
                public void onResponse(Call<POJOModelDetailed> call, Response<POJOModelDetailed> detailedResponse) {
                    arrayListDetailedResponce.add(detailedResponse.body());
                }

                @Override
                public void onFailure(Call<POJOModelDetailed> call, Throwable t) {
                    //todo add downloading error message
                }
            });
        }
    }*/
}
