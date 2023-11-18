package execute;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Positions {
    protected String position;
    protected String positionId;

    public Positions(){}

    public void filesProcessing(Path path, boolean registered){
        try{
            if (!Files.exists(path)){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
                writer.write("Position ID\tPosition Title");
                writer.newLine();
                writer.close();
            }

            if (!registered){
                BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), true));
                writer.write(getPositionId() + "\t" + getPosition());
                writer.newLine();
                writer.close();
            }

        }catch (IOException ex){
            System.out.println("An error has occurred. Operations could not be completed.\n" + ex);
        }
    }

    public boolean validation(Path path){
        boolean positionRegistered = false;

        try{
            if (Files.exists(path)) {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()));
                String line;
                boolean headerSkipped = false;
                String index = getPositionId();

                while ((line = bufferedReader.readLine()) != null) {
                    if (!headerSkipped){
                        headerSkipped = true;
                        continue;
                    }
                    String[] positions = line.split("\t");

                    if (positions.length == 2 && positions[0].equals(index)) {
                        positionRegistered = true;
                    }
                }
            }
        }catch (IOException e){
            System.out.println("An error has occurred.\n" + e);
        }

        return positionRegistered;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
}
