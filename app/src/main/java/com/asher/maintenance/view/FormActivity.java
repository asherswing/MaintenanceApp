package com.asher.maintenance.view;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Intent;
import android.provider.Settings;
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
import com.asher.maintenance.model.CompletedForm;
import com.asher.maintenance.model.CompletedItem;
import com.asher.maintenance.model.Form;
import com.asher.maintenance.model.FormItem;
import com.asher.maintenance.model.FormItemFirebase;
import com.asher.maintenance.realm.RealmController;
import com.asher.maintenance.utils.Networking;
import com.asher.maintenance.utils.NotificationUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, QuestionsAdapter.FormCompletionListener {

    public static final String EXTRA_FORM = "EXTRA_FORM";
    public static final String EXTRA_COMPLETED_FORM = "EXTRA_COMPLETED_FORM";

    private Form mSelectedForm;
    private List<FormItem> mFormItems = new ArrayList<>();
    private HashMap<String, String> mCompletedForm = new HashMap<>();

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private EditText mAuthorEditText, mDateEditText, mNotesEditText;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        NotificationUtils.showNotificationFormInProgress(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("notify-one-progress")
                .child("msg")
                .setValue("new notification sent "+System.currentTimeMillis());

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

            if (Networking.isNetworkConnected(this)){
                loadSelectedForm();
            }else{
                loadFormFromDb();
            }


            if (!mSelectedForm.isAllowsAuthor()){
                mAuthorEditText.setVisibility(View.GONE);
            }else {
                mAuthorEditText.setVisibility(View.VISIBLE);
            }
            if (!mSelectedForm.isAllowsDate()){
                mDateEditText.setVisibility(View.GONE);
            }else{
                mDateEditText.setVisibility(View.VISIBLE);
            }
            if (!mSelectedForm.isAllowsNotes()){
                mNotesEditText.setVisibility(View.GONE);
            }else {
                mNotesEditText.setVisibility(View.VISIBLE);
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

    private void loadFormFromDb() {
        mFormItems = new ArrayList<>();
        RealmResults<FormItem> formItems = RealmController.with(this)
                .getFormItemsById(mSelectedForm.getId());
    }

    private void loadSelectedForm() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference formsRef = database.getReference("forms")
                .child("formitems"+mSelectedForm.getId());
        formsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RealmController.with(FormActivity.this).deleteFormItemsById(mSelectedForm.getId());
                mFormItems = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    FormItemFirebase formItemFirebase = postSnapshot.getValue(FormItemFirebase.class);
                    FormItem formItem = new FormItem();
                    formItem.setItem(formItemFirebase.getItem());
                    formItem.setAllowsSignature(formItemFirebase.isAllowsSignature());
                    formItem.setAllowsNotes(formItemFirebase.isAllowsNotes());
                    formItem.setFormId(formItemFirebase.getFormId());
                    List<String> answers = new ArrayList<>();
                    String answersText="";
                    if (formItemFirebase.getAnswers() != null) {
                        for (Map.Entry<String, String> entry : formItemFirebase.getAnswers().entrySet()){
                            answers.add(entry.getValue());
                            if (answersText.length() != 0) {
                                answersText += ",";
                            }
                            answersText += entry.getValue();
                        }
                        formItem.setAnswers(answersText);
                    }

                    RealmController.with(FormActivity.this)
                            .insertFormItem(formItem);

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
                loadFormFromDb();
            }
        });

    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        QuestionsAdapter mAdapter  = new QuestionsAdapter(mFormItems, this, this);
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

        CompletedForm completedForm = new CompletedForm();
        List<CompletedItem> completedItems = new ArrayList<>();
        for (Map.Entry<String, String> entry : mCompletedForm.entrySet()) {
            completedItems.add(new CompletedItem(entry.getKey(),entry.getValue()));
        }
        if (mSelectedForm.isAllowsNotes()){
            completedForm.setNotes(mNotesEditText.getText().toString());
        }
        if (mSelectedForm.isAllowsDate()) {
            completedForm.setDate(mDateEditText.getText().toString());
        }
        if (mSelectedForm.isAllowsAuthor()){
            completedForm.setAuthor(mAuthorEditText.getText().toString());
        }
        String deviceId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        completedForm.setDeviceId(deviceId);
        completedForm.setFormId(mSelectedForm.getId());
        completedForm.setCompletedItems(completedItems);

        Intent openSignatureFirstStepActivity = new Intent(this, SignatureFirstStepActivity.class);
        openSignatureFirstStepActivity.putExtra(EXTRA_COMPLETED_FORM, completedForm);
        openSignatureFirstStepActivity.putExtra("title",mSelectedForm.getTitle());
        startActivity(openSignatureFirstStepActivity);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mDateEditText.setText(year+"-"+(month+1)+"-"+dayOfMonth);
    }

    @Override
    public void onItemEdited(String item, String answer) {
        mCompletedForm.put(item, answer);
    }
}
