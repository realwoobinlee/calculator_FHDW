package de.fhdw.calculator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
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

    // HashMap key: Long(Position) Values: String(Size ex. 1:3), String(Value ex.'+', 'output'
    //public HashMap<Long,String[]> MapPosSizeVal = new HashMap<Long,String[]>();
    public ArrayList<String[]> DataSet = new ArrayList<String[]>();
    public String[][] PosSizeVal;

    //must be tested!!!
    public RecyclerViewAdapter(String[][] possizeval) {
        //Value,Size(2:2)
        PosSizeVal = possizeval;
        for(int i = 0; i < PosSizeVal.length; i++) {
            DataSet.add(PosSizeVal[i]);
        }
        setHasStableIds(true);
    }

    public void setPosSizeVal(String[][] possizeval) {
        PosSizeVal = possizeval;
        DataSet.clear();
        for(int i = 0; i < PosSizeVal.length; i++) {
            DataSet.add(PosSizeVal[i]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(DataSet.get(position)[2] == "output") {
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
        String value = DataSet.get(position)[2].toString();
        boolean isspace = value.equals("space");

        if(getItemViewType(position) == TYPE_NORMALCARD) {
            if(!isspace) {
                ((RecyclerViewHolder) recyclerViewHolder).setValueInTextView(value);
            }
        } else {
            ((RecyclerViewHolder) recyclerViewHolder).setValueInTextView(value);
        }

        if(!isspace) {
            recyclerViewHolder.mButton.setBackgroundResource(Tilecolormanager.returncolor(value));
        }else {
            recyclerViewHolder.mButton.setBackgroundResource(Tilecolormanager.returncolor(value));
        }

        recyclerViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("LOGTEXT: " + DataSet.get(position)[2]);
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
