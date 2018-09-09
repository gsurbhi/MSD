package CloneDetection;

import org.eclipse.jdt.core.dom.*;

/**
 * This class is is a subclass of ASTMatcher.
 * It is used to compare two java statement by determine if they are structurally isomorphic and by testing whether
 * each of their subtrees are of the same type.
 * Difference in comment, variable name or function name is NOT taken into account when comparing two statements
 *
 */

public class ASTStatementMatcher extends ASTMatcher{

    /**
     * Difference in comment is NOT taken into account when comparing two statements
     * @param node
     * @param other
     * @return
     */
    @Override
    public boolean match(BlockComment node, Object other) {
        return true;
    }

    /**
     * Difference in comment is NOT taken into account when comparing two statements
     * @param node
     * @param other
     * @return
     */
    @Override
    public boolean match(LineComment node,  Object other) {
        return true;
    }

    /**
     * Difference in variable name is NOT taken into account when comparing two statements
     * @param node
     * @param other
     * @return
     */
    @Override
    public boolean match(SimpleName node, Object other) {
        return true;
    }
}
