package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Statement;
import org.junit.Test;

import CloneDetection.ASTStatementMatcher;

public class TestASTStatementMatcher {

    private static ASTStatementMatcher createASTStatementMatcher() {
        return new ASTStatementMatcher();
    }

    private static Statement parseStatement(String statementString) {

        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource((statementString).toCharArray());
        parser.setKind(ASTParser.K_STATEMENTS);;
        Statement statementNode=(Statement)parser.createAST(null);
        return statementNode;
    }

    // -----------------------------------------------
    // Test assignment statement comparison
    @Test
    public void testCompareAssignment() {

        ASTStatementMatcher matcher=createASTStatementMatcher();
        Statement st1=parseStatement("i=z+9;");
        Statement st2=parseStatement("j=k+9;");
        boolean result= st1.subtreeMatch(matcher,st2);
        assertTrue(result);


        st1=parseStatement("i=z+91;");
        st2=parseStatement("i=z+9;");
        result= st1.subtreeMatch(matcher,st2);
        assertFalse(result);

    }


    // -----------------------------------------------
    // Test Variable declaration statement comparison
    @Test
    public void testCompareDeclaration() {
  
    Statement st1=parseStatement("int i,k,l;");
    Statement st2=parseStatement("int j,count,sum;");
    boolean result= st1.subtreeMatch(new ASTStatementMatcher(),st2);
    assertTrue(result);

    st1=parseStatement("int i;");
    st2=parseStatement("double i;");

    result= st1.subtreeMatch(new ASTStatementMatcher(),st2);
    assertFalse(result);

    }
    // -----------------------------------------------
    // Test method invocation statement comparison
    @Test
    public void testCompareMethodInvocation() {
        
        Statement st1=parseStatement("ob.foo(i,j,k);");
        Statement st2=parseStatement("ob.bar(l,m,n);");
        boolean result= st1.subtreeMatch(new ASTStatementMatcher(),st2);
        assertTrue(result);

        st1=parseStatement("ob.foo(i,j,k)");
        st2=parseStatement("ob.bar(i);");

        result= st1.subtreeMatch(new ASTStatementMatcher(),st2);
        assertFalse(result);

    }


    // -----------------------------------------------
    // Test for loop statements comparison
    @Test
    public void testCompareForLoop() {
  
        Statement st1=parseStatement("for (int i=0; i < 10; i++) {\n" +
                "  System.out.println(i);\n" +
                "}");
        Statement st2=parseStatement("for (int j=0; j < 10; j++) {\n" +
                "  System.out.println(j);\n" +
                "}");
        boolean result= st1.subtreeMatch(new ASTStatementMatcher(),st2);
        assertTrue(result);

        st1=parseStatement("for (int i=0; i < 10; i++) {\n" +
                "  System.out.println(i);\n" +
                "}");
        st2=parseStatement("for (int i=0; i <= 10; i++) {\n" +
                "  System.out.println(i+1);\n" +
                "}");

        result= st1.subtreeMatch(new ASTStatementMatcher(),st2);
        assertFalse(result);

    }




}
