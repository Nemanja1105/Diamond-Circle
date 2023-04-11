package Simulations;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Exceptions.IncompatibeFigureColorException;
import Figures.*;

public class Player 
{
	private static int globalId=1;
	private String name;
	private Color color;
	private ArrayList<Figure> figures=new ArrayList<>();
	
	private LinkedList<Figure> activeFigures=new LinkedList<>();
	private Figure currentFigure;
	
	private static final String figuresArrayIsFullException="Igrac moze da ima najvise 4 figure";
	
	public Player()
	{
		this.name=this.generateName();
		this.color=null;
		this.currentFigure=null;
	}
	
	public Player(Color color)
	{
		this.name=this.generateName();
		this.color=color;
		this.currentFigure=null;
	}
	
	//funkcija za generisanje imena igraca
	private String generateName()
	{
		return "Igrac"+globalId++;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public ArrayList<Figure> getPlayerFigures()
	{
		return this.figures;
	}
	
	//metoda vraca figuricu sa kojom igrac trenutno igra
	//ako je pocetak igre setujemo prvu figuricu u nizu
	public Figure getCurrentFigure()
	{
		if(this.currentFigure==null)
			this.currentFigure=this.activeFigures.peekFirst();
		return this.currentFigure;
	}
	
	
	public ArrayList<Figure> getFigures()
	{
		return this.figures;
	}
	
	public List<Figure> geActiveFigures()
	{
		return this.activeFigures;
	}
	//kada figura propadne kroz rupu
	//poziva se ova metoda koja setuje novu figuru za kretanje
	//ako je igrac ostao bez figura trenutna figura se setuje na null
	//ili situacija u kojoj je figura stigla do cilja
	public void destroyCurrentFigure()
	{
		this.activeFigures.poll();
		if(!this.activeFigures.isEmpty())
			this.currentFigure=this.activeFigures.peekFirst();
		else
			this.currentFigure=null;
	}
	
	//metoda provjerava da li je igrac zavrsio igru
	//tj. da li ima jos figura za igru
	public boolean isFinished()
	{
		return this.activeFigures.isEmpty();
	}
	
	public void addFigures(Figure figure)throws IncompatibeFigureColorException,IndexOutOfBoundsException
	{
		if(this.color!=figure.getColor())
			throw new IncompatibeFigureColorException();
		if(this.figures.size()==Simulator.FIGURES_PER_PLAYER)
			throw new IndexOutOfBoundsException(figuresArrayIsFullException);
		this.figures.add(figure);
		this.activeFigures.offer(figure);
	}
	
	//metoda restartuje igreceve figure
	//u novoj partiji se generisu nove figure
	public void resetPlayerFigures()
	{
		this.currentFigure=null;
		this.figures.clear();
		this.activeFigures.clear();
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(this==other)
			return true;
		if(!(other instanceof Player))
			return false;
		Player right=(Player)other;
		return this.name.equals(right);
	}
	
	@Override
	public String toString()
	{
		StringBuilder builder=new StringBuilder();
		builder.append(this.name+":\n");
		for(var figure:this.figures)
		{
			builder.append("\t"+figure.toString());
			builder.append(" Vrijeme kretanja:"+Timer.getFormattedTime(figure.getMovementTime()));
			builder.append("\n");
		}
		return builder.toString();
	}
	
	@Override
	public int hashCode()
	{
		return this.name.hashCode();
	}

}
