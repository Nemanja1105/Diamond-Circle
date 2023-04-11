package Simulations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import Gui.GameFrame;
import Gui.GameScheme;

public class DiamondsSetter extends Thread {
	private HashMap<Integer, Object> map;
	private ArrayList<Integer> diamondsPosition = new ArrayList<>();
	private Random random = new Random();
	private GameFrame gameScheme;
	
	public DiamondsSetter(GameFrame gameScheme)
	{
		this.gameScheme=gameScheme;
	}

	@Override
	public void run() {
		while (Simulator.running) {
			
			if (Simulator.pause) {
                synchronized (Simulator.pauseLockObject) {
                    try {
                    	Simulator.pauseLockObject.wait();
                    } catch (InterruptedException ex) {
                        DiamondCircle.logger.warning(ex.getMessage());
                        
                    }
                }
            }
			
			this.removePrevDiamonds();
			int numOfDiamonds = random.nextInt(Simulator.matrixDimension - 2) + 2;
			synchronized (Simulator.map) {
				while (numOfDiamonds > 0) {
					int pos = random.nextInt(Simulator.pathFields.size());
					if (Simulator.map[pos] == null) {
						Simulator.map[pos] = 1;
						System.out
								.println("Duh je postavio dijamant na" + " poziciju:" + Simulator.pathFields.get(pos));
						diamondsPosition.add(pos);
						gameScheme.placeDiamond(Simulator.pathFields.get(pos));
						// crtanje na gui
						numOfDiamonds--;
					}

				}
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				DiamondCircle.logger.warning(e.getMessage());
			}
		}
	}

	private void removePrevDiamonds() {
		if (!this.diamondsPosition.isEmpty()) {
			for (var pos : this.diamondsPosition) {
				synchronized (Simulator.map) {
					Object fieldObject = Simulator.map[pos];
					if (fieldObject instanceof Integer) {
						Simulator.map[pos] = null;
						gameScheme.unplaceDiamond(Simulator.pathFields.get(pos));
						// brisanje sa gui
					}

				}
			}
			this.diamondsPosition.clear();
		}
	}
}
