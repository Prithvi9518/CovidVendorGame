/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Programming_Project;

import java.util.ArrayList;

public class Tournament {
    private String name;
    private double prize;
    private String game;
    private ArrayList<Score> listScores = new ArrayList<>();

    public Tournament(String name, double prize, String game, ArrayList<Score> listScores) {
        this.name = name;
        this.prize = prize;
        this.game = game;
        this.listScores = listScores;
    }

    public Tournament(String name, double prize, String game) {
        this.name = name;
        this.prize = prize;
        this.game = game;
    }

    public Tournament() {
    }

    public String getName() {
        return name;
    }

    public double getPrize() {
        return prize;
    }

    public String getGame() {
        return game;
    }

    public ArrayList<Score> getListScores() {
        return listScores;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setListScores(ArrayList<Score> listScores) {
        this.listScores = listScores;
    }
    
    public double totalValue()
    {
        double totVal=0;
        
        for (int i = 0; i < listScores.size(); i++) {
            totVal = totVal+listScores.get(i).getScore();
        }
        
        return totVal;
    }
    
    public double maxValue()
    {
        double maxVal=0;
        
        for (int i = 0; i < listScores.size(); i++) {
            
            if(listScores.get(i).getScore() >maxVal )
            {
                maxVal = listScores.get(i).getScore();
            }
            
        }
        
        return maxVal;
    }

    public void printScoresByPlayer(String nameIn)
    {
        for (int i = 0; i < listScores.size(); i++) {
            
            if(listScores.get(i).getGamertag() == nameIn)
            {
                System.out.println(listScores.get(i).getScore());
            }
            
        }
        
    }
	
	public void addScore(Score score)
	{
		listScores.add(score);
	}
    
    
    
    
    
    
    @Override
    public String toString() {
        String listScoresString;
        for(int i=0; i < listScores.size(); i++)
        {
            listScoresString = listScores.get(i).toString()+" "; 
        }
        return "Tournament{" + "name=" + name + ", prize=" + prize + ", game=" + game + ", listScores=" + listScores + '}';
    }
                            
    
}

