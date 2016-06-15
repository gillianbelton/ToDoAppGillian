package com.example.gbelton.todoappgillian;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    int position;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        position = getIntent().getIntExtra("position", 0);
        String text = getIntent().getStringExtra("text");
        id = getIntent().getLongExtra("id", 0);
        EditText editText = (EditText) findViewById(R.id.editedItemText);
        editText.setText(text);
    }

    public void onSubmit(View v) {
        // closes the activity and returns to first screen

        EditText editedText = (EditText) findViewById(R.id.editedItemText);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("text", editedText.getText().toString());
        data.putExtra("position", position); // ints work too
        data.putExtra("id", id);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        this.finish();
    }
}
