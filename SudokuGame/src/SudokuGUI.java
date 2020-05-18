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
		
		JButton submitButton = new JButton("  �ύ  ");
		JButton restartButton = new JButton("���¿�ʼ");
		JMenuItem jmiRule = new JMenuItem("����"); 
		JMenuItem jmiMessage = new JMenuItem("��Ϣ"); 
		JMenuItem jmiTime = new JMenuItem("ʱ��");
    
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
		Font f = new Font("����",Font.BOLD,20);

		
		for ( int i = 0; i < 9; i++ ) { 
			
			for ( int j = 0; j < 9; j++ ) { 
				
				if ( puzzle[i][j] != 0 ) { 
					
					text[i][j] = new JTextField("" + puzzle[i][j]); 
					text[i][j].setHorizontalAlignment(JTextField.CENTER);
					text[i][j].setEditable(false);   	
					text[i][j].setForeground(new Color(255,255,255));					
					text[i][j].setFont(f);
					
					// ����С�Ź�����ɫ
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
								JOptionPane.showMessageDialog(null, "��ϲ�㣬��ս�ɹ�!\n�ܹ���ʱ��" 
								+ minute + "��" + second + "��",
								"���",JOptionPane.INFORMATION_MESSAGE); 
								
								Object[] options = {"��","��"};
					            int result = JOptionPane.showOptionDialog(null,"�Ƿ������ս��","��ʾ",
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
								JOptionPane.showMessageDialog(null,"���д��󣬼�������!",
										"���",JOptionPane.INFORMATION_MESSAGE); 
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
						JOptionPane.showMessageDialog(null, "����ʱ��Ϊ��" + 
						minute + "��" + second + "��",
						"ʱ��",JOptionPane.INFORMATION_MESSAGE);						
					}
				}); 
	}  
	
	//�ڿ�
	private int[][] createSpaces(int a[][]){
		
		Random r = new Random(); 
		int a1, a2, number;
		
		number = 0;
		a1 = r.nextInt(9); 
		a2 = r.nextInt(9); 
		
		Object[] choices = {"��", "��ͨ", "����"};
		String s = (String) JOptionPane.showInputDialog(null, "��ѡ���Ѷ�\n", "�Ѷ�",
                JOptionPane.PLAIN_MESSAGE, new ImageIcon("icon.png"), choices, "��");
		
		switch(s) {
		case "��":
			number = 15;
			break;
		case "��ͨ":
			number = 30;
			break;
		case "����":
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
	
	//��ȡ�ı��������
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
					JOptionPane.showMessageDialog(null,"�����а��������֣�����������"); 
					return false; 
				} 
			} 
		} 
		return true; 
		
	} 
	
	//�ж�����Ĵ��Ƿ���ȷ
	private boolean judge() {   

		int [][]answer = result;    
		
		//�ж�ÿ���Ƿ����ظ�����
		for ( int i = 0; i < 9; i++ ) { 
			if ( noRepeat(answer[i]) == false )   
				return false; 
		} 
		
		//�ж�ÿ���Ƿ����ظ�����
		for ( int j = 0; j < 9; j++ ) {      
			int[] answerOfColumn = new int[9]; 
			for( int i = 0; i < 9; i++ ) { 
				answerOfColumn[i] = answer[i][j]; 
			} 
			if ( noRepeat(answerOfColumn) == false ) 
				return false; 
		} 
		
		//�ж�ÿ���������Ƿ����ظ�����
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
	
	// �ж��ظ�
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
		JOptionPane.showMessageDialog(null, "�������ֱ�֤ÿ��ÿ�м�ÿ��С�ľŹ������������ظ�",
				"����",JOptionPane.INFORMATION_MESSAGE); 
		} 
} 

class MessageListenerClass implements ActionListener{ 
	
	public void actionPerformed(ActionEvent e){ 
		JOptionPane.showMessageDialog(null, "�ɲ����ޡ���׿��������Ȩ����\n��ϵ��ʽ��"
				+ "1354620711@qq.com\n           ��    2353820667@qq.com",
				"��Ϣ",JOptionPane.INFORMATION_MESSAGE); 
	} 
} 

class RestartListenerClass implements ActionListener{ 
	
	public void actionPerformed(ActionEvent e){ 
		
		Object[] options = {"��","��"};
        int result = JOptionPane.showOptionDialog(null,"�Ƿ����¿�ʼһ���µ���Ϸ��","��ʾ",
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
