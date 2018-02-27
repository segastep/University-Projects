package Exercise2;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Runner2 {

    public static void main(String[] args) throws IOException{

        Path file = Paths.get("Resources/normalText.txt");
        VigenereUtils test = new VigenereUtils(file);
        // Output file is encrypted with "PASSWORD" as key file name -  Resources/vigereneEncode-2017-10-25-22-30.txt
        int start = (int) System.currentTimeMillis()*60*60;
        //test.encrypt();
        // result file from decrypt method is - Resources/VigenereEncrypt-2017-10-26-21-52.txt
        // when propted for password use "password"
        Path file2 = Paths.get("Resources/VigenereEncrypt-2017-10-26-21-52.txt");
        Path file3 = Paths.get("Resources/exercise2cipher.txt");
        //Build file name for this run
        String fileNameAnalysis = "Resources/CryptoAnalisysCypherTextEx2-" + new SimpleDateFormat("yyyy-MM-dd-HH-mm'.txt'").format(new Date());
        PrintStream fileStream = new PrintStream(fileNameAnalysis);
        System.setOut(fileStream);
        test.coincidenceIndex(file3);
        // I did something wrong during the implementation and the alphabet size needs to be changed in order for the program to work correctly sorry, at least learned that shouldn't use ints for such things.
        test.decrypt(file3, false, "plato", 25 );
        test.decrypt(file2, false, "password", 26 );
        int end = (int) System.currentTimeMillis()*60*60- start;
        System.out.println("Execution time decrypt: " + end);

    }
}
