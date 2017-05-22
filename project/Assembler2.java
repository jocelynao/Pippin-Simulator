package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler2 {

	public static void assemble(File input, File output, ArrayList<String> errors){

		ArrayList<String> code = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		ArrayList<String> inText = new ArrayList<>();
		ArrayList<Integer> blankLines = new ArrayList<>();
		ArrayList<Integer> illegalBlanks = new ArrayList<>();
		
		try (Scanner reader = new Scanner(new FileReader(input))){
			while(reader.hasNextLine()){
				String str = reader.nextLine();
//				System.out.println(str);
				inText.add(str);
			}
			
			reader.close();
			boolean found = false;
			boolean illegal = false;
			int illegalStart = -1;
//			System.out.println(inText);

			for (int i = 0; i < inText.size(); i++){
				if (inText.get(i).trim().length() == 0){
//					System.out.println(inText.get(i).length());
//					System.out.println("HI");
//					System.out.println(inText);
//					System.out.println(i);
					blankLines.add(i);
					illegal = true;
					if (illegalStart == -1){
						illegalStart = i;
//						System.out.println(illegalStart);
					}
//					System.out.println("WHAT");
//					System.out.println(inText.get(i));
//					System.out.println("FDS");
				}
				else{
//					if (!blankLines.contains(i)){
						if (inText.get(i).charAt(0) == ' ' ||
								inText.get(i).charAt(0) == '\t'){
							errors.add("Error on line " + (i + 1) + ": starts with white space");
						}
						if (inText.get(i).toUpperCase().startsWith("--")){
							if (found){
								errors.add("Error on line " + (i + 1) + ": has a duplicate data separator");
							}
							if (inText.get(i).trim().replace("-", "").length() != 0){
								errors.add("Error on line " + (i + 1) + ": has a badly formatted data separator");
							}
							else{
								found = true;
							}
						}
//					}
//					System.out.println(i);
					if (illegal){
						for (int y = illegalStart; y < i; y++){
							errors.add("Error on line " + (y + 1) + ": Illegal blank line in the source file");
						}
						illegal = false;
						illegalStart = -1;
					}
				}
//				System.out.println(inText.get(i));
			}

//			System.out.println(blankLines);
		}
		
		catch (FileNotFoundException e)
		{
			errors.add("Input file does not exist");
			return;
		}
		boolean found = false;
		for (String curEle : inText){
			if (curEle.trim().startsWith("--") &&
					!found){
				found = true;
			}
			else{
				if (found){
					data.add(curEle);
				}
				else{
					code.add(curEle);
				}
			}
//			if(!curEle.trim().startsWith("--")){
//				if (!found){
//					code.add(curEle);
//				}
//				else{
//					data.add(curEle);
//				}
//			}
//			else{
//				found = true;
//			}
		}
//
		ArrayList<String> outText = new ArrayList<>();
//
		for (int i = 0; i < code.size(); i++){
			
			boolean badCode = false;
			String[] parts = code.get(i).trim().split("\\s+");
			int indirLvl = 1; 
			
			if (!blankLines.contains(i)){
				if (!InstructionMap.sourceCodes.contains(parts[0])){
					if (!InstructionMap.sourceCodes.contains(parts[0].toUpperCase())){
					errors.add("Error on line " + (i + 1) + ": contains an illegal mnemonic " + parts[0]);
					badCode = true;
					}
				}
				if (InstructionMap.sourceCodes.contains(parts[0].toUpperCase()) &&
						!InstructionMap.sourceCodes.contains(parts[0])){
					errors.add("Error on line " + (i + 1) + ": mnemonic must be upper case");
				}
				if (InstructionMap.noArgument.contains(parts[0]) && 
						parts.length != 1){
					errors.add("Errors on line " + (i + 1) + ": this mnemonic cannot take arguments");
				}
//				System.out.println(badCode);
				if (!badCode){
					if (!InstructionMap.noArgument.contains(parts[0].toUpperCase()) && 
							parts.length == 1){
						errors.add("Error on line " + (i + 1) + ": is missing an argument");
					}
					if (parts.length == 2){
						if (parts[1].startsWith("[")){
							if (!InstructionMap.indirectOK.contains(parts[0].toUpperCase())){
								errors.add("Error on line " + (i + 1) + ": cannot be done in indirect mode");
							}
							else{
								if (!parts[1].endsWith("]")){
									errors.add("Error on line " + (i + 1) + ": this argument is missing closing ']' ");
									parts[1].replace("[","");
								}
							}
							parts[1] = parts[1].substring(1, parts[1].length() - 1);
							indirLvl = 2;
						}
						if (parts[0].toUpperCase().endsWith("I")){
								indirLvl = 0;
						}
						if (parts[0].toUpperCase().endsWith("A")){
							indirLvl = 3;
						}
						int arg = 0; 
						try {
							arg = Integer.parseInt(parts[1],16);
						} catch (NumberFormatException e) {
							errors.add("Error on line " + (i + 1) 
									+ ": argument is not a hex number");
						}
		
					}
//					System.out.println(code.get(i));
					int opcode = InstructionMap.opcode.get(parts[0].toUpperCase());
					if (parts.length == 1){
						outText.add(Integer.toHexString(opcode).toUpperCase() + " 0 0");
					}
					if (parts.length == 2){
						outText.add(Integer.toHexString(opcode).toUpperCase() + " " + indirLvl + " " + parts[1]);
					}
				}
				if (parts.length >= 3){
					errors.add("Error on line " + (i + 1) + ": this mnemonic has too many arguments");
				}
			}
		}
		
		int dataStart = code.size() + 2;
		int dataIndex = code.size() + 1;
//		System.out.println(dataStart);
//		System.out.println(data);
//		System.out.println(data.size());	
//		System.out.println(blankLines);
		for (int i = 0; i < data.size(); i++){
//			System.out.println(i);
			String line = data.get(i);
//			System.out.println(data.get(i));
			String[] parts = line.trim().split("\\s+");
			if (!blankLines.contains(i + dataIndex)){
				if (parts.length != 2){
					errors.add("Error on line " + (dataStart + i) + ": data format does not consist of two numbers");
				}
				else{
					int arg = 0; 
	//				System.out.println(data.get(i));
					try {
						arg = Integer.parseInt(parts[0],16);
					} catch (NumberFormatException e) {
						errors.add("Error on line " + (dataStart + i) 
								+ ": data address is not a hex number");
					}
					arg = 0; 
					try {
						arg = Integer.parseInt(parts[1],16);
					} catch (NumberFormatException e) {
						errors.add("Error on line " + (dataStart + i) 
								+ ": data value is not a hex number");
					}
				}
			}
		}
		
		outText.add("-1");
//		outText.add("HELLO");
		outText.addAll(data);
		
		if (errors.size() == 0){
			try (PrintWriter out = new PrintWriter(output)){
				for(String s : outText) out.println(s);
			} catch (FileNotFoundException e) {
				errors.add("Cannot create output file");
			}
		}
		
	}

	public static void main(String[] args) {
		ArrayList<String> errors = new ArrayList<>();
		assemble(new File("data/10e.pasm"), new File("out2.pexe"), errors);	
		for (String curEle : errors){
			System.out.println(curEle);
		}
	}

}
