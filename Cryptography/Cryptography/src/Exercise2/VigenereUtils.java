package Exercise2;

import Exercise1.CryptoUtils;

import javax.swing.*;
import java.io.*;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class VigenereUtils extends CryptoUtils
{

    /**
     * @Author - Georgi Nikolov - b4036790
     * Class to encrypt and decrypt vigenere cypher, this is for the first part of exercise two from the courseework
     */

    private CryptoUtils.FrequencyAnalyser file;
    private CryptoUtils.FrequencyAnalyser file2;
    private double[] IOC;
    // used to get first character from sorted map;
    private char tempFirstVal;


    public VigenereUtils(Path filePath)
    {
        // Call super class single param constructor, get local copy of the nFile field.
        super(filePath);
        this.file = super.getnFile();
    }

    public String coincidenceIndex(Path filePath) throws IOException
    {
         final double MIN_INDEX_OF_COINCIDENCE = 0.063;
         final double MAX_INDEX_OF_COINCIDENCE = 0.068;
         final char INNER_COUNT = '~';
        // List to keep most frequent letter from each portion in case of match
        List<Character> firstValues = new ArrayList<>();

        Map<Integer, Map<Character,Double>> map = new HashMap<>();

        // new frequency analyser obj
        this.file2 = getFrequencyAnalyserInstance(filePath);
        //Starting at 1 to prevent division by zero
        int keyLength = 1;


        do {
            // looop through all possible key lenghts

            //Update IOC
            this.IOC = new double[keyLength];
            for (int b = 0; b < keyLength; b++) {
                map.put(b, new HashMap<>());
                map.get(b).put(INNER_COUNT, 0.0);
            }

            BufferedReader reader = super.getBufferReader(getFrequencyAnalyserInstance(filePath));
            int read, j = 0;
            while ((read = reader.read()) != -1) {
                read = Character.toLowerCase((char) read);
                if (CryptoUtils.isValidChar((char) read)) {
                    map.get(j % keyLength).compute((char) read, (k, v) -> v == null ? 1 : v + 1);
                    map.get(j % keyLength).compute(INNER_COUNT, (k, v) -> v + 1);
                }
                j++;
            }

            for (Map.Entry<Integer, Map<Character, Double>> currEntry : map.entrySet()) {

                double IC = 0;
                // sort the nested map
                Map<Character, Double> sortedCharMap;
                sortedCharMap = currEntry.getValue().entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

                boolean lock = true;
                for (char key : sortedCharMap.keySet()) {

                    // Calculate the overall IC per portion
                    if (key != INNER_COUNT) {
                        IC += Math.pow(sortedCharMap.get(key) / sortedCharMap.get(INNER_COUNT), 2);
                        if (lock){this.tempFirstVal = key; lock= false;};
                        System.out.format("%s - %s\n", key, sortedCharMap.get(key));
                    }

                }
                // Add first value here as map is sorted and the first element is always the inner count
                firstValues.add(this.tempFirstVal);
                System.out.format("Overall IC for portion %d is: %.3f \n", currEntry.getKey(), IC);

                //put group IC to IOC array, used to calculate when to stop the do-while loop
                this.IOC[currEntry.getKey()] = IC;

            }

            double sumIOC = Arrays.stream(this.IOC).average().getAsDouble();
            if ((MIN_INDEX_OF_COINCIDENCE<= sumIOC) || (MAX_INDEX_OF_COINCIDENCE<=sumIOC))
            {   System.out.printf("IOC for key lenght %d is %.3f \n", keyLength, sumIOC);
                System.out.printf("Found key at length: " + keyLength);
                System.out.println("Trying to build plaintext key...\n");
                StringBuilder decryptKey = new StringBuilder();
                int x = 0;
                for (char fCh : firstValues)
                {// here we try to build a plain text given that we know the most common letter in
                    // the English alphabet is 'e'


                    //int rotateBy = fCh>101 ? ((int)fCh-101)-1 : (101 - (int)fCh)-1; - in the feedback if you could you tell me,
                    // why when we have encrypted value "t" 116 which needs to be transformed to "p" - 112, actually appears as 'b' - 99 ??
                    //trying to decypher one of the cipher texts created with my code and you will see, it's just this letter :(
                   // System.out.println(fCh + " " +  ++x);
                    // this below is kinda broken, but still manages to figure out the key for the cipher text but for example
                    // it will fail to find the one for the ciphers generated by my code, i think we have to operate with bytes
                    // instead, it would mean a lot if I'm told what's the proper way to do it.

                    int rotateBy = 0;
                    if (fCh > 'e'){
                        rotateBy = (int) fCh - (int) 'e';
                    }else
                        {
                            rotateBy = (int) 'e' - (int) fCh;
                        }
                    System.out.println("Key is: " + rotateBy);
                    // always add to 'a'
                    decryptKey.append((char) (97+rotateBy));


                }
                System.out.println(decryptKey.toString());
                return decryptKey.toString();
            }
            else
            {
                System.out.println("No good IOC found this run, trying greater key length....");
                //clear the map
                map.clear();
                //clear firstvalues
                firstValues.clear();
                // try with greater key length
                keyLength++;
            }



        }while (true);

    }

    public void decrypt(Path filePath, boolean enablePassword, String key, int alphabetSize) throws IOException
    {

        String password;
        if (enablePassword)
        {

        password = JOptionPane.showInputDialog("Type in a password.");
        if (!password.matches("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Password must consist of english letters only no numbers");
        }

        }
        else
            {
                password = key;
            }

        BufferedReader reader = super.getBufferReader(getFrequencyAnalyserInstance(filePath));
        try(BufferedWriter writer = getBufferedWriter("VigenereDecrypt"))
        {
            password = password.toLowerCase();

            int read,i, j = 0;
            while((read = reader.read()) != -1)
            {
                char currCh = Character.toLowerCase((char) read);
                //System.out.println("Message letter " + currCh);
                //int prepKey = (int) password.charAt( (j % password.length()) );
                int prepKey = alphabetSize - (password.charAt(j % password.length()) - 'a');
                writer.write(super.rotateCh(currCh, prepKey));
                j++;

            }
        }
    }

    private BufferedWriter getBufferedWriter(String toFile) throws IOException
    {
        /**
         * returns buffered file writer
         * only file name to be specified.
         * Default path is pointed to Resources dir
         */
        String filePath = "Resources/"+ toFile + "-" +
                        new SimpleDateFormat("yyyy-MM-dd-HH-mm'.txt'").format(new Date());
        return new BufferedWriter(new FileWriter(new File(filePath)));
    }

    public void encrypt() throws IOException
    {

        String password = JOptionPane.showInputDialog("Type in a word.");
        if(!password.matches("[a-zA-Z]+")){
           throw new IllegalArgumentException("Password must consist of english letters only no numbers");
        }

        /**
         * Using Buffered reader and writer here, because it was way too slow with streams and lambdas,
         * need to master them hehe
         */

        BufferedReader reader = super.getBufferReader(file);
        try(BufferedWriter writer = getBufferedWriter("VigenereEncrypt"))
        {
            password = password.toLowerCase();

            int read,i, j = 0;
            while((read = reader.read()) != -1)
            {

                char currCh = Character.toLowerCase((char) read);
                //System.out.println("Message letter " + currCh);
                //int prepKey = (int) password.charAt( (j % password.length()) );
                int prepKey = (password.charAt(j % password.length()) - 'a' + 1);
                //System.out.println("Encrypted:----" + super.rotateCh(currCh, prepKey));
                writer.write(super.rotateCh(currCh, prepKey));
                j++;

            }


        }
    }



}

