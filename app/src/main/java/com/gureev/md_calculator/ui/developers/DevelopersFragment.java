package com.gureev.md_calculator.ui.developers;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gureev.md_calculator.R;

public class DevelopersFragment extends Fragment {

    private DevelopersViewModel developersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        developersViewModel =
                ViewModelProviders.of(this).get(DevelopersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_developers, container, false);
//        final TextView textView = root.findViewById(R.id.text_tools);
//        developersViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}