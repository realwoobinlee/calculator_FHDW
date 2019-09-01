package de.fhdw.calculator;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.Dataset;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.support.v7.app.AlertDialog;

import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.fhdw.shared.InternalStorage;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static int TYPE_NORMALCARD = 0;
    private static int TYPE_OUTPUTCARD = 1;


    public static final int CHANGE_FUNCTION_ID = 1000;
    public static final int CHANGE_SIZE_ID =1001;
    public static final int CHANGE_POSITION_ID =1002;
    public static final int DELETE_ID = 1003;
    public static final int RESET_ID = 1004;

    // HashMap key: Long(Position) Values: String(Size ex. 1:3), String(Value ex.'+', 'output'
    //public HashMap<Long,String[]> MapPosSizeVal = new HashMap<Long,String[]>();
    //public ArrayList<String[]> DataSet = new ArrayList<String[]>();
    //public String[][] PosSizeVal; // = {{"1","1","-"},{"2","2","9"}};
    public List<String[]> DataSet;
    //must be tested!!!

    public RecyclerViewAdapter(String[][] possizeval) {
        //PosSizeVal = possizeval;
        DataSet = Arrays.asList(possizeval);
        setHasStableIds(true);
    }

    public void changeValue(int position, String newVal) {
        //PosSizeVal[position][2] = newVal;
        String[] tempdata = DataSet.get(position);
        System.out.println("LOGTEXT HALLO" + DataSet.get(position)[2]);
        tempdata[2] = newVal;
        DataSet.set(position,tempdata);

        notifyDataSetChanged();
        Log.d("LOGTEXT",DataSet.get(position)[2]);
    }
    public void changeSize(int position, String width, String height) {
        //PosSizeVal[position][0] = width;
        //PosSizeVal[position][1] = height;

        String[] tempdata = DataSet.get(position);
        tempdata[0] = width;
        tempdata[1] = height;
        DataSet.set(position,tempdata);

        notifyDataSetChanged();
        notifyItemChanged(position);
        Log.d("LOGTEXT",DataSet.get(position)[0] + "," + DataSet.get(position)[1]);
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
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, final int position) {
        String value = DataSet.get(position)[2].toString();
        //String value = DataSet.get(position)[2].toString();
        boolean isspace = value.equals("space");

        recyclerViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("LOGTEXT: " + DataSet.get(position)[0] + "," +DataSet.get(position)[1] + "," +DataSet.get(position)[2]);
            }
        });
        recyclerViewHolder.mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View longclickview) {
                longclickview.setOnCreateContextMenuListener( new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu contextMenu, final View contextview, ContextMenu.ContextMenuInfo contextMenuInfo) {
                        contextMenu.setHeaderTitle("Optionen");
                        contextMenu.add(0, CHANGE_FUNCTION_ID,0, "Funktion ändern")
                            .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    final Dialog change_function_dialog = new Dialog(contextview.getContext());
                                    change_function_dialog.setContentView(R.layout.function_options);
                                    change_function_dialog.setTitle("Funktionen ändern");
                                    final EditText functioninput = (EditText) change_function_dialog.findViewById(R.id.functioninput);
                                    //Schreibt den eigegebenen Text im custom Dialog in die Kachel und schließt den custom Dialog
                                    Button confirmfunction = (Button) change_function_dialog.findViewById(R.id.confirmfunction);
                                    confirmfunction.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,functioninput.getText().toString());
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //Ändert die Funktionalität einer Kachel zu einer ergebnis Kachel
                                    Button resultbutton = (Button) change_function_dialog.findViewById(R.id.resultbutton);
                                    resultbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"=");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //Ändert die Funktionalität einer Kachel dem Operand +
                                    Button plusbutton = (Button) change_function_dialog.findViewById(R.id.plusbutton);
                                    plusbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"+");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //Ändert die Funktionalität einer Kachel dem Operand -
                                    Button minusbutton = (Button) change_function_dialog.findViewById(R.id.minusbutton);
                                    minusbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"-");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //Ändert die Funktionalität einer Kachel dem Operand *
                                    Button multibutton = (Button) change_function_dialog.findViewById(R.id.multibutton);
                                    multibutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"*");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //Ändert die Funktionalität einer Kachel dem Operand /
                                    Button divbutton = (Button) change_function_dialog.findViewById(R.id.divbutton);
                                    divbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"/");
                                            change_function_dialog.dismiss();
                                        }
                                    });
                                    change_function_dialog.show();

                                    return false;
                                }
                            });
                        contextMenu.add(0, CHANGE_SIZE_ID,0 , "Größe ändern")
                                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        final Dialog change_size_dialog = new Dialog(contextview.getContext());
                                        change_size_dialog.setContentView(R.layout.options_change_size);
                                        change_size_dialog.setTitle("größe ändern");

                                        //Ändert die Kachel zu einer vordefinierten Größe Xlarge (4x3)
                                        Button dialog_Xlarge = (Button) change_size_dialog.findViewById(R.id.xlarge);
                                        dialog_Xlarge.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: xlarge");
                                                changeSize(position,"4","3");
                                                change_size_dialog.dismiss();
                                            }
                                        });

                                        //Ändert die Kachel zu einer vordefinierten Größe large (2x2)
                                        Button dialog_large = (Button) change_size_dialog.findViewById(R.id.large);
                                        dialog_large.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: large");
                                                changeSize(position,"2","2");
                                                change_size_dialog.dismiss();
                                            }


                                        });

                                        //Ändert die Kachel zu einer vordefinierten Größe medium (1x2)
                                        Button dialog_medium = (Button) change_size_dialog.findViewById(R.id.medium);
                                        dialog_medium.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: medium");
                                                changeSize(position,"1","2");
                                                change_size_dialog.dismiss();
                                            }


                                        });

                                        //Ändert die Kachel zu einer vordefinierten Größe small (1x1)
                                        Button dialog_small = (Button) change_size_dialog.findViewById(R.id.small);
                                        dialog_small.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: medium");
                                                changeSize(position,"1","1");
                                                change_size_dialog.dismiss();
                                            }


                                        });

                                        //Ändert die Kachel zu einer vordefinierten Größe wide (4x1)
                                        Button dialog_wide = (Button) change_size_dialog.findViewById(R.id.wide);
                                        dialog_wide.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: medium");
                                                changeSize(position,"4","1");
                                                change_size_dialog.dismiss();
                                            }


                                        });

                                        change_size_dialog.show();

                                        return false;
                                    }
                                });
                        contextMenu.add(0, CHANGE_POSITION_ID,0 , "Position ändern")
                                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        final Dialog change_function_dialog = new Dialog(contextview.getContext());
                                        change_function_dialog.setContentView(R.layout.function_options);
                                        change_function_dialog.setTitle("Position ändern");

                                        //verschiebt die Kachel nach links
                                        Button move_left = (Button) change_function_dialog.findViewById(R.id.move_left);
                                        move_left.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: Testi");
                                                change_function_dialog.dismiss();
                                            }
                                        });

                                        //verschiebt die Kachel nach rechts
                                        Button move_right = (Button) change_function_dialog.findViewById(R.id.move_right);
                                        move_right.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: Testi");
                                                change_function_dialog.dismiss();
                                            }
                                        });

                                        change_function_dialog.show();

                                        return false;

                                    }
                                });

                        contextMenu.add(0, RESET_ID, 0, "Leeren")
                                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        changeValue(position,"");
                                        return false;
                                    }
                                });
                    }
                });
                return false;
            }
        });
        if(getItemViewType(position) == TYPE_NORMALCARD) {
            if(!isspace) {
                ((RecyclerViewHolder) recyclerViewHolder).mButton.setText(value);
            }
        } else {
            ((RecyclerViewHolder) recyclerViewHolder).mButton.setText(value);
        }

        if(!isspace) {
            recyclerViewHolder.mButton.setBackgroundResource(Tilecolormanager.returncolor(value));
        }else {
            recyclerViewHolder.mButton.setBackgroundResource(Tilecolormanager.returncolor(value));
        }
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
