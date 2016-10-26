import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;

public class NaiveData {

	public static String datafile = "C:/Users/Rohan/workspace/CSVData/NoiseSmoothing/data";

	public static void main (String[] args){
		CSVData dataset = CSVData.createDataSet("C:/Users/Rohan/workspace/CSVData/NoiseSmoothing/data/walkingSampleData-out.csv", 0);
		System.out.println(countSteps(dataset.getAllData()));
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

	private static int countSteps(double[][] sensorData){
		double[] Mag = calculateMagFor(sensorData);
		System.out.println(Arrays.toString(Mag));
		double mean = calculatemean(Mag);
		double standarDev = calculateStandardDeviation(Mag, mean);
		double allow = mean + standarDev;
		int stepCount = 0;

		for(int i = 1; i < Mag.length - 1; i++){

			if(Mag[i] > Mag[i-1] && Mag[i] > Mag[i+1])

				stepCount ++;

		}

		return stepCount/2; 

	}

	public static double calculateMag(double x, double y, double z){
		return Math.sqrt(x*x + y*y + z*z );
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
