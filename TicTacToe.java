import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private boolean player1Turn;
    private int movesCount = 0;
    private String player1Symbol;
    private String player2Symbol;

    public TicTacToe() {
        setTitle("Tic Tac Toe Challenge");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        // Initialize buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }

        // Show player selection dialog
        choosePlayerSymbol();
    }

    // Method to display a dialog for player symbol selection (X or O)
    private void choosePlayerSymbol() {
        String[] options = {"X", "O"};
        int selection = JOptionPane.showOptionDialog(this, "Choose your symbol (Player 1)", 
                                                     "Player Symbol Selection",
                                                     JOptionPane.DEFAULT_OPTION,
                                                     JOptionPane.QUESTION_MESSAGE, 
                                                     null, options, options[0]);

        // Assign symbols based on the player's choice
        if (selection == 0) {
            player1Symbol = "X";
            player2Symbol = "O";
        } else {
            player1Symbol = "O";
            player2Symbol = "X";
        }

        player1Turn = true; // Player 1 always starts first
        JOptionPane.showMessageDialog(this, "Player 1 (" + player1Symbol + ") will play first!");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();

        // If the button is already clicked, ignore the action
        if (!clickedButton.getText().equals("")) {
            return;
        }

        // Set the button's text to either Player 1 or Player 2's symbol based on the player's turn
        if (player1Turn) {
            clickedButton.setText(player1Symbol);
        } else {
            clickedButton.setText(player2Symbol);
        }

        movesCount++;
        player1Turn = !player1Turn; // Switch turns

        // Check if there's a winner
        if (checkForWinner()) {
            String winner = player1Turn ? player2Symbol : player1Symbol;  // The last player who played is the winner
            JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
            resetGame();
        } else if (movesCount == 9) {  // If all moves are used, it's a draw
            JOptionPane.showMessageDialog(this, "It's a draw!");
            resetGame();
        }
    }

    // Method to check for win conditions
    private boolean checkForWinner() {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2])) {
                return true;
            }
            if (checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return true;
            }
        }
        // Check diagonals
        if (checkLine(buttons[0][0], buttons[1][1], buttons[2][2])) {
            return true;
        }
        if (checkLine(buttons[0][2], buttons[1][1], buttons[2][0])) {
            return true;
        }

        return false;
    }

    // Method to check if three buttons in a row, column, or diagonal are the same
    private boolean checkLine(JButton b1, JButton b2, JButton b3) {
        String text1 = b1.getText();
        String text2 = b2.getText();
        String text3 = b3.getText();

        return !text1.equals("") && text1.equals(text2) && text2.equals(text3);
    }

    // Method to reset the game
    // Method to reset the game with a "Play Again?" option
private void resetGame() {
    // Ask the player if they want to play again
    int response = JOptionPane.showConfirmDialog(this, 
        "Do you want to play again?", "Play Again?", 
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    
    if (response == JOptionPane.YES_OPTION) {
        // Player chose to play again, reset the game
        player1Turn = true;
        movesCount = 0;

        // Clear all the buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }

        // Show player selection dialog again for the new game
        choosePlayerSymbol();
    } else {
        // Player chose not to play again, close the game
        JOptionPane.showMessageDialog(this, "Thanks for playing!");
        System.exit(0);  // Exit the game
    }
}


    public static void main(String[] args) {
        // Set up the GUI in the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
        	TicTacToe game = new TicTacToe();
        	game.setVisible(true);
        	game.setLocationRelativeTo(null);
        });
    }
}
