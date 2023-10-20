package Detectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class LongMethodDetector {
    public static void detectLongMethods(CompilationUnit cu, int maxAllowedLines) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            int methodLineCount = method.getEnd().get().line - method.getBegin().get().line;
            if (methodLineCount > maxAllowedLines) {
                System.out.println("Long Method Detected:");
                System.out.println("Method Name: " + method.getName());
                System.out.println("Line Count: " + methodLineCount);
            }
        });
    }
}
