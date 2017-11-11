package com.shinhan.tripbill;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TripNote2Activity extends AppCompatActivity {

    String dbname = "contact.db";
    String tablename = "TRIPNAME";
    String sql;
    SQLiteDatabase db;   // db를 다루기 위한 SQLiteDatabase 객체 생성
    Cursor resultset;   // select 문 출력위해 사용하는 Cursor 형태 객체 생성
    ListView listView;   // ListView 객체 생성
    String[] result;   // ArrayAdapter에 넣을 배열 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_note2);

        db = openOrCreateDatabase(dbname, MODE_PRIVATE, null);   // 있으면 열고 없으면 DB를 생성
        listView = (ListView)findViewById(R.id.list01);

        Button lvbutton = (Button) findViewById(R.id.lvbutton) ;
        lvbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v){   // 항상 DB문을 쓸때는 예외처리(try-catch)를 해야한다
                try {
                    sql = "select * from "+ tablename;
                    resultset = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

                    int count = resultset.getCount();   // db에 저장된 행 개수를 읽어온다
                    result = new String[count];   // 저장된 행 개수만큼의 배열을 생성

                    for (int i = 0; i < count; i++) {
                        resultset.moveToNext();   // 첫번째에서 다음 레코드가 없을때까지 읽음
                        String str_date = resultset.getString(0);   // 첫번째 속성
                        String str_time = resultset.getString(1);   // 두번째 속성
                        String str_name = resultset.getString(2);   // 세번째 속성
                        String str_amt  = resultset.getString(3);   // 세번째 속성
                        String str_currency = resultset.getString(4);   // 세번째 속성
                        result[i] = str_date + " " + str_time + " " + str_name + " " + str_amt + " " + str_currency;   // 각각의 속성값들을 해당 배열의 i번째에 저장
                    }
                    System.out.println("select ok");

                    //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, result);   // ArrayAdapter(this, 출력모양, 배열)
                    ArrayAdapter adapter = new ArrayAdapter(this, R.layout.tran_list, result);
                    listView.setAdapter(adapter);   // listView 객체에 Adapter를 붙인다

                } catch (Exception e) {
                    System.out.println("select Error :  " + e);
                }
            }
        });
    }
}
