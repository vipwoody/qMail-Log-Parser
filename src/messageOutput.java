/**
 * Objects that store vital information about messages sent
 * @author Wadih El-Ghoussoubi(Woody)
 * v1.00
 */
import java.io.Serializable;
public class messageOutput extends messageType implements Serializable {
	/** 
	 * contains a starting timestamp in tain64n format
	 */
	private String startTimestamp;
	/** 
	 * contains an ending timestamp in tain64n format
	 */
	private String endTimestamp;
	/**
	 * contains pid 
	 */
	private long uniqueID;
	/**
	 * contains the uid from the qmail send
	 */
	private long UserID;
	/**
	 * contains info of message
	 */
	private connectionInfo cInfo;
	/**
	 * contains result info
	 */
	private list<deliveryInfo> dInfoList=new list<deliveryInfo>();
	/**
	 * 
	 * connectionInfo contains information on the connection of spamReader
	 * @author ch_daher
	 * 
	 */
		public class connectionInfo implements Serializable
		{
			private int size;
			private String email;
			private int qmailID;
			/**
		 * 	@param null
		 * 	@return connectionInfo object
		 * 	default constructor for connectionInfo
		 	*/
			public connectionInfo(){
				size=0;
				email=null;
				qmailID=-1;
			}
			/**
			 * 
		 * 	@param s for size
		 * 	@param eAdd for email Adress
		 * 	@param QP for qp
		 * 	@return connectionInfo object
		 * 	parameterized constructor for connectionInfo
		 	*/
			public connectionInfo(int s, String eAdd, int QP){
				size=s;
				email=eAdd;
				qmailID=QP;
			}
			/**
			 * 
		 * 	@param s
		 * 	sets size
		 	*/
			public void setSize(int s){
					size=s;
			}
			/**
			 * 
		 * 	@param eAdd
		 * 	sets email address
		  	*/
			public void setEmail(String eAdd){
				email=eAdd;
				}
			/**
			 * 
		 * 	@param QP
		 * 	sets qmailID
		 	*/
			public void setQP(int QP){
				qmailID=QP;
				}
			/**
			 * 
		 * 	@return size
		 * 	returns size
		 	*/
			public int getSize(){
				return size;
				}
			/**
			 * 
		 * 	@return email
		 * 	returns email address
		 	*/
			public String getEmailAddress(){
				return email;
				}
			/**
			 * 
		 * 	@return port
		 * 	returns port address
		 	*/
			public int getQP(){
				return qmailID;
			}
			public String toString(){
				/*RETURNS STRING FOR println()*/
				return ("qmailID: "+qmailID+" | Size: "+size+" | Email: "+email);
			}
		}
	/**
	 * stores the delivery information
	 * 
	 * @author ch_daher
	 */
		public class deliveryInfo implements Serializable{			
				/**
			 * 	true if delivery ending is success
			 	*/
				private boolean success;
				/**
			 * 	delivery ID msg
			 	*/
				private long deliveryID;
				/**
			 * 	if boolean is true, then the start delivery msg is to local
			 * 	else it is to remote.
			 	*/
				private boolean receiver;
				/**
			 * 	receiver email address
			 	*/
				private String receiverEmail;
				/**
				 * 	receiver for the end msg
				 	*/
				private String endMSG;
				/**
			 * 	@param null
			 * 	@return resultInfo object
			 * 	default constructor for resultInfo()
			 	*/
				public deliveryInfo(){
					deliveryID=-1;
					receiverEmail=null;
					endMSG=null;
				}
				/**
				 * 
			 * 	@param lr Local or Remote
			 * 	@param Rmail reciever's email
			 * 	@param S for success of not
			 * 	@param delID for delivery ID
			 * 	@return deliveryInfo object
			 * 	parameterized constructor for deliveryInfo
			 	*/
				public deliveryInfo(boolean S, String Rmail,boolean l_r,long delID,String s){
					success=S;
					receiverEmail=Rmail;
					receiver=l_r;
					deliveryID=delID;
					endMSG=s;
				}	
				
				/**
			 * 	@return success
			 * 	
			 	*/
				public boolean getSuccess(){
					return success;
					}
				/**
				 * 	@return endMessage
				 * 	
				 */
				public String getEndMSG(){
					return endMSG;
				}
				/**
			 * 	@return reveiverEmail
			 * 
			 	*/
				public String getRMail(){
					return receiverEmail;
				}
				public String toString(){
					return (deliveryID+"");
				}
				/**
				 * 
			 * 	@return receiver
			 	*/
				public boolean getReceiver(){
					return receiver;
					}
				/**
				 * 
			 * 	@return deliveryID
			 	*/
				public long getDID(){
					return deliveryID;
					}
				
