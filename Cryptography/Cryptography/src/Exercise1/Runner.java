package Exercise1;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Runner {

    public static void main(String[] args) throws IOException {

        final String NORMAL_FILE = "Resources/normalText.txt";
        final String ENCRYPTED_FILE = "Resources/encryptedText";

        //Build file name for this run
        String fileNameAnalysis = "Resources/analysis-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm'.txt'").format(new Date());

        //Runs slow when sys out is redicted, might just comment the two lines below to make it faster.
        PrintStream fileStream = new PrintStream(fileNameAnalysis);
        System.setOut(fileStream);
        CryptoUtils test = new CryptoUtils(Paths.get(NORMAL_FILE), Paths.get(ENCRYPTED_FILE));
        test.runFrequencyNcryptoAnalysis();
        test.encryptByFrequency();
        Arrays.stream(test.isShift()).forEach( (i) -> System.out.println("Difference: " + i));
        //test.bruteForceCeaser();
        test.decryptCeaser(4);


    	}
   
}
