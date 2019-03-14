package screens;

import asciiPanel.AsciiPanel;
import persistence.SaveLoader;

import java.awt.event.KeyEvent;

public class WorldSetupScreen implements Screen{

    private String worldName = "";

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Enter the name for your new world:", 2, 2);
        terminal.write(worldName,2,3);
        terminal.write("[enter] to begin playing in your new world.", 2, 4);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
            return new MainMenu();
        }

        if(key.getKeyCode() == KeyEvent.VK_ENTER){
            if(!SaveLoader.checkExist(worldName)) {
                return new GameScreen(worldName);
            }else{
                System.out.println("A world with this name already exists!");
            }
        }

        try {
            if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                worldName = worldName.substring(0, worldName.length() - 1);
            } else {
                worldName += key.getKeyChar();
            }
            return this;
        }catch(IllegalArgumentException e){
            return this;
        }
    }

}
