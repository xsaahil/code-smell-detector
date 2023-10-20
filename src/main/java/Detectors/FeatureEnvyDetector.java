package Detectors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;

public class FeatureEnvyDetector {
    public static void detectFeatureEnvy(CompilationUnit cu, int maxAllowedEnvyStatements) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            BlockStmt body = method.getBody().orElse(null);
            if (body != null) {
                int envyStatements = countEnvyStatements(body, method);
                if (envyStatements > maxAllowedEnvyStatements) {
                    System.out.println("Feature Envy Detected:");
                    System.out.println("Method Name: " + method.getName());
                    System.out.println("Envy Statements: " + envyStatements);
//                    System.out.println("File: " + cu.getStorage().getPath().toAbsolutePath());
                    System.out.println();
                }
            }
        });
    }

    private static int countEnvyStatements(BlockStmt block, MethodDeclaration method) {
        int envyStatements = 0;
        for (Statement stmt : block.getStatements()) {
            if (stmt.isExpressionStmt()) {
                ExpressionStmt exprStmt = stmt.asExpressionStmt();
                String methodName = exprStmt.getExpression().toString().split("\\(")[0].trim();
                if (!method.getNameAsString().equals(methodName)) {
                    envyStatements++;
                }
            }
        }
        return envyStatements;
    }
}
