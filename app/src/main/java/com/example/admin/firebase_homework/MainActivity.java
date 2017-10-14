package com.example.admin.firebase_homework;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private DatabaseReference journalCloudEndPoint;
    private DatabaseReference tagCloudEndPoint;
    ArrayList<JournalEntry> mJournalEntries;
    FirebaseRecyclerAdapter<JournalEntry, JournalViewHolder> mJournalFirebaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        mDatabase =  FirebaseDatabase.getInstance().getReference();
        journalCloudEndPoint = mDatabase.child("journalentris");
        tagCloudEndPoint = mDatabase.child("tags");
        mJournalEntries = new ArrayList<>();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean("FIRST_RUN", true)) {
            
            editor.putBoolean("FIRST_RUN", false).commit();
        }
        addInitialDataToFirebase();
        //read data from firebase
        Query myQuery = mDatabase.child("journalentris");
        myQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    JournalEntry journalEntry = snapshot.getValue(JournalEntry.class);
                    mJournalEntries.add(journalEntry);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        mJournalFirebaseAdapter = new FirebaseRecyclerAdapter<JournalEntry, JournalViewHolder>(
                JournalEntry.class,
                R.layout.item,
                JournalViewHolder.class,
                journalCloudEndPoint) {
            @Override
            protected void populateViewHolder
                    (JournalViewHolder holder, final JournalEntry journalEntry, int position) {
                holder.title.setText(journalEntry.getTitle());
                holder.journalDate.setText(getDueDate(journalEntry.getDateModified()));
                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(journalEntry.getJournalId())) {
                            journalCloudEndPoint.child(journalEntry.getJournalId()).
                                    removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if (mJournalFirebaseAdapter.getItemCount() < 1){
                                        // showEmptyText();
                                    }
                                }
                            });
                        }
                    }
                });
                String firstLetter = journalEntry.getTitle().substring(0, 1);
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color = generator.getRandomColor();
                TextDrawable drawable = TextDrawable.builder()
                        .buildRound(firstLetter, color);
                holder.journalIcon.setImageDrawable(drawable);
            }
        };
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mJournalFirebaseAdapter);

    }
    //write data firebase
    private void addInitialDataToFirebase() {
        ArrayList<JournalEntry> sampleJournalEntries = SampleData.getSampleJournalEntries();
        for (JournalEntry journalEntry : sampleJournalEntries) {
            String key = journalCloudEndPoint.push().getKey();
            journalEntry.setJournalId(key);
            journalCloudEndPoint.child(key).setValue(journalEntry);
        }
    }
    public String getDueDate(long miliSecond){
        Date date = new Date(miliSecond);
        DateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");
        return formatter.format(date);
    }
}
class JournalViewHolder extends RecyclerView.ViewHolder{
    ImageView journalIcon;
    TextView title, journalDate;
    ImageButton delete;
    public JournalViewHolder(View itemView) {
        super(itemView);
        journalIcon = (ImageView)itemView.findViewById(R.id.journalIcon);
        title = (TextView)itemView.findViewById(R.id.title);
        journalDate = (TextView)itemView.findViewById(R.id.journalDate);
        delete = (ImageButton)itemView.findViewById(R.id.delete);

    }
}

