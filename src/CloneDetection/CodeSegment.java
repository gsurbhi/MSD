package CloneDetection;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Block;

/**
 *
 * represent a segment (range) in a source file
 */
public class CodeSegment implements CodeSegmentInterface {

    private ASTNode block;


    public CodeSegment(Block block) {

        this.block= block;
    }
    public ASTNode getNode() { return block; }
}
