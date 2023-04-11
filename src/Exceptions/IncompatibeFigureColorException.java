package Exceptions;

public class IncompatibeFigureColorException extends Exception
{
	private static final String defaultMessage="Figure jednog igraca moraju biti iste boje";
	public IncompatibeFigureColorException()
	{
		super(defaultMessage);
	}
	public IncompatibeFigureColorException(String message)
	{
		super(message);
	}

}
