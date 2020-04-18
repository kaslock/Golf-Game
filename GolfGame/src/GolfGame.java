/*
���ϸ� : GolfGame.java
���α׷� ����: �� ������ ����� ���ϰ� 9Ȧ ���������� ������ �� �������� ����ϴ� ���α׷�
�Է�: ��ư Ŭ��
���: ���� �������� �̵��ϴ� �������� ���� �� ������
��¥: 2019�� 6�� 9��
�ۼ���: 20165549 �� �� ��
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class GolfGame extends JFrame {
	
	// ���� ���̺� ����
	String header[] = {"User", "1 (4)", "2 (4)", "3 (4)", "4 (5)", "5 (3)", "6 (4)", "7 (4)", "8 (3)", "9 (5)" , "Out"};
	String contents[][] = {
			{"A", "-", "-", "-", "-", "-", "-", "-", "-", "-", "00"},
			{"B", "-", "-", "-", "-", "-", "-", "-", "-", "-", "00"}};
	
	static int hole = 1;	// Ȧ �̵��� ���� ����
	static int user = 0;	// ���� ������ ���� ����
	static int count = 0;	// Ÿ�� ī��Ʈ�� ���� ����
	static int distance = 230;	// Ŭ���� �ִ� ��Ÿ�
	static int accuracy = 0;	// Ŭ���� ��Ȯ��
	static int par = 4;			// par ������ ���� ����
	static int endLine = 180;	// ���� ������ ���� ����
	
	String user1_grade;		// ����1�� ���
	String user2_grade;		// ����2�� ���
	int user1_score = 0;	// ����1�� �� ����
	int user2_score = 0;	// ����2�� �� ����
	int fin = 0;			// �� Ȧ�� ������ �� 
	
	static int handicap1 = 0;	// ����1�� �ڵ�ĸ
	static int handicap2 = 0;	// ����2�� �ڵ�ĸ

	JLabel golfBall = new JLabel("o");		// ������
	JLabel userColor = new JLabel(" ");		// ���� ���� ����
	JLabel greenZone = new JLabel("               G");	// �� ���� Label
	
	JTable table = new JTable(contents, header);
	
	JButton swing = new JButton("Swing");	// swing ��ư
	JButton next = new JButton("Next");		// ���� Ȧ�� �̵��ϴ� ��ư
	JButton driver = new JButton("Driver");	// ����̹� Ŭ��
	JButton iron = new JButton("Iron");		// ���̾� Ŭ��
	JButton putter = new JButton("Putter");	// ���� Ŭ��
	
	public GolfGame() {
		
		setTitle("Golf Game");	// Ÿ��Ʋ
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ������ �����ư�� Ŭ���� �� �������� �ݰ� ���� ���α׷� ����
		
		Container c = getContentPane();
		
		c.add(golfBall);
		c.add(new NorthPane(), BorderLayout.NORTH);   // ��� ����	
		c.add(new Field(), BorderLayout.CENTER); // �ߴ� ����
		c.add(new SouthPane(), BorderLayout.SOUTH);	  // �ϴ� ����
		
		golfBall.setForeground(new Color(0,50,0));	// ������ ����
		golfBall.setSize(20, 20);			// ������ ũ��
		golfBall.setLocation(210, 600);		// �ʱ� ��ġ ����
		
		swing.addMouseListener(new SwingMouseAdapter());
		next.addMouseListener(new NextMouseAdapter());
		driver.addMouseListener(new DriverMouseAdapter());
		iron.addMouseListener(new IronMouseAdapter());
		putter.addMouseListener(new PutterMouseAdapter());
		
        setSize(450,800);	// ������ ũ��
		setVisible(true);	// ������ ���
	}
	
	class SwingMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {	// ������ư Ŭ�� ��
			if (fin == 1 || hole > 9) return;
			
			int handicap, user_score;
			
			if (user == 0) handicap = handicap1;
			else handicap = handicap2;
			
			int dx = (int)(Math.random()*300);
	        int dy = (int)(Math.random()*150);
	        
	        if (golfBall.getX() > 210) dx = - dx;	// ���� x �� ��ġ�� �������� ���� x�� ��ġ ����
	        
	        int x = golfBall.getX() + dx / (accuracy + 3*handicap/2) - handicap;	// �ڵ�ĸ�� ������ ���� x ��ġ
	        int y = dx/(accuracy + 3*handicap/2) + golfBall.getY() - distance + dy / (accuracy + 2*handicap);	// �ڵ�ĸ�� ������ ���� y ��ġ
	        
	        if (golfBall.getY() < endLine) y = golfBall.getY() + distance + (int)(Math.random()*15) - 5;	// endLine �ڿ����� �ݴ������� �̵�
	        if (y < endLine - 20) y = endLine - 20;	// y�� ��ġ�� ���� ������ ����� �Ѱ輱���� �̵�
	        
	        if (x > 160 && x < 260 && y > endLine-50 && y < endLine+50) {	// �׸������� �ڵ�ĸ �߰� ����
	        	if ((handicap == 7 && dx > 90) || (handicap == 3 && dx > 270)) {	// �׸����� ���� �� A����� 70%, B����� 10% Ȯ���� ����
	        		x = 210;
	        		y = endLine;
	        	}	        
	        }
	        
        	golfBall.setLocation(x, y);		// ������ �̵�
        	count++;
        
	        String cs = count + "";
	        
	        if (x < 35) {
	        	x = 35;
	        	count++;	// �ʵ� ��Ż �� ��Ÿ 1
	        }
	        if (x > 395) {
	        	x = 395; 
	        	count++;	// �ʵ� ��Ż �� ��Ÿ 1
	        }
	        
	        if ((x > 197 && x < 223 && y < endLine + 13 && y > endLine - 13) || count >= par+2) {	// ���� ����
    		
    			userColor.setBackground(Color.BLUE);	// ���� �� ����
    			if (user == 0) user_score = user1_score = user1_score + count;	// user1 �� ���� ���
    	        else user_score = user2_score = user2_score+count;	// user2 �� ���� ���
    			
    			table.setValueAt(cs, user, hole);	// ������ ����
    			cs = user_score + "";
    			table.setValueAt(cs, user, 10);		// �� ���� ����

        		count = 0;
        		golfBall.setLocation(210, 600);	// ������
        		
        		if (user == 1) fin = 1;		// �� Ȧ ����
        		if (user == 0) user = 1;	// ���� ������ ���� ����
	        }			
		}
	}
	
	class DriverMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			distance = 230;		// ����̹��� �ִ� ��Ÿ�
			accuracy = 1;		// ����̹��� ��Ȯ��
			driver.setBackground(Color.GRAY);
			iron.setBackground(null);
			putter.setBackground(null);
		}
	}
	
	class IronMouseAdapter extends MouseAdapter {
			
		public void mousePressed(MouseEvent e) {
			distance = 150;		// ���̾��� �ִ� ��Ÿ�
			accuracy = 5;		// ���̾��� ��Ȯ��
			driver.setBackground(null);
			iron.setBackground(Color.GRAY);
			putter.setBackground(null);
		}
	}
	
	class PutterMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			distance = 25;		// ������ ��Ÿ�
			accuracy = 15;		// ������ ��Ȯ��
			driver.setBackground(null);
			iron.setBackground(null);
			putter.setBackground(Color.GRAY);
		}
	}
	
	class NextMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			if (fin == 0 && hole < 9) return; // Ȧ�� ����Ǿ��ų� ������ ������ ������������
			if (hole >= 9) {
				userColor.setBackground(Color.BLACK);	// ���� ���� �� ���������� ����
				return;
			}
			
			fin = 0;
			user = 0;
			count = 0;
			userColor.setBackground(Color.RED);	// ���� ���� ����
			hole++;
			
			if (hole == 4 || hole == 9) {	// par 5 �� ��
				par = 5;
				endLine = 80;
			}
			else if (hole == 1 || hole == 2 || hole == 3 || hole == 6 || hole == 7) { // par 4 �� ��
				par = 4;
				endLine = 180;
			}
			else if (hole == 5 || hole == 8){ // par 3 �� ��
				par = 3;
				endLine = 280;
			}
			else {}
			
			greenZone.setLocation(160, endLine - 100);	// �׸��� ��ġ ����
		}
	}
	
	class NorthPane extends JPanel {	// ������
	
		NorthPane() {
			setLayout(new GridLayout());
			JScrollPane scrollTable = new JScrollPane(table);
			scrollTable.setPreferredSize(new Dimension(100, 58));
			add(scrollTable);
			table.setFillsViewportHeight(true);
		}
	}
	
	 
	class SouthPane extends JPanel {
		
		SouthPane() {
			
			userColor.setOpaque(true);
			userColor.setBackground(Color.RED);	// ���� ���� �ʱ�ȭ
			setBackground(Color.lightGray);	// �ϴ� �г� ����
			setLayout(new BorderLayout(0, 10)); // �·� ���� �� ���� ����
			setPreferredSize(new Dimension(100, 100));
			
			JPanel wpn = new JPanel();	// west panel
			wpn.setLayout(new BorderLayout());
			wpn.add(driver, BorderLayout.NORTH);
			wpn.add(iron, BorderLayout.CENTER);
			wpn.add(putter, BorderLayout.SOUTH);
			
			JPanel cpn = new JPanel();	// center panel
			cpn.setLayout(new BorderLayout());
			cpn.add(userColor, BorderLayout.NORTH);
			cpn.add(swing, BorderLayout.CENTER);
			
			add(wpn, BorderLayout.WEST);
			add(cpn, BorderLayout.CENTER);
			add(next, BorderLayout.EAST);
		}
	}
	
	class Field extends JPanel {
		
		public Field() {
			
			setBackground(Color.WHITE);
			setLayout(null);
			add(greenZone);	// ������Ʈ �߰�
		    
			greenZone.setSize(100, 100);	// �׸��� ũ�� ����
		    greenZone.setLocation(160, endLine - 100);	// ��ġ ����
		    greenZone.setOpaque(true);
		    greenZone.setBackground(Color.GREEN);
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("User 1 : ");
		String user1_grade = scanner.next();	// ù ��° ���� ��� �Է�
		
		if (user1_grade.equals("A")) handicap1 = 8;	// ù ��° ���� �ڵ�ĸ
		else if (user1_grade.equals("B")) handicap1 = 3;
		else handicap1 = 1;
		
		System.out.print("User 2 : ");	
		String user2_grade = scanner.next();	// �� ��° ���� ��� �Է�
		
		if (user2_grade.equals("A")) handicap2 = 8;	// �� ��° ���� �ڵ�ĸ
		else if (user2_grade.equals("B")) handicap2 = 3;
		else handicap2 = 1;
		
		new GolfGame();
	}
}