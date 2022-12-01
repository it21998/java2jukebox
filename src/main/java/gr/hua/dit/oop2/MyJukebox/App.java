package gr.hua.dit.oop2.MyJukebox;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.Player.Status;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;

public class App {
	public static void main(String[] args) {

		if (args.length < 1 || args.length > 2) {
			System.out.println("You have to type the song and the strategy (if you want one) after java -jar cmd! Try again!");
			System.exit(0);
		}
		
		Player p = PlayerFactory.getPlayer();
		
		
		try {
			if(args.length==1) {
				InputStream song = new FileInputStream("myplaylist/" + args[0]);
				System.out.println("Now playing: " + args[0]);
				p.play(song);
			}	
			if(args.length==2 && args[1].contains("loop")) {
				while(true) {
				InputStream song2 = new FileInputStream("myplaylist/" + args[0]);
				System.out.println("Now playing: " + args[0] + " with stragety: " + args[1]);
				
					p.play(song2);
					p.stop();
				}
			}	
		} catch (FileNotFoundException e) {
			System.err.println("Song " + args[0] + " not found");
		} catch (PlayerException e) {
			System.err.println("Something's wrong with the player: " + e.getMessage());
		} finally {
			if (p != null)
				p.close();
		}

	}
}