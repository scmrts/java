package com.lgcns.test.vo;

public class Bus {
	public String time;
	public String name;
	public int from;
	public Bus before;
	public Bus after;
	
	
	public String toString() {
		return String.format("%s, %s, %d", this.time, this.name, this.from);
	}
	
	public void printSequence() {
		String format = String.format("%s#%s#%s,%05d#%s,%05d", 
				this.time, this.name, 
				this.after != null ? this.after.name : "NOBUS", 
						this.after != null ? this.after.from - this.from : 0, 
								this.before != null ? this.before.name : "NOBUS", 
										this.before != null ? this.from - this.before.from : 0);
		System.out.println(format);
	}
}
