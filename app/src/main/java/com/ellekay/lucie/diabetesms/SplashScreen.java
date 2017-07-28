package com.ellekay.lucie.diabetesms;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragment;
import agency.tango.materialintroscreen.SlideFragmentBuilder;

public class SplashScreen extends MaterialIntroActivity {

    private PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()){
            launchHomeScreen();
            finish();
        }

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.background4)
                        .buttonsColor(R.color.background3)
                        .image(R.drawable.gluco)
                        .title("Glucose monitoring")
                        .description("Description 1")
                        .build());
        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.background4)
                .buttonsColor(R.color.background3)
                .image(R.drawable.food)
                .title("Diet tips")
                .description("Description 2")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.background4)
                .buttonsColor(R.color.background3)
                .image(R.drawable.meds)
                .title("Medicine reminders")
                .description("")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent i = new Intent(SplashScreen.this, Home.class);
        startActivity(i);
    }

    private void launchHomeScreen(){
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SplashScreen.this, Home.class));
        finish();
    }
}
