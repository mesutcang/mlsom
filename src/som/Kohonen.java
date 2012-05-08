package som;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

import topology.ConstantFunctionalFactor;
import topology.KohonenNeuron;




public class Kohonen {
	 private int colNumber, rowNumber;
	    private int radius = 0;
	    private KohonenNeuron[] neuronList;
	    ArrayList <double[]> dataList = new ArrayList<double[]>();
	    private double constant;
		private int maxIteration;
		private ConstantFunctionalFactor functionalModel;
		private int UpStreamrowNumber;
		private int UpStreamcolNumber;
		private KohonenNeuron[] upStreamNeuronList;
	    
	    public String topologyToString() {
	        ArrayList tempList = new ArrayList();
	        String    conn     = "";

	        for (int i = 1; i < colNumber * rowNumber + 1; i++) {
	            tempList = topologyGetConnectedNeurons(i);
	            conn += "Neuron number " + i + " is connected with: " + tempList + "\n";
	        }
	        return conn;
	    }
	    
	    
	    
	    public String networkToString(){
	        String text = "";
	        int networkSize = neuronList.length;
	        for (int i=0; i< networkSize; i++ ){
	            text +="Neuron number "+ (i + 1) + ": " +  neuronList[i];
	            if(i < networkSize-1){
	                text += "\n";
	            }
	        }
	        return text;
	    }
	    
	    public String upStreamnetworkToString(){
	        String text = "";
	        int networkSize = upStreamNeuronList.length;
	        for (int i=0; i< networkSize; i++ ){
	            text +="Upstream Neuron number "+ (i + 1) + ": " +  upStreamNeuronList[i];
	            if(i < networkSize-1){
	                text += "\n";
	            }
	        }
	        return text;
	    }
	    
	    public KohonenNeuron[] getNeuronList() {
			return this.neuronList;
		}
	    
	    
	    public KohonenNeuron[] upStreamgetNeuronList() {
			return this.neuronList;
		}
	    
	    public ArrayList topologyGetConnectedNeurons(int neuronNumber) {
	        ArrayList connectedNeurons = new ArrayList();

	        if ((neuronNumber < colNumber * rowNumber) && (neuronNumber > 0)){
	            if (neuronNumber - colNumber > 0) {
	                connectedNeurons.add(neuronNumber - colNumber);
	            }

	            if ((neuronNumber - 1 > 0) && ((neuronNumber % colNumber) != 1)) {
	                connectedNeurons.add(neuronNumber - 1);
	            }

	            if ((neuronNumber + 1 <= colNumber * rowNumber)
	                    && ((neuronNumber % colNumber) != 0)) {
	                connectedNeurons.add(neuronNumber + 1);
	            }

	            if (neuronNumber + colNumber <= colNumber * rowNumber) {
	                connectedNeurons.add(neuronNumber + colNumber);
	            }
	        }
	      return connectedNeurons;   
	    }
	    
	    public ArrayList upStreamTopologyGetConnectedNeurons(int neuronNumber) {
	        ArrayList connectedNeurons = new ArrayList();

	        if ((neuronNumber < UpStreamcolNumber * UpStreamrowNumber) && (neuronNumber > 0)){
	            if (neuronNumber - UpStreamcolNumber > 0) {
	                connectedNeurons.add(neuronNumber - UpStreamcolNumber);
	            }

	            if ((neuronNumber - 1 > 0) && ((neuronNumber % UpStreamcolNumber) != 1)) {
	                connectedNeurons.add(neuronNumber - 1);
	            }

	            if ((neuronNumber + 1 <= UpStreamcolNumber * UpStreamrowNumber)
	                    && ((neuronNumber % UpStreamcolNumber) != 0)) {
	                connectedNeurons.add(neuronNumber + 1);
	            }

	            if (neuronNumber + UpStreamcolNumber <= UpStreamcolNumber * UpStreamrowNumber) {
	                connectedNeurons.add(neuronNumber + UpStreamcolNumber);
	            }
	        }
	      return connectedNeurons;   
	    }
	    
