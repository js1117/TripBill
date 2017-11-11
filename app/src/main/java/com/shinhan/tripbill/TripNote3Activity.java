package com.shinhan.tripbill;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

public class TripNote3Activity extends AppCompatActivity {

    TextView text01;
    SQLiteDatabase database;
    String tableName = "CUSTOMER";
    //DatabaseHelper helper;

    ListView list01;
    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_note3);

        text01 = (TextView) findViewById(R.id.text01);
        list01 = (ListView) findViewById(R.id.list01);


        //DB만들기 버튼
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                createDatabase();
            }
        });

        //테이블만들기 버튼
        Button button04 = (Button) findViewById(R.id.button04);
        button04.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Cursor cursor = queryData();
                //queryDataParam();
                if(cursor != null){
                    startManagingCursor(cursor); //액티비티가 커서를 관리
                    String[] columns = {"_id","name","age"};
                    int [] resIds = {R.id.text01,R.id.text02,R.id.text03};
                    //리턴된 커서의 컬럼과 listitem.xml에서 준비된 리소스아이디와 연결(매칭)

                    //public SimpleCursorAdapter (Context context, int layout, Cursor c, String[] from, int[] to)
                    SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.listitem,cursor, columns,resIds);

                    list01.setAdapter(adapter); //리스트에 아답터 부착
                    //_id 필드명 필요

                }//end if
            }
        });
    }


    //DB 생성
    private void createDatabase(){
        String name = "customer.db";
        //database = openOrCreateDatabase(name, MODE_WORLD_WRITEABLE, null); //DB가 존재하면 오픈. 존재하지않은면 생성
        //int version = 1;
        int version = 2; //Helper의 onUpgrade메소드 호출 확인
        helper = new DatabaseHelper(this, name, null, version);
        database = helper.getWritableDatabase(); //DB에 참조하여 읽거나 쓸수있다.


    }






    //데이터 조회
    private Cursor queryData(){
        String sql = "select _id,name,age from "+ tableName + " where age > 20";
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor != null){
            int count = cursor.getCount(); //조회된 개수얻기
            text01.append("헬퍼안에서 데이터를 조회했어요. 레코드 갯수: "+count + "\n");
        }

        return cursor;
    }


    private void queryDataParam(){
        //String sql = "select _id,name,age from "+ tableName + " where age > 20";
        //Cursor cursor = database.rawQuery(sql, null);

        //String sql = "select _id,name,age from "+ tableName + " where age > ?";
        //String[] args = {"18"};
        //Cursor cursor = database.rawQuery(sql, args);
        //내부적으로 args배열에 있는 값들이 sql쿼리문에 있는 ?(물음표) 를 대체한다.

        //[파라미터를 처리하는 방법]

        //String sql = "select id,name,age from "+ tableName + " where age > 20";
        String[] columns = {"_id", "name", "age"}; //추출할 필드명
        String selection = "age > ?"; //검색 조건
        String[] selectionArgs = {"18"};  //?를 대체할 값

        //Cursor cursor = database.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy)
        Cursor cursor = database.query(tableName, columns, selection, selectionArgs, null, null, null);


        if(cursor != null){
            int count = cursor.getCount(); //조회된 개수얻기
            text01.append("헬퍼안에서 데이터를 조회했어요. 레코드 갯수: "+count + "\n");

            for(int i = 0; i< count ; i++){
                cursor.moveToNext();

                String name=cursor.getString(0) + "/" +cursor.getString(1) +"/"+ cursor.getString(2);
                text01.append("데이터 #"+i+":"+name+"\n");
            }
        }

    }//queryDataParam




    //SQLiteOpenHelper클래스를 상속받은  DatabaseHelper이너 클래스 작성
    class DatabaseHelper extends SQLiteOpenHelper {

        //생성자
        public DatabaseHelper(Context context, String name,
                              CursorFactory factory, int version) {
            super(context, name, factory, version);
        }


        @Override //추상클래스 구현
        public void onCreate(SQLiteDatabase db) {

            text01.append("헬퍼를 이용해서 데이터베이스가 만들어졌어요\n");
            createTable(db);
            insertData(db);
        }

        @Override //추상클래스 구현
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            text01.append("헬퍼를 이용해서 데이터베이스를 업그레이드 했어요 -> 이전버전 : "+oldVersion+", 현재버전 : "+newVersion+"\n");

            //테이블을 변경하고자할때...
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            text01.append("헬퍼를 이용해서 데이터베이스를 오픈했어요.\n");

            super.onOpen(db);

        }


        //테이블 생성
        private void createTable(SQLiteDatabase db) {


            String sql = "create table " + tableName + "(_id integer,name text, age integer)";

            try {
                db.execSQL(sql);//slq문 실행
                text01.append("헬퍼안에서 테이블이 만들어졌어요"+tableName+"\n");
            } catch (Exception e) {
                text01.append("테이블 만들 때 예외 : "+e.getMessage()+"\n");
                e.printStackTrace();
            }

        }

        //데이터넣기
        private void insertData(SQLiteDatabase db){
            db.beginTransaction(); //sql문을 실행하는 일정구간을 트랜잭션으로 묶어주겠다라는 의미
            //트랜잭션 시작을 나타내는 메소드
            try{

                String sql = "insert into "+ tableName + "(_id, name,age) values(200,'장길산',22)";
                db.execSQL(sql);


                for(int i=0 ; i<5;i++){

                    sql = "insert into "+ tableName + "(_id, name,age) values("+(300+i)+",'자바킹"+i+"',23)";
                    db.execSQL(sql);

                }

                text01.append("헬퍼안에서 데이터를 넣었어요\n");

                db.setTransactionSuccessful(); //트랜잭션으로 묶어준 일정영역의 SQL문들이 모두 성공적으로 끝났을 지정

            }catch(Exception e){
                //SQL문 실행중 오류가 발생하면 예외처리가 되고..
                //트랜잭션에 정의된 SQL쿼리가 성공되지 않았기때문에 finally블록에서
                //트랜잭션 종료메서드 실행시(endTransaction()) 롤백이 된다.

                text01.append("데이터 추가할때 예외 : "+e.getMessage()+"\n");
                e.printStackTrace();
            }finally{
                db.endTransaction(); //트랜잭션을 끝내는 메소드.
            }

        }//end insertData

    }//end innerClass DatabaseHelper
  */
}//end
