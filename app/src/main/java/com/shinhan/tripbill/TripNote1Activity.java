package com.shinhan.tripbill;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

public class TripNote1Activity extends AppCompatActivity {

    //createTable();
    SQLiteDatabase sqliteDB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_note1);

        sqliteDB = init_database() ;
        init_tables() ;

        Button buttonSave = (Button) findViewById(R.id.buttonSave) ;
        buttonSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력값 insert
                save_values() ;
                // 화면 전환 (입력자료 확인)
                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        TripNote2Activity.class); // 다음넘어갈 화면

                startActivity(intent);


            }
        });

    }

    private SQLiteDatabase init_database() {

        SQLiteDatabase db = null ;
        // File file = getDatabasePath("contact.db") ;
        File file = new File(getFilesDir(), "contact.db") ;

        System.out.println("PATH : " + file.toString()) ;
        try {
            db = SQLiteDatabase.openOrCreateDatabase(file, null) ;
        } catch (SQLiteException e) {
            e.printStackTrace() ;
        }

        if (db == null) {
            System.out.println("DB creation failed. " + file.getAbsolutePath()) ;
        }

        return db ;
    }

    // 테이블 생성
    private void init_tables() {

        if (sqliteDB != null) {
            String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS TRIPNAME (" +
                    "DATE "         + "INTEGER NOT NULL," +
                    "TIME "         + "TEXT," +
                    "NAME "         + "TEXT," +
                    "AMOUNT "       + "INTEGER," +
                    "CURRENCY "     + "TEXT" + ")" ;
            System.out.println(sqlCreateTbl) ;

            sqliteDB.execSQL(sqlCreateTbl) ;
        }
    }

    // 자료 insert
    private void save_values() {
        if (sqliteDB != null) {

            // delete
            //sqliteDB.execSQL("DELETE FROM CONTACT_T") ;

            EditText editTextDate = (EditText) findViewById(R.id.editTextDate) ;
            String DateText = editTextDate.getText().toString() ;
            int date = 0 ;
            if (DateText != null && !DateText.isEmpty()) {
                date = Integer.parseInt(DateText) ;
            }

            EditText editTextTime = (EditText) findViewById(R.id.editTextTime) ;
            String time = editTextTime.getText().toString() ;

            EditText editTextName = (EditText) findViewById(R.id.editTextName) ;
            String name = editTextName.getText().toString() ;

            EditText editTextAmount = (EditText) findViewById(R.id.editTextAmount) ;
            String AmountText = editTextAmount.getText().toString() ;
            int amount = 0 ;
            if (AmountText != null && !AmountText.isEmpty()) {
                amount = Integer.parseInt(AmountText) ;
            }

            EditText editTextCurrency = (EditText) findViewById(R.id.editTextCurrency) ;
            String currency = editTextCurrency.getText().toString() ;

            String sqlInsert = "INSERT INTO TRIPNAME " +
                    "(DATE, TIME, NAME, AMOUNT, CURRENCY) VALUES (" +
                    Integer.toString(date) + "," +
                    "'" + time + "'," +
                    "'" + name + "'," +
                    Integer.toString(amount) + "," +
                    "'" + currency + "')" ;

            System.out.println(sqlInsert) ;

            sqliteDB.execSQL(sqlInsert) ;
        }
    }


}
