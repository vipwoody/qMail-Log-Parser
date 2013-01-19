/**
 * Folder v1.01
 * a List that holds a list of list SINCE JAVA CANNOT HOLD AN ARRAY OF GENERIC LIST.........
 * v1.01
 * 
 * 
 * @author Wadih El-Ghoussoubi(Woody)
 */

public class listOfList {
	public class listNode{
		/**
		 * list
		 */
		private list c;
		/**
		 * node of the list
		 */
		private listNode next;
		/**
		 * adds object to the list, depending on which class this object is from
		 * @param sender
		 */
		public listNode(Object sender){
			String temp=sender.getClass().toString().substring(6,sender.getClass().toString().length());
			System.out.println(sender);
			if (temp.equals("messageInput"))
				c=new list<messageInput>();
			else if(temp.equals("messageOutput"))
				c=new list<messageOutput>();
			else if (temp.equals("domainRank")){
				c=new list<domainRank>();
			}
			else
				c=new list<smtpdInput>();
			next=null;
		}
		/**
		 * parameterized constructor of the listNode
		 * @param a
		 * @param n
		 */
		public listNode(list a, listNode n){
			c=a;
			next=n;
		}
		/**
		 * returns the list at the node
		 * @return c
		 */
		public list getList(){
			return c;
		}
		/**
		 * returns the next pointer
		 * @return next
		 */
		public listNode getNext(){
			return next;
		}
		/**
		 * sets the list to a node
		 * @param a
		 */
		public void setList(list a){
			c=a;
		}
		/**
		 * sets the next pointer
		 * @param n
		 */
		public void setNext(listNode n){
			next=n;
		}
	}
	/**
	 * head pointer
	 */
	private listNode head;
	/**
	 * size of the list
	 */
	private int size;
	/**
	 * default constructor of the listOfList
	 */
	public listOfList(){
		head=null;
		size=0;
	}
	/**
	 * parameterized constructor
	 * @param a
	 * @param w
	 */
	public listOfList(int a, Object w){
		String temp=w.getClass().toString().substring(6,w.getClass().toString().length());
		
		if (temp.equals("messageInput")){
			list<messageInput> templist=new list<messageInput>();
			for (int i=0;i<a;i++){
				this.addToStart(templist, new messageInput());
			}
		}
		if (temp.equals("messageOutput")){
			list<messageOutput> templist=new list<messageOutput>();
			for (int i=0;i<a;i++){
				this.addToStart(templist, new messageInput());
			}
		}
		if(temp.equals("smtpdInput")){
			list<smtpdInput>templist=new list<smtpdInput>();
			for(int i=0;i<a;i++){
				this.addToStart(templist,new smtpdInput());
			}
		}
		if (temp.equalsIgnoreCase("messageinput$virusresultinfo")){
			list<messageInput.virusResultInfo>templist=new list<messageInput.virusResultInfo>();
			for (int i=0;i<a;i++){
				this.addToStart(templist, new messageInput().new virusResultInfo());
			}
		}
		if (temp.equals("domainRank")){			
			list<domainRank> templist=new list<domainRank>();
			for (int i=0;i<a;i++){
				this.addToStart(templist,new domainRank());
			}
		}
	}
	/**
	 * adds the object to the start of the list at the specific node
	 * @param a
	 * @param sender
	 */
	public void addToStart(list a, Object sender){		
		
		String temp=sender.getClass().toString().substring(6,sender.getClass().toString().length());
		if (temp.equals("messageInput"))
			a=new list<messageInput>();
		else if(temp.equals("messageOutput"))
			a=new list<messageOutput>();
		else if (temp.equals("domainRank"))
			a=new list<domainRank>();
		else
			a=new list<smtpdInput>();
		head=new listNode(a,head);
		size++;
	}
	/**
	 * returns the list at a specific index
	 * @param index
	 * @return list
	 */
	public list getList(long index){
		if (index<0||index>(size-1))
			return null;
		if (index==0)
			return head.getList();
		else{
			listNode temp=head;
			int i=0;
			while(i!=index){
				temp=temp.getNext();
				i++;
			}
			return temp.getList();
		}
	}
	/**
	 * returns the size of the list of list
	 * @return size
	 */
	public int getSize(){
		return size;
	}
	
}
	