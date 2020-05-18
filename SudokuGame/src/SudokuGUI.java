import javax.swing.*;  
import java.awt.*; 
import java.awt.event.*; 
import java.util.Random; 
import javax.swing.JOptionPane; 
  
public class SudokuGUI extends JFrame{ 
 
	private static final long serialVersionUID = -6676295371900707720L;

	private SudokuPuzzleGenerator example = SudokuPuzzleGenerator.getSingletonInstance(); 
 
	private int[][] origin = example.generatePuzzleMatrix(); 
 
	private int[][] puzzle; 
	
	private static JTextField[][] text = new JTextField[9][9]; 
	
	private static int[][] result = new int[9][9];  
	
	private long startTime = System.currentTimeMillis();
 
	public SudokuGUI(){ 
		
		JButton submitButton = new JButton("  提交  ");
		JButton restartButton = new JButton("重新开始");
		JMenuItem jmiRule = new JMenuItem("规则"); 
		JMenuItem jmiMessage = new JMenuItem("信息"); 
		JMenuItem jmiTime = new JMenuItem("时间");
    
		JPanel north = new JPanel(); 
		JPanel south = new JPanel();
		JPanel west = new JPanel();
		JPanel east = new JPanel();
		JPanel panel = new JPanel(new GridLayout(9, 9, 5, 5)); 
		

		south.add(submitButton); 
		south.add(restartButton); 
		north.add(jmiRule); 
		north.add(jmiMessage);
		north.add(jmiTime);
		 
		add(north,BorderLayout.NORTH); 
		add(panel,BorderLayout.CENTER); 
		
		
		add(south,BorderLayout.SOUTH);
		add(west,BorderLayout.WEST);
		add(east,BorderLayout.EAST);
		
		puzzle = createSpaces(origin);
		Font f = new Font("宋体",Font.BOLD,20);

		
		for ( int i = 0; i < 9; i++ ) { 
			
			for ( int j = 0; j < 9; j++ ) { 
				
				if ( puzzle[i][j] != 0 ) { 
					
					text[i][j] = new JTextField("" + puzzle[i][j]); 
					text[i][j].setHorizontalAlignment(JTextField.CENTER);
					text[i][j].setEditable(false);   	
					text[i][j].setForeground(new Color(255,255,255));					
					text[i][j].setFont(f);
					
					// 设置小九宫格颜色
					if ( i / 3 % 2 == 0 ) {
						if ( j /3 % 2 == 0 )
							text[i][j].setBackground(new Color(0,0,255));
						else
							text[i][j].setBackground(new Color(255,0,0));
					} else {
						if ( j /3 % 2 == 0 )
							text[i][j].setBackground(new Color(255,0,0));
						else
							text[i][j].setBackground(new Color(0,0,255));
					}
											
					panel.add(text[i][j]);  
					
				} else { 
					
					text[i][j] = new JTextField();  
					text[i][j].setHorizontalAlignment(JTextField.CENTER); 
					text[i][j].setFont(f);
					panel.add(text[i][j]); 
					
				} 
			} 
		} 
		add(panel); 
		
		submitButton.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) { 
						
						if ( getText() == true ) {	
							
							if ( judge() == true ) { 
								
								int totalSecond = (int) (System.currentTimeMillis() - startTime) / 1000;
								int minute = totalSecond / 60;
								int second = totalSecond % 60;								
								JOptionPane.showMessageDialog(null, "恭喜你，挑战成功!\n总共用时：" 
								+ minute + "分" + second + "秒",
								"结果",JOptionPane.INFORMATION_MESSAGE); 
								
								Object[] options = {"是","否"};
					            int result = JOptionPane.showOptionDialog(null,"是否继续挑战？","提示",
					                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
					                    null,options,options[0]);
					            
					            if (result == 0) {
					            	JFrame frame = new SudokuGUI(); 
					        		frame.setTitle("SuDoku"); 
					        		frame.setSize(600,650); 
					        		frame.setLocationRelativeTo(null);  
					        		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
					        		frame.setVisible(true); 
					        		
					            } else {
					            	System.exit(0);
					            }
					            
							} else { 
								JOptionPane.showMessageDialog(null,"仍有错误，继续加油!",
										"结果",JOptionPane.INFORMATION_MESSAGE); 
							}
							
						} 
					} 
				}); 
		
		ExplainListenerClass listener1 = new ExplainListenerClass(); 
		jmiRule.addActionListener(listener1); 
		MessageListenerClass listener2 = new MessageListenerClass(); 
		jmiMessage.addActionListener(listener2); 
		RestartListenerClass listener3 = new RestartListenerClass(); 
		restartButton.addActionListener(listener3);
		
		jmiTime.addActionListener(				
				new ActionListener(){					
					public void actionPerformed(ActionEvent e) {
						int totalSecond = (int) (System.currentTimeMillis() - startTime) / 1000;
						int minute = totalSecond / 60;
						int second = totalSecond % 60;
						JOptionPane.showMessageDialog(null, "已用时间为：" + 
						minute + "分" + second + "秒",
						"时间",JOptionPane.INFORMATION_MESSAGE);						
					}
				}); 
	}  
	
	//挖空
	private int[][] createSpaces(int a[][]){
		
		Random r = new Random(); 
		int a1, a2, number;
		
		number = 0;
		a1 = r.nextInt(9); 
		a2 = r.nextInt(9); 
		
		Object[] choices = {"简单", "普通", "困难"};
		String s = (String) JOptionPane.showInputDialog(null, "请选择难度\n", "难度",
                JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), choices, "简单");
		
		switch(s) {
		case "简单":
			number = 15;
			break;
		case "普通":
			number = 30;
			break;
		case "困难":
			number = 50;
			break;
		}
		
		for ( int i = 0; i < number; i++ ) { 
			a[a1][a2] = 0; 
			a1 = r.nextInt(9); 
			a2 = r.nextInt(9); 
		} 
		
		return a; 
	} 
	
	//获取文本框的文字
	private boolean getText() { 
		
		for ( int i = 0 ; i < 9; i++ ) { 
			for ( int j = 0; j < 9 ; j++ ) 
				result[i][j] = 0; 
		} 
		
		for ( int k = 0; k < 9; k++ ) { 
			for ( int n = 0; n < 9; n++ ) { 
				try {    
					result[k][n] = Integer.parseInt(text[k][n].getText());  
				} catch(NumberFormatException nfe) { 
					JOptionPane.showMessageDialog(null,"数据中包括非数字，请重新输入"); 
					return false; 
				} 
			} 
		} 
		return true; 
		
	} 
	
	//判断输入的答案是否正确
	private boolean judge() {   

		int [][]answer = result;    
		
		//判断每列是否有重复数字
		for ( int i = 0; i < 9; i++ ) { 
			if ( noRepeat(answer[i]) == false )   
				return false; 
		} 
		
		//判断每行是否有重复数字
		for ( int j = 0; j < 9; j++ ) {      
			int[] answerOfColumn = new int[9]; 
			for( int i = 0; i < 9; i++ ) { 
				answerOfColumn[i] = answer[i][j]; 
			} 
			if ( noRepeat(answerOfColumn) == false ) 
				return false; 
		} 
		
		//判断每个宫格内是否有重复数字
		for ( int i = 0; i < 3; i++ ) {   
			for ( int  j = 0; j < 3; j++ ) {
				
				int k = 0; 
				int[] answerOfBlock = new int[9]; 
				
				for ( int m = i * 3; m < i * 3 + 3; m++ ) { 
					for ( int n = j * 3; n < j * 3 + 3; n++ ) { 
						answerOfBlock[k] = answer[m][n]; 
						k++; 
					} 
				} 
				
				if( noRepeat(answerOfBlock) == false )  
					return false;   
				
			} 
		} 
		
		return true; 			
	} 	  
	
	// 判断重复
	private static boolean noRepeat(int[] answer) { 
		for( int i = 0; i < 9; i++ ) { 
			for( int j = 0; j < 9; j ++ ) { 
				if( i == j ) 
					continue; 
				if( answer[i] == answer[j] )  
					return false; 
			} 
		} 
		return true;  
	} 
} 