				/**
				 * 	@param s for end MSG
				 * 
				 */
				public void setEndMSG(String s){
					endMSG=s;
				}
				/**
			 * 	@param S for success
			 * 
			 */
				public void setSuccess(String S){
					if(S.equals("success")){
						success=true;
					}else{ 
						success=false;
						}
				}
				/**
			 * 	@param rm for recaiver email
			 * 
			 */
				public void setRMail(String rm){
					receiverEmail= rm;
					}
				/**
			 * 	@param r for local or remote
			 * 
			 */
				public void setReceiver(String r){
					if(r.equals("local")){
						receiver = true;
					}else{
						receiver =false;
					}
				}
				/**
			 * 	@param DID for delivery ID
			 * 
			 */
				public void setDID(long DID){
					deliveryID=DID;
				}
				public String getInfo(){
					/*RETURNS STRING FOR println()*/
					return ("Status: "+success+" | Receiver Email: "+receiverEmail+
							" | Remote/Local: "+receiver+" | Delivery ID: "+deliveryID+
							" | Msg Status: "+endMSG);
				}
				
			}

	/**
	 * default constructor for messageOutput
	 */
	public messageOutput()	{
		cInfo=new connectionInfo();
		startTimestamp=null;
		endTimestamp=null;
		uniqueID=-1;
		
	}
	/**
	 * 	paremeterized constructor for messageOutput
	 * @param STS for starting timestamp
	 * @param ETS for ending timestamp
	 * @param unique for the uniqueID
	 * @param uID for the uid
	 * @param CI for the connectionInfo
	 * @param DI for the deliveryInfo
	 */
	public messageOutput(String STS, String ETS,long unique, long uID,connectionInfo CI, deliveryInfo DI){
		startTimestamp=STS;
		endTimestamp=ETS;
		uniqueID=unique;
		UserID=uID;
		cInfo=CI;
		dInfoList.addToStart(DI);
	}
	/**
	 * 
	 * @param STS starting timestamp
	 */
	public void setSTS(String STS){
		startTimestamp=STS;
		}
	/**
	 * 
	 * @param ETS ending timestamp
	 */
	public void setETS(String ETS){
		endTimestamp=ETS;
		}
	/**
	 * 
	 * @param unique uniqueID
	 */
	public void setUniqueID(long unique){
		uniqueID=unique;
		}
	/**
	 * 
	 * @param uid for UserID
	 */
	public void setUID(long uid){
		UserID=uid;
		}
	
	/**
	 * 
	 * @return startTimestamp
	 */
	public String getSTS(){
		return startTimestamp;
		}
	/**
	 * 
	 * @return endTimestamp
	 */
	public String getETS(){
		return endTimestamp;
		}
	/**
	 * 
	 * @return uniqueID
	 */
	public long getUniqueId(){
		return uniqueID;
		}
	/**
	 * 
	 * @return UserID
	 */
	public long getUID(){
		return UserID;
		}
	/**
	 * 
	 * @return cInfo the connection info object
	 */
	public connectionInfo getCInfo(){
		return cInfo;
		}
	/**
	 * 
	 * @return dInfo the delivery info object
	 */
	
	/**
	 * @return UniqueID abstract method inherited
	 */
	public long getPID(){
		return getUniqueId();
		}
	/**
	 * @return timestamp
	 */
	public String getCreationTimestamp(){
		return getSTS();
	}
	/**
	 * toString
	 */
	public String toString(){
			 return (uniqueID+"");
	}
	/**
	 * adds a delivery info to the list
	 * @param d
	 */
	public void addDInfo(deliveryInfo d){
		dInfoList.addToStart(d);
	}
	/**
	 * displays the info of the message output object
	 * @return string
	 */
	public String showInfo(){
		return ("Message created @ "+startTimestamp+"-"+endTimestamp+" | MID #"+UserID+
				" | UniqueID #"+uniqueID+"\nConnection Info:"+
				"\n----------------\n"+this.cInfo+"\n\nDelivery:"+
				"\n----------------\n"+showDInfo());
	}
	
	/**
	 * displays the delivery info linked list
	 * @return string
	 */
	public String showDInfo(){
		deliveryInfo tempo;
		String returnString="";
		for (int i=0;i<dInfoList.getSize();i++){
			tempo=dInfoList.getIt(i);
			returnString+="Delivery info #"+(i+1)+"\n"+tempo.getInfo()+"\n";
		}
		return returnString;
	}
}
