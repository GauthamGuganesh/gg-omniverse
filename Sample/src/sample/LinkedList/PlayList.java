package sample.LinkedList;

import java.util.ArrayList;
import java.util.List;


public class PlayList 
{
	private List<Album> albums;
	
	public PlayList()
	{
		albums = new ArrayList<Album>();
	}
	
	public void addSongToPlayList(String albumName, String title, List<Song> ggPlaylist)
	{
		Album album = getAlbum(albumName);
		if(album != null)
		{
			Song song = album.getSong(title);
			if(song != null) 
				ggPlaylist.add(song);
			else
				System.out.println("Song not added....");
		}
		else
			System.out.println("Album not added.....");
	}
	
	public void addSongToAlbum(String songName, String albumName, int duration)
	{
		Album album = getAlbum(albumName);
		album.addSong(songName, duration);
	}
	
	public void addAlbum(String albumName)
	{
		albums.add(Album.createAlbum(albumName));
	}
	
	private Album getAlbum(String albumName)
	{
		for(int i = 0 ; i < albums.size() ; i++)
		{
			Album album = albums.get(i);
			if(album != null)
			{
				if(album.getAlbumName().equals(albumName))
					return album;
			}
			else
				System.out.println("## Album Not Present");
		}
		
		return null;
	}
	
	public void printAlbumsAndSongs()
	{
		for(int i = 0 ; i < albums.size() ; i++)
		{
			Album album = albums.get(i);
			System.out.println("Album Name :: " + album.getAlbumName());
			List<Song> songList = album.getSongs();
			for(int j = 0 ; j < songList.size() ; j++)
			{
				System.out.println("## " + j+1 + ". " + songList.get(j).getTitle());
				System.out.println(songList.get(j).getDurationInMinutes());
			}
			
			System.out.println("*********************************************");
		}
	}
}
