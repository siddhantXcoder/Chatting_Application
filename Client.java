package chatting.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client  implements ActionListener {
	
	JTextField text;
	static JPanel a1;
	static Box v = Box.createVerticalBox();
	static DataOutputStream dout;
	static JFrame f = new JFrame();

	
	Client(){
	f.setLayout(null);
	
	JPanel p1 = new JPanel();
	p1.setBackground(Color.BLUE);
	p1.setBounds(0,0,450,70);
	p1.setLayout(null);
	f.add(p1);
	
	ImageIcon i1 = new ImageIcon("C:\\Users\\Siddhant\\Downloads\\3.png");
	Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
	ImageIcon i3 = new ImageIcon(i2);
	JLabel back = new JLabel(i3);
	back.setBounds(5,20,25,25);
	p1.add(back);
	
	back.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent ae) {
			System.exit(0);
		}
	});
	
	ImageIcon img1 = new ImageIcon("C:\\Users\\Siddhant\\Downloads\\Screenshot_2023-07-14-10-45-36-65_99c04817c0de5652397fc8b56c3b3817.png");
	Image img2 = img1.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
	ImageIcon img3 = new ImageIcon(img2);
	JLabel profile = new JLabel(img3);
	profile.setBounds(40,10,50,50);
	p1.add(profile);
	
	ImageIcon icon1 = new ImageIcon("C:\\Users\\Siddhant\\Downloads\\phone.png");
	Image icon2 = icon1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
	ImageIcon icon3 = new ImageIcon(icon2);
	JLabel callicon = new JLabel(icon3);
	callicon.setBounds(300,20,30,30);
	p1.add(callicon);
	
	ImageIcon icon4 = new ImageIcon("C:\\Users\\Siddhant\\Downloads\\video.png");
	Image icon5 = icon4.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
	ImageIcon icon6 = new ImageIcon(icon5);
	JLabel video = new JLabel(icon6);
	video.setBounds(360,20,35,30);
	p1.add(video);
	
	ImageIcon icon7 = new ImageIcon("C:\\Users\\Siddhant\\Downloads\\3icon (1).png");
	Image icon8 = icon7.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
	ImageIcon icon9 = new ImageIcon(icon8);
	JLabel morevert = new JLabel(icon9);
	morevert.setBounds(420,20,35,30);
	p1.add(morevert);
	
	JLabel name = new JLabel("Nezuko");
	name.setBounds(110,15,100,18);
	name.setForeground(Color.WHITE);
	name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
	p1.add(name);
	
	JLabel status = new JLabel("Active");
	status.setBounds(110,35,100,18);
	status.setForeground(Color.WHITE);
	status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
	p1.add(status);
	
	a1 = new JPanel();
	a1.setBounds(5,75,440,570);
	f.add(a1);
	
    text = new JTextField();
	text.setBounds(5,655,310,40);
	text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
	f.add(text);
	
	JButton sendbtn = new JButton("Send");
	sendbtn.setBounds(320,655,123,40);
	sendbtn.setBackground(Color.BLUE);
	sendbtn.setForeground(Color.WHITE);
	sendbtn.addActionListener(this);
	sendbtn.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
	f.add(sendbtn);
	


	
	f.setSize(450,700);
	f.setLocation(800,50);
	f.setUndecorated(true);
	f.getContentPane().setBackground(Color.WHITE);
	f.setVisible(true);
}

public void actionPerformed(ActionEvent ae) {
	try {
	String out = text.getText();
	
	
	JPanel p2 = formateLabel(out);
	
	a1.setLayout(new BorderLayout());
	
	JPanel right = new JPanel(new BorderLayout());
	right.add(p2,BorderLayout.LINE_END);
	v.add(right);
	v.add(Box.createVerticalStrut(15));
	
	a1.add(v,BorderLayout.PAGE_START);
	
	
	dout.writeUTF(out);
	
	
	text.setText("");
	
	f.repaint();
	f.invalidate();
	f.validate();
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
}

public static JPanel formateLabel(String out) {
	JPanel panel = new JPanel();
	panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
	
	JLabel output = new JLabel("<html><p style =\"width:150px\">"+out+"</p></html>");
	output.setFont(new Font("Tahoma",Font.PLAIN,16));
	output.setBackground(Color.BLUE);
	output.setOpaque(true);
	output.setBorder(new EmptyBorder(15,15,15,50));
	
	panel.add(output);
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	
	
    JLabel time = new JLabel();
    time.setText(sdf.format(cal.getTime()));
    
    panel.add(time);		
	return panel;

}

public static void main(String[]args) {
	new Client();//ano obj.
	
	try {
		Socket s = new Socket("127.0.0.1",6001);
		
		DataInputStream din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());
		
		while(true) {
			a1.setLayout(new BorderLayout());
			String msg = din.readUTF();	
			JPanel panel = formateLabel(msg);
			
			JPanel left = new JPanel(new BorderLayout());
			left.add(panel,BorderLayout.LINE_START);
			v.add(left);
			
			
			v.add(Box.createVerticalStrut(15));
			a1.add(v,BorderLayout.PAGE_START);
			
			f.validate();
			
		}


	}
	catch(Exception e) {
		e.printStackTrace();
	}
	
	
}

	
}
