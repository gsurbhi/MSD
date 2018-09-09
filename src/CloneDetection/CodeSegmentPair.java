package CloneDetection;

/**
 * Represent a pair of code segment.
 */

public class CodeSegmentPair implements CodeSegmentPairInterface {

    /**
     * The levenstein distance between the two code segments
     */
    int mimimumEditDistance;

    /**
     * The similarity score between the two code segments
     */
    double similarityScore;

    CodeSegment leftSegment;
    CodeSegment rightSegment;

    public CodeSegmentPair(CodeSegment leftSegment,CodeSegment rightSegment,double similarityScore,int mimimumEditDistance) {

        this.leftSegment=leftSegment;
        this.rightSegment=rightSegment;
        this.similarityScore=similarityScore;
        this.mimimumEditDistance=mimimumEditDistance;
    }

    public CodeSegment getLeftSegment() {
        return leftSegment;
    }

    public CodeSegment getRightSegment() {
        return rightSegment;
    }

    public double getSimilarityScore() {return similarityScore; }

    public int getMimimumEditDistance() {return  mimimumEditDistance; }

}
