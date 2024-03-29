import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardGameFrame extends JFrame {
    private JPanel panel;
    private JTextField txtName;
    private JCheckBox CBOwned;
    private JRadioButton rb1;
    private JRadioButton rb2;
    private JRadioButton rb3;
    private JButton prevBtn;
    private JButton nxtBtn;
    private JButton saveBtn;
    private JButton deleteButton;
    private JButton addButton;
    private int index = 0;
    private int selectedRating;
    private final List<BoardGame> BGList = new ArrayList<>();

    public BoardGame getBG(int i){
        return BGList.get(i);
    }
    public BoardGameFrame() {
        setContentPane(panel);
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Board Games");

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(rb1);
        btnGroup.add(rb2);
        btnGroup.add(rb3);
        rb1.addItemListener(e -> handleRadioButtonClick(1));
        rb2.addItemListener(e -> handleRadioButtonClick(2));
        rb3.addItemListener(e -> handleRadioButtonClick(3));

        prevBtn.addActionListener(e -> {
            if (index > 0){
                index--;
                displayBG(getBG(index));
            }
            controlButtons();
        });
        nxtBtn.addActionListener(e -> {
            if (index < BGList.size() - 1) {
                index++;
                displayBG(getBG(index));
            }
            controlButtons();
        });
        deleteButton.addActionListener(e -> deleteGame());
        addButton.addActionListener(e -> {
            txtName.setText("");
            addNewGame(txtName.getText(), false, 0);
            index++;
        });
        saveBtn.addActionListener(e -> saveToFile());
        readingFromFIle();
        if (!BGList.isEmpty()){
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
                int rating = Integer.parseInt(parts[2]);
                BoardGame bg = new BoardGame(name, owned, rating);
                BGList.add(bg);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Soubor nenalezen: " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Wrongly formatted number: " + e.getLocalizedMessage());
        }
    }
    private void handleRadioButtonClick(int rating) {
        selectedRating = rating;
    }
    public void saveToFile() {
        BoardGame selectedBG = BGList.get(index);
        selectedBG.setName(txtName.getText());
        selectedBG.setOwned(CBOwned.isSelected());
        selectedBG.setRating(selectedRating);
        WriteIntoFile();
        JOptionPane.showMessageDialog(this, "Saved Game");
    }
    public void displayBG(BoardGame bg){
        txtName.setText(bg.getName());
        CBOwned.setSelected(bg.isOwned());
        switch (bg.getRating()){
            case 1 -> rb1.setSelected(true);
            case 2 -> rb2.setSelected(true);
            case 3 -> rb3.setSelected(true);
        }
    }

    public void addNewGame(String name, boolean owned, int rating){
        BoardGame newBG = new BoardGame(name, owned, rating);
        newBG.setName(txtName.getText());
        newBG.setOwned(CBOwned.isSelected());
        newBG.setRating(selectedRating);
        BGList.add(new BoardGame(name, owned, rating));
        JOptionPane.showMessageDialog(this, "New Game added");
        WriteIntoFile();
    }

    public void deleteGame(){
        if(!BGList.isEmpty()) BGList.remove(index);
        JOptionPane.showMessageDialog(this, "Game has been deleted");
        WriteIntoFile();
    }

    public void WriteIntoFile(){
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("deskovky.txt")))) {
            for (BoardGame bg : BGList) {
                writer.print(bg.getName() + ";" + (bg.isOwned() ? "owned" : "not owned") + ";" + bg.getRating() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getLocalizedMessage());
        }
    }
    public void controlButtons(){
        prevBtn.setEnabled(index != 0);
        nxtBtn.setEnabled(index != BGList.size() - 1);
    }
    public static void  main(String[] args) {
        BoardGameFrame BGFrame = new BoardGameFrame();
        BGFrame.setVisible(true);
    }
}
