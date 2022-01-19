 
/*****************************************************
 * Student Name:	 Patricia Reyes
  	File Name:		 Score.java
  	Assignment 		 number 1
******************************************************/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 *  @author Patricia Reyes
 *  @version 1.0 (10/21/2021)
 *   
 */

public class ScoreStatistics {
	
	double[] listSD = new double[5];
	int scoresPerDay =24;
	int numberOfDays =5;
	
	String[] dates = new String[numberOfDays];
	int[][] scores = new int[numberOfDays][scoresPerDay];
	double[] allMeans= new double[numberOfDays];
	
	/**
	 * Constructor
	 */
	
	public ScoreStatistics() {
				
		System.out.println("Means and Standard Deviations of Score");
		readScores();
		showAllDates();
		showScores();
		showMean();
		showSD(listSD);
		System.out.println(" ");
		System.out.println("Significant Differences in Mean Scores");
		showDatesForMean();
		tTest();
	}
	
	/**
	 * Reads the text file dailyScore.txt and adds the data to a dates[] String array or scores[][] double 2d array
	 */	
	public void readScores() {
				
		File fileInput = new File("dailyScores.txt");
		String newDate="";
		
		int dateIndex=0;
		int scoreDateIndex=0;
		int scoresForDay=0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(fileInput))) {
		  
	        //String[] data;
		    String line;
		  
		    //Iterate through each line in text file until no lines remain
		    while ((line = br.readLine()) != null) {
		    
		    	//remove any white space
		    	line = line.replace(" ", "");
		    
		    	//If a line contains a backslash, its a date, add to date array
		       	if(line.contains("/")) {
		    	
		    		newDate = line;
		    	   dates[dateIndex]=newDate;
		    	   dateIndex+=1;
		       }
		    
		    	//otherwise its a score value, add it to its appropriate spot in 2d array
		       else {
		    	   scores[scoreDateIndex][scoresForDay]=Integer.parseInt(line);
		    	   scoresForDay+=1;
		  
		    	   //Each date has a set number of scores, move to next column in 2d array when limit for day is reached
		    	   if(scoresForDay>=scoresPerDay) {
		    		   scoresForDay=0;
		    		   scoreDateIndex+=1;
		    		   
		    	   }
		       }
		    }
		} 
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * Display raw score values formatted by date in a column, use tab character to organize data
	 */	
	public void showScores() {
		
		for(int i=0;i<scoresPerDay;i++) {
			System.out.print(i+1+"\t");
			for(int k=0;k<numberOfDays;k++) {
				System.out.print(scores[k][i]+"\t\t");
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Display mean value of scores per date
	 */
	public void showMean(){
		
		double newMean =0;
		double sum=0;
		//double newSD=0;
	
		System.out.println("******************************************************************************");
		System.out.print("Mean\t");
	
		for(int i=0;i<scores.length;i++) {
			newMean =0;
			sum=0;
			for(int k=0;k<scores[i].length;k++) {
				sum+=scores[i][k];
			}
			
			newMean = sum/scoresPerDay;
			//Store all mean values for each day to be used in standard deviation calculation later
			allMeans[i]=newMean;
			//Format double value to include only 2 decimal values
			System.out.printf("%.2f",newMean);System.out.print("\t\t");
		}
		System.out.println();
	}
	
	/**
	 * Display Standard Deviation (SD) value per date
	 * @param listSD array will show the standard deviation for each of the dates
	 * 
	 */		
	public void showSD(double[] listSD){
				
		double sum=0;
		double newSD=0;
				
		System.out.print("SD\t");
		for(int i=0;i<scores.length;i++) {
			newSD =0;
			sum=0;
			for(int k=0;k<scores[i].length;k++) {
				//Subtract score from mean and square result, and then add to a sum
				sum+=Math.pow(allMeans[i]-scores[i][k],2);
			}
			//square root the sum divded by scores per day
			newSD = Math.sqrt(sum/scoresPerDay);
			listSD[i] = newSD;
			//Again, format double value to include only 2 decimal values
			System.out.printf("%.2f",newSD);System.out.print("\t\t");
		}
		System.out.println();
	}
	
	
	/**
	 * Show all Date values   
	 */
	public void showAllDates() {
		System.out.print("Student\t");
		for(int i=0;i<dates.length;i++) {
			System.out.print(dates[i]+"\t");
		}
		System.out.println();
	}
	
	
	/**
	 * Show the dates for the different Mean Scores
	 */
	
	public void showDatesForMean() {
		System.out.print("\t\t");
		for(int i=1;i<dates.length;i++) {

			System.out.print(dates[i]+"\t");
		}
		System.out.println();
		
	}
	/**
	 * Adds different amount of padding per line
	 * @param j line number
	 * @return str
	 */

	public static String getPadding(int j) {
		String str="\t";
		for(int i=0;i<j*2;i++) {
			str+="\t";
		}
		return str;
	}
	
	/**
	 * computes the significant Differences in mean scores
	 */
	public void tTest(){
		
		int i = 0;
		int j = 0;
		
		for(i = 0; i<4; i++) {
			
			System.out.print("\n" + dates[i] +" ");
			String padding = getPadding(i);
			System.out.print(padding);
			for(j = i + 1; j<5; j++) {
				
			//calculating value for t-test
		double t_test = ( allMeans[i] - allMeans[j]) /(double)Math.sqrt((listSD[i] * listSD[i+1]) /24 + (listSD[j] * listSD[j]) / 24);
			//if value of t-test is greater or  equal to 2.25 printing Y else N
		if(Math.abs(t_test)>= 2.25) {
				System.out.print("Y\t\t");
			
		}
			else {
				System.out.print("N\t\t");
				}
		
			}
		}
	}		
	
	/**
	 * Will display all the different computations in table form
	 * @param args supplies command-line arguments 
	 */	
	public static void main(String[] args) {
			
		new ScoreStatistics();
	}


}