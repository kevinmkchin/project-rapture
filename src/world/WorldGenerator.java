package world;

public class WorldGenerator {

    private int width;
    private int height;
    private Tile[][] groundTiles;
    private Tile[][] aboveTiles;

    private float[][] waterArray;
    private float[][] mountainArray;

    public WorldGenerator(int width, int height){
        this.width = width;
        this.height = height;
        this.groundTiles = new Tile[width][height];
        this.aboveTiles = new Tile[width][height];
    }

    public World getNewWorld(){
        return new World(groundTiles, aboveTiles);
    }

    public WorldGenerator generateWorld(){
        GenerationTools gt = new GenerationTools();
        // 1.
        waterArray = gt.generatePerlinNoise(width, height, 0.05f);
        assignWaterTiles();
        // 2.
        assignTrees();
        // 3.
        mountainArray = gt.generatePerlinNoise(width, height, 0.01f); //big mountains
        assignMountainTiles();
        mountainArray = gt.generatePerlinNoise(width, height, 0.075f); //small mountains
        assignMountainTiles();
        //generateMountainTiles();

        return this;
    }

    private void assignWaterTiles(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                float noiseVal = waterArray[i][j];
                if(noiseVal <= 0.35){
                    groundTiles[i][j] = Tile.WATER;
                }else if(noiseVal <= 0.38){
                    groundTiles[i][j] = Tile.SAND;
                }else if(noiseVal <= 1){
                    groundTiles[i][j] = Tile.SOIL;
                }
            }
        }
    }

    private void assignTrees(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                if(Math.random() < 0.2){
                    if(!groundTiles[i][j].equals(Tile.WATER) && !groundTiles[i][j].equals(Tile.SAND)){
                        aboveTiles[i][j] = Tile.TREE;
                    }
                }
            }
        }
    }

    private void assignMountainTiles(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                float noiseVal = mountainArray[i][j];
                if(noiseVal >= 0.60){
                    if(groundTiles[i][j].equals(Tile.WATER)){
                        continue;
                    }
                    groundTiles[i][j] = Tile.SOIL;
                    aboveTiles[i][j] = Tile.MOUNTAIN;
                }
            }
        }
    }

}
