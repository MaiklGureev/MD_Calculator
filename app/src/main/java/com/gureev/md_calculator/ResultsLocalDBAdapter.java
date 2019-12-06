package com.gureev.md_calculator;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.gureev.md_calculator.ui.maps.MapsFragment;

import java.util.List;

public class ResultsLocalDBAdapter extends RecyclerView.Adapter<ResultsLocalDBAdapter.ResultsLocalDHolder> {

    private final List<ResOfCalc> resOfCalcs;

    public ResultsLocalDBAdapter(List<ResOfCalc> resOfCalcs) {
        this.resOfCalcs = resOfCalcs;
    }

    @NonNull
    @Override
    public ResultsLocalDHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_local_db, viewGroup, false);
        return new ResultsLocalDHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsLocalDHolder viewHolder, int i) {
        viewHolder.bind(resOfCalcs.get(i));
    }

    @Override
    public int getItemCount() {
        return resOfCalcs.size();
    }

    static final class ResultsLocalDHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView resextView;
        private final MaterialTextView exprTextView;
        private final MaterialTextView dataTimeTextView;
        private final MaterialTextView latTextView;
        private final MaterialTextView lonTextView;
        private final MaterialButton materialButton;


        public ResultsLocalDHolder(@NonNull View itemView) {
            super(itemView);
            resextView = itemView.findViewById(R.id.item_result_res);
            exprTextView = itemView.findViewById(R.id.item_result_expr);
            dataTimeTextView = itemView.findViewById(R.id.item_result_data_time);
            latTextView = itemView.findViewById(R.id.item_result_lat);
            lonTextView = itemView.findViewById(R.id.item_result_lon);
            materialButton = itemView.findViewById(R.id.show_in_osm_button);
        }

        private void bind(@NonNull final ResOfCalc resOfCalc) {
            resextView.setText(String.valueOf(resOfCalc.result));
            exprTextView.setText(resOfCalc.expr);
            dataTimeTextView.setText(resOfCalc.datatime);
            latTextView.setText(resOfCalc.lat);
            lonTextView.setText(resOfCalc.lon);
            materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MapsFragment mapsFragment = new MapsFragment();
                    Bundle bundle = new Bundle();
                    double lat,lon;
                    String expr;
                    lat = Double.parseDouble(resOfCalc.lat);
                    lon = Double.parseDouble(resOfCalc.lon);
                    expr = resOfCalc.expr + " = " + resOfCalc.result;
                    bundle.putDouble("lat",lat);
                    bundle.putDouble("lon",lon);
                    bundle.putString("dateTime",resOfCalc.datatime);
                    bundle.putString("expr",expr);
                    mapsFragment.setArguments(bundle);
                    Context context =  v.getContext();
                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager
                            .beginTransaction()          // получаем экземпляр FragmentTransaction
                            .add(R.id.nav_host_fragment, mapsFragment)
                            .addToBackStack(null)
                            .commit();
                    fragmentManager.popBackStack();
                }
            });
        }


    }

}
