package com.example.admin.w2proj;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    String[] fruits = {"Orange", "Banana", "Pineapple", "Apple", "Grape",
            "Strawberry", "Mango", "Raspberry", "Avocado", "Tomato"};
    ListView lvMainList;
    ArrayAdapter<String> adapter;

    Button btnShowDialog;
    TextView tvDialog;
    Button btnShowAlert;
    Button btnShowCustomAlert;

    EditText etRecipient;
    EditText etMessage;
    String SENT = "Message Sent";
    String DELIVERED = "Message Delivered";
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////////////////////////////////////////////////////////////////////////////////LIST VIEW
        lvMainList = (ListView) findViewById(R.id.lvMainList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fruits);

        lvMainList.setAdapter(adapter);

        lvMainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, fruits[i] + " is selected!", Toast.LENGTH_LONG).show();
            }
        });

        btnShowDialog = (Button) findViewById(R.id.btnShowDialog);
        tvDialog = (TextView) findViewById(R.id.tvDialog);

        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        //////////////////////////////////////////////////////////////////////////////////////////////////////SHOW ALERT
        btnShowAlert = (Button) findViewById(R.id.btnShowAlert);

        btnShowAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("My Alert");
                builder.setMessage("Sprite Advertisement");

                builder.setCancelable(false);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Yes got selected", Toast.LENGTH_LONG);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "No was selected", Toast.LENGTH_LONG);
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnShowCustomAlert = (Button) findViewById(R.id.btnShowCustomAlert);

//////////////////////////////////////////////////////////////////////////////////////////////////////SHOW CUSTOM ALERT
        btnShowCustomAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.customalertlayout, null);

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });

        //////////////////////////////////////////////////////SMS
        etRecipient = (EditText) findViewById(R.id.etRecipient);
        etMessage = (EditText) findViewById(R.id.etMessage);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_PHONE_STATE)) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    }
                } else {

                }

                String rec = etRecipient.getText().toString();
                String msg = etMessage.getText().toString();

                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(rec, null, msg, null, null);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)
                            == PackageManager.PERMISSION_GRANTED) {

                        Toast.makeText(this, "Permission Grant", Toast.LENGTH_LONG).show();

                    }
                }
        }

    }

    public void send(View v) {

        ///////////////////////////////////////////////////////////////////////////////////////////NOTIFICATION PENDINGINTENT
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("data", "Intent Data");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("My Notification Title").setContentText("This is my notification text")
                .setSmallIcon(R.drawable.ic_launcher).setContentIntent(pendingIntent).setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(001, builder.build());


    }


    public void showDialog() {
        final Dialog dialog = new Dialog(this);

        dialog.setTitle("Advertisement");

        dialog.setContentView(R.layout.dialogfragment);

        dialog.show();

        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss();
                t.cancel();
            }
        }, 5000);
    }


}

