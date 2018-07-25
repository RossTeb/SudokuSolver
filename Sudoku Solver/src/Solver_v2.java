import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Solver_v2 {

	public static void main (String args[])
	{
		//hard coded board for #239
    	/*int[][] board = {{0,4,0,0,0,0,3,6,0},
						 {0,9,0,0,0,7,4,2,0},
						 {0,0,6,0,0,0,0,0,8},
						 {0,0,0,5,0,0,2,0,0},
						 {0,0,0,7,0,2,0,1,0},
						 {0,0,8,0,0,0,5,0,0},
						 {0,0,0,0,1,9,0,3,0},
						 {0,7,4,0,0,0,0,9,1},
					   	 {0,0,1,8,0,0,6,0,0}};
					   	 
       /* int[][] board = {{0,0,0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0,0,0},
						 {0,0,0,0,0,0,0,0,0},
					   	 {0,0,0,0,0,0,0,0,0}};*/
		
       /* int[][] board = {{0,0,8,0,0,0,6,0,0},
						 {0,4,0,0,0,0,0,3,7},
						 {0,7,0,0,0,0,0,2,5},
						 {0,0,0,0,0,2,0,0,0},
						 {3,0,9,0,0,1,7,0,0},
						 {0,0,0,6,9,0,0,0,8},
						 {5,0,0,0,0,3,0,0,0},
						 {0,0,0,0,4,7,0,0,0},
					   	 {0,8,0,0,0,0,4,0,0}};*/
	    File file = new File("board.txt");
	    System.out.println(file.getAbsolutePath());
	    Scanner s;
	    String[][] boards=new String[9][9];
		try 
		{
			s = new Scanner(file);  
			s.useDelimiter(",");
			//String[][] boards=new String[9][9];
			int row = 0;
			while(s.hasNext())
			{
				String[] numbers = s.nextLine().split(",");
				for(int col = 0; col < 9; col++) 
				{
					if(numbers[col].isEmpty())
					{
						boards[row][col]="0";
					}
					else
					{
					boards[row][col] = numbers[col];
					}
				}
				row++;
			}	
			//System.out.print(board[0][2]);
			System.out.println(boards[1][1].isEmpty());
			for(int i =0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					
					if(boards[i][j].isEmpty())
					{
						boards[i][j] = "0";
					}
						System.out.print(boards[i][j]);
				}
			}
			System.out.println("");
			s.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int[][] board = new int[9][9];
		for(int i =0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				board[i][j]= Integer.parseInt(boards[i][j]);
			}

		}
		
		int[][] copy = board;
		boolean solved = false;
		int step=0;
		while(step<300)
		{
			for(int i = 0;i<copy.length;i++)
			{
				int[] row = getRow(copy,i);
				int[] col = getCol(copy,i);
				int pos = -5; // position of a possible missing number in row, column, or nonet
				int count = 0; // count for missing numbers in row, columns, or nonets
				//check for a 8/9 solved row
				
				for(int x = 0; x<row.length;x++)
				{
					if(row[x] == 0)
					{
						count++;
						pos = x;
					}
				}
				//System.out.println(pos + "row position");
				
				if(count == 1)
				{
						if(missingOne(row)!= -1) //fancy bit of code that i found help with online in combination with java docs
						{
							//row[pos]= missingOne(row);
							copy[i][pos]=missingOne(row);
							count=0;
							//printLine(row);
							continue;
						}
					
				}
				
				//if there is not a 8/9 row then reset count and position back to 0
				else
				{
					count = 0;
					pos = -5;
				}
				
				
				
				for(int x = 0; x<col.length;x++)
				{
					if(col[x] == 0)
					{
						count++;
						pos = x;
						
					}
				}
	
				if(count == 1)
				{
						if(missingOne(col)!= -1) //fancy bit of code that i found help with online in combination with java docs
						{
							//col[pos]= missingOne(col);
							copy[pos][i]= missingOne(col);
							count=0;
							//printLine(col);
							continue;
						}
	
				}
				
				//if there is not a 8/9 column then reset count and position back to 0
				else
				{
					count = 0;
					pos = -5;
				}			

			}
			//end of loop to check 8/9 rows and cols
			//beginning of checking nonets for 8/9
			for(int i =0;i<copy.length;i++)
			{			
				int count = 0;
				int[][] net = getNonet(copy,i);
				for(int a=0;a<3;a++)
				{
					for(int b = 0; b<3;b++)
					{
						if(net[a][b]== 0)
						{
							count++;
						}
					}
				}

				//printNet(net);
				if(count == 1)
				{
					int[] xyn = missingOne(net,i);
					copy[xyn[0]][xyn[1]] = xyn[2];
					continue;
				}
			}
			for(int i = 0;i<9;i++)
			{
				//System.out.print("nonet# f$%@ing" +i);
				int[] xyn = solveNet(i,copy);
				if(xyn[2]!=0)
				{
					copy[xyn[0]][xyn[1]] = xyn[2];
				}
				
			}
			
			for(int i = 0; i<copy.length;i++)
			{
				int[] solRow = solveRow(i,copy);
				if(solRow[1]!=0)
				{
					copy[i][solRow[0]]=solRow[1];
				}
			}
			
			for(int i = 0; i<copy.length;i++)
			{
				int[] solCol = solveCol(i,copy);
				if(solCol[1]!=0)
				{
					copy[solCol[0]][i]=solCol[1];
				}
			}
			printBoard(copy);
			step++;
			//solved = true;
		}
	}
