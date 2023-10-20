package Detectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RefusedBequestDetector {
    public static void detectRefusedBequest(CompilationUnit cu) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDecl -> {
            if (classDecl.isClassOrInterfaceDeclaration() && !classDecl.getExtendedTypes().isEmpty()) {
                String superClass = classDecl.getExtendedTypes(0).getNameAsString();
                classDecl.findAll(MethodDeclaration.class).forEach(subclassMethod -> {
                    if (!isInheritedMethod(superClass, subclassMethod, cu)) {
                        System.out.println("Potential Refused Bequest Detected:");
                        System.out.println("Subclass: " + classDecl.getNameAsString());
                        System.out.println("Unused Method: " + subclassMethod.getNameAsString());
//                        System.out.println("File: " + cu.getStorage().getPath().toAbsolutePath());
                        System.out.println();
                    }
                });
            }
        });
    }

    private static boolean isInheritedMethod(String superClass, MethodDeclaration method, CompilationUnit cu) {
        // Define names of special methods to skip
        Set<String> specialMethodNames = new HashSet<>(Arrays.asList("main", "equals", "hashCode", "toString"));

        return cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(classDecl -> classDecl.getNameAsString().equals(superClass))
                .flatMap(superclass -> superclass.findAll(MethodDeclaration.class).stream())
                .anyMatch(superclassMethod ->
                        superclassMethod.getNameAsString().equals(method.getNameAsString())
                                && !specialMethodNames.contains(superclassMethod.getNameAsString()));
    }
}
