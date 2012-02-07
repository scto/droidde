package com.eyecreate;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Project {

	private Document projectXML = null;
	private DocumentBuilder dBuilder = null;
	private boolean isValid;
	private ProjectTypes projectType;
	private List<File> projectFiles;
	
	//This class manages the project structure from the project XML and gives other classes information about the loaded project.
	public Project(String path) {
		path=initialSanityChecks(path);
		try {
			dBuilder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
			projectXML = dBuilder.parse(new File(path));
			//TODO:Write code to read project here
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
		isValid=true;
	}
	
	private String initialSanityChecks(String path) {
		//In order to make error finding easier, pass a Directory when you need a new project and the project.xml when you need it loaded.
		File filePath = new File(path);
		if(!filePath.exists()) isValid=false;
		if(filePath.isDirectory()) path=createProject(filePath).getAbsolutePath();
		return path;
		
	}

	private File createProject(File filePath)
	{
		//TODO:write code to write project structure to XML. Then pass made file out to return.
		return filePath;
	}
	
	private void createProjectFileList(File filePath)
	{
		//TODO:write code to read folders recursively and add compatible project file to list.
	}
	
	public boolean isValid(){
		return isValid;
	}
	
	public List<File> getProjectFiles()
	{
		return projectFiles;
	}

}
