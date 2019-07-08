package de.fhdw.calculator;

import android.graphics.Rect;
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

import de.fhdw.shared.InternalStorage;
import de.fhdw.shared.InternalStorage.*;
import de.fhdw.shared.LayoutFormat;
import de.fhdw.shared.LayoutFormat.*;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;

    SpannedGridLayoutManager spannedGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _init();
    }

    void _init() {
        String[][] dataset = {{"1","2","+"},{"2","1","+"},{"3","1","view"}};
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
        final RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(dataset);
        spannedGridLayoutManager.setSpanSizeLookup(new SpannedGridLayoutManager.SpanSizeLookup(new Function1<Integer, SpanSize>() {
            @Override
            public SpanSize invoke(Integer position) {
                for (int i = 0; i < recyclerViewAdapter.clickedItems.length; i++) {
                    if (recyclerViewAdapter.clickedItems[position] == true) {
                        return new SpanSize(2, 2);
                }
            }
                return new SpanSize(1,1);
            }
        }));
        mRecyclerView.setAdapter(recyclerViewAdapter);
    }  

    @Override
    public void onClick(View v) {

    }
}
