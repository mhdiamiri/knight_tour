import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelBtn extends JPanel implements ActionListener {
    JButton resetBtn = new JButton("Reset");
    JButton exitBtn = new JButton("Exit");
    Board board ;
    public PanelBtn(Board board){
        setLayout(new GridLayout(1,2));
        this.board = board;
        resetBtn.setForeground(Color.black);
        exitBtn.setForeground(Color.black);
        resetBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        add(resetBtn);
        add(exitBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == resetBtn) {
            int ans = JOptionPane.showOptionDialog(null, "Are you sure?", "Reset", JOptionPane.YES_NO_OPTION
                    , JOptionPane.QUESTION_MESSAGE, null, null, 1);
            if (ans == 0)
                board.reset();
        }
        else
            System.exit(0);
    }
}
