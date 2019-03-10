package screens;

import asciiPanel.AsciiPanel;
import world.Tile;
import world.World;
import world.WorldBuilder;

import java.awt.event.KeyEvent;

public class GameScreen implements Screen {

    private int centerX;
    private int centerY;
    private int screenWidth;
    private int screenHeight;
    private World world;

    public GameScreen(){
        screenWidth = 48;
        screenHeight = 36;
        createWorld();
    }

    private void createWorld(){
        world = new WorldBuilder(80, 80).makeCaves().build();
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

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
    }

    private void movePlayer(int mx, int my){
        if(my == -1){
            if (!checkAdjacentTile("north", Tile.WALL)) {
                actuallyMove(mx, my);
            }
        }else if(my == 1){
            if (!checkAdjacentTile("south", Tile.WALL)) {
                actuallyMove(mx, my);
            }
        }else if(mx == -1){
            if (!checkAdjacentTile("west", Tile.WALL)) {
                actuallyMove(mx, my);
            }
        }else if(mx == 1){
            if (!checkAdjacentTile("east", Tile.WALL)) {
                actuallyMove(mx, my);
            }
        }
    }

    private void actuallyMove(int mx, int my){
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
        centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
    }

    //requires str is north west east south
    //effect returns if tile in adjacent direction matches the given tile
    private boolean checkAdjacentTile(String str, Tile tile){
        switch (str){
            case "north": return world.tile(centerX, centerY - 1).equals(tile);
            case "west": return world.tile(centerX - 1, centerY).equals(tile);
            case "east": return world.tile(centerX + 1, centerY).equals(tile);
            case "south": return world.tile(centerX, centerY + 1).equals(tile);
        }
        return false;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        int left = getScrollX();
        int top = getScrollY();

        displayTiles(terminal, left, top);

        terminal.write('@', centerX - left, centerY - top);
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        switch (key.getKeyCode()){
            case KeyEvent.VK_LEFT: movePlayer(-1, 0); break;
            case KeyEvent.VK_RIGHT: movePlayer( 1, 0); break;
            case KeyEvent.VK_UP: movePlayer( 0,-1); break;
            case KeyEvent.VK_DOWN: movePlayer( 0, 1); break;
        }

        return this;
    }
}