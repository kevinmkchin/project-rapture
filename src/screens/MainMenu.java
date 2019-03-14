package screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class MainMenu implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("SETTLERS OF THE", 24, 8);
        terminal.write("88\"\"Yb    db    88\"\"Yb 888888 88   88 88\"\"Yb 888888",22,10);
        terminal.write("88__dP   dPYb   88__dP   88   88   88 88__dP 88__",22,11);
        terminal.write("88\"Yb   dP__Yb  88\"\"\"    88   Y8   8P 88\"Yb  88\"\"",22,12);
        terminal.write("88  Yb dP\"\"\"\"Yb 88       88   `YbodP' 88  Yb 888888",22,13);
        terminal.writeCenter("-- [N]ew World  --", 22);
        terminal.writeCenter("-- [L]oad World --", 24);
        terminal.writeCenter("-- [O]ptions --", 26);
        terminal.writeCenter("-- [Esc] to Exit --", 28);

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_N: return new WorldSetupScreen();
            case KeyEvent.VK_L: return new LoadWorldScreen();
            case KeyEvent.VK_ESCAPE: System.exit(0);
        }
        return this;
    }
}
