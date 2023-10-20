package Detectors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.BlockStmt;

public class InappropriateIntimacyDetector {
    public static void detectInappropriateIntimacy(CompilationUnit cu, int maxAllowedMethodInvocations) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            BlockStmt body = method.getBody().orElse(null);
            if (body != null) {
                int methodInvocations = countMethodInvocations(body);
                if (methodInvocations > maxAllowedMethodInvocations) {
                    System.out.println("Inappropriate Intimacy Detected:");
                    System.out.println("Method Name: " + method.getName());
                    System.out.println("Method Invocations: " + methodInvocations);
//                    System.out.println("File: " + cu.getStorage().getPath().toAbsolutePath());
                    System.out.println();
                }
            }
        });
    }

    private static int countMethodInvocations(BlockStmt block) {
        return block.findAll(MethodDeclaration.class).size();
    }
}
