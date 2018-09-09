package CloneDetection;

public final class Constants {

    //html page construction finals
    public static final String HTML_INTRO_TEXT =
            "<html>" +
            "<head>" +
            "<style>" +
            "table, th, td {" +
            "    border: 1px solid black;" +
            " vertical-align:top;" +
            "}" +
            "</style>" +
            "</head>" +
            "<body>" +
            "<h3>Plagiarism Detection Output</h3>" +

            "<br />" +
            "Note: Similarity Score is defined as 1 -(Minimum Edit Distance of Statements Sequence) / (Total # of Statements in the two blocks that are compared).<br />" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Minimum Edit Distance of Statement Sequence is define as the minimum number of statements that are required to change in a block to convert it completely to the other block.<br />" + "<br />" +
            "<table style=\"width:100%\">" +
            "<tr>" +
            "<th>Code Segment in Source Code 1</th>" +
            "<th>Code Segment in Source Code 2</th>" +
            "<th>Mimimum Edit Distance</th>" +
            "<th>Similarity Score (0-1.0)</th>" +
            "<th>Source file names</th>" +
            "</tr>";

    public static final String OUTPUT_HTML_NAME = "output.html";

    public static final String TABLE_CELL = "</td><td>";
    public static final String TABLE_ROW_OPEN_CELL = "<tr><td>";
    public static final String TABLE_ROW_CLOSE_CELL = "</td></tr>";
    public static final String TABLE_CLOSE = "</table>" + "</body>" + "</html>";

    //------------------------------------------------------------
    // JUI element finals
    //------------------------------------------------------------

    // Window titles
    public static final String MAIN_TITLE = "Plagiarism Detection";
    public static final String RESULTS_TITLE = "Plagiarism Detection Result";

    // Button labels
    public static final String FIRST_SRC_BUTTON_LBL = "Choose First File/Dir";
    public static final String SECOND_SRC_BUTTON_LBL = "Choose Second File/Dir";
    public static final String DETECT_BUTTON_LBL = "Detect";
    public static final String CHOOSE_BUTTON_LBL = "Choose file";
    public static final String FILTER_LABEL = "java file";

    // Button Bounds
    public static final int FIRST_SRC_BUTTON_X = 70;
    public static final int FIRST_SRC_BUTTON_Y = 94;
    public static final int FIRST_SRC_BUTTON_WIDTH = 150;
    public static final int FIRST_SRC_BUTTON_HEIGHT = 30;

    public static final int SECOND_SRC_BUTTON_X = 70;
    public static final int SECOND_SRC_BUTTON_Y = 154;
    public static final int SECOND_SRC_BUTTON_WIDTH = 150;
    public static final int SECOND_SRC_BUTTON_HEIGHT = 30;

    public static final int DETECT_BUTTON_X = 300;
    public static final int DETECT_BUTTON_Y = 300;
    public static final int DETECT_BUTTON_WIDTH = 200;
    public static final int DETECT_BUTTON_HEIGHT = 50;

    // Window Bounds
    public static final int WINDOW_X = 500;
    public static final int WINDOW_Y = 280;
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 500;

    public static final int SCROLL_PANE_X = 400;
    public static final int SCROLL_PANE_Y = 100;
    public static final int SCROLL_PANE_WIDTH = 1200;
    public static final int SCROLL_PANE_HEIGHT = 800;

    // Label Bounds
    public static final int FIRST_SRC_LABEL_X = 230;
    public static final int FIRST_SRC_LABEL_Y = 94;
    public static final int FIRST_SRC_LABEL_WIDTH = 500;
    public static final int FIRST_SRC_LABEL_HEIGHT = 30;

    public static final int SECOND_SRC_LABEL_X = 230;
    public static final int SECOND_SRC_LABEL_Y = 154;
    public static final int SECOND_SRC_LABEL_WIDTH = 500;
    public static final int SECOND_SRC_LABEL_HEIGHT = 30;
}
