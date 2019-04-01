package com.groupchat.jasonchesney.groupchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class EditGroupName extends AppCompatActivity {

    private ArrayList<String> a1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_name);


    }


    private void initRecyclerView()
    {
        RecyclerView r1=findViewById(R.id.recycler_view);
        a1.add("sfdsf");
        a1.add("dfgfd");
        a1.add("ddd");
        a1.add("jhmjh");
        a1.add("errtt");

        //RecyclerViewGroupName r2=new RecyclerViewGroupName(a1,this);
        //r1.setAdapter(r2);
        //r1.setLayoutManager(new LinearLayoutManager(this));
    }
}
