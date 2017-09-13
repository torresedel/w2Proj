package com.example.admin.w2proj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by Admin on 9/11/2017.
 */

public class NotificationActivity extends Activity {

    TextView tvNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationlayout);

        tvNotification = findViewById(R.id.tvNotification);

        Intent intent = this.getIntent();

        tvNotification.setText(intent.getStringExtra("data"));
    }
}
