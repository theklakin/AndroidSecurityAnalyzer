package advert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoveAd {
	
	//constructor
	RemoveAd(){
		System.out.println("I am created");
	}
	
	public List<String> getSmali(File file){
		List<String> filesWithAd = new ArrayList<>();
		
		File[] files = file.listFiles();
		//for all the files the smali file contain check if they are directory or code
		for(File k : files){
			if(k.isDirectory()){
				//go deeper
				for(File j : k.listFiles()){
					List<String> help = new ArrayList<>();
					help = helper(j);
					filesWithAd.addAll(help);
				}
			} else{
				//it is a smali file :)
				//traverse it to get all the methods
				filesWithAd = traverse(k);				
			}			
		}	
		return filesWithAd;
	}
	
	
	public List<String> traverse(File f){
		//contains the files that use the method loadAd
		List<String> files = new ArrayList<>();
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains("loadAd")){
					files.add(f.getAbsolutePath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return files;
	}
	
	public List<String> helper(File f){
		List<String> filesWithAd = new ArrayList<>();	
		
		if(f.isDirectory()){
			//go deeper
			for(File j : f.listFiles()){
				List<String> help = new ArrayList<>();
				help = helper(j);
				filesWithAd.addAll(help);
			}
		} else{
			//it is a smali file :)
			//traverse it to get all the methods
			filesWithAd = traverse(f);				
		}	
		return filesWithAd;		
	}

}
