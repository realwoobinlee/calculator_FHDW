package de.fhdw.calculator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static int TYPE_NORMALCARD = 0;
    private static int TYPE_OUTPUTCARD = 1;
    public boolean[] clickedItems = new boolean[70];

    public RecyclerViewAdapter() {
        Arrays.fill(clickedItems,false);
    }

    // HashMap key: Long(Position) Values: String(Size ex. 1:3), String(Value ex.'+', 'output'
    //public HashMap<Long,String[]> MapPosSizeVal = new HashMap<Long,String[]>();
    public ArrayList<String[]> DataSet = new ArrayList<String[]>();

    //must be tested!!!
    public RecyclerViewAdapter(String[][] PosValSize) {
        //Value,Size(2:2)
        for(int i = 0; i < PosValSize.length; i++) {
            DataSet.add(PosValSize[i]);
        }
        setHasStableIds(true);
    }

    @Override
    public int getItemViewType(int position) {
        if(DataSet.get(position)[1] == "output") {
            return TYPE_OUTPUTCARD;
        }
        return TYPE_NORMALCARD;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.grid_item, viewGroup,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, final int position) {
        String value = DataSet.get(position)[2];

        if(getItemViewType(position) == TYPE_NORMALCARD) {
            ((RecyclerViewHolder) recyclerViewHolder).setValueInTextView(value);
        } else {
            ((RecyclerViewHolder) recyclerViewHolder).setValueInTextView(value);
        }
        Log.d("LOGTEXT",String.valueOf(Tilecolormanager.returncolor(value)));

        recyclerViewHolder.mTextView.setBackgroundResource(Tilecolormanager.returncolor(value));

        recyclerViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedItems[position] = !clickedItems[position];
            }
        });
    }

    @Override
    public int getItemCount() {
        return DataSet.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
