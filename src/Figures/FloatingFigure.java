package Figures;

public class FloatingFigure extends Figure
{
	public FloatingFigure()
	{
		super();
	}
	
	public FloatingFigure(Color color)
	{
		super(color);
	}
	
	@Override
	public String toString()
	{
		return super.toString().replace("?", "Lebdeca");
	}
	
	@Override
	public boolean equals(Object other)
	{
		if(other==null)
			return false;
		if(this==other)
			return true;
		if(!(other instanceof FloatingFigure))
			return false;
		Figure right = (Figure)other;
		return this.id==right.id;
	}

}
