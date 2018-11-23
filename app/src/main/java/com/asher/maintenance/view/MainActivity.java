package com.asher.maintenance.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.asher.maintenance.R;
import com.asher.maintenance.adapter.FormTitlesAdapter;
import com.asher.maintenance.model.Form;
import com.asher.maintenance.model.FormItem;
import com.asher.maintenance.model.FormItemFirebase;
import com.asher.maintenance.realm.RealmController;
import com.asher.maintenance.utils.Networking;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements FormTitlesAdapter.FormListener {

    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    private FormTitlesAdapter mAdapter;
    private List<Form> mForms;

    private static final String TAG = "view.MainActivity";

    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("notified");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = findViewById(R.id.recyclerview_forms);
        mToolbar = findViewById(R.id.toolbar_main);

        setSupportActionBar(mToolbar);

        mForms = new ArrayList<>();
       // loadStaticForms();
        if (Networking.isNetworkConnected(this)){
            loadForms();
        }else{
            loadFormsFromDb();
        }

        //setupRecyclerView();

        //Log.wtf(TAG, Networking.isNetworkConnected(this)+" connected");

    }

    private void loadFormsFromDb() {
        mForms = new ArrayList<>();
        //RealmController.with(MainActivity.this).deleteForms();
        RealmResults<Form> forms = RealmController.with(this)
                .getForms();

        mForms.addAll(forms);
        setupRecyclerView();
    }

    private void loadForms() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference formsRef = database.getReference("forms-general");
        formsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mForms = new ArrayList<>();
                RealmController.with(MainActivity.this).deleteForms();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Form form = postSnapshot.getValue(Form.class);

                    if (form != null && !TextUtils.isEmpty(form.getTitle())) {
                        loadSelectedForm(form.getId());
                        mForms.add(form);
                        Log.wtf("form",form.getTitle());
                        RealmController.with(MainActivity.this)
                                .insertForm(form);
                    }

                }
                setupRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
                loadFormsFromDb();
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

    private void loadSelectedForm(final int id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference formsRef = database.getReference("forms").child("formitems"+id);
        formsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RealmController.with(MainActivity.this).deleteFormItemsById(id);
              //  mFormItems = new ArrayList<>();
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

                    RealmController.with(MainActivity.this)
                            .insertFormItem(formItem);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("selectedformfirebase", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.action_login:
                showLoginDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showLoginDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.dialog_login, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);
        final EditText editText = deleteDialogView.findViewById(R.id.edit_pass);

        deleteDialogView.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic

               // FirebaseMessaging.getInstance().unsubscribeFromTopic("notified");
                if (!TextUtils.isEmpty(editText.getText().toString()) &&
                        ((editText.getText().toString()).equals("administration231118"))){
                    FirebaseMessaging.getInstance().unsubscribeFromTopic("notified");

                    mDatabase.child("notify-incomplete")
                            .child("msg")
                            .setValue("new notification sent "+System.currentTimeMillis());
                    deleteDialog.dismiss();
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("admin",true);
                    editor.apply();
                    Toast.makeText(MainActivity.this,
                            "Success. You will get notified when a form is in progress or completed.",
                            Toast.LENGTH_SHORT).show();

                    FirebaseMessaging.getInstance().subscribeToTopic("notified-one");


                }else{
                    Toast.makeText(MainActivity.this,
                            "Incorrect administration password.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        deleteDialogView.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog.dismiss();
            }
        });
        deleteDialog.show();
    }

}
