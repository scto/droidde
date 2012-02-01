package com.eyecreate;

import java.util.List;
import java.util.Arrays;

//This enum stores values about each project type so the Project class knows how to load and deal with files in the project.
public enum ProjectTypes {
	ANDROID (Arrays.asList("java", "xml"));
	
	private final List<String> acceptableTypes;
	
	ProjectTypes(List<String> types){
		this.acceptableTypes = types;
	}
	
	
	public boolean isAcceptedFile(String extension){
		if(acceptableTypes.contains(extension)) return true;
		return false;
	}
}
