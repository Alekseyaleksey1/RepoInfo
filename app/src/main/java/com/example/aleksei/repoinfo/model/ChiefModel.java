package com.example.aleksei.repoinfo.model;

import com.example.aleksei.repoinfo.model.pojo.ModelPOJODetailed;
import com.example.aleksei.repoinfo.model.pojo.ModelPOJOShort;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiefModel {

    ArrayList<ModelPOJOShort> arrayListShortResponce;
    ArrayList<ModelPOJODetailed> arrayListDetailedResponce;

    void getDataFromInternet() {
        RetrofitTuner.getInstance().getJSONApi().getData().enqueue(new Callback<ArrayList<ModelPOJOShort>>() {
            @Override
            public void onResponse(Call<ArrayList<ModelPOJOShort>> call, Response<ArrayList<ModelPOJOShort>> response) {

                arrayListShortResponce = response.body();
                arrayListDetailedResponce = new ArrayList<>();

                for (int i = 0; i < arrayListShortResponce.size(); i++) {

                    RetrofitTuner.getInstance().getJSONApi().getDetailedData(arrayListShortResponce.get(i).getFull_name()).enqueue(new Callback<ModelPOJODetailed>() {//todo adequate naming Full_name
                        @Override
                        public void onResponse(Call<ModelPOJODetailed> call, Response<ModelPOJODetailed> detailedResponse) {

                            arrayListDetailedResponce.add(detailedResponse.body());

                        }

                        @Override
                        public void onFailure(Call<ModelPOJODetailed> call, Throwable t) {
                            //todo downloading error message
                        }
                    });

                }


                saveDataFromInternet(arrayListShortResponce, arrayListDetailedResponce);
            }

            @Override
            public void onFailure(Call<ArrayList<ModelPOJOShort>> call, Throwable t) {
                //todo downloading error message
            }
        });
    }

    void saveDataFromInternet(ArrayList<ModelPOJOShort> shortData, ArrayList<ModelPOJODetailed> detailedData) {

        
        //todo saving data to DB
    }
}
