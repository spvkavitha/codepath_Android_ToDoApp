package com.learnandroid.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class EditActivity extends ActionBarActivity {


    private EditText eEditText;
    private int itemPosition;
    private final String EDIT_TODO_ITEMS = "itemToEdit";
    private final String POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        String newText = getIntent().getStringExtra(EDIT_TODO_ITEMS);
        eEditText = (EditText) findViewById(R.id.eEditText);
        eEditText.setText(newText);
        itemPosition = getIntent().getIntExtra(POSITION, -1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSave(View v) {
        // return the changed text and the original position
        Intent result = new Intent();
        result.putExtra(EDIT_TODO_ITEMS, eEditText.getText().toString());
        result.putExtra(POSITION, itemPosition);
        setResult(RESULT_OK, result);
        this.finish();
    }
}
