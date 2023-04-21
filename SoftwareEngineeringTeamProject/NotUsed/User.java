package NotUsed;

public class User {
	
	String username;
	int score;
	
	public User(int score, String username)
	{
		this.score = score;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getScore() {
		return score;
	}

	public void modScore(int addition) {
		this.score = this.score + addition;
	}
	
	
}
