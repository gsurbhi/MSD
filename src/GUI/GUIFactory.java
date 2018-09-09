package GUI;

import javax.swing.*;

public class GUIFactory {

    /**
     * create button based on inputs
     * @param label the description on the button
     * @param x the x of the button
     * @param y the y of the button
     * @param width the width of the button
     * @param height the height of the button
     * @returns constructed button
     */
    public static JButton createSourceButton(String label, int x, int y,
                                      int width, int height){
        JButton jButton = new JButton(label);
        jButton.setBounds(x, y, width, height);

        return jButton;
    }

    /**
     * create label based on inputs
     * @param label the description on the label
     * @param x the x of the label
     * @param y the y of the label
     * @param width the width of the label
     * @param height the height of the label
     * @returns constructed label
     */
    public static JLabel createSourceLabel(int x, int y,
                                           int width, int height,
                                           String label){
        JLabel jLabel = new JLabel("", JLabel.LEFT);
        jLabel.setBounds(x, y, width, height);
        jLabel.setText(label);

        return jLabel;
    }
}
