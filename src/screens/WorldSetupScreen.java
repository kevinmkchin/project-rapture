package screens;

import asciiPanel.AsciiPanel;
import persistence.SaveLoader;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WorldSetupScreen implements Screen{

    private String worldName = "new world";

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.writeCenter("=*=*=*=*=*=*=*=*=*=*=*=*=*=", 2, new Color(83, 219, 81));
        terminal.writeCenter("CREATE A NEW WORLD", 4, new Color(83, 219, 81));
        terminal.writeCenter("=*=*=*=*=*=*=*=*=*=*=*=*=*=", 6, new Color(83, 219, 81));

        terminal.writeCenter("Enter the name for your new world:", 10);
        terminal.writeCenter("> " + worldName + " <", 12, new Color(83, 219, 81));
        terminal.writeCenter("[Enter] to begin playing in your new world.", 14);

        terminal.write("[Esc] to return", 2, 51);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        try {
            if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
                return new MainMenu();
            }

            if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                if (!SaveLoader.checkExist(worldName)) {
                    if(worldName.length() > 0) {
                        return new GameScreen(worldName);
                    }else{
                        return this;
                    }
                } else {
                    System.out.println("A world with this name already exists!");
                }
            }

            if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                worldName = worldName.substring(0, worldName.length() - 1);
            } else if (key.getKeyCode() == KeyEvent.VK_SHIFT) {
                //ignore shift
            } else {
                if(worldName.length() <= 24) {
                    worldName += key.getKeyChar();
                }
            }
        }catch(StringIndexOutOfBoundsException e){
            return this;
        }
        return this;
    }

}
