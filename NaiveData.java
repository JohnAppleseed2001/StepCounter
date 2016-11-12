import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class NaiveData {



	public static void main (String[] args){
		
		
		CSVData a = CSVData.createDataSet("C:/Users/Rohan/workspace/StepCounter/NoiseSmoothing-master/data/Slow_walk.txt.txt", 0);
		double[][] data = a.getAllData();
		System.out.println(countStepsX(data));
		
		CSVData b = CSVData.createDataSet("C:/Users/Rohan/workspace/StepCounter/NoiseSmoothing-master/data/Med_walk.txt.txt", 0);
		double[][] data1 = b.getAllData();
		System.out.println(countStepsX(data1));
		
		CSVData c = CSVData.createDataSet("C:/Users/Rohan/workspace/StepCounter/NoiseSmoothing-master/data/Fast_walk.txt.txt", 0);
		double[][] data2 = c.getAllData();
		System.out.println(countStepsX(data2));
		
		CSVData d = CSVData.createDataSet("C:/Users/Rohan/workspace/StepCounter/NoiseSmoothing-master/data/Jog.txt.txt", 0);
		double[][] data3 = d.getAllData();
		System.out.println(countStepsX(data3));
		
		CSVData e = CSVData.createDataSet("C:/Users/Rohan/workspace/StepCounter/NoiseSmoothing-master/data/Sprint.txt.txt", 0);
		double[][] data4 = e.getAllData();
		System.out.println(countStepsX(data4));
		
	}
	/****
	 * This method allows the user to turn unix into time since data has been collecting
	 * @param sensorData the double array that is retrieved from the original CSV file
	 */
	public static void ChangeTime(double[][] sensorData) {
		int changeAmount = (int) sensorData[0][0];
		for(int i = 0; i < sensorData.length-1; i++){

			sensorData[i][0] = sensorData[i][0] - changeAmount;

		}

	}

	/****
	 * This method takes the double array of all the data and counts the number of steps in the 
	 * @param sensorData the double array that is retrieved from the original CSV file
	 * @return returns the number of steps in the data
	 */
	
	public static int countSteps(double[][] sensorData){
		double time = sensorData[0][0]- sensorData[0][1];
		double[] Mag = NaiveData.calculateMagFor(sensorData);
		double mean = calculatemean(Mag);
		double standarDev = calculateStandardDeviation(Mag, mean);
		double allow = mean + 0.45 * standarDev;
		int stepCount = 0;
		int instanceChanger = (int) (300/time);

		for(int i = 1; i < Mag.length - 1; i++){

			if(Mag[i] > Mag[i-1] && Mag[i] > Mag[i+1] && Mag[i] >= allow) {

				stepCount ++;

				i += instanceChanger;

			} 

		}

		return stepCount; 

	}
	
	public static int countStepsX(double[][] sensorData){
		
		double[] Mag = NaiveData.calculateMagFor(sensorData);
	
		int stepCount = 0;
		

		for(int i = 1; i < Mag.length - 1; i++){

			if(Mag[i] > Mag[i-1] && Mag[i] > Mag[i+1]) {

				stepCount ++;

				

			} 

		}

		return stepCount; 

	}

	/***
	 * 3d distance formula
	 * @param x acc x
	 * @param y acc y
	 * @param z acc z
	 * @return distance formula using those three inputs
	 */
	public static double calculateMag(double x, double y, double z){
		return Math.sqrt(x*x + y*y + z*z);
	}
	/***
	 * This method takes the original data and creates a double array
	 * which is filled with the combination of the x, y ,z vectors from the original data
	 * @param sensorData the double array that is retrieved from the original CSV file
	 * @return a double array that is a filled with numbers which are a combination of the x, y, z accelerometer vectors
	 */
	public static double[] calculateMagFor(double[][] sensorData){
		double[] mag = new double[sensorData.length-1];

		int tempX = 0;
		int tempY = 0;
		int tempZ = 0;

		for(int i = 0; i < sensorData.length-1; i ++){

			for(int col = 1; col < sensorData[0].length-1; col++){

				if(col == 0) tempX = (int) sensorData[i][col];
				if(col == 1) tempY = (int) sensorData[i][col];
				if(col == 2) tempZ = (int) sensorData[i][col];

			}

			mag[i] = calculateMag(tempX, tempY, tempZ);

		}

		return mag;
	}
	/***
	 * 
	 * @mag a double array that is a filled with numbers which are a combination of the x, y, z accelerometer vectors
	 * @param mean
	 * @return
	 */
	private static double calculateStandardDeviation(double[] mag, double mean){

		double temp = 0;

		for(int i = 0; i < mag.length-1; i ++){

			temp += (mag[i] - mean) * (mag[i] - mean);

		}

		return 	Math.sqrt(temp / mag.length-1);

	}
	/***
	 * this calculates the mean over an input double array
	 * @param mag a double array that is a filled with numbers which are a combination of the x, y, z accelerometer vectors
	 * @return the mean of the double array
	 */
	private static double calculatemean(double[] mag){

		double total = 0;

		for(int i = 0; i < mag.length - 1; i++) {
			total += mag[i]; 
		}

		return total / mag.length;

	}
	/***
	 * This smooths out all the small bumps in the data
	 * @param mean a double array that is a filled with numbers which are a combination of the x, y, z accelerometer vectors
	 * @return new double array that has been smoothed out
	 */
	public static double[] Smooth(double[] mean){

		double[] ret = new double[mean.length];

		for(int i = 1; i < mean.length-1; i++){

			ret[i-1] = (mean[i-1] + mean[i])/2; 

		}

		return ret;

	}

}
