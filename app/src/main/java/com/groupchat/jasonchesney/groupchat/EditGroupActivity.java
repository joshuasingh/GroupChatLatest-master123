package com.groupchat.jasonchesney.groupchat;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditGroupActivity extends AppCompatActivity {
    private RecyclerView sremainlist;
    Toolbar mtoolbar;
    AppBarLayout apb;
    private EditGroupRecyclerAdapter firebasereycleradapter;
    private DataSnapshot ds;

    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    private DatabaseReference rootRef, memberref, gRef, userRef, gnameRef, editRef;

    private String currentUserID, userProfileimage, currentUserName, newGroupName, randfetch, randfetch1, memtype;
    private String MEMBER_TYPE = "Admin";
    private ArrayList<String> a1=new ArrayList<>();
    private ArrayList<String> a2=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        DatabaseReference ref45 = FirebaseDatabase.getInstance().getReference().child("Admin");

        ref45.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Toast.makeText(MainpageActivity.this,"in ds",Toast.LENGTH_LONG).show();
                ds = dataSnapshot;

                //check pending requests
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        /* if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        editRef = FirebaseDatabase.getInstance().getReference().child("Admin").child("EditGroup");
        gnameRef = FirebaseDatabase.getInstance().getReference("Groups");
        currentUserID = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        rootRef = FirebaseDatabase.getInstance().getReference();

        setUpRecyclerView();

        apb = (AppBarLayout) findViewById(R.id.sappBarLayout);

        mtoolbar = (Toolbar) findViewById(R.id.smainpagetoolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#10101'><h6>Groups</h6></font>"));

        Toast.makeText(EditGroupActivity.this,"here",Toast.LENGTH_LONG).show();
       */

    }

    /*private void setUpRecyclerView()
    {

        FirebaseRecyclerOptions<EditModel> sgoptions = new FirebaseRecyclerOptions.Builder<EditModel>()
                .setQuery(editRef, EditModel.class).build();

        firebasereycleradapter = new EditGroupRecyclerAdapter(sgoptions);

        sremainlist = (RecyclerView) findViewById(R.id.sremainlist);
        sremainlist.setHasFixedSize(true);
        sremainlist.setLayoutManager(new LinearLayoutManager(this));
        sremainlist.setAdapter(firebasereycleradapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebasereycleradapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        //firebasereycleradapter.stopListening();
    }*/

    private void initRecyclerView()
    { DatabaseReference ref11 = FirebaseDatabase.getInstance().getReference("Admin").child("Pending Request");
        for (DataSnapshot datasnap : ds.child("EditGroup").getChildren()) {


            //  Toast.makeText(MainpageActivity.this,name,Toast.LENGTH_LONG).show();
            a1.add(datasnap.getKey());
            a2.add((String) datasnap.getValue());
        }

        RecyclerView r1=findViewById(R.id.sremainlist);

        RecyclerViewGroupName r2=new RecyclerViewGroupName(a1,a2,this);
        r1.setAdapter(r2);
        r1.setLayoutManager(new LinearLayoutManager(this));
    }


}