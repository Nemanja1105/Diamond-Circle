package Gui;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Figures.BasicFigure;
import Figures.Figure;
import Figures.SuperFigure;
import Simulations.Simulator;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

public class GameScheme extends JPanel {

	private ArrayList<JLabel> labels=new ArrayList<>();
	
	private static final String DIAMOND_PATH="diamond.png";
	private static final String HOVERBOAD_PATH="hoverboard.png";
	private static final String ROCKET_PATH="rocket.png";
	private static final String FIGURE_PATH="chess-piece.png";
	private ImageIcon diamondIcon;
	private ImageIcon hoverboardIcon;
	private ImageIcon rocketIcon;
	private ImageIcon figureIcon;
	/**
	 * Create the panel.
	 */
	public GameScheme() {
		setBorder(new LineBorder(new Color(0, 0, 0), 2));
		int n=Simulator.matrixDimension;
		setLayout(new GridLayout(n, n, 2, 2));
		
		this.diamondIcon=new ImageIcon(DIAMOND_PATH);
		this.hoverboardIcon=new ImageIcon(HOVERBOAD_PATH);
		this.rocketIcon=new ImageIcon(ROCKET_PATH);
		this.figureIcon=new ImageIcon(FIGURE_PATH);
		
		
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
			{
				int num=i*n+j+1;
				JLabel label=new JLabel(Integer.toString(num),SwingConstants.CENTER);
				label.setHorizontalTextPosition(JLabel.LEFT);
				label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
				label.setOpaque(true);
				if(Simulator.pathFields.contains(num))
					label.setBackground(new Color(162,162,208));
				else
					label.setBackground(Color.WHITE);
				this.add(label);
				this.labels.add(label);
			}
		}
		
		
	}
	
	public void unplaceFigure(Figure figure)
	{
		JLabel label=labels.get(Simulator.pathFields.get(figure.getCurrentPosition())-1);
		label.setBackground(new Color(162,162,208));
		label.setIcon(null);	
	}
	
	public void unplace(int position)
	{
		JLabel label=labels.get(position-1);
		label.setBackground(new Color(162,162,208));
	}
	
	public void unplaceWithColor(int position,Figures.Color color)
	{
		JLabel label=labels.get(position-1);
		this.setColor(label, color);
	}
	
	
	public void placeFigure(Figure figure)
	{
		JLabel label=labels.get(Simulator.pathFields.get(figure.getCurrentPosition())-1);
		this.setColor(label, figure.getColor());
		if(figure instanceof BasicFigure)
			label.setIcon(figureIcon);
		else if(figure instanceof SuperFigure)
			label.setIcon(rocketIcon);
		else 
			label.setIcon(hoverboardIcon);
	}
	
	public void setColor(JLabel label,Figures.Color color)
	{
		if(color==Figures.Color.CRVENA)
			label.setBackground(Color.RED);
		else if(color==Figures.Color.PLAVA)
			label.setBackground(Color.BLUE);
		else if(color==Figures.Color.ZELENA)
			label.setBackground(Color.GREEN);
		else 
			label.setBackground(Color.YELLOW);
	}
	
	public void placeDiamond(int position)
	{
		JLabel label=labels.get(position-1);
		label.setIcon(diamondIcon);
	}
	
	public void unplaceDiamond(int position)
	{
		JLabel label=labels.get(position-1);
		label.setIcon(null);
	}
	
	public void placeHole(int position)
	{
		JLabel label=labels.get(position-1);
		label.setBackground(Color.BLACK);
	}

}
