/**
 * contains timestamp and pointer
 * @author Ryan
 *
 */
//imports date 
import java.util.Date;
public class timestampContainer {
	//keeps timestamp of every object
	Date timestamp;
	//keeps a pointer to the object
	Object pointer;
	/**
	 * default constructor
	 */
	public timestampContainer(){
		timestamp=null;
		pointer=null;
	}
	/**
	 * parameterized constructor
	 * @param timestamp
	 * @param pointer
	 */
	public timestampContainer(String tstamp,Object ptn){
		String[] arrDate=tstamp.split("/");
		timestamp=new Date(Integer.parseInt(arrDate[2]),Integer.parseInt(arrDate[1]),Integer.parseInt(arrDate[0]),Integer.parseInt(arrDate[3]),Integer.parseInt(arrDate[4]));
		pointer=ptn;
	}
	/**
	 * sets the timestamp
	 * @param tstamp
	 */
	public void setTimestamp(Date tstamp){
		timestamp=tstamp;
	}
	/**
	 * sets the pointer
	 * @param ptn
	 */
	public void setPointer(Object ptn){
		pointer=ptn;
	}
	/**
	 * returns the timestamp (Date object)
	 * @return
	 */
	public Date getTimestamp(){
		return timestamp;
	}
	/**
	 * returns the pointer (Object)
	 * @return
	 */
	public Object getPointer(){
		return pointer;
	}
	/**
	 * returns String to display information
	 * @return toString
	 */
	public String toString(){
		return "This container has timestamp: "+timestamp+" pointing to "+pointer;
	}
}
