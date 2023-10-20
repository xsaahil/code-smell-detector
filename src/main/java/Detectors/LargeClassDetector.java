package Detectors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.CompilationUnit;

public class LargeClassDetector {
    public static void detectLargeClasses(CompilationUnit cu, int maxAllowedAttributes, int maxAllowedMethods) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {
            int attributeCount = classDeclaration.getFields().size();
            int methodCount = classDeclaration.getMethods().size();

            if (attributeCount > maxAllowedAttributes || methodCount > maxAllowedMethods) {
                System.out.println("Large Class Detected:");
                System.out.println("Class Name: " + classDeclaration.getName());
                System.out.println("Attribute Count: " + attributeCount);
                System.out.println("Method Count: " + methodCount);
//                System.out.println("File: " + cu.getStorage().getPath().toAbsolutePath());
                System.out.println();
            }
        });
    }
}
