package com.gureev.md_calculator.ui.server_db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.gureev.md_calculator.R;
import com.gureev.md_calculator.ResOfCalc;
import com.gureev.md_calculator.ResultsLocalDBAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServerDBFragment extends Fragment {

    private ServerDBViewModel serverDBViewModel;
    private DatabaseReference mDatabase;
    private List<ResOfCalc> resOfCalcs = new ArrayList<>();

    // ...
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        serverDBViewModel =
                ViewModelProviders.of(this).get(ServerDBViewModel.class);
        View root = inflater.inflate(R.layout.fragment_server_db, container, false);


        mDatabase = FirebaseDatabase.getInstance().getReference("main_table");

        final RecyclerView recyclerView = root.findViewById(R.id.server_db_recycler_view);
        final ResultsLocalDBAdapter resultsLocalDBAdapter = new ResultsLocalDBAdapter(getData(root));

        //recyclerView.setAdapter(resultsLocalDBAdapter);
        Query query = mDatabase;
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ResOfCalc resOfCalc = dataSnapshot.getValue(ResOfCalc.class);
                resOfCalcs.add(resOfCalc);
                recyclerView.setAdapter(resultsLocalDBAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(mLayoutManager);



//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        serverDBViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private List<ResOfCalc> getData(View root) {

        return resOfCalcs;
    }
}