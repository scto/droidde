package com.eyecreate;

import android.app.Activity;
import android.os.Bundle;

public class DroiddeActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*FragmentManager fragman=getFragmentManager();
        FragmentTransaction editortrans = fragman.beginTransaction();
        Fragment editorfrag = new EditorFragment();
        editortrans.add(R.id.mainlayout,editorfrag);
        editortrans.commit();*/
        
    }
}