package me.khrystal.snackbarutildemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import me.khrystal.widget.snackbar.SnackbarUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showSnackbar(View view) {
        SnackbarUtil.getInstance(this)
                .setMessage("Success~")
                .setMessageColor(0xffffffff)
                .setBackgroundAlpha(0.9f)
                .setBackgroundColor(0xffffc026)
                .setLeftAndRightDrawable(R.drawable.img_prompt_check, null)
                .setCoverStatusbar(true)
                .setGravity(Gravity.TOP).show();
    }

    public void openActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}
