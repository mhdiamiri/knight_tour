import javax.swing.*;
import java.awt.*;

public class PanelList extends JPanel {
    public JTextArea area = new JTextArea(5,10);
    private JScrollPane scrollPane;
    public PanelList(){
        setBackground(new Color(245 , 245 , 245));
        area.setEditable(false);
        area.setBackground(new Color(245 , 245 , 245));
        setLayout(new GridLayout(1,1));
        scrollPane = new JScrollPane(area);
        scrollPane.setBackground(new Color(245 , 245 , 245));
        add(scrollPane);
    }

}