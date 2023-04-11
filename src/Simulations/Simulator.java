package Simulations;

import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import Exceptions.ConfigFileCorruptedException;
import Exceptions.IncompatibeFigureColorException;
import Figures.BasicFigure;
import Figures.Color;
import Figures.Figure;
import Figures.FloatingFigure;
import Figures.SuperFigure;
import Gui.GameFrame;
import Cards.*;

public class Simulator implements Runnable {
	// polja koja vaze za sve igre
	public static int matrixDimension;
	private static int numOfPlayers;
	public static ArrayList<Integer> pathFields;
	private static int numOfGamesPlayed = 0;
	private static int numOfHoles;
	private static ArrayList<Player> players = new ArrayList<>();
	private static LinkedList<Card> cards = new LinkedList<>();

	// polja o stanju igre
	public static boolean running = false;
	public static boolean pause = false;
	public static Object pauseLockObject = new Object();
	public static Timer timer;

	// konstante za simulaciju
	public static final int FIGURES_PER_PLAYER = 4;

	private static final String GAME_RESULT_IOEXCEPTION_MESSAGE = "Greska prilikom cuvanja rezultata igre!!";

	// polja instance za svaku simulaciju
	public static HashMap<Figure, Player> figures = new HashMap<>();// mozda staviti hashmapu zbog gui
	private static GameFrame gameFrame;
	public static Object[] map;

	// generisemo za sve igrace nove figure
	// mijesamo redoslijed igraca
	// mijesamo karte
	// inicijalizujemo mapu tako da su sva polja prazna
	public Simulator() {
		Collections.shuffle(Simulator.players);
		Collections.shuffle(Simulator.cards);
		this.initializeMap();
		this.generateFigures();
		initializePlayerLabels();
		// this.gameFrame=gameFrame;

	}

	// pocetna inicijalizacija mape
	private void initializeMap() {
		Simulator.map = new Object[Simulator.pathFields.size()];
	}

	private static void initializePlayerLabels() {
		gameFrame.panel_2.removeAll();
		for (var player : players)
			gameFrame.addPlayer(player);
	}

	// (0-49)Basic,(50-99)Floating,(100-149)Super
	private void generateFigures() {
		Simulator.figures.clear();
		Figure.resetGlobalId();
		Random random = new Random();
		for (var player : Simulator.players) {
			int i = 1;
			player.getFigures().clear();
			player.geActiveFigures().clear();
			while (i <= Simulator.FIGURES_PER_PLAYER) {
				int num = random.nextInt(150);
				Figure figure = null;
				if (num >= 0 && num <= 49)
					figure = new BasicFigure(player.getColor());
				else if (num >= 50 && num <= 99)
					figure = new FloatingFigure(player.getColor());
				else
					figure = new SuperFigure(player.getColor());
				figures.put(figure, player);
				try {
					player.addFigures(figure);
				} catch (IncompatibeFigureColorException e) {
					DiamondCircle.logger.warning(e.getMessage());
				}
				i++;
			}
		}

	}

	public static ArrayList<Player> getPlayers() {
		return Simulator.players;
	}

	// funkcija koja postavlja osnovne parametre simulatora
	// koji su zajednicki za sve igre
	public static GameFrame setupSimulator(int matrixDimension, int numOfPlayers)
			throws IOException, ConfigFileCorruptedException {
		Simulator.matrixDimension = matrixDimension;
		Simulator.gameFrame = gameFrame;
		Simulator.numOfPlayers = numOfPlayers;
		Simulator.numOfGamesPlayed = 0;
		SimulatorConfigReader reader = new SimulatorConfigReader(matrixDimension);
		reader.getConfigProperties();
		Simulator.gameFrame = new GameFrame();

		Simulator.initializePlayers();
		Simulator.initializeCard();
		initializePlayerLabels();
		return Simulator.gameFrame;
	}

	// paketski pristup
	static void setNumOfHoles(int value) {
		Simulator.numOfHoles = value;
	}

	static void setPathFields(ArrayList<Integer> value) {
		Simulator.pathFields = value;
	}

	// metoda koja vrsi pocetnu inicijalizaciju igraca
	private static void initializePlayers() {
		List<Color> colors = new ArrayList<>(Arrays.asList(Color.CRVENA, Color.PLAVA, Color.ZELENA, Color.ZUTA));
		Collections.shuffle(colors);
		int n = 0;
		for (int i = 0; i < Simulator.numOfPlayers; i++)
			Simulator.players.add(new Player(colors.get(n++)));
	}

