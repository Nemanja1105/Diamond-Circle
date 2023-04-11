package Simulations;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import Gui.GameFrame;

public class Timer extends Thread
{
	private GameFrame gameFrame;
	//private LocalTime time;
	private long time;
	private static DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("mm:ss");
	
	public Timer(GameFrame gameFrame)
	{
		this.gameFrame=gameFrame;
		//this.time=LocalTime.MIN;
		this.time=0;
	}
	
	public LocalTime getTime()
	{
		//return this.time;
		return LocalTime.ofSecondOfDay(time);
	}
	
	public long getTimeLong()
	{
		return time;
	}
	
	public String getFormattedTime()
	{
		return this.getTime().format(dateTimeFormatter);
	}
	
	public static String getFormattedTime(LocalTime local)
	{
		return local.format(dateTimeFormatter);
	}
	
	@Override
	public void run()
	{
		//time=LocalTime.MIN;
		//DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("mm:ss");
		while(Simulator.running)
		{
			
			if (Simulator.pause) {
                synchronized (Simulator.pauseLockObject) {
                    try {
                    	Simulator.pauseLockObject.wait();
                    } catch (InterruptedException ex) {
                    	DiamondCircle.logger.warning(ex.getMessage());
                    }
                }
            }
			//String timeString=time.format(dateTimeFormatter);
			String timeString = this.getFormattedTime(this.getTime());
			gameFrame.printTime(timeString);
			//System.out.println(timeString);
			//time=time.pl
			time++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				DiamondCircle.logger.warning(e.getMessage());
			}
		}
	}

}
