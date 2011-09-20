package com.eyecreate;

import android.app.Fragment;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
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
			 //
		 }
		 else {
			 //
		 }
		 super.onActivityCreated(bundle);
	 }
	 
	 @Override
	 public void onResume() {
		 super.onResume();
		 TextView tv = (TextView) getActivity().findViewById(R.id.linenumbers);
		 RichEditText et = (RichEditText) getActivity().findViewById(R.id.editorcontent);
		 tv.setTextSize(et.getTextSize());
	 }


}
