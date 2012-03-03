package com.eyecreate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ProjectFilesFragment extends Fragment {

	List<File> fileList; 
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        return inflater.inflate(R.layout.projectfiles, container, false);
	    }
	 
	 public void AddFilesToList(List<File> files)
	 {
		 getActivity().setContentView(R.layout.projectfiles);
		 ListView lv = (ListView) getActivity().findViewById(R.id.filelist);
		 lv.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		 fileList = files;
		 List<String> values = new ArrayList<String>();
		 for(File f : fileList){
			 values.add(f.getName());
		 }
		 lv.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,values));
		 //This part creates the listener for list clicks
		 final OnItemClickListener newListener = new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				requestOpenFile(fileList.get(position));
			}
		    };
		 final OnItemLongClickListener longListener = new OnItemLongClickListener() {
			 public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) 
			 {
				 
				 for(int i=0;i<av.getCount();i++){
					 ((ListView)av).setItemChecked(i, false);
				 }
				 ((ListView)av).setItemChecked(pos, true);
				 ((DroiddeActivity)getActivity()).setProjectMainFile(fileList.get(pos));
				 return true;
			 }
		};
		lv.setOnItemLongClickListener(longListener);
		lv.setOnItemClickListener(newListener);

	 }
	 
	 public void requestOpenFile(File f){
		 DroiddeActivity da = (DroiddeActivity) getActivity();
		 da.openFileInEditor(f);
	 }


}
