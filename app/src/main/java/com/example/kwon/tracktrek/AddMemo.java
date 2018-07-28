package com.example.kwon.tracktrek;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddMemo extends AppCompatActivity {

    Button addBtn;
    Button showDB;
    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        addBtn = (Button)findViewById(R.id.memoAdd);
        showDB = (Button)findViewById(R.id.showDB);

        helper = new DBHelper(AddMemo.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ContentValues values = new ContentValues();

                values.put("title", "홍길동");
                values.put("content", "부산시");
                db.insert("memo", null, values);

                Toast.makeText(AddMemo.this,"ADD Memo",Toast.LENGTH_LONG).show();
            }
        });

        showDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.query("memo",
                        null, null, null,
                        null, null, null);

                SimpleCursorAdapter adapter = null;
                adapter = new SimpleCursorAdapter(AddMemo.this,
                        android.R.layout.simple_list_item_2, c,
                        new String[]{"title", "content"},
                        new int[]{android.R.id.text1, android.R.id.text2}, 0);

                ListView list = (ListView) findViewById(R.id.list);
                list.setAdapter(adapter);
            }
        });
    }


}

