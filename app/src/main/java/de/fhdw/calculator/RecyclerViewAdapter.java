package de.fhdw.calculator;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private String[] mDataset;
    public HashMap<Long,String[]> MapPosSize = new HashMap<Long,String[]>();

    //must be tested!!!
    public RecyclerViewAdapter(String[][] PosValSize) {
        //Value,Size(2:2)
        for(String[] splited: PosValSize) {
            String[] tempValSize = {splited[0],splited[1]};
            MapPosSize.put(Long.valueOf(splited[0]),tempValSize);
        }
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
