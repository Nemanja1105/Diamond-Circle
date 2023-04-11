package Simulations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Gui.GameFrame;

import java.io.PrintWriter;

public class SimulationResultFileIO 
{
	private static final File FOLDERPATH=new File("./Results");
	
	public static void writeSimulationResult(ArrayList<Player> players)throws IOException
	{
		if(!FOLDERPATH.exists())
			FOLDERPATH.mkdir();
		File path=new File(FOLDERPATH.getPath()+File.separator+getFileName());
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path)));
		for(var player:players)
			writer.println(player);
		writer.println("Ukupno vrijeme trajanja igre:"+Simulator.timer.getFormattedTime());
		writer.flush();
		writer.close();
	}
	
	public static File[] getFilesFromResultDirectory()
	{
		if(!FOLDERPATH.exists())
			return null;
		return FOLDERPATH.listFiles();
	}
	
	private static String getFileName()
	{
		String pattern = "dd-MM-YYYY-HH-mm-ss";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(new Date())+".txt";
	}

}
