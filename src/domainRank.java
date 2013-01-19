/**
 * domainRanker
 * @author Wadih El-Ghoussoubi(Woody)
 *
 */
public class domainRank {
	private int cntr;//counter for each domain, to be able to rank them
	private String domain;//the domain to be ranked
	/**
	 * default constructor
	 */
	public domainRank(){
		cntr=-1;
		domain="NOT_SET";
	}
	/**
	 * Parameterized constructor
	 * @param c for the counter
	 * @param d for the domain name
	 */
	public domainRank(int c, String d){
		cntr=c;
		domain=d;
	}
	/**
	 * counter incrementer
	 */
	public void incrementCounter(){
		cntr++;
	}
	/**
	 * returns the counter of a domain
	 * @return cntr
	 */
	public int getCounter(){
		return cntr;
	}
	/**
	 * returns the domain
	 * @return domain
	 */
	public String getDomain(){
		return domain;
	}
	/**
	 * toString method that returns a string of the information
	 */
	public String toString(){
		return "domain: "+ domain+" Counter: "+cntr;
	}
	/**
	 * checks for equality of 2 domains
	 * @param dRank the object of this class
	 * @return boolean 
	 */
	public boolean equals(domainRank dRank){
		return domain.equals(dRank.getDomain());
	}
	
}
