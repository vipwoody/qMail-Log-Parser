/**
 * Folder v1.01
 * Log Reader superClass class
 * logReader.java v1.05
 * @author Wadih El-Ghoussoubi(Woody)
 * 
 */

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.io.EOFException;
public class logReader extends Thread{
	/*SCANNER TO READ LOG*/
	protected Scanner scan;
	/*contains fis of log*/
	protected FileInputStream fis;
	/*contains fos off error*/
	protected FileOutputStream errFos;
	/*prints to error log*/
	protected PrintWriter errWriter;
	/*COUTNS # of error*/
	protected long errcntr;
	/*COUNTS # lines*/
	protected long linecntr;
	/*contains filename of log to read from*/
	protected String fileName;
	/*contains name of childs calling readFile*/
	private String readerName;
	/*checks if something was dumped*/
	private boolean dumpSpam=false;
	private boolean dumpQmail=false;
	private boolean dumpSmtpd=false;
	/*keeps the line read and passes it to parseLine*/
	private static String globalLine;
	/*thread*/
	private static Thread thread;
	/*enum for timestamp conversion*/
	public enum month{january,february,march,april,may,june,july,august,september,october,november,december};

	/**
	 * default constructor for logReader
	 * @param f
	 */
	public logReader(String f, String name){
		fileName=f;
		readerName=name;
		/*sets everything*/
		try{		
			errFos=new FileOutputStream(globalClass.log_error,true);
			errWriter=new PrintWriter(errFos,true);
		}
		catch(FileNotFoundException e){
			System.out.println("error file was not found...");
		}

	}
	/**
	 * sets fileName
	 * @param f
	 */
	public void setFileName(String f){
		/*SETS FILENAME*/
		fileName=f;
	}
	/**
	 * 
	 * @return fileName
	 */
	public String getfileName(){
		String temp=fileName;
		return temp;
	}
	/*OVERWRITE THIS METHOD*/
	public void parseLine(String s) throws unknownLineException{}
	/*for use with thread*/
	public void run(){
		try{
			parseLine(globalLine);
		}
		catch(unknownLineException e){
			writeError(linecntr,readerName,false); //line error #, reader index, false for not end
		}
	}
	/**
	 * reads file and process
	 */
	public void readFile(){
		/*Contains log directory*/
		String fName=fileName;
		/*TRIES to set all and read*/
		linecntr=0;
		if (readerName.equals("spamReader"))
			thread=new spamReader();
		else if (readerName.equals("smtpdReader"))
			thread=new smtpdReader();
		else if (readerName.equals("AVReader"))
			thread=new AVReader();
		else if (readerName.equals("qmailReader"))
			thread=new qmailReader();
		else{
			System.out.println("UNKNOWN READER...");
			System.exit(0);
		}
		try{
			
			fis=new FileInputStream(fName);
			scan=new Scanner(fis);
			boolean done=false;
			while(scan.hasNext() && !done){
				/*gets free memory and if it exceeds the max memory allcoated - ask user if he wants to dump and read*/
				if ((Runtime.getRuntime().freeMemory()/1048576)>=Runtime.getRuntime().maxMemory()/1048576)
					if (!confirmSelection(readerName))
						done=true; //breaks loop
					else
						System.out.println("Continuing read of log...");
				//increments linecntr
				linecntr++;
				//gets next line and sents to parseLine
				globalLine=scan.nextLine();
				//calls the thread to parseline
				thread.run();
			}	
		}
		catch(FileNotFoundException e){
			System.out.println("File was not found\texiting...");
			System.exit(0);
		}
		finally{
			writeError(0,readerName,true); //line error #, reader index, true for end
			scan.close();
			errWriter.close();
			//if dumps were stored - re-read everything from dump file
			if (dumpSpam)
				readDump(0);
			if (dumpQmail)
				readDump(1);
			if (dumpSmtpd)
				readDump(2);
		}
	}
	/**
	 * reads the dump files created if memory was exceeded
	 * @param a
	 */
	public void readDump(int a){
		ObjectInputStream ois=null;
		switch (a){
		//spam dump
		case 0:
			//first dump whatever is still in hash
			dumpIt("spam");
			try{
				//open the stream
				ois=new ObjectInputStream(new FileInputStream(globalClass.dump_spam));
				try{
					while (true){
						//reread everything from dump file
						globalClass.hashOfMessageInput.addToHashTable((messageInput)ois.readObject());
					}
				}
				catch(EOFException e){
					System.out.println("Done reading dump!");
				}
				catch(ClassNotFoundException e){
					System.out.println("An error has occured...");
					System.exit(0);
				}
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			break;
		//qmail
		case 1:
			//firt dump everything in qmail hash
			dumpIt("qmail");
			try{
				//open stream 
				ois=new ObjectInputStream(new FileInputStream(globalClass.dump_qmail));
				try{
					while (true){
						//reread everything from qmail dump and re-store
						globalClass.hashOfMessageOutput.addToHashTable((messageOutput)ois.readObject());
					}
				}
				catch(EOFException e){
					System.out.println("Done reading dump!");
				}
				catch(ClassNotFoundException e){
					System.out.println("An error has occured...");
					System.exit(0);
				}
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			break;
		//smtpd
		case 2:
			//first dump smtpd hash
			dumpIt("smtpd");
			try{
				//open stream
				ois=new ObjectInputStream(new FileInputStream(globalClass.dump_smtpd));
				try{
					while (true){
						//reread smtpd dump and store
						globalClass.hashOfSmtpdInputs.addToHashTable((smtpdInput)ois.readObject());
					}
				}
				catch(EOFException e){
					System.out.println("Done reading dump!");
				}
				catch(ClassNotFoundException e){
					System.out.println("An error has occured...");
					System.exit(0);
				}
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			break;
		}
	}
	/**
	 * Confirm selection if user wants to dump to log and continue reading
	 * @param readerName
	 * @return true if continue - false if stop
	 */
	public boolean confirmSelection(String readerN){
		//Sets local variables
		Scanner kb=new Scanner(System.in);
		String ans;		
		//displays warning to user
		System.out.println("WARNING! - CURRENT LOG HAS EXCEEDED HEAP MEMORY ALLOCATED...["+Runtime.getRuntime().freeMemory()/1048576+"]");
		System.out.println("Do you want to continue reading this file...\nWARNING! - THIS OPERATION CAN TAKE SEVERAL " +
				"MINUTES...");
		ans=kb.next().toLowerCase();
		//if user says 'yes' - dump according to readerName and continue reading file
		if (ans.charAt(0)=='y'){
			dumpIt(readerN);
			return true;
		}
		//if user says 'no' - stop reading
		System.out.println("WARNING! - NOT ALL LOG HAS BEEN PARSED!");
		return false;
	}
	/**
	 * dumps to dump log according to readerName
	 * @param readerName
	 */
	public void dumpIt(String rd){
		int cntr=0;
		ObjectOutputStream oos=null;
		System.out.println("Please wait...");
		switch (rd.charAt(3)){
		//spam
		case 'm':
			try{
				//opens stream
				oos=new ObjectOutputStream(new FileOutputStream(globalClass.dump_spam,true));
				//sets local variables
				list<messageInput> tempSp=null;
				messageInput tempo=null;
				//goes through every object in the hash and stores to log
				for (int i=0;i<globalClass.hashOfMessageInput.getSize();i++){
					tempSp=globalClass.hashOfMessageInput.getList(i);
					for (int k=0;k<tempSp.getSize();i++){
						tempo=tempSp.getIt(k);
						oos.writeObject(tempo);
						tempo=null;
					}
					tempSp=null;
				}
				globalClass.hashOfMessageInput=null;
				System.out.println("Please wait for garbage collection...");
				tempSp=null;
				//resets the hash of storage
				globalClass.hashOfMessageInput=new hash<messageInput>(new messageInput());
				dumpSpam=true;
				//calls the garbage collector until memory is free'd up
				while((Runtime.getRuntime().freeMemory()/1048576)>15)
					Runtime.getRuntime().gc();
			}
			catch(FileNotFoundException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			break;
		//smtpd
		case 'p':
			try{
				//opens stream
				oos=new ObjectOutputStream(new FileOutputStream(globalClass.dump_smtpd,true));
				list<smtpdInput> tempSm=null;
				smtpdInput tempoSm=null;
				//goes through every object in the hash and writes to dump log
				for (int i=0;i<globalClass.hashOfSmtpdInputs.getSize();i++){
					tempSm=globalClass.hashOfSmtpdInputs.getList(i);
					for (int k=0;k<tempSm.getSize();k++){
						tempoSm=tempSm.getIt(k);
						oos.writeObject(tempoSm);
						tempoSm=null;
					}
					tempSm=null;
				}
				//resets the hash of storage
				globalClass.hashOfSmtpdInputs=null;
				globalClass.hashOfSmtpdInputs=new hash<smtpdInput>(new smtpdInput());
				dumpSmtpd=true;
				System.out.println("Please wait for garbage collection...");
				//calls garbage collector until memory is free'd up
				while((Runtime.getRuntime().freeMemory()/1048576)>15)
					Runtime.getRuntime().gc();
			}
			catch(FileNotFoundException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			break;
		//qmail
		case 'i':
			try{
				//opens stream
				oos=new ObjectOutputStream(new FileOutputStream(globalClass.dump_qmail,true));
				list<messageOutput> tempSo=null;
				messageOutput tempMo=null;
				//goes through every object of the hash and writes in binary dump
				for (int i=0;i<globalClass.hashOfMessageOutput.getSize();i++){
					tempSo=globalClass.hashOfMessageOutput.getList(i);
					for (int k=0;k<tempSo.getSize();k++){
						tempMo=tempSo.getIt(k);
						oos.writeObject(tempMo);
						tempMo=null;
					}
					tempSo=null;
				}
				//resets hash of storage
				globalClass.hashOfMessageOutput=null;
				globalClass.hashOfMessageOutput=new hash<messageOutput>(new messageOutput());
				tempSo=null;
				dumpQmail=true;
				//calls garbage collector until memory is free'd up
				System.out.println("Please wait for garbage collection...");
				while((Runtime.getRuntime().freeMemory()/1048576)>15)
					Runtime.getRuntime().gc();
			}
			catch(FileNotFoundException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				System.exit(0);
			}
			break;
		}
	}
	/**
	 * writes error to the error log 
	 * @param a - line number where error occured
	 * @param b - reader name
	 * @param t - true if done reading - false if not
	 */
	public void writeError(long a,String b,boolean t){ //b contains reader name
		//if not done reading - just appened to log the error line # and readername and increment cntr
		if (!t){ 
			errWriter.println("Error in log <"+b+"> @ line #"+a);
			errcntr++;
		}
		//if done reading add the summary
		if (t){
			double errper=((double)errcntr/linecntr)*100;
			errWriter.println();
			errWriter.println("---------------------------------");
			errWriter.println("SUMMARY <"+b+">");
			errWriter.println("---------------------------------");
			errWriter.println("Lines read: "+linecntr);
			errWriter.println("Errors found: "+errcntr);
			errWriter.println("Error percentage " + Math.round(errper)+"%");
			errWriter.println("---------------------------------");
			errWriter.println();
		}
	}
	/**
	 * convert timestamp
	 * @param timestamp
	 * @return
	 */
public static String convertTimeStamp(String t){
		//cuts the timestamp into a substring (removing the @40000
		t="0x".concat(t.substring(9,17));
		//decodes the timestamp received with method 'decode' 
		int newTime=Integer.decode(t);
		//creates a calendar instance
		Calendar calendar = Calendar.getInstance();
		//gets day of year from 'newTime'
		int dayOfYear=(int)Math.round(((newTime/86400)%365.2425)+0.5);
		//gets year from 'newTime'
		int year=(int)((newTime/31536000)+1970);
		int monthInt=-1;
		//sets the appropriate values on calendar
		calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
        //gets month from the calendar
        String monthString=(calendar.getTime().toString()).split(" ")[1].toLowerCase();
        //using the enum - give the appropriate value for month
        if(monthString.equals("apr"))monthString="may";
        switch(month.valueOf(monthString)){
	        case january:monthInt=1;
	        	break;
	        case february:monthInt=2;
	        	break;
	        case march:monthInt=3;
	        	break;
	        case april:monthInt=4;
	        	break;
	        case may:monthInt=5;
	        	break;
	        case june:monthInt=6;
	        	break;
	        case july:monthInt=7;
	        	break;
	        case august:monthInt=8;
	        	break;
	        case september:monthInt=9;
	        	break;
	        case october:monthInt=10;
	        	break;
	        case november:monthInt=11;
	        	break;
	        case december:monthInt=12;
	        	break;
	        	
	        }
        //gets day of month with the calendar
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        //gives hours with 'newTime'
        int hours=(newTime/3600)%24;
        //gets minutes with 'newTime'
        int mins=(newTime/60)%60;
        //gets sec with 'newTime'
        int sec=newTime%60;
        //fixed the return timestamp in readable format UTC (d/m/y/h/m/s);
		String newTimeStamp=""+dayOfMonth+"/"+monthInt+"/"+year+"/"+hours+"/"+mins+"/"+sec+"";
		return newTimeStamp;
	}
}
