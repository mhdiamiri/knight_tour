public class Block {
    private int num;
    private boolean dis;
    private String[] available;
    private int number = -1;

    public Block(){
        available = new String[8];
        this.num = 0;
    }

    public void setDis(boolean dis){
        this.dis = dis;
    }

    public boolean isDis() {
        return dis;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String[] getAvailable(){
        return available;
    }

    public void setAvailable(String access){
        this.available[num] = access;
        this.num++;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
