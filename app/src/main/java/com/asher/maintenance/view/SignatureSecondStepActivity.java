package com.asher.maintenance.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.asher.maintenance.R;
import com.asher.maintenance.customviews.CanvasView;
import com.asher.maintenance.model.CompletedForm;
import com.asher.maintenance.model.CompletedItem;
import com.asher.maintenance.utils.NotificationUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class SignatureSecondStepActivity extends AppCompatActivity {

    private CanvasView mCanvas;
    private Toolbar mToolbar;
    private CompletedForm mForm;

    private DatabaseReference mDatabase;
    private String mFormTitle;

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
        if (getIntent().hasExtra("title")){
            mFormTitle = getIntent().getStringExtra("title");
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
                NotificationUtils.showNotificationFormCompleted(this, mFormTitle);
                sendFormToFirebase();
                generatePdf();
                Intent submitFormIntent = new Intent(this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(submitFormIntent);

                //finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generatePdf() {
        /**
         * Creating Document
         */
        Document document = new Document();
// Location to save
        try {
            File file = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    ,mForm.getDate()+"_"+mForm.getFormId());
            if (!file.exists()) {
              //  try {
                    file.createNewFile();
              /*  } catch (IOException e) {
                   * e.printStackTrace();
                }*/
            }
            PdfWriter.getInstance(document,
                    new FileOutputStream(file));
            document.open();
            // Document Settings
            document.setPageSize(PageSize.A4);
            document.addCreationDate();

            /***
             * Variables for further use....
             */
            BaseColor mColorAccent = new BaseColor(0, 153, 204, 255);
            float mHeadingFontSize = 20.0f;
            float mValueFontSize = 26.0f;
/**
 * How to USE FONT....
 */
            //  BaseFont urName = BaseFont.createFont("assets/fonts/brandon_medium.otf", "UTF-8", BaseFont.EMBEDDED);
            // LINE SEPARATOR
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 50));

            // Title Order Details...
// Adding Title....
            Font mOrderDetailsTitleFont = new Font();
            mOrderDetailsTitleFont.setSize(36.0f);
            mOrderDetailsTitleFont.setStyle(Font.NORMAL);
            mOrderDetailsTitleFont.setColor(BaseColor.BLACK);

            if (!TextUtils.isEmpty(mForm.getAuthor())){
                document.addAuthor(mForm.getAuthor());
                document.addCreator("Maintenance App");

                Chunk mOrderDetailsTitleChunk =
                        new Chunk("Author: "+mForm.getAuthor(), mOrderDetailsTitleFont);
                Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
              //  mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
                document.add(mOrderDetailsTitleParagraph);
            }
            List<CompletedItem> items = mForm.getCompletedItems();
            if (items!=null){
                for (CompletedItem item: items){
                    Chunk mOrderDetailsTitleChunk =
                            new Chunk(item.getItem()+": "+item.getAnswer(),
                                    mOrderDetailsTitleFont);
                    Paragraph mOrderDetailsTitleParagraph = new Paragraph(mOrderDetailsTitleChunk);
                    //  mOrderDetailsTitleParagraph.setAlignment(Element.ALIGN_CENTER);
                    document.add(mOrderDetailsTitleParagraph);
                }
            }

            document.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
// Open to write

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
