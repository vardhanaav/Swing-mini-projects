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

public class Homestud extends JDialog implements ActionListener{
	int id;
	JButton post, prevq;
	JLabel tname, trollno;
	TextArea textArea;
	private JLabel tbranch;
	int tidq;

	public Homestud(int id) {
		// TODO Auto-generated constructor stub
	
		getContentPane().setLayout(null);
		this.id = id;
		
		post = new JButton("Post Query");
		post.setBounds(409, 416, 126, 38);
		getContentPane().add(post);
		
		tname = new JLabel("New label");
		tname.setForeground(Color.WHITE);
		tname.setFont(new Font("Times New Roman", Font.BOLD, 13));
		tname.setBounds(155, 31, 217, 30);
		getContentPane().add(tname);
		
		trollno = new JLabel("New label");
		trollno.setForeground(Color.WHITE);
		trollno.setFont(new Font("Times New Roman", Font.BOLD, 13));
		trollno.setBounds(56, 32, 48, 29);
		getContentPane().add(trollno);
		
		textArea = new TextArea();
		textArea.setBounds(155, 136, 380, 227);
		getContentPane().add(textArea);
		
		prevq = new JButton("Show Previous Queries");
		prevq.setFont(new Font("Tahoma", Font.PLAIN, 12));
		prevq.setBounds(155, 602, 380, 38);
		getContentPane().add(prevq);
		
		tbranch = new JLabel("New label");
		tbranch.setForeground(Color.WHITE);
		tbranch.setFont(new Font("Times New Roman", Font.BOLD, 13));
		tbranch.setBounds(393, 31, 223, 30);
		getContentPane().add(tbranch);
		
		ImageIcon ii = new ImageIcon(getClass().getResource("Images/images.jpg"));// image run Homepage.java
		JLabel img = new JLabel(ii);
		img.setForeground(Color.WHITE);
		img.setBounds(0, 0, 684, 651);
		getContentPane().add(img);
		
		setTitle("Student Home");
		
		fillinfo();
		post.addActionListener(this);
		prevq.addActionListener(this);
		setSize(700, 690);
		setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		
		String q = "";
		if (ob == post){			
			q = textArea.getText();
			if (q.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please Enter a Query");
				new Homestud(id);
				dispose();
				return;
			}
			String s = "insert into queries (qid, query) values(?, ?)";
			String st = "select max(qid) from queries";
			
			Connection con = MyConnection.connect();
			try {
				PreparedStatement ps1 = con.prepareStatement(st);
				ResultSet rs = ps1.executeQuery();
				while (rs.next()) {
					tidq = rs.getInt(1);
				}
				tidq++;
				PreparedStatement ps = con.prepareStatement(s);
				ps.setInt(1, tidq);
				ps.setString(2, q);
//				ps.setString(2, "null");
				ps.executeUpdate();
				JOptionPane.showMessageDialog(null, "Query has been submitted");				
				dispose();
				new Homestud(id);
				con.close();
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (ob == prevq) {
			new ShowQ(id);
			dispose();
		}
		
	}
	
	void fillinfo(){
		String st = "select * from student where id=?";
		Connection con = MyConnection.connect();
		try {
			PreparedStatement ps = con.prepareStatement(st);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				tname.setText(rs.getString("name"));
				trollno.setText(String.valueOf(rs.getInt("id")));
				tbranch.setText(rs.getString("branch"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
