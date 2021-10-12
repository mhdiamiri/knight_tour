import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Board extends JPanel implements MouseListener {
    private PanelList list;
    Block[][] blocks = new Block[8][8];
    private boolean disableButtons = false;
    private int distanceX;
    private int distanceY;
    private final int margin = 15;
    private int stepCounter;
    private int firstI;
    private int firstJ;
    private int nextI;
    private int nextJ;
    Timer timer;

    public Board(PanelList list){
        this.list = list;
        for (int j = 0 ; j<8 ; j++)
            for(int i = 0 ; i<8 ; i++)
                blocks[i][j] = new Block();
        setBlockAccess();
        addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        distanceX = (w-margin)/8;
        distanceY = (h-margin)/8;
        for (int j = 0 ; j<8 ; j++){
            for (int i = 0 ; i < 8 ; i++){
                if ((i%2 == 0 && j%2 == 0) || (i%2 != 0 && j%2 != 0)){
                    g.setColor(new Color(0, 77, 64));
                }else{
                    g.setColor(Color.white);
                }
                g.fillRect(i* distanceX +margin , j*distanceY+margin,distanceX,distanceY);
                g.drawRect(i* distanceX +margin, j*distanceY+margin,distanceX,distanceY);
                if (blocks[i][j].getNumber() != -1){
                    if (blocks[i][j].getNumber() == stepCounter){
                        int picX =i* distanceX +margin;
                        int picY =j*distanceY+margin;
                        Image img = Toolkit.getDefaultToolkit().getImage("src/knight.png");
                        g.drawImage(img, picX, picY, distanceX,distanceY , this);
                    }else {
                        int x =(i + 1) * distanceX - distanceX / 2 + margin - 5;
                        int y =(j + 1) * distanceY - distanceY / 2 + margin + 5;
                        g.setColor(Color.black);
                        String string = Integer.toString(blocks[i][j].getNumber());
                        g.drawString(string, x, y);
                    }
                }
            }
            String str = Integer.toString(j);
            g.setColor(Color.BLACK);
            g.drawString(str,0,10+j*distanceY + distanceY/2+5);
            g.drawString(str,10+ j*distanceX + distanceX/2,10);
        }
    }

    private void setBlockAccess(){
        for (int j = 0 ; j<8 ; j++)
            for(int i = 0 ; i<8 ; i++){
                int[] ti = {i - 1, i - 2, i + 1, i + 2, i - 1, i - 2, i + 1, i + 2};
                int[] tj = {j - 2, j - 1, j - 2, j - 1, j + 2, j + 1, j + 2, j + 1};
                String access;
                blocks[i][j].setNum(0);
                for (int k = 0; k < 8; k++) {
                    if ((ti[k] >= 0 && ti[k] < 8) && (tj[k] >= 0 && tj[k] < 8)) {
                        if (!blocks[ti[k]][tj[k]].isDis()) {
                            access = ti[k] + "_" + tj[k];
                            blocks[i][j].setAvailable(access);
                        }
                    }
                }
            }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getY()>= margin && e.getX()>= margin) {
            int i = (e.getX() - margin) / distanceX;
            int j = (e.getY() - margin) / distanceY;
            if (i < 8 && j < 8 && i >= 0 && j >= 0){
                firstI = i;
                firstJ = j;
                click();
            }
        }
    }

    private void click(){
        if (!disableButtons) {
            String[] str = {"Start", "Cancel"};
            int ans;
            String message = String.format("i = %d And j = %d \n Available blocks = %d", firstI, firstJ, blocks[firstI][firstJ].getNum());

            ans = JOptionPane.showOptionDialog(null, message, "Information"
                    , 0, JOptionPane.INFORMATION_MESSAGE
                    , null, str, 0);
            if (ans == 0) {
                blocks[firstI][firstJ].setDis(true);
                blocks[firstI][firstJ].setNumber(0);
                disableButtons = true;
                list.area.setText("");
                list.area.append("0-(" + firstI + "," + firstJ + ")\n");
                stepCounter = 0;

                timer = new Timer(500, e -> {
                    repaint();
                    if (stepCounter == 0) step(firstI, firstJ);
                    else step(nextI, nextJ);
                });
                timer.start();
            }
        }
    }

    private void step(int i , int j){
        String[] strings = blocks[i][j].getAvailable();
        int minI = -1;
        int minJ = -1;

        String otherMins = "";
        int other = 0;
        for (int k = 0; k < blocks[i][j].getNum(); k++) {
            int ti = strings[k].charAt(0) - 48;
            int tj = strings[k].charAt(2) - 48;
            if (minI == -1) {
                if (!blocks[ti][tj].isDis()) {
                    minI = ti;
                    minJ = tj;
                }
            } else {
                if (blocks[ti][tj].getNum() < blocks[minI][minJ].getNum()) {
                    if (!blocks[ti][tj].isDis()) {
                        minI = ti;
                        minJ = tj;
                    }
                }
                else if (blocks[ti][tj].getNum() == blocks[minI][minJ].getNum()) {
                    if (!blocks[ti][tj].isDis()) {
                        otherMins += ti+""+tj+"_";
                        other++;
                    }
                }
            }
        }

        if (minI == -1){
            fail();
            timer.stop();
        }else {
            if (other == 0) {
                nextI = minI;
                nextJ = minJ;
                blocks[nextI][nextJ].setNumber(stepCounter + 1);
                blocks[nextI][nextJ].setDis(true);
            } else {
                Random random = new Random();
                int choosed = random.nextInt((otherMins.length() + 1) / 3 + 1);
                if (choosed == 0) {
                    nextI = minI;
                    nextJ = minJ;
                    blocks[nextI][nextJ].setNumber(stepCounter + 1);
                    blocks[nextI][nextJ].setDis(true);
                } else {
                    nextI = otherMins.charAt(choosed * 3 - 3) - 48;
                    nextJ = otherMins.charAt(choosed * 3 - 2) - 48;
                    blocks[nextI][nextJ].setNumber(stepCounter + 1);
                    blocks[nextI][nextJ].setDis(true);
                }
                setBlockAccess();
            }
            list.area.append(stepCounter + 1 + "-(" + i + "," + j + ")\n");
            stepCounter++;
        }

        if (stepCounter == 63){
            done();
            timer.stop();
        }

    }

    private boolean isClosed(){
        int[] ti = {nextI - 1, nextI - 2, nextI + 1, nextI + 2, nextI - 1, nextI - 2, nextI + 1, nextI + 2};
        int[] tj = {nextJ - 2, nextJ - 1, nextJ - 2, nextJ - 1, nextJ + 2, nextJ + 1, nextJ + 2, nextJ + 1};
        for (int k = 0; k < 8; k++)
            if (ti[k] == firstI && tj[k]==firstJ)
                return true;
        return false;
}

    private void done(){
            String R;
            if (isClosed()){
                list.area.setForeground(new Color(10, 54, 11));
                list.area.append("Done.\nTour is close.");
                R = "Done.\nTour is close.";
            }else{
                list.area.setForeground(new Color(230 , 81 , 0));
                list.area.append("Done.\n Tour is Open.");
                R = "Done.\nTour is Open.";
            }
            JOptionPane.showMessageDialog(null,R ,"result",JOptionPane.INFORMATION_MESSAGE);
    }

    private void fail(){
        list.area.setForeground(new Color(183 , 28 , 28));
        list.area.append("Failed!");
        String[] str = {"reset", "close"};
        int ans;
        ans = JOptionPane.showOptionDialog(null, "Failed!", "Result"
                ,0, JOptionPane.ERROR_MESSAGE
                , null, str, 0);
        if (ans == 0){
            reset();
        }
    }

    protected void reset() {
        for (int j = 0 ; j<8 ; j++)
            for(int i = 0 ; i<8 ; i++){
                blocks[i][j].setNumber(-1);
                disableButtons = false;
                blocks[i][j].setDis(false);
            }
        list.area.setText("");
        setBlockAccess();
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }
    @Override
    public void mouseReleased(MouseEvent e) {

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    @Override
    public void mouseExited(MouseEvent e) {

    }
}
