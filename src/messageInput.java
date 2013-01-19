/**
 * Folder v1.01
 * Objects that store information about messages being received
 * 
 * @author Wadih El-Ghoussoubi(Woody)
 * v1.01
 */
import java.io.Serializable;

public class messageInput extends messageType implements Serializable{
	/**contains timestamp in tain64n format*/
	private String creationTimestamp;
	/**contains pid*/
	private long pid;
	/**contains info of message*/
	private connectionInfo ci;
	/**contains virus result*/
	private virusResultInfo vri; 
	/**contains spam result info*/
	private spamResultInfo sri;
	/**uniqueId to track message through the readers*/
	private long uniqueId;
	/**
	 * connectionInfo contains information on the connection of spamReader
	 * 
	 * @author ra_nasr
	 */
	public class connectionInfo implements Serializable
	{
		/*Contains domain name*/
		String domain;
		/*contains IP address*/
		String ip;
		/*CONTAINS PORT NUMBER*/
		int port;
		/**
		 * default constructor for connectionInfo
		 * @param null
		 * @return connectionInfo object
		 */
		public connectionInfo(){
			/*DEFAULT VALUES*/
			domain="undefined"; 
			ip="undefined";
			port=-1;
		}
		/**
		 * parameterized constructor for connectionInfo
		 * @param domainName
		 * @param ipAdd
		 * @param portAdd
		 * @return connectionInfo object
		 */
		public connectionInfo(String domainName, String ipAdd, int portAdd){
			/*SET ALL*/
			domain=domainName;
			ip=ipAdd;
			port=portAdd;
		}
		/**
		 * copy constructor
		 * @param e
		 */
		public connectionInfo(connectionInfo e){
			/*COPIES ALL*/
			domain=e.getDomain();
			ip=e.getIpAddress();
			port=e.getPort();
		}
		/**
		 * 
		 * @param domainName
		 * sets domain name
		 */
		public void setDomain(String domainName){
			/*SET DOMAIN NAME*/
			domain=domainName;
		}
		/**
		 * 
		 * @param ipAdd
		 * sets ip address
		 */
		public void setIpAddress(String ipAdd){
			/*SET IP ADDRESS*/
			ip=ipAdd;
		}
		/**
		 * 
		 * @param portAdd
		 * sets port number
		 */
		public void setPort(int portAdd){
			/*SET PORT*/
			port=portAdd;
		}
		/**
		 * 
		 * @return domain
		 * returns domain name
		 */
		public String getDomain(){
			/*RETURN DOMAIN USING TEMP*/
			String temp=domain;
			return temp;
		}
		/**
		 * 
		 * @return ip
		 * returns ip address
		 */
		public String getIpAddress(){
			/*RETURN IP USING TEMP*/
			String temp=ip;
			return temp;
		}
		/**
		 * 
		 * @return port
		 * returns port address
		 */
		public int getPort(){
			/*RETURN PORT*/
			return port;
		}
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString(){
			/*RETURNS STRING FOR println()*/
			return ("Domain: "+domain+" | IP: "+ip+":"+port);
		}
		/**
		 * equals method 
		 * @param e connectionInfo object
		 * @return true/false
		 */
		public boolean equals(connectionInfo e){
			/*RETURN TRUE IF domain&ip&port ARE EQUAL*/
			return (this.getDomain().equals(e.getDomain()) && this.getIpAddress().equals(e.getIpAddress()) 
					&& this.getPort()==e.getPort());
		}
	}
	/**
	 * spamResultInfo contains result from spamReader
	 * v1.01
	 * @author Ryan
	 *
	 */
	public class spamResultInfo implements Serializable{
		/*timestamp of msg when spam receives message */
		String timestamp;
		/*unique message id*/
		String mid;
		/*score/5 of the spam message*/
		double score;
		/*time took to find spam*/
		double time;
		/*size of msg*/
		int size;
		/*true for spam false for clean*/
		boolean spam;
		/**
		 * default constructor for spamResultInfo()
		 * @param null
		 * @return resultInfo object
		 */
		public spamResultInfo(){
			/*DEFAULT VALUES*/
			timestamp="undefined";
			mid="NOT_SET";
			score=-1;
			time=-1;
			size=-1;
			spam=false; 
		}
		/**
		 * 
		 * @param messageId mid
		 * @param scr score
		 * @param t time
		 * @param sz size
		 * @param sp spam
		 * @return resultInfo object
		 * parameterized constructor for resultInfo
		 */
		public spamResultInfo(String tstamp,String messageId, double scr, double t, int sz, boolean sp){
			/*SET ALL*/
			timestamp=tstamp;
			mid=messageId;
			score=scr;
			time=t;
			size=sz;
			spam=sp;
		}
		/**
		 * copy constructor
		 * @param e
		 */
		public spamResultInfo(spamResultInfo e){
			/*COPY ALL*/
			mid=e.getMid();
			score=e.getScore();
			time=e.getTimeTook();
			size=e.getMsgSize();
			spam=e.getSpam();
		}
		/**
		 * 
		 * get messageId
		 * @return mid
		 */
		public String getMid(){
			String temp=mid;
			return temp;
		}
		/**
		 * get score of spam msg
		 * @return double
		 */
		public double getScore(){
			return score;
		}
		/**
		 * get time
		 * @return time
		 */
		public double getTimeTook(){
			return time;
		}
		/**
		 * gets msg size
		 * @return size
		 */
		public int getMsgSize(){
			return size;
		}
		/**
		 * returns true for spam, false for clean
		 * @return spam
		 */
		public boolean getSpam(){
			return spam;
		}
		/**
		 * sets message id
		 * @param m
		 */
		public void setMid(String m){
			mid=m;
		}
		/**
		 * set score
		 * @param scr
		 */
		public void setScore(double scr){
			score=scr;
		}
		/**
		 * set time taken
		 * @param t
		 */
		public void setTimeTook(double t){
			time=t;
		}
		/**
		 * set size of msg
		 * @param s
		 */
		public void setMsgSize(int s){
			size=s;
		}
		/**
		 * set spam (true for spam - false for clean)
		 * @param b
		 */
		public void setSpam(boolean b){
			spam=b;
		}
		public String toString(){
			return ("Spam: " +spam+" | msgID: "+mid+" | score: "+score+"/5 | time: "+time + " | size: "+size);
		}
		public boolean equals(spamResultInfo e){
			return (this.mid.equals(e.getMid())&&this.score==e.getScore()&&this.time==e.getTimeTook()
					&&e.size==e.getMsgSize()&&this.spam==e.getSpam());
		}
	
	}
	/**
	 * stores virusResultInfo
	 * v1.01
	 * @author RyanLaptop
	 *
	 */
	public class virusResultInfo implements Serializable{
		/*timestamp of message when entering AV */
		String timestamp;
		/*contains folder name with virus type suspected*/
		String folderId;
		/*virus flag true-virus false-clean*/
		boolean virus;
		/*contains virus type*/
		String virusType;
		/*
		 * default constructor for virusResultInfo
		 */
		public virusResultInfo(){
			/*DEFAULT VALUES*/
			timestamp="undefined";
			folderId="undefined";
			virus=false;
			virusType="undefined";
		}
		/**
		 * parameterized constructor for virusResultInfo
		 * @param timeStmp
		 * @param fID
		 * @param vir
		 */
		public virusResultInfo(String timeStmp, String fID, boolean vir, String vt){
			
			/*SET ALL*/
			timestamp=timeStmp;
			folderId=fID;
			virus=vir;
			virusType=vt;
		}
		/**
		 * copy constructor
		 * @param e
		 */
		public virusResultInfo(virusResultInfo e){
			/*COPY ALL*/
			timestamp=e.getTimestamp();
			folderId=e.getFolderID();
			virus=e.getVirus();
			virusType=e.getVirusType();
		}
		/**
		 * gets timestamp
		 * @return timestamp
		 */
		public String getTimestamp(){
			String temp=timestamp;
			return temp;
		}
		/**
		 * gets Folder ID
		 * @return folderId
		 */
		public String getFolderID(){
			String temp=folderId;
			return temp;
		}
		/**
		 * gets virus flag
		 * @return virus
		 */
		public boolean getVirus(){
			return virus;
		}
		/**
		 * gets virusType
		 * @return virusType
		 */
		public String getVirusType(){
			String temp=virusType;
			return temp;
		}
		/**
		 * set timestamp
		 * @param t
		 */
		public void setTimestamp(String t){
			timestamp=t;
		}
		/**
		 * set folder ID
		 * @param f
		 */
		public void setFolderID(String f){
			folderId=f;
		}
		/**
		 * set virus flag
		 * @param v
		 */
		public void setVirus(boolean v){
			virus=v;
		}
		/**
		 * set virus type
		 * @param vt
		 */
		public void setVirusType(String vt){
			virusType=vt;
		}
		/**
		 * toString method
		 */
		public String showInfo(){
			return ("Virus: "+virus+" | timestamp: "+timestamp+" | FolderID:<"+folderId+"> | VirusType: <"+virusType+">");
		}
		public String toString(){
			String[]temp=folderId.split("/");
			char tempChar[]=temp[4].toCharArray();
			String toHash="";
			int foundpoint=0;
			for (int i=0;i<tempChar.length;i++){
				if (foundpoint==1)
					if (tempChar[i]!='.') toHash+=tempChar[i];
				if (tempChar[i]=='.'){
					foundpoint++;
				}
			}
			toHash.replace(".","");		
			System.out.println(toHash);
			return toHash;
		}
		/**
		 * equals method
		 * @param e
		 * @return true for equal
		 */
		public boolean equals(virusResultInfo e){
			return (this.timestamp.equals(e.getTimestamp())&&this.folderId.equals(e.getFolderID())
					&&this.virus==e.getVirus()&&this.virusType.equals(e.getVirusType()));
		}
	}
	/**
	 * default constructor for messageinput
	 */
	public messageInput(){
		/*DEFAULT VALUES*/
		creationTimestamp="undefined";
		pid=-1;
		ci=null;
		sri=null;
		vri=null;
		uniqueId=-1;
	}
	/**
	 * messageInput parameterized constructor
	 * @param tm timestamp
	 * @param processId pid
	 * @param ci connectionInfo
	 * @param ri resultInfo
	 */
	public messageInput(String tm,long processId, connectionInfo cInfo, virusResultInfo vInfo,spamResultInfo sInfo, long uID){
		creationTimestamp=tm;
		pid=processId;
		if (cInfo!=null)
			ci=new connectionInfo(cInfo);
		else
			ci=null;
		if (vInfo!=null)
			vri=new virusResultInfo(vInfo);
		else 
			vri=null;
		if (sInfo!=null)
			sri=new spamResultInfo(sInfo);
		else
			sri=null;
		uniqueId=uID;
	}
	/**
	 * gets timestamp
	 * @return timestamp
	 */
	public String getCreationTimestamp(){
		String temp=creationTimestamp;
		return temp;
	}
	/**
	 * 
	 * @return pid
	 */
	public long getPID(){
		return pid;
	}
	/**
	 * 
	 * @return connectionInfo
	 */
	public connectionInfo getConnectionInfo(){
		return new connectionInfo(ci);
	}
	/**
	 * 
	 * @return virusResult
	 */
	public virusResultInfo getVirusResult(){
		return new virusResultInfo(vri);
	}
	/**
	 * 
	 * @return spamResult
	 */
	public spamResultInfo getSpamResult(){
		if (sri!=null)
			return new spamResultInfo(sri);
		else
			return null;
	}
	/**
	 * 
	 * @return uniqueID
	 */
	public long getUniqueID(){
		return uniqueId;
	}
	/**
	 * 
	 * @param t
	 */
	public void setCreationTimestamp(String t){
		creationTimestamp=t;
	}
	/**
	 * 
	 * @param p
	 */
	public void setPID(long p){
		pid=p;
	}
	/**
	 * 
	 * @param cInfo
	 */
	public void setConnectionInfo(connectionInfo cInfo){
		ci=new connectionInfo(cInfo);
	}
	/**
	 * 
	 * @param vInfo
	 */
	public void setVirusResult(virusResultInfo vInfo){
		vri=new virusResultInfo(vInfo);
	}
	/**
	 * 
	 * @param sInfo
	 */
	public void setSpamResult(spamResultInfo sInfo){
		sri=new spamResultInfo(sInfo);
	}
	/**
	 * 
	 * @param uid
	 */
	public void setUniqueID(long uid){
		uniqueId=uid;
	}
	/**
	 * toString method
	 */
	public String toString(){
		return (pid+"");
	}
	/**
	 * equal method
	 * @param mi
	 * @return
	 */
	public boolean equals(messageInput mi){
		return (this.creationTimestamp.equals(mi.getCreationTimestamp())&&this.pid==mi.getPID()
				&&this.ci.equals(mi.getConnectionInfo())&&this.vri.equals(mi.getVirusResult())
				&&this.sri.equals(mi.getSpamResult())&&this.uniqueId==mi.getUniqueID());
	}
	/**
	 * displays info of the message input object
	 * @return string
	 */
	public String showInfo(){
		return ("\n-----------------------------------------------------------------------\n" +
				"Message created @ "+creationTimestamp+" | pid #"+pid+
				" | UniqueID #"+uniqueId+"\nConnection Info:"+
				"\n----------------\n"+this.ci+"\n\nVirus Result Info:"+
				"\n----------------\n"+this.vri+"\n\nSpam Result Info"+
				"\n----------------\n"+this.sri);
	}
	
	public long getHasher(){
		
		return (long)(pid+sri.getScore()+sri.getTimeTook());
	}
	
}
