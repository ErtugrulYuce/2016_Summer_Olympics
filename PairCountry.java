package com.Olimpiads2016;


public class PairCountry implements Comparable<PairCountry>{
	private String p1;
	private String p2;
	
	public PairCountry(String p1, String p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	
	
	@Override
	public int compareTo(PairCountry o) {
		// TODO Auto-generated method stub
		if ((p1.equalsIgnoreCase(o.p1)  && p2.equalsIgnoreCase(o.p2)) || (p1.equalsIgnoreCase(o.p2) && p2.equalsIgnoreCase(o.p1))){
			
			//System.out.println("It is inside compareto: " + print(this) + " " + print(o));
			return 0;
		}
		
		//System.out.println("It is outside compareto: " + print(this) + " " + print(o));
		return (p1 + p2).compareTo(o.p1 + o.p2); // -1 veya +1

	}
	
	
	private String print(PairCountry o) {
		// TODO Auto-generated method stub
		return o.p1+ " " + o.p2;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof PairCountry) {
			//System.out.println("It is here in instance of");
			PairCountry p = (PairCountry) obj;
			return this.compareTo(p) == 0;
		}
		//System.out.println("It is outside in instance of");
		return false;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		//System.out.println("I am in hashcode");
		int total = 0;
		for (int i = 0; i < p1.length(); i++) {
			total += (int) p1.charAt(i);
		}
		for (int i = 0; i < p2.length(); i++) {
			total += (int) p2.charAt(i);
		}
		return total;
	}


	// hash - > code

	public String getP1() {
		return p1;
	}



	public void setP1(String p1) {
		this.p1 = p1;
	}



	public String getP2() {
		return p2;
	}



	public void setP2(String p2) {
		this.p2 = p2;
	}
	

}
