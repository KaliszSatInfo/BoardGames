import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardGamesGUI extends JFrame {
    private JPanel panel;
    private JTextField txtName;
    private JCheckBox CBOwned;
    private JRadioButton RB1;
    private JRadioButton RB2;
    private JRadioButton RB3;
    private JButton prevBtn;
    private JButton nxtBtn;
    private JButton saveBtn;
    private final List<BoardGame> BGList = new ArrayList<>();
    private int index;
    private final int[] selectedScore = {1};

    public BoardGame getBG(int i){
        return BGList.get(i);
    }

    public List<BoardGame> getBGList(){
        return BGList;
    }
    public BoardGamesGUI() {
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(RB1);
        btnGroup.add(RB2);
        btnGroup.add(RB3);
        RB1.addItemListener(e -> handleRadioButtonClick(1));
        RB2.addItemListener(e -> handleRadioButtonClick(2));
        RB3.addItemListener(e -> handleRadioButtonClick(3));

        index = 0;
        prevBtn.addActionListener(e -> {
            if (index > 0){
                index--;
                displayBG(getBG(index));
            }
        });
        nxtBtn.addActionListener(e -> {
            if (index < BGList.size() - 1) {
                index++;
                displayBG(getBG(index));
            }
        });
        saveBtn.addActionListener(e -> saveToFile());
        readingFromFIle();
        if (!getBGList().isEmpty()){
            displayBG(getBG(index));
        } else {
            JOptionPane.showMessageDialog(this, "There is nothing in the list", "Error", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void readingFromFIle() {
        try (Scanner sc = new Scanner(new BufferedReader(new FileReader("deskovky.txt")))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(";");
                String name = (parts[0]);
                boolean owned = parts[1].equals("owned");
                int score = Integer.parseInt(parts[2]);
                BoardGame bg = new BoardGame(name, owned, score);
                BGList.add(bg);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            System.err.println("Wrongly formatted number: " + e.getLocalizedMessage());
        }
    }
    private void handleRadioButtonClick(int score) {
        selectedScore[0] = score;
    }
    public void saveToFile() {
        BoardGame selectedBG = BGList.get(index);
        selectedBG.setName(txtName.getText());
        selectedBG.setOwned(CBOwned.isSelected());
        selectedBG.setScore(selectedScore[0]);

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("deskovky.txt")))) {
            for (BoardGame bg : BGList) {
                writer.println(bg.getName() + ";" + (bg.isOwned() ? "owned" : "not owned") + ";" + bg.getScore());
            }
            JOptionPane.showMessageDialog(this, "Changes saved to file.", "Message saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getLocalizedMessage());
        }
    }
    public void displayBG(BoardGame bg){
        txtName.setText(bg.getName());
        CBOwned.setSelected(bg.isOwned());
        switch (bg.getScore()){
            case 1 -> RB1.setSelected(true);
            case 2 -> RB2.setSelected(true);
            case 3 -> RB3.setSelected(true);
        }
    }
    public static void main(String[] args) {
        BoardGamesGUI bgGUI = new BoardGamesGUI();
        bgGUI.setContentPane(bgGUI.panel);
        bgGUI.setSize(300, 300);
        bgGUI.setDefaultCloseOperation(EXIT_ON_CLOSE);
        bgGUI.setTitle("Board Games");
        bgGUI.setVisible(true);
    }
}
