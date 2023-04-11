# Diamond-Circle
This is a game implementation of "DiamondCircle" played on a matrix with a minimum dimension of 7x7 and a maximum of 10x10. The game can be played by a minimum of 2 players and a maximum of 4 players. The dimensions of the matrix and the number of players can be set before the start of the application.

#Game Rules
Each player has a unique name and four figures of the same color. There are three types of figures: regular, floating, and super-fast. Each figure can be red, green, blue, or yellow. Regular and floating figures move over a specified number of squares, while the super-fast figure moves twice as many squares. Regular and super-fast figures can fall into a hole, while floating figures remain above the hole. At the beginning of the game, each player is given four randomly selected figures of the same color.

In addition to the figures used by the players, there is also a "ghost" figure - it starts moving when the first player does and moves along the path "in the background," placing bonus squares - diamonds - on the path. A random number of diamonds are placed between 2 and the dimension of the matrix at random positions every 5 seconds until the end of the game. When a figure encounters a diamond, it "picks it up," and for the rest of the game, the number of squares it moves is increased by the number of diamonds it has picked up.

The order of play is determined randomly, and players take turns moving one after the other. A move is defined as moving a figure from one position to another. If the destination square is already occupied, the figure is placed on the next available square. Moving from one square to another takes one second. The way the figure moves is determined by drawing a card from a deck of 52 cards. There are regular and special cards. A regular card consists of an image and the number of squares the figure moves. A special card only has an image. When a special card is drawn, holes are created at n positions on the path. The holes are black. After a card is drawn, it is returned to the deck. If a figure is on a hole, and it is not a floating figure, it falls. When a figure falls, the player starts with a new figure. The game ends when all players run out of figures, i.e., each of the player's figures has reached the finish line (square 25 in the example) or all figures have fallen.