	// funkcija za inicijalizaciju karata
	private static void initializeCard() {
		int value = 1;
		/*
		 * for (int i = 1; i <= 40; i++) { if (i % 10 == 0) value++;
		 * Simulator.cards.add(new Card(value)); }
		 */
		for (int i = 1; i <= 10; i++)
			Simulator.cards.add(new Card(1));
		for (int i = 1; i <= 10; i++)
			Simulator.cards.add(new Card(2));
		for (int i = 1; i <= 10; i++)
			Simulator.cards.add(new Card(3));
		for (int i = 1; i <= 10; i++)
			Simulator.cards.add(new Card(4));
		for (int i = 1; i <= 12; i++)
			Simulator.cards.add(new SpecialCard());
	}

	// funkcija vraca true ako je igra gotova
	public boolean checkGameEnd() {
		for (var player : players)
			if (!player.isFinished())
				return false;
		return true;
	}

	public void pauseSimulator() {
		Simulator.pause = true;
	}

	public void unpaseSimulator() {
		synchronized (Simulator.pauseLockObject) {
			Simulator.pause = false;
			pauseLockObject.notifyAll();
		}
	}

	@Override
	public void run() {
		DiamondsSetter casper = new DiamondsSetter(gameFrame);
		this.timer = new Timer(gameFrame);
		Simulator.running = true;
		casper.start();
		timer.start();
		// mozda sleep na sekund da postavi pocetne dijamante

		// definisan je redoslijed igraca figurice dodjeljene karte izmjesane
		while (!this.checkGameEnd()) {
			for (var player : Simulator.players) {
				if (!player.isFinished()) {
					Figure currentFigure = player.getCurrentFigure();

					if (currentFigure.getCurrentPosition() == 0) {
						// postavljamo figuru na pocetak i provjeravamo da li je duh
						// slucajno da tu poziciju postavio dijamant
						currentFigure.setStartTime(timer.getTimeLong());
						// System.out.println(timer.getTime());
						if (Simulator.map[0] instanceof Integer)
							currentFigure.addDiamond();
						Simulator.map[0] = currentFigure;
						currentFigure.addFieldToTraveledPath(Simulator.pathFields.get(0));
						System.out.println("Figura:" + currentFigure.getId() + " se nalazi na poziciji 4");
						gameFrame.placeFigure(currentFigure);
					}
					Card card = Simulator.cards.pollFirst();
					gameFrame.placeCard(card);
					if (card.getClass() == Card.class) {
						System.out.println("Izvucena je karta:" + card.getValue());
						gameFrame.setBasicCardDesc(player.getName(), "Figura" + currentFigure.getId(), card.getValue());
						// if (currentFigure.getCurrentPosition() == 0)
						// poseban slucaj kada se ne pomijeri sa prvog u vise partija
						// currentFigure.addFieldToTraveledPath(Simulator.pathFields.get(0));
						this.figureMove(player, card.getValue() + currentFigure.getDiamonds());
						currentFigure.resetDiamonds();
					} else {
						this.holeGenerate(player);
					}
					Simulator.cards.offer(card);

				}
			}
		}
		Simulator.running = false;
		Simulator.numOfGamesPlayed++;
		this.gameFrame.updateNumOfGames(Integer.toString(Simulator.numOfGamesPlayed));
		try {
			SimulationResultFileIO.writeSimulationResult(players);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(new JFrame(), GAME_RESULT_IOEXCEPTION_MESSAGE, "Greska",
					JOptionPane.ERROR_MESSAGE);
			DiamondCircle.logger.warning(GAME_RESULT_IOEXCEPTION_MESSAGE + ":" + e.getMessage());
		}
		System.out.println("Kraj igre!!");
	}

