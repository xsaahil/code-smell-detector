package FileProcessor;

import Detectors.*;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;

public class JavaFileProcessor {
    public static void processFilesInDirectory(String sourceDirectory, String directoryName, int maxAllowedLines, int maxAllowedParameters, int maxAllowedAttributes, int maxAllowedMethods, int minRequiredMethods, int maxChainLength, int maxAllowedStatements, int maxAllowedEnvyStatements, int maxAllowedMethodInvocations) {
        File inputDir = new File(sourceDirectory, directoryName);

        if (inputDir.exists() && inputDir.isDirectory()) {
            File[] files = inputDir.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            // Parse and process Java source files in the input directory
                            CompilationUnit cu = StaticJavaParser.parse(file);
                            LongMethodDetector.detectLongMethods(cu, maxAllowedLines);
                            LongMethodStatementCountDetector.detectLongMethods(cu, maxAllowedStatements);
                            LongParameterListDetector.detectLongParameterList(cu, maxAllowedParameters);
                            LargeClassDetector.detectLargeClasses(cu, maxAllowedAttributes, maxAllowedMethods);
                            DataClassDetector.detectDataClasses(cu, maxAllowedAttributes, minRequiredMethods);
                            MessageChainsDetector.detectMessageChains(cu, maxChainLength);
                            TemporaryFieldDetector.detectTemporaryFields(cu);
                            FeatureEnvyDetector.detectFeatureEnvy(cu, maxAllowedMethodInvocations);
                            InappropriateIntimacyDetector.detectInappropriateIntimacy(cu,maxAllowedMethodInvocations);
                            MiddleManDetector.detectMiddleMen(cu);
                            RefusedBequestDetector.detectRefusedBequest(cu);
                        } catch (Exception e) {
                            // Handle parsing errors or exceptions as needed
                            System.err.println("Error parsing file: " + file.getAbsolutePath());
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            System.out.println("The input directory does not exist or is not a directory.");
        }
    }
}
