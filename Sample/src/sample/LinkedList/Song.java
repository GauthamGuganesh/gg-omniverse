package sample.LinkedList;

public class Song 
{
	private String title;
	private int durationInMinutes;
	
	private Song(String title, int durationInMinutes)
	{
		this.title = title;
		this.durationInMinutes = durationInMinutes;
	}

	public String getTitle() {
		return title;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}
	
	protected static Song getSong(String title, int duration)
	{
		return new Song(title, duration);
	}
}
