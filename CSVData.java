import java.io.File;

import java.io.IOException;

import java.util.Scanner;

/***

 * @author Rohan W. and Anika 

 */

public class CSVData {

	private double[][] data;

	private String[] columnNames;

	private int numRows;

	private String filePathToCSV;

	public CSVData(String filepath, String[] columnNames, int startRow) {

		this.filePathToCSV = filepath;

		String dataString = readFileAsString(filepath);

		String[] lines = dataString.split("\n");

		int n = lines.length - startRow;

		this.numRows = n;

		int numCol = columnNames.length;

		this.columnNames = columnNames;

		this.data = new double[n][numCol];

		for (int i = 0; i < lines.length - startRow; i++) {

			String line = lines[startRow + i];

			String[] coords = line.split(",");

			for (int j = 0; j < numCol; j++) {

				if (coords[j].endsWith("#"))

					coords[j] = coords[j].substring(0, coords[j].length() - 1);

				double val = Double.parseDouble(coords[j]);

				data[i][j] = val;

			}

		}

	}

	private String readFileAsString(String filepath) {

		StringBuilder output = new StringBuilder();

		try (Scanner scanner = new Scanner(new File(filepath))) {

			while (scanner.hasNext()) {

				String line = scanner.nextLine();

				output.append(line + System.getProperty("line.separator"));

			}

		} catch (IOException e) {

			e.printStackTrace();

		}

		return output.toString();
	
	}
	
		public static CSVData readCSVFile(String filename, int numLinesToIgnore, String[] columnNames) {

			return new CSVData(filename, columnNames, numLinesToIgnore);

		}

		/***

		 * Returns a new CSV Data object for a file ignoring lines at the top It

		 * uses the first row as column names. All other data is stored as doubles.

		 * 

		 * @param filename

		 *            the file to read

		 * @param numLinesToIgnore

		 *            number of lines at the top to ignore

		 * @return a CSV data object for that file

		 */

		public static CSVData readCSVFile(String filename, int numLinesToIgnore) {

			return null;

		}

		/***

		 * returns a row

		 * 

		 * @param rowIndex

		 *            index of the row to return

		 * @return the row we're returning

		 */

		public double[] getIndividualRow(int rowIndex) {

			double[] indRow = new double[data.length];

			for(int i = 0; i <data.length; i++){

				indRow[i] = data[rowIndex][i];

			}

			return indRow;

		}

		/***

		 * returns an individual column

		 * 

		 * @param columnIndex

		 *            index of column being returned

		 * @return the column we're returning

		 */

		public double[] getIndividualColumn(int columnIndex) {

			double[] indCol = new double[data.length];

			for(int i = 0; i <data.length; i++){

				indCol[i] = data[i][columnIndex];

			}

			return indCol;

		}

		/***

		 * returns multiple rows

		 * 

		 * @param startIndex

		 *            first index of rows

		 * @param endIndex

		 *            last index of rows being returned

		 * @return the rows that we're returning

		 */
		
		public double[][] getRows(int startIndex, int endIndex) {

			int index = 0;

			double[][] multipleRows =  new double [data.length][endIndex-startIndex];

			for(int g = startIndex; g < endIndex; g++){

				for(int i = 0; i < data.length-1; i++){

					multipleRows[i][index] = data[i][g];

					index++;

				}

			}

			return multipleRows;

		}

		/***

		 * returns multiple columns

		 * 

		 * @param startIndex

		 *            first index of columns

		 * @param endIndex

		 *            last index of columns

		 * @return the columns that we're returning

		 */

		public double[][] getColumns(int startIndex, int endIndex) {

			int count  = 0;

			double[][] multipleColumns =  new double [data.length][endIndex-startIndex];

			for(int g = startIndex; g < endIndex; g++){

				for(int i = 0; i < data.length; i++){

					multipleColumns[count][i] = data[g][i];

				}

				count ++;

			}

			return multipleColumns;

		}

		/***

		 * 

		 * @param rowIndex

		 *            index of the row to return

		 * @param columnIndex

		 *            index of the column to return

		 * @param newValue

		 *            new value to set existing entry to @ return new Individual

		 * entry

		 */

		public void setIndividualEntry(int rowIndex, int columnIndex, double newValue) {

			data[rowIndex][columnIndex] = newValue;

		}

		public void saveToFile(String filename) {
			//Not important right now 
		}

	}


