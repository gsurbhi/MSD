package CloneDetection;

import org.eclipse.jdt.core.dom.*;

import java.util.HashSet;
import java.util.Set;


/**
 *  This visitor grabs all the block in a source file
 */


public class ASTBlockVisitor extends ASTVisitor {
    Set<Block> blocks = new HashSet<>();

    @Override
    public boolean visit(Block node) {

        blocks.add(node);
        return super.visit(node);
    }

    public Set<Block> getBlocks() {
        return blocks;
    }
}