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

public class Homefac extends JDialog implements ActionListener{
	int id, maxq, tidq;
	JLabel tname, trollno, tdept;
	TextArea ques;
	TextArea ans;
	JButton subans, prevq, nextq;
	ResultSet rs2;
	private JLabel tdesg;
	public Homefac(int id) {
		// TODO Auto-generated constructor stub
	
		getContentPane().setLayout(null);
		this.id = id;
		
		tname = new JLabel("New label");
		tname.setForeground(Color.WHITE);
		tname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tname.setBounds(155, 31, 126, 30);
		getContentPane().add(tname);
		
		trollno = new JLabel("New label");
		trollno.setForeground(Color.WHITE);
		trollno.setFont(new Font("Tahoma", Font.PLAIN, 15));
		trollno.setBounds(61, 32, 49, 29);
		getContentPane().add(trollno);
		
		ques = new TextArea();
		ques.setBackground(Color.WHITE);
		ques.setEditable(false);
		ques.setBounds(155, 136, 380, 130);
		getContentPane().add(ques);
		
		prevq = new JButton("Previous");
		prevq.setFont(new Font("Tahoma", Font.PLAIN, 11));
		prevq.setBounds(155, 602, 95, 38);
		getContentPane().add(prevq);
		
		ans = new TextArea();
		ans.setBounds(155, 370, 380, 130);
		getContentPane().add(ans);
		
		subans = new JButton("Submit Answer");
		subans.setBounds(409, 533, 126, 38);
		getContentPane().add(subans);
		
		tdept = new JLabel("New label");
		tdept.setForeground(Color.WHITE);
		tdept.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tdept.setBounds(334, 31, 126, 30);
		getContentPane().add(tdept);
		
		nextq = new JButton("Next");
		nextq.setFont(new Font("Tahoma", Font.PLAIN, 11));
		nextq.setBounds(440, 602, 95, 38);
		getContentPane().add(nextq);
		
		tdesg = new JLabel("New label");
		tdesg.setForeground(Color.WHITE);
		tdesg.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tdesg.setBounds(519, 31, 126, 30);
		getContentPane().add(tdesg);
		
		ImageIcon ii = new ImageIcon(getClass().getResource("Images/images.jpg"));// image run Homepage.java
		JLabel img = new JLabel(ii);
		img.setBounds(0, 0, 684, 651);
		getContentPane().add(img);
				
		setTitle("Faculty Home");
		
//		setLocationRelativeTo(null);
		
		fillinfo();
		prevq.addActionListener(this);
		nextq.addActionListener(this);
		subans.addActionListener(this);
		setSize(700, 690);
		setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		System.out.println("Question id="+tidq);
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		if (ob == subans)
		{
			String a = ans.getText();
			String rr = ques.getText() +"\n Answer: "+a;
			String s = "update queries set query=? where qid=?";
			Connection con = MyConnection.connect();
			
			try
			{
				PreparedStatement pps = con.prepareStatement(s);
				pps.setInt(2, tidq);
				pps.setString(1, rr);
//				pps.setString(2, a);
				
				JOptionPane.showMessageDialog(null, "Answer has been submitted");
//				dispose();
//				new Homefac(id);
				ques.setText(rr);
				
				ans.setText("");
				pps.executeUpdate();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		} 
		else if (ob == prevq)	{
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
//			new Homefac(id);
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
	
	void fillinfo()
	{
		String sst = "select * from teacher where id=?";
		Connection con = MyConnection.connect();
		try {
			PreparedStatement ps = con.prepareStatement(sst);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				tname.setText(rs.getString("name"));
				trollno.setText(String.valueOf(rs.getInt("id")));
				tdept.setText(rs.getString("dept"));
				tdesg.setText(rs.getString("designation"));
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
		try {
			Connection con = MyConnection.connect();
			String ss = "select * from queries where qid=?";
			PreparedStatement ps2 = con.prepareStatement(ss);
			ps2.setInt(1, tidq);
			rs2 = ps2.executeQuery();
			while (rs2.next()){
				ques.setText(rs2.getString("query"));
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main (String ar[])
	{
		new Homefac(1001);
	}
}
