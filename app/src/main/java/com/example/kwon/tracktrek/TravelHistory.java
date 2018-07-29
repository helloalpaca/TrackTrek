package com.example.kwon.tracktrek;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TravelHistory extends AppCompatActivity {

    ImageButton imgBtn;
    ListView listView;

    Bitmap bitmap;

    //SQLite 객체
    //static DBHelper helper;
    //static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_history);

        imgBtn = (ImageButton)findViewById(R.id.imgBtn);
        listView = (ListView)findViewById(R.id.list2);

        BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.drawable.chopper);
        Bitmap bitmap = drawable.getBitmap();
        imgBtn.setImageBitmap(resizeBitmapImage(bitmap,500));
/*
        helper = new DBHelper(TravelHistory.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        Cursor c = db.query("history",
                null, null, null,
                null, null, null);

        SimpleCursorAdapter adapter = null;
        adapter = new SimpleCursorAdapter(TravelHistory.this,
                R.layout.listview_history_item, c,
                new String[]{"title","startDay","endDay"},
                new int[]{R.id.travelTitle, R.id.startDay, R.id.endDay}, 0);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
*/
        //Button을 누르면 AddHistory화면으로 넘어감.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TravelHistory.this, AddHistory.class);
                startActivity(intent);
            }
        });

    }

    // 이미지 리사이즈(가로==세로) 코드
    public Bitmap resizeBitmapImage(Bitmap bmpSource, int maxResolution) {
        int iWidth = bmpSource.getWidth();      //비트맵이미지의 넓이
        int iHeight = bmpSource.getHeight();     //비트맵이미지의 높이
        int newWidth = iWidth ;
        int newHeight = iHeight ;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if(iWidth > iHeight ){
            if(maxResolution < iWidth ){
                rate = maxResolution / (float) iWidth ;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < iHeight ){
                rate = maxResolution / (float) iHeight ;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }
        return Bitmap.createScaledBitmap(
                bmpSource, newWidth, newHeight, true);
    }
}
