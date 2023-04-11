package Exceptions;

public class ConfigFileCorruptedException extends Exception
{
	private static final String DEFAULTMESSAGE="Konfig fajl nije u ispravnom formatu!!";
	
	public ConfigFileCorruptedException()
	{
		super(DEFAULTMESSAGE);
	}
	
	public ConfigFileCorruptedException(String message)
	{
		super(message);
	}
}
