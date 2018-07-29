package com.example.kwon.tracktrek;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ShowDB extends AppCompatActivity {

    Button showDB;
    EditText deleteTitle;
    Button deleteInstance;
    Button deleteDB;

    //SQLite 객체
    static DBHelper helper;
    static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_db);

        showDB = (Button)findViewById(R.id.showDB);
        deleteTitle = (EditText)findViewById(R.id.deleteTitle);
        deleteInstance = (Button)findViewById(R.id.deleteInstance);
        deleteDB = (Button)findViewById(R.id.deleteDB);

        helper = new DBHelper(ShowDB.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        showDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.query("memo",
                        null, null, null,
                        null, null, null);

                SimpleCursorAdapter adapter = null;
                adapter = new SimpleCursorAdapter(ShowDB.this,
                        R.layout.listview_db_item, c,
                        new String[]{"title","content","latitude", "longitude"},
                        new int[]{R.id.txtTitle, R.id.txtContent, R.id.txtLatitude, R.id.txtLongitude}, 0);

                ListView list = (ListView) findViewById(R.id.list);
                list.setAdapter(adapter);
            }
        });

        //DB에 메모 데이터 추가
        deleteInstance.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String Title = deleteTitle.getText().toString();
                db.execSQL("DELETE FROM memo WHERE title='" + Title + "';");
                Toast.makeText(ShowDB.this,Title+"을(를) 지웠습니다!",Toast.LENGTH_SHORT).show();
            }
        });

        deleteDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete("memo",null,null);
                Toast.makeText(ShowDB.this,"DB를 모두 지웠습니다!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
