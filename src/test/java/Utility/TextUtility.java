package Utility;

import java.io.*;

public class TextUtility
{
    public static void createTextFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editTextFile(String filePath, String additionalText) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.newLine();  // Move to a new line
            writer.write(additionalText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteTextFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    public static boolean verifyTextFileContent(String filePath, String textToFind) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(textToFind)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
