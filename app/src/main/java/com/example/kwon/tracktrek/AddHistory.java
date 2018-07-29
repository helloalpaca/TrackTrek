package com.example.kwon.tracktrek;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddHistory extends AppCompatActivity {

    Button addHistory;
    EditText setTitle;
    Button setStart;
    Button setEnd;
    DatePicker dp;


    DBHelper helper;
    SQLiteDatabase db;

    long now;
    Date date;
    SimpleDateFormat sdf;
    String getTime;

    String StartDay;
    String EndDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);

        addHistory = (Button)findViewById(R.id.addHistory);
        setTitle = (EditText)findViewById(R.id.setTitle);
        setStart = (Button)findViewById(R.id.setStart);
        setEnd = (Button)findViewById(R.id.setEnd);
        dp = (DatePicker)findViewById(R.id.dp);


        helper = new DBHelper(AddHistory.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        //db = helper.getReadableDatabase();
        helper.onCreate(db);

        //현재 날짜 구하기
        now = System.currentTimeMillis();
        date = new Date(now);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);

        setStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int year = dp.getYear();
                int month = dp.getMonth();
                int dayOfMonth = dp.getDayOfMonth();

                String syear = Integer.toString(year);
                String smonth;
                if (month < 9)
                {
                    smonth = "0"; smonth+=Integer.toString(month+1);
                }
                else smonth=Integer.toString(month+1);
                String sdayOfMonth = Integer.toString(dayOfMonth);

                StartDay = syear + "-" + smonth + "-" + sdayOfMonth;
                System.out.println("시작일 : " +StartDay);
            }
        });

        setEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int year = dp.getYear();
                int month = dp.getMonth();
                int dayOfMonth = dp.getDayOfMonth();

                String syear = Integer.toString(year);
                String smonth;
                if (month < 9)
                {
                    smonth = "0"; smonth+=Integer.toString(month+1);
                }
                else smonth=Integer.toString(month+1);
                String sdayOfMonth = Integer.toString(dayOfMonth);

                EndDay = syear + "-" + smonth + "-" + sdayOfMonth;
                System.out.println("마지막일 : " +EndDay);
            }
        });


        //DB에 History 데이터 추가
        addHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ContentValues values = new ContentValues();

                String Title = setTitle.getText().toString();

                values.put("title", Title);
                values.put("StartDay",StartDay);
                values.put("endDay",EndDay);

                db.insert("history", null, values);

                System.out.println("title : "+Title+"StartDay : "+getTime+"endDay : "+"2018-07-31");
                Toast.makeText(AddHistory.this,"ADD History",Toast.LENGTH_LONG).show();

                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date beginDate = formatter.parse(getTime);
                    Date endDate = formatter.parse("2018-07-31");

                    // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
                    long diff = endDate.getTime() - beginDate.getTime();
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    System.out.println("날짜차이 : " + diffDays);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
