package de.fhdw.hackstreetboys.calculator;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.Button;

public class RecyclerViewHolder extends ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    public Button mButton;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mButton = (Button) itemView.findViewById(R.id.tv_griditem);

        mButton.setOnClickListener(this);
    }

    public void setValueInTextView(String val) {
        mButton.setText(val);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
