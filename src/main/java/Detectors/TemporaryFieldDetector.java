package Detectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.NameExpr;

public class TemporaryFieldDetector {
    public static void detectTemporaryFields(CompilationUnit cu) {
        cu.findAll(ClassOrInterfaceDeclaration.class).forEach(classDecl -> {
            classDecl.findAll(FieldDeclaration.class).forEach(field -> {
                // Check if the field is declared outside the enclosing class
                if (!isFieldOutsideClass(field, cu, classDecl) && !isFieldUsed(field, cu)) {
                    System.out.println("Temporary Field Detected:");
                    field.getVariables().forEach(variable -> {
                        System.out.println("Field Name: " + variable.getNameAsString());
                    });
                    System.out.println("Class Name: " + classDecl.getNameAsString());
//                    System.out.println("File: " + cu.getStorage().getPath().toAbsolutePath());
                    System.out.println();
                }
            });
        });
    }

    private static boolean isFieldOutsideClass(FieldDeclaration field, CompilationUnit cu, ClassOrInterfaceDeclaration classDecl) {
        // Get the field name
        String fieldName = field.getVariable(0).getNameAsString();

        // Check if the field is referenced outside the specified class
        return cu.findAll(NameExpr.class).stream()
                .filter(nameExpr -> nameExpr.getName().equals(fieldName))
                .anyMatch(nameExpr -> isNameExprOutsideClass(nameExpr, classDecl));
    }

    private static boolean isNameExprOutsideClass(NameExpr nameExpr, ClassOrInterfaceDeclaration classDecl) {
        // Check if the NameExpr is outside the specified class
        return !nameExpr.findAncestor(ClassOrInterfaceDeclaration.class).filter(parent -> parent == classDecl).isPresent();
    }

    private static boolean isFieldUsed(FieldDeclaration field, CompilationUnit cu) {
        // Get the field name
        String fieldName = field.getVariable(0).getNameAsString();

        // Count the occurrences of the field name in NameExpr nodes
        long occurrences = cu.findAll(NameExpr.class).stream()
                .filter(nameExpr -> nameExpr.getName().equals(fieldName))
                .count();

        // If occurrences is greater than one, the field is used
        return occurrences > 1;
    }


}
