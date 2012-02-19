package com.eyecreate;

import java.io.File;
import java.util.List;

public interface Project {

	public abstract boolean isValid();

	public abstract List<File> getProjectFiles();

	public abstract ProjectTypes getProjectType();
	
	public abstract void triggerProjectStateSave();
	
	public abstract void runProject();

}