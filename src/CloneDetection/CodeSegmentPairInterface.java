package CloneDetection;

public interface CodeSegmentPairInterface {

    CodeSegment getLeftSegment() ;

    CodeSegment getRightSegment() ;

    double getSimilarityScore();

    int getMimimumEditDistance();
}
