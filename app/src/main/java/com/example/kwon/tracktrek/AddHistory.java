package com.example.kwon.tracktrek;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddHistory extends AppCompatActivity {

    Button addHistory;
    EditText setTitle;
    EditText setStartMonth;
    EditText setStartDate;
    EditText setEndMonth;
    EditText setEndDate;

    DBHelper helper;
    SQLiteDatabase db;

    long now;
    Date date;
    SimpleDateFormat sdf;
    String getTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_history);

        addHistory = (Button)findViewById(R.id.addHistory);
        setTitle = (EditText)findViewById(R.id.setTitle);
        setStartMonth = (EditText)findViewById(R.id.setStartMonth);
        setStartDate = (EditText)findViewById(R.id.setStartDate);
        setEndMonth = (EditText)findViewById(R.id.setEndDate);
        setEndDate = (EditText)findViewById(R.id.setEndDate);

        helper = new DBHelper(AddHistory.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        //db = helper.getReadableDatabase();
        helper.onCreate(db);

        //현재 날짜 구하기
        now = System.currentTimeMillis();
        date = new Date(now);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        getTime = sdf.format(date);


        //DB에 History 데이터 추가
        addHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ContentValues values = new ContentValues();

                String Title = setTitle.getText().toString();
                //Integer StartMonth = Integer.parseInt(setStartMonth.getText().toString());
                //Integer StartDate = Integer.parseInt(setStartDate.getText().toString());
                //Integer EndMonth = Integer.parseInt(setEndMonth.getText().toString());
                //Integer EndDate = Integer.parseInt(setEndDate.getText().toString());

                values.put("title", Title);
                values.put("StartDay",getTime);
                values.put("endDay","2018-07-31");

                db.insert("history", null, values);

                System.out.println("title : "+Title+"StartDay : "+getTime+"endDay : "+"2018-07-31");
                Toast.makeText(AddHistory.this,"ADD Memo",Toast.LENGTH_LONG).show();

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
