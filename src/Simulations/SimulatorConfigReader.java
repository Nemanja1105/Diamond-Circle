package Simulations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import Exceptions.ConfigFileCorruptedException;

public class SimulatorConfigReader 
{
	private static final String CONFIG_FILE_NAME="Config.txt";
	private int matrixDimension;
	
	public SimulatorConfigReader(int matrixDimension)
	{
		this.matrixDimension=matrixDimension;
	}
	
	//funkcija ucitava putanju na osnovu dimenzije matrice
	//koju je korisnik izabrao
	//svaka linija se sastoji od sljedeceg formata
	//dimenzija:putanja(a-b-c-d)
	public void getConfigProperties()throws IOException,ConfigFileCorruptedException
	{
		BufferedReader reader=new BufferedReader(new FileReader(CONFIG_FILE_NAME));
		String line="";
		line=reader.readLine();
		try {
			String[] args = line.split(":");
			Integer numHoles=Integer.parseInt(args[1]);
			Simulator.setNumOfHoles(numHoles);	
		} catch (IndexOutOfBoundsException|NumberFormatException e) 
		{
			DiamondCircle.logger.warning(e.getMessage());
			throw new ConfigFileCorruptedException();
		}
		
		ArrayList<Integer> path=new ArrayList<>();
		while((line=reader.readLine())!=null)
		{
			String[] args=line.split(":");
			try {
				if(Integer.parseInt(args[0])==this.matrixDimension)
				{
					String[]fields = args[1].split("-");
					for(var el:fields)
						path.add(Integer.parseInt(el));
					Simulator.setPathFields(path);
					reader.close();
					return;
				}
			} catch (IndexOutOfBoundsException|NumberFormatException e)
			{
				DiamondCircle.logger.warning(e.getMessage());
				throw new ConfigFileCorruptedException();
			}
		}
		//throw ne podrzava dimenzija matrice
		//nece nikad biti imacemo u fajlu za sve dimenzije
		
	}
}
