package CloneDetection;

import GUI.GUIFactory;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.net.*;

public class Main extends JFrame {

    /**
     * The two file to compared. Default file names are "src/source1.java" and "src/source2.java";
     */
    private String leftFile = "src/source1.java";
    private String rightFile = "src/source2.java";

    private File[] leftFilesInDirectory = new File[0];
    private File[] rightFilesInDirectory = new File[0];

    private CloneDetection cloneDetection = new CloneDetection();

    ArrayList<String> matchSources = new ArrayList<>();

    /**
     * Main function for program
     */
    public static void main(String[] args) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main form = new Main();
                form.setVisible(true);
            }
        });

    }

    /**
     * Output the clone detection result to a html file and show the html in a panel
     * @param clones
     * @throws IOException
     */
    private void outputToHtml(Set<CodeSegmentPairInterface> clones) throws IOException {

        StringBuilder buf = new StringBuilder();
        buf.append(Constants.HTML_INTRO_TEXT);


        int index = 0;

        for (CodeSegmentPairInterface clone : clones) {

            CodeSegmentInterface leftCodeSegment = clone.getLeftSegment();

            CodeSegmentInterface rightCodeSegment = clone.getRightSegment();
            double score = clone.getSimilarityScore();
            int mimimumEditDistance = clone.getMimimumEditDistance();

            buf.append(Constants.TABLE_ROW_OPEN_CELL)
                    .append(leftCodeSegment.getNode().toString().replaceAll(" ", "&nbsp;")
                            .replaceAll("\n", "<br />"))
                    .append(Constants.TABLE_CELL)
                    .append(rightCodeSegment.getNode().toString().replaceAll(" ", "&nbsp;")
                            .replaceAll("\n", "<br />"))
                    .append(Constants.TABLE_CELL)
                    .append(mimimumEditDistance)
                    .append(Constants.TABLE_CELL)
                    .append(score)
                    .append(Constants.TABLE_CELL)
                    .append(matchSources.get(index))
                    .append(Constants.TABLE_ROW_CLOSE_CELL);
            index++;
        }

        buf.append(Constants.TABLE_CLOSE);
        String html = buf.toString();

        try (PrintStream out = new PrintStream(new FileOutputStream(Constants.OUTPUT_HTML_NAME))) {
            out.print(html);
            JTextPane tp = new JTextPane();
            JScrollPane js = new JScrollPane();
            js.getViewport().add(tp);
            JFrame jf = new JFrame(Constants.RESULTS_TITLE);
            jf.getContentPane().add(js);
            jf.pack();
            jf.setLocation(Constants.SCROLL_PANE_X, Constants.SCROLL_PANE_Y);
            jf.setSize(Constants.SCROLL_PANE_WIDTH, Constants.SCROLL_PANE_WIDTH);

            jf.setVisible(true);
            URL url = new File(Constants.OUTPUT_HTML_NAME).toURI().toURL();
            tp.setPage(url);


        } catch (Exception e) {
            return;
        }
    }

    /**
     * GUI implementation. It shows a panel with two file chooser and a button to invoke the CloneDetection core logic functions
     */
    public Main() {
        // Create Main Form Frame
        super(Constants.MAIN_TITLE);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLocation(Constants.WINDOW_X, Constants.WINDOW_Y);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);


        // Label for left file
        JLabel lblResult1 = GUIFactory.createSourceLabel(
                Constants.FIRST_SRC_LABEL_X,
                Constants.FIRST_SRC_LABEL_Y,
                Constants.FIRST_SRC_LABEL_WIDTH,
                Constants.FIRST_SRC_LABEL_HEIGHT,
                leftFile);

        getContentPane().add(lblResult1);

        // Create Button Open JFileChooser
        JButton firstSourceButton = GUIFactory.createSourceButton(
                Constants.FIRST_SRC_BUTTON_LBL,
                Constants.FIRST_SRC_BUTTON_X,
                Constants.FIRST_SRC_BUTTON_Y,
                Constants.FIRST_SRC_BUTTON_WIDTH,
                Constants.FIRST_SRC_BUTTON_HEIGHT);

        firstSourceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();

                //set default selection
                fileopen.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileopen.setCurrentDirectory(new File("./src"));
                fileopen.setSelectedFile(new File("." + leftFile));

                //filter relavent file type only
                FileFilter filter = new FileNameExtensionFilter(Constants.FILTER_LABEL, "java");
                fileopen.addChoosableFileFilter(filter);

                int ret = fileopen.showDialog(null, Constants.CHOOSE_BUTTON_LBL);

                //get file or directory of files
                if (ret == JFileChooser.APPROVE_OPTION) {
                    if(fileopen.getSelectedFile().isDirectory()){
                        leftFilesInDirectory = fileopen.getSelectedFile().listFiles();
                    }else {
                        leftFilesInDirectory = new File[] {fileopen.getSelectedFile()};
                    }
                    leftFile = fileopen.getSelectedFile().getName();
                    lblResult1.setText(leftFile);
                }

            }
        });
        getContentPane().add(firstSourceButton);

        // Label for right file
        JLabel lblResult2 = GUIFactory.createSourceLabel(
                Constants.SECOND_SRC_LABEL_X,
                Constants.SECOND_SRC_LABEL_Y,
                Constants.SECOND_SRC_LABEL_WIDTH,
                Constants.SECOND_SRC_LABEL_HEIGHT,
                rightFile);

        getContentPane().add(lblResult2);

        // Create Button Open JFileChooser
        JButton secondSourceButton = GUIFactory.createSourceButton(
                Constants.SECOND_SRC_BUTTON_LBL,
                Constants.SECOND_SRC_BUTTON_X,
                Constants.SECOND_SRC_BUTTON_Y,
                Constants.SECOND_SRC_BUTTON_WIDTH,
                Constants.SECOND_SRC_BUTTON_HEIGHT);

        secondSourceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                fileopen.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileopen.setCurrentDirectory(new File("./src"));
                fileopen.setSelectedFile(new File("." + leftFile));
                FileFilter filter = new FileNameExtensionFilter(Constants.FILTER_LABEL, "java");
                fileopen.addChoosableFileFilter(filter);

                int ret = fileopen.showDialog(null, Constants.CHOOSE_BUTTON_LBL);

                //get file or directory of files
                if (ret == JFileChooser.APPROVE_OPTION) {
                    if(fileopen.getSelectedFile().isDirectory()){
                        rightFilesInDirectory = fileopen.getSelectedFile().listFiles();
                    }else {
                        rightFilesInDirectory = new File[] {fileopen.getSelectedFile()};
                    }
                    rightFile = fileopen.getSelectedFile().getName();
                    lblResult2.setText(rightFile);
                }
            }
        });
        getContentPane().add(secondSourceButton);

        JButton detectButton = GUIFactory.createSourceButton(
                Constants.DETECT_BUTTON_LBL,
                Constants.DETECT_BUTTON_X,
                Constants.DETECT_BUTTON_Y,
                Constants.DETECT_BUTTON_WIDTH,
                Constants.DETECT_BUTTON_HEIGHT);

        detectButton.addActionListener(new ActionListener() {
            Set<CodeSegmentPairInterface> clones;
            public void actionPerformed(ActionEvent e) {
                Set<CodeSegmentPairInterface> clones = new HashSet<>();

                //in case of no files entered, do default demo files
                if(leftFilesInDirectory.length == 0 && rightFilesInDirectory.length == 0){
                    try{
                        cloneDetection = new CloneDetection(leftFile, rightFile);
                        clones.addAll(cloneDetection.findClones());
                        matchSources.addAll(cloneDetection.getCloneFileNames());
                    }catch (IOException ex) {

                    }
                }

                //else iterate over all selected files
                for(File lFile : leftFilesInDirectory){
                    for(File rFile : rightFilesInDirectory){
                        try{
                            cloneDetection = new CloneDetection(lFile.toString(), rFile.toString());
                            clones.addAll(cloneDetection.findClones());
                            matchSources.addAll(cloneDetection.getCloneFileNames());
                        }catch (IOException ex) {

                        }
                    }
                }
                try{
                    outputToHtml(clones);
                }catch (IOException ex) {

                }
            }
        });
        getContentPane().add(detectButton);


    }


}

