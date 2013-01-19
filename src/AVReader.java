/**
 * Reads clamd log file and parses the information
 * Folder v1.05
 * AV Reader class
 * AVReader.java v1.00
 * @author Peter Rockwell
 */

/*IMPORTS*/
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.File;

public class AVReader extends logReader 
{
	//List for clamconfig sets
	private list<clamConfig> configList=new list<clamConfig>();
	//create first clamConfig set
	private clamConfig clamConfigSet = new clamConfig();
	
	//List of messages from clam file 
	private list<clamMessage> messageList = new list<clamMessage>();
	
	/**
	 * default constructor for AVReader
	 */
	public AVReader(){
		super(globalClass.log_virus,"AVReader");
	}
	/**
	 * parameterized AVReader constructor 
	 * @param inputFile the clamd log file
	 */
	public AVReader(String inputFile){
		super(inputFile,"AVReader");
	}
	
	private String[] lineSplit(String inputString)
	{
		/*SPLITS THE LINE PASSED AS A ARRAY OF STRINGS AT EVERY SPACE IN LINE*/
		String[] newLine=inputString.split(" ");
		return newLine;
	}
	
	/**
	 * parses the given line and saves it to data types
	 * @param inputLine line from clamd file
	 */
	public void parseLine(String inputLine) throws unknownLineException
	{
		//time stamp
		String timeStamp;
		//array of words in the read line
		String[] lineArray;
		
		//split line into array
		lineArray = lineSplit(inputLine);
		//time stamp is first word
		timeStamp = lineArray[0];
		
		if (timeStamp.charAt(0) != '@')
		{
			throw new unknownLineException();
		}
		
		//VIRUS FOUND
		if (lineArray[3].equals("FOUND"))
		{
			messageInput virusMessage=new messageInput();
			messageInput.virusResultInfo vInfo = virusMessage.new virusResultInfo();
			
			String folderID = lineArray[1];
			String virusType = lineArray[2];
			
			if (folderID !=null && virusType != null)
			{
				//Send information to virusResult
				vInfo.setTimestamp(timeStamp);
				vInfo.setFolderID(folderID);
				vInfo.setVirusType(virusType);
				vInfo.setVirus(true);
				virusMessage.setVirusResult(vInfo);
				globalClass.hashOfMessageInput.addToHashTable(vInfo);
			}
		}
		
		//selfcheck database
		else if (lineArray[1].equalsIgnoreCase("selfcheck:"))
		{
			/** Sends information to clamMessage - needs a new class in order to hold useful information **/
			//Database is ok
			if(lineArray[4].equals("ok.")){
				createClamMessage(timeStamp, inputLine);
			}
			
			//Database is being reloaded
			else
			{
				createClamMessage(timeStamp, inputLine);
			}
		}
		
		//Database Warning message (3 lines)
		else if (lineArray[2].equalsIgnoreCase("warning:"))
		{
			if(lineArray.length>4&&lineArray[4] !=null)
			{
				createClamMessage(timeStamp, inputLine);
			}
		}
		//LIMITS
		else if (lineArray[1].equalsIgnoreCase("limits:"))
		{
			if (lineArray[2].equalsIgnoreCase("global")) clamConfigSet.setGlobalSizeLimit(Integer.parseInt(lineArray[7]));
			else if (lineArray[2].equalsIgnoreCase("file")) clamConfigSet.setFileSizeLimit(Integer.parseInt(lineArray[7]));
			else if (lineArray[2].equalsIgnoreCase("recursion")){
				//Remove ending period from string
				String str = removePeriod(lineArray[7]);
				clamConfigSet.setRecursionLevelLimit(Integer.parseInt(str));
			}
			else if (lineArray[2].equalsIgnoreCase("files")){
				String str = removePeriod(lineArray[6]);
				clamConfigSet.setFilesLimit(Integer.parseInt(str));
			}
			else if (lineArray[2].equalsIgnoreCase("core-dump")){
				String str = removePeriod(lineArray[5]);
				clamConfigSet.setCoreDumpLimit(Integer.parseInt(str));
			}
		}
		//CONFIG
		else if (lineArray[1].equalsIgnoreCase("archive")) clamConfigSet.setArchiveSupport(true);
		else if (lineArray[1].equalsIgnoreCase("algorithmic")) clamConfigSet.setAlgorithmicDetection(true);
		else if (lineArray[1].equalsIgnoreCase("portable")) clamConfigSet.setPortableExecutableSupport(true);
		else if (lineArray[1].equalsIgnoreCase("elf")) clamConfigSet.setElfSupport(true);
		else if (lineArray[1].equalsIgnoreCase("mail")) clamConfigSet.setMailFilesSupport(true);
		else if (lineArray[1].equalsIgnoreCase("ole2")) clamConfigSet.setOle2Support(true);
		else if (lineArray[1].equalsIgnoreCase("pdf")) clamConfigSet.setPdfSupport(true);
		else if (lineArray[1].equalsIgnoreCase("html")) clamConfigSet.setHtmlSupport(true);
		else if (lineArray[1].equalsIgnoreCase("listening")) clamConfigSet.setListeningDaemonPID(Integer.parseInt(lineArray[4]));
		else if (lineArray[1].equalsIgnoreCase("maxqueue")) clamConfigSet.setMaxQueue(Integer.parseInt(lineArray[4]));
		//RESTART clamConfig
		else if (lineArray[1].equalsIgnoreCase("---")){
			//Save old clamConfigSet to configList
			configList.addToLast(clamConfigSet);
			globalClass.listOfClamConfig.addToStart(clamConfigSet);
			//create new configSet
			clamConfigSet = new clamConfig();
		}
		//ERROR MESSAGES (Dump of all other messages in log)
		else if (lineArray[1] != null)
		{
			createClamMessage(timeStamp, inputLine);
		}
		//No message exists
		else throw new unknownLineException();
	}
	/**
	 * Creates a new clam message from timestamp and full input line;
	 * @param ts timestamp
	 * @param fullLine input line
	 */
	private void createClamMessage(String ts, String fullLine)
	{
		clamMessage cMessage = new clamMessage();
		String em = fullLine;
		
		em.substring(25);
		
			cMessage.setTimeStamp(ts);
			cMessage.setErrorMessage(em);
			
			messageList.addToLast(cMessage);
			globalClass.listOfClamMessage.addToStart(cMessage);
	}
	/**
	 * Removes the period at the end of a string to turn it into a integer
	 * @param str number with ending period
	 * @return substring
	 */
	private String removePeriod(String str)
	{
		//Remove period from end of integer
		return str.replace(".","");
	}
}