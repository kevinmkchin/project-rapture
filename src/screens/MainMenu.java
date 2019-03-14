package screens;

import asciiPanel.AsciiPanel;

import java.awt.event.KeyEvent;

public class MainMenu implements Screen {

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("SETTLERS OF THE", 10, 2);
        terminal.write("88\"\"Yb    db    88\"\"Yb 888888 88   88 88\"\"Yb 888888",6,4);
        terminal.write("88__dP   dPYb   88__dP   88   88   88 88__dP 88__",6,5);
        terminal.write("88\"Yb   dP__Yb  88\"\"\"    88   Y8   8P 88\"Yb  88\"\"",6,6);
        terminal.write("88  Yb dP\"\"\"\"Yb 88       88   `YbodP' 88  Yb 888888",6,7);
        terminal.writeCenter("-- press [enter] to start --", 22);
        terminal.writeCenter("-- [L]oad World --", 24);
        terminal.writeCenter("-- [O]ptions --", 26);

    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ENTER: return new WorldSetupScreen();
            case KeyEvent.VK_L: return new LoadWorldScreen();
        }
        return this;
    }
}
