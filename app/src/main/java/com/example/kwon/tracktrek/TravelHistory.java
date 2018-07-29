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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class TravelHistory extends AppCompatActivity {


    ListView list2;

    Bitmap bitmap;

    //SQLite 객체
    static DBHelper helper;
    static SQLiteDatabase db;

    //DB 데이터
    String titleStr;
    String startDayStr;
    String endDayStr;

    //DB 데이터 여러개 받아오기 위한 Array
    ArrayList<ListViewItemHistory> listViewItemHistoryArrayList = new ArrayList<ListViewItemHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_history);

        list2 = (ListView)findViewById(R.id.list2);

        helper = new DBHelper(TravelHistory.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        //DB데이터 받아오기.
        try {
            readDB();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Button을 누르면 AddHistory화면으로 넘어감.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TravelHistory.this, AddHistory.class);
                startActivity(intent);
            }
        });


        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TravelHistory.this,"position : "+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TravelHistory.this,MainActivity.class);
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

    //DB값 읽어서 ListViewItemHistoryArrayList에 넣음
    public void readDB() throws Exception {
        db = helper.getReadableDatabase();
        Cursor c = db.query("history",
                null, null, null,
                null, null, null);

        SimpleCursorAdapter adapter = null;
        adapter = new SimpleCursorAdapter(TravelHistory.this,
                R.layout.listview_history_item, c,
                new String[]{"title","StartDay","endDay"},
                new int[]{R.id.travelTitle, R.id.startDay, R.id.endDay}, 0);

        list2.setAdapter(adapter);
    }
}
