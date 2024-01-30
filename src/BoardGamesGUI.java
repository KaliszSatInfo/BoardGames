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

    public BoardGame getBG(int i){
        return BGList.get(i);
    }
    public BoardGamesGUI() {
        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(RB1);
        btnGroup.add(RB2);
        btnGroup.add(RB3);

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
        readingFromFIle();
        displayBG(getBG(index));
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
            System.err.println("Wrongly formated number: " + e.getLocalizedMessage());
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
        bgGUI.setSize(500, 700);
        bgGUI.setDefaultCloseOperation(EXIT_ON_CLOSE);
        bgGUI.setTitle("Board Games");
        bgGUI.setVisible(true);
    }
}
