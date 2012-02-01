package com.eyecreate;

import java.io.File;
import java.io.IOException;

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
	
	//This class manages the project structure from the project XML and gives other classes information about the loaded project.
	public Project(String path) {
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
	
	public boolean isValid(){
		return isValid;
	}

}
