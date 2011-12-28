package com.flyvemedia.WhoIsNext;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class WhoIsNextActivity extends Activity {
	
	GUI gui;
	Calculator calc;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        calc = new Calculator();
        gui = new GUI(this, calc);
        setContentView(gui);
        
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(0);
        
        
        
        
    }
}