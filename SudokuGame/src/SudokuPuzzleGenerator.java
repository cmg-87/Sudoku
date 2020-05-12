import java.util.Random; 

public class SudokuPuzzleGenerator {
	
	private static SudokuPuzzleGenerator singletonInstance;
	
	private Random random = new Random(); 
	   
	private static final int MAX_TIMES = 100; 
	   
	private int currentTimes = 0; 
	
	private SudokuPuzzleGenerator() {}
	
	// 单例
	public static SudokuPuzzleGenerator getSingletonInstance() {
		if(singletonInstance == null) {
			 singletonInstance = new SudokuPuzzleGenerator();
		}
		return singletonInstance;
	}
	 
	// 随机产生一个含1到9不重复的9个数字的数组 
	private int[] buildRandomArray() { 
		
		int[] array = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }; 
		int randomInt = 0;  
		
		for (int i = 0; i < 20; i++) { 
			randomInt = random.nextInt(8) + 1; 
			int temp = array[0]; 
			array[0] = array[randomInt]; 
			array[randomInt] = temp; 
		} 
		
		currentTimes++; 
		
		return array; 
	} 
	 
	// 产生9*9的数独矩阵
	public int[][] generatePuzzleMatrix() { 
	  
		int[][] randomMatrix = new int[9][9]; 
		  
		for (int row = 0; row < 9; row++) { 
			if (row == 0) { 
				currentTimes = 0; 
				randomMatrix[row] = buildRandomArray(); 			  
			} else { 
				
				int[] tempRandomArray = buildRandomArray(); 
					
				for (int col = 0; col < 9; col++) { 
					
					if (currentTimes < MAX_TIMES) { 
						if (!isValid(randomMatrix, tempRandomArray, row, col)) { 
							for (int j = 0; j < 9; j++)  
								randomMatrix[row][j] = 0;  
							row -= 1; 
							break;
						} 
					} else {  								
							for (int i = 1; i < 9; i++) { 
								for (int j = 0; j < 9; j++)  
									randomMatrix[i][j] = 0;  
							}
							row = 0; 
							currentTimes = 0; 
							break;
						} 
					
				} 
			} 
		} 
		return randomMatrix; 		
	} 
	
	// 检查矩阵中某行某列的一个数字是否符合数独条件
	private boolean isValid(int[][] matrix, int[] array, int row, int col) { 
		for (int i = 0; i < 9; i++) { 
			matrix[row][col] = array[i]; 
			if (isValidInRow(matrix, row, col)
					&&isValidInColumn(matrix, row, col) 
					&& isValidInBlock(matrix, row, col)) { 
				return true; 
			} 
		} 
		return false; 
	} 
	  
	// 检查是否满足行条件
	private boolean isValidInRow(int[][] matrix, int row, int col) {	
		 
		int currentValue = matrix[row][col]; 
	  
		for (int colNum = 0; colNum < col; colNum++) { 
			if (currentValue == matrix[row][colNum])  
				return false; 
		} 	  
		return true; 
	} 
	  
	// 检查是否满足列条件
	private boolean isValidInColumn(int[][] matrix, int row, int col) {
		 
		int currentValue = matrix[row][col]; 
	  
		for (int rowNum = 0; rowNum < row; rowNum++) { 
			if (currentValue == matrix[rowNum][col]) 
				return false; 
		} 	  
		return true; 
	} 
	  
	// 检查是否满足宫格条件
	private boolean isValidInBlock(int[][] matrix, int row, int col) { 
	  
		int baseRow = row / 3 * 3; 
		int baseCol = col / 3 * 3; 

		for (int rowNum = 0; rowNum < 8; rowNum++) { 
			if (matrix[baseRow + rowNum / 3][baseCol + rowNum % 3] == 0)  
				continue; 
				 
			for (int colNum = rowNum + 1; colNum < 9; colNum++) { 
				if (matrix[baseRow + rowNum / 3][baseCol + rowNum % 3] == 
						matrix[baseRow + colNum / 3][baseCol + colNum % 3])  
					return false; 					 
			} 
		} 
		return true;  
	}  
}
