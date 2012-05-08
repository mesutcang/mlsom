package som;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 File file = new File("iris.data");
	        String[] tempTable;
	        String[] tempList;
	        int rows = 0;
	        try{
	            FileReader fr = new FileReader(file);
	            BufferedReader input = new BufferedReader(fr);
	            String line;
	           
	            while((line = input.readLine()) != null){
	                
	                rows ++;
	                tempTable = line.split(",");
	                int tableLenght = tempTable.length;
	                tempList = new String[tableLenght];
	                for(int i = 0; i< tableLenght; i++){
	                    tempList[i] = tempTable[i];
	                }
	                System.out.println(tempList);
	            	/*
	            	rows ++;
	                tempTable = line.split(",");
	                
	                
	                
	                int tableLenght = tempTable.length;
	                tempList = new String[tableLenght];
	                for(int i = 0; i< tableLenght; i++){
	                    tempList[i] = tempTable[i];
	                }*/
	                //dataList.add(tempList);     
	             }
	            fr.close();
	            
	        }catch(IOException e){
	            System.out.println("Error: " + e);
	        }

	}

}
