import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
class gameDataFrame extends JFrame implements ActionListener{
    FiveGame gameGraphic =new FiveGame();
    JButton []jb = new JButton[3];
    public gameDataFrame(){
        this.setTitle("五子棋控制中心 - Data");
        this.setSize(700,700);
        setLocationRelativeTo(this);
        setBounds(900,100,300,700);
        super.setLayout(new GridLayout(3,1));
        this.setVisible(true);//視窗的起始設定要在setVisible之前！！！//
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        jb[0] = new JButton("悔棋");
        jb[1] = new JButton("重新開局");
        jb[2] = new JButton("關閉程式");
        for(int i=0;i<3;i++){
            super.add(jb[i]);
            jb[i].addActionListener(this);
        }
    }
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        if(command.equals("悔棋")){
            System.out.println("悔棋");
            gameGraphic.back();
        }else if(command.equals("重新開局")){
            System.out.println("重新開局");
            gameGraphic.resetGame();
        }else if(command.equals("關閉程式")){
            System.out.println("關閉程式");
            System.exit(0);
        }
    }
}
class Dot{
    boolean notEmpty;
    String color="not";
}
class Data{
    int x,y;
    String color;
    int colorInt;//1是黑色 2是白色
    public Data(int x,int y,String color,int colorInt){
        this.x=x;
        this.y=y;
        this.color=color;
        this.colorInt=colorInt;
    }
}
public class FiveGame extends JFrame implements MouseInputListener,ActionListener{
    Dot []dot= new Dot[225+56];//56是判斷邊界
    Data []chess = new Data[225];
    int x=0,y=0;
    int moveTime=0;
    int controlColor=1;
    int n = 0;

