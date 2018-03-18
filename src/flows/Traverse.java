package step3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Traverse {
	
	private File file;
	private HashMap<String,List<String>> methods;
	private HashMap<String,String> flows;
	private List<String> source;
	private List<String> sink;
	private HashMap<String,String> returnValues = new HashMap<>();
	
	//constructor
	Traverse(File file, List<String> source, List<String> sink){
		this.setFile(file);
		System.out.println("Ready to traverse smali file");
		methods = new HashMap<>();
		flows = new HashMap<>();
		this.sink = sink;
		this.source = source;
	}
	
	public HashMap<String,List<String>> getMethods() {		
		try {		
			PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\thekl\\Desktop\\Capita_Slecta\\methods.txt"));
			for (Entry<String, List<String>> entry : methods.entrySet()) {
				out.println("#################################################################");
				out.println("The file: " + entry.getKey()+" has the methods:");
				for(String s : entry.getValue()) {
					out.println(s);
				}
			}
			out.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		return methods;
	}
	
	public HashMap<String,String> getFlows() {
		return flows;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
		
	public void printFiles(){
		System.out.println("This directory contains:");
		String temp2[] = file.list();
		for(String s2: temp2){
			System.out.println(s2);
		}
	}
	
	//for the given directory it saves the path and for each file the methods it contains
	public void getSmali(){		
		File[] files = file.listFiles();
		//for all the files the smali file contain check if they are directory or code
		for(File k : files){
			System.out.println(k.getName());
			if(k.isDirectory()){
				//go deeper
				for(File j : k.listFiles()){
					HashMap<String,List<String>> help = new HashMap<>();
					help = helper(j);
					methods.putAll(help);
				}
			} else{
				List<String> temp = new ArrayList<>();
				//it is a smali file :)
				//traverse it to get all the methods
				//and possible flows
				temp = tr(k);
				methods.put(k.getAbsolutePath(), temp);				
			}		
		}	
	}
	
	
	public List<String> tr(File f){
		List<String> methods = new ArrayList<>();
		BufferedReader br = null;
		FileReader fr = null;
		//to know when i am inside a method in order to check for leakage
		boolean flag = false;
		HashMap<String,String> tainted = new HashMap<>();
		Rules r = new Rules();
		List<String> sour = new ArrayList<>();
		List<String> sin = new ArrayList<>();
		String tempp="";
		
		try {
			fr = new FileReader(f);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains(".method")){
					methods.add(sCurrentLine);
					tempp = sCurrentLine;
					flag=true;
					continue;
					//now i am in the method time to find sources and sinks and flows
				}
				
				if(sCurrentLine.contains(".end method")) {
					flag=false;
				}
				
				//if i am inside a method
				if(flag) {		
					//we need to save any return value that is tainted in case this method is called somewhere else
					if(sCurrentLine.contains("return")) {
						//a method will return a variable, thus check if it is tainted
						if(!tainted.isEmpty()) {
							for(String s:tainted.values()) {
								if(sCurrentLine.contains(s)) {
									//then add the method name which is the last added and the variable
									returnValues.put(methods.get(methods.size()-1), s);
								}
							}
						}
					}
					
					//if this line has a source keep it
					String src = checkSource(sCurrentLine);
					if(!src.equals("")) {
						sour.add(src);
						//if the line contains a source check if it is passed to a variable
						String temp = r.checkVariable(sCurrentLine);
						if(!temp.equals("")) {
							//keep track from which source this variable was tainted
							tainted.put(src, temp);
						}
					}
					
					//if there is something in tainted check if it is used in the next lines and passed to other variables
					if(!tainted.isEmpty()) {
						for(String s:tainted.values()) {
							if(sCurrentLine.contains(s)) {
								String temp = r.checkVariable(sCurrentLine);
								if(!temp.equals("") && !temp.equals(s)) {
									String sr = getIndex(s,tainted);
									tainted.put(sr, temp);
								}
							}
						}
						
						//there is the case of move-result that doesn't contain sink or source, it just moves the result of the previous command to a variable
						if(sCurrentLine.contains("move-result")) {
							//to get the variable
							String temp = r.checkVariable(sCurrentLine);
							//and added to the tainted values
							int k = tainted.size();
							//it will be the just added source
							String j = (String) tainted.keySet().toArray()[k-1];
							tainted.put(j, temp);
						}
					}
					
					for(String m : methods) {
						//if another method is called check if it returns a tainted value
						if(sCurrentLine.contains(m)) {
							for (Map.Entry<String, String> entry : returnValues.entrySet()) {
								if(m.equals(entry.getKey())) {
									tainted.put(entry.getValue(), entry.getValue());
								}
							}
						}
					}
					
					//if we find a sink check if it has a source or a tainted variable
					String sk = checkSink(sCurrentLine);
					if(!sk.equals("") ) {
						sin.add(sk);
						//check if it uses a source directly
						src = checkSource(sCurrentLine);
						if(!src.equals("")) {
							//then there is a flow
							flows.put(sk, src);
						}
						
						//check if it uses a tainted variable
						for(String s:tainted.values()) {
							if(sCurrentLine.contains(s)) {
								String k = getIndex(s,tainted);
								flows.put(sk, k);
							}
						}
					}					
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return methods;
	}
	
	public String getIndex(String value, HashMap<String, String> tainted) {
		
	    for (Map.Entry<String, String> entry : tainted.entrySet()) {
            if (value.equals(entry.getValue())) {
            return entry.getKey();
            }
	    }
	    return "";
	}
	
	
	public String checkSource(String line) {
		String flag="";
		for(String s:source) {
			if(line.contains(s)) {
				flag = s;
			}
		}		
		return flag;
	}
	
	
	public String checkSink(String line) {
		String flag="";
		for(String s:sink) {
			if(line.contains(s)) {
				flag = s;
			}
		}		
		return flag;
	}
	
	public HashMap<String,List<String>> helper(File f){
		HashMap<String,List<String>> h = new HashMap<>();
		
		if(f.isDirectory()){
			//go deeper
			for(File j : f.listFiles()){
				HashMap<String,List<String>> help = new HashMap<>();
				help = helper(j);
				h.putAll(help);
			}
		} else{
			List<String> temp = new ArrayList<>();
			//it is a smali file :)
			//traverse it to get all the methods
			temp = tr(f);
			h.put(f.getAbsolutePath(), temp);				
		}	
		return h;		
	}
	
}
