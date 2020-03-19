import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Matrisong final project for computer music.
 * 
 * @author Rom
 *
 */
public class MatriSong {

	//switches the song mode, original or existing song for noobnotes:
	public static String songMode;
	
	//if EXISTNG mode then enter the song file path:
	public static String filePath;
	
	//if ORIGINAL mode then enter lyrics and melody file path:
	public static String lyricsFilePath;
	public static String melodyFilePath;
	
	//switch single word match mode on or off:
	public static String singleWordMatch;
	
	//chooses the number of consecutive notes to catch:
	public static int numOfNotes;

	
	/**
	 * the main function of martixSong
	 * @throws IOException 
	 */
	public static void matrixSong() throws IOException {

		String[] lyrics;
		String[] melody;
		int[][] lyrMatrix;
		int[][] melMatrix;
		
		if(songMode.equals("ORIGINAL")){
			lyrics = lyricsToArray(lyricsFilePath);
			melody = melodyToArray(melodyFilePath);
			
			lyrMatrix = arrayLyrToMatrix(lyrics);
			melMatrix = arrayMelToMatrix(melody);
			
			show(lyrMatrix, melMatrix);
		}
		
		if(songMode.equals("EXISTING")){
			String [] newFilePath = filePath.split("\\.");
			String path = newFilePath[0];
			songSplit(filePath, path+" L.txt", path+" M.txt");

			lyrics = lyricsToArray(path+" L.txt");
			melody = melodyToArray(path+" M.txt");

			lyrMatrix = arrayLyrToMatrix(lyrics);
			melMatrix = arrayMelToMatrix(melody);

			show(lyrMatrix, melMatrix);
		}	
	}

	/**
	 * insert a song's lyrics to an array
	 * 
	 * @param fileName - the lyrics file to read from
	 * @return array of all the words
	 */
	public static String[] lyricsToArray(String fileName) {
		List<String> tmp = new ArrayList<String>();
		StdIn.setInput(fileName);

		while (!StdIn.isEmpty())
			tmp.add(StdIn.readString().toLowerCase());

		String[] ans = tmp.toArray(new String[0]);

		for (String s : ans)
			s = s.replaceAll("[ |!|\"|#|$|%|&|'|(|)|*|+|,|-|.|/|:|;|<|=|>|?|@|\\|^|_|`|[|]|~|{|}]+", "");

		return ans;
	}

	/**
	 * Enter the notes from the file into an array and return it
	 * @param filePath - the path of the file
	 * @return an array with notes
	 * @throws IOException
	 */
	public static String[] melodyToArray(String filePath) throws IOException {
		// Creating a list to add the notes to
		List<String> listNotes = new ArrayList<String>();
		String[] ans;

		// Open the file
		FileInputStream fstream = new FileInputStream(filePath);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		String line;

		//Read file line by line
		while ((line = br.readLine()) != null)   {

			// split the line by space and enter the notes to an array
			String[] sourceAry = line.split(" ");

			// add the notes to the list
			for (String note : sourceAry)
				listNotes.add(note);
		}

		//Close the input stream
		br.close();

		// Convert from list to array
		ans = listNotes.toArray(new String[0]);
		return ans;
	}

	/**
	 * Creating lyrics and melody files
	 * @param filePath - the path of the file
	 * @throws IOException
	 */
	public static void songSplit(String source, String lyr, String mel) throws IOException
	{	
		File lyrics = new File(lyr);
		File melody = new File(mel);

		// Create new empty files if the file does not exist 
		lyrics.createNewFile();
		melody.createNewFile();

		// Open the file
		FileInputStream fstream = new FileInputStream(source);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

		// Write to files
		BufferedWriter writeToLyrics = new BufferedWriter(new FileWriter(lyr, false));
		BufferedWriter writeToMelody = new BufferedWriter(new FileWriter(mel, false));

		String line;

		// The index of the current line being processed
		int lineNumber = 0;

		//Read file line by line
		while ( (line = br.readLine()) != null ) 
		{
			// in case the line is not empty/all spaces
			if (line.trim().length() > 0)
			{
				// the notes are in even lines
				if (lineNumber%2 == 0)
				{

					// replace '-' with single space
					String lineWithoutMinus = line.replace("-", " ");

					// replace one or more consecutive spaces with a single space
					String newLine = lineWithoutMinus.trim().replaceAll(" +", " ");

					writeToMelody.write(newLine);

					writeToMelody.newLine();
				}

				// the lyrics are in odd lines
				else
				{
					writeToLyrics.write(line);

					writeToLyrics.newLine();
				}
				lineNumber++;
			}
		}

		//Close the streams
		br.close();
		writeToLyrics.close();
		writeToMelody.close();
	}

