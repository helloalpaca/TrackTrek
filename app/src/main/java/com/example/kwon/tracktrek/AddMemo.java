package com.example.kwon.tracktrek;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
    Button dbBtn;
    EditText memoTitle;
    EditText memoContent;
    static DBHelper helper;
    static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        addBtn = (Button)findViewById(R.id.memoAdd);
        dbBtn = (Button)findViewById(R.id.watchDB);
        memoTitle = (EditText)findViewById(R.id.memoTitle);
        memoContent = (EditText)findViewById(R.id.memo);

        helper = new DBHelper(AddMemo.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        //db = helper.getReadableDatabase();
        helper.onCreate(db);

        //DB에 메모 데이터 추가
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ContentValues values = new ContentValues();

                String Title = memoTitle.getText().toString();
                String Content = memoContent.getText().toString();


                values.put("title", Title);
                values.put("content", Content);
                //values.put("latitude",MainActivity.mLatitude);
                //values.put("longitude",MainActivity.mLongitude);
                values.put("latitude",37.56);
                values.put("longitude", 126.97);
                db.insert("memo", null, values);

                Toast.makeText(AddMemo.this,"ADD Memo",Toast.LENGTH_LONG).show();
            }
        });

        dbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMemo.this,ShowDB.class);
                startActivity(intent);
            }
        });


    }
}

