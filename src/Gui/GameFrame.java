package Gui;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

import javax.swing.border.LineBorder;

import Figures.Figure;
import Simulations.DiamondCircle;
import Simulations.Player;
import Simulations.Simulator;
import Simulations.Timer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.FlowLayout;
import javax.swing.event.ListSelectionListener;

import Cards.Card;

import javax.swing.event.ListSelectionEvent;

public class GameFrame extends JFrame {

	private JPanel contentPane;
	public JPanel panel_2;
	// private ArrayList<JLabel> labels=new ArrayList<>();
	private GameScheme panel;
	private Simulator currSimulator;
	private JLabel numOfGames;
	private JList list;
	private JPanel panel_3;
	private JLabel lblUno;
	private JLabel lblNewLabel_2;

	private ImageIcon uno1;
	private ImageIcon uno2;
	private ImageIcon uno3;
	private ImageIcon uno4;
	private ImageIcon unoBlocked;
	private JButton btnPrikazuListuFajlova;
	private JLabel lblNewLabel_3;
	private JPanel panel_4;
	private JLabel timeLbl;
	private JPanel panel_5;
	private JLabel lblNewLabel_4;
	private JLabel currPlayerLbl;
	private JLabel aboutLbl;
	private JLabel figurePosLbl;
	//public static Timer timer;

