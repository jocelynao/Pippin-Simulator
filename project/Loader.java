package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Loader {
	
	public static String load(MachineModel model,
			File file, int codeOffset, int memoryOffset){
		int codeSize = 0;
		if (model == null || file == null){
			return null;
		}
		try (Scanner input = new Scanner(file)){
			boolean incode = true;
			while (input.hasNextLine()){
//				System.out.println("H");
				String line = input.nextLine();
//				System.out.println(line);
				Scanner parser = new Scanner(line);
//				System.out.println("EHS");
				int num = parser.nextInt(16);
//				System.out.println(num);
				if (incode == false){
//					System.out.println("FUECK");
//					System.out.println(line);
//					parser = new Scanner(line);
					int value = parser.nextInt(16);
//					System.out.println("EHI");
//					System.out.println(value);
//					int value = parser.nextInt(16);
//					System.out.println(value);
					model.setData(memoryOffset + num, value);
//					System.out.println(model.getData(memoryOffset + memory));
//					System.out.println("WHY");
					parser.close();
				}
				if (incode && num == -1){
					incode = false;
				}
				if (incode && num != -1){
					int indirLvl = parser.nextInt(16);
//					System.out.println(num);
					int arg = parser.nextInt(16);
//					System.out.println(arg);
//					System.out.println(codeOffset + codeSize);
					model.setCode(codeOffset + codeSize,
							num, indirLvl, arg);
					codeSize = codeSize + 1;
				}
			}
		}
		catch (FileNotFoundException e){
			System.out.println("File " + file.getName() + " Not Found");
		}
		catch (ArrayIndexOutOfBoundsException e1){
			System.out.println("Array Index" + 
		e1.getMessage());
		}
		catch (NoSuchElementException e2){
			System.out.println("From Scanner: NoSuchElementException");
		}
		
		return "" + codeSize;

	}
	
//	public static void main(String[] args) {
//		MachineModel model = new MachineModel();
//		String s = Loader.load(model, new File("out.pexe"),16,32);
//		for(int i = 16; i < 16+Integer.parseInt(s); i++) {
//			System.out.println(model.getCode().getText(i));			
//		}
//		System.out.println("--");
//		System.out.println("4FF " + 
//			Integer.toHexString(model.getData(0x20+0x4FF)).toUpperCase());
//		System.out.println("0 " + 
//			Integer.toHexString(model.getData(0x20)).toUpperCase());
//		System.out.println("10 -" + 
//			Integer.toHexString(-model.getData(0x20+0x10)).toUpperCase());
//	}

}
