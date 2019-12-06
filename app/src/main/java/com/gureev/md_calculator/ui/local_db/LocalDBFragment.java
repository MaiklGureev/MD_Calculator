package com.gureev.md_calculator.ui.local_db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gureev.md_calculator.DBHelper;
import com.gureev.md_calculator.R;
import com.gureev.md_calculator.ResOfCalc;
import com.gureev.md_calculator.ResultsLocalDBAdapter;

import java.util.ArrayList;
import java.util.List;

public class LocalDBFragment extends Fragment {

    private LocalDBViewModel localDBViewModel;
    private List<ResOfCalc> resOfCalcs = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        localDBViewModel =
                ViewModelProviders.of(this).get(LocalDBViewModel.class);
        View root = inflater.inflate(R.layout.fragment_local_db, container, false);



        RecyclerView recyclerView = root.findViewById(R.id.local_db_recycler_view);
        ResultsLocalDBAdapter resultsLocalDBAdapter = new ResultsLocalDBAdapter(getData(root));
        recyclerView.setAdapter(resultsLocalDBAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);

//        final TextView textView = root.findViewById(R.id.text_gallery);
//        localDBViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }


    private List<ResOfCalc> getData(View root){

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_MAIN, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            int exprIndex = cursor.getColumnIndex(DBHelper.expression);
            int resIndex = cursor.getColumnIndex(DBHelper.result);
            int dataTimeIndex = cursor.getColumnIndex(DBHelper.dataTime);
            int latIndex = cursor.getColumnIndex(DBHelper.lat);
            int lonIndex = cursor.getColumnIndex(DBHelper.lon);
            do {
                resOfCalcs.add(new ResOfCalc(cursor.getString(exprIndex),cursor.getDouble(resIndex),
                        cursor.getString(dataTimeIndex),cursor.getString(latIndex),
                        cursor.getString(lonIndex)));
            } while (cursor.moveToNext());
        } else
            Log.d("mLog","0 rows");

        return resOfCalcs;
    }
}