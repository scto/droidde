package com.eyecreate;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends Activity {
	static final int DIALOG_NEW_PROJECT_ID = 0;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.welcomemenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId())
    	{
    		//If the "New Project" button is selected
	    	case R.id.newprojectitem:
	    		showNewProjectDialog();
	    		return true;
	        default:
	            return super.onOptionsItemSelected(item);
    	}
    }
    protected Dialog onCreateDialog (int id)
    {
    	final Dialog dialog;
    	switch(id)
    	{
    		//Define the New Project Dialog window
    		case DIALOG_NEW_PROJECT_ID:
    			//Initialize the dialog
    			dialog = new Dialog(this);
    			dialog.setContentView(R.layout.projectdialog);
    			dialog.setTitle("New Project");
    			//Set up the spinner
    			final Spinner projectType = (Spinner)dialog.findViewById(R.id.projectType);
    			//Get every ProjectTypes object from the enumeration and input into String Array
    			ArrayList<String> projectTypeArrayList = new ArrayList<String>();
    			for(ProjectTypes p : ProjectTypes.values())
    				projectTypeArrayList.add(p.toString());
    			String [] projectTypeArray = projectTypeArrayList.toArray(new String[projectTypeArrayList.size()]);
    			//connect buttons
    			Button create = (Button)dialog.findViewById(R.id.createButton);
    			create.setOnClickListener(new View.OnClickListener() {
					
					public void onClick(View v) {
						dialog.cancel();
						createNewProject(ProjectTypes.valueOf((String) projectType.getSelectedItem()), ((EditText)dialog.findViewById(R.id.projectName)).getText().toString(), ((EditText)dialog.findViewById(R.id.projectDirectory)).getText().toString());
						
						
					}
				});
    			//Initialize adapter
    			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,
    					projectTypeArray);
    			projectType.setAdapter(adapter);
    			break;
    	    default:
    	        dialog = null;
    	}
    	return dialog;
    }
    protected void onPrepareDialog (int id, Dialog dialog)
    {
    	switch(id)
    	{
	    	case DIALOG_NEW_PROJECT_ID:
	    		TextView projectName = (TextView)dialog.findViewById(R.id.projectName);
	    		projectName.setText(R.string.project_name);
	    		break;
	    	default:
	    		dialog = null;
    	}
    }
    public void showNewProjectDialog()
    {
    	showDialog(DIALOG_NEW_PROJECT_ID);
    }
    
    private void createNewProject(ProjectTypes type,String name, String path)
    {
    	if(type.equals(ProjectTypes.ANDROID))
    	{
    		AndroidProject ap = new AndroidProject(path,name,type.name());
    		if(ap.isValid())
    		{
    			ComponentName cn = new ComponentName("com.eyecreate","com.eyecreate.DroiddeActivity");
    			Intent intent = new Intent("android.intent.action.VIEW");
    			intent.setComponent(cn);
    			intent.setData(Uri.parse("file://"+path+File.separator+name+".xml"));
    			startActivity(intent);
    		}
    		else{
    			Toast.makeText(getBaseContext(), "Project Creation went wrong.", Toast.LENGTH_LONG);
    		}
    	}
    }
}
