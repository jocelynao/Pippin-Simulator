package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Assembler{

	public static void assemble(File input, File output, ArrayList<String> errors){

		ArrayList<String> code = new ArrayList<>();
		ArrayList<String> data = new ArrayList<>();
		try (Scanner reader = new Scanner(new FileReader(input))){
			boolean found = false;
			while(reader.hasNextLine()){
				String str = reader.nextLine();
				//System.out.println(str);
				if(!str.trim().startsWith("--")){
					if (!found){
						code.add(str);
					}
					else{
//						System.out.println(str);

						data.add(str);
					}
				}
				else{
					found = true;
				}
			}
			reader.close();
			//System.out.println(code);

			ArrayList<String> outText = new ArrayList<>();

			for (String line : code){
//				System.out.println(line);
				String[] parts = line.trim().split("\\s+");
				int indirLvl = 1; 
				
//				if (parts.length == 3){
//					System.out.println("HI");
//				}

				if (parts.length == 2){
					if (parts[1].startsWith("[")){
						parts[1] = parts[1].substring(1, parts[1].length() - 1);
						indirLvl = 2;
					}
					if (parts[0].endsWith("I")){
//						System.out.println(parts[0]);
//						System.out.println(parts[1]);
//						System.out.println("HE");
						indirLvl = 0;
					}
					if (parts[0].endsWith("A")){
						indirLvl = 3;
				}

				}
//				System.out.println(parts[0]);
				int opcode = InstructionMap.opcode.get(parts[0]);
//				System.out.println(opcode);
//				System.out.println("E");
				if (parts.length == 1){
//					System.out.println("HI");
					outText.add(Integer.toHexString(opcode).toUpperCase() + " 0 0");
				}
				if (parts.length == 2){
//					System.out.println(parts[0]);
//					System.out.println(parts[1]);
//					System.out.println("HELO");
//					System.out.println(parts[0]);
//					System.out.println(Integer.toHexString(opcode));

					outText.add(Integer.toHexString(opcode).toUpperCase() + " " + indirLvl + " " + parts[1]);
				}
			}

			outText.add("-1");
			outText.addAll(data);

			try (PrintWriter out = new PrintWriter(output)){
				for(String s : outText) out.println(s);
			} catch (FileNotFoundException e) {
				errors.add("Cannot create output file");
			}
		}

		catch (FileNotFoundException e)
		{
			errors.add("Input file does not exist");
			return;
		}

	}

	public static void main(String[] args) {
		ArrayList<String> errors = new ArrayList<>();
//		assemble(new File("data/01.pasm"), new File("out.pexe"), errors);	
//		System.out.println(errors);
	}

}