class ExplainListenerClass implements ActionListener{ 
	
	public void actionPerformed(ActionEvent e){ 
		JOptionPane.showMessageDialog(null, "填入数字保证每行每列及每个小的九宫格内数字无重复",
				"规则",JOptionPane.INFORMATION_MESSAGE); 
		} 
} 

class MessageListenerClass implements ActionListener{ 
	
	public void actionPerformed(ActionEvent e){ 
		JOptionPane.showMessageDialog(null, "由曹鸣皋、杨卓制作，版权所有\n联系方式："
				+ "1354620711@qq.com\n           或    2353820667@qq.com",
				"信息",JOptionPane.INFORMATION_MESSAGE); 
	} 
} 

class RestartListenerClass implements ActionListener{ 
	
	public void actionPerformed(ActionEvent e){ 
		
		Object[] options = {"是","否"};
        int result = JOptionPane.showOptionDialog(null,"是否重新开始一局新的游戏？","提示",
                JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,
                null,options,options[0]);
        
        if (result == 0) { 
        	JFrame frame = new SudokuGUI(); 
    		frame.setTitle("SuDoku"); 
    		frame.setSize(600,650); 
    		frame.setLocationRelativeTo(null);  
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    		frame.setVisible(true);     		
        } else 
        	return;
        
	} 
} 
