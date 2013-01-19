/**
 * Folder v1.01
 * GENERIC hash class
 * v1.01
 * 
 * HOW TO USE:
 * example:
 * hash<messageInput> mInHash=new hash<messageInput>();
 * mInHash.addToStart();
 * 
 * @author Wadih El-Ghoussoubi(Woody)
 * @param <T>
 */


public class hash<T> {
	/**
	 * hash requires a List of list, since java does not allow usage of generic array
	 */
	private listOfList hashArr;
	/**
	 * the max size of the hash table
	 */
	private final int MAX_SIZE=9999; //WILL CHANGE AS WE GO ON
	/**
	 * parameterized constructor for the hash table
	 * @param obj for the object needed
	 */
	public hash(Object obj){
		hashArr=new listOfList(MAX_SIZE,obj);
		list<T> temp;
		for (int i=0;i<MAX_SIZE;i++){
			temp=hashArr.getList(i);
			temp=new list<T>();
		}
	}
	/**
	 * displays the contents of the hash table
	 */
	public void displayHash(){
		for (int i = 0; i < MAX_SIZE; i++)
        {
            System.out.println("The list at index" + i + " has the following info:");
            if (hashArr.getList(i)!=null)
            	hashArr.getList(i).showContents();
            else
            	System.out.println("EMPTY!");
        }
	}
	/**
	 * returns the size of the hash table
	 * @return size
	 */
	public int getSize(){
		return MAX_SIZE;
	}
	/**
	 * returns the linked list at the index specified
	 * @param index
	 * @return list
	 */
	public list<T> getList(long index){
		return hashArr.getList(index);
	}
	/**
	 * computes the hash value of an ID
	 * @param id
	 * @return hashValue
	 */
	public long computeHashValue(long id){ //we'll see how to compute this is for testing
		return (id%MAX_SIZE);
	}
	/**
	 * computes hash value of a string
	 * @param a
	 * @return hashValue
	 */
	public long computeHashValue(String a){
		long wut=Long.parseLong(a);
		return wut%MAX_SIZE;
	}
	/**
	 * Checks the hash table if it contains the specific object
	 * @param c for the object
	 * @return boolean
	 */
	public boolean contains(T c){
		long temp=computeHashValue(c.toString());
		return (hashArr.getList(temp).contains(c));
	}
	/**
	 * 
	 * @param c
	 * @return boolean
	 */
	public boolean contains(String c){
		long temp=computeHashValue(c);
		return (hashArr.getList(temp).contains(c));
	}
	/**
	 * finds the object looking for
	 * @param c for the object
	 * @return obj
	 */
	public T findIt(T c){
		String tempo=c.getClass().toString().substring(6,c.getClass().toString().length());
		long temp=0;
		if (tempo.equals("domainRank")){
			long tempcom;
			tempcom=(long)c.toString().hashCode();
			temp=computeHashValue(tempcom);
		}
		else{
			temp=computeHashValue(c.toString());
		}
		if (temp<0)temp*=-1;
		T tempt=(T)hashArr.getList(temp).find(c);
		return tempt;
	}
	/**
	 * finds the object from the string hash computation
	 * @param c
	 * @return obj
	 */
	public T findIt(String c){
		long temp=0;
		temp=computeHashValue(c);
		T tempt=(T)hashArr.getList(temp).find(c);
		return tempt;
	}
	/**
	 * adds object to hash
	 * @param c
	 */
	public void addToHashTable(Object c){
		String tempo=c.getClass().toString().substring(6,c.getClass().toString().length());
		messageInput min;
		messageInput.virusResultInfo vin;;
		long temp=0;
		if (tempo.equals("messageInput")){
			min=(messageInput)c;
			temp=computeHashValue(min.getHasher());
		}
		if (tempo.equals("messageInput$virusResultInfo")){
			vin=(messageInput.virusResultInfo)c;
			temp=computeHashValue(vin.toString());
		}
		if (tempo.equals("domainRank")){
			long tempcom;
			tempcom=(long)c.toString().hashCode();
			temp=computeHashValue(tempcom);
		}
		else
			temp=computeHashValue(c.toString());
		if (temp<0)temp*=-1;
		if (!hashArr.getList(temp).contains(c)){
			if (hashArr.getList(temp).getSize()!=0)globalClass.hit++;
			hashArr.getList(temp).addToStart(c);
		}
	}
	/**
	 * adds list to hash
	 * @param c
	 */
	public void addToHashTable(list<T> c){
		messageInput global=new messageInput();
		messageInput.virusResultInfo temp=global.new virusResultInfo();
		for (int i=0;i<c.getSize();i++){
			global=(messageInput)c.getIt(i);
			temp=global.getVirusResult();
			addToHashTable(temp);
		}
	}
	/**
	 * converts the hash table to a list
	 * @return list
	 */
	public list<T> convertToList(){
		list<T> temp=new list<T>();
		for (int i=0;i<MAX_SIZE;i++){
			for (int k=0;k<hashArr.getList(i).getSize();k++)
				temp.addToStart((T)hashArr.getList(i).getIt(k));
		}
		
		return temp;
	}
}
