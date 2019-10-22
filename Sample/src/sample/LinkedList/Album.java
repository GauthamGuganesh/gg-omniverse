package sample.LinkedList;

import java.util.ArrayList;
import java.util.List;

public class Album
{
	private String albumName;
	private SongList songList;
	
	public interface sample
	{
		default void sampleMethod()
		{
			System.out.println("Hello");
		}
		
		static void sample2()
		{
			System.out.println("gello");
		}
	}
	
	private Album(String name)
	{
		this.albumName = name;
	}
	
	private void setSongList(SongList songList) {
		this.songList = songList;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public Song getSong(String title) 
	{
		return songList.getSong(title);
	}
	
	public List<Song> getSongs()
	{
		return songList.getSongs();
	}

	private class SongList
	{
		private List<Song> songs;
		
		private SongList()
		{
			songs = new ArrayList<Song>();
		}

		private List<Song> getSongs() {
			return songs;
		}
		
		private Song getSong(String title)
		{
			for(int i = 0 ; i < songs.size() ; i++)
			{
				Song song = songs.get(i);
				if(song != null)
					if(song.getTitle().equals(title))
						return song;
			}
			
			return null;
		}
		
		private void addSong(String songName, int duration)
		{
			songs.add(Song.getSong(songName, duration));
		}
	}
	
	protected void addSong(String songName, int duration)
	{
		songList.addSong(songName, duration);
	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	protected static Album createAlbum(String albumName)
	{
		Album album = new Album(albumName);
		album.setSongList(album.new SongList());
		return album;
	}
}
