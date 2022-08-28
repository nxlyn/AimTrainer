package projects.mora.j0927.aimtrainer.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import projects.mora.j0927.aimtrainer.Main;
import projects.mora.j0927.aimtrainer.Storage;

import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;

public class StatsController {
    @FXML
    private Label highestScore, highestAccuracy, averageScore, averageAccuracy;
    @FXML
    private Button returnButton;
    //Back to menu screen
    @FXML
    public void menuReturn() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("aim-menu.fxml"));
        Stage window = (Stage) returnButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 800,800));
    }
    @FXML
    public void deleteStats() {
        try {
            new FileWriter("src/main/resources/projects/mora/j0927/aimtrainer/PlayerData.txt", false).close();
            updateData();
        } catch (IOException e) {
            System.out.println("Clearing player data failed." +e);
        }
    }
    //updateData method will read data file and update Labels with corresponding values
    public void updateData() throws IOException {
        // Instantiating variables for file input
        Scanner inputStream = null;
        ArrayList<Storage> storage = new ArrayList<Storage>();

        //Set scanner input to PlayerData file.
        try{
            inputStream = new Scanner(new FileInputStream("src/main/resources/projects/mora/j0927/aimtrainer/PlayerData.txt"));
        } catch(FileNotFoundException fnfe){}
        //Instantiate new Storage object into ArrayList<Storage> while inputStream has a new line, then close.
        while(inputStream.hasNextLine()){
            storage.add(new Storage(inputStream.nextLine()));
        }
        inputStream.close();
        //Creating int arrays to hold group of values together
        int[] hits = new int[storage.size()], misses = new int[storage.size()], accuracy = new int[storage.size()], score = new int[storage.size()], combo = new int[storage.size()];
        for(int i=0; i<storage.size(); i++){
            hits[i] = Integer.parseInt(storage.get(i).getHits());
            misses[i] = Integer.parseInt(storage.get(i).getMisses());
            accuracy[i] = Integer.parseInt(storage.get(i).getAccuracy().replaceAll("%",""));
            score[i] = Integer.parseInt(storage.get(i).getScore());
        }
        //Calling bestStat with parameters of each unique array's values
        bestStat(hits);
        bestStat(misses);
        highestAccuracy.setText(bestStat(accuracy) +"%");
        highestScore.setText(bestStat(score)+"");
        //Calling calculateAverage to set corresponding stat Label
        averageAccuracy.setText(printAverage(accuracy)+"%");
        averageScore.setText(printAverage(score));
    }
    public String printAverage(int[] array){
        int average = 0;
        if(array.length <= 0){
            return "0";
        } else {
            for(int i=0; i<array.length; i++){
                average += array[i];
            }
            return String.format("%.2f", (double)average/array.length);
        }
    }
    public int bestStat(int[] array){
        int max;
        if(array.length <= 0){
            max = 0;
        } else {
            max = array[0];
            for (int i = 1; i < array.length; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
        }
        return max;
    }
}
