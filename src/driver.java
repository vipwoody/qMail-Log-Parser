/**
 * Folder v1.01
 * Driver class to test all current methods/classes
 * How to use:
 * 	-created method called testMessageInputMethods()
 * 	-inside that method create an object of the class you want to test and test ALL methods of that class
 * 	-DO NOT remove any methods someone else creates to test
 * driver.java v1.01
 * @author Wadih El-Ghoussoubi(Woody)
 * 
 */
public class driver {

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("testing driver"); //IT WORKS...DUH (r)
		//testMessageInput(); IT WORKS - for messageOuput display, follow my toString in messageInput (r)
		//to view decomment testMessageInput() (r)
		//testUnknownLineException(); IT WORKS (r)
		//testList(); IT WORKS (r)
		//testListOfList(); //IT WORKS (r)
		//testHash(); IT WORKS
		//testQueue(); IT WORKS!
		//testSpamReader();  
		//testQueue2(); IT WORKS
		//System.out.println("DUDE");
		//System.out.println("more");
		testQmail();
		//testSmtpd();
		//testmouthash();
		//System.out.println((592295+2638346)%20);
		//System.out.println((592298+2638346)%20);
		//System.out.println((592302+2638346)%20);
		//testQ();
		//testAV();
		
	}
	/**
	 * method that runs the AVReader
	 */
	public static void testAV(){
		AVReader ar=new AVReader();
		ar.readFile();
		//System.out.println("hit count: "+globalClass.hit);
		
		//list<messageInput> temp=globalClass.hashOfMessageInput.convertToList();
		//int siz=0;
		//for (int i=0;i<globalClass.hashOfMessageInput.getSize();i++){
			//System.out.println(globalClass.hashOfMessageInput.getList(i).getSize());
			//siz+=globalClass.hashOfMessageInput.getList(i).getSize();
		//}
		//System.out.println(siz);
		/*
		list<messageInput>temp=globalClass.hashOfVirusMessages.convertToList();
		System.out.println(temp.getSize());
		System.out.println("\n\n---------------------\nTOTAL OBJECTS CREATED"+
				"\n---------------------\n"+temp.getSize());
		*/
	}
	/**
	 * Method that runs the Queue class
	 */
	public static void testQ(){
		queue<Integer> ql=new queue<Integer>();
		ql.inqueue(1);
		try{
			ql.unqueue();
			ql.unqueue();
			ql.unqueue();
		}
		catch(emptyListException e){}
		ql.inqueue(2);
		ql.inqueue(3);
		ql.inqueue(4);
		ql.inqueue(6);
		ql.showContents();
	}
	public static void testmouthash(){
		messageOutputHashValue moh=new messageOutputHashValue(123,456);
	}
	/**
	 * Method runs the smtpdTeader
	 */
	public static void testSmtpd(){
		smtpdReader sr=new smtpdReader();
		sr.readFile();
		//list<smtpdInput> temp=globalClass.hashOfSmtpdInputs.convertToList();
		//System.out.println(temp.getSize());
		//for (int i=0;i<temp.getSize();i++)
		//	System.out.println(temp.getIt(i).showInfo());
		//System.out.println("\n\n---------------------\nTOTAL OBJECTS CREATED"+
		//					"\n---------------------\n"+temp.getSize());
	}
	/**
	 * method runs the qmailReader
	 */
	public static void testQmail(){
		qmailReader qr=new qmailReader();
		qr.readFile();
		
		//messageOutput.convertTimeStamp("0xc");
		//list<messageOutput> temp=globalClass.hashOfMessageOutput.convertToList();
		//System.out.println(temp.getSize());
		//for (int i=0;i<temp.getSize();i++)
		//	System.out.println(temp.getIt(i).showInfo());
		//System.out.println("\n\n---------------------\nTOTAL OBJECTS CREATED"+
		//		"\n---------------------\n"+temp.getSize());
	}
	/**
	 * method tests the queue
	 */
	public static void testQueue2(){
		
		messageInput m1=new messageInput("nothing",459,null,null,null,-1);
		messageInput m2=new messageInput("nothing",455,null,null,null,-1);
		messageInput m3=new messageInput("nothing",4525,null,null,null,-1);
		queue<messageInput> qm=new queue<messageInput>();
		qm.inqueue(m1);
		qm.inqueue(m2);
		qm.inqueue(m3);
		qm.showContents();
		System.out.println(qm.findByPID(12));
	}
	/**
	 * method runs the spam reader
	 */
	public static void testSpamReader(){
		spamReader sr=new spamReader();
		sr.readFile();
		//list<messageInput> temp=globalClass.hashOfMessageInput.convertToList();
		//System.out.println(temp.getSize());
		//for (int i=0;i<temp.getSize();i++)
		//	System.out.println(temp.getIt(i).showInfo());
		//System.out.println("\n\n---------------------\nTOTAL OBJECTS CREATED"+
		//					"\n---------------------\n"+temp.getSize());
	}
	/**
	 * Method runs tests the queue
	 */
	public static void testQueue(){
		queue<String> qstring=new queue<String>();
		qstring.inqueue("hey tehre");
		qstring.inqueue("ok there");
		qstring.inqueue("WUT U WANT U");
		System.out.println(qstring.getSize());
		qstring.showContents();
		try{
		System.out.println(qstring.unqueue());
		qstring.showContents();
		System.out.println(qstring.peek());
		qstring.showContents();
		qstring.inqueue("ASSSS");
		qstring.showContents();
		System.out.println(qstring.getSize());
		System.out.println("Verifying empty.."+qstring.isEmpty());
		qstring.unqueue();
		qstring.unqueue();
		qstring.unqueue();
		qstring.unqueue();
		qstring.unqueue();
		qstring.showContents();
		System.out.println("Verifying empty.."+qstring.isEmpty());
		}catch(emptyListException e){
			System.out.println("List is empty");
		}
	}
	/**
	 * method tests the Hash
	 */
	public static void testHash(){
		hash<messageInput> hashOfMessageInput=new hash<messageInput>(new messageInput());
		messageInput globalMIN=new messageInput();
		messageInput mI1=new messageInput();
		messageInput mI2=new messageInput("123213423",80,globalMIN.new connectionInfo("google.com",
				"92.32.43.343",27015),globalMIN.new virusResultInfo("32434223434","abcd123@lol.com",true,"trojan"),
				globalMIN.new spamResultInfo("32343424323@ele.com",17,45,1000,true),343343);
		hashOfMessageInput.addToHashTable(mI1);
		hashOfMessageInput.addToHashTable(mI2);
		System.out.println("verifying..."+hashOfMessageInput.contains(mI2));
		//hashOfMessageInput.addToHashTable(mI2);
		//hashOfMessageInput.displayHash();
		//System.out.println(hashOfMessageInput.computeHashValue(mI1.toString()));
		//hashOfMessageInput.displayHash();
	}
	/**
	 * method tests the list of list
	 */
	public static void testListOfList(){
		list<Integer> inter=new list<Integer>();
		list<Double> inter2=new list<Double>();
		inter.addToStart(233);
		inter.addToStart(232);
		inter2.addToStart(2.34);
		inter2.addToStart(4.34);
		listOfList o=new listOfList();
		//o.addToStart(inter);
		//o.addToStart(inter2);
		for (int i=0;i<o.getSize();i++){
			System.out.println("At index "+i+"\n----------------------");
			list a=o.getList(i);
			Object temp=a.getIt(0);
			System.out.println(temp.getClass());
			a.showContents();
		}
	}
	/**
	 * method tests the list
	 */
	public static void testList(){
		list<messageInput> lMin=new list<messageInput>();
		messageInput globalMIN=new messageInput();
		messageInput m1=new messageInput();
		messageInput m2=new messageInput("123213423",80,globalMIN.new connectionInfo("google.com",
				"92.32.43.343",27015),globalMIN.new virusResultInfo("32434223434","abcd123@lol.com",true,"trojan"),
				globalMIN.new spamResultInfo("32343424323@ele.com",17,45,1000,true),343343);
		messageInput m3=new messageInput("3243244",80,null,null,null,123);
		lMin.addToStart(m1);
		lMin.addToStart(m2);
		lMin.addToStart(m3);
		lMin.showContents();
		System.out.println("Verifying if contains m1..."+lMin.contains(m1));
		System.out.println("size is..."+lMin.getSize());
		try{
			lMin.insertAtIndex(m1, 10);
		}
		catch(outOfBoundsException e){
			System.out.println(e.getMessage());
		}
		try{
			lMin.insertAtIndex(m1,1);
		}
		catch(outOfBoundsException e){
			System.out.println(e.getMessage());
		}
		lMin.showContents();
		
	}
	/**
	 * method tests the unknown line exception
	 */
	public static void testUnknownLineException(){
		try{
			//throw new unknownLineException();
			//throw new unknownLineException(133);
			throw new unknownLineException(new qmailReader(),133);
		}
		catch(unknownLineException e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * method tests the messageInput
	 */
	public static void testMessageInput(){
		messageInput globalMIN=new messageInput();
		messageInput mI1=new messageInput();
		//System.out.println(mI1);
		messageInput mI2=new messageInput("123213423",80,globalMIN.new connectionInfo("google.com",
				"92.32.43.343",27015),globalMIN.new virusResultInfo("32434223434","abcd123@lol.com",true,"trojan"),
				globalMIN.new spamResultInfo("32343424323@ele.com",17,45,1000,true),343343);
		System.out.println(mI2);
	}
	
}
