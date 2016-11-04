import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;

public class NaiveData {

	//"C:/Users/Rohan/workspace/StepCounter/NoiseSmoothing-master/data/walkingSampleData-out.csv"
	//"C:/Users/Rohan/workspace/StepCounter/NoiseSmoothing-master/data/64StepsInPocketJogging-out.csv"
	//"C:/Users/Rohan/workspace/StepCounter/NoiseSmoothing-master/data/64StepsInHandJogging-out.csv"

	public static void main (String[] args){
		
		
		
	}

	public static void ChangeTime(double[][] sensorData) {
		int changeAmount = (int) sensorData[0][0];
		for(int i = 0; i < sensorData.length-1; i++){

			sensorData[i][0] = sensorData[i][0] - changeAmount;

		}

	}

	public static Writer writeDatatoFile (){

		Writer writer = null;

		try {

			writer = new BufferedWriter(new OutputStreamWriter(

					new FileOutputStream("filename.txt"), "utf-8"));

			writer.write("Something");

		} catch (IOException ex) {

		} finally {

			try {writer.close();} catch (Exception ex) {/*ignore*/}

		}

		return writer;

	}

	public static int countSteps(double[][] sensorData, double time){

		double[] Mag = calculateMagFor(sensorData);
		double mean = calculatemean(Mag);
		double standarDev = calculateStandardDeviation(Mag, mean);
		double allow = mean + 0.45 * standarDev;
		int stepCount = 0;
		int g = (int) (300/time);
		
		for(int i = 1; i < Mag.length - 1; i++){

			if(Mag[i] > Mag[i-1] && Mag[i] > Mag[i+1] && Mag[i] >= allow) {

				stepCount ++;

				i += g; // every interval of mag is 0.111 of a second so a step cannot be taken in less than 0.3 seconds

			} 

		}

		return stepCount; 

	}

	public static double calculateMag(double x, double y, double z){
		return Math.sqrt(x*x + y*y + z*z);
	}

	private static double[] calculateMagFor(double[][] sensorData){
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

	private static double calculateStandardDeviation(double[] mag, double mean){

		double temp = 0;

		for(int i = 0; i < mag.length-1; i ++){

			temp += (mag[i] - mean) * (mag[i] - mean);

		}

		return 	Math.sqrt(temp / mag.length-1);

	}

	private static double calculatemean(double[] mag){

		double total = 0;

		for(int i = 0; i < mag.length - 1; i++) {
			total += mag[i]; 
		}

		return total / mag.length;

	}

}
