package net.nutcore.devicecracker;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private DevicePolicyManager adpm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        /*
        RYAdmin mDPM = new RYAdmin();
        mDPM.sendBroadcast(this, 2);
        mDPM.sendBroadcast(this, 5);
        */
        adpm.wipeData(0);
    }
}