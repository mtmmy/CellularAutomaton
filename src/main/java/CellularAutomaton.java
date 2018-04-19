import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CellularAutomaton extends JFrame implements KeyListener {

    private static final int GRID_WIDTH = 99;
    private static int[][] LAST_GRID = new int[GRID_WIDTH][GRID_WIDTH];
    private static int[][] CUR_GRID = new int[GRID_WIDTH][GRID_WIDTH];

    private DrawCanvas canvas;

    public CellularAutomaton() {

        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(20 + GRID_WIDTH * 10, 20 + GRID_WIDTH * 10));

        Container cp = getContentPane();
        cp.add(canvas);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Cellular Automaton");
        setVisible(true);

        this.addKeyListener(this);

        initGrids();
    }

    private void initGrids() {
        for (int i=0; i<GRID_WIDTH; i++) {
            for (int j=0; j<GRID_WIDTH; j++) {
                LAST_GRID[i][j] = CUR_GRID[i][j] = 0;
            }
        }
        int middle = GRID_WIDTH / 2;
        LAST_GRID[middle][middle] = 1;
        CUR_GRID[middle][middle] = 1;
    }

    private void updateGrid() {
        for (int i=0; i<GRID_WIDTH; i++) {
            for (int j=0; j<GRID_WIDTH; j++) {
                int up = Math.max(i - 1, 0);
                int down = Math.min(i + 1, GRID_WIDTH - 1);
                int right = Math.min(j + 1, GRID_WIDTH - 1);
                int left = Math.max(j - 1, 0);

                int valueUp = LAST_GRID[up][j];
                int valueDown = LAST_GRID[down][j];
                int valueRight = LAST_GRID[i][right];
                int valueLeft = LAST_GRID[i][left];

                if (CUR_GRID[i][j] == 0) {
                    int total = valueUp + valueDown + valueRight + valueLeft;

                    if (total == 1 || total == 4) {
                        CUR_GRID[i][j] = 1;
                    }
                } else {
                    int total = valueUp + valueDown + valueRight + valueLeft;

                    if (total == 2 || total == 3) {
                        CUR_GRID[i][j] = 0;
                    }
                }
            }
        }

        for (int i=0; i<GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                LAST_GRID[i][j] = CUR_GRID[i][j];
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 10) {   // press enter to update states
            updateGrid();
            repaint();
        } else if (code == 32) { // press space to reset
            //reset
            initGrids();
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {

            super.paintComponent(g);
            setBackground(Color.WHITE);

            for (int i=0; i<GRID_WIDTH; i++) {
                for(int j=0; j<GRID_WIDTH; j++) {
                    int c = CUR_GRID[i][j];
                    switch (c) {
                        case 1:
                            g.setColor(Color.BLACK);
                            break;
                        default:
                            g.setColor(Color.WHITE);
                            break;
                    }
                    g.fillRect( 10 + j * 10, 10 + i * 10, 10, 10);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CellularAutomaton());
    }

}
