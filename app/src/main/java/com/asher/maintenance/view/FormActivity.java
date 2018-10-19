package com.asher.maintenance.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.asher.maintenance.R;
import com.asher.maintenance.adapter.QuestionsAdapter;
import com.asher.maintenance.model.Form;
import com.asher.maintenance.model.FormItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_FORM = "EXTRA_FORM";

    private Form mSelectedForm;
    private List<FormItem> mFormItems = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private EditText mAuthorEditText, mDateEditText, mNotesEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        mRecyclerView = findViewById(R.id.recyclerview_questions);
        mToolbar = findViewById(R.id.toolbar_form);
        mAuthorEditText = findViewById(R.id.edit_author);
        mDateEditText = findViewById(R.id.edit_date);
        mNotesEditText = findViewById(R.id.edit_notes);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSelectedForm = getIntent().getParcelableExtra(EXTRA_FORM);
        if (mSelectedForm != null ){
            Log.wtf("formselected","id "+mSelectedForm.getId());
            loadSelectedForm();
            if (!mSelectedForm.isAllowsAuthor()){
                mAuthorEditText.setVisibility(View.GONE);
            }
            if (!mSelectedForm.isAllowsDate()){
                mDateEditText.setVisibility(View.GONE);
            }
            if (!mSelectedForm.isAllowsNotes()){
                mNotesEditText.setVisibility(View.GONE);
            }
        }

        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormActivity.this,
                        FormActivity.this,
                        mYear,
                        mMonth,
                        mDay);
                datePickerDialog.show();
            }
        });



        //loadStaticForm();
        //setupRecyclerView();

    }

    private void loadSelectedForm() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference formsRef = database.getReference("forms").child("formitems"+mSelectedForm.getId());
        formsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mFormItems = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    FormItem formItem = postSnapshot.getValue(FormItem.class);
                    if (formItem != null && !TextUtils.isEmpty(formItem.getItem())) {
                        mFormItems.add(formItem);
                        Log.wtf("form",formItem.getItem());
                    }

                }
                setupRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("selectedformfirebase", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        QuestionsAdapter mAdapter  = new QuestionsAdapter(mFormItems, this);
        mRecyclerView.setAdapter(mAdapter);
        //   mRecyclerView.setAdapter(adapter);

        /*if (results==null || results.size()==0){
            showEmptyView();
        }else{
            showRecyclerView();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBack(View view) {
        finish();
    }

    public void onNext(View view) {
        Intent openSignatureFirstStepActivity = new Intent(this, SignatureFirstStepActivity.class);
        startActivity(openSignatureFirstStepActivity);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        mDateEditText.setText(year+"-"+(month+1)+"-"+dayOfMonth);
    }
}