package com.example.kwon.tracktrek;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddHistory extends AppCompatActivity {

    Button addHistory;
    EditText setTitle;
    EditText setStartMonth;
    EditText setStartDate;
    EditText setEndMonth;
    EditText setEndDate;

    DBHelper helper;
    SQLiteDatabase db;

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

        //DB에 History 데이터 추가
        addHistory.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                ContentValues values = new ContentValues();

                String Title = setTitle.getText().toString();
                Integer StartMonth = Integer.parseInt(setStartMonth.getText().toString());
                Integer StartDate = Integer.parseInt(setStartDate.getText().toString());
                Integer EndMonth = Integer.parseInt(setEndMonth.getText().toString());
                Integer EndDate = Integer.parseInt(setEndDate.getText().toString());


                values.put("title", Title);
                values.put("startMonth", StartMonth);
                values.put("startDate", StartDate);
                values.put("endMonth", EndMonth);
                values.put("endDay", EndDate);



                db.insert("history", null, values);

                System.out.println("title : "+Title+"시작일 :"+StartMonth+"-"+StartDate+"마지막일 :"+EndMonth+"-"+EndDate);
                Toast.makeText(AddHistory.this,"ADD Memo",Toast.LENGTH_LONG).show();
            }
        });
    }
}
