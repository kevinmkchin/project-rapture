package world;

import tools.PerlinNoiseGenerator;

public class GenerationTools {

    //RETURNS A FLOAT ARRAY width * height OF PERLIN NOISE AS GRADIENT OF [0,1]
    public float[][] generatePerlinNoise(int width, int height, float perlinScale){
        float[][] noiseArray = new float[width][height];
        PerlinNoiseGenerator perlin = new PerlinNoiseGenerator();
        boolean smallBigInitialized = false;
        float smallestSoFar = 0;
        float biggestSoFar = 0;

        //Generate perlin noiseArray
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                noiseArray[i][j] = perlin.noise2(i * perlinScale, j * perlinScale);
                float currentVal = noiseArray[i][j];
                if(smallBigInitialized){
                    if(currentVal < smallestSoFar){
                        smallestSoFar = currentVal;
                    }
                    if(currentVal > biggestSoFar){
                        biggestSoFar = currentVal;
                    }
                }else{
                    smallestSoFar = currentVal;
                    biggestSoFar = currentVal;
                    smallBigInitialized = true;
                }
            }
        }
        //Find max value after setting floor to 0
        float modifiedMax = biggestSoFar + (-smallestSoFar);
        //Make values a gradient between 0 to 1
        for(int i = 0; i < noiseArray.length; i++){
            for(int j = 0; j < noiseArray[0].length; j++){
                noiseArray[i][j] += -smallestSoFar; //set floor to 0
                noiseArray[i][j] = noiseArray[i][j] / modifiedMax; //divide all values by max so 0 <= all values <= 1
            }
        }

        return noiseArray;
    }

//    public WorldBuilder generateChunks(Tile chunkTile, Tile baseTile){
//        int numWaters = ThreadLocalRandom.current().nextInt(20,30);
//        int actualNum = 0;
//
//        while(actualNum < numWaters){
//            int radius = ThreadLocalRandom.current().nextInt(7,13);
//            int ox = ThreadLocalRandom.current().nextInt(10,width-10);
//            int oy = ThreadLocalRandom.current().nextInt(10,height-10);
//
//            for(int i = 0; i < radius; i++){
//                for(int h = 0; h < radius - i; h++){
//
//                    tiles[ox + h][oy + i] = chunkTile;
//                    tiles[ox - h][oy + i] = chunkTile;
//                    tiles[ox + h][oy - i] = chunkTile;
//                    tiles[ox - h][oy - i] = chunkTile;
//
//                    if(h == radius - i - 1){
//
//                        if(Math.random() < 0.7){
//                            tiles[ox + h][oy + i] = baseTile;
//                            if(Math.random() < 0.5){
//                                tiles[ox + h - 1][oy + i] = baseTile;
//                            }
//                        }
//
//                        if(Math.random() < 0.7){
//                            tiles[ox - h][oy + i] = baseTile;
//                            if(Math.random() < 0.5){
//                                tiles[ox - h + 1][oy + i] = baseTile;
//                            }
//                        }
//
//                        if(Math.random() < 0.7){
//                            tiles[ox + h][oy - i] = baseTile;
//                            if(Math.random() < 0.5){
//                                tiles[ox + h][oy - i + 1] = baseTile;
//                            }
//                        }
//
//                        if(Math.random() < 0.7){
//                            tiles[ox - h][oy - i] = baseTile;
//                            if(Math.random() < 0.5){
//                                tiles[ox - h][oy - i + 1] = baseTile;
//                            }
//                        }
//                    }
//                }
//            }
//            actualNum++;
//        }
//        return this;
//    }

}
