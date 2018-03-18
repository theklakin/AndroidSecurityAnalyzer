package step3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Flows {

	public static void main(String[] args) {
		
		File f = new File("C:\\Users\\thekl\\Desktop\\malware_test\\FieldSensitivity1.apk");
		Disassemble d = new Disassemble(f);
		
		d.disass();
		 
	    String myDir = System.getProperty("user.dir");
	    File temp = new File(myDir);	    
	    String s = f.getName();
	    String myString = s.substring(0, s.length()-4);
		
		//get a separate list for sources and sinks that can be appeared in the smali code*/
	    SourceSinksParser ssp = new SourceSinksParser();	    
	    ssp.parse();
		
		List<String> source = new ArrayList<>();
		List<String> sink = new ArrayList<>();
		
		source = ssp.getSource();
		sink = ssp.getSink();

	    //obtain the directory of the smali files
	    for (File fileEntry : temp.listFiles()) {
	    	if(fileEntry.getName().equals(myString)){		
	    		for(File e : fileEntry.listFiles()){
	    			if(e.getName().equals("smali")){
	    				File dir = new File(e.getPath());
	    				Traverse t = new Traverse(dir,source,sink);
	    				HashMap<String,List<String>> h = new HashMap<>();
	    				HashMap<String,String> fl = new HashMap<>();
	    				t.getSmali();
	    				h =t.getMethods();
	    				fl = t.getFlows();	    				
	    				System.out.println("Flow:");
	    				if(fl.isEmpty()) {
	    					System.out.println("No flow Found");
	    				}
	    				
	    				for (Map.Entry<String, String> entry : fl.entrySet()) {
	    				    System.out.println("There is a flow to sink: " + entry.getKey()+" from source: "+entry.getValue());
	    				}
	    			}
	    		}
	    	}
	    }
	}
}



