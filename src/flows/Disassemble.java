package step3;

import java.io.File;
import java.io.IOException;

public class Disassemble {
	
	//this class is used to disassemble a given application (.apk)
	private File f;
	
	//constructor
	Disassemble(File f){
		this.setFile(f);
		System.out.println("Object created");
	}

	public File getFile() {
		return f;
	}

	public void setFile(File f) {
		this.f = f;
	}
	
	public void disass(){		
		//the command that is going to be given to cmd
		String temp = "apktool d " + f;
		
		//put it in the cmd
		ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c",temp);
		builder.redirectErrorStream(true);
		
		try{
			Process p = builder.start();
		}catch(IOException e){
			System.out.println(e.getMessage());
		}	
	}
	

}