/*
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@	
 */
	
	public static int[] solveCol(int line,int[][]board)
	{
		System.out.println("start solve col");
		int[] cn={0,0};
		
		for(int a =1;a<=9;a++)
		{
			int[] col = getCol(board,line);
			printLine(col);
			if(contains(col,a)!= true)
			{
				for(int i =0;i<col.length;i++)
				{	
					if(col[i] == 0)
					{
						if(contains(getRow(board,i),a)==true)
						{
							col[i]=-1;
						}
					}
				}
				int count =0;
				int c =0;
				printLine(col);
				for(int i =0;i<col.length;i++)
				{
					if(col[i]==0)
					{
						count++;
						System.out.println("0 count is: "+count);
						c=i;
					}
				}
				if(count ==1)
				{
					System.out.println("found something!: "+ a);
					cn[0]=c;
					cn[1]=a;
					return cn;
				}
				
			}
			
		}
		System.out.println("end solve col");
		return cn;
	}
	
	public static int[] solveRow(int line,int[][]board)
	{
		System.out.println("start solve row");
		int[] cn={0,0};
		
		for(int a =1;a<=9;a++)
		{
			int[] row = getRow(board,line);
			printLine(row);
			if(contains(row,a)!= true)
			{
				for(int i =0;i<row.length;i++)
				{	
					if(row[i] == 0)
					{
						if(contains(getCol(board,i),a)==true)
						{
							row[i]=-1;
						}
					}
				}
				int count =0;
				int c =0;
				printLine(row);
				for(int i =0;i<row.length;i++)
				{
					if(row[i]==0)
					{
						count++;
						System.out.println("0 count is: "+count);
						c=i;
					}
				}
				if(count ==1)
				{
					System.out.println("found something!: "+ a);
					cn[0]=c;
					cn[1]=a;
					return cn;
				}
				
			}
			
		}
		System.out.println("end solve row");
		return cn;
	}
	
	public static int[] solveNet(int net, int[][] board)
	{
		int[] xyn = new int[3];

		//xyn[0] = netpos/3*3+i;
		//xyn[1] = netpos%3*3+j;
		for(int a = 1;a<=9;a++)
		{
			int[][] nonet = getNonet(board,net);
			//System.out.print("testing this:");
			//printNet(nonet);
			if(contains(nonet,a)!= true)
			{
				int x = 0;
				int f=net%3*3;
				for(int i = net/3*3;i<net/3*3+3;i++)
				{
					
					if(contains(getRow(board,i),a))
					{
						for(int j = 0;j<3;j++)
						{

				//			System.out.println("Row: x is:" + x + " j is: " + j);
							
							nonet[x][j]= -1;
							//x++;
						}
					//	printNet(nonet);
					}
					if(contains(getCol(board,f),a))
					{
						for(int j = 0;j<3;j++)
						{
						//	System.out.println("Col: j is:" + j + " x is: " + x);

							nonet[j][x]= -1;
							
						}
						//printNet(nonet);

					}
					x++;
					f++;
					
				}
				x+=3;
				
			}
			int count = 0;
			for(int i = 0;i<3;i++)
			{
				for(int j = 0;j<3;j++)
				{
					if(nonet[i][j] == 0)
					{
						count++;
						//System.out.println("0 count is: "+count);
					}
				}
			}
			if(count==1)
			{
				//System.out.println("here!");
				for(int i = 0;i<3;i++)
				{
					for(int j = 0;j<3;j++)
					{
						if(nonet[i][j] == 0)
						{
							nonet[i][j] = a;
							xyn[0] = net/3*3+i;
							xyn[1] = net%3*3+j;
							xyn[2] = a;
							
						}
					}
				}
			}
			printNet(nonet);
		}
		return xyn;
	}
	
	public static boolean contains(int[] line, int num)
	{
		for(int i = 0; i< line.length;i++)
		{
			if(line[i] == num)
			{
				System.out.print("line:");
				printLine(line);
				System.out.println("contains:"+ num);
				return true;
			}
		}
		return false;
	}
	
	public static boolean contains(int[][] nonet, int num)
	{
		int[] line = new int[9];
		int a = 0;
		for(int i=0;i<3;i++)
		{
			for(int j = 0; j<3;j++)
			{
				line[a]=nonet[i][j];
				a++;
			}
		}
		if(contains(line,num))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static int missingOne(int[] line)
	{
		
		int missing = -1;
		int[] counts = {1,2,3,4,5,6,7,8,9};
		for(int i = 0; i< line.length;i++)
		{
			for(int j = 1; j<=line.length;j++)
			{
				//System.out.println("i is:" + line[i] + " j is: " + j);
				if(line[i] == j)
				{
					counts[j-1]=0;
					//System.out.println("Ding!");
					break;
				}

			}
		}
		//printLine(counts);
		for(int i = 0; i<counts.length;i++)
		{
			if(counts[i]!= 0)
			{
				missing = counts[i];
				System.out.println("the line is missing " + missing);
			}
		}
		return missing;
	}
	
	public static int[] missingOne(int[][] net, int netpos)
	{
		//this stands for x =[1]=X coordinate,y=[2]=y coordinate,n= the missing number 
		int[] xyn = {0,0,0};
		int[] line = new int[9];
		int a = 0;
		printNet(net);
		for(int i=0;i<3;i++)
		{
			for(int j = 0; j<3;j++)
			{
				line[a]=net[i][j];
				if(net[i][j]==0)
				{
					xyn[0] = netpos/3*3+i;
					xyn[1] = netpos%3*3+j;
					System.out.println("x:"+ xyn[0]+" y:"+xyn[1]);
				}
				a++;
			}
		}
		//printLine(line);
		xyn[2] = missingOne(line);
		return xyn;
	}
	
	public static int[][] getNonet(int[][]board, int net)
	{
		int[][] nonet = {{0,0,0},
		                 {0,0,0},
		                 {0,0,0}};
		int x = 0;
		int y = 0;
		//System.out.println("start net");
		for(int i= net/3*3; i<net/3*3+3;i++)
		{
			y = 0;
			//System.out.println("net:"+net);
			for(int j = net%3*3; j<net%3*3+3;j++)
			{
				//System.out.println("i:"+i+" j:"+j);
				nonet[x][y] = board[i][j];
				//System.out.println("x:"+x+" y:"+y);
				y++;
			}
			x++;
		}
		//printNet(nonet);
		//System.out.println("end net");
		return nonet;
	}
	
	public static int[] getRow(int[][] board, int index)
	{
		int[] row = new int[9];
		for(int j =0; j<board[0].length;j++)
		{
			if(board[index][j] == 0)
			{
				row[j] = 0;
			}
			else
			{
				row[j] = board[index][j];				
			}
		}
		//printLine(row);
		//printLine(col);
		return row;
	}
	
	public static int[] getCol(int[][] board, int index)
	{
		int[] col = new int[9];
		for(int j =0; j<board[0].length;j++)
		{
			if(board[j][index] == 0)
			{
				col[j] = 0;
			}
			else
			{
				col[j] = board[j][index];				
			}
		}
		//printLine(row);
		//printLine(col);	
		return col;
	}
	
	public static void printBoard(int[][] board)
	{
		System.out.println("-------------");
		for(int i = 0; i<board.length;i++)
		{
			for(int j = 0; j<board[0].length;j++)
			{
				if(j%3 == 0)
				{
					System.out.print("|");
				}
				System.out.print(board[i][j]);

			}
			System.out.println("|");
			if((i+1)%3==0)
			{
				System.out.println("-------------");
			}
	
		}
		System.out.println("************************");
	}
	
	public static void printNet(int[][] net)
	{
		System.out.println("+++++");
		for(int i = 0;i<3;i++)
		{
			for(int j = 0;j<3;j++)
			{
				System.out.print(net[i][j]);
			}
			System.out.println();
		}
		//System.out.println("+++++");
	}
	
	public static void printLine(int[] line)
	{
		for(int i = 0; i<line.length;i++)
		{				
			System.out.print(line[i]);
			if((i+1)%3 == 0)
			{
				System.out.print("|");
			}
		}
		System.out.println();
		//System.out.println("*****************");
	}
}
