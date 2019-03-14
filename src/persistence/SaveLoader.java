package persistence;

import screens.GameScreen;
import world.Tile;
import world.World;

import java.io.*;

public class SaveLoader {

    public static final String SAVE_FOLDER = "./saves/";
    private static File saveFile;
    private World world;

    public SaveLoader(String worldName, World world){
        String name = worldName.replaceAll(" ", "_");
        saveFile = new File(SAVE_FOLDER + name + ".txt");
        this.world = world;
    }

    public void save(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            Tile[][][] tiles;

            saveFile.createNewFile();
            tiles = world.getTiles();
            writeLine(writer, tiles, 0);
            writer.write("\n");
            writeLine(writer, tiles, 1);
            writer.write("\n");
            writeLine(writer, tiles, 2);
            writer.flush();

        } catch (IOException e) {
            System.out.println("Couldn't save");
            e.printStackTrace();
        }
    }

    //REQUIRES: saveFile exists
    public World load(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(saveFile));
            Tile[][][] tiles = world.getTiles();
            int layer = 0;
            String line = reader.readLine();

            while(layer < GameScreen.TILE_LAYERS){ //reads each layer line once
                String[] tileNames = line.split(";");
                for(int i = 0; i < world.width(); i++){
                    for(int j = 0; j < world.height(); j++){
                        String tileName = tileNames[i * world.width() + j];
                        if(!tileName.equals("NULL")){
                            tiles[layer][i][j] = Tile.valueOf(tileName);
                        }
                    }
                }
                line = reader.readLine();
                layer++;
            }

            return new World(tiles, world.getName());

        } catch (IOException e) {
            System.out.println("Couldn't load");
            e.printStackTrace();
            return null;
        }

    }

    public static boolean checkExist(String worldName){
        String name = worldName.replaceAll(" ", "_");
        saveFile = new File(SAVE_FOLDER + name + ".txt");
        try {
            FileReader testReader = new FileReader(saveFile);
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }

    private void writeLine(BufferedWriter writer, Tile[][][] tiles, int layer) throws IOException {
        for(int i = 0; i < world.width(); i++){
            for(int j = 0; j < world.height(); j++){
                try {
                    Tile t = tiles[layer][i][j];
                    writer.write(t.toString() + ";");
                }catch(NullPointerException e){
                    writer.write("NULL;");
                }
            }
        }
    }

}
