import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;

public class lineCntr {
	public static void main(String[] args){
	FileInputStream fis;
	Scanner scan;
	int cntr=0;
	try{
		System.out.println("WTF");
		System.out.println("WTF");
		System.out.println("WTF");
		
		String currentDir=new File("").getAbsolutePath();
		currentDir+="\\src";
		File folder = new File(currentDir);
	    File[] listOfFiles = folder.listFiles();
	    String temp;
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	        temp=listOfFiles[i].getName();
	        System.out.println(temp);
	        fis=new FileInputStream(currentDir+"\\AVReader.java");
	        System.out.println(temp);

	        scan=new Scanner(fis);
	        
	        while(scan.hasNextLine())
	        	cntr++;
	      } else if (listOfFiles[i].isDirectory()) {
	        System.out.println("Directory " + listOfFiles[i].getName());
	      }
	    }
	    System.out.println(cntr);
	}
	catch(FileNotFoundException e){System.out.println("caught");}
	}
}
