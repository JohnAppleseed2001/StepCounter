
public class Bag {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:" + '\n' + '\r';
	private static final String SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static final String DEFAULT_ALPHABET = ALPHABET;
	private Dictionary d = new Dictionary();
	private static int[] letterFreq;

	public Bag(){
		letterFreq = new int[26];
	}

	public static void add(String Letter){
		int index = getIndex(Letter);
		letterFreq[index]++;
	}

	private static int getIndex(String lower){
		return ALPHABET.indexOf(lower);
		
	}

	public static int getTotalWords(){
		int counter = 0;
		for(int i = 0; i < letterFreq.length-1;i++){
			counter += letterFreq[i];
		}
		return counter;
	}

	public static int getNumUniqueWords(){
		int counter = 0;
		for(int i = 0; i < letterFreq.length-1; i++){
			if(letterFreq[i] > 0)counter++;
		}
		return counter;
	}

	public static int getNumOccurances(String letter){
		int index = getIndex(letter);
		return letterFreq[index];
	}

	public static String getMostFrequent(){
		int max = 0;
		int maxIndex = 0;
		int temp = 0;
		for(int i = 0; i < letterFreq.length;i++){
			temp = getNumOccurances(ALPHABET.substring(letterFreq[i],letterFreq[i]+1));	
			if(temp > max){
				max = temp;
				maxIndex = letterFreq[i];
			}
		}
		return ALPHABET.substring(maxIndex, maxIndex+1);
	}

}
