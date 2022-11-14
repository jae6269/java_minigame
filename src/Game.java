//2021-10 �ڹ� �̴ϰ��� ������Ʈ

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

	private final int FRAME_WIDTH = 500; // ������ �ʺ�
	private final int FRAME_HEIGHT = 1000; // ������ ���� 1025

	private final int BUTTON_SIZE = 60; // ��ư�� �ʺ�, ����

	boolean musicSwitch = true; // ���� �¿��� ���� ����(�ʱⰪ true)
	boolean volumeSwitch = true; // ���� �¿��� ���� ����(�ʱⰪ true)
	boolean homeMusicSwitch = true; // Ȩȭ�� ���� �¿��� ���� ����
	boolean homeVolumeSwitch = true; // Ȩȭ�� ���� �¿��� ���� ����

	int count = 0;// �÷��̾ Ű���带 ���� Ƚ��(Ű�� �������� ������ �����̶� ��ġ�ϴ��� Ȯ���Ҷ� ���)

	int startCount = 3; // ī��Ʈ �ٿ� Ÿ�̸ӿ� ���� ����
	int rest = 100; // ���� ���� Ÿ�̸ӿ� ���� ����

	// ���� ���� �̹�����
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
	ImageIcon helpImg = new ImageIcon("./Image/����ȭ��.png");
	ImageIcon tiger = new ImageIcon("./Image/tiger.png");
	ImageIcon elephant = new ImageIcon("./Image/elephant.png");
	ImageIcon giraffe = new ImageIcon("./Image/giraffe.png");
	ImageIcon bear = new ImageIcon("./Image/bear.png");
	ImageIcon ranking = new ImageIcon("./Image/ranking.png");

	// �����ӵ�
	JFrame frame = new JFrame(); // ������
	JFrame helpFrame = new JFrame(); // ������
	JFrame rankFrame = new JFrame(); // ������

	// �гε�
	JPanel homePanel = new JPanel();// Ȩȭ���� �г�
	JPanel gamePanel = new JPanel();// ������ �������϶� ȭ���� �г�
	HelpPanel helpPanel = new HelpPanel(); // �����г�
	HomeImg homeImg = new HomeImg(); // Ȩ ȭ���� �̹����� �׸��� �г�
	RankPanel rankPanel = new RankPanel(); //��ŷ ȭ�� �г�
	PlayPanel playPanel;

	// ��ư��
	JButton startButton = new JButton(); // ���ο� ���� ���۹�ư (home�г�->game �г�)
	JButton rankButton = new JButton();// ��ŷ ��ư(home �г�)
	JButton exitButton = new JButton();// ������ ��ư(home �г�)
	JButton helpButton = new JButton();// ���� ��ư(home �г�)
	JButton stopButton = new JButton(); // ���� �ߴܹ�ư(game �г�-Ÿ�̸Ӱ� ����)
	JButton restartButton = new JButton(); // ���ο� ���� ���۹�ư (game�г�-stopButton�� ������ ������)
	JButton keepButton = new JButton(); // ������ ��� �����ϴ� ��ư(game �г�-stopButton�� ������ ������)
	JButton homeButton = new JButton(); // homePannel�� ���ư��� ��ư(game�г�-stopButton�� ������ ������)
	JButton finishButton = new JButton(); // homePannel�� ���ư��� ��ư(finish �г�)
	JButton regameButton = new JButton();// ������ �ٽ� �����ϴ� ��ư(finish�г�)
	JButton musicButton = new JButton(); // music�� on off�� �����ϴ� ��ư
	JButton homeMusicButton = new JButton(); // Ȩȭ�� ���� ����ġ��ư
	
	// �뷡��ü��
	Audio homeMusic = new Audio("./Music/homeMusic.wav", true);
	Audio gameMusic = new Audio("./Music/gameMusic.wav", true);
	Audio buttonSound = new Audio("./Music/buttonSound.wav", false);
	Audio correctSound = new Audio("./Music/correct.wav", false);
	Audio errorSound = new Audio("./Music/error.wav", false);
	Audio countSound = new Audio("./Music/countSound.wav", false);

	// �����ʵ�� Ÿ�̸�
	ButtonListener buttonListener = new ButtonListener(); // ��ư������ ��ü
	KeyBoardListener keyListener = new KeyBoardListener(); // Ű���帮���� ��ü
	Timer timer; // ���ӽð��� üũ�ϴ� Ÿ�̸�
	Timer counter; // ���ӽ��۽� ī��Ʈ�ٿ��� ���� Ÿ�̸�
	TimerListener timerListener;
	CountListener countListener;
	
	public static void main(String[] args) { 
		// TODO Auto-generated method stub
			new Game().start();
	}

	public void start() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ������ �ʿ��� ������ �߰�
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
		gamePanel.setFocusable(false);// Ű �ȸ԰�(�ʱ����)

		// Ÿ�̸Ӹ����� �߰�
		timerListener = new TimerListener();
		countListener = new CountListener();

		timer = new Timer(1000, timerListener);
		counter = new Timer(1000, countListener);


		helpPanel.setBackground(Color.white);
		gamePanel.setBackground(Color.white);
		homePanel.setBackground(Color.white);

		// home �г� ������
		homePanel.setLayout(null);// Layout�� ���ּ� �����Ӱ� ��ư��ġ���� ����

		// homePanel ��ư����
		buttonSetting(startButton, start, 50, 810);
		buttonSetting(rankButton, rank, 160, 810);
		buttonSetting(helpButton, help, 270, 810);
		buttonSetting(exitButton, exit, 380, 810);
		buttonSetting(homeMusicButton, musicOn, 415, 880);

		// game�г� ������
		gamePanel.setLayout(null);

		// gamePanel ��ư����
		buttonSetting(stopButton, pause, 400, 20);
		buttonSetting(homeButton, home, 400, 20);
		buttonSetting(restartButton, restart, 310, 20);
		buttonSetting(keepButton, keep, 220, 20);
		buttonSetting(musicButton, musicOn, 415, 880);

		// ������ �гο� �ʿ��� ��ư,�󺧵��� �߰�
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
		

		// gamePanel���� stop��ư�� �������� ���̴� �� ��ư�� �ʱ���¼���
		restartButton.setVisible(false);
		keepButton.setVisible(false);
		homeButton.setVisible(false);

		frame.add(homePanel);

		frame.setResizable(false);// ������ ũ������ ����: true �Ұ��� : false
		frame.setTitle("LEFT & RIGHT GAME");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		frame.setVisible(true);

		homeMusic.play(); // home�гο��� �����ϴϱ� Ȩ���� ���

	}

	// ��ư���� �ִ� �̹����� ���̵��� �����ϴ� �ż���
	public void buttonSetting(JButton bt, ImageIcon image, int x, int y) {

		Image img = image.getImage(); // ImageIcon ũ�⸦ �����ϱ����� �̹����� ����
		
		//�̹����� ��ư ũ��� ������ �����ؼ� �־���
		Image updateImg = img.getScaledInstance(BUTTON_SIZE, BUTTON_SIZE, Image.SCALE_SMOOTH); 
																								
		ImageIcon updateIcon = new ImageIcon(updateImg);

		bt.setIcon(updateIcon);

		bt.setBorderPainted(false); // ��ư�� �׵θ��� ����
		bt.setFocusPainted(false); // ��ư �̹����� �׵θ� ����

		bt.setBackground(Color.white);
		bt.setBounds(x, y, BUTTON_SIZE, BUTTON_SIZE);
	}

	public class ButtonListener implements ActionListener {
		// ������ ��ư���� ���������� �װſ� �´� �гη� �̵��ϱ� ���� ��ư������
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == startButton) {

				//ȭ����� �ڵ�
				homePanel.setVisible(false);
				gamePanel.setVisible(true); //�ѹ� Ȩ�гη� ���ٰ� �ٽ� �����Ҷ��� ���ؼ�

				playPanel = new PlayPanel();
				
				gamePanel.add(playPanel);

				playPanel.setBounds(0, 90, playPanel.getWidth(), playPanel.getHeight());
				

				frame.add(gamePanel);

				stopButton.setVisible(false);
				musicButton.setVisible(false);
				
				gamePanel.requestFocus();
				gamePanel.setFocusable(true);

				//���� ���� �ڵ�
				buttonSound.play();
				homeMusic.stop();
				countSound.play();
				// 321 ī���� ���Ŀ� ������� �ҰŶ� ī��Ʈ�ٿ�Ÿ�̸Ӹ� ����

				counter.start();
			} else if (e.getSource() == stopButton) {
				// ���ǿ� ���� �ڵ��
				buttonSound.play();
				gameMusic.stop();

				restartButton.setVisible(true);
				keepButton.setVisible(true);
				homeButton.setVisible(true);

				gamePanel.setFocusable(false);// Ű �ȸ԰�

				stopButton.setVisible(false);

				timer.stop();

			} else if (e.getSource() == restartButton) {
				// ���ǿ� ���� �ڵ��
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
				// ���ǿ� ���� �ڵ��
				buttonSound.play();
				gameMusic.restart();

				// Ÿ�̸Ӱ� ����۵Ǵ� �ڵ�
				restartButton.setVisible(false);
				keepButton.setVisible(false);
				homeButton.setVisible(false);

				gamePanel.requestFocus();
				gamePanel.setFocusable(true);

				stopButton.setVisible(true);

				timer.restart();

			}

			else if (e.getSource() == rankButton) {

				// ���ǿ� ���� �ڵ��
				buttonSound.play();

				rankFrame.setTitle("Ranking");
				rankFrame.setBackground(Color.white);
				rankFrame.setSize(400, 500);
				rankFrame.add(rankPanel);
				rankFrame.setVisible(true);

			}

			else if (e.getSource() == helpButton) {
				// ���ǿ� ���� �ڵ��
				buttonSound.play();

				helpFrame.setTitle("Game Explanation");
				helpFrame.setSize(500, 800);
				helpFrame.setBackground(Color.white);

				helpFrame.add(helpPanel);
				helpFrame.setVisible(true);
			} else if (e.getSource() == exitButton) {
				// ���ǿ� ���� �ڵ��
				buttonSound.play();

				System.exit(0);
			}

			else if (e.getSource() == homeButton) {
				// ���ǿ� ���� �ڵ��
				buttonSound.play();
				homeMusic.play();
				
				playPanel.reset();
				playPanel.animalReset();
				playPanel.randomSet();

				restartButton.setVisible(false); // �ٽ� ������ �����Ҷ��� ����� ��ư���� �ʱ���·�
				keepButton.setVisible(false); // �ٽ� ������ �����Ҷ��� ����� ��ư���� �ʱ���·�
				homeButton.setVisible(false); // �ٽ� ������ �����Ҷ��� ����� ��ư���� �ʱ���·�
				stopButton.setVisible(true); // �ٽ� ������ �����Ҷ��� ����� ��ư���� �ʱ���·�

				gamePanel.setVisible(false);// gamePanel�� ����

				homePanel.setVisible(true);// start��ư�� ������ false�� �ٲ� ���� �ٽ� true��
				frame.add(homePanel);
			}

			else if (e.getSource() == musicButton) {

				buttonSound.play();

				if (musicSwitch == true) {
					gameMusic.off();// ��������� ���� �ڵ�

					buttonSetting(musicButton, musicOff, 415, 880); // ����ġ �̹����� �ٲٴ� �ڵ�
					musicSwitch = false;
				}

				else {
					gameMusic.on();// ��������� �Ѵ� �ڵ�
					buttonSetting(musicButton, musicOn, 415, 880); // ����ġ �̹����� �ٲٴ� �ڵ�
					musicSwitch = true;
				}
				//�����г����� playPanel�� �����ֱ⶧���� Ű�� �ٽ� �԰��������
				gamePanel.requestFocus();
				gamePanel.setFocusable(true);

			} 

			else if (e.getSource() == homeMusicButton) {

				buttonSound.play();

				if (homeMusicSwitch == true) {

					homeMusic.off();// ��������� ���� �ڵ�
					buttonSetting(homeMusicButton, musicOff, 415, 880); // ����ġ �̹����� �ٲٴ� �ڵ�
					homeMusicSwitch = false;
				}

				else {
					homeMusic.on();// ��������� �Ѵ� �ڵ�
					buttonSetting(homeMusicButton, musicOn, 415, 880); // ����ġ �̹����� �ٲٴ� �ڵ�
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

	// �������Ҷ� ��ǥ�� �˱����ؼ� ����� ���콺������
	public class MListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("x��ǥ :" + e.getX() + "y��ǥ :" + e.getY());
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
			g.drawString("1. ��,�� ȭ��ǥŰ�� ����� ���� 100������", 30, helpFrame.getHeight() * 6 / 8 + 30);
			g.drawString("    �ð����� �˸��� �������� �Ű��ּ���", 30, helpFrame.getHeight() * 6 / 8 + 45);

			g.drawString("2. ȣ���̿� ���� ���� �Ĺݿ� �߰��˴ϴ�.", 30, helpFrame.getHeight() * 6 / 8 + 75);

			g.drawString("3. ������ ��Ȯ�ϰ� �ű����", 30, helpFrame.getHeight() * 6 / 8 + 105);
			g.drawString("    ���� ������ ������ �ֽ��ϴ�", 30, helpFrame.getHeight() * 6 / 8 + 120);

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

			g.setFont(new Font("�������üB", Font.BOLD, 60));
			g.drawString("LEFT & RIGHT", 50, 430);
		}
	}

	public class RankPanel extends JPanel {
		public RankPanel() {
			setSize(rankFrame.getWidth(), rankFrame.getHeight());
			setLayout(null);

			// Collections.sort(playerList,new PlayerComparator()); //������ ������������ ����

		}

		public void paintComponent(Graphics g) {
			g.drawImage(ranking.getImage(), -10, 315, rankFrame.getWidth(), rankFrame.getHeight() - 350, null);

			g.setFont(new Font("�������üB", Font.BOLD, 15));

		}

	}
}
