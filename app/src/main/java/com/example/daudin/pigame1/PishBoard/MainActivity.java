package com.example.daudin.pigame1.PishBoard;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daudin.pigame1.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView cible;
    ImageView barreJauge;
    ImageView jauge;
    ImageView ninja;
    int ninjaHealth;
    ImageView hpView;
    TextView scoreView;
    Timer ninjaTime,jaugeTime;
    ConstraintLayout mainLayout;
    Point windowSize;
    static int MULTI_JAUGE = 1;
    int n = 0, f = 0;
    double pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display dp = getWindowManager().getDefaultDisplay();
        windowSize = new Point();
        dp.getSize(windowSize);
        mainLayout = findViewById(R.id.mainLayout);
        hpView = findViewById(R.id.hpView);
        cible = findViewById(R.id.cibleView);
        cible.setMaxHeight(windowSize.y*5/6);
        cible.setMinimumHeight(windowSize.y*5/6);
        barreJauge = findViewById(R.id.barreJaugeView);
        scoreView = findViewById(R.id.scoreView);
        jauge = findViewById(R.id.jaugeView);
        jauge.setMaxHeight(windowSize.y/6);
        jauge.setMinimumHeight(windowSize.y/6);
        hpView.setMaxWidth(windowSize.x);
        hpView.setMinimumWidth(windowSize.x);
//        jauge.setLayoutParams(new ConstraintLayout.LayoutParams(windowSize.x,windowSize.y/6));
//        jauge.setY(windowSize.y*5/6);
        Log.e("LogTest","JH : "+jauge.getHeight());
        Log.e("LogTest","W/6 : "+windowSize.y/6);
        ninjaTime = new Timer();
        ninjaTime.schedule(new NinjaTimerTask(), 100);
        jaugeTime = new Timer();
        jaugeTime.schedule(new JaugeTimerTask(), 50,30);
    }

    private class NinjaTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(hpView.getWidth()<windowSize.x/10) {
                        jaugeTime.cancel();
                        alertEnd();
                    }
                    else{
                        hpView.setLayoutParams(new ConstraintLayout.LayoutParams(hpView.getWidth() - windowSize.x / 10,10));
                        Log.e("LogTest","WidthHealth::::::::"+hpView.getWidth());
                        addNewNinja();
                    }
                }
            });
        }
    }

    private void alertEnd() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("You Lose. PI: "+(Math.round(Double.parseDouble(scoreView.getText().toString())*100000)/100000)+"\nDifférence: "+(Math.PI-Double.parseDouble(scoreView.getText().toString())));
        alert.setCancelable(false);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(MainActivity.this,MenuActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog end = alert.create();
        end.show();
    }

    private void generateNewNinja() {
            ninjaHealth = 2;
            ninja = new ImageView(this);
            ninja.setImageResource(R.drawable.stickman);
            ninja.setLayoutParams(new ConstraintLayout.LayoutParams(50, 50));
            ninja.setX((float) (Math.random() * cible.getWidth() * 0.8 + cible.getWidth() * 0.1 - ninja.getWidth()));
            ninja.setY((float) (Math.random() * cible.getHeight() * 0.8 + cible.getHeight() * 0.1 - ninja.getHeight() + cible.getTranslationY()));
    }

    private void addNewNinja() {
        ninjaTime.cancel();
        if(ninja != null) {
            hpView.setMaxWidth(hpView.getWidth()-windowSize.x/10);
            hpView.setMinimumWidth(hpView.getWidth()-windowSize.x/10);
            mainLayout.removeView(ninja);
        }
        generateNewNinja();
        mainLayout.addView(ninja);
        ninja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(getHealth()) {
                    case 0:
                        scorePi();
                        addNewNinja();
                        break;
                    case 1:
                        ninjaHealth--;
                        if(ninjaHealth!=0)break;
                        scorePi();
                        addNewNinja();
                        break;
                    default:
                        Log.e("LogTest","You tap to far");
                }
            }
        });
        ninjaTime = new Timer();
        ninjaTime.schedule(new NinjaTimerTask(), 3500);

        Log.e("LogTest","\n\n==========================================================================\n" +
                "Log Infos :" +
                "-----------" +
                "WindowSize : ( "+windowSize.x+" ; "+windowSize.y+" )\n" +
                "CibleSize : ( "+cible.getWidth()+" ; "+cible.getHeight()+" )\n" +
                "NinjaSize : ( "+ninja.getWidth()+" ; "+ninja.getHeight()+" )\n" +
                "CiblePos : ( "+cible.getTranslationX()+" ; "+cible.getTranslationY()+" )\n" +
                "NinjaPos : ( "+ninja.getTranslationX()+" ; "+ninja.getTranslationY()+")\n" +
                "====================================================================================\n\n\n");
    }

    private int getHealth() {
        double i = Math.abs(barreJauge.getX()-windowSize.x/2);
        if(i>windowSize.x/3)return -1;
        else if(i>windowSize.x/5) return 1;
        else return 0;
    }

    private void scorePi() {
        n++;
        double a = cible.getWidth() - ninja.getWidth(),
                b = cible.getHeight() - ninja.getHeight() + cible.getTranslationY();
        while(n%100!=0) {
            n++;
            if (ninja.getX() / a * ninja.getX() / a + ninja.getY() / b * ninja.getY() / b <= 1) f++;
        }
        pi = 4.0*f/(double)n;
        Log.e("PILog","pi "+pi);
        scoreView.setText(""+pi);
    }

    private class JaugeTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    barreJauge.setX(barreJauge.getX() + 5 * MULTI_JAUGE);
                    if (barreJauge.getX() > windowSize.x - 10 - barreJauge.getWidth())
                        MULTI_JAUGE = -1;
                    else if (barreJauge.getX() < 10)
                        MULTI_JAUGE = 1;
                }
            });
        }
    }
}
