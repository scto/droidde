package com.eyecreate;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.util.Log;

public class AndroidProject implements Project {

	private Document projectXML = null;
	private DocumentBuilder dBuilder = null;
	private boolean isValid;
	private ProjectTypes projectType;
	private List<File> projectFiles;
	private List<File> projectLibs;
	private String projectName;
	private File projectFile;
	private String projectAuthor = "";
	
	//This class manages the project structure from the project XML and gives other classes information about the loaded project.
	//In order to make error finding easier, pass a Directory, name, and type when you need a new project and the project.xml when you need it loaded.
	public AndroidProject(String path) {
		initialSanityChecks(path);
		try {
			dBuilder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			projectXML = dBuilder.parse(new File(path));
			if(projectXML.getChildNodes().item(0).getNodeName().equals("Project")){
				isValid=true;
				processXMLForLoad();
			}
			else{
				isValid=false;
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			isValid = false;
		} catch (SAXException e) {
			e.printStackTrace();
			isValid = false;
		} catch (IOException e) {
			e.printStackTrace();
			isValid = false;
		}
	}
	
	public AndroidProject(String path, String name, String type) {
		File projectDir=new File(path);
		if(projectDir.isDirectory() && projectDir.list().length==0)
		{
			createProject(new File(path),name,type);
			isValid=true;
		}
		else
		{
			Log.e("Droidde","Project directory is not empty. Must be empty in order to start a new project.");
			isValid=false;
		}
	}
	
	private void initialSanityChecks(String path) {
		File filePath = new File(path);
		if(!filePath.exists()) isValid=false;
		
	}

	private void createProject(File filePath,String name, String type)
	{
		try{
			projectType = ProjectTypes.valueOf(type.toUpperCase());
			projectName = name;
			projectFile = new File(filePath.getAbsolutePath()+File.pathSeparator+projectName+".xml");
			createProjectFileList(filePath);
			for(String s: projectType.getDefaultLibs()){
				if(!(new File(filePath.getAbsolutePath()+File.pathSeparator+s).exists())){
					Log.w("Droidde","File "+filePath.getAbsolutePath()+File.pathSeparator+s+" was not found. You might want to find out why this file wasn't found.");
				}
				projectLibs.add(new File(filePath.getAbsolutePath()+File.pathSeparator+s));
			}
			triggerProjectStateSave();
		}
		catch(IllegalArgumentException e){
			Log.e("Droidde", "Invalid project type given to createProject");
			isValid=false;
		}
	}
	
	private void triggerProjectStateSave() {
		// TODO Auto-generated method stub
		
	}

	private void createProjectFileList(File filePath)
	{
		recursiveDirectorySearch(filePath);
	}
	
	private void recursiveDirectorySearch(File dir)
	{
		File[] dirs = dir.listFiles();
		for (File f :dirs)
		{
			if(f.isDirectory()){
				recursiveDirectorySearch(f);
			}
			if(f.isFile() && projectType.isAcceptedFile(f.getName().split(".")[1])){
				projectFiles.add(f);
			}
		}
	}
	
	private void processXMLForLoad()
	{
		//TODO:Create XML parsing
	}
	
	/* (non-Javadoc)
	 * @see com.eyecreate.Project#isValid()
	 */
	public boolean isValid(){
		return isValid;
	}
	
	/* (non-Javadoc)
	 * @see com.eyecreate.Project#getProjectFiles()
	 */
	public List<File> getProjectFiles()
	{
		return projectFiles;
	}
	
	/* (non-Javadoc)
	 * @see com.eyecreate.Project#getProjectType()
	 */
	public ProjectTypes getProjectType()
	{
		return projectType;
	}

}
