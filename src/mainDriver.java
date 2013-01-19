/**
 * Class for the MAIN output program - deals with queries
 * @author Wadih El-Ghoussoubi(Woody)
 *
 */
import java.util.Scanner;
import java.util.Date;
import java.util.InputMismatchException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
public class mainDriver {
	/**
	 * boolean for spam caught
	 */
	static boolean spamCached=false;
	/**
	 * boolean for qmail caught
	 */
	static boolean qmailCached=false;
	/**
	 * boolean for smtpd caught
	 */
	static boolean smtpdCached=false;
	/**
	 * boolean for read smtpd log file
	 */
	static boolean readSmtpdLog=false;
	/**
	 * main
	 * @param args
	 */
	public static void main(String[] args){
		verifyCache();
		displayIntro();
		showMainMenu();
	}
	/**
	 * converts the hash tables to lists
	 */
	public static void convertToList(){
		System.out.println("Please wait...");
		globalClass.listOfM=globalClass.hashOfMessageInput.convertToList();
		globalClass.listOfMo=globalClass.hashOfMessageOutput.convertToList();
		globalClass.listOfSm=globalClass.hashOfSmtpdInputs.convertToList();
	}
	/**
	 * checks if the cache is empty, if not possible loading of the cache
	 */
	public static void verifyCache(){
		Scanner scan=null;
		Scanner kb=new Scanner(System.in);
		String ans;
		String lineSpam="";
		String lineQmail="";
		String lineSmtpd="";
		try{
			scan=new Scanner(new FileInputStream(globalClass.cache_info));
			lineSpam=scan.nextLine();
			lineQmail=scan.nextLine();
			lineSmtpd=scan.nextLine();			
		}
		catch(FileNotFoundException e){
			System.out.println("No cache found!\nLogs will have to be read to continue...");
			System.out.println("Do you want to read the logs...");
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y'){
				//read all
				readFile(0); 
				readFile(1);
				readFile(2);
			}
			else{
				System.out.println("Exiting...");
				System.exit(0);
			}
		}
		if (lineSpam.equals("NOT_SET")){
			System.out.println("Spam reader cache is NOT saved!\n"+
					"Spam logs have to be read before continuing...");
			System.out.println("Do you want to read the spam logs...");
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y'){
				readFile(0); //0=spam
			}
			else{
				System.out.println("Exiting...");
				System.exit(0);
			}
		}
		if (!lineSpam.equals("NOT_SET")){
			System.out.println("Spam logs are saved in cache\n Do you want to read cache..");
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y'){
				readCache(0);//0=spam
			}
			else{
				System.out.println("Reading from log...");
				readFile(0);//spam log
			}
		}
		if (lineQmail.equals("NOT_SET")){
			System.out.println("Qmail reader cache is NOT saved!\n"+
					"Qmail logs have to be read before continuing...");
			System.out.println("Do you want to read the qmail logs...");
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y'){
				readFile(1); //1=spam
			}
			else{
				System.out.println("Exiting...");
				System.exit(0);
			}
		}
		if (!lineQmail.equals("NOT_SET")){
			System.out.println("Qmail logs are saved in cache\n Do you want to read cache..");
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y'){
				readCache(1);//0=qmail
			}
			else{
				System.out.println("Reading from log...");
				readFile(1);//qmail log
			}
		}
		if (lineSmtpd.equals("NOT_SET")){
			System.out.println("Smtpd reader cache is NOT saved!\n"+
					"Smtpd logs have to be read before continuing...");
			System.out.println("Do you want to read the smtpd logs...");
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y'){
				readSmtpdLog=true;
				readFile(2); //1=stmpd
			}
			else{
				System.out.println("Exiting...");
				System.exit(0);
			}
		}
		if (!lineSmtpd.equals("NOT_SET")){
			System.out.println("Smtpd logs are saved in cache\n Do you want to read cache..");
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y'){
				readCache(2);//0=stmpd
			}
			else{
				readSmtpdLog=true;
				System.out.println("Reading from log...");
				readFile(2);//smtpd log
			}
		}
			scan.close();
			if (spamCached)
				fixCacheInfo(0);
			if (qmailCached)
				fixCacheInfo(1);
			if (smtpdCached)
				fixCacheInfo(2);
			else
				convertToList();
	}
		/**
		 * reads from the cache
		 * @param a
		 */
	public static void readCache(int a){
		ObjectInputStream ois=null;
		int cntr=0;
		switch (a){
		//spam
		case 0:
			messageInput tempMi=null;
			System.out.println("Attempting to read file...");
			try{
				ois=new ObjectInputStream(new FileInputStream(globalClass.cache_spam));
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			try{
				while(true){						
						tempMi=(messageInput)ois.readObject();
						globalClass.hashOfMessageInput.addToHashTable(tempMi);
						cntr++;
				}
			}
			catch(EOFException e){
				System.out.println();
				System.out.println("Successfully saved "+cntr+" objects from cache!");
			}
			catch(ClassNotFoundException e){
				System.out.println("An error has occured while reading the file..");
				resetCacheFile();
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured while reading the file..");
				resetCacheFile();
				System.exit(0);
			}
			
			break;
		//qmail
		case 1:
			messageOutput tempMo=null;
			System.out.println("Attempting to read file...");
			try{
				ois=new ObjectInputStream(new FileInputStream(globalClass.cache_qmail));
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			try{
				while(true){
						tempMo=(messageOutput)ois.readObject();
						globalClass.hashOfMessageOutput.addToHashTable(tempMo);
						cntr++;
				}
			}
			catch(EOFException e){
				System.out.println();
				System.out.println("Successfully saved "+cntr+" objects from cache!");
			}
			catch(ClassNotFoundException e){
				System.out.println("An error has occured while reading the file..");
				resetCacheFile();
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured while reading the file..");
				resetCacheFile();
				System.exit(0);
			}
			break;
		//smtpd
		case 2:
			smtpdInput tempSm=null;
			System.out.println("Attempting to read file...");
			try{
				ois=new ObjectInputStream(new FileInputStream(globalClass.cache_smtpd));
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			try{
				while(true){
						tempSm=(smtpdInput)ois.readObject();
						globalClass.hashOfSmtpdInputs.addToHashTable(tempSm);
						cntr++;
				}
			}
			catch(EOFException e){
				System.out.println();
				System.out.println("Successfully saved "+cntr+" objects from cache!");
			}
			catch(ClassNotFoundException e){
				System.out.println("An error has occured while reading the file..");
				resetCacheFile();
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured while reading the file..");
				resetCacheFile();
				System.exit(0);
			}
			break;
		}
		try{
			ois.close();
		}
		catch(IOException e){
			System.out.println("An error has occured while reading the file..");
			resetCacheFile();
			System.exit(0);
		}
	}
	/**
	 * reads from the log file
	 * @param a
	 */
	public static void readFile(int a){
		String ans;
		Scanner kb=new Scanner(System.in);
		switch (a){
		case 0:
			System.out.println("Please wait...\nAttempting to read spam log files...");
			spamReader sr=new spamReader();
			sr.readFile();
			convertToList();
			System.out.println("Spam logs have been successfully read!\nDo you want to save to cache?");
			kb=new Scanner(System.in);
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y')
				saveToCache(0);//0=spam
			break;
		case 1:
			System.out.println("Please wait...\nAttempting to read qmail log files...");
			qmailReader qr=new qmailReader();
			qr.readFile();
			convertToList();
			System.out.println("qmail logs have been successfully read!\nDo you want to save to cache?");
			kb=new Scanner(System.in);
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y')
				saveToCache(1);//1=qmail
			break;
		case 2:
			System.out.println("Please wait...\nAttempting to read smtpd log files...");
			smtpdReader smr=new smtpdReader();
			smr.readFile();
			convertToList();
			System.out.println("smtpd logs have been successfully read!\nDo you want to save to cache?");
			kb=new Scanner(System.in);
			ans=kb.nextLine().toLowerCase();
			if (ans.charAt(0)=='y')
				saveToCache(2);//2=smtpd
			break;
		}
	}
	/**
	 * saves to cache after reading from log file
	 * @param a
	 */
	public static void saveToCache(int a){
		ObjectOutputStream oos=null;
		int cntr=0;
		switch(a){
		//spam
		case 0:
			System.out.println("Attempting to write to cache...");
			list<messageInput> tempMi=globalClass.listOfM;
			try{				
				oos=new ObjectOutputStream(new FileOutputStream(globalClass.cache_spam));
				for (int i=0;i<tempMi.getSize();i++){
					oos.writeObject(tempMi.getIt(i));			
				}
				spamCached=true;
				System.out.println();
				System.out.println("Successfully saved spam to cache!");
			}
			catch(FileNotFoundException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			break;
		//qmail
		case 1:
			list<messageOutput> tempMo=globalClass.listOfMo;
			try{
				System.out.println("Attempting to write to cache...");
				oos=new ObjectOutputStream(new FileOutputStream(globalClass.cache_qmail));
				for (int i=0;i<tempMo.getSize();i++){
					oos.writeObject(tempMo.getIt(i));
				}
				qmailCached=true;
				System.out.println();
				System.out.println("Successfully saved qmail to cache!");
			}
			catch(FileNotFoundException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			break;
		//smtpd
		case 2:
			list<smtpdInput> tempSm=globalClass.listOfSm;
			try{
				System.out.println("Attempting to write to cache...");
				oos=new ObjectOutputStream(new FileOutputStream(globalClass.cache_smtpd));
				for (int i=0;i<tempSm.getSize();i++){
					oos.writeObject(tempSm.getIt(i));
				}
				smtpdCached=true;
				System.out.println();
				System.out.println("Successfully saved smtpd to cache!");
			}
			catch(FileNotFoundException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			catch(IOException e){
				System.out.println("An error has occured..");
				resetCacheFile();
				System.exit(0);
			}
			break;
		}
		try{
			oos.close();
		}
		catch(IOException e){
			System.out.println("An error as occured...");
			resetCacheFile();
			System.exit(0);
		}
	}
	/**
	 * when done saving to cache, write set
	 * @param a
	 */
	public static void fixCacheInfo(int a){
		String forSpam="";
		String forQmail="";
		String forSmtpd="";
		Scanner scan=null;
		PrintWriter pw=null;
		try{
			scan=new Scanner(new FileInputStream(globalClass.cache_info));
			forSpam=scan.nextLine();
			forQmail=scan.nextLine();
			forSmtpd=scan.nextLine();
			scan.close();
			pw=new PrintWriter(new FileOutputStream(globalClass.cache_info));
		}
		catch(FileNotFoundException e){
			System.out.println("An error has occured..");
			resetCacheFile();
			System.exit(0);
		}
		switch (a){
		case 0:
			pw.println("SET");
			pw.println(forQmail);
			pw.println(forSmtpd);
			break;
		case 1:
			pw.println(forSpam);
			pw.println("SET");
			pw.println(forSmtpd);
			break;
		case 2:
			pw.println(forSpam);
			pw.println(forQmail);
			pw.println("SET");
		}
		pw.close();
	}
	/**
	 * resets the cache files to empty
	 */
	public static void resetCacheFile(){
		PrintWriter pw=null;
		try{
			pw=new PrintWriter(new FileOutputStream(globalClass.cache_info));
			for (int i=0;i<3;i++)
				pw.println("NOT_SET");
			pw.close();
			System.out.println("Cache info has been reset...");			
		}
		catch(FileNotFoundException e){
			System.out.println("An error has occured..");
			System.exit(0);
		}
		
	}
	/**
	 * displays the main menu
	 */
	public static void showMainMenu(){
		Scanner kb=new Scanner(System.in);
		int sel=0;
		boolean done=false;
		while (!done){
			try{
				System.out.println("--------------------------\n" +
								   "       Main Menu          \n" +
								   "--------------------------\n"+
								   "1. spamReader menu\n"+
								   "2. smtpdReader menu\n"+
								   "3. qmailReader menu\n"+
								   "4. Exit");
				sel=kb.nextInt();
				done=true;
			}
			catch(InputMismatchException e){
				System.out.println("Invalid input!");
				kb.nextLine();
				done=false;
			}
		}
		switch (sel){
		case 1:
			showSpamMenu();
			break;
		case 2:
			showSmtpdMenu();
			break;
		case 3:
			showQmailMenu();
			break;
		case 4:
			System.out.println("exiting...");
			System.exit(0);
		}
	}
	/**
	 * shows the spam menu
	 */
	public static void showSpamMenu(){
		Scanner kb=new Scanner(System.in);
		int sel=0;
		list<messageInput> temp;
		boolean done=false;
		while (!done){
			try{
				System.out.println("--------------------------\n" +
								   "       Spam Menu          \n" +
								   "--------------------------\n"+
								   "1. Display total spam messages\n"+
								   "2. Average score of all messages\n"+
								   "3. Average time of all messages\n"+
								   "4. Ranking of top domain spammers\n"+
								   "5. Verify spam by domain\n"+
								   "6. Main menu");
				sel=kb.nextInt();
				done=true;
			}
			catch(InputMismatchException e){
				System.out.println("Invalid input!");
				kb.nextLine();
				done=false;
			}
		}
		switch(sel){
		case 1:
			long cntr=0;
			temp=globalClass.listOfM;
			cntr=temp.getSize();
			System.out.println("TOTAL spamMessages found: "+cntr);
			break;
		case 2:
			double avgscore=0;
			double temp2;
			double maxScore=0;
			messageInput mostDangerous=null;
			messageInput temp3;
			temp=globalClass.listOfM;
			for (int i=0;i<temp.getSize();i++){
				temp3=temp.getIt(i);
				temp2=temp3.getSpamResult().getScore();
				if (temp2>maxScore){
					maxScore=temp2;
					mostDangerous=temp3;
				}
				avgscore+=temp2;
			}
			avgscore=avgscore/(temp.getSize());
			avgscore=Math.round(avgscore);
			System.out.println("Average score of all messages is: "+avgscore+"/5");
			System.out.println("Most dangerous spam message found...\n"+
								"------------------------------------\n"+mostDangerous.getSpamResult());
			break;
		case 3:
			double avgtime=0;
			temp=globalClass.listOfM;
			for (int i=0;i<temp.getSize();i++){
				avgtime+=temp.getIt(i).getSpamResult().getTimeTook();
			}
			avgtime=avgtime/(temp.getSize());
			avgtime=Math.round(avgtime);
			System.out.println("Average time of all messages is: "+avgtime+"s");
			break;
		case 4:
			hash<domainRank> dr=new hash<domainRank>(new domainRank());
			temp=globalClass.listOfM;
			domainRank tempdr=null;
			domainRank tempdr2=null;
			String dom;
			String temp4;
			messageInput temp1;
			for (int i=0;i<temp.getSize();i++){
				temp1=temp.getIt(i);
				temp4=temp1.getSpamResult().getMid();
				if (temp4.contains("@")){
					dom=temp4.split("@")[1];
					dom=dom.replace(">","");
					tempdr2=new domainRank(0,dom);
					tempdr=dr.findIt(tempdr2);
					if (tempdr!=null)
						tempdr.incrementCounter();
					else{
						tempdr=new domainRank(1,dom);
						dr.addToHashTable(tempdr);
					}
				}
			}
			
			list<domainRank>ldr=dr.convertToList();
			list<domainRank> sortedList=ldr.sort();
			System.out.println("Top spammers by domain:");
			for (int i=0;i<sortedList.getSize();i++){
				System.out.println("#"+(i+1)+" "+sortedList.getIt(i).getDomain());
			}
		
			break;
		case 5:
			dr=new hash<domainRank>(new domainRank());
			temp=globalClass.listOfM;
			tempdr=null;
			tempdr2=null;
			dom="";
			temp4="";
			temp1=null;
			for (int i=0;i<temp.getSize();i++){
				temp1=temp.getIt(i);
				temp4=temp1.getSpamResult().getMid();
				if (temp4.contains("@")){
					dom=temp4.split("@")[1];
					dom=dom.replace(">","");
					tempdr2=new domainRank(0,dom);
					tempdr=dr.findIt(tempdr2);
					if (tempdr!=null)
						tempdr.incrementCounter();
					else{
						tempdr=new domainRank(1,dom);
						dr.addToHashTable(tempdr);
					}
				}
			}
			kb=new Scanner(System.in);
			System.out.println("Please input domain name to look for...");
			String inp=kb.nextLine();
			tempdr2=new domainRank(0,inp);
			tempdr=dr.findIt(tempdr2);
			if (tempdr==null)System.out.println("No domain found!");else System.out.println("FOUND "+tempdr.getCounter()+" times");
			break;
		case 6:
			showMainMenu();
			break;
		}
		showSpamMenu();
	}
	/**
	 * when reading from cache, the timestamp will also be added to the list of timestamp containers
	 */
	public static void fixTimeStamp(){
		list<smtpdInput> temp=globalClass.listOfSm;
		smtpdInput tempo=null;
		timestampContainer tsc=null;
		for (int i=0;i<temp.getSize();i++){
			tempo=temp.getIt(i);
			tsc=new timestampContainer(tempo.getTimestamp(),tempo);
			globalClass.listOfTsSm.addToStart(tsc);
		}
		tsc=null;
	}
	/**
	 * shows the smtpd menu
	 */
	public static void showSmtpdMenu(){
		Scanner kb=new Scanner(System.in);
		int sel=0;
		boolean done=false;
		while (!done){
			try{
				System.out.println("--------------------------\n" +
								   "       smtpd Menu          \n" +
								   "--------------------------\n"+
								   "1. Get all messages created within timestamp range\n"+
								   "2. Check all messages with simscan flag\n"+
								   "3. Get rank of ips with most simscan flags\n"+
								   "4. Main menu");
				sel=kb.nextInt();
				done=true;
			}
			catch(InputMismatchException e){
				System.out.println("Invalid input!");
				kb.nextLine();
				done=false;
			}
		}
		kb.nextLine();
		String startts="";
		String endts="";
		switch(sel){
		//all messages in range 
		case 1:
			boolean good=false;
			if (!readSmtpdLog)
				fixTimeStamp();
			System.out.println(globalClass.listOfTsSm.getIt(2).getTimestamp());
			while (!good){
				try{
					System.out.println("Please input starting time...(d/m/y/h/m)");
					startts=kb.nextLine();
					if (!startts.matches("\\d+[/]{1}\\d+[/]{1}\\d+[/]{1}\\d+[/]{1}\\d+"))
						throw new InputMismatchException();
					good=true;
				}
				catch(InputMismatchException e){
					System.out.println("Invalid input [d/m/y/h/m]");
				}
			}
			good=false;
			while(!good){
				try{
					System.out.println("Please input end time...(d/m/y/h/m)");
					endts=kb.nextLine();
					if (!endts.matches("\\d+[/]{1}\\d+[/]{1}\\d+[/]{1}\\d+[/]{1}\\d+"))
						throw new InputMismatchException();
					good=true;
				}
				catch(InputMismatchException e){
					System.out.println("Invalid input [d/m/y/h/m]");
				}
			}
			smtpdInput smTemp=null;
			list<timestampContainer> tempts=getRange(startts,endts,0); //0=smtpd 1=spam 2=qmail			
			for (int i=0;i<tempts.getSize();i++){
				smTemp=(smtpdInput)tempts.getIt(i).getPointer();
				System.out.println(smTemp.showInfo());
			}
			smTemp=null;
			startts=null;
			tempts=null;
			kb=null;
			break;
		//all simscan flagged messages
		case 2:
			kb=new Scanner(System.in);
			System.out.println("Please wait while information is retrieved....");
			list<smtpdInput> tempList=globalClass.listOfSm;
			int cntr=0;
			System.out.println("Found "+tempList.getSize()+" messages with POSSIBLE simscan flag\n"+
					"How many messages do you want to display...");
			try{
				cntr=kb.nextInt();
			}
			catch(InputMismatchException e){
				System.out.println("Invalid input!");
			}
			smtpdInput temp=null;
			int found=0;
			for (int i=0;i<tempList.getSize();i++){
				temp=tempList.getIt(i);
				if (temp.getSimScanResult()!=null && (temp.getSimScanResult().isSpam() || temp.getSimScanResult().isVirus())){
					System.out.println(temp.showInfo());
					found++;
				}
				if (cntr==found)
					break;
			}
			temp=null;
			tempList=null;
			kb=null;
			
			break;
		//rank of ips
		case 3:
			System.out.println("Please wait while info is retrieved...");
			list<smtpdInput> temper;
			hash<domainRank> dr=new hash<domainRank>(new domainRank());
			temper=globalClass.listOfSm;
			domainRank tempdr=null;
			domainRank tempdr2=null;
			String dom="";
			String temp4;
			smtpdInput temp1;
			for (int i=0;i<temper.getSize();i++){
				if (temper.getIt(i).getSimScanResult().getIpFrom()!=null){
					temp1=temper.getIt(i);
					temp4=temp1.getSimScanResult().getIpFrom();
					tempdr2=new domainRank(0,temp4);
					tempdr=dr.findIt(tempdr2);
					if (tempdr!=null){
						tempdr.incrementCounter();
					}
					else{
						tempdr=new domainRank(1,temp4);
						dr.addToHashTable(tempdr);
					}
				}
			}
			
			list<domainRank>ldr=dr.convertToList();
			list<domainRank> sortedList=ldr.sort();
			
			System.out.println("Top simscan flag by ip:");
			for (int i=0;i<sortedList.getSize();i++){
				System.out.println("#"+(i+1)+" "+sortedList.getIt(i).getDomain() +" found "+sortedList.getIt(i).getCounter());
			}
			break;
		//go to menu
		case 4:
			showMainMenu();
			break;
		}
		showSmtpdMenu();
	}
	@SuppressWarnings({ "deprecation", "deprecation" })
	/**
	 * returns the list of timestamp containers within a range
	 */
	public static list<timestampContainer> getRange(String start,String end, int a){
		timestampContainer[] arrTs=null;
		switch (a){
		case 0:
			arrTs=new timestampContainer[globalClass.listOfTsSm.getSize()];
			for (int i=0;i<arrTs.length;i++){
				arrTs[i]=globalClass.listOfTsSm.getIt(i);
			}
			break;
		case 1:
			arrTs=new timestampContainer[globalClass.listOfTsMi.getSize()];
			for (int i=0;i<arrTs.length;i++){
				arrTs[i]=globalClass.listOfTsMi.getIt(i);
			}
			break;
		case 2:
			arrTs=new timestampContainer[globalClass.listOfTsMo.getSize()];
			for (int i=0;i<arrTs.length;i++){
				arrTs[i]=globalClass.listOfTsMo.getIt(i);
			}
			break;
		}
		
		list<timestampContainer> temp=new list<timestampContainer>();
		String[] arrStart=start.split("/");
		String[] arrEnd=end.split("/");
		Date startDt=new Date(Integer.parseInt(arrStart[2]),Integer.parseInt(arrStart[1]),Integer.parseInt(arrStart[0]),Integer.parseInt(arrStart[3]),Integer.parseInt(arrStart[4]));
		Date endDt=new Date(Integer.parseInt(arrEnd[2]),Integer.parseInt(arrEnd[1]),Integer.parseInt(arrEnd[0]),Integer.parseInt(arrEnd[3]),Integer.parseInt(arrEnd[4]));
		int cntr=binarySearch(0,arrTs.length,startDt,arrTs);
		System.out.println(cntr);
		if (cntr!=-1){
			while(arrTs[cntr].getTimestamp().getTime()>endDt.getTime() && cntr<arrTs.length-1){
				temp.addToStart(arrTs[cntr]);
				cntr++;
			}
		}
		return temp;
	}
	/**
	 * binary search for the array of timestamps
	 * @param startIndex
	 * @param endIndex
	 * @param val
	 * @param arrTs
	 * @return index in the array
	 */
	public static int binarySearch(int startIndex, int endIndex, Date val, timestampContainer[] arrTs){
		Date temp=arrTs[(startIndex+endIndex)/2].getTimestamp();
		if (startIndex>endIndex)
			return (startIndex+endIndex)/2;
		else if (temp.equals(val))
			return (startIndex+endIndex)/2;
		else if(temp.getTime()<val.getTime())
			return binarySearch(startIndex,((startIndex+endIndex)/2)-1,val,arrTs);
		else if(temp.getTime()>val.getTime())
			return binarySearch(((startIndex+endIndex)/2)+1,endIndex-1,val,arrTs);
		else
			return -1;
		
	}
	/**
	 * shows the qmail menu
	 */
	public static void showQmailMenu(){
		Scanner kb=new Scanner(System.in);
		int sel=0;
		boolean done=false;
		while (!done){
			try{
				System.out.println("--------------------------\n" +
								   "       qmail Menu          \n" +
								   "--------------------------\n"+
								   "1. Get all messages deliveries to specific user\n"+
								   "2. Rank top emails\n"+
								   "3. Main menu");
				sel=kb.nextInt();
				done=true;
			}
			catch(InputMismatchException e){
				System.out.println("Invalid input!");
				kb.nextLine();
				done=false;
			}
		}
		kb.nextLine();
		String startts="";
		String endts="";
		switch(sel){
		//all messages by email 
		case 1:
			boolean good=false;
			while (!good){
				try{
					System.out.println("neighbors@frontporchforum.com");
					System.out.println("Please input a valid email address...user@domain.xyz");
					startts=kb.nextLine();
					if (!startts.matches("\\w+[@]{1}\\w+[.]{1}\\w{3}"))
						throw new InputMismatchException();
					good=true;
				}
				catch(InputMismatchException e){
					System.out.println("Invalid input [user@domain.xyz]");
				}
			}
			list<messageOutput> tempMo=globalClass.listOfMo;
			messageOutput tempo=null;
			String tempEmail="";
			int cntr=0;
			for (int i=0;i<tempMo.getSize();i++){
				tempo=tempMo.getIt(i);
				tempEmail=tempo.getCInfo().getEmailAddress().replace("<","");
				tempEmail=tempEmail.replace(">","");
				if (tempEmail.equals(startts)){
					cntr++;
					System.out.println("Found delivery to <"+startts+">");
					System.out.println(tempo.showInfo());
				}
			}
			System.out.println("Total number of emails found: "+ cntr);
			break;
		//all simscan flagged messages
		case 2:
			System.out.println("Please wait while info is retrieved...");
			list<messageOutput> temper;
			hash<domainRank> dr=new hash<domainRank>(new domainRank());
			temper=globalClass.listOfMo;
			domainRank tempdr=null;
			domainRank tempdr2=null;
			String dom="";
			String temp4;
			messageOutput temp1;
			for (int i=0;i<temper.getSize();i++){
					temp1=temper.getIt(i);
					temp4=temp1.getCInfo().getEmailAddress();
					tempdr2=new domainRank(0,temp4);
					tempdr=dr.findIt(tempdr2);
					if (tempdr!=null){
						tempdr.incrementCounter();
					}
					else{
						tempdr=new domainRank(1,temp4);
						dr.addToHashTable(tempdr);
					}
				}
			list<domainRank>ldr=dr.convertToList();
			list<domainRank> sortedList=ldr.sort();
			
			System.out.println("Top email:");
			for (int i=0;i<15;i++){
				System.out.println("#"+(i+1)+" "+sortedList.getIt(i).getDomain() +" found "+sortedList.getIt(i).getCounter());
			}
			break;
		//rank of ips
		case 3:
			showMainMenu();
			break;
		}
		showQmailMenu();
	}
	/**
	 * displays the intro menu
	 */
	public static void displayIntro(){
		System.out.println("------------------------------------------------------");
		System.out.println("                  Qmail LOG PARSER                    ");
		System.out.println("                        v1.08                         ");
		System.out.println("------------------------------------------------------");
		System.out.println("\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
	}
}