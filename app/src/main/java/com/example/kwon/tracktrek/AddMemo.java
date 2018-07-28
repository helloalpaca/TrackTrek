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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class AddMemo extends AppCompatActivity {

    Button addBtn;
    Button showDB;
    Button deleteDB;
    EditText memoTitle;
    EditText memoContent;
    DBHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        addBtn = (Button)findViewById(R.id.memoAdd);
        showDB = (Button)findViewById(R.id.showDB);
        deleteDB = (Button)findViewById(R.id.deleteDB);
        memoTitle = (EditText)findViewById(R.id.memoTitle);
        memoContent = (EditText)findViewById(R.id.memo);

        helper = new DBHelper(AddMemo.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ContentValues values = new ContentValues();

                String Title = memoTitle.getText().toString();
                String Content = memoContent.getText().toString();


                values.put("title", Title);
                values.put("content", Content);
                values.put("latitude",MainActivity.mLatitude);
                values.put("longitude",MainActivity.mLongitude);
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
                        R.layout.listview_db_item, c,
                        new String[]{"title","content","latitude", "longitude"},
                        new int[]{R.id.txtTitle, R.id.txtContent, R.id.txtLatitude, R.id.txtLongitude}, 0);

                ListView list = (ListView) findViewById(R.id.list);
                list.setAdapter(adapter);
            }
        });

        deleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete("memo", "title=?", new String[]{"홍길동"});
            }
        });
    }


}

