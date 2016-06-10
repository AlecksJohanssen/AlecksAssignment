package com.example.alecksjohansson.Assignment;
import android.graphics.Color;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by alecksjohansson on 6/8/16.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public final class DefaultIntro extends AppIntro {
    private ImageView mImageView;
    private Animation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getLayoutInflater().inflate(R.layout.intro_background_2,null);
        addSlide(IntroFragment.newInstance(R.layout.intro_background_2));
        addSlide(IntroFragment.newInstance(R.layout.intro_background_3));
        setBarColor(Color.parseColor("#26FFFFFF"));
        setSeparatorColor(Color.parseColor("#26FFFFFF"));
        mImageView = (ImageView) v.findViewById(R.id.ivTravel);
         mAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide);
        mImageView.startAnimation(mAnimation);
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
        Toast.makeText(getApplicationContext(), getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent i = new Intent(DefaultIntro.this,MainPage.class);
        startActivity(i);
    }
    public void getStarted(View v){
        loadMainActivity();
    }
}