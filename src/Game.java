//2021-10 자바 미니게임 프로젝트

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JFrame {

	private final int FRAME_WIDTH = 500; // 프레임 너비
	private final int FRAME_HEIGHT = 1000; // 프레임 높이 1025

	private final int BUTTON_SIZE = 60; // 버튼의 너비, 높이

	boolean musicSwitch = true; // 음악 온오프 상태 변수(초기값 true)
	boolean volumeSwitch = true; // 볼륨 온오프 상태 변수(초기값 true)
	boolean homeMusicSwitch = true; // 홈화면 음악 온오프 상태 변수
	boolean homeVolumeSwitch = true; // 홈화면 음악 온오프 상태 변수

	int count = 0;// 플레이어가 키보드를 누른 횟수(키가 눌렸을때 순서의 동물이랑 일치하는지 확인할때 사용)

	int startCount = 3; // 카운트 다운 타이머에 사용될 변수
	int rest = 100; // 게임 진행 타이머에 사용될 변수

	// 사용될 각종 이미지들
	ImageIcon rank = new ImageIcon("./Image/rank.png");
	ImageIcon start = new ImageIcon("./Image/keep.png");
	ImageIcon keep = new ImageIcon("./Image/keep.png");
	ImageIcon restart = new ImageIcon("./Image/restart.png");
	ImageIcon home = new ImageIcon("./Image/home.png");
	ImageIcon help = new ImageIcon("./Image/help.png");
	ImageIcon exit = new ImageIcon("./Image/exit.png");
	ImageIcon pause = new ImageIcon("./Image/pause.png");
	ImageIcon musicOn = new ImageIcon("./Image/musicon.png");
	ImageIcon musicOff = new ImageIcon("./Image/musicoff.png");
	ImageIcon helpImg = new ImageIcon("./Image/게임화면.png");
	ImageIcon tiger = new ImageIcon("./Image/tiger.png");
	ImageIcon elephant = new ImageIcon("./Image/elephant.png");
	ImageIcon giraffe = new ImageIcon("./Image/giraffe.png");
	ImageIcon bear = new ImageIcon("./Image/bear.png");
	ImageIcon ranking = new ImageIcon("./Image/ranking.png");

	// 프레임들
	JFrame frame = new JFrame(); // 프레임
	JFrame helpFrame = new JFrame(); // 프레임
	JFrame rankFrame = new JFrame(); // 프레임

	// 패널들
	JPanel homePanel = new JPanel();// 홈화면의 패널
	JPanel gamePanel = new JPanel();// 게임이 진행중일때 화면의 패널
	HelpPanel helpPanel = new HelpPanel(); // 도움말패널
	HomeImg homeImg = new HomeImg(); // 홈 화면의 이미지를 그리는 패널
	RankPanel rankPanel = new RankPanel(); //랭킹 화면 패널
	PlayPanel playPanel;

	// 버튼들
	JButton startButton = new JButton(); // 새로운 게임 시작버튼 (home패널->game 패널)
	JButton rankButton = new JButton();// 랭킹 버튼(home 패널)
	JButton exitButton = new JButton();// 나가기 버튼(home 패널)
	JButton helpButton = new JButton();// 도움말 버튼(home 패널)
	JButton stopButton = new JButton(); // 게임 중단버튼(game 패널-타이머가 중지)
	JButton restartButton = new JButton(); // 새로운 게임 시작버튼 (game패널-stopButton이 눌리면 보여짐)
	JButton keepButton = new JButton(); // 게임을 계속 진행하는 버튼(game 패널-stopButton이 눌리면 보여짐)
	JButton homeButton = new JButton(); // homePannel로 돌아가는 버튼(game패널-stopButton이 눌리면 보여짐)
	JButton finishButton = new JButton(); // homePannel로 돌아가는 버튼(finish 패널)
	JButton regameButton = new JButton();// 게임을 다시 시작하는 버튼(finish패널)
	JButton musicButton = new JButton(); // music의 on off를 조정하는 버튼
	JButton homeMusicButton = new JButton(); // 홈화면 음악 스위치버튼
	
	// 노래객체들
	Audio homeMusic = new Audio("./Music/homeMusic.wav", true);
	Audio gameMusic = new Audio("./Music/gameMusic.wav", true);
	Audio buttonSound = new Audio("./Music/buttonSound.wav", false);
	Audio correctSound = new Audio("./Music/correct.wav", false);
	Audio errorSound = new Audio("./Music/error.wav", false);
	Audio countSound = new Audio("./Music/countSound.wav", false);

	// 리스너들과 타이머
	ButtonListener buttonListener = new ButtonListener(); // 버튼리스너 객체
	KeyBoardListener keyListener = new KeyBoardListener(); // 키보드리스너 객체
	Timer timer; // 게임시간을 체크하는 타이머
	Timer counter; // 게임시작시 카운트다운을 위한 타이머
	TimerListener timerListener;
	CountListener countListener;
	
	public static void main(String[] args) { 
		// TODO Auto-generated method stub
			new Game().start();
	}

	public void start() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 각각에 필요한 리스너 추가
		startButton.addActionListener(buttonListener);
		stopButton.addActionListener(buttonListener);
		restartButton.addActionListener(buttonListener);
		rankButton.addActionListener(buttonListener);
		helpButton.addActionListener(buttonListener);
		exitButton.addActionListener(buttonListener);
		keepButton.addActionListener(buttonListener);
		homeButton.addActionListener(buttonListener);
		musicButton.addActionListener(buttonListener);
		homeMusicButton.addActionListener(buttonListener);
		regameButton.addActionListener(buttonListener);
		finishButton.addActionListener(buttonListener);

		gamePanel.addMouseListener(new MListener());
		homePanel.addMouseListener(new MListener());
		helpPanel.addMouseListener(new MListener());
		rankPanel.addMouseListener(new MListener());

		gamePanel.addKeyListener(keyListener);
		gamePanel.setFocusable(false);// 키 안먹게(초기상태)

		// 타이머리스너 추가
		timerListener = new TimerListener();
		countListener = new CountListener();

		timer = new Timer(1000, timerListener);
		counter = new Timer(1000, countListener);


		helpPanel.setBackground(Color.white);
		gamePanel.setBackground(Color.white);
		homePanel.setBackground(Color.white);

		// home 패널 디자인
		homePanel.setLayout(null);// Layout을 없애서 자유롭게 버튼위치선정 가능

		// homePanel 버튼세팅
		buttonSetting(startButton, start, 50, 810);
		buttonSetting(rankButton, rank, 160, 810);
		buttonSetting(helpButton, help, 270, 810);
		buttonSetting(exitButton, exit, 380, 810);
		buttonSetting(homeMusicButton, musicOn, 415, 880);

		// game패널 디자인
		gamePanel.setLayout(null);

		// gamePanel 버튼세팅
		buttonSetting(stopButton, pause, 400, 20);
		buttonSetting(homeButton, home, 400, 20);
		buttonSetting(restartButton, restart, 310, 20);
		buttonSetting(keepButton, keep, 220, 20);
		buttonSetting(musicButton, musicOn, 415, 880);

		// 각각의 패널에 필요한 버튼,라벨들을 추가
		homePanel.add(startButton);
		homePanel.add(rankButton);
		homePanel.add(helpButton);
		homePanel.add(exitButton);
		homePanel.add(homeMusicButton);

		homePanel.add(homeImg);

		gamePanel.add(stopButton);
		gamePanel.add(restartButton);
		gamePanel.add(keepButton);
		gamePanel.add(homeButton);
		gamePanel.add(musicButton);
		

		// gamePanel에서 stop버튼이 눌려야지 보이는 세 버튼의 초기상태설정
		restartButton.setVisible(false);
		keepButton.setVisible(false);
		homeButton.setVisible(false);

		frame.add(homePanel);

		frame.setResizable(false);// 프레임 크기조절 가능: true 불가능 : false
		frame.setTitle("LEFT & RIGHT GAME");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		frame.setVisible(true);

		homeMusic.play(); // home패널에서 시작하니까 홈뮤직 재생

	}

	// 버튼위에 있는 이미지만 보이도록 설정하는 매서드
	public void buttonSetting(JButton bt, ImageIcon image, int x, int y) {

		Image img = image.getImage(); // ImageIcon 크기를 조절하기위해 이미지만 추출
		
		//이미지를 버튼 크기로 사이즈 조절해서 넣어줌
		Image updateImg = img.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH); 
																								
		ImageIcon updateIcon = new ImageIcon(updateImg);

		bt.setIcon(updateIcon);

		bt.setBorderPainted(false); // 버튼의 테두리를 제거
		bt.setFocusPainted(false); // 버튼 이미지의 테두리 제거

		bt.setBackground(Color.white);
		bt.setBounds(x, y, BUTTON_SIZE, BUTTON_SIZE);
	}

	public class ButtonListener implements ActionListener {
		// 각각의 버튼들이 눌렸을때에 그거에 맞는 패널로 이동하기 위한 버튼리스너
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == startButton) {

				//화면관련 코드
				homePanel.setVisible(false);
				gamePanel.setVisible(true); //한번 홈패널로 갔다가 다시 시작할때를 위해서

				playPanel = new PlayPanel();
				
				gamePanel.add(playPanel);

				playPanel.setBounds(0, 90, playPanel.getWidth(), playPanel.getHeight());
				

				frame.add(gamePanel);

				stopButton.setVisible(false);
				musicButton.setVisible(false);
				
				gamePanel.requestFocus();
				gamePanel.setFocusable(true);

				//사운드 관련 코드
				buttonSound.play();
				homeMusic.stop();
				countSound.play();
				// 321 카운터 이후에 음악재생 할거라서 카운트다운타이머만 실행

				counter.start();
			} else if (e.getSource() == stopButton) {
				// 음악에 관한 코드들
				buttonSound.play();
				gameMusic.stop();

				restartButton.setVisible(true);
				keepButton.setVisible(true);
				homeButton.setVisible(true);

				gamePanel.setFocusable(false);// 키 안먹게

				stopButton.setVisible(false);

				timer.stop();

			} else if (e.getSource() == restartButton) {
				// 음악에 관한 코드들
				buttonSound.play();
				countSound.play();

				playPanel.animalReset();
				playPanel.randomSet();
				playPanel.reset();
				playPanel.left= new ImageIcon("./Image/left1.png");
				playPanel.right= new ImageIcon("./Image/right1.png");
				playPanel.repaint();

				restartButton.setVisible(false);
				keepButton.setVisible(false);
				homeButton.setVisible(false);
				musicButton.setVisible(false);

				gamePanel.requestFocus();
				gamePanel.setFocusable(true);

				
				counter.start();

			}

			else if (e.getSource() == keepButton) {
				// 음악에 관한 코드들
				buttonSound.play();
				gameMusic.restart();

				// 타이머가 재시작되는 코드
				restartButton.setVisible(false);
				keepButton.setVisible(false);
				homeButton.setVisible(false);

				gamePanel.requestFocus();
				gamePanel.setFocusable(true);

				stopButton.setVisible(true);

				timer.restart();

			}

			else if (e.getSource() == rankButton) {

				// 음악에 관한 코드들
				buttonSound.play();

				rankFrame.setTitle("Ranking");
				rankFrame.setBackground(Color.white);
				rankFrame.setSize(400, 500);
				rankFrame.add(rankPanel);
				rankFrame.setVisible(true);

			}

			else if (e.getSource() == helpButton) {
				// 음악에 관한 코드들
				buttonSound.play();

				helpFrame.setTitle("Game Explanation");
				helpFrame.setSize(500, 800);
				helpFrame.setBackground(Color.white);

				helpFrame.add(helpPanel);
				helpFrame.setVisible(true);
			} else if (e.getSource() == exitButton) {
				// 음악에 관한 코드들
				buttonSound.play();

				System.exit(0);
			}

			else if (e.getSource() == homeButton) {
				// 음악에 관한 코드들
				buttonSound.play();
				homeMusic.play();
				
				playPanel.reset();
				playPanel.animalReset();
				playPanel.randomSet();

				restartButton.setVisible(false); // 다시 게임을 시작할때를 대비해 버튼들을 초기상태로
				keepButton.setVisible(false); // 다시 게임을 시작할때를 대비해 버튼들을 초기상태로
				homeButton.setVisible(false); // 다시 게임을 시작할때를 대비해 버튼들을 초기상태로
				stopButton.setVisible(true); // 다시 게임을 시작할때를 대비해 버튼들을 초기상태로

				gamePanel.setVisible(false);// gamePanel을 숨김

				homePanel.setVisible(true);// start버튼을 누를때 false로 바뀐 값을 다시 true로
				frame.add(homePanel);
			}

			else if (e.getSource() == musicButton) {

				buttonSound.play();

				if (musicSwitch == true) {
					gameMusic.off();// 배경음악을 끄는 코드

					buttonSetting(musicButton, musicOff, 415, 880); // 스위치 이미지를 바꾸는 코드
					musicSwitch = false;
				}

				else {
					gameMusic.on();// 배경음악을 켜는 코드
					buttonSetting(musicButton, musicOn, 415, 880); // 스위치 이미지를 바꾸는 코드
					musicSwitch = true;
				}
				//게임패널위에 playPanel이 따로있기때문에 키를 다시 먹게해줘야함
				gamePanel.requestFocus();
				gamePanel.setFocusable(true);

			} 

			else if (e.getSource() == homeMusicButton) {

				buttonSound.play();

				if (homeMusicSwitch == true) {

					homeMusic.off();// 배경음악을 끄는 코드
					buttonSetting(homeMusicButton, musicOff, 415, 880); // 스위치 이미지를 바꾸는 코드
					homeMusicSwitch = false;
				}

				else {
					homeMusic.on();// 배경음악을 켜는 코드
					buttonSetting(homeMusicButton, musicOn, 415, 880); // 스위치 이미지를 바꾸는 코드
					homeMusicSwitch = true;
				}

			}

		}
	}

	public class KeyBoardListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT: {

				if (playPanel.animals.get(playPanel.counts) == playPanel.left1
						|| playPanel.animals.get(playPanel.counts) == playPanel.left2){
					correctSound.play();
					playPanel.corrects++;
				} else {
					errorSound.play();
					playPanel.errors++;
				}

				playPanel.countPlus();
				playPanel.left = new ImageIcon("./Image/left2.png");
				playPanel.repaint();
				
				if (playPanel.counts ==100) {
					timer.stop();
					gamePanel.setFocusable(false);

				}

			}
				break;
			case KeyEvent.VK_RIGHT: {
				if (playPanel.animals.get(playPanel.counts) == playPanel.right1
						|| playPanel.animals.get(playPanel.counts) == playPanel.right2) {
					correctSound.play();
					playPanel.corrects++;
				} else {
					errorSound.play();
					playPanel.errors++;
				}

				playPanel.countPlus();
				playPanel.right = new ImageIcon("./Image/right2.png");
				playPanel.repaint();
				
				if (playPanel.counts ==100) {
					timer.stop();
					gamePanel.setFocusable(false);
				}

			}
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {

			case KeyEvent.VK_LEFT: {
				playPanel.left = new ImageIcon("./Image/left1.png");
				playPanel.repaint();
			}
				break;

			case KeyEvent.VK_RIGHT: {
				playPanel.right = new ImageIcon("./Image/right1.png");
				playPanel.repaint();
			}
				break;
			}
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

	}

	// 디자인할때 좌표를 알기위해서 사용할 마우스리스너
	public class MListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("x좌표 :" + e.getX() + "y좌표 :" + e.getY());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			playPanel.time--;
			playPanel.repaint();

			if (playPanel.time == 0) {
				timer.stop();
				gamePanel.setFocusable(false);

			}

		}

	}

	public class CountListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
				 
			startCount--;
			if (startCount == 0) {
				counter.stop();
				timer.start();
				startCount = 3;

				stopButton.setVisible(true);
				musicButton.setVisible(true);

				gamePanel.requestFocus();
				gamePanel.setFocusable(true);

				gameMusic.play();
			}
		}

	}

	public class HelpPanel extends JPanel {
		public HelpPanel() {
			setLayout(null);
		}

		public void paintComponent(Graphics g) {
			g.drawImage(helpImg.getImage(), 0, 0, helpFrame.getWidth(), helpFrame.getHeight() * 6 / 8, null);

			g.setFont(new Font("Monospaced", Font.BOLD, 15));
			g.drawString("1. 좌,우 화살표키를 사용해 동물 100마리를", 30, helpFrame.getHeight() * 6 / 8 + 30);
			g.drawString("    시간내에 알맞은 방향으로 옮겨주세요", 30, helpFrame.getHeight() * 6 / 8 + 45);

			g.drawString("2. 호랑이와 곰은 게임 후반에 추가됩니다.", 30, helpFrame.getHeight() * 6 / 8 + 75);

			g.drawString("3. 빠르고 정확하게 옮길수록", 30, helpFrame.getHeight() * 6 / 8 + 105);
			g.drawString("    좋은 점수를 받을수 있습니다", 30, helpFrame.getHeight() * 6 / 8 + 120);

		}
	}

	public class HomeImg extends JPanel {
		public HomeImg() {
			setSize(500, 800);
			setLayout(null);

		}

		public void paintComponent(Graphics g) {

			g.drawImage(elephant.getImage(), 50, 50, 150, 300, null);
			g.drawImage(tiger.getImage(), 300, 50, 150, 300, null);
			g.drawImage(giraffe.getImage(), 50, 450, 150, 300, null);
			g.drawImage(bear.getImage(), 300, 450, 150, 300, null);

			g.setFont(new Font("양재깨비체B", Font.BOLD, 60));
			g.drawString("LEFT & RIGHT", 50, 430);
		}
	}

	public class RankPanel extends JPanel {
		public RankPanel() {
			setSize(rankFrame.getWidth(), rankFrame.getHeight());
			setLayout(null);

			// Collections.sort(playerList,new PlayerComparator()); //점수의 내림차순으로 정렬

		}

		public void paintComponent(Graphics g) {
			g.drawImage(ranking.getImage(), -10, 315, rankFrame.getWidth(), rankFrame.getHeight() - 350, null);

			g.setFont(new Font("양재깨비체B", Font.BOLD, 15));

		}

	}
}
