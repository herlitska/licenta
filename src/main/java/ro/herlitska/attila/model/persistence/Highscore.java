package ro.herlitska.attila.model.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "highscore")
public class Highscore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
    @Column(name = "idhighscore", unique = true)
    private int idHighScore;

    @Column(name = "player_name", nullable = false)
    private String playerName;

    @Column(name = "time", nullable = false)
    private String time;
    
    @Column(name = "zombies_killed", nullable = false)
    private int zombiesKilled;

	public int getIdHighScore() {
		return idHighScore;
	}

	public void setIdHighScore(int idHighScore) {
		this.idHighScore = idHighScore;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getZombiesKilled() {
		return zombiesKilled;
	}

	public void setZombiesKilled(int zombiesKilled) {
		this.zombiesKilled = zombiesKilled;
	}
	

}
