package com.example.musicapp_daolv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.musicapp_daolv.fragment.FragmentHome;
import com.example.musicapp_daolv.fragment.FragmentMusic;
import com.example.musicapp_daolv.fragment.FragmentOnline;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private BottomNavigationView bottomNavigation;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FragmentHome fragmentHome;
    private FragmentMusic fragmentMusic;
    private FragmentOnline fragmentOnline;

    private final int readCode  = 200;
    private final int writeCode = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // xin quyen doc ghi
        checkPermission("android.permission.READ_EXTERNAL_STORAGE", readCode);
        checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", writeCode);
        System.out.println("Data can write??--->"+ Environment.getExternalStorageDirectory().canWrite());
        System.out.println("Data can read??--->"+Environment.getExternalStorageDirectory().canRead());

        actionBar = getSupportActionBar();
        actionBar.setTitle("Bài hát cá nhân");
        bottomNavigation = findViewById(R.id.bottomnavigation);

        fragmentHome = new FragmentHome();
        fragmentOnline = new FragmentOnline();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainframelayout, fragmentHome);
        fragmentTransaction.add(R.id.mainframelayout, fragmentOnline).hide(fragmentOnline);
        fragmentTransaction.commit();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        actionBar.setTitle("Bài hát cá nhân");
                        fragmentManager.beginTransaction().show(fragmentHome).hide(fragmentOnline).commit();
                        break;
                    case R.id.online:
                        actionBar.setTitle("Bảng xếp hạng");
                        fragmentManager.beginTransaction().show(fragmentOnline).hide(fragmentHome).commit();
                        break;
                }
                return true;
            }
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 201) {

            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Showing the toast message
                Toast.makeText(MainActivity.this, "Storage write Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Storage write Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 200) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Storage read Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Storage read Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}