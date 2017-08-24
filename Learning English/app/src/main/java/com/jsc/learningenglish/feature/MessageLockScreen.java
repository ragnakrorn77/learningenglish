package com.jsc.learningenglish.feature;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.provider.UserDictionary;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jsc.learningenglish.R;
import com.jsc.learningenglish.database.Word;
import com.jsc.learningenglish.service.HeadService;

import java.io.File;
import java.util.List;


/**
 * Creates the head layer view which is displayed directly on window manager.
 * It means that the view is above every application's view on your phone -
 * until another application does the same.
 */
public class MessageLockScreen extends View {

    private Context mContext;
    private FrameLayout mFrameLayout;
    private WindowManager mWindowManager;
    private boolean isLearning = false;
    private int countWrong = 0; // number of count answer wrong;
    public MessageLockScreen(Context context, boolean isLearning) {
        super(context);
        mContext = context;
        mFrameLayout = new FrameLayout(mContext);
        this.isLearning = isLearning;
        addToWindowManager();
    }

    private void addToWindowManager() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0,
                0,
                WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON /* | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON */,
                PixelFormat.RGBA_8888);
       // params.gravity = Gravity.LEFT;

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mFrameLayout, params);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Here is the place where you can inject whatever layout you want.

        layoutInflater.inflate(R.layout.popupmessagelockscreen, mFrameLayout);

        final ImageView imgExample = (ImageView) mFrameLayout.findViewById(R.id.imgExample);
        final EditText txtFillVoc = (EditText) mFrameLayout.findViewById(R.id.txtFillVoc);
        final TextView tvExampleFullPhrase = (TextView) mFrameLayout.findViewById(R.id.tvExampleFullPhrase);
        final ImageView closePopup = (ImageView) mFrameLayout.findViewById(R.id.closePopup);
        closePopup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.stopService(new Intent(mContext, HeadService.class));
            }
        });

        List<Word> words = Word.find(Word.class, "ACTIVE = ?", "1");
        ListWordManager listWordManager = new ListWordManager(words);
        final Word word = listWordManager.getRandomWord();
        Bitmap myBitmap = word.getBimapImage();
        if (myBitmap != null) {
            imgExample.setImageBitmap(myBitmap);
        }
        SpannableString fullPhrase = new SpannableString(word.getFullPhrase());
        fullPhrase.setSpan(new UnderlineSpan(), 0, fullPhrase.length(), 0);
        tvExampleFullPhrase.setText(fullPhrase);


        final TextView tvWord = (TextView) mFrameLayout.findViewById(R.id.tvWord);
        tvWord.setText(word.getWord());
        final TextView tvMeaning = (TextView) mFrameLayout.findViewById(R.id.tvMeaning);
        tvMeaning.setText(word.getMeaning());
        final Button btnCheck = (Button) mFrameLayout.findViewById(R.id.btnCheck);
        btnCheck.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtFillVoc.getText().toString().length() == 0) {
                    return;
                }
                if (txtFillVoc.getText().toString().equalsIgnoreCase(word.getWord())) {
                    mContext.stopService(new Intent(mContext, HeadService.class));
                } else {
                    countWrong++;
                }

                if (countWrong >= Contants.COUNT_ALLOW_WRONG) {
                    isLearning = true;
                    txtFillVoc.setVisibility(View.GONE);
                    btnCheck.setVisibility(View.GONE);
                    tvExampleFullPhrase.setVisibility(View.VISIBLE);
                    tvWord.setVisibility(View.VISIBLE);
                    closePopup.setVisibility(View.VISIBLE);
                }
            }
        });


        if (this.isLearning) {
            txtFillVoc.setVisibility(View.GONE);
            btnCheck.setVisibility(View.GONE);
            tvExampleFullPhrase.setVisibility(View.VISIBLE);
            tvWord.setVisibility(View.VISIBLE);
            closePopup.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Removes the view from window manager.
     */
    public void destroy() {
        mWindowManager.removeView(mFrameLayout);
    }
}
