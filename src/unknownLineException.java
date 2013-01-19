/**
 * v1.01
 * Folder v1.01
 * Exception thrown by a reader when a 
 * log line or segment is unknown or unparsable
 * 
 * @author Ryan Nasr
 */

public class unknownLineException extends Exception {
	/**
	 * default constructor for unknownLineException
	 */
	public unknownLineException(){
		super("An exception has occured when trying to parse log file...[UNKNOWN LINE]");
	}
	/**
	 * parameterized constructor with line #
	 * @param a
	 */
	public unknownLineException(long a){
		super("An exception has occured when trying to parse log file...[LINE #"+a+"]");
	}
	/**
	 * 
	 * @param s sets message 
	 */
	public unknownLineException(String s){
		super(s);
	}
	/**
	 * 
	 * @param obj sets message according to object that called the exception
	 */
	public unknownLineException(Object sender, long a){
		super("An exception has occured when trying to parse <"+
				sender.getClass().toString().substring(6,sender.getClass().toString().length())+
				"> log files...[LINE #"+a+"]");
	}
	/**
	 * @return String getMessage from super
	 */
	public String getMessage(){
		return super.getMessage();
	}
}
