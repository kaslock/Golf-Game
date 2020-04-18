/*
파일명 : GolfGame.java
프로그램 설명: 두 선수의 등급을 정하고 9홀 골프게임을 진행한 후 점수판을 기록하는 프로그램
입력: 버튼 클릭
출력: 골인 지점으로 이동하는 골프공과 라운드 별 점수판
날짜: 2019년 6월 9일
작성자: 20165549 장 재 용
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class GolfGame extends JFrame {
	
	// 점수 테이블 구성
	String header[] = {"User", "1 (4)", "2 (4)", "3 (4)", "4 (5)", "5 (3)", "6 (4)", "7 (4)", "8 (3)", "9 (5)" , "Out"};
	String contents[][] = {
			{"A", "-", "-", "-", "-", "-", "-", "-", "-", "-", "00"},
			{"B", "-", "-", "-", "-", "-", "-", "-", "-", "-", "00"}};
	
	static int hole = 1;	// 홀 이동을 위한 변수
	static int user = 0;	// 유저 구별을 위한 변수
	static int count = 0;	// 타수 카운트를 위한 변수
	static int distance = 230;	// 클럽의 최대 비거리
	static int accuracy = 0;	// 클럽의 정확도
	static int par = 4;			// par 구별을 위한 변수
	static int endLine = 180;	// 골인 지점을 위한 변수
	
	String user1_grade;		// 유저1의 등급
	String user2_grade;		// 유저2의 등급
	int user1_score = 0;	// 유저1의 총 점수
	int user2_score = 0;	// 유저2의 총 점수
	int fin = 0;			// 한 홀이 끝났을 때 
	
	static int handicap1 = 0;	// 유저1의 핸디캡
	static int handicap2 = 0;	// 유저2의 핸디캡

	JLabel golfBall = new JLabel("o");		// 골프공
	JLabel userColor = new JLabel(" ");		// 유저 구별 색상
	JLabel greenZone = new JLabel("               G");	// 골 지점 Label
	
	JTable table = new JTable(contents, header);
	
	JButton swing = new JButton("Swing");	// swing 버튼
	JButton next = new JButton("Next");		// 다음 홀로 이동하는 버튼
	JButton driver = new JButton("Driver");	// 드라이버 클럽
	JButton iron = new JButton("Iron");		// 아이언 클럽
	JButton putter = new JButton("Putter");	// 퍼터 클럽
	
	public GolfGame() {
		
		setTitle("Golf Game");	// 타이틀
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 프레임 종료버튼이 클릭될 때 프레임을 닫고 응용 프로그램 종료
		
		Container c = getContentPane();
		
		c.add(golfBall);
		c.add(new NorthPane(), BorderLayout.NORTH);   // 상단 구성	
		c.add(new Field(), BorderLayout.CENTER); // 중단 구성
		c.add(new SouthPane(), BorderLayout.SOUTH);	  // 하단 구성
		
		golfBall.setForeground(new Color(0,50,0));	// 골프공 색상
		golfBall.setSize(20, 20);			// 골프공 크기
		golfBall.setLocation(210, 600);		// 초기 위치 설정
		
		swing.addMouseListener(new SwingMouseAdapter());
		next.addMouseListener(new NextMouseAdapter());
		driver.addMouseListener(new DriverMouseAdapter());
		iron.addMouseListener(new IronMouseAdapter());
		putter.addMouseListener(new PutterMouseAdapter());
		
        setSize(450,800);	// 프레임 크기
		setVisible(true);	// 프레임 출력
	}
	
	class SwingMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {	// 스윙버튼 클릭 시
			if (fin == 1 || hole > 9) return;
			
			int handicap, user_score;
			
			if (user == 0) handicap = handicap1;
			else handicap = handicap2;
			
			int dx = (int)(Math.random()*300);
	        int dy = (int)(Math.random()*150);
	        
	        if (golfBall.getX() > 210) dx = - dx;	// 현재 x 의 위치를 기준으로 다음 x의 위치 지정
	        
	        int x = golfBall.getX() + dx / (accuracy + 3*handicap/2) - handicap;	// 핸디캡을 적용한 다음 x 위치
	        int y = dx/(accuracy + 3*handicap/2) + golfBall.getY() - distance + dy / (accuracy + 2*handicap);	// 핸디캡을 적용한 다음 y 위치
	        
	        if (golfBall.getY() < endLine) y = golfBall.getY() + distance + (int)(Math.random()*15) - 5;	// endLine 뒤에서는 반대쪽으로 이동
	        if (y < endLine - 20) y = endLine - 20;	// y의 위치가 범위 밖으로 벗어나면 한계선으로 이동
	        
	        if (x > 160 && x < 260 && y > endLine-50 && y < endLine+50) {	// 그린존에서 핸디캡 추가 적용
	        	if ((handicap == 7 && dx > 90) || (handicap == 3 && dx > 270)) {	// 그린존에 있을 때 A등급은 70%, B등급은 10% 확률로 골인
	        		x = 210;
	        		y = endLine;
	        	}	        
	        }
	        
        	golfBall.setLocation(x, y);		// 골프공 이동
        	count++;
        
	        String cs = count + "";
	        
	        if (x < 35) {
	        	x = 35;
	        	count++;	// 필드 이탈 시 벌타 1
	        }
	        if (x > 395) {
	        	x = 395; 
	        	count++;	// 필드 이탈 시 벌타 1
	        }
	        
	        if ((x > 197 && x < 223 && y < endLine + 13 && y > endLine - 13) || count >= par+2) {	// 골인 조건
    		
    			userColor.setBackground(Color.BLUE);	// 유저 색 변경
    			if (user == 0) user_score = user1_score = user1_score + count;	// user1 총 점수 계산
    	        else user_score = user2_score = user2_score+count;	// user2 총 점수 계산
    			
    			table.setValueAt(cs, user, hole);	// 점수판 갱신
    			cs = user_score + "";
    			table.setValueAt(cs, user, 10);		// 총 점수 갱신

        		count = 0;
        		golfBall.setLocation(210, 600);	// 시작점
        		
        		if (user == 1) fin = 1;		// 한 홀 종료
        		if (user == 0) user = 1;	// 다음 유저로 게임 진행
	        }			
		}
	}
	
	class DriverMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			distance = 230;		// 드라이버의 최대 비거리
			accuracy = 1;		// 드라이버의 정확도
			driver.setBackground(Color.GRAY);
			iron.setBackground(null);
			putter.setBackground(null);
		}
	}
	
	class IronMouseAdapter extends MouseAdapter {
			
		public void mousePressed(MouseEvent e) {
			distance = 150;		// 아이언의 최대 비거리
			accuracy = 5;		// 아이언의 정확도
			driver.setBackground(null);
			iron.setBackground(Color.GRAY);
			putter.setBackground(null);
		}
	}
	
	class PutterMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			distance = 25;		// 퍼터의 비거리
			accuracy = 15;		// 퍼터의 정확도
			driver.setBackground(null);
			iron.setBackground(null);
			putter.setBackground(Color.GRAY);
		}
	}
	
	class NextMouseAdapter extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			if (fin == 0 && hole < 9) return; // 홀이 종료되었거나 게임이 끝나면 실행하지않음
			if (hole >= 9) {
				userColor.setBackground(Color.BLACK);	// 게임 종료 시 검정색으로 변경
				return;
			}
			
			fin = 0;
			user = 0;
			count = 0;
			userColor.setBackground(Color.RED);	// 유저 색상 변경
			hole++;
			
			if (hole == 4 || hole == 9) {	// par 5 일 때
				par = 5;
				endLine = 80;
			}
			else if (hole == 1 || hole == 2 || hole == 3 || hole == 6 || hole == 7) { // par 4 일 때
				par = 4;
				endLine = 180;
			}
			else if (hole == 5 || hole == 8){ // par 3 일 때
				par = 3;
				endLine = 280;
			}
			else {}
			
			greenZone.setLocation(160, endLine - 100);	// 그린존 위치 조정
		}
	}
	
	class NorthPane extends JPanel {	// 점수판
	
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
			userColor.setBackground(Color.RED);	// 유저 색상 초기화
			setBackground(Color.lightGray);	// 하단 패널 색상
			setLayout(new BorderLayout(0, 10)); // 좌로 정렬 후 간격 설정
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
			add(greenZone);	// 컴포넌트 추가
		    
			greenZone.setSize(100, 100);	// 그린존 크기 설정
		    greenZone.setLocation(160, endLine - 100);	// 위치 설정
		    greenZone.setOpaque(true);
		    greenZone.setBackground(Color.GREEN);
		}
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("User 1 : ");
		String user1_grade = scanner.next();	// 첫 번째 유저 등급 입력
		
		if (user1_grade.equals("A")) handicap1 = 8;	// 첫 번째 유저 핸디캡
		else if (user1_grade.equals("B")) handicap1 = 3;
		else handicap1 = 1;
		
		System.out.print("User 2 : ");	
		String user2_grade = scanner.next();	// 두 번째 유저 등급 입력
		
		if (user2_grade.equals("A")) handicap2 = 8;	// 두 번째 유저 핸디캡
		else if (user2_grade.equals("B")) handicap2 = 3;
		else handicap2 = 1;
		
		new GolfGame();
	}
}