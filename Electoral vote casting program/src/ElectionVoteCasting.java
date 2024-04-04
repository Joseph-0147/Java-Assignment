import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ElectionVoteCasting extends JFrame implements ActionListener {
    private JTextField tfCandidate;
    private JButton btnVote;
    private JTextArea taResult;

    private volatile int votesCast;
    private int countCandidateA;
    private int countCandidateB;

    public ElectionVoteCasting() {
        setTitle("Election Vote Casting Application");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tfCandidate = new JTextField(10);
        btnVote = new JButton("Vote");
        taResult = new JTextArea(10, 30);
        taResult.setEditable(false);

        btnVote.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Candidate (A/B): "));
        panel.add(tfCandidate);
        panel.add(btnVote);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(taResult), BorderLayout.CENTER);
    }

    private void castVote(String candidate) {
        // Simulate voting action
        if (votesCast < 5) {
            taResult.append("Vote casted successfully: " + candidate + "\n");
            votesCast++;
            if (candidate.equals("A")) {
                countCandidateA++;
            } else if (candidate.equals("B")) {
                countCandidateB++;
            }
        } else {
            taResult.append("Maximum number of votes (5) reached.\n");
        }
    }

    private void determineWinner() {
        // Determine the winner based on the votes cast
        if (countCandidateA > countCandidateB) {
            taResult.append("Winner: Candidate A with " + countCandidateA + " votes\n");
        } else if (countCandidateB > countCandidateA) {
            taResult.append("Winner: Candidate B with " + countCandidateB + " votes\n");
        } else {
            taResult.append("It's a tie\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnVote) {
            String candidate = tfCandidate.getText().trim().toUpperCase();
            if (candidate.equals("A") || candidate.equals("B")) {
                if (votesCast < 5) {
                    new Thread(new Runnable() {
                        public void run() {
                            castVote(candidate);
                            if (votesCast == 5) {
                                determineWinner();
                            }
                        }
                    }).start();
                } else {
                    taResult.append("Maximum number of votes (5) reached.\n");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid candidate. Please enter A or B.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ElectionVoteCasting app = new ElectionVoteCasting();
                app.setVisible(true);
            }
        });
    }
}