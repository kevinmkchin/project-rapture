package world;

import java.awt.*;

public class World {

    private Tile[][] groundTiles;
    private Tile[][] aboveTiles;
    //list of zone tiles


    private int width;
    public int width() { return width; }

    private int height;
    public int height() { return height; }

    public World(Tile[][] groundTiles, Tile[][] aboveTiles){
        this.groundTiles = groundTiles;
        this.aboveTiles = aboveTiles;
        this.width = this.groundTiles.length;
        this.height = this.groundTiles[0].length;
    }

    public Tile groundTile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return groundTiles[x][y];
    }
    public Tile aboveTile(int x, int y){
        if (x < 0 || x >= width || y < 0 || y >= height)
            return Tile.BOUNDS;
        else
            return aboveTiles[x][y];
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
}
