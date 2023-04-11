package Simulations;
import java.io.IOException;
import java.nio.channels.IllegalBlockingModeException;

import Exceptions.ConfigFileCorruptedException;
import Exceptions.IncompatibeFigureColorException;
import Figures.BasicFigure;
import Figures.Color;
import Figures.Figure;
import Figures.FloatingFigure;
import Gui.GameConfigFrame;
import Gui.GameFrame;
import Simulations.Player;
import Simulations.SimulationResultFileIO;
import Simulations.Simulator;
import java.util.logging.*;

public class DiamondCircle {
	public static Logger logger=Logger.getLogger("DiamondCircleLogger");
	private static final String LOG_FILE = "DiamondCircle.log";
	
	static {
		FileHandler fHandler;
		try {
			fHandler=new FileHandler(LOG_FILE,true);
			logger.addHandler(fHandler);
			SimpleFormatter formatter=new SimpleFormatter();
			fHandler.setFormatter(formatter);
			logger.setUseParentHandlers(false);
		} catch (Exception e) {
			System.exit(-1);
		}
	}
	
	
	public static void main(String[] args) 
	{

		
		GameConfigFrame configFrame = new GameConfigFrame();
		configFrame.setVisible(true);

	}

}
