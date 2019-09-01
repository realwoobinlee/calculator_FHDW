package de.fhdw.calculator;

import android.graphics.Rect;
import android.nfc.Tag;
import android.os.PersistableBundle;
import android.service.autofill.Dataset;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.mariuszgromada.math.mxparser.*;
import com.arasthel.spannedgridlayoutmanager.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fhdw.shared.EvalService;
import de.fhdw.shared.InternalStorage;
import de.fhdw.shared.LayoutFormat;
import de.fhdw.shared.LayoutFormat.*;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    InternalStorage internalStorage = new InternalStorage();
    SpannedGridLayoutManager spannedGridLayoutManager;
    //String[][] fdataset = {{"3","1","1"},{"8","2","2"},{"1","1","3"},{"3","1","+"},{"3","1","+"}};
    //String[] felements = {"sizex","sizey","value"};
    List<String[]> dataset = new ArrayList<String[]>();
    //String[][] dataset = {{"1","1","1"},{"2","2","+"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] temp = {"1","1","1"};
        dataset.add(temp);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        spannedGridLayoutManager = new SpannedGridLayoutManager(
                SpannedGridLayoutManager.Orientation.VERTICAL, 10);
        spannedGridLayoutManager.setItemOrderIsStable(true);
        SpaceItemDecorator spaceItemDecorator = new SpaceItemDecorator(10,10,10,10);
        mRecyclerView.setLayoutManager(spannedGridLayoutManager);
        mRecyclerView.addItemDecoration(spaceItemDecorator);
        recyclerViewAdapter = new RecyclerViewAdapter(dataset);

        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer position) {
                return new SpanSize(Integer.valueOf(dataset.get(position)[0]),Integer.valueOf(dataset.get(position)[1]));
            }
        }));

        mRecyclerView.setAdapter(recyclerViewAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("PRS Test EvalService");

        System.out.println("PRS 7 * 11 + 13 / 19");

        ArrayList<String> postfix = new ArrayList<String>();
        postfix.add("7");
        postfix.add("11");
        postfix.add("*");
        postfix.add("13");
        postfix.add("19");
        postfix.add("/");
        postfix.add("+");

        System.out.println("PRS " + EvalService.calculateEquation(postfix));

        //A * (B + C) / D
        //7 * (11 + 13) / 19
        //7 11 13 + * 19 /

        System.out.println("PRS 7 * (11 + 13) / 19");

        postfix.clear();
        postfix.add("7");
        postfix.add("11");
        postfix.add("13");
        postfix.add("+");
        postfix.add("*");
        postfix.add("19");
        postfix.add("/");

        System.out.println("PRS " + EvalService.calculateEquation(postfix));


        //A * (B + C / D)
        //7 * (11 + 13 / 19)
        //7 11 13 19 / + *

        System.out.println("PRS 7 * (11 + 13 / 19)");

        postfix.clear();
        postfix.add("7");
        postfix.add("11");
        postfix.add("13");
        postfix.add("19");
        postfix.add("/");
        postfix.add("+");
        postfix.add("*");

        System.out.println("PRS " + EvalService.calculateEquation(postfix));

        //2x-4
        postfix.clear();
        postfix.add("2");
        postfix.add("x");
        postfix.add("*");
        postfix.add("4");
        postfix.add("-");

        //Variable, nach der aufgelöst werden soll und Intervall, in dem Nullstellen gesucht werden sollen.
        String target = "x";
        String interval_lower = "-100";
        String interval_upper = "100";

        System.out.println(EvalService.solveEquation(postfix, target, interval_lower, interval_upper));
   }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LOGTEXT","pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LOGTEXT","resume");
    }
}