		public void setMap(int i, int j) {
			 this.rowNumber = i;
		     this.colNumber = j;
			
		}
		public void setNetwork(int weightNumber, double[] maxWeight) {
			
		        int numberOfNeurons = topologyGetNumbersOfNeurons();
		        neuronList = new KohonenNeuron[numberOfNeurons];
		        for (int i=0; i<numberOfNeurons; i++){
		            neuronList[i] = new KohonenNeuron(weightNumber,maxWeight,null);
		        }	
		        
		        
		        numberOfNeurons = upStreamTopologyGetNumbersOfNeurons();
		        upStreamNeuronList = new KohonenNeuron[numberOfNeurons];
		        for (int i=0; i<numberOfNeurons; i++){
		        	upStreamNeuronList[i] = new KohonenNeuron(weightNumber,maxWeight,null);
		        }	
		}
		public int topologyGetNumbersOfNeurons() {
	        return colNumber * rowNumber;
	    }
		public int upStreamTopologyGetNumbersOfNeurons() {
	        return UpStreamcolNumber * UpStreamrowNumber;
	    }
		public void setConstantFunctionalFactor(double d) {
			this.constant = d;
			
		}
		public void  parseData(String fileName){
	        File file = new File(fileName);
	        String[] tempTable;
	        double[] tempList;
	        int rows = 0;
	        try{
	            FileReader fr = new FileReader(file);
	            BufferedReader input = new BufferedReader(fr);
	            String line;
	           
	            while((line = input.readLine()) != null){
	                rows ++;
	                tempTable = line.split(",");
	                int tableLenght = tempTable.length;
	                tempList = new double[tableLenght];
	                for(int i = 0; i< tableLenght; i++){
	                	
	                	if (tempTable[i] == null || tempTable[i].trim().equals("")) {
							continue;
						}
	                    tempList[i] = Double.parseDouble(tempTable[i]);
	                }
	                dataList.add(tempList);     
	             }
	            fr.close();
	            
	        }catch(IOException e){
	            System.out.println("Error: " + e);
	        }
	    }
		
		
		public double getDistance(double[] firstVector, double[] secondVector) {
	        double distance = 0;
	        double x = 0, w = 0;
	        double sum = 0;
	        int weightSize = firstVector.length;
	        
	        if(weightSize != secondVector.length)
	            return -1;
	        
	        for(int i=0; i< weightSize; i++){
	            w = firstVector[i]; 
	            x = secondVector[i];
	            sum += (x - w) *( x - w);
	        }
	        
	        distance = Math.sqrt(sum);
	        return distance;
	    }
		public void setLearningFunction(int maxIteration,
				ConstantFunctionalFactor constantFactor) {
			this.maxIteration = maxIteration;
			this.functionalModel = constantFactor;
			
		}
		public void learn() {
			//layer 1
			
			int bestNeuron = 0;
			double[] vector;
	        
	        int dataSize = getDataSize();
	        for (int i=0; i< maxIteration; i++){

	            for(int j= 0; j<dataSize; j++){
	                vector = getData(j);
	                bestNeuron = getBestNeuron(vector);
	                changeNeuronWeight(bestNeuron, vector, i);
	                
	                
	                
	                // layer 2 
	                vector = getNeuron(bestNeuron).getWeight();
	                bestNeuron = upStreamGetBestNeuron(vector);		
	                changeNeuronWeight(bestNeuron, vector, i);
	                
	                
	                
	                
	                
	                
	            }
	        }
			
		}
		 public int getDataSize(){
		        return dataList.size();
		    }
		 public double[] getData(int index){
		        return dataList.get(index);
		    }
		 public double[] upStreamgetData(int index){
			 	return getNeuron(index).getWeight();	
		    }
		 protected int getBestNeuron(double[] vector){
			 double [] temp;
		       
		        double distance, bestDistance = -1;
		        int networkSize = topologyGetNumbersOfNeurons();
		        int bestNeuron = 0;
		        for(int i=0; i< networkSize; i++){
		            temp = getData(i);
		          
		            distance = getDistance(temp, vector);
		            if((distance < bestDistance) || (bestDistance == -1)){
		                bestDistance = distance;
		                bestNeuron = i;
		            }
		            
		        }
		        return bestNeuron;
		    }
		 
		 protected int upStreamGetBestNeuron(double[] vector){
			 double [] temp;
		       
		        double distance, bestDistance = -1;
		        int networkSize = upStreamTopologyGetNumbersOfNeurons();
		        int bestNeuron = 0;
		        for(int i=0; i< networkSize; i++){
		            temp = upStreamgetData(i);
		          
		            distance = getDistance(temp, vector);
		            if((distance < bestDistance) || (bestDistance == -1)){
		                bestDistance = distance;
		                bestNeuron = i;
		            }
		            
		        }
		        return bestNeuron;
		    }
		 public KohonenNeuron getNeuron(int neuronNumber) {
		        return neuronList[neuronNumber];
		    }
		 
		 public KohonenNeuron upStreamGetNeuron(int neuronNumber) {
		        return upStreamNeuronList[neuronNumber];
		    }
		 
		 protected void changeNeuronWeight(int neuronNumber, double[] vector, int iteration){
		        double[] weightList = getNeuron(neuronNumber).getWeight();
		        int weightNumber = weightList.length;
		        double weight;
		             
		           
		        
		        for (int i=0; i<weightNumber; i++){
		            weight = weightList[i];
		            weightList[i] += functionalModel.getValue(iteration) * getDistance(vector, getData(neuronNumber));
		        }
		       
		        
		    }

		 protected void upStreamChangeNeuronWeight(int neuronNumber, double[] vector, int iteration){
		        double[] weightList = upStreamGetNeuron(neuronNumber).getWeight();
		        int weightNumber = weightList.length;
		        double weight;
		             
		           
		        
		        for (int i=0; i<weightNumber; i++){
		            weight = weightList[i];
		            weightList[i] += functionalModel.getValue(iteration) * getDistance(vector, upStreamgetData(neuronNumber));
		        }
		       
		        
		    }

		public void setUpStreamMap(int i, int j) {
			 this.UpStreamrowNumber = i;
		     this.UpStreamcolNumber = j;
			
		}
}
