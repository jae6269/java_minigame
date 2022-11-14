import java.io.File;

import javax.sound.sampled.*;

public class Audio {
	private String path;
	private boolean isLoop;
	private boolean onOff = true;

	private Clip clip;

	AudioInputStream audioStream;
	File audioFile;

	public Audio(String path, boolean isLoop) {
		this.path = path;
		this.isLoop = isLoop;
	}

	public void play() {

		try {

			clip = AudioSystem.getClip();
			audioFile = new File(path);
			audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
			clip.start();
			if (isLoop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);

		}

		catch (Exception e) {
			System.out.println("음향파일 로딩 실패");
		}

	}

	public void stop() {
		clip.stop();
	}

	public void restart() {
		clip.start();
	}

	public void off() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME.MASTER_GAIN);
		float min = gainControl.getMinimum();

		gainControl.setValue(min);
	}

	public void on() {
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.VOLUME.MASTER_GAIN);
		float max = gainControl.getMaximum();
		gainControl.setValue(max);
	}

}
