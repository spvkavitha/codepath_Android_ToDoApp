package com.learnandroid.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private ListView lvItems;
    private ArrayAdapter<String> itemsAdapter;
    private ArrayList<String> items;
    private final String TODO_ITEM1 = "Buy Groceries";
    private final int REQUEST_CODE = 20;
    private final String EDIT_TODO_ITEMS = "itemToEdit";
    private final String POSITION = "position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<String>();
        readItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        items.add(TODO_ITEM1);
        setupListViewListener();
    }

    // Adding items to the ToDoListView
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.eNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems(); // Write when items added

    }

    // Attaching listener to the list view to remove items from the list
    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems(); // Write when items removed
                        return true;
                    }

                });


        // This listener is for launching edit screen
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // launch the Edit Item Activity
                Intent data = new Intent(MainActivity.this, EditActivity.class);
                String currentItem = items.get(position).toString();
                data.putExtra(EDIT_TODO_ITEMS, currentItem);
                data.putExtra(POSITION, position);
                startActivityForResult(data, REQUEST_CODE);
            }
        });
    }


    // On success edit, display changed value on screen
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Save Changes when returned to mainactivity
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String editTodoItem = data.getExtras().getString("itemToEdit");
            int position = data.getIntExtra("position", -1);
            items.set(position,editTodoItem );
            writeItems();
            itemsAdapter.notifyDataSetChanged();
        }
    }

    // Read todolist items from the file
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    // Write todolist items to the file
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
