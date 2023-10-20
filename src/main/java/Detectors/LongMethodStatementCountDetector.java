package Detectors;

import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

public class LongMethodStatementCountDetector {
    public static void detectLongMethods(CompilationUnit cu, int maxAllowedStatements) {
        cu.findAll(MethodDeclaration.class).forEach(method -> {
            int statementCount = countStatements(method.getBody().orElse(null));
            if (statementCount > maxAllowedStatements) {
                System.out.println("Long Method Detected (by Statement Count):");
                System.out.println("Method Name: " + method.getName());
                System.out.println("Statement Count: " + statementCount);
//                System.out.println("File: " + cu.getStorage().getPath().toAbsolutePath());
                System.out.println();
            }
        });
    }

    private static int countStatements(Statement statement) {
        if (statement == null) {
            return 0;
        }
        return statement.getChildNodes().stream()
                .filter(node -> node instanceof Statement)
                .mapToInt(node -> countStatements((Statement) node) + 1)
                .sum();
    }
}
