package com.jsc.learningenglish.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.filepicker.controller.DialogSelectionListener;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.jsc.learningenglish.R;
import com.jsc.learningenglish.database.Word;
import com.jsc.learningenglish.feature.Contants;
import com.jsc.learningenglish.feature.TinyDB;
import com.jsc.learningenglish.feature.utils;

import java.io.File;
import java.io.IOException;

public class AddNewWordActivity extends AppCompatActivity {
    private int ACTIVITY_CHOOSE_FILE = 1000;
    private String pathImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_word);
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.extensions = null;

        final EditText edtWord = (EditText) findViewById(R.id.edtWord);
        final EditText edtFullPhrase = (EditText) findViewById(R.id.edtFullPhrase);
        final EditText edtMeaning = (EditText) findViewById(R.id.edtMeaning);
        Button btnChooseFile = (Button) findViewById(R.id.btnChooseFile);
        final TextView tvNameFile = (TextView) findViewById(R.id.tvNameFile);
        final ImageView img = (ImageView) findViewById(R.id.img);

        final FilePickerDialog dialog = new FilePickerDialog(AddNewWordActivity.this,properties);
        dialog.setTitle("Select a File");

        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public void onSelectedFilePaths(String[] files) {
                pathImg = files[0];
                tvNameFile.setText(new File(pathImg).getName());
                File filePath = new File(pathImg);
                if(filePath.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
                    img.setImageBitmap(myBitmap);
                }
            }
        });

        btnChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        Button btnAddWord = (Button) findViewById(R.id.btnAddWord);
        btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathImg == null) {
                    Toast.makeText(AddNewWordActivity.this, "You haven't choose file!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (edtWord.getText().length() == 0) {
                    Toast.makeText(AddNewWordActivity.this, "You haven't fill word!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (edtFullPhrase.getText().length() == 0) {
                    Toast.makeText(AddNewWordActivity.this, "You haven't fill full phrase!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (edtMeaning.getText().length() == 0) {
                    Toast.makeText(AddNewWordActivity.this, "You haven't fill Meaning of word!", Toast.LENGTH_LONG).show();
                    return;
                }
                File src = new File(pathImg);
                File dest = new File(Environment.getExternalStorageDirectory() + "/" + Contants.FOLDER_IMAGE_NAME + "/" + src.getName());
                try {
                    utils.copy(src, dest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Word word = new Word(edtWord.getText().toString(), edtFullPhrase.getText().toString(), dest.getAbsolutePath(), edtMeaning.getText().toString(),  Contants.ACTIVE_WORD);
                word.save();
                finish();
            }
        });
    }
}
