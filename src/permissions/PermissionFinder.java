package step1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PermissionFinder {
	
	//constructor
	PermissionFinder(){
		System.out.println("Object is created");
	}
	
	//for a given direcroty get the AndroidManifest.xml and dind out what permissions are needed
	public List<String> getPermissions(File folder){
		List<String> perm = new ArrayList<String>();
		
		//for this specific .xml file parse it to find all the permissions		
		StringBuilder sb = new StringBuilder();
		try{
			BufferedReader br = new BufferedReader(new FileReader(folder));
			String line;
			while ((line = br.readLine()) != null) {
		            sb.append(line);
		            String temp = sb.toString();
		            String[] newt = temp.split(">");
		            for(String s: newt){
		            	if(s.contains("uses-permission")){
		            		perm.add(s);
		            	}
		            }	            
		        }
		}catch (Exception e) {
			System.out.println(e.getMessage());
	    }		
		return perm;
	}
}
