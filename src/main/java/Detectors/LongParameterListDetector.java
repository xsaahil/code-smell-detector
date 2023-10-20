package Detectors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.CompilationUnit;

public class LongParameterListDetector {
    public static void detectLongParameterList(CompilationUnit cu, int maxAllowedParameters) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            int parameterCount = method.getParameters().size();
            if (parameterCount > maxAllowedParameters) {
                System.out.println("Long Parameter List Detected:");
                System.out.println("Method Name: " + method.getName());
                System.out.println("Parameter Count: " + parameterCount);
            }
        });
    }
}
