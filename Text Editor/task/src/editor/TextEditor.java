package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    public static final JTextArea jTextArea = new JTextArea();
    public static boolean withRegex = false;

    public static int index = 0;
    public static int i = 1;
    public static Map<Integer, String> map = new LinkedHashMap<>();

    public TextEditor() {

        super("TextEditor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new BorderLayout(10, 5));
        setLocationRelativeTo(null);


        JButton saveButton = new JButton(new ImageIcon("C:\\Users\\RAMONA\\Desktop\\Icons\\Save.png"));
        saveButton.setName("SaveButton");

        JButton openButton = new JButton(new ImageIcon("C:\\Users\\RAMONA\\Desktop\\Icons\\Open.png"));
        openButton.setName("OpenButton");

        JTextField searchField = new JTextField();
        searchField.setName("SearchField");

        JButton searchButton = new JButton(new ImageIcon("C:\\Users\\RAMONA\\Desktop\\Icons\\Search.png"));
        searchButton.setName("StartSearchButton");

        JButton prevButton = new JButton(new ImageIcon("C:\\Users\\RAMONA\\Desktop\\Icons\\Previous.png"));
        prevButton.setName("PreviousMatchButton");

        JButton nextButton = new JButton(new ImageIcon("C:\\Users\\RAMONA\\Desktop\\Icons\\Next.png"));
        nextButton.setName("NextMatchButton");

        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setName("FileChooser");
        jFileChooser.setVisible(false);

        JCheckBox useRegexCheck = new JCheckBox("Use regex");
        useRegexCheck.setName("UseRegExCheckbox");

        JPanel textAreaAndButtons = new JPanel();
        textAreaAndButtons.setLayout(new BoxLayout(textAreaAndButtons, BoxLayout.X_AXIS));
        textAreaAndButtons.add(saveButton);
        textAreaAndButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        textAreaAndButtons.add(openButton);
        textAreaAndButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        textAreaAndButtons.add(searchField);
        textAreaAndButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        textAreaAndButtons.add(searchButton);
        textAreaAndButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        textAreaAndButtons.add(prevButton);
        textAreaAndButtons.add(Box.createRigidArea(new Dimension(5, 0)));
        textAreaAndButtons.add(nextButton);
        textAreaAndButtons.add(useRegexCheck);
        textAreaAndButtons.add(jFileChooser);

        textAreaAndButtons.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));


        jTextArea.setName("TextArea");
        jTextArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setName("ScrollPane");
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));


        saveButton.addActionListener(actionEvent -> {

            jFileChooser.setVisible(true);

            int returnValue = jFileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = new File(jFileChooser.getSelectedFile().toString());
                try  (PrintWriter printWriter = new PrintWriter(file)) {
                    printWriter.print(jTextArea.getText());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        openButton.addActionListener(actionEvent -> {

            jFileChooser.setVisible(true);

            int returnValue = jFileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                jTextArea.setText(null);
                try {
                    File selectedFile = jFileChooser.getSelectedFile();
                    jTextArea.setText(new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath()))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        searchButton.addActionListener(actionEvent -> {

            map.clear();
            index = 0;
            i = 1;

            if (withRegex) {

                Pattern pattern = Pattern.compile(searchField.getText());

                Matcher matcher = pattern.matcher(jTextArea.getText());
                while (matcher.find()) {
                    index++;
                    map.put(index, matcher.start() + " " + matcher.end());
                }

            } else {

                if (jTextArea.getText().contains(searchField.getText())) {
                    int i = jTextArea.getText().indexOf(searchField.getText());
                    while (i >= 0) {
                        index++;
                        map.put(index, i + " " + (i + searchField.getText().length()));
                        i = jTextArea.getText().indexOf(searchField.getText(), i + 1);
                    }
                }
            }

            String bound = map.get(1);
            String[] bounds = bound.split(" ");

            jTextArea.setCaretPosition(Integer.parseInt(bounds[1]));
            jTextArea.select(Integer.parseInt(bounds[0]), Integer.parseInt(bounds[1]));
            jTextArea.grabFocus();

        });

        nextButton.addActionListener(actionEvent -> {

            if (i >= index) {
                i = 1;
            } else {
                i++;
            }
            String bound = map.get(i);
            String[] bounds = bound.split(" ");

            jTextArea.setCaretPosition(Integer.parseInt(bounds[1]));
            jTextArea.select(Integer.parseInt(bounds[0]), Integer.parseInt(bounds[1]));
            jTextArea.grabFocus();

        });

        prevButton.addActionListener(actionEvent -> {
            if (i == 1) {
                i = index;
            } else {
                i--;
            }
            String bound = map.get(i);
            String[] bounds = bound.split(" ");

            jTextArea.setCaretPosition(Integer.parseInt(bounds[1]));
            jTextArea.select(Integer.parseInt(bounds[0]), Integer.parseInt(bounds[1]));
            jTextArea.grabFocus();

        });

        useRegexCheck.addActionListener(actionEvent -> withRegex = !withRegex);

        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setName("MenuOpen");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setName("MenuSave");
        JMenuItem closeMenuItem = new JMenuItem("Exit");
        closeMenuItem.setName("MenuExit");

        JMenuItem searchMenuItem = new JMenuItem("Start search");
        searchMenuItem.setName("MenuStartSearch");
        JMenuItem prevMenuItem = new JMenuItem("Previous match");
        prevMenuItem.setName("MenuPreviousMatch");
        JMenuItem nextMenuItem = new JMenuItem("Next match");
        nextMenuItem.setName("MenuNextMatch");
        JMenuItem useRegexMenuItem = new JMenuItem("Use regular expressions");
        useRegexMenuItem.setName("MenuUseRegExp");


        saveMenuItem.addActionListener(e -> saveButton.doClick());
        openMenuItem.addActionListener(e -> openButton.doClick());
        closeMenuItem.addActionListener(event -> System.exit(0));

        searchMenuItem.addActionListener(e -> searchButton.doClick());
        nextMenuItem.addActionListener(e -> nextButton.doClick());
        prevMenuItem.addActionListener(e -> prevButton.doClick());
        useRegexMenuItem.addActionListener(e -> useRegexCheck.doClick());

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(closeMenuItem);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setName("MenuSearch");
        searchMenu.setMnemonic(KeyEvent.VK_A);
        searchMenu.add(searchMenuItem);
        searchMenu.add(prevMenuItem);
        searchMenu.add(nextMenuItem);
        searchMenu.add(useRegexMenuItem);


        add(BorderLayout.CENTER, jScrollPane);
        add(textAreaAndButtons, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(searchMenu);

        setJMenuBar(menuBar);
        setVisible(true);

    }

}
