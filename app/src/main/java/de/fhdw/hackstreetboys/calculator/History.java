package de.fhdw.hackstreetboys.calculator;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import de.fhdw.hackstreetboys.calculator.shared.DBCalculator;

public class History extends ListActivity {

    Button mMainActivityButton; // redirect to MainActivity

    ListView listView;

    DBCalculator dB = new DBCalculator(this);;
    List<String[]> myList = new ArrayList<String[]>();
    Integer myListSize;
    String[] eintrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        System.out.println("Start ---- ");
        dB.RetrieveHistoryData(myList);
        eintrag = new String[myList.size() + 1];
        listView=(ListView)findViewById(android.R.id.list);
        for (int i=0; i<myList.size(); i++){
           eintrag[i]= myList.get(i)[0] + " = " + myList.get(i)[1];
           System.out.println(eintrag[i]);
        }

        //Listview adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,eintrag);
        listView.setAdapter(arrayAdapter);

        mMainActivityButton = (Button) findViewById(R.id.mainactivity);
        mMainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ToMainIntent = new Intent(History.this,MainActivity.class);
                History.this.startActivity(ToMainIntent);
            }

        });



    }
}
