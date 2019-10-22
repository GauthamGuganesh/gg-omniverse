package sample;

import java.util.Collections;

/* Java Collection classes are fail-fast which means that if the 
 * Collection will be changed while some thread is traversing over it using iterator,
 *  the iterator.next() will throw a ConcurrentModificationException.

This situation can come in case of multithreaded as well as single threaded environment. */

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import sample.LinkedList.PlayList;
import sample.LinkedList.Song;

public class PlayListApplication 
{
	public static void main(String[] args) 
	{
		List<Song> ggPlaylist = Collections.synchronizedList(new LinkedList<Song>());
		
		PlayList playList = new PlayList();
		playList.addAlbum("Tamil");
		playList.addAlbum("English");
		
		playList.addSongToAlbum("Petta Paraak", "Tamil", 4);
		playList.addSongToAlbum("Marana Mass", "Tamil", 5);
		playList.addSongToAlbum("Urvasi", "Tamil", 3);
		playList.addSongToAlbum("Romeo aatam Potaa", "Tamil", 6);
		
		playList.addSongToAlbum("Billie Jean", "English", 5);
		playList.addSongToAlbum("Bad", "English", 6);
		playList.addSongToAlbum("Earth Song", "English", 3);
		playList.addSongToAlbum("Smooth Criminal", "English", 4);
		
		playList.addSongToPlayList("Tamil", "Marana Mass", ggPlaylist);
		playList.addSongToPlayList("Tamil", "Urvasi", ggPlaylist);
		playList.addSongToPlayList("English", "Smooth Criminal", ggPlaylist);
		playList.addSongToPlayList("English", "Billie Jean", ggPlaylist);
		
		playList.printAlbumsAndSongs();
		
		play(ggPlaylist);
	}

	private static void play(List<Song> ggPlaylist) 
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Choice :: ");
		boolean flag = true;
		boolean isForward = true;
		ListIterator<Song> playListIterator = ggPlaylist.listIterator();
		
		while(flag)
		{
			printOptions();
			int choice = scanner.nextInt();
			
			switch(choice)
			{
				case 1:
					flag = false;
					break;
				case 2:
					if(!isForward)
					{
						if(playListIterator.hasNext())
							playListIterator.next();
					    isForward = true;
					}
					
					if(playListIterator.hasNext())
					{
						Song nextSong = playListIterator.next();
						System.out.println("Playing Song ... :: " + nextSong.getTitle() + "## " + nextSong.getDurationInMinutes());
						isForward = true;
					}
					else 
					{
						System.out.println("End Of PlayList#############");
						isForward = false;
					}
					break;
					
				case 3:
					if(isForward)
					{
						if(playListIterator.hasPrevious())
							playListIterator.previous();
					    isForward = false;
					}
					
					if(playListIterator.hasPrevious())
					{
						Song previousSong = playListIterator.previous();
						System.out.println("Playing Song ... :: " + previousSong.getTitle() + "## " + previousSong.getDurationInMinutes());
						isForward = false;
					}
					else 
					{
						System.out.println("Start Of PlayList#############");
						isForward = true;
					}
					break;
					
				case 4:
					isForward = currentSong(isForward, playListIterator);
					break; 
				case 5:
					playListIterator.remove();
					currentSong(isForward, playListIterator);
					break;
				case 6:
					printPlayList(ggPlaylist);
					break;
			}
		}
		scanner.close();
	}

	private static boolean currentSong(boolean isForward, ListIterator<Song> playListIterator) {
		if(isForward) 
		{
			isForward = false;
			Song previous = playListIterator.previous();
			System.out.println("Playing song....:: " + previous.getTitle());
		}
		else
		{
			isForward = true;
			Song next = playListIterator.next();
			System.out.println("Playing song....:: " + next.getTitle());
		}
		return isForward;
	}

	private static void printOptions() 
	{
		System.out.println("1. Quit \n ");
		System.out.println("2. Next song \n ");
		System.out.println("3. Previous Song \n ");
		System.out.println("4. Play Current Song \n ");
		System.out.println("5. Remove Song \n ");
	}
	
	public static void printPlayList(List<Song> ggPlaylist)
	{
		ListIterator<Song> i = ggPlaylist.listIterator();
		System.out.println("## Songs in playlist.....");
		while(i.hasNext())
			System.out.println(i.next().getTitle());
	}
}
