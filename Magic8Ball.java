import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.File;

public class Magic8Ball extends JFrame {

    JTextField questionBox;
    JLabel answerText;
    JLabel ball;
    JButton askButton;

    Timer timer;
    int count = 0;
    int startX = 150;

    Clip shakeSound;
    Clip answerSound;

    String[] answers = {
            "Yes!",
            "No!",
            "Maybe...",
            "Ask again later.",
            "Definitely!",
            "I don't think so.",
            "Signs say yes.",
            "Very doubtful."
    };

    public Magic8Ball() {

        // Window settings
        setTitle("Magic 8 Ball");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Instruction text
        JLabel instruction = new JLabel("Ask a Yes or No Question:");
        instruction.setBounds(100, 20, 200, 20);
        add(instruction);

        // Text field
        questionBox = new JTextField();
        questionBox.setBounds(50, 45, 300, 25);
        add(questionBox);

        // Press ENTER to ask
        questionBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shakeBall();
            }
        });

        // Magic 8 ball
        ball = new JLabel("🎱");
        ball.setFont(new Font("Arial", Font.BOLD, 50));
        ball.setBounds(startX, 80, 100, 100);
        add(ball);

        // Answer label
        answerText = new JLabel("Your answer will appear here", JLabel.CENTER);
        answerText.setBounds(50, 180, 300, 30);
        add(answerText);

        // Ask button
        askButton = new JButton("Ask");
        askButton.setBounds(150, 230, 100, 30);
        add(askButton);

        askButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shakeBall();
            }
        });

        // Load sounds
        loadSounds();

        setVisible(true);
    }

    // Load sound files
    void loadSounds() {
        try {
            shakeSound = AudioSystem.getClip();
            shakeSound.open(AudioSystem.getAudioInputStream(new File("shake.wav")));

            answerSound = AudioSystem.getClip();
            answerSound.open(AudioSystem.getAudioInputStream(new File("answer.wav")));
        } catch (Exception e) {
            System.out.println("Sound files missing");
        }
    }

    // Shake animation
    void shakeBall() {

        if (questionBox.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please type a question!");
            return;
        }

        count = 0;
        askButton.setEnabled(false);
        answerText.setText("Shaking...");

        if (shakeSound != null) {
            shakeSound.setFramePosition(0);
            shakeSound.start();
        }

        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (count % 2 == 0) {
                    ball.setLocation(startX - 10, 80);
                } else {
                    ball.setLocation(startX + 10, 80);
                }

                count++;

                if (count == 12) {
                    timer.stop();
                    ball.setLocation(startX, 80);
                    showAnswer();
                    askButton.setEnabled(true);
                }
            }
        });

        timer.start();
    }

    // Show random answer
    void showAnswer() {
        Random rand = new Random();
        int number = rand.nextInt(answers.length);
        answerText.setText(a
