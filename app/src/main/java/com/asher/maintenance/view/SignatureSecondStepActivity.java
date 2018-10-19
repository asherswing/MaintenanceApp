package com.asher.maintenance.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.asher.maintenance.R;
import com.asher.maintenance.customviews.CanvasView;

public class SignatureSecondStepActivity extends AppCompatActivity {

    private CanvasView mCanvas;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_second_step);

        mCanvas = findViewById(R.id.canvas_signature);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
              //  startActivity(new Intent(this, MainActivity.class)
                //        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signature_second_step, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }
}
