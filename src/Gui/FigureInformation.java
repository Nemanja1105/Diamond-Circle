package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Figures.BasicFigure;
import Figures.Figure;
import Figures.SuperFigure;
import Simulations.Simulator;

import javax.swing.JLabel;
import java.awt.Font;

public class FigureInformation extends JFrame {

	private JPanel contentPane;
	private GameScheme panel;
	private Figure figure;

	/**
	 * Launch the application.
	 */
	
	/**
	 * Create the frame.
	 */
	public FigureInformation(Figure figure) {
		setResizable(false);
		this.figure=figure;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 554, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(204, 255, 204));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		panel = new GameScheme();
		panel.setBounds(98, 125, 326, 230);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("INFORMACIJE O FIGURI:");
		lblNewLabel.setForeground(new Color(102, 0, 153));
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setBounds(139, 10, 202, 25);
		contentPane.add(lblNewLabel);
		
		JLabel figureIdLbl = new JLabel("1");
		figureIdLbl.setForeground(new Color(102, 0, 153));
		figureIdLbl.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 20));
		figureIdLbl.setBounds(340, 10, 54, 25);
		contentPane.add(figureIdLbl);
		
		JLabel tip = new JLabel("Tip:");
		tip.setForeground(new Color(102, 0, 153));
		tip.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		tip.setBounds(10, 36, 46, 14);
		contentPane.add(tip);
		
		JLabel lblNewLabel_1_1 = new JLabel("Igrac:");
		lblNewLabel_1_1.setForeground(new Color(102, 0, 153));
		lblNewLabel_1_1.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1_1.setBounds(10, 51, 46, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Dostigla cilj:");
		lblNewLabel_1_1_1.setForeground(new Color(102, 0, 153));
		lblNewLabel_1_1_1.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1_1_1.setBounds(10, 69, 86, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel tipLbl = new JLabel("Obicna");
		tipLbl.setForeground(new Color(102, 0, 153));
		tipLbl.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		tipLbl.setBounds(44, 35, 84, 16);
		contentPane.add(tipLbl);
		
		JLabel igracLbl = new JLabel("Igrac1");
		igracLbl.setForeground(new Color(102, 0, 153));
		igracLbl.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		igracLbl.setBounds(54, 51, 46, 14);
		contentPane.add(igracLbl);
		
		JLabel finishedLbl = new JLabel("Igrac:");
		finishedLbl.setForeground(new Color(102, 0, 153));
		finishedLbl.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		finishedLbl.setBounds(98, 69, 46, 14);
		contentPane.add(finishedLbl);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Predjena putanja:");
		lblNewLabel_1_1_2.setForeground(new Color(102, 0, 153));
		lblNewLabel_1_1_2.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_1_1_2.setBounds(10, 107, 118, 14);
		contentPane.add(lblNewLabel_1_1_2);
		
		if(figure instanceof BasicFigure)
			tipLbl.setText("Obicna");
		else if(figure instanceof SuperFigure)
			tipLbl.setText("Super");
		else 
			tipLbl.setText("Lebdeca");
		
		figureIdLbl.setText(Integer.toString(figure.getId()));
		igracLbl.setText(Simulator.figures.get(figure).getName());
		finishedLbl.setText((figure.isReachedToGoal())?"da":"ne");
		
		for(var path:figure.getTraveledPath())
			panel.unplaceWithColor(path, figure.getColor());
			
	}

}
