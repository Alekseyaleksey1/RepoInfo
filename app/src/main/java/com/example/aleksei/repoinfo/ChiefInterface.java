package com.example.aleksei.repoinfo;

import java.util.ArrayList;
import java.util.HashMap;

public interface ChiefInterface {

    interface ViewInterface {

        void initRecyclerView(ArrayList<HashMap> mapArrayList);

        void recyclerViewItemSelected();
    }

    interface PresenterInterface {

        void getDataFromDataBase();
    }
}
