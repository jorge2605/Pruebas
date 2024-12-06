package componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Stack;
import javax.swing.JPanel;

public class PanelMultiColor extends JPanel {
    
    public Stack<Color> colors;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < colors.size(); i++) {
            g2d.setColor(colors.get(i));
            g2d.fillRect(0, i * height / colors.size(), width, height / colors.size());
        }
    }
    
    public PanelMultiColor(Stack<Color> color){
        colors = color;
    }
}
