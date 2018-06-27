package com.example.luzcamacho.todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> myItems; //storage container for inputed words?
    ArrayAdapter<String> ItemApt; //connection between listview and container
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItems();
        ItemApt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myItems);
        lvItems = findViewById(R.id.listView);
        lvItems.setAdapter(ItemApt);

        /* creating mock data */
       // myItems.add("What is even happening???");
       // myItems.add("Second thing");

        setUpListViewListener();
    }

    public void onAddItem(View V)
    {
        /* why is this method different? */
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        ItemApt.add(itemText);
        etNewItem.setText("");
        writeItems();
        Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
    }

    private void setUpListViewListener()
    {
        Log.i("MainActivity", "Setting up Listener on List View");
        lvItems.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                Log.i("MainActivity", "Item removed from list at position: " + pos);
                myItems.remove(pos);
                ItemApt.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    private File getFileData()
    {
        return new File(getFilesDir(), "todo.txt");
    }

    private void readItems()
    {
        try {
            myItems = new ArrayList<>(FileUtils.readLines(getFileData(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading file. ", e);
            myItems = new ArrayList<>();
        }
    }

    private void writeItems()
    {
        try {
            FileUtils.writeLines(getFileData(), myItems);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing file. ", e);
        }
    }
}