	public GameFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 862, 677);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			DiamondCircle.logger.warning(e.getMessage());
			// handle exception
		}
		setLocationRelativeTo(null);

		// int n=Simulator.matrixDimension;
		// JPanel panel = new JPanel();
		panel = new GameScheme();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(174, 192, 403, 313);
		contentPane.add(panel);
		//panel.setLayout(new GridLayout(7, 7, 2, 2));

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(102, 0, 204), 2));
		panel_1.setBackground(new Color(204, 255, 204));
		panel_1.setBounds(0, 0, 856, 82);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("DiamondCircle");
		lblNewLabel.setForeground(new Color(102, 0, 204));
		lblNewLabel.setBounds(304, 22, 188, 39);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 27));
		panel_1.add(lblNewLabel);

		JButton btnNewButton = new JButton("Pokreni/Zaustavi");
		btnNewButton.setForeground(new Color(102, 0, 153));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				startStopBtnAction(e);
			}
		});
		btnNewButton.setFont(new Font("Calibri", Font.BOLD, 12));
		btnNewButton.setBackground(new Color(0, 255, 102));
		btnNewButton.setBounds(699, 22, 122, 43);
		btnNewButton.setOpaque(true);
		panel_1.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("Trenutni broj odigranih partija:");
		lblNewLabel_1.setForeground(new Color(102, 0, 204));
		lblNewLabel_1.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_1.setBounds(10, 11, 171, 39);
		panel_1.add(lblNewLabel_1);

		numOfGames = new JLabel("0");
		numOfGames.setForeground(new Color(51, 0, 102));
		numOfGames.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 14));
		numOfGames.setBounds(85, 40, 46, 14);
		panel_1.add(numOfGames);

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(204, 255, 204));
		panel_2.setBounds(0, 82, 856, 43);
		contentPane.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 15));

		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listFigureChoose(e);
			}
		});
		/*
		 * list.addListSelectionListener(new ListSelectionListener() { public void
		 * valueChanged(ListSelectionEvent e) { listFigureChoose(e); } });
		 */
		list.setBorder(new LineBorder(new Color(102, 0, 153), 2));

		list.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 17));
		list.setBounds(0, 125, 129, 513);
		list.setBackground(new Color(204, 255, 204));
		list.setForeground(new Color(102, 0, 204));
		contentPane.add(list);
		// Vector<String> arrayList=new
		// Vector<String>(Arrays.asList("aaa","bbbb","cccc"));
		// list.setListData(arrayList);

		list.setCellRenderer(new DefaultListCellRenderer() {

			@Override
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (value instanceof Figure) {
					Figure figure = (Figure) value;
					setText("Figura" + figure.getId());
					setForLabelColor(c, figure.getColor());
					setHorizontalAlignment(CENTER);
				}

				return c;
			}

		});

		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(102, 0, 153), 2));
		panel_3.setBounds(625, 125, 221, 391);
		contentPane.add(panel_3);

		panel_3.setBackground(new Color(204, 255, 204));
		panel_3.setLayout(null);

		lblUno = new JLabel("");
		lblUno.setBounds(10, 32, 200, 348);
		panel_3.add(lblUno);

		this.uno1 = new ImageIcon(new ImageIcon("unoblue1.png").getImage().getScaledInstance(lblUno.getWidth(),
				lblUno.getHeight(), Image.SCALE_SMOOTH));
		this.uno2 = new ImageIcon(new ImageIcon("unoblue2.png").getImage().getScaledInstance(lblUno.getWidth(),
				lblUno.getHeight(), Image.SCALE_SMOOTH));
		this.uno3 = new ImageIcon(new ImageIcon("unoblue3.png").getImage().getScaledInstance(lblUno.getWidth(),
				lblUno.getHeight(), Image.SCALE_SMOOTH));
		this.uno4 = new ImageIcon(new ImageIcon("unoblue4.png").getImage().getScaledInstance(lblUno.getWidth(),
				lblUno.getHeight(), Image.SCALE_SMOOTH));
		this.unoBlocked = new ImageIcon(new ImageIcon("unocardblocked.png").getImage()
				.getScaledInstance(lblUno.getWidth(), lblUno.getHeight(), Image.SCALE_SMOOTH));

		lblNewLabel_2 = new JLabel("IZVUCENA KARTA");
		lblNewLabel_2.setForeground(new Color(0, 51, 204));
		lblNewLabel_2.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_2.setBounds(60, 0, 171, 39);
		panel_3.add(lblNewLabel_2);

		btnPrikazuListuFajlova = new JButton();
		btnPrikazuListuFajlova.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resultListBtnClicked(e);
			}
		});
		btnPrikazuListuFajlova.setOpaque(true);
		btnPrikazuListuFajlova.setForeground(new Color(102, 0, 153));
		btnPrikazuListuFajlova.setFont(new Font("Calibri", Font.BOLD, 12));
		btnPrikazuListuFajlova.setBackground(new Color(0, 255, 102));
		btnPrikazuListuFajlova.setBounds(692, 544, 122, 59);
		btnPrikazuListuFajlova
				.setText("<html><center>" + "Prikaz liste fajlova" + "<br>" + "sa rezultatima" + "</center></html>");
		contentPane.add(btnPrikazuListuFajlova);

		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(102, 0, 153), 2));
		panel_4.setBounds(272, 136, 206, 43);
		contentPane.add(panel_4);
		panel_4.setLayout(null);
		panel_4.setBackground(new Color(204, 255, 204));

		lblNewLabel_3 = new JLabel("Vrijeme trajanja igre:");
		lblNewLabel_3.setBounds(10, 0, 129, 39);
		panel_4.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(new Color(102, 0, 204));
		lblNewLabel_3.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));

		timeLbl = new JLabel("00:00");
		timeLbl.setForeground(new Color(102, 0, 204));
		timeLbl.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));
		timeLbl.setBounds(129, 0, 68, 39);
		panel_4.add(timeLbl);

		panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(102, 0, 153), 2));
		panel_5.setBounds(242, 527, 284, 100);
		contentPane.add(panel_5);
		panel_5.setBackground(new Color(204, 255, 204));
		panel_5.setLayout(null);
		
		lblNewLabel_4 = new JLabel("Opis znacenja karte:");
		lblNewLabel_4.setBounds(59, 11, 154, 17);
		lblNewLabel_4.setForeground(new Color(102, 0, 204));
		lblNewLabel_4.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		panel_5.add(lblNewLabel_4);
		
		currPlayerLbl = new JLabel("");
		currPlayerLbl.setForeground(new Color(102, 0, 204));
		currPlayerLbl.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));
		currPlayerLbl.setBounds(69, 39, 154, 17);
		panel_5.add(currPlayerLbl);
		currPlayerLbl.setVisible(false);
		
		aboutLbl = new JLabel("Na mapi se stvara ? rupa");
		aboutLbl.setForeground(new Color(102, 0, 204));
		aboutLbl.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));
		aboutLbl.setBounds(48, 56, 191, 14);
		panel_5.add(aboutLbl);
		
		figurePosLbl = new JLabel("Figurica se pomijera sa polja ? na polje #");
		figurePosLbl.setForeground(new Color(102, 0, 153));
		figurePosLbl.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 13));
		figurePosLbl.setBounds(24, 75, 242, 14);
		figurePosLbl.setVisible(false);
		panel_5.add(figurePosLbl);
		aboutLbl.setVisible(false);

	}

	public void unplace(int position) {
		panel.unplace(position);
	}

	public void unplaceFigure(Figure figure) {
		panel.unplaceFigure(figure);
	}

	public void placeFigure(Figure figure) {
		panel.placeFigure(figure);
	}

	public void placeHole(int position) {
		panel.placeHole(position);
	}

	public void placeDiamond(int position) {
		panel.placeDiamond(position);
	}

	public void unplaceDiamond(int position) {
		panel.unplaceDiamond(position);
	}

	public void unplaceWithColor(int position, Figures.Color color) {
		panel.unplaceWithColor(position, color);
	}

	public void startStopBtnAction(MouseEvent e) {
		if (!Simulator.running) {
			list.clearSelection();
			this.currSimulator = new Simulator();
			// DefaultListModel<Figure> model=new DefaultListModel<>();
			// for(var figure:Simulator.figures.keySet())
			// model.addElement(figure);
			this.list.setListData(Simulator.figures.keySet().toArray());
			this.list.updateUI();
			this.aboutLbl.setVisible(true);
			this.currPlayerLbl.setVisible(true);
			figurePosLbl.setVisible(true);
			Thread thread = new Thread(this.currSimulator);
			//this.timer = new Timer(this);
			thread.start();
			//timer.start();
		} else if (Simulator.running && !Simulator.pause) {
			currSimulator.pauseSimulator();
		} else {
			currSimulator.unpaseSimulator();
		}
	}

	public void updateNumOfGames(String num) {
		numOfGames.setText(num);
	}

	public void addPlayer(Player p) {
		JLabel label = new JLabel(p.getName());
		this.setForLabelColor(label, p.getColor());
		label.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 16));
		panel_2.add(label);
	}

	private void setForLabelColor(Component label, Figures.Color color) {
		if (color == Figures.Color.CRVENA)
			label.setForeground(Color.RED);
		else if (color == Figures.Color.PLAVA)
			label.setForeground(Color.BLUE);
		else if (color == Figures.Color.ZELENA)
			label.setForeground(Color.GREEN);
		else
			label.setForeground(Color.YELLOW);
	}

	private void listFigureChoose(MouseEvent e) {
		// if (!e.getValueIsAdjusting()) {
		if (list.getModel().getSize() != 0) {
			Figure figure = (Figure) list.getSelectedValue();
			FigureInformation figureInformation = new FigureInformation(figure);
			figureInformation.setVisible(true);
			figureInformation.setFocusable(true);
		}
		list.clearSelection();
	}

	public void placeCard(Card card) {
		if (card.getValue() == 0)
			lblUno.setIcon(this.unoBlocked);
		else if (card.getValue() == 1)
			lblUno.setIcon(this.uno1);
		else if (card.getValue() == 2)
			lblUno.setIcon(this.uno2);
		else if (card.getValue() == 3)
			lblUno.setIcon(this.uno3);
		else
			lblUno.setIcon(this.uno4);
	}

	private void resultListBtnClicked(MouseEvent e) {
		ResultListFrame listFrame = new ResultListFrame();
		listFrame.setVisible(true);
	}

	public void printTime(String time) {
		this.timeLbl.setText(time);
	}
	
	public void setBasicCardDesc(String name,String figure,int pos)
	{
		currPlayerLbl.setText("Na potezu je "+name);
		aboutLbl.setText(figure+" prelazi minimalno "+pos+" polja");
	}
	
	public void setSpecCardDesc(String name,int numOfHoles)
	{
		figurePosLbl.setVisible(false);
		currPlayerLbl.setText("Na potezu je "+name);
		aboutLbl.setText("Na mapi se stvara "+numOfHoles+" rupa/e");
	}
	
	public void setFigureOldNewPosition(int oldPos,int newPos)
	{
		figurePosLbl.setVisible(true);
		figurePosLbl.setText("Figurica se pomijera sa polja " +oldPos+" na polje "+newPos);
	}
}
