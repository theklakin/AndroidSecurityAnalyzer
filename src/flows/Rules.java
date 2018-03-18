package step3;

import java.util.ArrayList;
import java.util.List;

public class Rules {
	
	private List<String> commands = new ArrayList<>();
	private List<String> restCommands= new ArrayList<>();
	
	//constructor
	Rules(){
		commands.add("move");
		commands.add("new-instance");
		commands.add("new-array");
		commands.add("aget");
		commands.add("aput");
		commands.add("iget");
		commands.add("sget");
		commands.add("neg-");
		commands.add("int-to-");
		commands.add("long-to-");
		commands.add("float-to-");
		commands.add("double-to-");
		commands.add("add-");
		commands.add("sub-");
		commands.add("mul-");
		commands.add("div-");
		commands.add("rem-");
		commands.add("and-");
		commands.add("or-");
		commands.add("xor-");
		commands.add("shl-");
		commands.add("shr-");
		commands.add("ushr-");
		
		restCommands.add("iput");
		restCommands.add("sput");
		
		//System.out.println("Done!");
	}
	
	
	public String checkVariable(String line) {
		String variable="";
		boolean flag=true;
		
		//based on the command given in the line the proper variable is returned
		//split it into words
		String[] var = line.split(" ");
		
		for(String s:commands) {
			if(line.contains(s)) {
				flag=false;
				String[] temp = var[1].split("");
				for(String ss: temp) {
					if(ss.equals(",") || ss.equals(null)) {
						break;
					} else variable = variable+s;
				}
			}
		}
		
		if(flag) {
			for(String s: restCommands) {
				boolean local = false;
				if(line.contains(s)) {
					String[] temp = var[1].split("");
					for(String ss: temp) {
						if(ss.equals(",")) {
							local = !local;
							continue;
						}
						if(local) {
							variable = variable+ss;
						}
						if(ss == null) {
							break;
						}
					}
				}
			}
		}	
		String v = checkTainted(variable);
		return v;
	}
	
	public String checkTainted(String variable) {
		
		String var = "";
		
		if(variable.contains("{")) {
			var = variable;
		}else {
			String[] s = variable.split("");
			for(int i =0; i<s.length-1;i++) {
				if(s[i].equals("{")) {
					continue;
				}else if(s[i].equals("}")) {
					break;
				}else {
					var = var+s[i];
				}				
			}
		}
		return var;
	}
}
