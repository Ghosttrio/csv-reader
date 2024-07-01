package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    private static final String CSV_FILE_PATH = "src/main/resources/.csv";
    private static final String DB_URL = "";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try (CSVReader csvReader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] nextRecord;
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "INSERT INTO  (, ) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            while ((nextRecord = csvReader.readNext()) != null) {
                String a = nextRecord[0];
                String b = nextRecord[1];
                preparedStatement.setString(1, a);
                preparedStatement.setString(2, b);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.close();
            System.out.println("Data has been inserted successfully.");

        } catch (IOException | CsvValidationException | SQLException e) {
            e.printStackTrace();
        }
    }
}