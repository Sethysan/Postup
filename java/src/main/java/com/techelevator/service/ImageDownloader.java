package com.techelevator.service;

import com.techelevator.exception.DaoException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
//new class to download and save the images from the DB
public class ImageDownloader {

    public void saveImageFromUrl(String imageUrl, String destinationFile, String fileName) {
        try{
            // Convert the image URL into a URL object
            URL url = new URL(imageUrl);

            /* Open a stream from the URL. This method establishes a connection to the specified
            URL and returns an InputStream object.*/
            InputStream inputStream = url.openStream();

            // Create the destination path where the image will be saved
            Path destinationPath = Paths.get(destinationFile + "/" + fileName);

            // Use Files.copy() to copy the input stream data to the destination path
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Close the input stream to release the resources
            inputStream.close();

            System.out.println("Image downloaded successfully to: " + destinationPath.toString());
        }catch (CannotGetJdbcConnectionException e) {
            e.printStackTrace();
            throw new DaoException("Unable to connect to server or database", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
