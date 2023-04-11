package Figures;

public class BasicFigure extends Figure
{
	public BasicFigure()
	{
		super();
	}
	
	public BasicFigure(Color color)
	{
		super(color);
	}
	
	@Override
	public String toString()
	{
		return super.toString().replace("?", "Obicna");
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(this==other)
			return true;
		if(!(other instanceof BasicFigure))
			return false;
		Figure right = (Figure)other;
		return this.id==right.id;
	}

}
