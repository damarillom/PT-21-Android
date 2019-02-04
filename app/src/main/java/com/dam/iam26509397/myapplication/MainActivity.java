package com.dam.iam26509397.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageButton imgButton1, imgButton2, imgButton3, imgButton4;
    //Button startButton, stopButton;
    private SoundPool soundPool;
    private int soundID1, soundID2, soundID3, soundID4;
    int loaded = 0;

    MediaRecorder recorder;
    File audiofile = null;
    private static final String TAG = "SoundRecordingActivity";
    private View startButton;
    private View stopButton;
    private static final int MY_PERMISSIONS_REQUESTS = 10;
    File sampleDir = Environment.getExternalStorageDirectory();
    TextView txtInfo;
    List<String> llista=new ArrayList<String>();
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

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded++;
            }
        });
        soundID1 = soundPool.load(this, R.raw.sound1, 1);
        soundID2 = soundPool.load(this, R.raw.sound2, 1);
        soundID3 = soundPool.load(this, R.raw.sound3, 1);
        soundID4 = soundPool.load(this, R.raw.sound4, 1);

        startButton = findViewById(R.id.button);
        stopButton = findViewById(R.id.button2);

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
        if (id == R.id.action_call) {
            try {
                Intent intent = new Intent(this, CallActivity.class);
                startActivity(intent);
            } catch(Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener imgButtonHandler = new View.OnClickListener() {

        public void onClick(View v) {
            //imgButton1.setImageResource(R.drawable.ic_action_image_button);
            //setBackgroundResource(R.drawable.ic_action_image_button);
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            float actualVolume = (float) audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            if (loaded == 4) {
                soundPool.play(soundID1, volume, volume, 0, 0, 1);
            }
        }
    };

    View.OnClickListener imgButtonHandler2 = new View.OnClickListener() {

        public void onClick(View v) {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            float actualVolume = (float) audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            if (loaded == 4) {
                soundPool.play(soundID2, volume, volume, 0, 0, 1);
            }
        }
    };
    View.OnClickListener imgButtonHandler3 = new View.OnClickListener() {

        public void onClick(View v) {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            float actualVolume = (float) audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            if (loaded == 4) {
                soundPool.play(soundID3, volume, volume, 0, 0, 1);
            }
        }
    };

    View.OnClickListener imgButtonHandler4 = new View.OnClickListener() {

        public void onClick(View v) {
            AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            float actualVolume = (float) audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            float maxVolume = (float) audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            float volume = actualVolume / maxVolume;
            if (loaded == 4) {
                soundPool.play(soundID4, volume, volume, 0, 0, 1);
            }
        }
    };

    public void startRecording(View view) throws IOException {
        startButton.setEnabled(false);
        stopButton.setEnabled(true);

        String estat=Environment.getExternalStorageState();
        // comprova si hi ha SD i si puc escriure en ella
        if (estat.equals(Environment.MEDIA_MOUNTED)) {
            //txtInfo.setText("");

            Log.d(TAG, "media mounted" + ", " + String.valueOf(sampleDir));

            int permCheck1= ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
            int permCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            // Log.d(TAG, "permCheck és:" + ", " + String.valueOf(permCheck)+","+String.valueOf(permCheck1));

            if (!(permCheck== PackageManager.PERMISSION_GRANTED) |
                    !(permCheck1== PackageManager.PERMISSION_GRANTED)) {

                //ara cal demanar permissos...
                if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) |
                        (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)))) {


                    // asincrona:no bloquejar el thread esperant la seva resposta
                    // Bona pràctica, try again to request the permission.
                    // explicar a l usuari per què calen aquests permisos
                    Toast.makeText(this, "Per seguretat, està deshabilitada la SD i el microfon, habiliti'ls ", Toast.LENGTH_LONG).show();
                    //menu dialeg
                    //txtInfo.setText("Per seguretat, està deshabilitada la SD i el microfon, habiliti'ls els dos");

                    ActivityCompat.requestPermissions
                            (this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUESTS);

                } else {
                    // request the permission.
                    // CALLBACK_NUMBER is a integer constants
                    Toast.makeText(this, "demana permis, no rationale ", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUESTS);
                    // The callback method gets the result of the request.
                    Log.d(TAG, "startRecording: no rationale");
                }
                //txtInfo.setText("");

            }

            else {
                Log.d(TAG, "entra, té permissos:" + ", " + String.valueOf(permCheck));
                try {
                    audiofile = File.createTempFile("sound", ".3gp", sampleDir);
                    recorder = new MediaRecorder();
                    //aquí, peta si no té permissos...

                    //NO TÉ PERMISOS PER defecte,
                    //hem de demanar .record_audio en runtime, com write_external_storage
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    recorder.setOutputFile(audiofile.getAbsolutePath());

                    Log.d(TAG, "startRecording: "+audiofile.getAbsolutePath());
                    recorder.prepare();
                    recorder.start();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Log.d(TAG, "startRecording1: "+ e.getMessage() + e.getCause());

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "sd card error: "+String.valueOf(sampleDir)+ e.getMessage() + e.getCause());

                } catch (Exception e) {
                    Log.d(TAG, "startRecording3: " + e.getMessage() + e.getCause());
                    Toast.makeText(this, "Exception: missatge: " + e.getMessage() + ", causa: " + e.getCause() + ", " +
                            String.valueOf(audiofile.getAbsolutePath()), Toast.LENGTH_SHORT).show();
                }

            }
        }

        /**startButton.setEnabled(false);
        stopButton.setEnabled(true);

        File sampleDir = Environment.getExternalStorageDirectory();
        try {
            audiofile = File.createTempFile("sound", ".3gp", sampleDir);
        } catch (IOException e) {
            Log.e(TAG, "sdcard access error");
            return;
        }
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        recorder.prepare();
        recorder.start();*/

    }

    public void stopRecording(View view) {
        /**startButton.setEnabled(true);
        stopButton.setEnabled(false);
        recorder.stop();
        recorder.release();
        addRecordingToMediaLibrary();*/
        try {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            recorder.stop();
            recorder.release();
            llista.add(audiofile.getAbsolutePath());
            addRecordingToMediaLibrary();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.d(TAG, "stopRecording: " + e.getMessage() + e.getCause());
            Toast.makeText(this, "IllegalStateException"+e.getMessage() + e.getCause(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Exception"+e.getMessage() + e.getCause(), Toast.LENGTH_SHORT).show();
        }
        try {
            showRecordings(view);
        } catch (Exception e) {

        }
    }

    protected void addRecordingToMediaLibrary() {
        /**ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
        ContentResolver contentResolver = getContentResolver();

        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(base, values);

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
        Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();*/
        try {
            ContentValues values = new ContentValues(4);
            long current = System.currentTimeMillis();
            values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
            values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
            values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
            values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
            ContentResolver contentResolver = getContentResolver();

            Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Uri newUri = contentResolver.insert(base, values);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
            //Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
            Log.d(TAG, "addRecordingToMediaLibrary: "+newUri);


        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "addRecordingToMediaLibrary: "+e.getCause()+", " + e.getMessage()+", ");
            Toast.makeText(this, e.getMessage()+ e.getCause(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUESTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1]==PackageManager.PERMISSION_GRANTED ) {
                    // permission was granted, do your work....
                    Log.d("test" ,"tot concedit");

                    try {
                        audiofile = File.createTempFile("sound", ".3gp", sampleDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    recorder = new MediaRecorder();
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    recorder.setOutputFile(audiofile.getAbsolutePath());
                    try {
                        recorder.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    recorder.start();

                } else {
                    // permission denied
                    // Disable the functionality that depends on this permission.

                    //deixarem que torni a intentar-ho
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                }
                return;
            }
        }
    }

    public void showRecordings(View view) {
        RecyclerView recyclerView;

        try {
            recyclerView = (RecyclerView) findViewById(R.id.my_recycler);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            //recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
            if (! (llista== null)) {
                //Toast.makeText(this, "size:" + String.valueOf(llista.size()), Toast.LENGTH_LONG).show();
                if (llista.size()==0) Toast.makeText(this, "No hi ha cap gravació a mostrar", Toast.LENGTH_LONG).show();
                RecyclerView.Adapter mAdapter = new CustomAdapter(llista);
                recyclerView.setAdapter(mAdapter);
                //Log.d("test", "recy done 2");
            }

            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            //addRecordingToMediaLibrary();

        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.d(TAG, "stopRecording: " + e.getMessage() + e.getCause());
            Toast.makeText(this, "IllegalStateException"+e.getMessage() + e.getCause(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(this, "Exception"+e.getMessage() + e.getCause(), Toast.LENGTH_SHORT).show();
        }

    }

}
