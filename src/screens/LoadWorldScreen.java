package screens;

import asciiPanel.AsciiPanel;
import persistence.SaveLoader;

import java.awt.event.KeyEvent;
import java.io.File;

public class LoadWorldScreen implements Screen {

    final File folder = new File(SaveLoader.SAVE_FOLDER.substring(0, SaveLoader.SAVE_FOLDER.length() - 1));

    private int yLocStart = 4;
    private int cursorLoc = yLocStart;
    private int yLoc = yLocStart;
    private int yIncrement = 2;
    private int numOfFiles = 0;

    private void moveCursorUp(){
        if(cursorLoc > yLocStart){
            cursorLoc -= yIncrement;
        }
    }

    private void moveCursorDown(){
        if(cursorLoc < yIncrement * (numOfFiles + 1)){
            cursorLoc += yIncrement;
        }
    }

    private GameScreen loadSelection(){
        int index = (cursorLoc - yLocStart) / yIncrement;
        File[] files = folder.listFiles();
        String name = files[index].getName().substring(0, files[index].getName().length()-4);

        return new GameScreen(name);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        yLoc = yLocStart;
        numOfFiles = 0;
        for (final File fileEntry : folder.listFiles()) {
            String name = fileEntry.getName().substring(0, fileEntry.getName().length()-4);
            terminal.write(name, 2, yLoc);
            yLoc += 2;
            numOfFiles++;
        }
        if(numOfFiles > 0){
            terminal.write(">", 1, cursorLoc);
        }else{
            terminal.write("There are no saved worlds!", 1, 10);
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_ESCAPE: return new MainMenu();
            case KeyEvent.VK_UP: moveCursorUp(); break;
            case KeyEvent.VK_DOWN: moveCursorDown(); break;
            case KeyEvent.VK_ENTER: return loadSelection();
        }

        return this;
    }

}
