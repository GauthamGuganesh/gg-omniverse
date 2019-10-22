package sample.Generics;

public class CricketTeam extends Team implements Comparable<CricketTeam>
{
	public CricketTeam(int points, String teamName) 
	{
		super(points, teamName);
	}

	@Override // Implementing our own Comparable interface compareTo method 
	// will help us in sorting. Collections framework will use this method for comparing. Collections.sort
	public int compareTo(CricketTeam team)
	{
		int points = team.getPoints();
		if(this.getPoints() > points)
			return -1;
		else if (this.getPoints() < points)
			return 1;
		else 
			return 0;
	}
}
