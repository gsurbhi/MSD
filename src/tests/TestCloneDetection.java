package tests;

import org.eclipse.jdt.core.dom.*;
import org.junit.Test;
import CloneDetection.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class TestCloneDetection {
    /**
     * Method for creating a CloneDetection object
     *
     * @return
     */
    private static CloneDetection createCloneDetection() {
        return new CloneDetection();

    }

    // -----------------------------------------------
    // Test the ExtractBlocks
    @Test
    public void testExtractBlocksFromString() throws IOException {
        CloneDetection cb = createCloneDetection();

        String codeSnippet = "\n" +
                "\n" +
                "public class source1 {\n" +
                "    public void foo() { int i = 0;i++; }\n" +
                "\n" +
                "    public void doo() { String s1 = \"Computer Science\";int y, x = 307; }\n" +
                "}";

        Set<Block> blocks = cb.extractBlocksFromString(codeSnippet);

        Set<String> blockString = new HashSet<String>();
        for (Block block : blocks
                ) {
            blockString.add(block.toString());

        }

        String expectedBlock1 = "{\n" +
                "  int i=0;\n" +
                "  i++;\n" +
                "}\n";

        String expectedBlock2 = "{\n" +
                "  String s1=\"Computer Science\";\n" +
                "  int y, x=307;\n" +
                "}\n";
        Set<String> expectedBlocks = new HashSet<String>();
        expectedBlocks.add(expectedBlock1);
        expectedBlocks.add(expectedBlock2);

        assertTrue(blockString.containsAll(expectedBlocks));

    }

    // Test the ExtractStatements
    @Test
    public void testExtractStatements()  {
        CloneDetection cb = createCloneDetection();

        String codeSnippet = "public class source1 { "+
                "\n" +
                "    public void doo() { String s1 = \"Computer Science\";int x = 307; }\n" +
                "}";


        Set<Block> blocks = cb.extractBlocksFromString(codeSnippet);
       Block block=null;
        for (Block item : blocks
                ) {
            block=item;
            break;
        }

        List<String> statements= cb.extractStatements(block).stream().map(i -> i.toString()).collect(Collectors.toList());
     statements.stream().forEach(System.out::println);

        List<String> expectedList = new ArrayList<>();
        expectedList.add( "String s1=\"Computer Science\";\n" );
        expectedList.add("int x=307;\n");
        assertTrue(statements.containsAll(expectedList));
    }


}

