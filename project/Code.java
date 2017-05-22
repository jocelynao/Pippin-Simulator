package project;

public class Code {

	public static int CODE_MAX = 1024;
	private long[] code = new long[CODE_MAX];

	public void setCode(int index, int op, int indirLvl, int arg) {
		long longOp = op*8;
		longOp += indirLvl;
		long longArg = arg;
		long OpAndArg = longOp << 32;
		longArg = longArg & 0x00000000FFFFFFFFL;
		code[index] = OpAndArg | longArg;
	}

	int getOp(int i) {
		return (int)(code[i] >> 35);
	}

	int getIndirLvl(int i) {
		return (int)(code[i] >> 32)%8;
	}

	int getArg(int i) {
		return (int)(code[i] & 0x00000000FFFFFFFFL);
	}

	public void clear (int start, int length){
		for (int i = start; i < start + length - 1; i++)
		{
			code[i] = 0;
		}
	}

	//THIS ONE
	public String getText(int i){
		StringBuilder bldr = new StringBuilder();
		String mnem = InstructionMap.mnemonics.get(getOp(i));
		bldr.append(mnem);
		int x = getIndirLvl(i);

		//		bldr.append(InstructionMap.mnemonics.get(getOp(i)));
		//		System.out.println(bldr);
		//		int x = getIndirLvl(i);
		if (x == 0){
			if (!InstructionMap.noArgument.contains(mnem)){
				bldr.append("I ");
			}
			else{
				bldr.append(" ");
			}
		}
		else if (x == 1){
			bldr.append(" ");
		}
		else if (x == 2){
			bldr.append(" [");
		}
		else{
			bldr.append("A ");
		}

		//		System.out.println(bldr);

		int arg = getArg(i);
		if (arg >= 0){
			bldr.append(Integer.toHexString(arg).toUpperCase());
		}
		else{
			bldr.append("-" + Integer.toHexString(-arg).toUpperCase());
		}

		if (x == 2){
			bldr.append("]");
		}

		return bldr.toString();

	}

	public String getHex(int i)
	{
		int arg = getArg(i);
		String args = " ";
		if(arg < 0)
		{
			args += "-";
		}
		if(arg == getArg(i))
		{
			return Integer.toHexString(getOp(i)).toUpperCase() + " " + Integer.toHexString(getIndirLvl(i)).toUpperCase() +
				args + Integer.toHexString(arg).toUpperCase();
		}
		return "";
		
	}

	public static void main(String[] args) {
		Code c = new Code();
//		for(int i = 0; i <= 3; i++) {
//			c.setCode(2*i, 12, i, 2015);
//			System.out.print(c.getText(2*i) + ", ");
//			System.out.print(c.code[2*i] + ", ");
//			System.out.print(c.getOp(2*i) + " ");
//			System.out.print(c.getIndirLvl(2*i) + " ");
//			System.out.println(c.getArg(2*i));
//			c.setCode(2*i+1, 12, i, -2015);
//			System.out.print(c.getText(2*i+1) + ", ");
//			System.out.print(c.code[2*i+1] + ", ");
//			System.out.print(c.getOp(2*i+1) + " ");
//			System.out.print(c.getIndirLvl(2*i+1) + " ");
//			System.out.println(c.getArg(2*i+1));
//		}
//		c.setCode(8, 0, 0, 0);	//NEW
//		c.setCode(9, 8, 0, 0);	//NEW
//		c.setCode(10, 15, 0, 0);	//NEW
//		System.out.println(c.getText(8));	//NEW
//		System.out.println(c.getText(9));	//NEW
//		System.out.println(c.getText(10));	//NEW
	}

}

