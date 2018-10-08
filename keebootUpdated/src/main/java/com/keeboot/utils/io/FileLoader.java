package com.keeboot.utils.io;

import static com.keeboot.utils.TestReporter.logTrace;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.keeboot.utils.exception.InvalidFileException;

public class FileLoader {

    public static String loadFileFromProjectAsString(String filePath) throws IOException {
        logTrace("Entering FileLoader#loadFileFromProjectAsString");

        logTrace("Attempting to load file from path [ " + filePath + " ]");
        BufferedReader resource;
        resource = openTextFileFromProject(filePath);

        logTrace("Attempting to read file as String");
        String text;
        try {
            text = IOUtils.toString(resource);
        } finally {
            resource.close();
        }
        logTrace("Exiting FileLoader#loadFileFromProjectAsString");
        return text;
    }

    public static BufferedReader openTextFileFromProject(String filePath) throws IOException {
        logTrace("Entering FileLoader#openFileFromProject");
        filePath = filePath.replace("%20", " ");
        if (!filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }
        if (!isReadableFile(filePath)) {
            throw new InvalidFileException("File path of [ " + filePath + " ] was invalid or file was unreadable");
        }

        logTrace("Attempting to load file from path [ " + filePath + " ]");
        FileReader fileReader = new FileReader(getAbosutePathForResource(filePath));
        logTrace("Successfully loaded file into FileReader");

        logTrace("Loading FileReader object into BufferedReader");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        logTrace("Successfully loaded FileReader object into BufferedReader");

        logTrace("File successfully loaded");
        logTrace("Exiting FileLoader#openFileFromProject");
        return bufferedReader;
    }

    public static boolean isReadableFile(String filePath) {
        logTrace("Entering FileLoader#isReadableFile");

        logTrace("Validating file from path [ " + filePath + " ] is readable");
        boolean readable = false;
        File file = new File(filePath);
        if (!file.isDirectory() && file.exists() && file.canRead()) {
            readable = true;
        } else if (null != FileLoader.class.getResource(filePath)) {
            readable = true;
        }

        logTrace("File was readable returning [ " + readable + " ]");
        logTrace("Exiting FileLoader#isReadableFile");
        return readable;
    }

    public static String getAbosutePathForResource(String filePath) {
        URL file = FileLoader.class.getResource(filePath);
        if (null == file) {
            return filePath;
        }
        return file.getPath();
    }

    /**
     * Read a file and return the string
     *
     */

    public static String readFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}
