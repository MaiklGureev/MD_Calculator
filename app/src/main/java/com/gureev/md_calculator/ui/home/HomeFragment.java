package com.gureev.md_calculator.ui.home;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gureev.md_calculator.CalculatorStateMachine;
import com.gureev.md_calculator.DBHelper;
import com.gureev.md_calculator.MyLocationListener;
import com.gureev.md_calculator.R;
import com.gureev.md_calculator.ResOfCalc;

import java.util.Date;

public class HomeFragment extends Fragment {

    private CalculatorStateMachine calculatorStateMachine = new CalculatorStateMachine();

    private TextView textViewExpr, textViewRes;
    private Button one, two, three, four, five, six, seven, eight, nine, zero;
    private Button plus, minus, div, mul, dot, saveResult, del;

    private View root;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    private HomeViewModel homeViewModel;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("object", calculatorStateMachine);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
        {
            calculatorStateMachine = (CalculatorStateMachine) savedInstanceState.getSerializable("object");
        }
        SetTextToView();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        initFB(root.getContext());


        dbHelper = new DBHelper(getContext());
        database = dbHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        one = root.findViewById(R.id.home_button_1);
        two = root.findViewById(R.id.home_button_2);
        three = root.findViewById(R.id.home_button_3);
        four = root.findViewById(R.id.home_button_4);
        five = root.findViewById(R.id.home_button_5);
        six = root.findViewById(R.id.home_button_6);
        seven = root.findViewById(R.id.home_button_7);
        eight = root.findViewById(R.id.home_button_8);
        nine = root.findViewById(R.id.home_button_9);
        zero = root.findViewById(R.id.home_button_0);

        plus = root.findViewById(R.id.home_button_plus);
        minus = root.findViewById(R.id.home_button_minus);
        div = root.findViewById(R.id.home_button_div);
        mul = root.findViewById(R.id.home_button_mul);
        dot = root.findViewById(R.id.home_button_dot);
        saveResult = root.findViewById(R.id.home_button_save);
        del = root.findViewById(R.id.home_button_del);

        textViewExpr = root.findViewById(R.id.home_editText_expression);
        textViewRes = root.findViewById(R.id.home_editText_result);

        int[] numberIds = new int[]{
                one.getId(),
                two.getId(),
                three.getId(),
                four.getId(),
                five.getId(),
                six.getId(),
                seven.getId(),
                eight.getId(),
                nine.getId(),
                zero.getId()
        };

        int[] actionIds = new int[]{
                mul.getId(),
                div.getId(),
                dot.getId(),
                plus.getId(),
                minus.getId(),
                //saveResult.getId(),
                del.getId()
        };


        saveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentValues.put(dbHelper.expression, calculatorStateMachine.getExpr());
                contentValues.put(dbHelper.result, calculatorStateMachine.getResult());
                contentValues.put(dbHelper.dataTime, getDateTime());
                contentValues.put(dbHelper.lat, MyLocationListener.getMyLatitude());
                contentValues.put(dbHelper.lon, MyLocationListener.getMyLongitude());
                database.insert(dbHelper.TABLE_MAIN, null, contentValues);
                //Log.d("Database Operations", "One raw inserted");

                //Cursor cursor = database.query(DBHelper.TABLE_MAIN, null, null, null, null, null, null);

//                if (cursor.moveToFirst()) {
//                    int idIndex = cursor.getColumnIndex(DBHelper.key);
//                    int nameIndex = cursor.getColumnIndex(DBHelper.expression);
//                    int emailIndex = cursor.getColumnIndex(DBHelper.result);
//                    do {
//                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
//                                ", expr = " + cursor.getString(nameIndex) +
//                                ", res = " + cursor.getString(emailIndex));
//                    } while (cursor.moveToNext());
//                } else
//                    Log.d("mLog","0 rows");

                //cursor.close();
                //contentValues.clear();

                sendDataOnServer();
                Toast.makeText(v.getContext(),R.string.toast_message,Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener numButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatorStateMachine.onNumPressed(v.getId());
                //Toast.makeText(v.getContext(),calculatorStateMachine.getState(),Toast.LENGTH_SHORT).show();
                SetTextToView();
            }
        };

        View.OnClickListener actionButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatorStateMachine.onActionPressed(v.getId());
                //Toast.makeText(v.getContext(),calculatorStateMachine.getState(),Toast.LENGTH_SHORT).show();
                SetTextToView();
            }
        };

        for (int i = 0; i < numberIds.length; i++) {
            root.findViewById(numberIds[i]).setOnClickListener(numButtonClickListener);
        }

        for (int i = 0; i < actionIds.length; i++) {
            root.findViewById(actionIds[i]).setOnClickListener(actionButtonClickListener);
        }

//        textViewExpr = root.findViewById(R.id.home_editText_expression);
//        textViewRes = root.findViewById(R.id.home_editText_result);

        calculatorStateMachine = new CalculatorStateMachine();

        InitKeys();

        return root;
    }


    public void InitKeys() {
        del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                calculatorStateMachine.resetAll();
                SetTextToView();
                return false;
            }
        });

    }

    public void SetTextToView() {
        textViewExpr.setText(calculatorStateMachine.getFormattedExpr());
        textViewRes.setText(calculatorStateMachine.getFormattedResult());
    }

    public String getDateTime() {
        Date date = new Date();
        String dt = String.format("%td-%tm-%tY %tk:%tM", date, date, date, date, date);
        return dt;
    }


    public void sendDataOnServer() {
        ResOfCalc resOfCalc = new ResOfCalc(
                calculatorStateMachine.getExpr(),
                Double.parseDouble(calculatorStateMachine.getResult()),
                getDateTime(),
                String.valueOf(MyLocationListener.getMyLatitude()),
                String.valueOf(MyLocationListener.getMyLongitude())
        );
        mDatabaseReference.push().setValue(resOfCalc);
    }

    private void initFB(Context context) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("main_table");
    }

}