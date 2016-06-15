package com.example.gbelton.todoappgillian;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    //private final int RESULT_OK = 10;
    private final int REQUEST_CODE = 20;
    String DATABASE_NAME = "todoItemDatabase";
    int DATABASE_VERSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // first parameter is the context, second is the class of the activity to launch
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);

                i.putExtra("position", position);
                i.putExtra("text", todoItems.get(position));
                i.putExtra("id", id);
                startActivityForResult(i,REQUEST_CODE); // brings up the second activity
            }

        });
    }

    public void populateArrayItems(){
        //todoItems = new ArrayList<String>();
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todoItems);


    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    private void readItems() {

        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
                todoItems = new ArrayList<String>();
            }

    }

    private void writeItems() {

        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file,todoItems);
        } catch (IOException e) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String text = data.getExtras().getString("text");
            int position = data.getExtras().getInt("position");
            todoItems.set(position,text);
            aToDoAdapter.notifyDataSetChanged();
        }

    }

    //to store the data in a mySQL database and not the File Utils thing
    public class TodoItemDatabase extends SQLiteOpenHelper {
        public TodoItemDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // These is where we need to write create table statements.
        // This is called when database is created.
        @Override
        public void onCreate(SQLiteDatabase db) {
            // SQL for creating the tables

        }

        // This method is called when database is upgraded like
        // modifying the table structure,
        // adding constraints to database, etc
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            // SQL for upgrading the tables
        }
    }

}
