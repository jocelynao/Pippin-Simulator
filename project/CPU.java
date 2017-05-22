package project;

public class CPU {
	
	private int accum;
	private int pCounter;
	private int memBase;
	
	public int getAccum() {
		return accum;
	}
	
	public void setAccum(int accum) {
		this.accum = accum;
	}
	
	public int getpCounter() {
		return pCounter;
	}
	
	public void setpCounter(int pCounter) {
		this.pCounter = pCounter;
	}
	
	public int getMemBase() {
		return memBase;
	}
	
	public void setMemBase(int memBase) {
		this.memBase = memBase;
	}
	
	public void incrPC(){
		pCounter = pCounter + 1;
	}

}
