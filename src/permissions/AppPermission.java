package step1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class AppPermission {

	public static void main(String[] args) {
		System.out.println("Enter the manifest directory: ");
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String fold = reader.nextLine(); 
		System.out.println("You inserted: "+ fold);
		reader.close(); 
		
		File folder = new File(fold);		
		PermissionFinder p = new PermissionFinder();		
		List<String> perm = new ArrayList<String>();
		
		perm = p.getPermissions(folder);
		
		List<String> d = new ArrayList<>(new HashSet<>(perm));
		
		for(String s : d){
			System.out.println(s);
		}

	}

}
