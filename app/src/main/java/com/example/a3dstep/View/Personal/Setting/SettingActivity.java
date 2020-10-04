package com.example.a3dstep.View.Personal.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a3dstep.Constant.SharePreference;
import com.example.a3dstep.R;
import com.example.a3dstep.View.Login.LoginActivity;

public class SettingActivity extends AppCompatActivity {
    LinearLayout lnLocation,lnWeight,lnGender,lnDateOfBirth,lnName,lnChangePassword,lnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        addControl();
        addEvent();
        SharePreference.getInstance().Init(this);
    }

    private void addControl() {
        lnLocation = (LinearLayout)findViewById(R.id.lnLocation);
        lnWeight = (LinearLayout)findViewById(R.id.lnWeight);
        lnGender = (LinearLayout)findViewById(R.id.lnGender);
        lnDateOfBirth = (LinearLayout)findViewById(R.id.lnDateOfBirth);
        lnName = (LinearLayout)findViewById(R.id.lnName);
        lnChangePassword = (LinearLayout)findViewById(R.id.lnChangePassword);
        lnLogout = (LinearLayout)findViewById(R.id.lnLogout);
    }

    private void addEvent() {
        lnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreference.getInstance().DeleteSharePre();
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}