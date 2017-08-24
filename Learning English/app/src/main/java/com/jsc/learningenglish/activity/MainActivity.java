package com.jsc.learningenglish.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jsc.learningenglish.R;
import com.jsc.learningenglish.adapter.ListWordAdapter;
import com.jsc.learningenglish.broadcast.AlarmReceiver;
import com.jsc.learningenglish.database.Word;
import com.jsc.learningenglish.feature.Contants;
import com.jsc.learningenglish.feature.ListWordManager;
import com.jsc.learningenglish.service.StartBroadCastScreenOnOff;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListWordAdapter listWordAdapter;
    ListView lvListWord;
    int positionSelectListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File f = new File(Environment.getExternalStorageDirectory() + "/" + Contants.FOLDER_IMAGE_NAME);
        if(!f.isDirectory()) {
            f.mkdir();
        }
        if (!StartBroadCastScreenOnOff.isRunning()) {
            startService(new Intent(this, StartBroadCastScreenOnOff.class));
        }
        Button btAddNewWord = (Button) findViewById(R.id.btAddNewWord);
        btAddNewWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNewWordActivity.class));
            }
        });

        lvListWord = (ListView) findViewById(R.id.lvListWord);
        List<Word> words = Word.listAll(Word.class);
        ListWordManager listWordManager = new ListWordManager(words);
        this.listWordAdapter = new ListWordAdapter(this, listWordManager);
        lvListWord.setAdapter(listWordAdapter);

        boolean alarmUp = (PendingIntent.getBroadcast(this, 0,
                new Intent("com.jsc.learningenglish.broadcast.AlarmReceiver"),
                PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUp) {
            Intent launchIntent = new Intent(this, AlarmReceiver.class);
            PendingIntent mAlarmIntent = PendingIntent.getBroadcast(this, 0, launchIntent, 0);
            AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            long interval = Contants.TIME_OPEN_DIALOG;
            manager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + interval, interval,
                    mAlarmIntent);
        }
        lvListWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionSelectListView = position;
                System.out.println("zo");
            }
        });

        lvListWord.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                positionSelectListView = position;
                System.out.println("zo");
                return false;
            }
        });
        registerForContextMenu(lvListWord);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.word, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        switch (item.getItemId()) {
            case R.id.remove_item:
                Word word = this.listWordAdapter.getItem(positionSelectListView);
                word.delete();
                this.refreshListWord();
                return true;
        }
        return false;
    }

    private void refreshListWord() {
        if (this.listWordAdapter != null) {
            List<Word> words = Word.listAll(Word.class);
            ListWordManager listWordManager = new ListWordManager(words);
            this.listWordAdapter.updateListWordManager(listWordManager);
            this.listWordAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.refreshListWord();
    }
}
