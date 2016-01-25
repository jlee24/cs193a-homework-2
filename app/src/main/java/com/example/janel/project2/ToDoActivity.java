package com.example.janel.project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import stanford.androidlib.SimpleActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoActivity extends SimpleActivity implements AdapterView.OnItemLongClickListener {

    private ArrayList<String> itemList;
    private ArrayAdapter<String> arrayAdapter;
    public static final String TODO_ITEMS = "todoitems.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        readAllItems();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemList);
        ListView items = (ListView) findViewById(R.id.items);
        items.setAdapter(arrayAdapter);
        items.setOnItemLongClickListener(this);

        arrayAdapter.notifyDataSetChanged();
    }

    private void readAllItems() {
        ListView items = (ListView) findViewById(R.id.items);
        itemList = new ArrayList<>();
        try {
            Scanner scan = new Scanner(openFileInput(TODO_ITEMS));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        listSetItems(items, itemList);
    }

    public void addItem(View view) {
        ListView items = (ListView) findViewById(R.id.items);
        EditText newItem = (EditText) findViewById(R.id.new_item);
        String item = newItem.getText().toString();

        try {
            PrintStream out = new PrintStream(
                    openFileOutput(TODO_ITEMS, MODE_PRIVATE)
            );
            out.println(item);
            out.close();
        } catch (IOException ioe) {
            Log.wtf("AddItem", ioe);
        }
        itemList.add(item);
        arrayAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ListView items = (ListView) findViewById(R.id.items);
        itemList.remove(position);
        Toast.makeText(this, "One less thing to do!", Toast.LENGTH_SHORT).show();
        arrayAdapter.notifyDataSetChanged();
        return false;
    }
}
