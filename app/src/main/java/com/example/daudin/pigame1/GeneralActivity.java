package com.example.daudin.pigame1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.daudin.pigame1.PishBoard.MainActivity;

public class GeneralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        findViewById(R.id.pishboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chargePishBoard();
            }
        });
    }

    private void chargePishBoard() {
        Intent i = new Intent(GeneralActivity.this,com.example.daudin.pigame1.PishBoard.MenuActivity.class);
        startActivity(i);
    }
}
