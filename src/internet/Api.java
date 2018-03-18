package internet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Api {
	//this class will be used to get all the API calls relevant to the android.permission.Internet
	private List<String> internet;
	private HashMap<String,List<String>> i;
	
	//constructor
	Api(){
		System.out.println("Object created");
		internet = new ArrayList<>();
		i = new HashMap<>();
	}
	
	public List<String> getInternet(){
		return internet;
	}
	
	void parse() {
		File file = new File("C:\\Users\\thekl\\Desktop\\All_API_calls.txt");
		List<String> inter = new ArrayList<>();
		
		BufferedReader br = null;
		FileReader fr = null;
		String sCurrentLine;
		boolean flag = false;
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			while ((sCurrentLine = br.readLine()) != null) {
				//if i reached that line then I found the methods I wanted
				if(sCurrentLine.contains("permission.INTERNET")){
					//set the flag to true so the rest lines are read
					flag=true;
					continue;
				}
				
				if(flag) {
					//the next line after every permission in the file is Callers, but we don't want that
					if(sCurrentLine.contains("Callers")) {
						continue;
					}else {//get the methods
						String[] st = sCurrentLine.split(" ");
						String temp = st[2];
						st  = temp.split("");
						temp = "";
						for(String s:st) {
							if(!s.equals("(")) {
								temp = temp+s;
							}else break;
						}
						inter.add(temp);
					}
				}
				if(sCurrentLine.contains("Permission")) {//then I reached the next permission
					flag=false;
				}
			}
		}catch (IOException e) {

			e.printStackTrace();
		}		
		internet = new ArrayList<>(new HashSet<>(inter));
	}
}
