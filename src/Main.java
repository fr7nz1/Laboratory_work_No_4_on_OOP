import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame implements ActionListener {
    private Color selectedColor = Color.BLACK;
    private int selectedThickness = 1;

    private JButton colorButton;
    private JButton thicknessButton;
    private DrawingPanel drawingPanel;

    private int prevX, prevY;
    private int currX, currY;

    public Main() {
        setTitle("Drawing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // действия с кнопками
        colorButton = new JButton("Color");
        thicknessButton = new JButton("Thickness");

        // Add action listeners to the buttons
        colorButton.addActionListener(this);
        thicknessButton.addActionListener(this);

        // создание панели рисования
        drawingPanel = new DrawingPanel();

        // создание кнопок на панели
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(colorButton);
        buttonPanel.add(thicknessButton);

        // панель кнопок и панель рисования
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(buttonPanel, BorderLayout.NORTH);
        contentPane.add(drawingPanel, BorderLayout.CENTER);

        // отслеживания движения мыши
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currX = e.getX();
                currY = e.getY();
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                prevX = currX;
                prevY = currY;
                currX = e.getX();
                currY = e.getY();
                drawingPanel.repaint();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == colorButton) {
            // диалоговое окно выбора цвета
            selectedColor = JColorChooser.showDialog(this, "Choose Color", selectedColor);
        } else if (e.getSource() == thicknessButton) {
            // диалоговое окно выбора толщины
            String input = JOptionPane.showInputDialog(this, "Enter Thickness");
            try {
                selectedThickness = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                // In case of incorrect input, use default value (1)
                selectedThickness = 1;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main app = new Main();
                app.setVisible(true);
            }
        });
    }

    // панель для рисования
    private class DrawingPanel extends JPanel {

        public DrawingPanel() {
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(selectedColor);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(selectedThickness));
            g.drawLine(prevX, prevY, currX, currY);
        }
    }
}