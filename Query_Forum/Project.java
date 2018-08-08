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
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//import ppp.MyConnection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import java.awt.Color;

public class Project extends JDialog implements ActionListener {
	private JTextField u;
	private JPasswordField p;
	static String user;
	JButton submit;
	JButton forgot;
	JComboBox authch;
	//JFrame frame;

	public Project() {
		// TODO Auto-generated constructor stub
		
		//frame = new JFrame("Forum");
		getContentPane().setLayout(null);
		
		
		JLabel lblRollNo = new JLabel("ID");
		lblRollNo.setForeground(Color.WHITE);
		lblRollNo.setBackground(Color.WHITE);
		lblRollNo.setBounds(137, 143, 67, 26);
		getContentPane().add(lblRollNo);	
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(137, 192, 67, 26);
		getContentPane().add(lblPassword);
		
		u = new JTextField();
		u.setBounds(248, 146, 168, 23);
		getContentPane().add(u);
		u.setColumns(10);
		
		p = new JPasswordField();
		p.setColumns(10);
		p.setBounds(248, 195, 168, 23);
		getContentPane().add(p);
		
		submit = new JButton("Sign In");
		submit.setBounds(137, 262, 89, 23);
		getContentPane().add(submit);
		
		forgot = new JButton("Cancel");
		forgot.setBounds(327, 262, 89, 23);
		getContentPane().add(forgot);
		
		authch = new JComboBox();
		authch.setModel(new DefaultComboBoxModel(new String[] {"---Set Authorization---", "Faculty", "Student"}));
		authch.setBounds(137, 59, 141, 20);
		getContentPane().add(authch);
		setSize(635, 500);
		//frame.setSize(frame.getMaximumSize());
		setModal(true);
		
		ImageIcon ii = new ImageIcon(getClass().getResource("Images/images.jpg"));// image run Homepage.java
		JLabel img = new JLabel(ii);
		img.setBounds(0, 0, 619, 461);
		getContentPane().add(img);
		
		setLocationRelativeTo(null);
		
		//frame.setVisible(true);
		setTitle("Forum");
		getRootPane().setDefaultButton(submit);
		submit.addActionListener(this);
		forgot.addActionListener(this);
		setVisible(true);		
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new Project();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object ob = e.getSource();
		user = u.getText();
		String auth = authch.getSelectedItem().toString();
		if (ob == submit && auth.equalsIgnoreCase("Student")) {
			String pass = new String(p.getPassword());
			String s = "select * from student where id=? and password=?";
			Connection c = MyConnection.connect();
			try {
				PreparedStatement ps = c.prepareStatement(s);
				ps.setString(1, user);
				ps.setString(2, pass);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					JOptionPane.showMessageDialog(null, "Ok");
					dispose();
					new Homestud(Integer.parseInt(user));
				} else {
					JOptionPane.showMessageDialog(null, "Invalid ID or Password");
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (ob == submit && auth.equalsIgnoreCase("Faculty")) {
			String pass = new String(p.getPassword());
			String s = "select * from teacher where id=? and password=?";
			Connection c = MyConnection.connect();
			try {
				PreparedStatement ps = c.prepareStatement(s);
				ps.setString(1, user);
				ps.setString(2, pass);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					JOptionPane.showMessageDialog(null, "Ok");
					dispose();
					new Homefac(Integer.parseInt(user));
					
				} else {
					JOptionPane.showMessageDialog(null, "Invalid Username or Password");
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (ob == submit && auth.equalsIgnoreCase("---Set Authorization---")){
			JOptionPane.showMessageDialog(null, "Please Select Authorty Type First");
		} else if (ob == forgot){
			dispose();
		}

	}
}