	private void figureMove(Player player, int n) {

		Figure figure = player.getCurrentFigure();
		if (figure instanceof SuperFigure)
			n *= 2;

		for (int i = 1; i <= n; i++) {
			if (pause) {
				synchronized (pauseLockObject) {
					try {
						pauseLockObject.wait();
					} catch (InterruptedException ex) {
						DiamondCircle.logger.warning(ex.getMessage());
					}
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				DiamondCircle.logger.warning(e.getMessage());
			}
			// pomjeramo se na sljedece slobodno polje
			// ogranicavamo se da ne odemo do kraja
			// pomjeramo se za 1 u svakom slucaju
			synchronized (Simulator.map) {
				int pos = figure.getCurrentPosition();
				int first = pos;
				Simulator.map[pos] = null;
				gameFrame.unplaceFigure(figure);
				pos++;
				while (pos < Simulator.pathFields.size() - 1 && Simulator.map[pos] instanceof Figure) {
					figure.addFieldToTraveledPath(Simulator.pathFields.get(pos));
					gameFrame.setFigureOldNewPosition(Simulator.pathFields.get(first), Simulator.pathFields.get(pos));
					first=pos;
					pos++;
					i++;
					if (i != n) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							DiamondCircle.logger.warning(e.getMessage());
						}
					}
				}
				if (Simulator.map[pos] instanceof Integer) {
					// figure.addDiamond();
					n++;
					Simulator.map[pos] = null;
					System.out.println("Figura:" + figure.getId() + " je pokupila dijamant!!");
					this.gameFrame.unplaceDiamond(Simulator.pathFields.get(pos));
					// brisanje dijamanta sa gui
				}
				// postavljanje na poziciju i azuriranje gui
				Simulator.map[pos] = figure;
				figure.addFieldToTraveledPath(Simulator.pathFields.get(pos));
				figure.setCurrentPosition(pos);
				System.out.println("Figura:" + figure.getId() + "tip" + figure.getClass().getName()
						+ " se nalazi na poziciji " + Simulator.pathFields.get(pos));
				gameFrame.placeFigure(figure);
				gameFrame.setFigureOldNewPosition(Simulator.pathFields.get(first), Simulator.pathFields.get(pos));

				if (pos == Simulator.pathFields.size() - 1) {
					// mozda neki sleep i obavjestenje da je stigla na cilj
					System.out.println("Figura:" + figure.getId() + " je zavrsila igra(" + pos + ")");
					// System.out.println(timer.getTime());
					figure.setEndTime(timer.getTimeLong());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						DiamondCircle.logger.warning(e.getMessage());
					}
					Simulator.map[pos] = null;
					gameFrame.unplaceFigure(figure);
					figure.ReachedToGoal();
					player.destroyCurrentFigure();// uzima se sljedeca figura
					return;
				}

			}
		}

		/*
		 * if (figure.getDiamonds() != 0) { int newMove = figure.getDiamonds();
		 * figure.resetDiamonds(); this.figureMove(player, newMove); }
		 */

	}

	// sinhronizacija
	public void holeGenerate(Player p) {

		Random random = new Random();
		HashSet<Integer> generatedHole = new HashSet<>();
		// System.out.println(Simulator.numOfHoles);
		int n = random.nextInt(Simulator.numOfHoles) + 1;
		gameFrame.setSpecCardDesc(p.getName(), n);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			DiamondCircle.logger.warning(e.getMessage());
		}
		synchronized (Simulator.map) {

			while (n > 0) {
				int pos = random.nextInt(Simulator.pathFields.size());
				if (!generatedHole.contains(pos) && pos != 0) {
					System.out.println("Rupa je na poziciji:" + Simulator.pathFields.get(pos));
					gameFrame.placeHole(Simulator.pathFields.get(pos));
					generatedHole.add(pos);
					// crtaj na gui gupu
					if (Simulator.map[pos] instanceof Figure) {
						Figure temp = (Figure) Simulator.map[pos];
						if (!(temp instanceof FloatingFigure)) {
							this.figures.get(temp).destroyCurrentFigure();
							System.out.println("Rupa je progutala figuru:" + temp.getId());
							// System.out.println(timer.getTime());
							temp.setEndTime(timer.getTimeLong());
							gameFrame.unplaceFigure(temp);
							Simulator.map[pos] = null;
						}
					}
					n--;
				}

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				DiamondCircle.logger.warning(e.getMessage());
			}
			for (var pos : generatedHole)
				if (Simulator.map[pos] instanceof FloatingFigure) {
					FloatingFigure figure = (FloatingFigure) Simulator.map[pos];
					gameFrame.unplaceWithColor(Simulator.pathFields.get(pos), figure.getColor());
				} else {
					gameFrame.unplace(Simulator.pathFields.get(pos));
				}

		}

	}

}
