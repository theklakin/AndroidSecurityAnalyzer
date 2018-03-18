package internet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Internet {

	public static void main(String[] args) {
		List<String> internet = new ArrayList<>();
		
		//use this object to get all relevant internet methods
		Api ap = new Api();
				
		ap.parse();
		
		internet = ap.getInternet();
		
		Set<String> inter = new HashSet<String>(internet);
		//now time to go through the smali code
		Traverse tr = new Traverse(inter);
		
		File file = new File("C:\\Users\\thekl\\Desktop\\Capita_Slecta\\internet\\com.adpog.diary");
		
		tr.getSmali(file);
		
		HashMap<String,String> access = new HashMap<String,String>();
		
		access = tr.getAccess();
		
		try {		
			PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\thekl\\Desktop\\Capita_Slecta\\internet\\com.adpog.diary\\access.txt"));
			for (Map.Entry<String, String> entry : access.entrySet()) {
				out.println("#" + "The file: " + entry.getKey());
				out.println(" uses the method: "+entry.getValue());
				out.println();
			}
			out.close();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}		
	}
}
