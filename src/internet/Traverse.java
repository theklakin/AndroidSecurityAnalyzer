package internet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.HashMap;
//import java.util.List;
import java.util.Set;

public class Traverse {
	
	private Set<String> internet;
	private HashMap<String,String> access = new HashMap<String,String>();
	
	//constructor
	Traverse(Set<String> internet){
		this.internet = internet;
		System.out.println("I am created");
	}
	
	public HashMap<String,String> getAccess(){
		return access;
	}
	
	//for the given directory it saves the path and for each file the methods it contains and 
	//arguments if it accesses the internet
	public void getSmali(File file){		
		File[] files = file.listFiles();
		//for all the files the smali file contain check if they are directory or code
		for(File k : files){
			if(k.isDirectory()){
				//go deeper
				for(File j : k.listFiles()){
					helper(j);
				}
			} else{
				//it is a smali file :)
				//traverse it to get all the methods
				tr(k);				
			}			
		}	
	}
		
	public void tr(File f){
		BufferedReader br = null;
		FileReader fr = null;
		boolean flag=false;
		
		try {
			
			fr = new FileReader(f);
			br = new BufferedReader(fr);

			String sCurrentLine;
			String method="";

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains(".method") && !sCurrentLine.contains("constructor")) {//then I found the beginning of a method
					method = sCurrentLine;
					flag = true;
					continue;
				}
				
				if(sCurrentLine.contains(".end method")) {//then I found the end of a method
					flag = false;
				}
				
				if(flag) {
					String temp = check_line(sCurrentLine);
					if(!temp.equals("")) {
						//i don't save the name of the method using the internet method
						access.put(f.getCanonicalPath(), sCurrentLine);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//use this helper function to check if the current line inside a method contains an internet method
	//public boolean check_line(String sCurrentLine) {
	public String check_line(String sCurrentLine) {
		String flag="";
		
		for(String s: internet) {
			if(sCurrentLine.contains(s)) {				
				flag=s;
				break;
			}
		}		
		return flag;		
	}
	
	public void helper(File f){		
		if(f.isDirectory()){
			//go deeper
			for(File j : f.listFiles()){
				helper(j);
			}
		} else{
			//it is a smali file :)
			//traverse it to get all the methods
			tr(f);				
		}		
	}
}

