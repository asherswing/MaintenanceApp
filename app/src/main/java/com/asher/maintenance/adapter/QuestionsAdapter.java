package com.asher.maintenance.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.asher.maintenance.R;
import com.asher.maintenance.model.FormItem;
import com.asher.maintenance.view.SignatureFirstStepActivity;
import com.asher.maintenance.view.SignatureSecondStepActivity;

import java.util.List;
import java.util.Map;

public class QuestionsAdapter extends
    RecyclerView.Adapter<QuestionsAdapter.FormItemsViewHolder> {

    private List<FormItem> mFormItems;
    private Activity mActivity;

    public static final String EXTRA_CORRECTION = "EXTRA_CORRECTION";
    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String EXTRA_ANSWER = "EXTRA_ANSWER";

    public QuestionsAdapter(List<FormItem> formItems, Activity activity){
            this.mFormItems = formItems;
            mActivity = activity;
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FormItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.item_question;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);
        return new FormItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FormItemsViewHolder holder, final int position) {
        holder.questionText.setText(mFormItems.get(position).getItem());
        holder.answersGroup.removeAllViews();
        if (mFormItems.get(position).getAnswers() != null){
            for (Map.Entry answer: mFormItems.get(position).getAnswers().entrySet()){
                RadioButton radioButton = new RadioButton(mActivity);
                radioButton.setText(answer.getValue()+"");
                holder.answersGroup.addView(radioButton);
            }
        }
        holder.signatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSignatureActivity = new Intent(mActivity, SignatureSecondStepActivity.class);

                String selectedAnswer = null;
                for (int i=0; i<holder.answersGroup.getChildCount(); i++){
                    if (((RadioButton)holder.answersGroup.getChildAt(i)).isChecked()){
                        selectedAnswer = ((RadioButton)holder.answersGroup.getChildAt(i)).getText().toString();
                    }
                }
                openSignatureActivity.putExtra(EXTRA_ITEM, mFormItems.get(holder.getAdapterPosition()).getItem());
                openSignatureActivity.putExtra(EXTRA_ANSWER, selectedAnswer);
                openSignatureActivity.putExtra(EXTRA_CORRECTION, holder.correctionEdit.getText().toString());
                mActivity.startActivity(openSignatureActivity);
            }
        });
        //todo add check change listener for each radio button -> to change in shared pref the answer and use it later

    }

    @Override
    public int getItemCount() {
        if (mFormItems == null){
            return 0;
        }
        return mFormItems.size();
    }

    class FormItemsViewHolder extends RecyclerView.ViewHolder{

        private TextView questionText;
        private RadioGroup answersGroup;
        private EditText correctionEdit;
        private Button signatureButton;

        FormItemsViewHolder(View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.text_question);
            answersGroup = itemView.findViewById(R.id.radiogroup_answers);
            correctionEdit = itemView.findViewById(R.id.edit_correction);
            signatureButton = itemView.findViewById(R.id.button_signature);
        }
    }

}
