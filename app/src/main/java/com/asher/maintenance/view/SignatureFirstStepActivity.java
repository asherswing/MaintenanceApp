

package com.asher.maintenance.view;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.asher.maintenance.R;

public class SignatureFirstStepActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_first_step);

        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_home:
                startActivity(new Intent(this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSignature(View view) {
        Intent openSignatureSecondStepActivity = new Intent(this, SignatureSecondStepActivity.class);
        startActivity(openSignatureSecondStepActivity);
    }

    public void onBack(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signature_first_step, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

}
