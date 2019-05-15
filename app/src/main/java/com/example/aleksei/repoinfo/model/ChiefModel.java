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

    static List<ModelPOJOShort> arrayListShortResponce;
    static List<ModelPOJODetailed> arrayListDetailedResponce;

//todo is DB existing? false - getDataFromInterner-saveDataToDatabase, true - getDataFromDatabase

    public static void getDataFromInternet(final Context context) {

        RetrofitTuner.getInstance().getJSONApi().getData().enqueue(new Callback<ArrayList<ModelPOJOShort>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelPOJOShort>> call, Response<ArrayList<ModelPOJOShort>> response) {
                arrayListShortResponce = response.body();
                Log.i("saveDataToDatabase", "success");
                SQLiteWorker.saveDataToDatabase(context, arrayListShortResponce);
            }
            @Override
            public void onFailure(Call<ArrayList<ModelPOJOShort>> call, Throwable t) {
                //todo add downloading error message
                Log.i("saveDataToDatabase", "error");
            }
        });
    }

//TODO requesting of detailed data only on click of list item
    public static void getDetailedDataFromInternet(final Context context) {
        arrayListDetailedResponce = new ArrayList<>();

        for (int i = 0; i < arrayListShortResponce.size(); i++) {
            Log.i("arrayListShortResponce", arrayListShortResponce.get(i).getFull_name());
            RetrofitTuner.getInstance().getJSONApi().getDetailedData(arrayListShortResponce.get(i).getFull_name()).enqueue(new Callback<ModelPOJODetailed>() {//todo adequate naming Full_name
                @Override
                public void onResponse(Call<ModelPOJODetailed> call, Response<ModelPOJODetailed> detailedResponse) {
                    Log.i("saveDataToDatabase", "onResponse ModelPOJODetailed");
                    arrayListDetailedResponce.add(detailedResponse.body());
                }
                @Override
                public void onFailure(Call<ModelPOJODetailed> call, Throwable t) {
                    //todo add downloading error message
                    Log.i("saveDataToDatabase", "onFailure ModelPOJODetailed");
                }
            });
        }
    }
}
