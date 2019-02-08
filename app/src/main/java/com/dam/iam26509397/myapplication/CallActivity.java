package com.dam.iam26509397.myapplication;

import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.net.Uri;
import android.widget.Toast;

public class CallActivity extends AppCompatActivity implements View.OnClickListener{


    Button button;
    Button llamadas;
    TextView textView;
    private static final int MY_PERMISSIONS_REQUESTS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        button = (Button) findViewById(R.id.button);
        llamadas = (Button) findViewById(R.id.llamadas);
        textView = (TextView) findViewById(R.id.texto);


        button.setOnClickListener(this);
        llamadas.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                ObtenerDatos();


                break;
            case R.id.llamadas:

                ObtenerDatosLlamadas();


                break;
            default:
                break;
        }
    }



    public void ObtenerDatosLlamadas() {
        String estat= Environment.getExternalStorageState();
        // comprova si hi ha SD i si puc escriure en ella
        if (estat.equals(Environment.MEDIA_MOUNTED)) {
            int permCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            int permCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG);
            if (!(permCheck1== PackageManager.PERMISSION_GRANTED)
                    | !(permCheck== PackageManager.PERMISSION_GRANTED)) {
                if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE))
                        | (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALL_LOG))) {


                    // asincrona:no bloquejar el thread esperant la seva resposta
                    // Bona pràctica, try again to request the permission.
                    // explicar a l usuari per què calen aquests permisos
                    Toast.makeText(this, "Per seguretat, està deshabilitada la SD i el microfon, habiliti'ls ", Toast.LENGTH_LONG).show();
                    //menu dialeg
                    //txtInfo.setText("Per seguretat, està deshabilitada la SD i el microfon, habiliti'ls els dos");

                    ActivityCompat.requestPermissions
                            (this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUESTS);

                } else {
                    // request the permission.
                    // CALLBACK_NUMBER is a integer constants
                    Toast.makeText(this, "demana permis, no rationale ", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE,
                                    Manifest.permission.READ_CALL_LOG}, MY_PERMISSIONS_REQUESTS);
                    // The callback method gets the result of the request.
                    Log.d("Permission:", "No hay permisos");
                }
            } else {
                Uri uri;
                /*
                content://media/internal/images
                content://media/external/video
                content://media/internal/audio

                */

                //         content://media/*/images
                //         content://settings/system/ringtones
                uri = Uri.parse("content://call_log/calls");

                String[] projeccion = new String[]{CallLog.Calls.TYPE, CallLog.Calls.NUMBER, CallLog.Calls.DURATION};



                Cursor c = getContentResolver().query(
                        uri,
                        projeccion,
                        null,
                        null,
                        null);

                textView.setText("");


                while(c.moveToNext()){
                    textView.append("Tipo: " + c.getString(0) + " Número: " + c.getString(1) + " Duración: " + c.getString(2) +"\n");
                }
                c.close();
            }
        }

    }

    public void ObtenerDatos(){
        String estat= Environment.getExternalStorageState();
        // comprova si hi ha SD i si puc escriure en ella
        if (estat.equals(Environment.MEDIA_MOUNTED)) {
            int permCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
            //int permCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (!(permCheck1== PackageManager.PERMISSION_GRANTED)) {
                if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS))) {


                    // asincrona:no bloquejar el thread esperant la seva resposta
                    // Bona pràctica, try again to request the permission.
                    // explicar a l usuari per què calen aquests permisos
                    Toast.makeText(this, "Per seguretat, està deshabilitada la SD i el microfon, habiliti'ls ", Toast.LENGTH_LONG).show();
                    //menu dialeg
                    //txtInfo.setText("Per seguretat, està deshabilitada la SD i el microfon, habiliti'ls els dos");

                    ActivityCompat.requestPermissions
                            (this, new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUESTS);

                } else {
                    // request the permission.
                    // CALLBACK_NUMBER is a integer constants
                    Toast.makeText(this, "demana permis, no rationale ", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUESTS);
                    // The callback method gets the result of the request.
                    Log.d("Permission:", "No hay permisos");
                }
            } else {
                String[] projeccion = new String[]{ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE};
                String selectionClause = ContactsContract.Data.MIMETYPE + "='" +
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND "
                        + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
                String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

                Cursor c = getContentResolver().query(
                        ContactsContract.Data.CONTENT_URI,
                        projeccion,
                        selectionClause,
                        null,
                        sortOrder);

                textView.setText("");


                while (c.moveToNext()) {
                    textView.append("Identificador: " + c.getString(0) + " Nombre: " + c.getString(1) + " Número: " + c.getString(2) + " Tipo: " + c.getString(3) + "\n");
                }
                c.close();
            }
        }
        /*contactsCursor = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,   // URI de contenido para los contactos
                projection,                        // Columnas a seleccionar
                selectionClause                    // Condición del WHERE
                selectionArgs,                     // Valores de la condición
                sortOrder);                        // ORDER BY columna [ASC|DESC]*/

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

                    /**try {
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
                     stopButton.setEnabled(false);*/
                }
                return;
            }
        }
    }
}
