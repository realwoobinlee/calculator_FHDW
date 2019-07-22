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

import de.fhdw.shared.InternalStorage;
import de.fhdw.shared.LayoutFormat;
import de.fhdw.shared.LayoutFormat.*;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    InternalStorage internalStorage = new InternalStorage();
    SpannedGridLayoutManager spannedGridLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    String[][] fdataset = {{"4","1","view"},{"4","3","space"},{"1","1","vector"},{"1","1", "prime"},
            {"1","1", "AC"},{"1","1", "+/-"},{"1","1", "%"},{"1","1", "/"},{"1","1", "ln"},{"1","1", "xsqr"},
            {"1","1", "7"},{"1","1", "8"},{"1","1", "9"},{"1","1", "*"},{"1","1", "sin"},{"1","1", "cos"},
            {"1","1", "4"},{"1","1", "5"},{"1","1", "6"},{"1","1", "-"},{"1","1", "i"},{"1","1", "e"},{"1","1", "c"},{"1","1", "pi"},{"1","1", "tan"},{"1","1", "xsqr"},
            {"1","1", "1"},{"1","1", "2"},{"1","1", "3"},{"1","1", "+"},{"1","1", "y"},{"1","1", "x"},{"1","1", "electronm"},{"1","1", "electronc"},{"1","1", "log"},{"1","1", "log10"},
        {"1","1", "0"},{"1","1", ","},{"1","1", "^"},{"1","1", "sqr"},{"1","1", "result"},{"1","1", "result"},{"1","1", ""},{"1","1", ""},{"1","1", "summe"},{"1","1", "seq"},
            {"1","1", "10"},{"1","1", "100"},{"1","1", "."},{"1","1", ""},{"1","1", ""},{"1","1", ""},{"1","1", ""},{"1","1", ""},{"1","1", "summenprodukt"},{"1","1", "bin"}};
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
    }

}
