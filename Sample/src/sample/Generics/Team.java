package sample.Generics;

public abstract class Team
{
	private int points;
	private String teamName;
	
	public Team(int points, String teamName)
	{
		this.points = points;
		this.teamName = teamName;
	}
	
	public String getTeamName() {
		return teamName;
	}

	public int getPoints() {
		return points;
	}
}
 