package nori.m1nthing2322.lang.learn_russian.activity.learn;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nori.m1nthing2322.lang.learn_russian.R;

/**
 * Created by m1nth on 2016-10-15.
 */

public class Alphabet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setSubtitle(R.string.alphabet_ru);}
    }
}