package advert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Ad {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\thekl\\Desktop\\Capita_Slecta\\advert\\com.androidcasualgames.bubbleshooterclassic\\smali");
		RemoveAd remove = new RemoveAd();
		
		List<String> filesWithAd = new ArrayList<>();
		
		filesWithAd = remove.getSmali(file);
		
		for(String s : filesWithAd) {
			System.out.println(s);
		}
		
	}

}
