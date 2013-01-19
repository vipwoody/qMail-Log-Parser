/**
 * Folder v1.04
 * GLOBAL CLASS TO HOLD HASH 
 * v1.02
 * Added: String for logError @ logError.bin
 * Added: String for qmail-smtpd
 * 
 * @author Wadih El-Ghoussoubi(Woody)
 */

 import java.io.File;
 /**
  * contains all the objects of the classes we need (ex hash, list, list of list....)
  * as well as all the constants for the file information
  * @author Ryan
  *
  */
public class globalClass {
	public static hash<messageInput> hashOfMessageInput=new hash<messageInput>(new messageInput());
	public static hash<messageOutput> hashOfMessageOutput=new hash<messageOutput>(new messageOutput());
	public static hash<messageInput> hashOfVirusMessages=new hash<messageInput>(new messageInput());
	public static hash<messageInput> hashOfSpamMessages=new hash<messageInput>(new messageInput());
	public static hash<smtpdInput> hashOfSmtpdInputs=new hash<smtpdInput>(new smtpdInput());
	public static list<clamConfig> listOfClamConfig=new list<clamConfig>();
	public static list<clamMessage> listOfClamMessage=new list<clamMessage>();
	public static list<messageInput> listOfM=new list<messageInput>();
	public static list<messageOutput> listOfMo=new list<messageOutput>();
	public static list<smtpdInput> listOfSm=new list<smtpdInput>();
	public static list<timestampContainer> listOfTsMi=new list<timestampContainer>();
	public static list<timestampContainer> listOfTsMo=new list<timestampContainer>();
	public static list<timestampContainer> listOfTsSm=new list<timestampContainer>();
	public static hash<domainRank> hashDm=new hash<domainRank>(new domainRank());
	public static int hit=0;
	static String currentDir=new File("").getAbsolutePath();
	public final static String log_spam=currentDir+"\\log\\spamd";
	public final static String log_virus=currentDir+"\\log\\clamd";
	public final static String log_qmailSend=currentDir+"\\log\\qmail-send";
	public final static String log_qmailSmtpd=currentDir+"\\log\\qmail-smtpd";
	public final static String log_error=currentDir+"\\log\\logError.bin";
	public final static String cache_info=currentDir+"\\cache\\cachInfo.inf";
	public final static String cache_qmail=currentDir+"\\cache\\qmail.bin";
	public final static String cache_smtpd=currentDir+"\\cache\\smptd.bin";
	public final static String cache_spam=currentDir+"\\cache\\spam.bin";
	public final static String dump_spam=currentDir+"\\dump\\spamDump.bin";
	public final static String dump_smtpd=currentDir+"\\dump\\smtpdDump.bin";
	public final static String dump_qmail=currentDir+"\\dump\\qmailDump.bin";
}
