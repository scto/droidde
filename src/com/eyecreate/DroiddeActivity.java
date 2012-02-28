package com.eyecreate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class DroiddeActivity extends Activity {
	
	FragmentManager fragman;
	Project loadedProject;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if(!getIntent().getData().getPath().isEmpty())
        {
        	if(!checkFileForProject(getIntent().getData().getPath()))
        	{
        		throwInvalid();
        	}
        }
        else{
        	throwInvalid();
        }
        loadedProject = null;
        if(findProjectType(getIntent().getData().getPath()).equals("Android")) loadedProject = new AndroidProject(getIntent().getData().getPath());
        if(loadedProject.isValid()) setUpProjectSpace(loadedProject);
        if(!loadedProject.isValid()) findFaultAndNotify();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId())
    	{
	    	case R.id.saveproj:
	    		loadedProject.triggerProjectStateSave();
	    		return true;
	    	case R.id.run:
	    		if(!loadedProject.runProject(this)) Toast.makeText(getBaseContext(), "Project failed to run!", Toast.LENGTH_LONG);
	    		return true;
	        default:
	            return super.onOptionsItemSelected(item);
    	}
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	if(requestCode == 42 && resultCode==RESULT_CANCELED)
    	{
    		Toast.makeText(getBaseContext(), "Problem while running/compiling.", Toast.LENGTH_LONG);
    	}
    }

	private void findFaultAndNotify() {
		//This needs to do something besides say oops.
		Toast.makeText(getApplicationContext(), "A problem was found loading the project. Please examine Logcat.",Toast.LENGTH_LONG).show();
		
	}

	private void setUpProjectSpace(Project loadedProject) {
		// TODO Auto-generated method stub
		//Check if some specific files are missing and notify. This might be a good idea to put into a Project subclass in the future.
		fragman=getFragmentManager();
		ProjectFilesFragment projFiles = (ProjectFilesFragment) fragman.findFragmentById(R.layout.projectfiles);
		for(File f : loadedProject.getProjectFiles())
		{
			if(loadedProject.getProjectType().equals(ProjectTypes.ANDROID)){
				if(f.getName().equals("android.jar") && !f.exists())
				Toast.makeText(getApplicationContext(), "File android.jar was missing from project folder. Please copy this from an android SDK to your project folder.",Toast.LENGTH_LONG).show();
			}
		}
		projFiles.AddFilesToList(loadedProject.getProjectFiles());
        /*FragmentTransaction editortrans = fragman.beginTransaction();
        Fragment editorfrag = new EditorFragment();
        editortrans.add(R.id.mainlayout,editorfrag);
        editortrans.commit();*/
		
	}

	private void throwInvalid() {
		Toast.makeText(getApplicationContext(),R.string.invalidProject,5).show();
		this.finish();
		
	}
	
	public void openFileInEditor(File f)
	{
		EditorFragment edFragment = (EditorFragment) fragman.findFragmentById(R.id.fileeditor);
		edFragment.openFile(f);
	}

	private boolean checkFileForProject(String path) {
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new File(path));
			if(doc.getChildNodes().item(0).getNodeName().toLowerCase().equals("project")){
				return true;
			}
			else{
				return false;
			}
		}catch (ParserConfigurationException e1) {
			e1.printStackTrace();
			return false;
		} 
		catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	private String findProjectType(String path){
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new File(path));
			if(doc.getChildNodes().item(0).getNodeName().toLowerCase().equals("project")){
				return doc.getChildNodes().item(0).getAttributes().getNamedItem("type").getTextContent();
			}
		}catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} 
		catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}