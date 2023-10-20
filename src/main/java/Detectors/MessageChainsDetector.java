package Detectors;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.CompilationUnit;

public class MessageChainsDetector {
    public static void detectMessageChains(CompilationUnit cu, int maxChainLength) {
        cu.findAll(MethodCallExpr.class).forEach(methodCall -> {
            int chainLength = getChainLength(methodCall);
            if (chainLength > maxChainLength) {
                System.out.println("Message Chains Detected:");
                System.out.println("Method Call: " + methodCall);
                System.out.println("Chain Length: " + chainLength);
//                System.out.println("File: " + cu.getStorage().getPath().toAbsolutePath());
                System.out.println();
            }
        });
    }

    private static int getChainLength(MethodCallExpr methodCall) {
        int length = 0;
        while (methodCall.getScope().isPresent() && methodCall.getScope().get() instanceof MethodCallExpr) {
            methodCall = (MethodCallExpr) methodCall.getScope().get();
            length++;
        }
        return length;
    }
}
