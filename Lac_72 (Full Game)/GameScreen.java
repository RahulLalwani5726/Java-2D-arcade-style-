
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameScreen extends JPanel implements ActionListener, KeyListener {
    private static int PANEL_WIDTH = 500;
    private static int PANEl_HEGHT = 470;
    static int ammo = 4;
    File GaneOver = new File("gta-san-andreas-ah-shit-here-we-go-again.wav");
    File Hit = new File("go-away-pt1-101soundboards.wav");
    AudioInputStream GameOverAudio;
    AudioInputStream HitAudio;
    Clip clp;
    Clip clp2;

    static Image Explosion = new ImageIcon("D:\\Code\\Java\\01_Bro Code\\Projects\\Lac_72 (Full Game)\\bang.png")
            .getImage()
            .getScaledInstance(80, 80, Image.SCALE_AREA_AVERAGING);
    int ExplosionX = 600;
    int ExplosionY = 600;

    static Image BG = new ImageIcon("pexels-pixabay-71116.jpg").getImage().getScaledInstance(500, 500,
            Image.SCALE_AREA_AVERAGING);
    static Image enemy = new ImageIcon("pngwing.com.png").getImage().getScaledInstance(50, 50,
            Image.SCALE_AREA_AVERAGING);
    static Image User = new ImageIcon("spaceship-removebg-preview.png").getImage().getScaledInstance(100, 100,
            Image.SCALE_AREA_AVERAGING);
    static Image Bullet = new ImageIcon("pixel-art-bullet-pixelated-pistol-260nw-2366089421-removebg-preview.png")
            .getImage().getScaledInstance(50, 50,
                    Image.SCALE_AREA_AVERAGING);

    int bulletPosition = 600;
    int BulletHPstion = UserPositon;
    boolean nextBullrt = true;

    static int Xpositon = 0;
    int x = 1;
    static int Ypositon = 0;
    int y = 1;

    static Timer timer;
    static int UserPositon = 250;

    GameScreen() throws InterruptedException {

        this.setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener((KeyListener) this);

        timer = new Timer(10, this);

        timer.start();
    }

    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(BG, 0, 0, this);
        g2d.drawImage(enemy, Xpositon, Ypositon, this);
        g2d.drawImage(User, UserPositon, 370, this);
        g2d.drawImage(Bullet, BulletHPstion, bulletPosition, this);

        g2d.setColor(Color.red);
        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g2d.drawImage(Bullet, 410, 10, this);
        g2d.drawString("x" + Integer.valueOf(ammo - 1).toString(), 450, 40);

        g2d.drawImage(Explosion, ExplosionX, ExplosionY, this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (Xpositon >= PANEL_WIDTH - enemy.getWidth(null) || Xpositon < 0) {
            x = x * -1;

        }
        Xpositon = Xpositon + x;

        if (Ypositon >= PANEl_HEGHT - enemy.getWidth(null) || Ypositon < 0) {
            y = y * -1;
        }
        Ypositon = Ypositon + y;

        if (Ypositon >= 350 && (UserPositon >= Xpositon - 30 && UserPositon <= Xpositon + 30)) {

            try {

                GameOverAudio = AudioSystem.getAudioInputStream(GaneOver);

                clp = AudioSystem.getClip();
                clp.open(GameOverAudio);

                clp.start();

            } catch (Exception exception) {

            }

            UserPositon = 200;
            ExplosionX = Xpositon;
            ExplosionY = Ypositon;
            enemy = null;
            Bullet = null;
            ammo = 1;
            BG = new ImageIcon("D:\\Code\\Java\\01_Bro Code\\Projects\\Lac_72 (Full Game)\\3912192_2066996.jpg")
                    .getImage().getScaledInstance(500, 500,
                            Image.SCALE_AREA_AVERAGING);
        }

        if ((BulletHPstion >= Xpositon - 30 && BulletHPstion <= Xpositon + 30)
                && (bulletPosition >= Ypositon - 30 && bulletPosition <= Ypositon + 30)) {

            try {
                HitAudio = AudioSystem.getAudioInputStream(Hit);
                clp2 = AudioSystem.getClip();
                clp2.open(HitAudio);
                clp2.start();

            } catch (Exception exception) {
            }

            x = new Random().nextInt(1, 8);
            y = new Random().nextInt(1, 8);
            ExplosionX = Xpositon;
            ExplosionY = Ypositon;
            Xpositon = new Random().nextInt(0, 400);
            Xpositon = 0;
            Ypositon = 0;
            nextBullrt = true;
            bulletPosition = 600;
            ammo += 3;
        }

        if (ammo <= 0) {
            enemy = null;
            BG = new ImageIcon("3912192_2066996.jpg")
                    .getImage().getScaledInstance(500, 500,
                            Image.SCALE_AREA_AVERAGING);
        }

        if (nextBullrt == false) {
            bulletPosition -= 1;

        } else {
            BulletHPstion = UserPositon;
        }
        if (bulletPosition <= -30) {
            nextBullrt = true;
            bulletPosition = 600;
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getKeyChar() == 'a') {
            if (UserPositon > -25) {
                UserPositon -= 10;
            }

        } else if (e.getKeyChar() == 'd') {
            if (UserPositon <= 400) {
                UserPositon += 10;
            }
        }
        if (e.getKeyChar() == ' ') {
            if (nextBullrt == true) {
                ammo -= 1;
                bulletPosition = 370;
                nextBullrt = false;
                ExplosionX = 600;
                ExplosionY = 600;
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            if (UserPositon > -25) {
                UserPositon -= 10;
            }
        } else if (key == KeyEvent.VK_RIGHT) {
            if (UserPositon <= 400) {
                UserPositon += 10;
            }
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        GameScreen game = null;
        JFrame frame = new JFrame("Game");
        try {

            game = new GameScreen();
        } catch (Exception e) {
        }

        frame.add(game);
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
