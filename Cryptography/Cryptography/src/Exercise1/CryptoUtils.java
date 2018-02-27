package Exercise1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CryptoUtils{
	/**
	 * @author Georgi Nikolov - b4036790
	 * 
	 * This class provides set of methods to achieve the outcomes of task one
	 * from the CW specification.
	 * 
	 * We want to "hide" the frequency analysis from users, thus we have
	 * a private class in here which provides some basic functionality,
	 * in Exercise1.CryptoUtils we deriver objects from the FrequencyAnalyser class,
	 * and use their data to try find a solution to the CW.
     *
     * Sorry for the messy class should have extracted the protected class below as separate one and
     * extend inherit from it....
	 * 
	 */

	private Integer counter =0;

    /**
	 * 
	 * @param nFile - Object representing non encrypted text file
	 * @param cFile - Object representing encrypted text file
	 */
	private FrequencyAnalyser nFile;
    private FrequencyAnalyser cFile;
	
	
	public CryptoUtils(Path normalText, Path cryptoText)
	{

		this.nFile = new FrequencyAnalyser(normalText);
		this.cFile = new FrequencyAnalyser(cryptoText);
	
	}

	public CryptoUtils(Path filePath)
	{
		this.nFile = new FrequencyAnalyser(filePath);
	}
	
	public void runFrequencyNcryptoAnalysis() throws IOException
	{
		nFile.runAnalysis();
		reportBigrams(nFile);
		reportTrigrams(nFile);

		cFile.runAnalysis();
		reportBigrams(cFile);
		reportTrigrams(cFile);
	}

	public FrequencyAnalyser getFrequencyAnalyserInstance(Path filePath)
    {// sorry for the mess bad design decisions
        return new FrequencyAnalyser(filePath);
    }
	public static boolean isValidChar(char ch){ return ch >= 'a' && ch <= 'z';}

    public FrequencyAnalyser getcFile() {
        return cFile;
    }

    public void setcFile(FrequencyAnalyser cFile) {
        this.cFile = cFile;
    }


    public FrequencyAnalyser getnFile() {
        return nFile;
    }

    public BufferedReader getBufferReader(FrequencyAnalyser file) throws IOException
    {
        return file.getBufferReader();
    }

    public Stream<String> streamFile(FrequencyAnalyser file) throws IOException
    {
        return file.getFStream();
    }

    public void setnFile(FrequencyAnalyser nFile) {
        this.nFile = nFile;
    }
	
	private Integer counter()
	{
		return this.counter++;
	}
	
	private void resetCounter(){this.counter = 0;}
	
	private void lineToBigramTrigram(String line, int separator, Map<String, Integer> someMap)
	{
		/**
		 * Helper method to build digrams/trigrams out of a line
		 * separator must have values between 2 and 3 otherwise exception will e thrown
		 */
			String lowerCaseLine = line.toLowerCase();
			boolean checkFirstTwo;
			
			if(separator>3 || separator<=1)
			{
			 throw new IllegalArgumentException("Separator size must be between 2-3");	
			}
			
			
			for (int i=0; i<=lowerCaseLine.length() - separator - 1; i++)
			{
				
                checkFirstTwo = isValidChar(line.charAt(i)) && isValidChar(line.charAt(i+1));
				if(separator==2 && checkFirstTwo){
					String addToMap = line.substring(i, i+2);
					someMap.compute(addToMap, (k, v) -> v == null ? 1 : v+1);
					counter();
				} else if(separator==3 && checkFirstTwo && isValidChar(line.charAt(i+2)))
				{
					String addToMap = line.substring(i, i+3);
					someMap.compute(addToMap, (k, v) -> v == null ? 1 : v+1);
					counter();
				}
					
			}
		 
	}

	public void printSortedMap(Map<String,Integer> map)
    {
        map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach((s) -> System.out.printf("%s - %d - %.2f %%\n ", s.getKey(), s.getValue(),
                                (double) s.getValue()/counter()*100)
                        );


    }


	public Map findBigrams(FrequencyAnalyser file) throws IOException
	{
		resetCounter();
		Map<String,Integer> digrams = new HashMap<String,Integer>();
		file.getFStream().forEach( (s) -> lineToBigramTrigram(s, 2, digrams));
		System.out.println("Counter is: " + (counter()-1));

		return digrams;

	}


	public Map findTrigrams(FrequencyAnalyser file) throws IOException
    {
        resetCounter();
        Map<String, Integer> trigrams = new HashMap<>();
        file.getFStream().forEach((s) -> lineToBigramTrigram(s,3,trigrams));
        return trigrams;
    }

	public void reportBigrams(FrequencyAnalyser file) throws IOException
    {
        System.out.println("Printing all digrams found for file " + file.getFilePath());
        printSortedMap(findBigrams(file));

    }

    public void reportTrigrams(FrequencyAnalyser file) throws  IOException
    {
        System.out.println("Printing all trigrams for file " + file.getFilePath());
        printSortedMap(findTrigrams(file));
    }


    public void encryptByFrequency(){

        Map tmp = new HashMap();

        // Not implemented as when expecting the result files from the analysis i noticed that 'xli' decrypted
		// might be 'the'
    }


	public int[] isShift()
	{
		// This could be automated but the sake of the coursework is to decrypt the text, so we don't
		// get the values programatically we assume we know them

		//xli
		int[] xli = {'x', 'l', 'i'};
		int[] the = {'t', 'h', 'e'};

		return new int[]{ xli[0] - the[0], xli[1] - the[1], xli[2]-the[2] };
		// this returns 4 all the time, so we will try to decrypt by shifting each character by 4 places,
		// if this doesn't decrypt the cypher text, then we will brute force decrypt it as we have just 26 possible keys
	}

	public char rotateCh(char ch, int key)
	{
        /**
         * Rotates character by given index, if char int value goes beyond 'z'
         * works out which is the actual value corresponding to the english alphabet
         */

        // first if to skip non interesting values
		if (!(isValidChar(ch))) return ch;

		// shared between all cyphers !!!!
        char ch1 = (char) (ch+key);

        if (isValidChar(ch1))  return ch1;
        else return (char) (ch -(26 - key));

	}

    private char rotateChOld(char ch, int key)
    {
        if (!(isValidChar(ch))) return ch;

        assert ch >= 'a' && ch <= 'z';
        int diff = ch - key;
        if (diff >= 'a') return (char) diff;
        else return (char) (diff+26);
    }


    public void decryptCeaser(int key) throws IOException
	{

		String fName= "Resources/ceaserDecrypt-" +
				new SimpleDateFormat("yyyy-MM-dd-HH-mm'.txt'").format(new Date()) + "key-" + key;

		// Assuming we are not interested in the text formatting.
		final String wf = cFile.getFStream().collect(Collectors.joining())
						 .chars()
						 .mapToObj(ch -> Character.toLowerCase((char) ch))
						 .collect(Collector.of(StringBuilder::new,
						 		(stringBuilder, str) -> stringBuilder.append(rotateChOld(str, key)),
						 		StringBuilder::append,
						 		StringBuilder::toString))
						 ;
        Files.write(Paths.get(fName), wf.getBytes());
	}


	public void bruteForceCeaser() throws IOException
	{
		for (int i = 0; i<=25; i++)
		{
			decryptCeaser(i);
		}
	}


	protected class FrequencyAnalyser {


	    /**
	     * @Author G.Nikolov - b4036790
	     *
	     * Given a file, provides set of methods to analyse letters frequency.
	     * Each instance of the class to be used with a single file.
	     * Should have used inheritance not to make this a private clas.... but too late
	     */

	    private Path filePath;
	    private Map<Character, Integer> charMap;
	    private Map<Character, Integer> sortedCharMap;
	    private int totalChars;

	    /**
	     *
	     * @param filePath - File path to file
	     */
	    protected FrequencyAnalyser(Path filePath)
	    {
	       this.filePath = filePath;
	       this.charMap = new HashMap<>();
	    }

	    private Path getFilePath() {
	        return filePath;
	    }
	    private void setFilePath(Path filePath)
	    {
	        this.filePath = filePath;
	    }
	    private Map getCharMap()
	    {
	        return charMap;
	    }
	    private void setCharMap(HashMap charMap)
	    {
	        this.charMap = charMap;
	    }
	    private Map getSortedCharMap()
	    {
	    	return sortedCharMap;
	    }
	    private Integer getCharCount(){ return this.totalChars;}
	    

	    private void lettersFrequency() throws IOException
	    {
	        /**
	         * Given a file path, scans a text file and returns populated map
	         * @return Map from constructor
	         *
	         */
	        Files.lines(this.filePath).collect(Collectors.joining())
	        		.chars()
	        		.mapToObj(i -> Character.toLowerCase((char) i))
	                .forEach( (s) ->
	                				{
	                					if (CryptoUtils.isValidChar(s))
	                					{
	                					   this.charMap.compute(s, (k, v) -> v == null ? 1 : v+1);
	                					   this.totalChars++;
	                					}
	                				});
	        
	    }
	    
	  
	    private Map sortedByFrequency()
	   
	    {
	    	
	    	/**
	    	 * @return Sorts values from a map and returns new map with sorted values
	    	 */
	    	
	    	this.sortedCharMap = this.charMap.entrySet().stream()
	    			.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
	    			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	    			(oldValue, newValue) -> oldValue, LinkedHashMap::new));
	    	
	    	return sortedCharMap;	
	    
	    }
	    
	    private Stream<String> getFStream() throws IOException
	    {
	    	/**
	    	 * Given a file path returns line stream, used for crypto analysis
	    	 */

	    	return Files.lines(this.filePath);
	    }

	    private BufferedReader getBufferReader() throws IOException
        {
            return new BufferedReader(new FileReader(this.filePath.toFile()));
        }
	    
	    private void reportStatistics()
	    {
	    	
	    	System.out.println("Frequency analysis for file: " + this.filePath.toString() );
	    	
	    	for (Entry<Character,Integer> currEntry : this.sortedCharMap.entrySet())
	    	{	
	    		System.out.printf("%c - %d - %.2f %%\n ", currEntry.getKey(), currEntry.getValue(), 
	    												(double) currEntry.getValue()/this.totalChars*100);
	    	}
	    
	    }
	    
	    
	    private void runAnalysis() throws IOException
	    {
	        lettersFrequency();
	        sortedByFrequency();
	        reportStatistics();    
	    }


	}

}

