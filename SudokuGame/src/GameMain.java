import javax.swing.JFrame;

public class GameMain {
	
	public static void main(String[] args) {

		JFrame frame = new SudokuGUI(); 
		frame.setTitle("SuDoku"); 
		frame.setSize(600,650); 
		frame.setLocationRelativeTo(null);  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true); 
	}

}