	/**
	 * converts a lyrics array into a similarity matrix
	 * 
	 * @param arr - the array to make a matrix from
	 * @return the similarity matrix
	 */
	public static int[][] arrayLyrToMatrix(Object[] arr) {
		int[][] matrix = new int[arr.length][arr.length];

		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				matrix[i][j] = 0;

		if(singleWordMatch.equals("ON")){
			for (int i = 0; i < arr.length; i++) {
				matrix[i][i] = 1;
				for (int j = 0; j < i; j++) {
					if (arr[j].equals(arr[i])) {
						matrix[i][j]=1;
						matrix[j][i]=1;
					}
				}
			}
		}

		if(singleWordMatch.equals("OFF")){
			for (int i = 0; i < arr.length-1; i++) {
				matrix[i][i] = 1;
				matrix[i+1][i+1] = 1;
				for (int j = 0; j < i; j++) {
					if (arr[j].equals(arr[i]) && arr[j+1].equals(arr[i+1])) {
						matrix[i][j]=1;
						matrix[j][i]=1;
						matrix[i+1][j+1]=1;
						matrix[j+1][i+1]=1;

					}
				}
			}	
		}
		return matrix;
	}

	/**
	 * converts a melody array into a similarity matrix
	 * @param arr
	 * @return
	 */
	public static int[][] arrayMelToMatrix(Object[] arr) {
		int[][] matrix = new int[arr.length][arr.length];
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++){
				matrix[i][j] = 0;
				if(i==j)
					matrix[i][j]=1;
			}
		for (int i = 0; i < arr.length - (numOfNotes-1); i++) {
			for (int j = 0; j < i; j++) {
				boolean flush = true;
				for (int k = 0; k < numOfNotes; k++) {
					if(!arr[j+k].equals(arr[i+k])){
						flush = false;
						break;
					}
				}
				if(flush){
					for (int k = 0; k < numOfNotes; k++) {
						matrix[i+k][j+k] = 1;
						matrix[j+k][i+k] = 1;
					}
				}
			}
		}
		return matrix;
	}

	/**
	 * showing a matrix on a drawing canvas
	 * @param matrix - the matrix to be shown
	 */
	public static void show(int[][] lyrics, int[][] melody) {
		double size = 720;
		StdDraw.setCanvasSize((int) size, (int) size);
		StdDraw.setScale(0, size);
		StdDraw.clear(StdDraw.DARK_GRAY);

		if (lyrics != null) {
			System.out.println("Number of words: "+lyrics[0].length);
			double ratio1 = size / (double) lyrics.length;
			Color orange = new Color(255, 200, 0, 200);
			StdDraw.setPenColor(orange);
			for (int i = 0; i < lyrics.length; i++) {
				for (int j = 0; j < lyrics[i].length; j++) {
					if (lyrics[i][j] == 1)
						StdDraw.filledSquare(0 + j * ratio1, size - i * ratio1, ratio1 / 2);
				}
			}
		}
		
		if (melody != null) {
			System.out.println("Number of notes: "+melody[0].length);
			double ratio2 = size / (double) melody.length;
			Color green = new Color(0, 255, 0, 100);
			StdDraw.setPenColor(green);
			for (int i = 0; i < melody.length; i++) {
				for (int j = 0; j < melody[i].length; j++) {
					if (melody[i][j] == 1)
						StdDraw.filledSquare(0 + j * ratio2, size - i * ratio2, ratio2 / 2);
				}
			}
		}
	}
}