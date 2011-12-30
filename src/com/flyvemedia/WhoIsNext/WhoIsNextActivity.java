package com.flyvemedia.WhoIsNext;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class WhoIsNextActivity extends Activity {
	
	GUI gui;
	Calculator calc;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        calc = new Calculator();
        gui = new GUI(this, calc);
        setContentView(gui);
    }
}