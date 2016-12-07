
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class that creates GUI, computes palindrome, and returns a message GUITester
 * extends JFrame class and implements ActionListener
 *
 * @author Kayger Duran-Mateo
 */
public class GUITester extends JFrame implements ActionListener {

    // private instance variables
    private JFrame frame;
    private JPanel jp;
    private JTextField text;
    private JTextArea output;
    private JButton test;
    private JButton load;
    Stack<Character> st = new Stack<Character>();
    Queue<Character> q = new LinkedList<Character>();
    ArrayList<String> wordList = new ArrayList<String>();
    File f;
    String message = " ";

    /**
     * empty constructor with no parameters
     */
    public GUITester() {

    }

    /**
     * Method that constructs the GUI frame and its components
     */
    public void display() {
        //frame 
        frame = new JFrame("DECODER");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //layout
        jp = new JPanel();
        jp.setBackground(Color.GRAY);
        frame.add(jp);
        frame.setSize(300, 200);

        //text field
        text = new JTextField(20);
        text.setEditable(false);
        jp.add(text);
        //Jbuttons 
        load = new JButton("load");
        load.setBackground(Color.CYAN);
        jp.add(load);
        load.addActionListener(this);
        test = new JButton("Test");
        test.setBackground(Color.CYAN);
        test.addActionListener(this);
        jp.add(test);
        //text area
        output = new JTextArea(3, 14);
        output.setEditable(false);
        jp.add(output);
        frame.setVisible(true);
        jp.repaint();

    }

    /**
     * Method that implements ActionListener
     *
     * @param e passes JButton test and load as an action listener capable
     * component
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == test) {
            if (text.getText().equals("")) {

                JOptionPane.showMessageDialog(this, "File has not been loaded: ", "File not Recognized", 0);

            } else {
                FileCases();
                System.out.println("test hit");
            }
        } else {
            loadFile();
            System.out.println("file loaded");
        }
    }

    /**
     * method called by the load JButton actionPerformed method allowing the
     * user to select file through JChooser and throws dialog prompt of selected
     * file's path.
     */
    public void loadFile() {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            text.setText(chooser.getSelectedFile().getPath());
            JOptionPane.showConfirmDialog(this, "You chose to open this file: " + chooser.getSelectedFile().getPath(), "File Loaded", 2, 1);
        }
    }

    /**
     * method called from actionPerformed test JButton that reads ".txt" file
     * into file object and stores individual words into both a stack and queue.
     * FileNotFoundException thrown if file is not valid or found on OS.
     * Palindrome Special cases: last, side by side, one character, and side by
     * side palindrome pointing to same word. Note message string is reset to
     * allow for multiple test cases after each run.
     */
    public void FileCases() {

        //spicific file to be read through
        f = new File(text.getText());
        //split declaration because scanner is in try catch (file not found exception)
        Scanner scan;
        try {
            scan = new Scanner(f);
            //adding file string contents to arraylist wordList
            while (scan.hasNext()) {
                wordList.add(scan.next());
            }
            //special case conditions handled 
            for (int i = 0; i < wordList.size(); i++) {
                if (isPal(wordList.get(i)) == true) {
                    if (wordList.get(i).length() + i >= wordList.size()) //takes care of last position, overreaching, & first pal bigger than list size
                    {

                        message = "This is an invalid message: palindrome at end and or palindrome over reaching message length";
                        throw new IndexOutOfBoundsException(message);

                    }

                    if (i + 1 < wordList.size() && isPal(wordList.get(i + 1)) == true) 
                    {

                        //sidebyside case of same end point negation (eliminates duplicates)
                        if (wordList.get(i).length() != wordList.get(i + 1).length() + 1) 
                        {
                            //add word to message
                            message = message + wordList.get(i + wordList.get(i).length()) + " ";
                            //message to text area
                            output.setText(message);

                        }

                    } 
                    else 
                    {
                        //always printed if isPal(i+1 == false) 
                        message = message + wordList.get(i + wordList.get(i).length()) + " ";
                        //add message to text area
                        output.setText(message);

                    }
                }

            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GUITester.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("File Not Found");
        }
        catch (IndexOutOfBoundsException ex){
        //message passed to exception inside if condition line 144
         output.setText( ex.getLocalizedMessage()); 
        // System.out.println(output);

        }
        //message reset for clean output every run
        message = "";

    }

    /**
     * Helper method that is called to perform main function of filling stack
     * and queue and then comparing nodes to determine a valid palindrome
     *
     * @param _scan represents the String word objects passed into method from
     * scanner
     * @return boolean match defining if it meets the requirements of a
     * palindrome
     */
    public boolean isPal(String _scan) {
        //set to true 
        boolean match = true;

        if (_scan != null) {
            //fill in stack and queue
            for (int i = 0; i < _scan.length(); i++) {
                st.push(_scan.charAt(i));
                q.add(_scan.charAt(i));
            }
            for (int i = 0; i < _scan.length(); i++) {
                //comparisions 
                if (!st.pop().equals(q.remove())) {
                    match = false;

                }

            }
        }
        return match;
    }

}
