package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Figures.Figure;
import Simulations.DiamondCircle;
import Simulations.SimulationResultFileIO;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ResultListFrame extends JFrame {

	private JPanel contentPane;
	private JList list;
	private final static String IOEXCEPTION_ERROR_MESSAGE = "Nije moguce otvoriti datoteku!!";
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public ResultListFrame() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 338, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(204, 255, 204));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		list = new JList();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fileListSelected(e);
			}
		});
		/*list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				fileListSelected(e);
			}
		});*/
		list.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 17));
		list.setBackground(new Color(204, 255, 204));
		scrollPane.setViewportView(list);
		File[]files = SimulationResultFileIO.getFilesFromResultDirectory();
		list.setListData(files);
		
		list.setCellRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index,
                      boolean isSelected, boolean cellHasFocus) {
                 Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                 if (value instanceof File) {
                      File file = (File) value;
                      setText("Fajl:"+file.getName());
                      setHorizontalAlignment(CENTER);
                      setForeground(new Color(102, 0, 204));
                 }
                 
         
                 return c;
            }
            

       });
		
		JLabel lblNewLabel = new JLabel("Lista fajlova sa rezultatima");
		lblNewLabel.setForeground(new Color(51, 0, 153));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 20));
		lblNewLabel.setBackground(new Color(204, 255, 204));
		lblNewLabel.setOpaque(true);
		scrollPane.setColumnHeaderView(lblNewLabel);
	}
	
	private void fileListSelected(MouseEvent e )
	{
		if (list.getModel().getSize()!=0)
		{
			File file=(File)list.getSelectedValue();
		    ProcessBuilder pb = new ProcessBuilder("Notepad.exe",file.getAbsolutePath());
		    try {
		    	pb.start();
			} catch (IOException e2) {
				JOptionPane.showMessageDialog(new JFrame(), IOEXCEPTION_ERROR_MESSAGE, "Greska",
				        JOptionPane.ERROR_MESSAGE);
				DiamondCircle.logger.warning(IOEXCEPTION_ERROR_MESSAGE+e2.getMessage());
			}
			
		}
		list.clearSelection();
	}
}
