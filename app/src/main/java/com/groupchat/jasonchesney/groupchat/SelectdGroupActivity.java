package com.groupchat.jasonchesney.groupchat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectdGroupActivity extends AppCompatActivity {

    private RecyclerView onrecycler, othmemrecycler, allmemcycler;
    private DatabaseReference gnameRef;
    private String groupname, memtype, currentUserID, timeget, tim;
    private String MEMBER_TYPE = "Admin";
    private TextView timer, msgdir, mTextViewCTD;
    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    public static final String GPREFS = "Groupprefs";
    public static final String GNAME_KEY = "groupname";
    long t;
    private OnItemClickListener listener;

    CountDownTimer cdt;
    long START_TIME_IN_MILLIS = 16000;
    long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private boolean mTimerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectd_group);

        if(Build.VERSION.SDK_INT >= 21){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }

        mTextViewCTD= (TextView) findViewById(R.id.text_view_countdown);
        mTextViewCTD.setVisibility(View.VISIBLE);
        timer = (TextView) findViewById(R.id.text_view_timer);
        timer.setVisibility(View.VISIBLE);

        msgdir = (TextView) findViewById(R.id.msgdir);

        onrecycler = (RecyclerView) findViewById(R.id.online_recycler);
        onrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        othmemrecycler = (RecyclerView) findViewById(R.id.othmem_recycler);
        othmemrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        allmemcycler = (RecyclerView) findViewById(R.id.allmem_recycler);
        allmemcycler.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));

        Bundle bundle = getIntent().getExtras();
        groupname = bundle.getString("newGroupName");
        timeget = bundle.getString("timer");
        Bundle b = getIntent().getExtras();
        tim = b.getString("time");

        if(timeget == null){
            t= Long.parseLong(tim) * 60000;
        }
        else{
            t= Long.parseLong(timeget) * 60000;
        }


        SharedPreferences prefs = getSharedPreferences(GPREFS, 0);
        prefs.edit().putString(GNAME_KEY, groupname).apply();

        mAuth = FirebaseAuth.getInstance();
        gnameRef = FirebaseDatabase.getInstance().getReference("Groups")
                .child(groupname).child("Members");
        currentUserID = mAuth.getCurrentUser().getUid();

        gnameRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("member_type")) {
                    memtype = dataSnapshot.child("member_type").getValue().toString();

                    if(!memtype.equals(MEMBER_TYPE)) {
                        startTimer();
                    }
                    else {
                        mTextViewCTD.setVisibility(View.INVISIBLE);
                        timer.setVisibility(View.INVISIBLE);
                    }
                    }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        msgdir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectdGroupActivity.this,
                        GroupChatActivity.class);
                intent.putExtra("groupName", groupname);
                startActivity(intent);
            }
        });
    }

    private void startTimer(){
        cdt = new CountDownTimer(t, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                t = millisUntilFinished;
                updatecdt();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mTextViewCTD.setVisibility(View.INVISIBLE);
                timer.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(SelectdGroupActivity.this, ConnectPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }.start();
        mTimerRunning = true;
    }

    private void updatecdt(){
        int minutes= (int) (t/1000)/60;
        int seconds= (int) (t/1000)%60;

        String timeLeftFormated = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCTD.setText(timeLeftFormated);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cdt.cancel();
        Intent intent = new Intent(SelectdGroupActivity.this, ConnectPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<MemberModel> onoptions =
                new FirebaseRecyclerOptions.Builder<MemberModel>()
                        .setQuery(gnameRef, MemberModel.class)
                        .build();

        FirebaseRecyclerAdapter<MemberModel, OnlineMemViewHolder> firebaseRecyclerONMAdapter =
                new FirebaseRecyclerAdapter<MemberModel, OnlineMemViewHolder>(onoptions) {
                    @Override
                    protected void onBindViewHolder(@NonNull OnlineMemViewHolder holder, int position, @NonNull MemberModel model) {
                        holder.recyclername.setText(model.getPname());
                        Picasso.get().load(model.getPimage()).placeholder(R.drawable.profileimg).into(holder.recyclerimage);
                    }

                    @NonNull
                    @Override
                    public OnlineMemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view = LayoutInflater.from(viewGroup.getContext())
                                .inflate(R.layout.onlinerecycler, viewGroup, false);
                        OnlineMemViewHolder onlineMemViewHolder = new OnlineMemViewHolder(view);
                        return onlineMemViewHolder;
                    }
                };
                onrecycler.setAdapter(firebaseRecyclerONMAdapter);
                othmemrecycler.setAdapter(firebaseRecyclerONMAdapter);
                allmemcycler.setAdapter(firebaseRecyclerONMAdapter);
                firebaseRecyclerONMAdapter.startListening();
    }

    public class OnlineMemViewHolder extends RecyclerView.ViewHolder{

        TextView recyclername;
        CircleImageView recyclerimage;

        public OnlineMemViewHolder(View itemview) {
            super(itemview);

            recyclername = itemView.findViewById(R.id.online_user_profile_name);
            recyclerimage = itemView.findViewById(R.id.online_users_profile_image);

//            itemview.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION && listener != null){
//                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
//                    }
//                }
//            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.group__menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.groupsett) {
            sendUsertoGroupSettings();
        }
        return true;
    }

    private void sendUsertoGroupSettings() {
        Intent updateintent = new Intent(SelectdGroupActivity.this, GroupSettingsActivity.class);
        startActivity(updateintent);
    }

    public interface OnItemClickListener{
        void onItemClick(DataSnapshot dataSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
