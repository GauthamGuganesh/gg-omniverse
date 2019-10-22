package sample.Generics;

import java.util.ArrayList;
import java.util.Collections;

public class LeagueTable<T extends Team & Comparable<T>> 
{
	ArrayList<T> leagueTable;

	public LeagueTable()
	{
		leagueTable = new ArrayList<>();
	}
	
	public void addTeam(T team)
	{
		leagueTable.add(team);
	}
	
	public void sortAndPrintLeagueTable()
	{
		Collections.sort(leagueTable);
		
		for(T element : leagueTable)
		{
			System.out.println("## Team Name :: " + element.getTeamName());
			System.out.println("## Team points :: " + element.getPoints());
			System.out.println("");
		}
	}
}
