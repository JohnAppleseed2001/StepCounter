
public class NaiveData {
	
	public static void main (String[] args){
		CSVData a = CSVData ;
		double[][] sensorData = ;
		
	}
	
	
	private static int countSteps(double[][] sensorData){
		double[] Mag = calculateMagFor(sensorData);
		double mean = calculatemean(Mag);
		double standarDev = calculateStandardDeviation(Mag, mean);
		double allow = mean + standarDev;
		int stepCount = 0;
		
		for(int i = 0; i < Mag.length-1; i++){
			if(Mag[i] > Mag[i-1] && Mag[i] > Mag[i+1] && Mag[i] > allow)
				
				stepCount ++;
			
		}
		
		return stepCount; 
	
	}

	public static double calculateMag(double x, double y, double z){
		return Math.sqrt(x*x + y*y + z*z );
	}

	private static double[] calculateMagFor(double[][] sensorData){
		double[] mag = new double[sensorData.length];

		int tempX = 0;
		int tempY = 0;
		int tempZ = 0;

		for(int i = 0; i < sensorData.length; i ++){

			for(int col = 0; col < sensorData[0].length; col++){

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

		for(int i = 0; i < mag.length; i++) {
			total += mag[i]; 
		}

		return total / mag.length;

	}

}
