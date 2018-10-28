package com.asher.maintenance.view;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.asher.maintenance.R;
import com.asher.maintenance.customviews.CanvasView;
import com.asher.maintenance.model.CompletedForm;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignatureSecondStepActivity extends AppCompatActivity {

    private CanvasView mCanvas;
    private Toolbar mToolbar;
    private CompletedForm mForm;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_second_step);

        mCanvas = findViewById(R.id.canvas_signature);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (getIntent().hasExtra(FormActivity.EXTRA_COMPLETED_FORM)){

            mForm = getIntent().getParcelableExtra(FormActivity.EXTRA_COMPLETED_FORM);
        }
    }

    public void onClearSignature(View view) {
        mCanvas.clearCanvas();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_done:
                sendFormToFirebase();
                
                Intent submitFormIntent = new Intent(this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(submitFormIntent);

                //finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendFormToFirebase() {
     //   String key = mDatabase.child("posts").push().getKey();
        //Post post = new Post(userId, username, title, body);
       // Map<String, Object> postValues = post.toMap();

        //Map<String, Object> childUpdates = new HashMap<>();
       // childUpdates.put("/posts/" + key, postValues);
     //   childUpdates.put("/user-posts/" + userId + "/" + key, postValues);



        mDatabase.child("completed-forms").child("form-"+mForm.getFormId()+"-device-"+mForm.getDeviceId()).setValue(mForm);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signature_second_step, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }
}
