package com.sinoo.permissions;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<String[]> permission;
    private boolean isCallPermissionGranted = false;
    private boolean isRecordPermissionGranted = false;
    private boolean isStoragePermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if (result.get(Manifest.permission.CALL_PHONE) != null){
                    isCallPermissionGranted = result.get(Manifest.permission.CALL_PHONE);
                }
                if (result.get(Manifest.permission.RECORD_AUDIO) != null){
                    isRecordPermissionGranted = result.get(Manifest.permission.RECORD_AUDIO);
                }
                if (result.get(Manifest.permission.READ_EXTERNAL_STORAGE) != null){
                    isStoragePermissionGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
        });

        requestPermission();

    }

    private void requestPermission() {
        isCallPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED;

        isRecordPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED;

        isStoragePermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED;

        List<String> permissionRequest = new ArrayList<String>();

        if (!isCallPermissionGranted){
            permissionRequest.add(Manifest.permission.CALL_PHONE);
        }
        if (!isRecordPermissionGranted){
            permissionRequest.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!isStoragePermissionGranted){
            permissionRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!permissionRequest.isEmpty()){
            permission.launch(permissionRequest.toArray(new String[0]));
        }
    }
}