package screens;

import asciiPanel.AsciiPanel;
import persistence.SaveLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;

public class LoadWorldScreen implements Screen {

    final File folder = new File(SaveLoader.SAVE_FOLDER.substring(0, SaveLoader.SAVE_FOLDER.length() - 1));

    private int yLocStart = 12;
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

    private void displayFileInfo(AsciiPanel terminal, File file){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String lastModified = "Last Saved: " + sdf.format(file.lastModified());
        String name = "World Name: " + file.getName()
                .substring(0, file.getName().length()-4)
                .replaceAll("_", " ");

        terminal.write(name, 44, 13, new Color(83, 219, 81));
        terminal.write(lastModified, 44, 15, new Color(83, 219, 81));
    }

    private File getSelectedFile(){
        int index = (cursorLoc - yLocStart) / yIncrement;
        File[] files = folder.listFiles();

        return files[index];
    }

    private GameScreen loadSelection(){
        int index = (cursorLoc - yLocStart) / yIncrement;
        File[] files = folder.listFiles();
        String name = files[index].getName().substring(0, files[index].getName().length()-4);

        return new GameScreen(name);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        terminal.writeCenter("=*=*=*=*=*=*=*=*=*=*=*=*=*=", 2, new Color(83, 219, 81));
        terminal.writeCenter("LOAD AN EXISTING WORLD", 4, new Color(83, 219, 81));
        terminal.writeCenter("=*=*=*=*=*=*=*=*=*=*=*=*=*=", 6, new Color(83, 219, 81));
        terminal.writeCenter("Up and Down arrows to select, [Enter] to load.", 8);
        terminal.write("[Esc] to return", 2, 51);
        try {
            displayFileInfo(terminal, getSelectedFile());
        }catch(ArrayIndexOutOfBoundsException e){
            //do nothing
        }

        yLoc = yLocStart;
        numOfFiles = 0;

        for (final File fileEntry : folder.listFiles()) {
            String name = fileEntry.getName().substring(0, fileEntry.getName().length()-4).replaceAll("_", " ");
            terminal.write(name, 6, yLoc);
            yLoc += 2;
            numOfFiles++;
        }
        if(numOfFiles > 0){
            terminal.write(">", 4, cursorLoc);
        }else{
            terminal.write("There are no saved worlds!", 5, 12, new Color(83, 219, 81));
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
