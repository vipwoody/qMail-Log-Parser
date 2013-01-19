/**
 * Folder v1.04
 * smtpdReader v1.01
 * Parses the log file qmail_smtpd and changes 
 * attributes of the objects of MessageOutput by pid
 * 
 * @author Wadih El-Ghoussoubi(Woody)
 *
 */
/*IMPORTS*/
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
public class smtpdReader extends logReader {
	private queue<smtpdInput> qs=new queue<smtpdInput>();
	/**
	 * default constructor of smtpdReader
	 */
	public smtpdReader(){
		super(globalClass.log_qmailSmtpd,"smtpdReader");
	}
	/**
	 * parameterized constructor for smtpdReader
	 * @param f
	 * 
	 */
	public smtpdReader(String f){
		super(f,"smtpdReader");
	}
	/**
	 * reader function
	 */
	public void parseLine(String a) throws unknownLineException{
		//splits the line read into parts to parse
		String[] all=a.split(" ");
		//gets the timestamp
		String timestamp=all[0];
		//gets the type
		String type=all[1];
		//sets local variables
		long pid=0;
		String ip;
		String whichTcp;
		String dom;
		int status;
		double scr;
		double tme;
		String subj;
		String iFrom;
		smtpdInput tempSmtpd;
		type.toLowerCase();		
		switch (type.charAt(0)){
		/*TCP TYPE*/
		case 't':
			whichTcp=all[2];
			//ignore status case
			if (whichTcp.equals("status:")){ 
				break;
			}
			pid=Long.parseLong(all[3]);
			/*IF NEW TCP START CREATE OBJECT*/
			if(all[2].equals("pid")){
				pid=Long.parseLong(all[3]);
				ip=all[5];
				timestamp=convertTimeStamp(timestamp);
				tempSmtpd=new smtpdInput(pid,timestamp,ip,-1,false,"NOT_SET",null);
				qs.inqueue(tempSmtpd);
			}
			/*SET MISSING TCP INFO*/
			else if(all[2].equals("ok")){
				pid=Long.parseLong(all[3]);
				dom=all[4];
				if (qs.peek()!=null&&qs.peek().getPid()==pid){
					try{
						tempSmtpd=qs.unqueue();
					}
					catch(emptyListException e){
						System.out.println("empty queue");
						throw new unknownLineException("Queue empty!");
					}
					tempSmtpd.setDomain(dom);
					tempSmtpd.setPass(true);
					qs.inqueue(tempSmtpd);
				}
				else {
					tempSmtpd=qs.findByPID(pid);
					if (tempSmtpd!=null){
						tempSmtpd.setDomain(dom);
						tempSmtpd.setPass(true);
					}
					else{
						throw new unknownLineException();
					}
				}
			}
			//END status means dump to hash
			else if(all[2].equals("end")){
				pid=Long.parseLong(all[3]);
				status=Integer.parseInt(all[5]);
					if (qs.peek()!=null && qs.peek().getPid()==pid){
						try{
							tempSmtpd=qs.unqueue();
						}
						catch(emptyListException e){
							
							throw new unknownLineException("Empty list!");
						}					
					}
					else{
						tempSmtpd=qs.findAndDelete(pid);
					}
					if (tempSmtpd!=null){
						tempSmtpd.setStatus(status);
					}
					else
						throw new unknownLineException();
					//dumps everything to hash
					timestampContainer tsc=new timestampContainer(tempSmtpd.getTimestamp(),tempSmtpd);
					//dumps timecontainer to hash
					globalClass.listOfTsSm.addToStart(tsc);
					globalClass.hashOfSmtpdInputs.addToHashTable(tempSmtpd);
				}
			break;
		/*SIMSCAN TYPE*/
		case 's':
			boolean inPeek=true;
			//gets all the simscan info
			String[] simTemp=type.split(":");
			String tempSim=simTemp[1];
			tempSim=tempSim.replace("[","");
			tempSim=tempSim.replace("]","");
			pid=Long.parseLong(tempSim);
			smtpdInput global=new smtpdInput();
			smtpdInput.simScanResult tempSimScanResult;
			smtpdInput.simScanResult tempo2;
			boolean isSpam=false;
			/*SPAMFLAG*/
			if (simTemp[2].equals("PASSTHRU")){
				isSpam=true;
			}
			if (qs.peek()==null) throw new unknownLineException();
			if (qs.peek().getPid()==pid){
				try{
					tempSmtpd=qs.unqueue();
				}
				catch(emptyListException e){
					throw new unknownLineException("Empty list!");
				}
			}
			else{
				tempSmtpd=qs.findByPID(pid);
				inPeek=false;
			}
			tempSimScanResult=global.new simScanResult();
			if (tempSmtpd==null) throw new unknownLineException();
			if(isSpam){
				String tempScore;
				tempScore=all[2].split(":")[0];
				char[] tScore=tempScore.toCharArray();
				tempScore="";
				for (int i=1;i<tScore.length;i++){
					if (tScore[i]!='/')
						tempScore+=tScore[i];
					else
						break;
				}
				scr=Double.parseDouble(tempScore);
				tme=Double.parseDouble(all[2].split(":")[1].replace("s",""));
				subj="";			
				for (int i=3;i<all.length;i++)
					subj+=all[i];
				String[] tempFrom=all[all.length-1].split(":");
				iFrom="";
				for (int i=1;i<simTemp.length;i++)
					iFrom+=tempFrom[i];				
				subj=subj.split(":")[0];
				//edits simscan info of the object with the new result
				tempo2=tempSmtpd.getSimScanResult();
				if (tempo2!=null&&!tempo2.isVirus())
					tempSimScanResult=global.new simScanResult(scr,tme,subj,iFrom,false,"NO_VIRUS!",true);
				else
					if(tempo2!=null)
						tempSimScanResult=global.new simScanResult(scr,tme,subj,iFrom,true,tempo2.getVirusType(),true);
			}
			else{
				tme=Double.parseDouble((simTemp[3].replace("s","")));
				String vType=simTemp[4];
				iFrom=simTemp[5]+" from "+simTemp[6]+" to "+simTemp[7];
				tempo2=tempSmtpd.getSimScanResult();
				if (tempo2!=null&&!tempo2.isSpam())
					tempSimScanResult=global.new simScanResult(-1,tme,"NO_SPAM_FOUND",iFrom,true,vType,false);
				else
					if(tempo2!=null)
						tempSimScanResult=global.new simScanResult(tempo2.getScore(),tme,tempo2.getSubject(),iFrom,true,vType,true);
			}
			tempSmtpd.setSimScanResult(tempSimScanResult);
			if (inPeek)
				qs.inqueue(tempSmtpd);
			break;
		}
	}
}
