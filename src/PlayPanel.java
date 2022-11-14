import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PlayPanel extends JPanel {

	private final int SIZE = 100;
	private final int DIRECTION_SIZE = 100;
	private final int ICON_SIZE = 30;
	private final int FIRST_SIZE = 140;
	private final int SECOND_SIZE = 100;
	private final int THIRD_SIZE = 70;
	private final int BACK_ANIMAL_SIZE = 60;

	int time = 100; // 남은시간 (0이 되면 게임종료)
	int corrects = 0;
	int errors = 0;
	int counts = 0; // 좌 우 키보드를 누를때마다 증가됨( 100이되면 게임 종료)
	int score;

	// 게임의 배경화면
	ImageIcon background = new ImageIcon("./Image/gameBack.jpg");

	// 화살표들(1 : 눌리기 전 이미지 2 : 눌렸을때 이미지)
	ImageIcon left = new ImageIcon("./Image/left1.png");
	ImageIcon right = new ImageIcon("./Image/right1.png");

	// 좌상단에 들어갈 시계 , 정답 , 오답 수
	ImageIcon clock = new ImageIcon("./Image/clock.png");
	ImageIcon correct = new ImageIcon("./Image/correct.png");
	ImageIcon error = new ImageIcon("./Image/error.png");

	// 동물들
	ImageIcon elephant = new ImageIcon("./Image/elephant.png");
	ImageIcon giraffe = new ImageIcon("./Image/giraffe.png");
	ImageIcon bear = new ImageIcon("./Image/bear.png");
	ImageIcon tiger = new ImageIcon("./Image/tiger.png");
	ImageIcon bear2 = new ImageIcon("./Image/bear2.png");
	ImageIcon img = new ImageIcon("./Image/finishPanel.png");

	ImageIcon left1, left2, right1, right2;

	Game game = new Game();

	ArrayList<ImageIcon> animals = new ArrayList<ImageIcon>();

	public PlayPanel() {
		animalReset();
		randomSet();

		left = new ImageIcon("./Image/left1.png");
		right = new ImageIcon("./Image/right1.png");

		setSize(new Dimension(500, 780));
		setLayout(null);

	}

	public void paintComponent(Graphics g) {

		if (counts == 100 || time == 0) {

			if (time * 10 - errors * 15 <= 0)
				score = 0;
			else
				score = time * 10 - errors * 15; // 점수를 설정

			g.drawImage(img.getImage(), 30, 200, 400, 400, null);

			g.setFont(new Font("양재깨비체B", Font.BOLD, 60));
			g.setColor(Color.white);

			g.drawString(score + "", 160, 400);
			g.setFont(new Font("양재깨비체B", Font.BOLD, 25));
		}

		else {

			super.paintComponent(g);

			Dimension d = getSize();

			g.drawImage(background.getImage(), 0, 0, d.width, d.height, null);

			g.drawImage(left.getImage(), 30, 670, DIRECTION_SIZE, DIRECTION_SIZE, null);
			g.drawImage(right.getImage(), 370, 670, DIRECTION_SIZE, DIRECTION_SIZE, null);

			g.drawImage(clock.getImage(), 15, 25, ICON_SIZE, ICON_SIZE, null);
			g.drawImage(correct.getImage(), 15, 60, ICON_SIZE, ICON_SIZE, null);
			g.drawImage(error.getImage(), 15, 95, ICON_SIZE, ICON_SIZE, null);

			g.drawImage(left2.getImage(), 10, 590, BACK_ANIMAL_SIZE, BACK_ANIMAL_SIZE, null);
			g.drawImage(left1.getImage(), 80, 590, BACK_ANIMAL_SIZE, BACK_ANIMAL_SIZE, null);

			g.drawImage(right1.getImage(), 360, 590, BACK_ANIMAL_SIZE, BACK_ANIMAL_SIZE, null);
			g.drawImage(right2.getImage(), 425, 590, BACK_ANIMAL_SIZE, BACK_ANIMAL_SIZE, null);

			g.setFont(new Font("양재깨비체B", Font.BOLD, 20));
			g.drawString(time + "", 60, 45);
			g.setFont(new Font("양재깨비체B", Font.BOLD, 15));
			g.drawString(corrects + "", 60, 80);
			g.drawString(errors + "", 60, 115);

			if (counts <= 50)
			// 동물그려주기! - 3마리그려줄거임
			{
				g.drawImage(animals.get(counts).getImage(), 180, 640, FIRST_SIZE, FIRST_SIZE, null);
				g.drawImage(animals.get(counts + 1).getImage(), 200, 540, SECOND_SIZE, SECOND_SIZE, null);
				g.drawImage(animals.get(counts + 2).getImage(), 215, 470, THIRD_SIZE, THIRD_SIZE, null);
			}

			if (counts > 50 && counts <= 97) {
				g.drawImage(animals.get(counts).getImage(), 180, 640, FIRST_SIZE, FIRST_SIZE, null);
				g.drawImage(animals.get(counts + 1).getImage(), 200, 540, SECOND_SIZE, SECOND_SIZE, null);
				g.drawImage(animals.get(counts + 2).getImage(), 215, 470, THIRD_SIZE, THIRD_SIZE, null);
			}

			if (counts == 98) {
				g.drawImage(animals.get(counts).getImage(), 180, 640, FIRST_SIZE, FIRST_SIZE, null);
				g.drawImage(animals.get(counts + 1).getImage(), 200, 540, SECOND_SIZE, SECOND_SIZE, null);
			}
			if (counts == 99) {
				g.drawImage(animals.get(counts).getImage(), 180, 640, FIRST_SIZE, FIRST_SIZE, null);
			}
		}

	}

	public void countPlus() {
		this.counts++;
	}

	public void animalReset() {
		// 랜덤으로 100개의 동물들을 생성 (50개는 코끼리랑 기린만, 50개는 4개다)
		for (int i = 0; i < SIZE; i++) {
			int random1, random2;
			if (i < 50) {
				double dValue = Math.random();
				random1 = (int) (dValue * 10) + 1;
				if (random1 % 2 == 1)
					animals.add(i, elephant);
				else
					animals.add(i, giraffe);
			}

			else if (i >= 50) {
				double dValue = Math.random();
				random1 = (int) (dValue * 10) + 1;
				double dValue2 = Math.random();
				random2 = (int) (dValue2 * 10) + 1;
				if (random1 % 2 == 1) {
					if (random2 % 2 == 1) {
						animals.add(i, elephant);
					} else if (random2 % 2 == 0) {
						animals.add(i, giraffe);
					}
				}
				if (random1 % 2 == 0) {
					if (random2 % 2 == 1) {
						animals.add(i, bear);
					} else if (random2 % 2 == 0) {
						animals.add(i, tiger);
					}
				}

			}

		}

	}

	public void randomSet() {
		int random1, random2;

		double dValue = Math.random();
		random1 = (int) (dValue * 10) + 1;
		double dValue2 = Math.random();
		random2 = (int) (dValue2 * 10) + 1;
		if (random1 % 2 == 0) {
			left1 = elephant;
			right1 = giraffe;
		} else {
			left1 = giraffe;
			right1 = elephant;
		}

		if (random2 % 2 == 0) {
			left2 = bear;
			right2 = tiger;
		} else {
			left2 = tiger;
			right2 = bear;
		}
	}

	public void reset() {
		this.time = 100;
		this.errors = 0;
		this.corrects = 0;
		this.counts = 0;

	}

}
