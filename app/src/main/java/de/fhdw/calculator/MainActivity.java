package de.fhdw.calculator;

import android.graphics.Rect;
import android.os.PersistableBundle;
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

import java.util.ArrayList;
import java.util.Arrays;

import de.fhdw.shared.EvalService;
import de.fhdw.shared.InternalStorage;
import de.fhdw.shared.LayoutFormat;
import de.fhdw.shared.LayoutFormat.*;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    InternalStorage internalStorage = new InternalStorage();
    SpannedGridLayoutManager spannedGridLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    String[][] fdataset = {{"3","1","1"},{"8","2","2"},{"1","1","3"},{"3","1","4"}};
    String[] felements = {"sizex","sizey","value"};
    String[][] dataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internalStorage.writeJSONFile(this,"layoutformat.json",internalStorage.ConvertToJsonArray(fdataset,felements));
        JSONArray jarray = internalStorage.readJSONFile(this,"layoutformat.json");
        dataset = internalStorage.ConvertToStringArrayArray(jarray,
                felements);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        spannedGridLayoutManager = new SpannedGridLayoutManager(
                SpannedGridLayoutManager.Orientation.VERTICAL, 10);
        spannedGridLayoutManager.setItemOrderIsStable(true);
        SpaceItemDecorator spaceItemDecorator = new SpaceItemDecorator(10,10,10,10);
        // mRecyclerView gets LayoutManager
        mRecyclerView.setLayoutManager(spannedGridLayoutManager);
        // Space Decorator
        mRecyclerView.addItemDecoration(spaceItemDecorator);
        //mRecyclerView.addOnItemTouchListener();
        recyclerViewAdapter = new RecyclerViewAdapter(dataset);

        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer position) {
                if(dataset[position]!=null) {
                    return new SpanSize(Integer.valueOf(dataset[position][0]),Integer.valueOf(dataset[position][1]));
                } else {
                    return new SpanSize(1,1);
                }
            }
        }));

        mRecyclerView.setAdapter(recyclerViewAdapter);

        System.out.println("Test PRS");

        //2,794758327383 ^ 3 / (5 * 11) + 10 + 9000000000 / (500 *1000)
        ArrayList<String> postfix = new ArrayList<String>();
        postfix.add("2.794758327383");
        postfix.add("3");
        postfix.add("^");
        postfix.add("5");
        postfix.add("11");
        postfix.add("*");
        postfix.add("/");
        postfix.add("10");
        postfix.add("+");
        postfix.add("9000000000");
        postfix.add("500");
        postfix.add("1000");
        postfix.add("*");
        postfix.add("/");
        postfix.add("+");

        System.out.println(EvalService.calculateEquation(postfix));

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
}