    int []storeXY = new int[256];
    public FiveGame(){
        this.setTitle("五子棋 FiveChess");
        this.setSize(700,700);
        // setLocationRelativeTo(this); //設定視窗初始位置在螢幕中心
        setBounds(200,100,700,700);
        setResizable(false); //設定視窗大小不可變
        //選單列

        this.setVisible(true);//視窗的起始設定要在setVisible之前！！！//
        this.addMouseListener(this);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        //initinalize
        for(int i=0;i<225+56;i++){
            dot[i] = new Dot();
        }

    }
    public void resetGame(){
        for(int i=0;i<225;i++){
            dot[i].notEmpty = false;
            dot[i].color = "not";
            // chess[i].x = 0;
            // chess[i].y = 0;
            // chess[i].color = "not";
            // chess[i].colorInt = 0;
        }
        for(int i=0;i<moveTime;i++){
            chess[i].color = "not";
            chess[i].colorInt = 0;
        }
        repaint();
    }
    //override
    public void actionPerformed(ActionEvent e){

    }
    public void mouseClicked (MouseEvent e){}
    public void mouseEntered (MouseEvent e){}
    public void mouseExited  (MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseMoved   (MouseEvent e){}
    public void mouseDragged (MouseEvent e){}
    //點擊事件
    public void mousePressed (MouseEvent e){
        //檢查x y座標與預期是否相同
        // JOptionPane.showMessageDialog(this,e.getX()+" "+e.getY());
        if (e.getX()<65 || e.getX()>625 || e.getY() <65 || e.getY()>620){
            JOptionPane.showMessageDialog(this,"超出範圍囉，請重新下");
            return ;
        }

        final int grid = 40;
        //餘數小於一半
        if((e.getX()-grid)%grid < 40)
            x = (e.getX()-grid)/grid;
        else
            x = ((e.getX()-grid)/grid)+1;
        if((e.getY()-grid)%grid < 40)
            y = (e.getY()-grid)/grid;
        else
            y = ((e.getY()-grid)/grid)+1;

        //棋盤邊界  x 65 ~ 560
        //         y 65 ~ 560

        //下過的位置不能下
        if(dot[15*y+x].notEmpty)
            return ;
        System.out.print("x 座標:"+x+" y座標:"+y);
        if(controlColor==1){
            storeXY[moveTime] = 15*y+x;
            chess[moveTime++] = new Data(x,y,"BLACK",1);
            controlColor=2;
            dot[15*y+x].notEmpty=true;
            dot[15*y+x].color="BLACK";

        }else if(controlColor==2){
            storeXY[moveTime] = 15*y+x;
            chess[moveTime++] = new Data(x,y,"WHITE",2);
            controlColor=1;
            dot[15*y+x].notEmpty=true;
            dot[15*y+x].color="WHITE";
        }

        int tmp = moveTime-1;
        System.out.println(chess[tmp].color);
        repaint();

        //判斷輸贏 4種
        //左至右
        //上至下
        //至右下
        //至左下
        for(int i=0;i<225;i++){
            if(dot[i].notEmpty){
                // System.out.println("i是"+i+"不是空的");
                if(dot[i].color.equals(dot[i+1].color)&&
                    dot[i+1].color.equals(dot[i+2].color)&&
                    dot[i+2].color.equals(dot[i+3].color)&&
                    dot[i+3].color.equals(dot[i+4].color)){
                    //左至右
                    System.out.println(dot[i].color+" win!!!");
                    JOptionPane.showMessageDialog(this,dot[i].color+"獲勝啦！");
                    // new FiveGame();
                    resetGame();
                }else if(dot[i].color.equals(dot[i+15].color)&&
                    dot[i+15].color.equals(dot[i+30].color)&&
                    dot[i+30].color.equals(dot[i+45].color)&&
                    dot[i+45].color.equals(dot[i+60].color)){
                    //上至下
                    System.out.println(dot[i].color+" win!!!");
                    JOptionPane.showMessageDialog(this,dot[i].color+"獲勝啦！");
                    resetGame();
                }else if(dot[i].color.equals(dot[i+16].color)&&
                    dot[i+16].color.equals(dot[i+32].color)&&
                    dot[i+32].color.equals(dot[i+48].color)&&
                    dot[i+48].color.equals(dot[i+64].color)){
                    //至右下
                    System.out.println(dot[i].color+" win!!!");
                    JOptionPane.showMessageDialog(this,dot[i].color+"獲勝啦！");
                    resetGame();
                }else if(dot[i].color.equals(dot[i+14].color)&&
                    dot[i+14].color.equals(dot[i+28].color)&&
                    dot[i+28].color.equals(dot[i+42].color)&&
                    dot[i+42].color.equals(dot[i+56].color)){
                    //至左下
                    System.out.println(dot[i].color+" win!!!");
                    JOptionPane.showMessageDialog(this,dot[i].color+"獲勝啦！");
                    resetGame();
                }
            }else{
                // System.out.println("i是"+i+" 是空的");
                // break;
            }
            // System.out.println("test");
        }
    }
    public void back(){
        if(moveTime-1<0)
            return ;
        int index = moveTime-1;
        chess[index].x = 100; //讓他不顯示
        chess[index].y = 100;
        chess[index].color = "not";
        chess[index].colorInt =0;
        dot[storeXY[index]].notEmpty = false;
        dot[storeXY[index]].color = "not";
        moveTime--;
        repaint();
    }
    public void paint(Graphics g) {
        // super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,700,700);
        g.setColor(Color.YELLOW);
        //塗色 起始x,y 終止x,y
        g.fillRect(65,65,560,560);
        //視窗大小700 x 700
        // 700/14(間格要減一) = 46 --> 6...循環
        //間格給65 還剩下16 左右各留8
        final int grid = 40; //線間格
        g.setColor(Color.BLACK);
        //縱線
        for(int i=0;i<15;i++)
            g.drawLine(65+grid*i,65,65+grid*i,625);
        //橫線
        for(int i=0;i<15;i++)
            g.drawLine(65,65+grid*i,625,65+grid*i);
        //

        for(int i=0;i<225;i++){
            if(chess[i]==null){
                break;
            }
            if(!chess[i].color.equals("not")){
                if(chess[i].color.equals("BLACK")){
                    // System.out.println("black hi");
                    g.setColor(Color.BLACK);
                }
                else if(chess[i].color.equals("WHITE")){
                    g.setColor(Color.WHITE);
                }

                g.fillOval((chess[i].x*grid)-20+65,(chess[i].y*grid)-20+65,40,40);
            }
        }
        //         g.fillOval(x-20,y-20,40,40);
        //         /*
        //         #使用目前颜色填充橢圓(矩形外圍)
        //         (a,b,c,d)
        //         #a - 橢圓的左上角的 x 坐标。
        //         #b - 橢圓左上角的 y 坐标。
        //         #c width -  橢圓的宽度。
        //         #d height - 橢圓的高度。*/
        //

    }
    public static void main(String[] args) {
        new gameDataFrame();
    }
}
