package code.smell.analyser;

import FileProcessor.InputDirectoryLister;
import FileProcessor.JavaFileProcessor;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.nio.file.Path;

public class CodeSmellAnalyzer {
    public static void main(String[] args) throws IOException {
        String sourceDirectory = "src"; // Set your source code directory
        int maxAllowedLines = 20; // Specify the maximum allowed lines for a method
        int maxAllowedParameters = 5; // Specify the maximum allowed parameters for a method
        int maxAllowedAttributes = 10; // Specify the maximum allowed attributes for a class
        int maxAllowedMethods = 10; // Specify the maximum allowed methods for a class
        int minRequiredMethods = 2; // Specify the minimum required methods for a data class
        int maxChainLength = 3; // Specify the maximum allowed message chain length
        int maxAllowedStatements = 50; // Specify the maximum allowed statements for a method
        int maxAllowedEnvyStatements = 4;
        int maxAllowedMethodInvocations = 4;
        // Define the name of your input directory
        String inputDirectoryName = "inputs";

        // Initialize a source root for parsing Java files
        SourceRoot sourceRoot = new SourceRoot(Path.of(sourceDirectory));
        sourceRoot.tryToParse("");

        // List files in the input directory
        InputDirectoryLister.listFilesInDirectory(sourceDirectory, inputDirectoryName);

        // Process Java source files in the input directory
        JavaFileProcessor.processFilesInDirectory(sourceDirectory, inputDirectoryName, maxAllowedLines, maxAllowedParameters, maxAllowedAttributes, maxAllowedMethods, minRequiredMethods, maxChainLength, maxAllowedStatements, maxAllowedEnvyStatements, maxAllowedMethodInvocations);
    }
}
