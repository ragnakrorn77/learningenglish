package com.jsc.learningenglish.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsc.learningenglish.R;
import com.jsc.learningenglish.database.Word;
import com.jsc.learningenglish.feature.ListWordManager;

/**
 * Created by ADMIN on 8/18/2017.
 */
public class ListWordAdapter extends BaseAdapter {
    private Context context;
    private ListWordManager listWordManager;

    public ListWordAdapter(Context context, ListWordManager listWordManager) {
        this.context = context;
        this.listWordManager = listWordManager;
    }

    @Override
    public int getCount() {
        return this.listWordManager.getSize();
    }

    @Override
    public Word getItem(int position) {
        return this.listWordManager.getWord(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.word_item, null);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.imageView);
            holder.tvFullPhrase = (TextView) convertView.findViewById(R.id.tvFullPhrase);
            holder.tvWord = (TextView) convertView.findViewById(R.id.tvWord);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.chkActive);
            holder.tvMeaning = (TextView) convertView.findViewById(R.id.tvMeaning);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Word word = (Word) getItem(position);

        holder.tvFullPhrase.setText(word.getFullPhrase());
        holder.tvWord.setText(word.getWord());
        Bitmap myBitmap = word.getBimapImage();
        if (myBitmap != null) {
            holder.img.setImageBitmap(myBitmap);
        }
        holder.checkBox.setChecked(word.isActive());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                word.setActive(isChecked);
                word.save();
            }
        });
        holder.tvMeaning.setText(word.getMeaning());
        return convertView;
    }

    public void updateListWordManager(ListWordManager listWordManager) {
        this.listWordManager = listWordManager;
    }

    class ViewHolder {
        ImageView img;
        TextView tvFullPhrase;
        TextView tvWord;
        TextView tvMeaning;
        CheckBox checkBox;
    }
}
