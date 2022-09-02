import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *Class for reading a .csv file and placing every value in a list as a string
 *
 */

public class CsvReader {

    public CsvReader() {

    }
    ArrayList<String> readCsvFile(String csvFile) throws IOException {
        ArrayList<String> output = new ArrayList<>();
        String line = "";
        BufferedReader br = new BufferedReader(new FileReader(csvFile));
        while ((line = br.readLine()) != null) {
            String[] data = line.split(","); // Date (0), Close(1), Volume(2), Open(3), High(4), Low(5)
            output.addAll(Arrays.asList(data));
        }
        return output;
    }





}


