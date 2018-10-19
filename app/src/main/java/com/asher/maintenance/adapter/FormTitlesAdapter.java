package com.asher.maintenance.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asher.maintenance.R;
import com.asher.maintenance.model.Form;
import com.bumptech.glide.Glide;

import java.util.List;

public class FormTitlesAdapter extends
    RecyclerView.Adapter<FormTitlesAdapter.FormTitlesViewHolder> {

    private List<Form> mForms;
    private Activity mActivity;
    private FormListener mFormListener;

    public interface FormListener{
        void onFormSelected(Form selectedForm);
    }

    public FormTitlesAdapter(List<Form> forms, Activity activity, FormListener listener){
            this.mForms = forms;
            mActivity = activity;
            mFormListener = listener;
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FormTitlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutId = R.layout.item_form_title;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutId, parent, false);
        return new FormTitlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FormTitlesViewHolder holder, final int position) {
        holder.nameText.setText(mForms.get(position).getTitle());
        Glide.with(mActivity).load(mForms.get(position).getImageUrl()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFormListener.onFormSelected(mForms.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mForms == null){
            return 0;
        }
        return mForms.size();
    }

    class FormTitlesViewHolder extends RecyclerView.ViewHolder{

        private TextView nameText;
        private ImageView image;

        FormTitlesViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_form_title);
            image = itemView.findViewById(R.id.image_form);
        }
    }

}
