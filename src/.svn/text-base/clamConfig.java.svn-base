/**
 * Stores clamd config information
 * Folder v1.05
 * Clam Config class
 * clamConfig.java v1.02
 * @author Peter Rockwell
 */

public class clamConfig {
	
	/* timestamp of clamconfig startup*/
	private String timeStamp;
		
	/*LIMITS*/
	private int globalSizeLimit;
	private int fileSizeLimit;
	private int recursionLevelLimit;
	private int filesLimit;
	private int coreDumpLimit;
	
	/*FEATURE ENABLING*/
	private boolean archiveSupport = false;
	private boolean algorithmicDetection = false;
	private boolean portableExecutableSupport = false;
	private boolean elfSupport = false;
	private boolean mailFilesSupport = false;
	private boolean ole2Support = false;
	private boolean pdfSupport = false;
	private boolean htmlSupport = false;
		
	/* how often it checks if freshclam has updated the databases*/	
	private int selfCheckTimer;
		
	/*process id (PID) of the daemon that is listening for connections*/
	private int listeningDaemonPID;
		
	/*maximum number of messages that can sit it the queue*/
	private int maxQueue;	
	
	/* MUTATORS */
	
	/**
	 * set time stamp
	 * @param newTime
	 */
	public void setTimeStamp(String newTime){
		timeStamp = newTime;
	}
	
	/**
	 * set globalSizeLimit
	 * @param gSize
	 */
	public void setGlobalSizeLimit(int gSize){
		globalSizeLimit = gSize;
	}
	
	/**
	 * set fileSizeLimit
	 * @param fSize
	 */
	public void setFileSizeLimit(int fSize){
		fileSizeLimit = fSize;
	}
	
	/**
	 * set recursionLevelLimit
	 * @param rLvl
	 */
	public void setRecursionLevelLimit(int rLvl){
		recursionLevelLimit = rLvl;
	}
	
	/**
	 * set FilesLimit
	 * @param fLmt
	 */
	public void setFilesLimit(int fLmt){
		filesLimit = fLmt;
	}
	
	/**
	 * set coreDumpLimit
	 * @param dLmt
	 */
	public void setCoreDumpLimit(int dLmt){
		coreDumpLimit = dLmt;
	}
	
	/**
	 * set archiveSupport
	 * @param support
	 */
	public void setArchiveSupport(boolean support){
		archiveSupport = support;
	}
	
	/**
	 * set algorithmicDetection
	 * @param alDetect
	 */
	public void setAlgorithmicDetection(boolean alDetect){
		algorithmicDetection = alDetect;
	}
	
	/**
	 * set portableExecutableSupport
	 * @param portable
	 */
	public void setPortableExecutableSupport(boolean portable){
		portableExecutableSupport = portable;
	}
	
	/**
	 * set elfSupport
	 * @param eSupport
	 */
	public void setElfSupport(boolean eSupport){
		elfSupport = eSupport;
	}
	
	/**
	 * set mailFilesSupport
	 * @param fileSupport
	 */
	public void setMailFilesSupport(boolean fileSupport){
		mailFilesSupport = fileSupport;
	}
	
	/**
	 * set ole2Support
	 * @param oSupport
	 */
	public void setOle2Support(boolean oSupport){
		ole2Support = oSupport;
	}
	
	/**
	 * set pdfSupport
	 * @param pdf
	 */
	public void setPdfSupport(boolean pdf){
		pdfSupport = pdf;
	}

	/**
	 * set htmlSupport
	 * @param html
	 */
	public void setHtmlSupport(boolean html){
		htmlSupport = html;
	}
	
	/**
	 * set selfCheckTimer
	 * @param timeCheck
	 */
	public void setSelfCheckTimer(int timeCheck){
		selfCheckTimer = timeCheck;
	}
	
	/**
	 * set listeningDaemonPID
	 * @param listen
	 */
	public void setListeningDaemonPID(int listen){
		listeningDaemonPID = listen;
	}
	
	/**
	 * set maxQueue
	 * @param queue
	 */
	public void setMaxQueue(int queue){
		maxQueue = queue;
	}
	
	/* ACCESSORS */
	
	/**
	 * get time stamp
	 * @return timestamp
	 */
	public String getTimeStamp(){
		return timeStamp;
	}

	//More accessors necessary
	
	/**
	 * toString method
	 */
	public String toString(){
		return ("timestamp: "+timeStamp+" | globalSizeLimit:"+globalSizeLimit+" | fileSizeLimit: "+fileSizeLimit+" | recursionLevelLimit: "+recursionLevelLimit+" | filesLimit: "+filesLimit+" | coreDumpLimit:"+coreDumpLimit+" | archiveSupport: "+archiveSupport+" | algorithmicDetection: "+algorithmicDetection+" | portableExecutableSupport: "+portableExecutableSupport+" | elfSupport: "+elfSupport+" | mailFilesSupport: "+mailFilesSupport+" | ole2Support: "+ole2Support+" | pdfSupport: "+pdfSupport+" | htmlSupport: "+htmlSupport+" | selfCheckTimer:"+selfCheckTimer+" | listeningDaemonPID: "+listeningDaemonPID+" | maxQueue:"+maxQueue);
	}
}