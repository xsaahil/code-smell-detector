package FileProcessor;

import java.io.File;

public class InputDirectoryLister {
    public static void listFilesInDirectory(String sourceDirectory, String directoryName) {
        File inputDir = new File(sourceDirectory, directoryName);

        if (inputDir.exists() && inputDir.isDirectory()) {
            File[] files = inputDir.listFiles();

            if (files != null) {
                System.out.println("Files in the input directory:");
                for (File file : files) {
                    if (file.isFile()) {
                        System.out.println("File Name: " + file.getName());
                    }
                }
            }
        } else {
            System.out.println("The input directory does not exist or is not a directory.");
        }
    }
}
