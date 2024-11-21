package chatting.application;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

 public class Server  implements ActionListener {
    JTextField text;
    JPanel a1;
    static Box vertical=Box.createVerticalBox();
    static JFrame f=new JFrame();
    static DataOutputStream dout;
    
    Server(){
        f.setLayout(null);
       //idu green color panel ge 
        JPanel p1 =new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
         f.add(p1);
        //idu back arrow icon ge
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2=i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back=new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        //idu click adaga work agoke
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        //idu profile pic
        ImageIcon i4=new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5=i4.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile=new JLabel(i6);
        profile.setBounds(40, 20, 25, 25);
        p1.add(profile);
        
        //idu video icon ge
        ImageIcon v1=new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image v2=v1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon v3=new ImageIcon(v2);
        JLabel video=new JLabel(v3);
        video.setBounds(300, 20, 25, 25);
        p1.add(video);
        
        //idu phone icon
        ImageIcon c1 =new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image c2=c1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon c3=new ImageIcon(c2);
        JLabel phone=new JLabel(c3);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);
        
        //idu more icon
        ImageIcon m1=new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image m2=m1.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon m3=new ImageIcon(m2);
        JLabel morevert=new JLabel(m3);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);
        
        //name bariyoke
        JLabel name=new JLabel("Harsha");
        name.setBounds(80, 10, 100, 30);
        name.setForeground(Color.white);
        name.setFont(new Font("SAN_SERIE",Font.BOLD,18));
        p1.add(name);
        
        //online
        JLabel online=new JLabel("online");
        online.setBounds(80, 30, 100, 30);
        online.setForeground(Color.white);
        online.setFont(new Font("SAN_SERIE",Font.BOLD,14));
        p1.add(online);
        
        //idu kelgade panel
        a1=new JPanel();
        a1.setBounds(5, 75,440,570);
         f.add(a1);
        
        //idu textfield ge
        text=new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN,18));
         f.add(text);
        
        //idu send button
        JButton send=new JButton("send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN,20));
         f.add(send);
        
        //idu background
         f.setSize(450,700);
         f.setLocation(200,50);
         f.setUndecorated(true);
         f.getContentPane().setBackground(Color.WHITE);
         f.setVisible(true);
    }
     public void actionPerformed(ActionEvent ae){
         
         //idu text na send madudmele torsake
         try{
        String out= text.getText();//text na store madi
       
        JPanel p2=formatLabel(out);
        
        a1.setLayout(new BorderLayout());
        
        JPanel right=new JPanel(new BorderLayout());//idu right alli torsake
        right.add(p2,BorderLayout.LINE_END);//ilige p2 panel aktidivi
        vertical.add(right);//idbandu vertical agi nxt txt takoalake
        vertical.add(Box.createVerticalStrut(12));//idu 2 text madya gap
        
        a1.add(vertical,BorderLayout.PAGE_START);//idu vertical ne a1 panel ge akki page start alli
        
        dout.writeUTF(out);//idu nan msg na using server kalsoke
        text.setText("");
        
        //iv 3 yavdo functions
         f.repaint();
         f.invalidate();
         f.validate();
         }
         catch(Exception e){
             e.printStackTrace();
         }
         
     }
     //idu txt field melgade irodune box kansotara madoke
     public static JPanel formatLabel(String out){
         JPanel panel=new JPanel();
         panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
         
         JLabel output=new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
         output.setFont(new Font("Tahoma",Font.PLAIN,16));
         output.setBackground(new Color(37,211,102));
         output.setOpaque(true);
         output.setBorder(new EmptyBorder(15,15,15,50));
         
         panel.add(output);
         
         Calendar cal=Calendar.getInstance();
         SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
         JLabel time=new JLabel();
         time.setText(sdf.format(cal.getTime()));
         panel.add(time);
         
         
         return panel;
     }
    public static void main(String[]args){
        new Server();
        
        //idu msgs na server inda input output madoke
        try{
            ServerSocket skt=new ServerSocket(6001);
            while(true){
                Socket s=skt.accept();
                DataInputStream din= new DataInputStream(s.getInputStream());
                 dout= new DataOutputStream(s.getOutputStream());
                //idu receive madoke
                while(true){
                    String msg =din.readUTF();
                    JPanel panel=formatLabel(msg);
                    JPanel left=new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
                
                    
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
