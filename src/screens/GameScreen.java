package screens;

import asciiPanel.AsciiPanel;
import persistence.SaveLoader;
import world.Tile;
import world.World;
import world.WorldGenerator;

import java.awt.event.KeyEvent;

public class GameScreen implements Screen {

    public static final int WORLD_SIZE_X = 256;
    public static final int WORLD_SIZE_Y = 256;
    public static final int TILE_LAYERS = 3;

    private int centerX = 128;
    private int centerY = 128;
    private int screenWidth = 64; //out of TERMINAL_WIDTH
    private int screenHeight = 52; //out of TERMINAL_HEIGHT
    private World world;
    private SaveLoader saveLoader;

    public GameScreen(String worldName){
        world = new World(new Tile[TILE_LAYERS][WORLD_SIZE_X][WORLD_SIZE_Y], worldName);
        if(SaveLoader.checkExist(worldName)) {
            saveLoader = new SaveLoader(worldName, world);
            world = saveLoader.load();
        }else {
            createWorld(worldName);
            saveLoader = new SaveLoader(worldName, world);
        }
    }

    private void createWorld(String name){
        world = new WorldGenerator(WORLD_SIZE_X, WORLD_SIZE_Y).generateWorld().getNewWorld(name);
    }

    public int getScrollX() {
        return Math.max(0, Math.min(centerX - screenWidth / 2, world.width() - screenWidth));
    }
    public int getScrollY() {
        return Math.max(0, Math.min(centerY - screenHeight / 2, world.height() - screenHeight));
    }

    private void displayTiles(AsciiPanel terminal, int left, int top) {
        for (int x = 0; x < screenWidth; x++){
            for (int y = 0; y < screenHeight; y++){
                int wx = x + left;
                int wy = y + top;

                try {
                    terminal.write(world.groundGlyph(wx, wy), x, y, world.groundColor(wx, wy));
                    terminal.write(world.aboveGlyph(wx, wy), x, y, world.aboveColor(wx, wy));
                } catch (NullPointerException e){
                    //if array item is null, ignore it
                }
            }
        }
    }

    //check collision in moving direction then move
    private void movePlayer(int mx, int my){
        if(my == -1){
            if (!checkAdjacentGroundTile("north", Tile.MOUNTAIN)) {
                actuallyMove(mx, my);
            }
        }else if(my == 1){
            if (!checkAdjacentGroundTile("south", Tile.MOUNTAIN)) {
                actuallyMove(mx, my);
            }
        }else if(mx == -1){
            if (!checkAdjacentGroundTile("west", Tile.MOUNTAIN)) {
                actuallyMove(mx, my);
            }
        }else if(mx == 1){
            if (!checkAdjacentGroundTile("east", Tile.MOUNTAIN)) {
                actuallyMove(mx, my);
            }
        }
    }
    private void actuallyMove(int mx, int my){
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
        centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
    }

    //requires str is north west east south
    //effect returns if groundTile in adjacent direction matches the given groundTile
    private boolean checkAdjacentGroundTile(String dir, Tile tile){
        switch (dir){
            case "north": return world.groundTile(centerX, centerY - 1).equals(tile);
            case "west": return world.groundTile(centerX - 1, centerY).equals(tile);
            case "east": return world.groundTile(centerX + 1, centerY).equals(tile);
            case "south": return world.groundTile(centerX, centerY + 1).equals(tile);
        }
        return false;
    }

    private Screen saveAndQuit(){ //returns main menu screen
        saveLoader.save();
        return new MainMenu();
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);
        terminal.write('X', centerX - left, centerY - top);

        terminal.write("x-pos: " + centerX, screenWidth + 1, 1);
        terminal.write("y-pos: " + centerY, screenWidth + 1, 2);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_LEFT: movePlayer(-1, 0); break;
            case KeyEvent.VK_RIGHT: movePlayer( 1, 0); break;
            case KeyEvent.VK_UP: movePlayer( 0,-1); break;
            case KeyEvent.VK_DOWN: movePlayer( 0, 1); break;
            case KeyEvent.VK_BACK_SPACE: return saveAndQuit();
        }

        return this;
    }
}