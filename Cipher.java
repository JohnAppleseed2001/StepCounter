import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Cipher {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:" + '\n' + '\r';
	private Dictionary d = new Dictionary();
	private static int[] letterFreq;
	private static Bag bag = new Bag();

	public static void main(String[] args){
		System.out.println(vigenereCipherEncrypt("abc","abc",ALPHABET));
	}
	
	public static String vigenereCipherCrackThreeLetter(String text, String alphabet){
		String Vord = "";
		int length = text.length();
		for(int len = 0; len < length-1; len++){
			for(int i = len; i < text.length(); i++){
				String g = text.substring(i,i+length);
				bag.add(g);
			}
			String neWorld = Bag.getMostFrequent();
			Vord += (alphabet.indexOf(neWorld)-alphabet.indexOf("e"))%alphabet.length();
		}
		text = vigenereCipherEncrypt(text, Vord, alphabet);
		return text;
	}

	public static String vigenereCipherCrack(String text,int length, String alphabet){
		String Vord = "";
		for(int len = 0; len < length-1; len++){
			for(int i = len; i < text.length(); i++){
				if(i+length < text.length()-1){
					String g = text.substring(i,i+length);
					Bag.add(g);
				}else{
					String g = text.substring(i);
					Bag.add(g);
				}
			}
			String neWorld = Bag.getMostFrequent();
			Vord += (alphabet.indexOf(neWorld)-alphabet.indexOf("e"))%alphabet.length();
		}
		text = vigenereCipherEncrypt(text, Vord, alphabet);
		return text;
	}

	public static String vigenereCipherEncrypt(String text, String EncryptWord, String alphabet){
		String Encrypt = ""; 
		String temp = "";
		int tep = 1;
		int tep2 = 0;
		for(int i = 0; i < text.length(); i += EncryptWord.length()){
			for(int g = i; g < EncryptWord.length();g++){
				tep = alphabet.indexOf(text.substring(g, g+1));
				tep2 = tep + alphabet.indexOf(EncryptWord.substring(g, g +1))+ 1;
				temp = alphabet.substring(tep2, tep2 +1);
				Encrypt  += temp;
			}
		}
		return Encrypt;
	}

	public static String vigenereCipherDecrypt(String text, String EncryptWord , String alphabet){
		String Encrypt = ""; 
		for(int i = 0; i < text.length(); i += EncryptWord.length()){
			for(int g = 0; g < EncryptWord.length();g++){
				rotationCipherEncrypt(text.substring(g, g+1),ALPHABET.indexOf(EncryptWord.substring(i, i+1)),alphabet);
				Encrypt  += rotationCipherEncrypt(text.substring(g, g+1),ALPHABET.indexOf(EncryptWord.substring(g, g+1)),alphabet);
			}
		}
		return Encrypt;
	}


	public static String rotationCipherEncrypt(String Str, int movement, String alphabet) {
		String Encrypt = "";
		int positions = 0;
		for(int i = 0; i < Str.length(); i++){
			positions = alphabet.indexOf(Str.substring(i, i+1));
			positions += movement;
			while(positions < 0){
				positions += alphabet.length();
			}
			positions %= alphabet.length();
			Encrypt += alphabet.substring(positions,positions+1);
		}	
		return Encrypt;
	}

	public static String rotationCipherDecrypt(String Str, int movement, String alphabet) {
		String Encrypt = "";
		int positions = 0;
		for(int i = 0; i < Str.length(); i++){
			positions = alphabet.indexOf(Str.substring(i, i+1));
			positions -= movement;
			while(positions < 0){
				positions += alphabet.length();
			}
			positions %= alphabet.length();
			Encrypt += alphabet.substring(positions,positions+1);
		}	
		return Encrypt;
	}

	public static String rotationCipherCrack(String cipher, String alphabet){
		for(int i = 1; i < alphabet.length(); i++){
			cipher = rotationCipherDecrypt(cipher, i, alphabet);
			if(IsEnglish(cipher)) return cipher;
		}
		return "I am sorry this is uncrackable";
	}


	/**
	 * returns true if plaintext is valid English.
	 * 
	 * @param plaintext
	 *          the text you wish to test for whether it's valid English
	 * @return boolean returns true if plaintext is valid English.
	 */
	private static boolean IsEnglish(String plaintext) {
		String[] words = getWords(plaintext);
		double count = 0;
		for(int i = 0; i <  words.length; i++) {
			if (true) count++; //d.isWord(words[i])
		}
		if (count/words.length > 0.3) return true;
		return false;
	}

	public static String[] getWords(String input) {

		if (!(input.substring(input.length()-1, input.length() ) == " ")) input += " ";
		int index = 0;
		int nextIndex = 0;
		ArrayList<String> words = new ArrayList<>();
		for (int i = 0; i < input.length(); i++) {
			while (input.substring(i, i + 1) == " ") {
				i++;
			}
			index = i;
			nextIndex = input.indexOf(" ", index);
			words.add(input.substring(index, nextIndex));
			i = nextIndex;
		}
		String[] output = new String[words.size()];
		for (int j = 0; j < output.length; j++) {
			output[j] = words.get(j);
		}
		return output;
	}

	public static int CountWords(String word){
		int count = 1;
		int g = 0;
		word = word.trim();
		for(int i = 0; i <  (word.length()/2)-1;i++){
			i = word.indexOf(" ",i);
			word.substring(g, i);
			g = i;
			count++;
		}
		return count;
	}

	public Cipher(){
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