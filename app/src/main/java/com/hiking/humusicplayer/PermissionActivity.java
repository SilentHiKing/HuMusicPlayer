package com.hiking.humusicplayer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hiking.humusicplayer.bean.IConstant;
import com.hiking.humusicplayer.util.PermissionUtil;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!PermissionUtil.hasPermission(this,permissions)){
            PermissionUtil.requestPermissions(this,permissions,PermissionUtil.DEFAULT_REQUEST_CODE);
        }else{
            Log.d(IConstant.TAG,"start MainActivity in onCreate");
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PermissionUtil.DEFAULT_REQUEST_CODE:
                if (PermissionUtil.verifyPermissions(grantResults)){
                    Log.d(IConstant.TAG,"start MainActivity");
                    startActivity(new Intent(this,MainActivity.class));
                }else{
                    finish();
                }
                break;
            default:
                break;
        }

    }
}
