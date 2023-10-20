package Detectors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.CompilationUnit;

public class DataClassDetector {
    public static void detectDataClasses(CompilationUnit cu, int maxAllowedAttributes, int minRequiredMethods) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDeclaration -> {
            int attributeCount = 0;
            int getterCount = 0;
            int setterCount = 0;

            for (FieldDeclaration field : classDeclaration.getFields()) {
                attributeCount++;
            }

            for (MethodDeclaration method : classDeclaration.getMethods()) {
                String methodName = method.getNameAsString();
                if (methodName.startsWith("get")) {
                    getterCount++;
                } else if (methodName.startsWith("set")) {
                    setterCount++;
                }
            }

            if (attributeCount > maxAllowedAttributes && (getterCount + setterCount) <= minRequiredMethods) {
                System.out.println("Data Class Detected:");
                System.out.println("Class Name: " + classDeclaration.getName());
                System.out.println("Attribute Count: " + attributeCount);
                System.out.println("Getter Count: " + getterCount);
                System.out.println("Setter Count: " + setterCount);
                System.out.println();
            }
        });
    }
}
