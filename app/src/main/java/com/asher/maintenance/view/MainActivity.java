package com.asher.maintenance.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;

import com.asher.maintenance.R;
import com.asher.maintenance.adapter.FormTitlesAdapter;
import com.asher.maintenance.model.Form;
import com.asher.maintenance.utils.Networking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FormTitlesAdapter.FormListener {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private FormTitlesAdapter mAdapter;
    private List<Form> mForms;

    private static final String TAG = "view.MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_forms);
        mToolbar = findViewById(R.id.toolbar_main);

        setSupportActionBar(mToolbar);

        mForms = new ArrayList<>();
       // loadStaticForms();
        loadForms();
        //setupRecyclerView();

        Log.wtf(TAG, Networking.isNetworkConnected(this)+" connected");
    }

    private void loadForms() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference formsRef = database.getReference("forms");
        formsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mForms = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Form form = postSnapshot.getValue(Form.class);
                    if (form != null && !TextUtils.isEmpty(form.getTitle())) {
                        mForms.add(form);
                        Log.wtf("form",form.getTitle());
                    }

                }
                setupRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }

    private void loadStaticForms() {
        mForms = new ArrayList<>();
        Form form = new Form();
        form.setId(0);
        form.setTitle("Maintenance schedule");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();

        form.setAllowsDate(true);
        form.setId(1);
        form.setAllowsAuthor(true);
        form.setAllowsNotes(true);
        form.setTitle("Maintenance records");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();
        form.setId(2);
        form.setAllowsNotes(true);
        form.setAllowsAuthor(true);
        form.setAllowsDate(true);
        form.setAllowsTime(true);
        form.setTitle("Internal Facility Inspection Checklist");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();
        form.setId(3);
        form.setTitle("Customer Complaint Form");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();
        form.setId(4);
        form.setTitle("Corrective Action Report");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();
        form.setId(5);
        form.setTitle("Correction Log");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();
        form.setId(6);
        form.setTitle("Cooler Temperature Log");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();
        form.setId(7);
        form.setTitle("Cleaning Verification Log");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();
        form.setId(8);
        form.setTitle("Cleaning Schedule");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);

        form = new Form();
        form.setId(9);
        form.setTitle("Cleaning Records");
        form.setImageUrl("https://cpahispano.com/private/wp-content/uploads/2016/10/green-icon-time.png");
        mForms.add(form);
    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mAdapter  = new FormTitlesAdapter(mForms, this, this);
        mRecyclerView.setAdapter(mAdapter);
     //   mRecyclerView.setAdapter(adapter);

        /*if (results==null || results.size()==0){
            showEmptyView();
        }else{
            showRecyclerView();
        }*/
    }

    @Override
    public void onFormSelected(Form selectedForm) {
        Intent openFormActivity = new Intent(this, FormActivity.class);
        openFormActivity.putExtra(FormActivity.EXTRA_FORM, selectedForm);
        startActivity(openFormActivity);
    }
}
