package world;

import java.awt.*;

public class World {

    //1D is layer, 2D is horizontal, 3D is vertical
    private Tile[][][] tiles;

    private String name;

    private int width;
    public int width() { return width; }

    private int height;
    public int height() { return height; }

    public World(Tile[][][] tiles, String name){
        this.tiles = tiles;
        this.name = name;
        this.width = this.tiles[0].length;
        this.height = this.tiles[0][0].length;
    }

    public Tile groundTile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[0][x][y];
    }
    public Tile aboveTile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return tiles[1][x][y];
    }
    public char groundGlyph(int x, int y){
        return groundTile(x, y).glyph();
    }
    public Color groundColor(int x, int y){
        return groundTile(x, y).color();
    }
    public char aboveGlyph(int x, int y){
        return aboveTile(x, y).glyph();
    }
    public Color aboveColor(int x, int y){
        return aboveTile(x, y).color();
    }

    public String getName() {
        return name;
    }

    public Tile[][][] getTiles() {
        return tiles;
    }
}
