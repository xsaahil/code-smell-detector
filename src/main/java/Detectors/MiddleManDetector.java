package Detectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MiddleManDetector {
    public static void detectMiddleMen(CompilationUnit cu) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            method.findAll(MethodCallExpr.class).forEach(callExpr -> {
                String calledMethodName = callExpr.getNameAsString();
                if (!isInheritedMethod(method, calledMethodName, cu)) {
                    System.out.println("Potential Middle Man Detected:");
                    System.out.println("Method Name: " + method.getNameAsString());
                    System.out.println("Called Method: " + calledMethodName);
//                    System.out.println("File: " + cu.getStorage().getPath().toAbsolutePath());
                    System.out.println();
                }
            });
        });
    }

    private static boolean isInheritedMethod(MethodDeclaration method, String methodName, CompilationUnit cu) {
        // Define names of special methods to skip
        Set<String> specialMethodNames = new HashSet<>(Arrays.asList("main", "equals", "hashCode", "toString"));

        // Check if the method is inherited and not a special method
        return cu.findAll(MethodDeclaration.class).stream()
                .filter(otherMethod -> otherMethod != method && !specialMethodNames.contains(otherMethod.getNameAsString()))
                .anyMatch(otherMethod -> otherMethod.getNameAsString().equals(methodName));
    }
}