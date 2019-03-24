package project02;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class GameOfLife
{

    private boolean[][] board;
    private int currentGeneration;

    private double x;
    private double y;

    public static final int NUM_OF_CELLS = 70;

    private static final double WIDTH_OF_BOARD = 800;
    private static final double HEIGHT_OF_BOARD = 800;

    public GameOfLife(double x, double y)
    {
	this.x = x;
	this.y = y;

	board = new boolean[NUM_OF_CELLS][NUM_OF_CELLS];
    }

    /**
     * Creates a new Game of Life with given board. <br>
     * <br>
     * If the board doesn't have width and length 70, will not allow.<br>
     * If the board has a null value, will not allow.
     * 
     * @param x
     *            the top left x value of the board
     * @param y
     *            the top left y value of the board
     * @param board
     *            a two dimensional array containing values true and false
     *            depending on whether or not the cell is filled
     */
    public GameOfLife(double x, double y, boolean[][] board)
    {

	this.x = x;
	this.y = y;

	if (GameOfLife.validateBoard(board))
	{
	    this.board = board;
	    currentGeneration = 0;
	} else
	{
	    board = new boolean[NUM_OF_CELLS][NUM_OF_CELLS];
	}
    }

    /**
     * Draws the board.
     * 
     * @param g2
     *            Graphics2D object
     */
    public void draw(Graphics2D g2)
    {

	drawBasics(g2);

	// fill the cells where they need to be filled
	for (int i = 0; i < board.length; i++)
	{
	    for (int j = 0; j < board[0].length; j++)
	    {
		if (board[i][j])
		{
		    this.drawFillCell(g2, i, j);
		}
	    }
	}
    }

    /**
     * Draws the board and inverts the cells. <br>
     * Will invert cells between first and second point.
     * 
     * @param g2
     *            Graphics2D object
     * @param first
     *            the first point
     * @param second
     *            the second point
     */
    public void draw(Graphics2D g2, Point first, Point second)
    {
	drawBasics(g2);

	// calculate ij values
	int[] startIJ = convertPixelToIndex(new double[] { first.getX(), first.getY() });
	int[] endIJ = convertPixelToIndex(new double[] { second.getX(), second.getY() });

	// fill the cells where they need to be filled
	for (int i = 0; i < board.length; i++)
	{
	    for (int j = 0; j < board[0].length; j++)
	    {
		if (((i >= startIJ[0] && i <= endIJ[0]) || (i <= startIJ[0] && i >= endIJ[0]))
			&& ((j >= startIJ[1] && j <= endIJ[1]) || (j <= startIJ[1] && j >= endIJ[1])))
		{
		    if (!board[i][j])
		    {
			this.drawFillCell(g2, i, j);
		    }
		} else
		{
		    if (board[i][j])
		    {
			this.drawFillCell(g2, i, j);
		    }
		}
	    }
	}
    }

    /**
     * Draws the basics of the board.<br>
     * Resets the color back to black.
     * 
     * @param g2
     *            Graphics2D object
     */
    private void drawBasics(Graphics2D g2)
    {
	final double WIDTH_OF_CELL = WIDTH_OF_BOARD / NUM_OF_CELLS;
	final double HEIGHT_OF_CELL = HEIGHT_OF_BOARD / NUM_OF_CELLS;

	g2.setColor(Color.LIGHT_GRAY);

	// draw the vertical lines
	for (int i = 0; i <= NUM_OF_CELLS; i++)
	{
	    Line2D.Double line = new Line2D.Double(x + i * WIDTH_OF_CELL, y, x + i * WIDTH_OF_CELL,
		    y + HEIGHT_OF_BOARD);
	    g2.draw(line);
	}

	// draw the horizontal lines
	for (int i = 0; i <= NUM_OF_CELLS; i++)
	{
	    Line2D.Double line = new Line2D.Double(x, y + i * HEIGHT_OF_CELL, x + WIDTH_OF_BOARD,
		    y + i * HEIGHT_OF_CELL);
	    g2.draw(line);
	}

	g2.setColor(Color.BLACK);
    }

    private void drawFillCell(Graphics2D g2, int i, int j)
    {

	// fill the cell
	final double WIDTH_OF_CELL = WIDTH_OF_BOARD / NUM_OF_CELLS;
	final double HEIGHT_OF_CELL = HEIGHT_OF_BOARD / NUM_OF_CELLS;

	final double CIRCLE_SIZE = WIDTH_OF_CELL * 0.5;
	double bufferAroundCell = (WIDTH_OF_CELL - CIRCLE_SIZE) / 2;

	Ellipse2D.Double circle = new Ellipse2D.Double(x + bufferAroundCell + j * WIDTH_OF_CELL,
		y + bufferAroundCell + i * HEIGHT_OF_CELL, CIRCLE_SIZE, CIRCLE_SIZE);

	g2.fill(circle);
	g2.draw(circle);
    }

    /**
     * Adds a value from the board based on the array and the value. <br>
     * <br>
     * Array must be of length two, with index 0 containing the x value, and
     * index 1 containing the y value.<br>
     * The values are the xy pixel positions.
     * 
     * @param xyValues
     *            an array containing the xy pixel positions of a cell to fill
     * 
     */
    public void addValueToBoard(double[] xyValue)
    {
	if (this.validatePointXY(xyValue))
	{
	    board[(this.convertPixelToIndex(xyValue))[0]][(this.convertPixelToIndex(xyValue))[1]] = true;
	}
    }

    /**
     * Adds a value from the board based on the array and the value. <br>
     * <br>
     * Array must be of length two, with index 0 containing the x value, and
     * index 1 containing the y value.<br>
     * The values are the index of the board, starting at 0.
     * 
     * @param ijValues
     *            a size 2 array containing the x value in index 0, and y in
     *            index 1
     */
    public void addValueToBoard(int[] ijValues)
    {
	// check if values are valid
	if (ijValues.length == 2 && GameOfLife.validatePointIJ(ijValues))
	{
	    board[ijValues[0]][ijValues[1]] = true;
	}

    }

    /**
     * Removes a value from the board based on the array and the value. <br>
     * <br>
     * Array must be of length two, with index 0 containing the x value, and
     * index 1 containing the y value.<br>
     * The values are the xy pixel positions.
     * 
     * @param xyValues
     *            an array containing the xy pixel positions of a cell to fill
     * 
     */
    public void removeValueFromBoard(double[] xyValue)
    {
	if (this.validatePointXY(xyValue))
	{
	    board[(this.convertPixelToIndex(xyValue))[0]][(this.convertPixelToIndex(xyValue))[1]] = false;
	}
    }

    /**
     * Removes a value from the board based on the array and the value. <br>
     * <br>
     * Array must be of length two, with index 0 containing the x value, and
     * index 1 containing the y value.<br>
     * The values are the index of the board, starting at 0.
     * 
     * @param ijValues
     *            a size 2 array containing the x value in index 0, and y in
     *            index 1
     */
    public void removeValueFromBoard(int[] ijValues)
    {
	// check if values are valid
	if (ijValues.length == 2 && GameOfLife.validatePointIJ(ijValues))
	{
	    board[ijValues[0]][ijValues[1]] = false;
	}

    }

    /**
     * Sets the board to the given generation. <br>
     * <br>
     * Ex: currentGeneration = 8; plus = 2; <br>
     * new generation = 10
     * 
     * @param plus
     *            the amount of generations to go after the current one
     */
    public void skipGenerations(int plus)
    {
	// for each plus, do the generation 1 after
	for (int i = 0; i < plus; i++)
	{
	    this.doNextGeneration();
	}

    }

    /**
     * Does the next generation.<br>
     * Updates current generation.
     */
    public void doNextGeneration()
    {
	boolean[][] newBoard = new boolean[board.length][board[0].length];

	for (int i = 0; i < newBoard.length; i++)
	{
	    System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
	}

	// check all the cells
	for (int i = 0; i < board.length; i++)
	{
	    for (int j = 0; j < board[0].length; j++)
	    {
		int numOfNeighbors = this.getNumOfNeighbors(i, j);

		// if the cell is populated
		if (board[i][j])
		{
		    if (numOfNeighbors >= 4)
		    {
			// overcrowded
			newBoard[i][j] = false;
		    } else if (numOfNeighbors <= 1)
		    {
			// lonely
			newBoard[i][j] = false;
		    }
		} else // if is empty
		{
		    if (numOfNeighbors == 3)
		    {
			// fill cell
			newBoard[i][j] = true;
		    }
		}
	    }
	}

	currentGeneration++;

	for (int i = 0; i < board.length; i++)
	{
	    System.arraycopy(newBoard[i], 0, board[i], 0, newBoard[i].length);
	}

    }

    public void setGeneration(int amount)
    {
	this.currentGeneration = amount;
    }

    /**
     * Returns the number of neighbors a cell has.
     * 
     * @param i
     *            the row coordinate of the cell
     * @param j
     *            the column coordinate of the cell
     * @return the number of neighbors
     */
    private int getNumOfNeighbors(int i, int j)
    {
	int numOfNeighbors = 0;

	int currentRow = i - 1;
	// if the row above the cell is in bounds
	if (currentRow >= 0)
	{
	    // check the cells up there
	    for (int a = 0; a < 3; a++)
	    {
		int nextYValue = j + a - 1;

		// if neighboring cell is in bounds and is filled
		if (nextYValue >= 0 && nextYValue < board.length && board[currentRow][nextYValue])
		{
		    numOfNeighbors++;
		}
	    }
	}
	currentRow = i + 1;
	// check if the row below the cell is in bounds
	if (currentRow < board.length)
	{
	    // check the cells up there
	    for (int a = 0; a < 3; a++)
	    {
		int nextYValue = j + a - 1;

		// if neighboring cell is in bounds and is filled
		if (nextYValue >= 0 && nextYValue < board[0].length && board[currentRow][nextYValue])
		{
		    numOfNeighbors++;
		}
	    }
	}

	// check the cell to the left
	if (j - 1 >= 0 && board[i][j - 1])
	{
	    numOfNeighbors++;
	}
	// check the cell to the right
	if (j + 1 < board[0].length && board[i][j + 1])
	{
	    numOfNeighbors++;
	}
	return numOfNeighbors;
    }

    /**
     * Returns the current generation.
     * 
     * @return the current generation
     */
    public int getCurrentGeneration()
    {
	return currentGeneration;
    }

    /**
     * Returns the width of the board.
     * 
     * @return the width of the board
     */
    public static double getWidthOfBoard()
    {
	return WIDTH_OF_BOARD;
    }

    /**
     * Returns the height of the board.
     * 
     * @return the height of the board
     */
    public static double getHeightOfBoard()
    {
	return HEIGHT_OF_BOARD;
    }

    public boolean getValueOfCell(int i, int j)
    {
	if (GameOfLife.validatePointIJ(new int[] { i, j }))
	{
	    return board[i][j];
	}
	return false;
    }

    public int[] convertPixelToIndex(double[] xyValue)
    {
	if (xyValue.length == 2)
	{
	    // if the two values are in bounds
	    if (this.validatePointXY(xyValue))
	    {
		final double WIDTH_OF_CELL = WIDTH_OF_BOARD / NUM_OF_CELLS;
		final double HEIGHT_OF_CELL = HEIGHT_OF_BOARD / NUM_OF_CELLS;

		// calculate the row and column num and put it in the board
		int i = (int) ((xyValue[1] - y) / HEIGHT_OF_CELL);
		int j = (int) ((xyValue[0] - x) / WIDTH_OF_CELL);

		return new int[] { i, j };
	    }
	}

	return new int[] {};
    }

    public boolean validatePointXY(double[] xyValue)
    {
	if (xyValue[0] >= this.x && xyValue[0] <= WIDTH_OF_BOARD + this.x && xyValue[1] >= this.y
		&& xyValue[1] <= HEIGHT_OF_BOARD + y)
	{
	    return true;
	}
	return false;
    }

    private static boolean validatePointIJ(int[] ijValue)
    {
	if (ijValue[0] >= 0 && ijValue[0] < NUM_OF_CELLS && ijValue[1] >= 0 && ijValue[1] < NUM_OF_CELLS)
	{
	    return true;
	}
	return false;
    }

    /**
     * Validates the board. Checks that the board is a NUM_OF_CELLS by
     * NUM_OF_CELLS size.
     * 
     * @param board
     *            the board to check
     * @return true if the board is valid; false otherwise
     */
    private static boolean validateBoard(boolean[][] board)
    {
	if (board.length == NUM_OF_CELLS && board[0].length == NUM_OF_CELLS)
	{
	    return true;
	}

	return false;
    }
}
