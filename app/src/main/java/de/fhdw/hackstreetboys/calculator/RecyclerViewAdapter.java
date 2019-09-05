package de.fhdw.hackstreetboys.calculator;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import de.fhdw.hackstreetboys.calculator.shared.DBCalculator;
import de.fhdw.hackstreetboys.calculator.shared.EvalService;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static int TYPE_NORMALCARD = 0;

    public static final int CHANGE_FUNCTION_ID = 1000;
    public static final int CHANGE_SIZE_ID =1001;
    public static final int CHANGE_POSITION_ID =1002;
    public static final int DELETE_ID = 1003;
    public static final int RESET_ID = 1004;

    public DBCalculator DB;

    // Dataset of Layout where three types of data are being stored from MainActivity
    // Key= Position  Value= {Width, Height, Value}
    public List<String[]> DataSet;
    // the Value data will be temporarily in the Arraylist of CalcList for the Calculation
    public ArrayList<String> CalcList = new ArrayList<String>();
    // the Result of Calculation stays at the local variable of CalcResult which will be emitted when = is pressed
    public String CalcResult = "";
    // Constructor: possizeval using shallow Copy and DBCaculator to save the data later on
    public RecyclerViewAdapter(List<String[]> possizeval, DBCalculator db) {
        DataSet = possizeval;
        DB = db;
        setHasStableIds(true); // stable IDs will be distributed
    }
    // In Case of Value Changes of a Tile, this function will be executed in order to save the data in the database,
    // renew the dataset and make the change visibel on the layout of MainActivity
    public void changeValue(int position, String newVal) {
        //PosSizeVal[position][2] = newVal;
        String[] tempdata = DataSet.get(position);
        tempdata[2] = newVal;
        DataSet.set(position,tempdata);
        notifyDataSetChanged();
        DB.SaveLayoutData(DataSet);
        Log.d("LOGTEXT",DataSet.get(position)[2]);
    }
    // with the same logic above (changeValue) for the width and height
    public void changeSize(int position, String width, String height) {
        String[] tempdata = DataSet.get(position);
        tempdata[0] = width;
        tempdata[1] = height;
        DataSet.set(position,tempdata);
        notifyDataSetChanged();
        notifyItemChanged(position);
        DB.SaveLayoutData(DataSet);
    }
    // must be written for RecyclerLayout
    @Override
    public int getItemViewType(int position) {
        // es sendet nur den Wert 0 zurück
        return TYPE_NORMALCARD;
    }
    // duplicate (inflate) the predefined View with each Values, Positions and Sizes
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.grid_item, viewGroup,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    // contains the program logic for dynamic Changes of Tiles Colors, Values
    //            ,the ClickListener to execute mathmatic funtions with EvalService
    //            ,ContextMenu with LongPress, in order to make some changes such as size, value and position
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder recyclerViewHolder, final int position) {
        // store the value of dataset on the postion into a local variable
        String value = DataSet.get(position)[2];
        // Text will be set on the Button surface
        ((RecyclerViewHolder) recyclerViewHolder).mButton.setText(value);
        // this line of code is responsible for the color choice depending on the Tile Value
        recyclerViewHolder.mButton.setBackgroundResource(Tilecolormanager.returncolor(value));
        // To Execute mathmatic functions
        recyclerViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempValue = DataSet.get(position)[2];
                // when the CalcResult is not empty and a tile has been touched, the Value of the tile will be
                // replaced with the Result Value (CalcaResult)
                if (CalcResult != "") {
                    changeValue(position,CalcResult);
                    DB.SaveHistoryData(CalcList,CalcResult);
                    CalcList.clear();
                    CalcResult = ""; // CalcValue is being reset
                } else if (tempValue.equals("=")) {
                    // when the CalcList contains x then the value of the X will be evaluated
                    if (CalcList.contains("x")){
                        CalcResult = EvalService.solveEquation(CalcList,"x","-100000","100000");
                    }else {
                        CalcResult = EvalService.calculateEquation(CalcList);
                    };
                } else if (tempValue != "=" && tempValue != "" && CalcResult == "") {
                    CalcList.add(tempValue); // when the tile is neither = nor empty, the tile value will be added in to the list
                }
            }
        });
        // SetOnLongClickListener
        recyclerViewHolder.mButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View longclickview) {
                longclickview.setOnCreateContextMenuListener( new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu contextMenu, final View contextview, ContextMenu.ContextMenuInfo contextMenuInfo) {
                        contextMenu.setHeaderTitle("Optionen");
                        contextMenu.add(0, CHANGE_FUNCTION_ID,0, "Funktion aendern")
                            .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    final Dialog change_function_dialog = new Dialog(contextview.getContext());
                                    change_function_dialog.setContentView(R.layout.function_options);
                                    change_function_dialog.setTitle("Funktionen aendern");
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

                                    //changes functionality of the tile to a result tile
                                    Button resultbutton = (Button) change_function_dialog.findViewById(R.id.resultbutton);
                                    resultbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"=");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //changes functionality of the tile to a + tile
                                    Button plusbutton = (Button) change_function_dialog.findViewById(R.id.plusbutton);
                                    plusbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"+");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //changes functionality of the tile to a - tile
                                    Button minusbutton = (Button) change_function_dialog.findViewById(R.id.minusbutton);
                                    minusbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"-");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //changes functionality of the tile to a * tile
                                    Button multibutton = (Button) change_function_dialog.findViewById(R.id.multibutton);
                                    multibutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"*");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    //changes functionality of the tile to a / tile
                                    Button divbutton = (Button) change_function_dialog.findViewById(R.id.divbutton);
                                    divbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"/");
                                            change_function_dialog.dismiss();
                                        }
                                    });

                                    Button xbutton = (Button) change_function_dialog.findViewById(R.id.xtarget);
                                    xbutton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            changeValue(position,"x");
                                            change_function_dialog.dismiss();
                                        }
                                    });
                                    change_function_dialog.show();

                                    return false;
                                }
                            });
                        contextMenu.add(0, CHANGE_SIZE_ID,0 , "Größe aendern")
                                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        final Dialog change_size_dialog = new Dialog(contextview.getContext());
                                        change_size_dialog.setContentView(R.layout.options_change_size);
                                        change_size_dialog.setTitle("größe aendern");

                                        //changes Tile to size Xlarge (4x3)
                                        Button dialog_Xlarge = (Button) change_size_dialog.findViewById(R.id.xlarge);
                                        dialog_Xlarge.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: xlarge");
                                                changeSize(position,"4","3");
                                                change_size_dialog.dismiss();
                                            }
                                        });

                                        //changes Tile to size large (2x2)
                                        Button dialog_large = (Button) change_size_dialog.findViewById(R.id.large);
                                        dialog_large.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: large");
                                                changeSize(position,"2","2");
                                                change_size_dialog.dismiss();
                                            }


                                        });

                                        //changes Tile to size medium (1x2)
                                        Button dialog_medium = (Button) change_size_dialog.findViewById(R.id.medium);
                                        dialog_medium.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: medium");
                                                changeSize(position,"1","2");
                                                change_size_dialog.dismiss();
                                            }


                                        });

                                        //changes Tile to size small (1x1)
                                        Button dialog_small = (Button) change_size_dialog.findViewById(R.id.small);
                                        dialog_small.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Log.d("LOGTEXT", "onClick: medium");
                                                changeSize(position,"1","1");
                                                change_size_dialog.dismiss();
                                            }


                                        });

                                        //changes Tile to size wide (4x1)
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
                        contextMenu.add(0, CHANGE_POSITION_ID,0 , "Position aendern")
                                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        final Dialog change_function_dialog = new Dialog(contextview.getContext());
                                        change_function_dialog.setContentView(R.layout.options_change_position);
                                        change_function_dialog.setTitle("Position aendern");

                                        //moves tile to the left
                                        Button move_left = (Button) change_function_dialog.findViewById(R.id.move_left);
                                        move_left.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (position != 0) {
                                                    String[] tempItem = DataSet.get(position);
                                                    DataSet.remove(position);
                                                    DataSet.add(position - 1,tempItem);
                                                    notifyDataSetChanged();
                                                    notifyItemMoved(position, position - 1);
                                                    DB.SaveLayoutData(DataSet);
                                                }
                                                change_function_dialog.dismiss();
                                            }
                                        });

                                        //vmoves tile to the right
                                        Button move_right = (Button) change_function_dialog.findViewById(R.id.move_right);
                                        move_right.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (position != DataSet.size() - 1) {
                                                    String[] tempItem = DataSet.get(position);
                                                    DataSet.remove(position);
                                                    DataSet.add(position + 1,tempItem);
                                                    notifyDataSetChanged();
                                                    notifyItemMoved(position, position + 1);
                                                    DB.SaveLayoutData(DataSet);
                                                }
                                                change_function_dialog.dismiss();
                                            }
                                        });

                                        change_function_dialog.show();

                                        return false;

                                    }
                                });
                        contextMenu.add(0, DELETE_ID, 0, "Loeschen")
                                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        DataSet.remove(position);
                                        notifyDataSetChanged();
                                        notifyItemRemoved(position);
                                        DB.SaveLayoutData(DataSet);
                                        return false;
                                    }
                                });
                        contextMenu.add(0, RESET_ID, 0, "Neue Kachel")
                                .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        String[] tempTile = {"1","1",""};
                                        DataSet.add(tempTile);
                                        notifyItemInserted(DataSet.size() - 1);
                                        DB.SaveLayoutData(DataSet);
                                        return false;
                                    }
                                });
                    }
                });
                return false;
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
