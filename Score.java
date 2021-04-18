/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Programming_Project;

/**
 *
 * @author niall
 */
public class Score {
    private String gamertag;
    private int age;
    private int score;
	private int level;

    public Score(String gamertag, int age, int score, int level) {
        this.gamertag = gamertag;
        this.age = age;
        this.score = score;
		this.level = level;
    }

    public Score() {
    }

    public String getGamertag() {
        return gamertag;
    }

    public int getAge() {
        return age;
    }

    public int getScore() {
        return score;
    }

	public int getLevel() {
		return level;
	}
	

    public void setGamertag(String gamertag) {
        this.gamertag = gamertag;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setScore(int score) {
        this.score = score;
    }

	public void setLevel(int level) {
		this.level = level;
	}
	

    @Override
    public String toString() {
        return "<Score: gamertag= " +gamertag+", age= "+age+", score= "+score+" >";
    }
    
    
}

