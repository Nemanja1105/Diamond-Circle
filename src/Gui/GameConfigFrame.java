package Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import Exceptions.ConfigFileCorruptedException;
import Simulations.DiamondCircle;
import Simulations.Simulator;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameConfigFrame extends JFrame {

	private JPanel contentPane;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private static final String IOEXCEPTION_MESSAGE = "Greska prilikom otvoranja konfig fajla!!";
	
	

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public GameConfigFrame() {
		setTitle("DiamondCircle");
		setBackground(new Color(102, 204, 204));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 294, 269);
		
		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			DiamondCircle.logger.warning(e.getMessage());
		}
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("DiamondCircle");
		lblNewLabel.setForeground(new Color(102, 0, 204));
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 27));
		lblNewLabel.setBounds(50, 11, 188, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Veličina mape:");
		lblNewLabel_1.setForeground(new Color(102, 0, 204));
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_1.setBounds(29, 71, 86, 39);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Podesite parametre igre");
		lblNewLabel_1_1.setForeground(new Color(102, 0, 204));
		lblNewLabel_1_1.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1_1.setBounds(10, 46, 203, 39);
		contentPane.add(lblNewLabel_1_1);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"7x7", "8x8", "9x9", "10x10"}));
		comboBox.setBounds(39, 96, 56, 22);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_1_2 = new JLabel("Broj igrača:");
		lblNewLabel_1_2.setForeground(new Color(102, 0, 204));
		lblNewLabel_1_2.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_1_2.setBounds(179, 71, 86, 39);
		contentPane.add(lblNewLabel_1_2);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4"}));
		comboBox_1.setBounds(189, 96, 56, 22);
		contentPane.add(comboBox_1);
		
		JButton btnUdjiUIgru = new JButton("Udji u igru");
		btnUdjiUIgru.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startGameBtnClicked(e);
			}
		});
		btnUdjiUIgru.setOpaque(true);
		btnUdjiUIgru.setForeground(new Color(102, 0, 153));
		btnUdjiUIgru.setFont(new Font("Calibri", Font.BOLD, 14));
		btnUdjiUIgru.setBackground(new Color(153, 204, 204));
		btnUdjiUIgru.setBounds(72, 151, 122, 43);
		contentPane.add(btnUdjiUIgru);
	}
	
	private void startGameBtnClicked(MouseEvent e)
	{
		String dimension =((String)comboBox.getSelectedItem()).split("x")[0];
		String numOfPlayer=(String)comboBox_1.getSelectedItem();
		GameFrame mainFrame=null;
		try {
			mainFrame=Simulator.setupSimulator(Integer.parseInt(dimension),Integer.parseInt(numOfPlayer));
		} 
		catch (IOException a) {
			
			JOptionPane.showMessageDialog(new JFrame(),IOEXCEPTION_MESSAGE, "Greska",
			        JOptionPane.ERROR_MESSAGE);
			DiamondCircle.logger.warning(IOEXCEPTION_MESSAGE+":"+ a.getMessage());
			System.exit(-1);
		}
		catch(ConfigFileCorruptedException r)
		{
			JOptionPane.showMessageDialog(new JFrame(),r.getMessage(), "Greska",
			        JOptionPane.ERROR_MESSAGE);
			DiamondCircle.logger.warning(r.getMessage());
			System.exit(-1);
		}
		this.setVisible(false);
		mainFrame.setVisible(true);
	}
}
