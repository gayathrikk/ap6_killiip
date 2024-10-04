package com.test.Database_Testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Path_verification {

    static class Slide {
        private int id;
        private String filename;
        private String jp2Path;

        public Slide(int id, String filename, String jp2Path) {
            this.id = id;
            this.filename = filename;
            this.jp2Path = jp2Path;
        }

        // Getters
        public int getId() { return id; }
        public String getFilename() { return filename; }
        public String getJp2Path() { return jp2Path; }

        @Override
        public String toString() {
            return String.format("Slide{id=%d, filename='%s', jp2Path='%s'}", id, filename, jp2Path);
        }
    }

    // Function to fetch slides from the database
    private static List<Slide> getSlides(int slidebatchId, String jp2PathPattern) {
        List<Slide> slides = new ArrayList<>();
        String url = "jdbc:mysql://apollo2.humanbrain.in:3306/HBA_V2";
        String username = "root";
        String password = "Health#123";

        String query = "SELECT id, filename, jp2Path FROM slide WHERE slidebatch LIKE ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set parameters
            statement.setString(1, "%" + slidebatchId + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String filename = resultSet.getString("filename");
                    String jp2Path = resultSet.getString("jp2Path");

                    slides.add(new Slide(id, filename, jp2Path));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return slides;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get slidebatch ID from user
        System.out.println("Enter the slidebatch ID:");
        int slidebatchId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Define jp2Path pattern for search
        String jp2PathPattern = "compressed.jp2"; // Example pattern; adjust as needed

        // Fetch slides based on the given slidebatch ID
        List<Slide> slides = getSlides(slidebatchId, jp2PathPattern);

        // Check paths
        boolean allPathsCorrect = true;

        System.out.println("Slides retrieved:");
        for (Slide slide : slides) {
            // Verify if the path contains the expected pattern
            if (!slide.getJp2Path().contains(jp2PathPattern)) {
                System.out.println("Path is incorrect: " + slide.getJp2Path());
                allPathsCorrect = false;
            }
        }

        if (allPathsCorrect) {
            System.out.println("All paths are correct.");
        } else {
            System.out.println("Some paths are incorrect.");
        }

        scanner.close();
    }
}
