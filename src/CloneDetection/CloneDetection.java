package CloneDetection;

import org.eclipse.jdt.core.dom.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * We treat a source file as if it was an article in natural language.
 * A function block in a source file is a sentence in an article.
 * A function block consists of a sequence of statements, just like a sentence consisting of a sequence of words(or letters)
 * We compare two function blocks (sequences of statements) by computing their Levenshtein distance
 *
 */

public class CloneDetection {

    /**
     * The two files to be compared
     */
    String leftFileName;
    String rightFileName ;
    ArrayList<String> cloneFileNames;

    /**
     *  The smallest size of blocks to be extracted for comparsion
     */
    final int MIN_SIZE_OF_BLOCK_TO_COMPARE=3;

    /**
     * If the similarity score of the two blocks exceeds this threshold, we determine that they are potentially clone
     */
    final double SCORE_THRESHOLD=0.6;

    public CloneDetection(String source1, String source2) {
        this.leftFileName=source1;
        this.rightFileName=source2;
        this.cloneFileNames = new ArrayList<>();
    }

    public CloneDetection() {

    }

    public ArrayList<String> getCloneFileNames(){
        return this.cloneFileNames;
    }

    /**
     * Compare the two java source code and output all the code segments that are similar to each other
     * @return a set of clone pairs of code
     * @throws IOException
     */
     public Set<CodeSegmentPairInterface> findClones() throws IOException {

         //  Obtain all function blocks in leftFile and rightFile
         Set<Block> leftBlocks, rightBlocks;
         leftBlocks=extractBlocks(leftFileName);
         rightBlocks=extractBlocks(rightFileName);

      // exclude blocks that contain less statements than the number of MIN_SIZE_OF_BLOCK_TO_COMPARE
         leftBlocks=leftBlocks.stream().filter(b -> b.statements().size()>MIN_SIZE_OF_BLOCK_TO_COMPARE).collect(Collectors.toSet());
         rightBlocks=rightBlocks.stream().filter(b -> b.statements().size()>MIN_SIZE_OF_BLOCK_TO_COMPARE).collect(Collectors.toSet());

         // Compare each block in leftBlocks with each block in rightBlocks
         Set<CodeSegmentPairInterface> clones=new HashSet<>();
         for(Block left:leftBlocks) {
             for (Block right:rightBlocks) {
                 if (findCloneSegmentPair(left,right)!=null) {
                     clones.add(findCloneSegmentPair(left, right));
                     cloneFileNames.add(leftFileName + " and " + rightFileName);
                 }
             }
         }

         // The SET clones contains all the similar code segment and is ready to present.
        return clones;
     }


    /**
     * Compare the two blocks. If they are similar enough, return the code segment pair, otherwise return null
     * @return pair of code if the two blocks match, null if they do not
     * @throws IOException
     */
    public CodeSegmentPairInterface findCloneSegmentPair(Block leftBlock, Block rightBlock) {

        List<Statement> leftStatements=extractStatements(leftBlock);
        List<Statement> rightStatemets=extractStatements(rightBlock);

       // use function levenshtein to find the mimimum edit distance between the two statement sequence
        int minimumEditDistance=levenshtein(leftStatements,rightStatemets);

        // calculate the similarity score.
        // Similarity Score is defined as 1 -(Minimum Edit Distance of Statements Sequence) / (Total # of Statements in the two blocks that are compared).
        double score= 1-((double)minimumEditDistance/(leftStatements.size()+rightStatemets.size()));
        score=Math.floor(score*100)/100;

        if (score>SCORE_THRESHOLD) {

            return new CodeSegmentPair(new CodeSegment(leftBlock),new CodeSegment(rightBlock),score,minimumEditDistance);
        }
        else {
            return null;
        }
    }

    /**
     * read file into string
     * @param filePath
     * @return string form of file
     * @throws IOException
     */
    private String readFileToString(String filePath) throws IOException {
        StringBuilder fileData = new StringBuilder(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        char[] buf = new char[10];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();

        return  fileData.toString();
    }


    /**
     * extract all its blocks from a java source file
     * @param sourceFilePath
     * @return set of blocks from source
     * @throws IOException
     */
    public Set<Block> extractBlocks(String sourceFilePath) throws IOException {
        String sourceString= readFileToString(sourceFilePath);

        return extractBlocksFromString(sourceString);

    }

    /**
     * extract all its blocks from a string that is the content of a java source file
     * @param sourceString
     * @return set of blocks of code
     */
    public Set<Block> extractBlocksFromString(String sourceString) {

        ASTBlockVisitor blockVistor=new ASTBlockVisitor();

        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setSource(sourceString.toCharArray());
        parser.setKind(ASTParser.K_COMPILATION_UNIT);

        final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        cu.accept(blockVistor);
        return blockVistor.getBlocks();
    }

    /**
     * extract all its statements from a block
     * @param block
     * @return list of statements from the block
     */
    public List<Statement> extractStatements(Block block) {

        List<Statement> statements=new ArrayList<>();

        Iterator itr= block.statements().iterator();
        while(itr.hasNext()){
            statements.add((Statement)itr.next());
        }
        return statements;
    }

    /**
     * Utility function to calculate the minimum among three integers
     * @param a
     * @param b
     * @param c
     * @return
     */
    private int Minimum (int a, int b, int c) {
        int mi;

        mi = a;
        if (b < mi) {
            mi = b;
        }
        if (c < mi) {
            mi = c;
        }
        return mi;

    }

    /**
     * Compute Levenshtein distance, or Minimum Edit Distance of Statement Sequence, which is define as the miminum
     * number of statenments that are required to change in a block to convert it completely to the other block.
     * Probabally Smith-Waterman algorithm is a better alterntive, which instead of looking at the entire sequence,
     * compares segments of all possible lengths and optimizes the similarity measure. For now, we use the classic
     * Levenshtein distance.
     * @param s
     * @param t
     * @return
     */

    public int levenshtein (List<Statement> s, List<Statement> t) {
        int d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        Statement s_i; // ith statement of s
        Statement t_j; // jth statement of t
        int cost; // cost

        // Step 1
        n = s.size();
        m = t.size();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n+1][m+1];

        // Step 2
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3
        for (i = 1; i <= n; i++) {
            s_i = s.get(i - 1);

            // Step 4
            for (j = 1; j <= m; j++) {
                t_j = t.get (j - 1);

                // Step 5
                if (s_i.subtreeMatch(new ASTStatementMatcher(),t_j)) {
                    cost = 0;
                }
                else {
                    cost = 1;
                }

                // Step 6
                d[i][j] = Minimum (d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);
            }

        }

        // Step 7
        return d[n][m];

    }

}

