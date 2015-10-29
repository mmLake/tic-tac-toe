import java.util.Scanner;

public class ticTacToe
{
    public static char PLAYER_ONE = 'X';
    public static char PLAYER_TWO = 'O';
    public static char BLANK = '.';
    public static char[][] board = new char[3][3];
    public static Scanner kb = new Scanner(System.in);
    public static boolean person;

    public static void clearBoard()
    {
	for (int row = 0; row < 3; row++)
	{
	    for (int col = 0; col < 3; col++)
	    {
		board[row][col] = BLANK;
	    }
	}
	
    }

    public static char otherPlayer(char player)
    {
	if (player == PLAYER_ONE)
	{
	    return PLAYER_TWO;
	}
	else 
	{
	    return PLAYER_ONE;
	}
    }

    public static void makeMove(int row, int col, char player)
    { 
        board[row][col] = player;
    }

    public static boolean isLegalMove(int row, int col)
    {
	if (row < 3 && row >= 0 && col < 3 && col >= 0)
	    return (board[row][col] == BLANK);
	else
	    return false;
    }

    public static boolean stalemate()
    {
	if (victory(PLAYER_ONE) || victory(PLAYER_TWO))
	    {
	    return false;
	    }
	for (int row = 0; row < 3; row++)
	    {
	    for (int col = 0; col < 3; col++)
		{
		if (board[row][col] == BLANK)
		    return false;
		}
	    }
	return true;   
    }

    public static boolean victory(char player)
    {
	for (int row = 0; row < 3; row++)
	{
            boolean won = true;            
	    for (int col = 0; col < 3; col++)
	    {
		if (board[row][col] != player)
	        {
		    won = false;
		    break;
    		}
	    }
	    if (won) return true;
	}   
	for (int col = 0; col < 3; col++)
        {
            boolean won = true;
            for (int row = 0; row < 3; row++)
            {
                if (board[row][col] != player)
                {
                    won = false;
                    break;
                }
            }
            if (won) return true; 
        }  
   	if (board[0][0] == player && board[1][1] == player && 
	board[2][2] == player)
	    return true;
    	if (board[0][2] == player && board[1][1] == player && 
	board[2][0] == player)
	    return true;
    	return false;
    }

    public static void printBoard()
    {
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                System.out.printf("%4c", board[row][col]);
            }
	    System.out.println();
        }
    }

    public static int value(char player)
    {
	int best = -1;
	char other = otherPlayer(player);
	if (victory(player))
	{
	    return 1;
	}
	else if (victory(other))
	{
	    return -1;
	}
	else if (stalemate())
	{
	    return 0;
	}

	for (int row = 0; row < 3; row++)
	{
	    for (int col = 0; col < 3; col++)
	    {
		if (isLegalMove(row, col))
		{
		    makeMove(row, col, player);
		    int newValue = -value(other);
		    board[row][col] = BLANK;
		    if (newValue > best)
		    {
			best = newValue;
		    }
		}
	    }
	}
	return best;
    }

    public static int[] chooseMove(char player)
    {
	char other = otherPlayer(player);
	int best = -1;
	int[] result = new int[2];
	for (int row = 0; row < 3; row++)
	{
	    for (int col = 0; col < 3; col++)
	    {
		if (isLegalMove(row, col))
		{
		    makeMove(row, col, player);
		    int current = -value(other);
		    board[row][col] = BLANK;
		    if (current > best)
		    {
			result[0] = row;
			result[1] = col;
			best = current;
		    }
		}
	    }
	}
	return result;
    }

    public static boolean executeMove(char player, boolean person)
    {
	int row;
	int col;
	if (person)
	{
	    do
	    {
	    printBoard();
	    System.out.print("Enter a position [row][col]. First enter " 
				+ "[row]: ");
	    row = kb.nextInt();
	    System.out.print("Now enter [col]: ");
	    col = kb.nextInt();
	
	    } while (!isLegalMove(row, col));
	}
	else 
	{
	    int[] results = chooseMove(player);
	    row = results[0];
	    col = results[1]; 
	}
	makeMove(row, col, player);
	if (victory(player))
	{
	   System.out.print("Congradulations, you won!");
	   return true;
	}
	if (stalemate())
	{
	   System.out.print("It's a tie");
	   return true;
	}
	return false;
    }

    public static void main(String[] args)
    {
	System.out.print("Enter # of players(0,1, or 2): ");
	int numOfPlayers = kb.nextInt();


	while (0 > numOfPlayers || numOfPlayers > 2)
	{
	    System.out.print("Invalid # of players. Enter 0, 1, or 2: ");
	    numOfPlayers = kb.nextInt();
	}

	clearBoard();
	char player = PLAYER_ONE;

	boolean gameover = false;
	
	    switch (numOfPlayers)
	    {
		case 0: 
		person = false;
		break;
		case 2:
		person = true;
		break;
		case 1:
		if (player == PLAYER_ONE)
		{
		    person = true;
		}
		break;
	    }
	while(!gameover) {    
	    gameover = executeMove(player, person);
	    player = otherPlayer(player);
	}
    }
}

