package world;

import screens.GameScreen;

public class WorldGenerator {

    private int width;
    private int height;
    private Tile[][][] tiles;

    private float[][] noiseArray;

    public WorldGenerator(int width, int height){
        this.width = width;
        this.height = height;
        this.tiles = new Tile[GameScreen.TILE_LAYERS][width][height];
    }

    public World getNewWorld(String name){
        return new World(tiles, name);
    }

    public WorldGenerator generateWorld(){
        GenerationTools gt = new GenerationTools();

        // 1. Ground/Floor tiles
        noiseArray = gt.generatePerlinNoise(width, height, 0.05f);
        assignWaterTiles();
        // 2. Rich soil
        noiseArray = gt.generatePerlinNoise(width, height, 0.1f);
        assignRichSoilTiles();
        // 3. Trees (more to come here)
        assignTrees();
        // 4. Mountains
        noiseArray = gt.generatePerlinNoise(width, height, 0.01f); //big mountains
        assignMountainTiles();
        noiseArray = gt.generatePerlinNoise(width, height, 0.075f); //small mountains
        assignMountainTiles();

        return this;
    }

    private void assignWaterTiles(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                float noiseVal = noiseArray[i][j];
                if(noiseVal <= 0.35){
                    tiles[0][i][j] = Tile.WATER;
                }else if(noiseVal <= 0.38){
                    tiles[0][i][j] = Tile.SAND;
                }else if(noiseVal <= 1){
                    tiles[0][i][j] = Tile.SOIL;
                }
            }
        }
    }

    private void assignRichSoilTiles() {
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                float noiseVal = noiseArray[i][j];
                if(noiseVal >= 0.8){
                    if(tiles[0][i][j].equals(Tile.SOIL))
                        tiles[0][i][j] = Tile.RICH_SOIL;
                }
            }
        }
    }

    private void assignTrees(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(Math.random() < 0.12){
                    if(!tiles[0][i][j].equals(Tile.WATER) && !tiles[0][i][j].equals(Tile.SAND)){
                        tiles[1][i][j] = Tile.TREE;
                    }
                }
            }
        }
    }

    private void assignMountainTiles(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                float noiseVal = noiseArray[i][j];
                if(noiseVal >= 0.70){
                    if(tiles[0][i][j].equals(Tile.WATER)){
                        continue;
                    }
                    tiles[0][i][j] = Tile.SOIL;
                    tiles[1][i][j] = Tile.MOUNTAIN;
                }
            }
        }
    }

}
