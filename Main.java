import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    PanelList  pnlList;
    Board pnlBoard;
    PanelBtn pnlBtn;

    public Main(){
        pnlList = new PanelList();
        pnlBoard = new Board(pnlList);
        pnlBtn = new PanelBtn(pnlBoard);
        this.setTitle("Knight tour");
        this.setLocationRelativeTo(null);
        this.setSize(500,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(pnlBoard, BorderLayout.CENTER);
        this.add(pnlList,BorderLayout.EAST);
        this.add(pnlBtn , BorderLayout.SOUTH);
    }

    public static void main(String[] args){
        Main main = new Main();
        main.setVisible(true);
    }
}
