package Utility;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class WordUtility
{
    public static void createWordDocument(String filePath, String text) {
        try (XWPFDocument doc = new XWPFDocument()) {
            XWPFParagraph para = doc.createParagraph();
            para.createRun().setText(text);
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                doc.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void editWordDocument(String filePath, String newText) {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument doc = new XWPFDocument(fis)) {
            XWPFParagraph para = doc.createParagraph();
            para.createRun().setText(newText);
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                doc.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteWordDocument(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }

    public static boolean verifyWordContent(String filePath, String textToFind) {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis)) {
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (XWPFParagraph para : paragraphs) {
                if (para.getText().contains(textToFind)) {
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
