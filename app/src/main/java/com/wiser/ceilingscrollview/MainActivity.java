package com.wiser.ceilingscrollview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rlvContent;

    List<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        rlvContent = findViewById(R.id.rlv_content);
        rlvContent.setLayoutManager(new LinearLayoutManager(this));
        rlvContent.setAdapter(new MainAdapter(this,dataList));

//        rlvContent.addItemDecoration(new CeilingItemDecoration(this,1, Color.YELLOW,Color.RED,Color.BLUE,20,10,20, new CeilingItemDecoration.OnGroupListener() {
//            @Override
//			public String getGroupName(int position) {
//                return dataList.get(position).substring(0,1);
//            }
//        }));
    }


    private void initData(){
        dataList.add("java");
        dataList.add("jdk");
        dataList.add("php");
        dataList.add("c++");
        dataList.add("linux");
        dataList.add("windows");
        dataList.add("macos");
        dataList.add("red hat");
        dataList.add("python");
        dataList.add("jvm");
        dataList.add("wechat");
        dataList.add("cellphone");
        dataList.add("iphone");
        dataList.add("mouse");
        dataList.add("huawei");
        dataList.add("xiaomi");
        dataList.add("meizu");
        dataList.add("mocrosoft");
        dataList.add("google");
        dataList.add("hoogle");
        dataList.add("whatsapp");
        dataList.add("iMac");
        dataList.add("c#");
        dataList.add("iOS");
        dataList.add("water");
        dataList.add("xiaohongshu");
        dataList.add("jake");
        dataList.add("zuk");


        Collections.sort(dataList);
    }
}
