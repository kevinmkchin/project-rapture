import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import screens.MainMenu;
import screens.Screen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ApplicationMain extends JFrame implements KeyListener {

    private final int TERMINAL_WIDTH = 64;
    private final int TERMINAL_HEIGHT = 36;

    private AsciiPanel terminal;
    private Screen screen;

    public ApplicationMain(){
        super();
        terminal = new AsciiPanel(TERMINAL_WIDTH, TERMINAL_HEIGHT, AsciiFont.CP437_16x16);
        add(terminal);
        pack();
        screen = new MainMenu();
        addKeyListener(this);
        repaint();
    }

    public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setMinimumSize(app.getSize());
        app.setLocationRelativeTo(null);
        app.setVisible(true);
    }

}
