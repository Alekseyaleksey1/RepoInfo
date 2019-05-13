package com.example.aleksei.repoinfo.model;

import android.content.Context;

public class ResponceObserver {

   public static void notifyResponceSuccessful(Context context){

       ChiefModel.getDetailedDataFromInternet(context);


    }
}
