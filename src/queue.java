/**
 * Folder v1.01
 * GENERIC QUEUE
 * uses list.java
 * @author Wadih El-Ghoussoubi(Woody)
 */

public class queue<T> {
	/*LIST THAT CONTAINS ALL INFO*/
	list<T> qlist;
	/**
	 * default constructor
	 */
	public queue(){
		qlist=new list<T>();
	}
	/**
	 * insert object T at start
	 * @param obj for the object added
	 */
	public void inqueue(T obj){
		qlist.addToLast(obj);
	}
	/**
	 * remove and display first object 
	 * @return obj
	 */
	public T unqueue() throws emptyListException{
		return qlist.deleteFromStart();
	}
	/**
	 * display first object
	 * @return object
	 */
	public T peek(){
		return qlist.getIt(0);
	}
	/**
	 * verifies if list is empty
	 * @return boolean
	 */
	public boolean isEmpty(){
		return qlist.isEmpty();
	}
	/**
	 * Displays the content of the queue
	 */
	public void showContents(){
		qlist.showContents();
	}
	/**
	 * returns the size of the queue
	 * @return size
	 */
	public int getSize(){
		return qlist.getSize();
	}
	/**
	 * takes a long for the pid and returns an object T if found
	 * @param pid
	 * @return obj
	 */
	public T findByPID(long pid){
		return qlist.findByPID(pid);
	}
	/**
	 * finds ID and deletes it from the queue
	 * @param ID
	 * @return obj
	 */
	public T findAndDelete(long ID){
		queue<T> tempQueue=new queue<T>();
		if (!this.isEmpty()){//if its not empty, then you can proceed
			try{
				while(true){// keep doing it till you catch and empty list exception
					if (peek()!=null && Long.parseLong(peek().toString())==ID){
						T tempObj=unqueue();
						try{
							while(true)
								inqueue(tempQueue.unqueue());
						}
						catch(emptyListException f){}
						return tempObj;
					}
					else{
						tempQueue.inqueue(unqueue());
					}
				}
			}
			catch(emptyListException e){
				try{
					while(true)
						inqueue(tempQueue.unqueue());
				}
				catch(emptyListException f){return null;}
			}
		}
		else
			return null;
	}
}
