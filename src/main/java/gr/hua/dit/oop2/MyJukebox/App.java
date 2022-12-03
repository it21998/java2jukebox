package gr.hua.dit.oop2.MyJukebox;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import gr.hua.dit.oop2.musicplayer.Player;
import gr.hua.dit.oop2.musicplayer.PlayerException;
import gr.hua.dit.oop2.musicplayer.PlayerFactory;

public class App {
	public static void main(String[] args) throws IOException {
		// Check if user types more/less commands than expected and show him message for
		// help!
		if (args.length < 1 || args.length > 2) {
			System.out.println(
					"You have to type the song/file and the strategy (if you want one) after java -jar <<jukebox>>.jar cmd! Try again!");
			System.exit(0);
		}
		// Check strategy of user.Must be either loop or random or order!
		if (args.length == 2) {
			if (!args[1].equals("loop") && !args[1].equals("random") && !args[1].equals("order")) {
				System.out.println("The default strategies are : loop / random / order! Try again!");
				System.exit(0);

			}
		}
		// Set player
		Player p = PlayerFactory.getPlayer();

		try {
			// first case:user only types song/playlist file
			if (args.length == 1) {
				// case song
				if (args[0].contains(".mp3")) {
					InputStream song = new FileInputStream(args[0]);
					System.out.println("Now playing: " + args[0]);
					p.play(song);
					p.stop();
					System.out.println("Song: " + args[0] + " just ended!");

				} else {
					// case m3ufile
					try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
						String line;
						while ((line = br.readLine()) != null) {
							if (line.contains("mp3")) {
								InputStream song = new FileInputStream(line);
								System.out.println("Now playing: " + line);
								p.play(song);
								p.stop();
								System.out.println("Song: " + line + " just ended!");

							}
						}
					}
				}
			}
			// second case:user types song/playlist file and strategy loop
			else if (args.length == 2 && args[1].equals("loop")) {
				while (true) {
					if (args[0].contains(".mp3")) {
						// case song
						InputStream song = new FileInputStream(args[0]);
						System.out.println("Now playing: " + args[0] + " with strategy: " + args[1]);
						p.play(song);
						p.stop();
						System.out.println("Song: " + args[0] + " just ended!");

					} else {
						// case m3ufile
						try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
							String line;
							while ((line = br.readLine()) != null) {
								if (line.contains("mp3")) {
									InputStream song = new FileInputStream(line);
									System.out.println("Now playing: " + line + " with strategy: " + args[1]);
									p.play(song);
									p.stop();
									System.out.println("Song: " + line + " just ended!");

								}
							}
						}
					}
				}
			}
			// third case:user types playlist file and strategy order
			else if (args.length == 2 && args[1].equals("order")) {
				try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
					String line;
					while ((line = br.readLine()) != null) {
						if (line.contains("mp3")) {
							InputStream song = new FileInputStream(line);
							System.out.println("Now playing: " + line + " with stragety: " + args[1]);
							p.play(song);
							p.stop();
							System.out.println("Song: " + line + " just ended!");

						}
					}
				}
			}
			// fourth case:user types playlist file and strategy random
			else if (args.length == 2 && args[1].equals("random")) {
				try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
					String line;
					ArrayList<String> mysongs = new ArrayList<String>();
					while ((line = br.readLine()) != null) {
						if (line.contains("mp3")) {
							mysongs.add(line);
						}
					}
					Random random = new Random();
					int size = mysongs.size();
					for (int i = 0; i < size; i++) {
						String selected = mysongs.get(random.nextInt(mysongs.size()));
						InputStream song = new FileInputStream(selected);
						System.out.println("Now playing: " + selected + " with stragety: " + args[1]);
						p.play(song);
						p.stop();
						System.out.println("Song: " + selected + " just ended!");
						// remove duplicates
						mysongs.remove(mysongs.indexOf(selected));
						// refix arraylist after removal
						mysongs.trimToSize();
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Song/Music File " + args[0] + " not found");
		} catch (PlayerException e) {
			System.err.println("Something's wrong with the player: " + e.getMessage());
		} finally {
			if (p != null)
				p.close();
		}

	}
}