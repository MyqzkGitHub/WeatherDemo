 package com.example.administrator.weatherdemo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.weatherdemo.Dao.BookStoreDao;
import com.example.administrator.weatherdemo.bean.BookStore;
import com.example.administrator.weatherdemo.bean.WeatherInfo;
import com.example.administrator.weatherdemo.service.WeatherService;

import java.util.List;

 public class MainActivity extends AppCompatActivity {

     private PersonSQLiteOpenHelper dbHelper;
     private TextView tv;
     private BookStoreDao dao;
     private LinearLayout ll_root;
     private List<BookStore> bookStores;
     private static String[] names = {"功能1","功能2","功能3","功能4","功能5","功能6","功能7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new PersonSQLiteOpenHelper(this,"BookStore.db",null,2);
        Button createDataBase =findViewById(R.id.create);
        Button addData =findViewById(R.id.add_data);
        Button upData =findViewById(R.id.updata_data);
       // tv = findViewById(R.id.tv);
        ll_root = (LinearLayout)findViewById(R.id.ll_root);
        dao = new BookStoreDao(this);
        bookStores = dao.findAll();
        ListView lv = findViewById(R.id.lv);
        lv.setAdapter(new MyAdapter());
        for (BookStore bookStore : bookStores){
            String info = bookStore.toString();
            TextView data = new TextView(this);
            data.setTextSize(20);
            data.setTextColor(Color.RED);
            data.setText(info);
            ll_root.addView(data);
        }
        //lv.setAdapter(new ArrayAdapter<String>(this,R.layout.list_views,R.id.array,names));
        try {
            List<WeatherInfo> infos = WeatherService.getWeatherInfos(
                    MainActivity
                            .class
                            .getClassLoader()
                            .getResourceAsStream("weather.xml"));
            StringBuffer sb = new StringBuffer();
            for (WeatherInfo info : infos){
                String str = info.toString();
                sb.append(str);
                sb.append("\n");
            }
            //tv.setText(sb.toString());
        } catch (Exception e) {
            Toast.makeText(this,"解析失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        createDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
                Toast.makeText(MainActivity.this, "创建成功", Toast.LENGTH_SHORT).show();
            }
        });
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                for(int i=0;i<50;i++){
                    //开始组装第一条数据
                    values.put("name","第一行代码"+i);
                    values.put("author","郭霖"+i);
                    values.put("pages",666+i);
                    values.put("price",45.95);
                    db.insert("Book",null,values);//插入第一条数据
                    values.clear();
                }
                //开始组装第二条数据
                values.put("name","C#");
                values.put("author","张三");
                values.put("pages",342);
                values.put("price",35.95);
                db.insert("Book",null,values);//插入第二条数据
            }
        });
        upData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private class MyAdapter extends BaseAdapter{

        /**
         * 控制ListView里面总共有多少条目
         * @return
         */
        @Override
        public int getCount() {
            return bookStores.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            /*Log.d("MyAdapter", "返回View对象,位置:"+i);
            TextView tv = new TextView(getApplicationContext());
            tv.setTextSize(20);
            tv.setTextColor(Color.GREEN);
            //得到某个位置对应的bookStore对象
            BookStore bookStore = bookStores.get(i);
            tv.setText(bookStore.toString());
            return tv;*/
            //得到某个位置对应的bookStore对象
            BookStore bookStore = bookStores.get(i);
            View v = View.inflate(MainActivity.this,R.layout.list_view,null);
            TextView id = v.findViewById(R.id.id);
            id.setText("ID:"+bookStore.getId());
            TextView author = v.findViewById(R.id.author);
            author.setText("作者:"+bookStore.getAuthor());
            TextView name = v.findViewById(R.id.name);
            name.setText("书名:"+bookStore.getName());
            return v;
        }
    }
}
