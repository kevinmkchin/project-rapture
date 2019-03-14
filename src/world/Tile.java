package world;

import asciiPanel.AsciiPanel;

import java.awt.Color;

public enum Tile {

    TREE((char)157, new Color(134, 67, 29)),
    SOIL((char)39, new Color(0, 177, 64)),
    RICH_SOIL((char)34, new Color(0, 113, 38)),
    SAND((char)176, new Color(177, 175, 84)),
    WATER((char)247, new Color(47, 108, 167)),

    MOUNTAIN((char)30, new Color(66, 74, 75)), //remove later since we not using wall
    BOUNDS('X', AsciiPanel.brightBlack);

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    //private Entity entity THIS IS THE LOGIC ATTACHED TO EACH TILE

    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
    }

}
