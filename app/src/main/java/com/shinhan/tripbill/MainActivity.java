package com.shinhan.tripbill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;

    GridView gridView;
    SingerAdapter adapter;

    //SQLiteDatabase sqliteDB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB 초기화
        //sqliteDB = init_database() ;

        gridView = (GridView) findViewById(R.id.gridView);

        adapter = new SingerAdapter();

        adapter.addItem(new SingerItem("일본", "9/1 ~ 9/3", 1, R.drawable.japan));
        adapter.addItem(new SingerItem("미국", "10/11 ~ 10/20", 2, R.drawable.usa));
        adapter.addItem(new SingerItem("영국", "11/1 ~ 11/10", 3, R.drawable.england));

        gridView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editText.getText().toString();
                String mobile = "00";
                int age = 4;

                adapter.addItem(new SingerItem(name, mobile, age, R.drawable.travel));
                adapter.notifyDataSetChanged();
            }
        });

        //클릭한 후 next
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //SingerItem item = (SingerItem) adapter.getItem(position);
                //Toast.makeText(getApplicationContext(), "선택 : " + item.getName(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(
                        getApplicationContext(), // 현재화면의 제어권자
                        TripNote1Activity.class); // 다음넘어갈 화면

                startActivity(intent);

            }
        });
    }

    public class SingerItemView extends LinearLayout {
        TextView textView;
        TextView textView2;
        TextView textView3;
        ImageView imageView;

        public SingerItemView(Context context) {
            super(context);
            init(context);
        }

        public SingerItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public void init(Context context) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.singer_item, this, true);

            textView = (TextView) findViewById(R.id.textView);
            textView2 = (TextView) findViewById(R.id.textView2);
            textView3 = (TextView) findViewById(R.id.textView3);
            imageView = (ImageView) findViewById(R.id.imageView);
        }

        public void setName(String name) {
            textView.setText(name);
        }
        public void setMobile(String mobile) {
            textView2.setText(mobile);
        }
        public void setAge(int age) {
            textView3.setText(String.valueOf(age));
        }
        public void setImage(int resId) {
            imageView.setImageResource(resId);
        }
    }

    public class SingerItem {
        String name;
        String mobile;
        int age;
        int resId;

        public SingerItem(String name, String mobile) {
            this.name = name;
            this.mobile = mobile;
        }

        public SingerItem(String name, String mobile, int age, int resId) {
            this.name = name;
            this.mobile = mobile;
            this.age = age;
            this.resId = resId;
        }

        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public int getResId() {
            return resId;
        }
        public void setResId(int resId) {
            this.resId= resId;
        }
        public String getMobile() {
            return mobile;
        }
        public void setMobile(String mobile) {
            this.mobile= mobile;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name= name;
        }
    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount() {
            return items.size();
        }
        public void addItem(SingerItem item) {
            items.add(item);
        }
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            SingerItemView view = new SingerItemView(getApplicationContext());
            SingerItem item = items.get(position);
            view.setName(item.getName());
            view.setMobile(item.getMobile());
            view.setAge(item.getAge());
            view.setImage(item.getResId());

            int numColumns = gridView.getNumColumns();
            int rowIndex = position / numColumns;
            int columnIndex = position % numColumns;
            Log.d("SingerAdapter", "index : " + rowIndex + ", " + columnIndex);

            return view;
        }
    }

    /*
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
     */

}
