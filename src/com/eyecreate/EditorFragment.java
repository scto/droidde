package com.eyecreate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EditorFragment extends Fragment {

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        return inflater.inflate(R.layout.editor, container, false);
	    }
	 
	 @Override
	 public void onActivityCreated(Bundle bundle) {
		 if(bundle != null) {
			 //Put stuff here if getting a present!
		 }
		 else {
			 //
		 }
		 File extStorage = Environment.getExternalStorageDirectory();
		 File confDir = new File(extStorage,"droidde-config");
		 if(!confDir.exists()) confDir.mkdirs();
		 File configFile = new File(confDir,"syntax.xml");
		 if(!configFile.exists()) copyFile("syntaxregex.xml",configFile.getAbsolutePath());
		 super.onActivityCreated(bundle);
	 }
	 
	 private void copyFile(String fromFilename, String toFilename) {
		    AssetManager assetManager = this.getActivity().getAssets();

		    InputStream in = null;
		    OutputStream out = null;
		    try {
		        in = assetManager.open(fromFilename);
		        out = new FileOutputStream(toFilename);

		        byte[] buffer = new byte[1024];
		        int read;
		        while ((read = in.read(buffer)) != -1) {
		            out.write(buffer, 0, read);
		        }
		        in.close();
		        in = null;
		        out.flush();
		        out.close();
		        out = null;
		    } catch (Exception e) {
		        Log.e("tag", e.getMessage());
		    }

		}
	 
	 public void openFile(File f)
	 {
		 RichEditText ret = (RichEditText) getActivity().findViewById(R.id.editorcontent);
		 try {
			ret.setText(FileUtils.readFileToString(f));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 ret.setCurrentLangFromExt(f.getName().split(".")[f.getName().split(".").length-1]);

	 }
	 
	 public void saveFile(File f)
	 {
		 RichEditText ret = (RichEditText) getActivity().findViewById(R.id.editorcontent);
		 try {
			FileUtils.writeStringToFile(f, ret.getText().toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	 
	 @Override
	 public void onResume() {
		 super.onResume();
		 File extStorage = Environment.getExternalStorageDirectory();
		 File confDir = new File(extStorage,"droidde-config");
		 File configFile = new File(confDir,"syntax.xml");
		 if(!configFile.exists()) copyFile("syntaxregex.xml",configFile.getAbsolutePath());
		 TextView tv = (TextView) getActivity().findViewById(R.id.linenumbers);
		 RichEditText et = (RichEditText) getActivity().findViewById(R.id.editorcontent);
		 tv.setTextSize(et.getTextSize());
	 }


}
