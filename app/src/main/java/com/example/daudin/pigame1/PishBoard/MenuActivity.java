package com.example.daudin.pigame1.PishBoard;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import com.example.daudin.pigame1.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Point p = new Point();
        Display d = getWindowManager().getDefaultDisplay();
        d.getSize(p);
        findViewById(R.id.exp).setLayoutParams(new LinearLayout.LayoutParams(p.x,p.y/2));
        findViewById(R.id.histoirelay).setVisibility(View.GONE);
        findViewById(R.id.tutoLay).setVisibility(View.GONE);
        findViewById(R.id.hist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.histoirelay).setVisibility(View.VISIBLE);
                findViewById(R.id.pishboardlay).setVisibility(View.GONE);
                findViewById(R.id.tutoLay).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.tuto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.histoirelay).setVisibility(View.GONE);
                findViewById(R.id.pishboardlay).setVisibility(View.GONE);
                findViewById(R.id.tutoLay).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.board).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.histoirelay).setVisibility(View.GONE);
                findViewById(R.id.pishboardlay).setVisibility(View.VISIBLE);
                findViewById(R.id.tutoLay).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.jouer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.histoire).setOnKeyListener(null);
    }
}
