package com.example.aleksei.repoinfo;

import android.content.Intent;
import java.util.HashMap;

public class ChiefPresenter {

    public static boolean checkDBExists(){

        return false;
    }

    public static boolean checkInternetAvailability(){
        return false;
    }

    public static void startDetailedInfoActivity(HashMap hashMap) {
        Intent intent = new Intent(RepositoriesActivity.context, DetailedInfoActivity.class);
        intent.putExtra("fullName", hashMap.get("fullName").toString());
        RepositoriesActivity.context.startActivity(intent);
    }
}
