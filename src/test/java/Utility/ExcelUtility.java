package Utility;

import WebDriverFactory.DriverFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ExcelUtility
{
    Workbook workbook;
    Sheet sheet;
    String excelFilePath;

    public void setExcelFile(String filePath, String sheetName) throws IOException
    {
        FileInputStream excelFile = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(excelFile);
        sheet = workbook.getSheet(sheetName);
        excelFilePath = filePath;
        writeToPropertiesFile(GlobalVariables.PROPERTIES_PATH+"/"+sheetName+".properties",sheetName);
    }

    public void writeToPropertiesFile(String propertiesFilePath, String sheetName) throws IOException
    {
        Properties prop = new Properties();
        Path path= Paths.get(propertiesFilePath);
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
                System.out.println("Properties file created.");
            } catch (IOException e) {
                System.err.println("Error creating properties file: " + e.getMessage());
                return;
            }
        }
        else {
            System.out.println("Properties file already Exists.");
        }

        GlobalVariables.CREATED_PROP_FILE_PATH=propertiesFilePath;
        FileOutputStream output = new FileOutputStream(propertiesFilePath);

        for (Row row : sheet) {
            Cell keyCell = row.getCell(0);
            Cell valueCell = row.getCell(1);
            if (keyCell != null && valueCell != null) {
                prop.setProperty(keyCell.getStringCellValue(), valueCell.getStringCellValue());
            }
        }
        prop.store(output, null);
        GlobalVariables.prop=prop;
        output.close();
    }

    public void updateExcelFromProperties(String propertiesFilePath,String key,String value) throws IOException
    {
        boolean flag= false;
        for (Row row : sheet) {
            Cell keyCell = row.getCell(0);
            if (keyCell != null && key.contains(keyCell.getStringCellValue())) {
                Cell valueCell = row.createCell(1);
                valueCell.setCellValue(value);
                flag=true;
            }
        }

        if(flag==false)
        {
            int lastRowNum = sheet.getLastRowNum();
            Row newRow = sheet.createRow(lastRowNum + 1);
            Cell cell1 = newRow.createCell(0);
            cell1.setCellValue(key);
            Cell cell2 = newRow.createCell(1);
            cell2.setCellValue(value);
        }
        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}

