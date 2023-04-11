package Figures;

public class SuperFigure extends Figure
{

	public SuperFigure()
	{
		super();
	}
	
	public SuperFigure(Color color)
	{
		super(color);
	}
	
	@Override
	public String toString()
	{
		return super.toString().replace("?", "Super");
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(this==other)
			return true;
		if(!(other instanceof SuperFigure))
			return false;
		Figure right = (Figure)other;
		return this.id==right.id;
	}
}
