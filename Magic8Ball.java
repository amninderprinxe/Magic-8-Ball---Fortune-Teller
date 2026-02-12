import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Magic8Ball extends JFrame {

    JTextField questionField;
    JLabel answerLabel, ballLabel;
    JButton askButton;
    Timer shakeTimer;

    int shakeCount = 0;
    int xPos = 150;

    String[] answers = {
            "Yes, definitely!",
            "No way!",
            "Ask again later...",
            "It is certain.",
            "Very doubtful.",
            "Signs point to yes.",
            "Better not tell you now.",
            "Absolutely not."
    };

    public Magic8Ball() {
        setTitle("Magic 8 Ball Fortune Teller");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel instruction = new JLabel("Ask a Yes/No Question:");
        instruction.setBounds(100, 20, 200, 20);

        questionField = new JTextField();
        questionField.setBounds(50, 45, 300, 25);

        ballLabel = new JLabel("🎱");
        ballLabel.setFont(new Font("Arial", Font.BOLD, 50));
        ballLabel.setBounds(xPos, 80, 100, 100);

        answerLabel = new JLabel("Your fortune will appear here", JLabel.CENTER);
        answerLabel.setBounds(50, 180, 300, 30);

        askButton = new JButton("Ask the Ball");
        askButton.setBounds(130, 230, 140, 30);

        add(instruction);
        add(questionField);
        add(ballLabel);
        add(answerLabel);
        add(askButton);

        askButton.addActionListener(e -> startShaking());

        setVisible(true);
    }

    void startShaking() {
        if (questionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a question!");
            return;
        }

        shakeCount = 0;
        askButton.setEnabled(false);
        answerLabel.setText("Shaking...");

        shakeTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (shakeCount % 2 == 0) {
                    ballLabel.setLocation(xPos - 10, 80);
                } else {
                    ballLabel.setLocation(xPos + 10, 80);
                }

                shakeCount++;

                if (shakeCount == 12) {
                    shakeTimer.stop();
                    ballLabel.setLocation(xPos, 80);
                    showAnswer();
                    askButton.setEnabled(true);
                }
            }
        });

        shakeTimer.start();
    }

    void showAnswer() {
        Random random = new Random();
        answerLabel.setText(answers[random.nextInt(answers.length)]);
    }

    public static void main(String[] args) {
        new Magic8Ball();
    }
}
