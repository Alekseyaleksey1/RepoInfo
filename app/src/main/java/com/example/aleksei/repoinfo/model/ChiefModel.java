package com.example.aleksei.repoinfo.model;

import android.content.Context;
import android.util.Log;

import com.example.aleksei.repoinfo.model.pojo.ModelPOJODetailed;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiefModel {

    //static List<ModelPOJODetailed> arrayListDetailedResponce;
    static ChiefModel chiefModel;
    List<ModelPOJOShort> arrayListShortResponce;

    private ChiefModel() {
    }

    public static ChiefModel getInstance() {
        if (chiefModel == null) {
            chiefModel = new ChiefModel();
        }
        return chiefModel;
    }

    public void getDataFromInternet(final Context context) {

        RetrofitTuner.getInstance().getJSONApi().getData().enqueue(new Callback<ArrayList<ModelPOJOShort>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelPOJOShort>> call, Response<ArrayList<ModelPOJOShort>> response) {
                arrayListShortResponce = response.body();
                SQLiteWorker.getInstance(context).saveDataToDatabase(arrayListShortResponce);
            }

            @Override
            public void onFailure(Call<ArrayList<ModelPOJOShort>> call, Throwable t) {

            }
        });
    }

    /*public  void getDetailedDataFromInternet(final Context context) {
        arrayListDetailedResponce = new ArrayList<>();

        for (int i = 0; i < arrayListShortResponce.size(); i++) {
            RetrofitTuner.getInstance().getJSONApi().getDetailedData(arrayListShortResponce.get(i).getFull_name()).enqueue(new Callback<ModelPOJODetailed>() {//todo adequate naming Full_name
                @Override
                public void onResponse(Call<ModelPOJODetailed> call, Response<ModelPOJODetailed> detailedResponse) {
                    arrayListDetailedResponce.add(detailedResponse.body());
                }

                @Override
                public void onFailure(Call<ModelPOJODetailed> call, Throwable t) {
                    //todo add downloading error message
                }
            });
        }
    }*/
}
