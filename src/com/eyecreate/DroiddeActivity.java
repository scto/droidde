package com.eyecreate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.Toast;

public class DroiddeActivity extends Activity {
	
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
        Project loadedProject = new Project(getIntent().getData().getPath());
        if(loadedProject.isValid()) setUpProjectSpace(loadedProject);
        if(!loadedProject.isValid()) findFaultAndNotify();
        /*FragmentManager fragman=getFragmentManager();
        FragmentTransaction editortrans = fragman.beginTransaction();
        Fragment editorfrag = new EditorFragment();
        editortrans.add(R.id.mainlayout,editorfrag);
        editortrans.commit();*/
        
    }

	private void findFaultAndNotify() {
		// TODO Auto-generated method stub
		
	}

	private void setUpProjectSpace(Project loadedProject) {
		// TODO Auto-generated method stub
		//Check if some specific files are missing and notify
		for(File f : loadedProject.getProjectFiles())
		{
			if(loadedProject.getProjectType().equals(ProjectTypes.ANDROID) && f.getName().equals("android.jar") && !f.exists()){
				Toast.makeText(getApplicationContext(), "File android.jar was missing from project folder. Please copy this from an android SDK to your project folder.",Toast.LENGTH_LONG).show();
			}
		}
		
	}

	private void throwInvalid() {
		Toast.makeText(getApplicationContext(),R.string.invalidProject,5).show();
		this.finish();
		
	}

	private boolean checkFileForProject(String path) {
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(new File(path));
			if(doc.getChildNodes().item(0).getNodeName().equals("Project")){
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
}