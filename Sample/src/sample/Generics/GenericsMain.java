package sample.Generics;

public class GenericsMain 
{
	public static void main(String[] args) 
	{
		LeagueTable<CricketTeam> leagueTable = new LeagueTable<>();
		
		CricketTeam team1 = new CricketTeam(5, "Pakistan");
		CricketTeam team2 = new CricketTeam(4, "England");
		CricketTeam team3 = new CricketTeam(3, "South Africa");
		CricketTeam team4 = new CricketTeam(2, "Namibia");
		CricketTeam team5 = new CricketTeam(1, "Canada");
		CricketTeam team6 = new CricketTeam(9, "India");
		CricketTeam team7 = new CricketTeam(6, "China");
		CricketTeam team8 = new CricketTeam(7, "Australia");
		
		leagueTable.addTeam(team1);
		leagueTable.addTeam(team2);
		leagueTable.addTeam(team3);
		leagueTable.addTeam(team4);
		leagueTable.addTeam(team5);
		leagueTable.addTeam(team6);
		leagueTable.addTeam(team7);
		leagueTable.addTeam(team8);
		
		leagueTable.sortAndPrintLeagueTable();
	}
}
