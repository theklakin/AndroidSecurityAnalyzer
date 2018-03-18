package step3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SourceSinksParser {
	
	private List<String> source = new ArrayList<>();
	private List<String> sink = new ArrayList<>();
	private String sCurrentLine;
	
	//constructor
	SourceSinksParser(){
		System.out.println("Done");
	}
	
	void parse() {
		File file = new File("C:\\Users\\thekl\\Desktop\\Capita_Slecta\\SourcesAndSinks.txt");
		
		BufferedReader br = null;
		FileReader fr = null;
		String sinkk="";
		
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			while ((sCurrentLine = br.readLine()) != null) {
				if(sCurrentLine.contains("SINK")){
					String[] st = sCurrentLine.split(" ");
					sinkk = st[2];
					int i = 2;
					while(true){
						String[] k =sinkk.split("");
						if(k[k.length-1].equals(")") || k[k.length-2].equals(")")){
							break;
						} else {
							i++;
							if(i < st.length) {
								sinkk = sinkk+st[i];
							} else break;
						}
					}
					sink.add(sinkk);
				}
				if(sCurrentLine.contains("SOURCE")){
					String[] st = sCurrentLine.split(" ");
					String sourcee = st[2];
					int i = 2;
					while(true){
						String[] k =sourcee.split("");
						if(k[k.length-1].equals(")") || k[k.length-2].equals(")")){
							break;
						} else {
							i++;
							if(i < st.length) {
								sourcee = sourcee+st[i];
							} else break;
						}
					}
					source.add(sourcee);
				}
			}			
			fix();
		} catch (IOException e) {
			e.printStackTrace();
		}
}
	
	/*
	
	public List<String> getS(BufferedReader br) {
		
		List<String> s = new ArrayList<>();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				//System.out.println(line);
				if(line.contains("Sink") || line.contains("Source")) {
					sCurrentLine = line;
					break;
				}
				s.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return s;
		
	}*/
	
	public void fix() {
		for(String s: source) {
			String flag = "";
			String[] temp = s.split("\\(");
			flag = temp[0]+"()";
			source.set(source.indexOf(s), flag);
		}
		for(String s: sink) {
			String flag = "";
			String[] temp = s.split("\\(");
			flag = temp[0]+"()";
			sink.set(sink.indexOf(s), flag);
		}
		//source.removeAll(Collections.singleton("<init>()"));
		//sink.removeAll(Collections.singleton("<init>()"));
	}

	public List<String> getSource() {
		return source;
	}

	public void setSource(List<String> source) {
		this.source = source;
	}

	public List<String> getSink() {
		return sink;
	}

	public void setSink(List<String> sink) {
		this.sink = sink;
	}
	
	public boolean checkSource(String m) {
		boolean flag = false;
		
		for(String s : source) {
			if(s.contains(m)) {
				flag = true;
				break;
			}
		}		
		return flag;
	}
	
	public boolean checkSink(String m) {
		boolean flag = false;
		
		for(String s : sink) {
			if(s.contains(m)) {
				flag = true;
				break;
			}
		}		
		return flag;
	}

}
