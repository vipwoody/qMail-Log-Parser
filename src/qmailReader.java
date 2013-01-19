
/**
 * 
 * @author Wadih El-Ghoussoubi(Woody)
 * v1.00
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class qmailReader extends logReader {
	String[] st;//this is for line parsing
	queue<messageOutput.deliveryInfo> dInfoQueue=new queue<messageOutput.deliveryInfo>();
	list<messageOutput> li=new list<messageOutput>();//list for a stack/queue
	messageOutput global=new messageOutput();
	private int lineCTR, errCTR;
	public static int ctr;
	
	/**
	 * default constructor qmailReader
	 */
	public qmailReader(){
		super(globalClass.log_qmailSend,"qmailReader");
		}
	/**
	 * 
	 * @param f
	 */
	public qmailReader(String f)throws FileNotFoundException{
		super(f,"qmailSendReader");
		}
	/**
	 * readFile method reads the fileName
	 */
	public void readFile(){
		try{
			fis=new FileInputStream(globalClass.log_qmailSend);
			scan=new Scanner((fis));
			String line;
			String nxt,time;
			line=scan.nextLine();
			
			while(scan.hasNextLine()){//keep reading till file is done
				linecntr++;
				st=parseLiner(line);
				time=st[0];//get the timestamp
				nxt=st[1];//get the following string
				line=scan.nextLine();
				try{
					if(nxt.equals("new")){//if the next string says new then you readNew
						readNew(scan,time);//readNew method which would read the all the lines that start with new. Start timestamp is also passed to be stored
					}else if(nxt.equals("info")){//same as above for the rest
						readInfo(scan);
					}else if(nxt.equals("starting")){
						readStarting(scan);
					}else if(nxt.equals("status:")){
						
					}else if(nxt.equals("delivery")){
						readDelivery(scan);
					}else if(nxt.equals("end")){
						readEnd(scan,time);//end timestamp is passed to be stored
					}else if(nxt.equals("status")){throw new unknownLineException();
					}
					else{
						throw new unknownLineException();
					}
				}
				catch(unknownLineException e){
					writeError((int)linecntr,"qmailReader",false);
				}
			}
		}
		catch(FileNotFoundException e){// if bad directory then just exit
			System.out.println("Sorry, the file qmail-send is not available...");
			System.out.println("Program will now terminate...");
			System.exit(0);
		}
		finally{
			writeError(0,"qmailReader",true);
			scan.close();
			errWriter.close();
		}
	}
	public void readNew(Scanner scan,String time){
		//System.out.println("In readNew");
		long msgID=Long.parseLong(st[3]);
		//System.out.println("msgID="+msgID +" Time="+time);
		messageOutput created=new messageOutput(); 
		time=convertTimeStamp(time);
		//System.out.println("msgID="+msgID +" Time="+time);
		//System.out.println(convertTimeStamp(time));
		
		created.setUniqueID(msgID);
		created.setSTS(time);
		li.addToStart(created);
		
		
	}
	public void readInfo(Scanner scan)throws unknownLineException{
		//System.out.println("in readInfo:\n");
		String mid=st[3];
		long msgID=Long.parseLong(mid.replace(":",""));
		int size;
		String email;
		int qp;
		long uid;
		size=Integer.parseInt(st[5]);
		//to save the email without <> uncomment the next code
		email=st[7]/* 	 .replace("<", "").replace(">","")  	*/;
		qp=Integer.parseInt(st[9]);
		uid=Long.parseLong(st[11]);
		messageOutput MO=li.findByPID(msgID);
		if(MO!=null){
			MO.getCInfo().setSize(size);
			MO.getCInfo().setEmail(email);
			MO.getCInfo().setQP(qp);
			MO.setUID(uid);
		}else{
			throw new unknownLineException();
		}
		
		
	}
	public void readStarting(Scanner scan)throws unknownLineException{
		long DID, MID;
		String e;
		String[] split;
		String receiver, email;
		DID=Long.parseLong(st[3].replace(":",""));
		MID=Long.parseLong(st[5]);
		receiver=st[7];
		e=st[8];
		if(e.contains("-")){
			split=e.split("-");
			email=split[1];
		}else{
			email=e;
		}
		messageOutput MO=li.findByPID(MID);
		if(MO!=null){
			boolean t=false;
			if (receiver.equals("local")) t=true;
			messageOutput.deliveryInfo dInfoTemp=global.new deliveryInfo(false,email,t,DID,"");
			MO.addDInfo(dInfoTemp);
			dInfoQueue.inqueue(dInfoTemp);
			
		}else{
			throw new unknownLineException();
		}
	}
	public void readDelivery(Scanner scan)throws unknownLineException{
		long DID;
		String success,endMSG;
		DID=Long.parseLong(st[2].replace(":",""));
		success=st[3].replace(":","");	
		endMSG=st[4];
		messageOutput.deliveryInfo dInfoTempo=null;
		
		dInfoTempo=dInfoQueue.findAndDelete(DID);
		if(dInfoTempo!=null){
			dInfoTempo.setSuccess(success);
			dInfoTempo.setEndMSG(endMSG);
		}else{
			throw new unknownLineException();
		}
	}
	
	public void readEnd(Scanner scan, String time)throws unknownLineException{
		long MID;
		MID=Long.parseLong(st[3]);
		messageOutput MO=li.findByPID(MID);
		if(MO!=null){
			MO.setETS(time);
		}else{
			throw new unknownLineException();
		}
		timestampContainer tsc=new timestampContainer(MO.getCreationTimestamp(),MO);
		globalClass.listOfTsMo.addToStart(tsc);
		globalClass.hashOfMessageOutput.addToHashTable(MO);
		try {
			li.deleteFromIndex(li.getIndex(MID));// this is O(n^2) but it doesnt matter because we will only have 3-4 objects of messageOutput...
		} catch (outOfBoundsException e) {}
		//System.out.println("MID= "+MID+ " time="+time);
		
	}
	public String[] parseLiner(String s){
		String[] st=s.split(" ");
		return st;
	}
	

	
	/**
	 * 
	 * @param uniqueID
	 * @return messageOutput
	 */


	//use queue for pop and push pid for the DID
	//findMofromUID method
	//fix the last line not reading... read line split to array of strings read it
	
	}