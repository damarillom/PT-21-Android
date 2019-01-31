package com.dam.iam26509397.myapplication;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity  {

    ImageButton imgButton1, imgButton2, imgButton3, imgButton4;
    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imgButton1 = (ImageButton) findViewById(R.id.imageButton2);
        imgButton1.setOnClickListener(imgButtonHandler);

        imgButton2 = (ImageButton) findViewById(R.id.imageButton3);
        imgButton2.setOnClickListener(imgButtonHandler2);

        imgButton3 = (ImageButton) findViewById(R.id.imageButton4);
        imgButton3.setOnClickListener(imgButtonHandler3);

        imgButton4 = (ImageButton) findViewById(R.id.imageButton5);
        imgButton4.setOnClickListener(imgButtonHandler4);
        //setImageResource(R.drawable.ic_action_image_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener imgButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //imgButton1.setImageResource(R.drawable.ic_action_image_button);
            //setBackgroundResource(R.drawable.ic_action_image_button);
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            /**soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int sampleId,
                                           int status) {
                    loaded = true;
                }
            });*/
            //soundID = soundPool.load(this, R.raw.sound1, 1);
        }
    };

    View.OnClickListener imgButtonHandler2 = new View.OnClickListener() {

        public void onClick(View v) {

        }
    };
    View.OnClickListener imgButtonHandler3 = new View.OnClickListener() {

        public void onClick(View v) {

        }
    };

    View.OnClickListener imgButtonHandler4 = new View.OnClickListener() {

        public void onClick(View v) {

        }
    };


}
