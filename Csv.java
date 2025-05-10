import java.io.*;
import java.util.*;
import javafx.util.Pair;
import java.nio.charset.StandardCharsets;

public class Csv{
    
    public static void write(String writeLine, String fileToWrite){

        try {
            FileWriter writer = new FileWriter(fileToWrite + ".csv", true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(writeLine + "\n");

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[] read(String fileToRead){

        ArrayList<String> content = new ArrayList<> ();

        try {

            BufferedReader fileReader = new BufferedReader(new FileReader(fileToRead + ".csv"));
            String line;

            while ((line = fileReader.readLine()) != null) {

                content.add(line);

            }

            content.remove(0);

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }        

        String[] contentArray = content.toArray(new String[0]);

        return contentArray;
    }

    
    public static void ChangeValue(String fileToModify, int lineIndex, String newLineContent) {
        try {
            // Read the existing content of the file
            ArrayList<String> content = new ArrayList<>();
            BufferedReader fileReader = new BufferedReader(new FileReader(fileToModify + ".csv"));
            String line;
            while ((line = fileReader.readLine()) != null) {
                content.add(line);
            }
            fileReader.close();

            // Check if the specified line index is valid
            if (lineIndex >= 0 && lineIndex < content.size()) {
                // Modify the line at the specified index
                content.set(lineIndex, newLineContent);

                // Write the updated content back to the file
                FileWriter writer = new FileWriter(fileToModify + ".csv");
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                for (String lineToWrite : content) {
                    bufferedWriter.write(lineToWrite + "\n");
                }
                bufferedWriter.close();

            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateLine(int lineNum, String lineContent, String csvFileName) {
        try {
            File inputFile = new File(csvFileName + ".csv");
            File tempFile = new File(csvFileName + ".tmp");

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempFile), StandardCharsets.UTF_8));

            String currentLine;
            int lineNumber = 1;

            while ((currentLine = reader.readLine()) != null) {
                if (lineNumber == lineNum) {
                    writer.write(lineContent);
                } else {
                    writer.write(currentLine);
                }

                writer.newLine();
                lineNumber++;
            }

            reader.close();
            writer.close();

            // Delete the original file and rename the temp file to the original file name
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            } else {
                throw new IOException("Failed to update the CSV file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void removeLine(String fileToUpdate, int lineToRemove) {
        try {
            File inputFile = new File(fileToUpdate + ".csv");
            File tempFile = new File(fileToUpdate + ".tmp");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            int currentLine = 1;

            while ((line = reader.readLine()) != null) {
                if (currentLine != lineToRemove) {
                    writer.write(line + System.lineSeparator());
                }
                currentLine++;
            }

            reader.close();
            writer.close();

            // Delete the original file
            if (inputFile.delete()) {
                // Rename the temporary file to the original file
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Error renaming the temporary file");
                }
            } else {
                System.out.println("Error deleting the original file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

