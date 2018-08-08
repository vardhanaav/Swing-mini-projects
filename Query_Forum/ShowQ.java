//package ppp;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import java.awt.TextArea;
import java.awt.Font;
import java.awt.Color;

public class ShowQ extends JDialog implements ActionListener{
	int id, maxq, tidq;
	JLabel tname, trollno, tbranch;
	TextArea textArea;
	JButton prevq, nextq;
	ResultSet rs2;

	public ShowQ(int id) {
		// TODO Auto-generated constructor stub
	
		getContentPane().setLayout(null);
		this.id = id;
		
		tname = new JLabel("New label");
		tname.setForeground(Color.WHITE);
		tname.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tname.setBounds(155, 31, 305, 30);
		getContentPane().add(tname);
		
		trollno = new JLabel("New label");
		trollno.setForeground(Color.WHITE);
		trollno.setFont(new Font("Times New Roman", Font.BOLD, 15));
		trollno.setBounds(61, 32, 49, 29);
		getContentPane().add(trollno);
		
		textArea = new TextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setBounds(126, 254, 432, 240);
		getContentPane().add(textArea);
		
		prevq = new JButton("Previous");
		prevq.setFont(new Font("Tahoma", Font.PLAIN, 11));
		prevq.setBounds(155, 602, 95, 38);
		getContentPane().add(prevq);
		
		tbranch = new JLabel("New label");
		tbranch.setForeground(Color.WHITE);
		tbranch.setFont(new Font("Times New Roman", Font.BOLD, 15));
		tbranch.setBounds(155, 111, 305, 30);
		getContentPane().add(tbranch);
		
		nextq = new JButton("Next");
		nextq.setFont(new Font("Tahoma", Font.PLAIN, 11));
		nextq.setBounds(440, 602, 95, 38);
		getContentPane().add(nextq);
		
		ImageIcon ii = new ImageIcon(getClass().getResource("Images/images.jpg"));// image run Homepage.java
		JLabel img = new JLabel(ii);
		img.setBounds(0, 0, 684, 651);
		getContentPane().add(img);
		
		
		setTitle("Queries");
//		setLocationRelativeTo(null);
		
//		String s = "select * from queries";
		
		fillinfo();
		prevq.addActionListener(this);
		nextq.addActionListener(this);
		setSize(700, 690);
		setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		if (ob == prevq)	{
			try 
			{
				if(tidq>1)
				{
					rs2.previous();
					--tidq;
					showQuestion();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Cannot Move Back");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		} else if (ob == nextq)	{
				try {
					rs2.next();
					if(tidq<maxq)
					{
						++tidq;
						showQuestion();
					} else {
						JOptionPane.showMessageDialog(null, "Cannot Move Further");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
	}
	
	void fillinfo(){
		String s = "";

		if (id > 1000){
			s = "select * from teacher where id=?";
		} else {
			s = "select * from student where id=?";
		}
		Connection con = MyConnection.connect();
		try {		
			PreparedStatement ps = con.prepareStatement(s);

			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				tname.setText(rs.getString("name"));
				trollno.setText(String.valueOf(rs.getInt("id")));
				if (id < 1000){
				tbranch.setText(rs.getString("branch"));
				} else {
					tbranch.setText(rs.getString("dept"));
				}
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tidq = 1;
		
		String st = "select max(qid) from queries";
		try {
			PreparedStatement ps1 = con.prepareStatement(st);
			ResultSet rs1 = ps1.executeQuery();	
			while(rs1.next()){
				tidq = rs1.getInt(1);
			}
			maxq = tidq;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		showQuestion();
		
		
	}
	void showQuestion()
	{
		Connection con = MyConnection.connect();
		String ss = "select * from queries where qid=?";
		try {
			PreparedStatement ps2 = con.prepareStatement(ss);
			ps2.setInt(1, tidq);
			rs2 = ps2.executeQuery();
			while (rs2.next()){
				textArea.setText(rs2.getString("query"));
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
