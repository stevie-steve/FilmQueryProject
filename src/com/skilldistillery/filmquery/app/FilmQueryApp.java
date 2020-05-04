package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		Scanner input = new Scanner(System.in);
		FilmQueryApp app = new FilmQueryApp();
		System.out.println("Welcome to the Film Bank!");
		// app.test();
		app.launch(input);
	}

//	private void test() throws SQLException {
//	 Actor actor = db.findActorById(1);
//	  System.out.println(actor);

//		Film film = db.findFilmById(1);
//		System.out.println(film);

//		List<Film> films = db.findFilmsByKeyword("Wo");
//		System.out.println(films);
//	}

	private void launch(Scanner input) throws SQLException {
		System.out.println("Choose from the following options:\n");
		System.out.println("1. Search Films by ID");
		System.out.println("2. Search films by a Keyword.");
		System.out.println("3. Exit ");
		System.out.print("Entry: ");
		System.out.println();
		startUserInterface(input);

	}

	private void startUserInterface(Scanner input) throws SQLException {
		String choice = input.next();

		while (true) {
			if (choice.equals("1")) {
				System.out.print("Enter the ID of the Film: ");
				int enteredFilmId = input.nextInt();
				Film film = db.findFilmById(enteredFilmId);
				if (film == null) {
					System.out.println("No movies found. Try again.");
				} else {
					System.out.println(film);

				}
				System.out.println();
				launch(input);
			}

			if (choice.equals("2")) {
				System.out.println("Enter the keyword: ");
				String keyword = input.next();
				List<Film> films = db.findFilmsByKeyword(keyword);
				if (films.size() == 0) {
					System.out.println("No movies found. Try again.");
				} else {
					for (Film film : films) {
						System.out.println(film);
						System.out.println();

					}
				}

				launch(input);
			}
			if (!choice.equals("3") && (!choice.equals("2")) && (!choice.equals("1"))) {
				System.out.println("Please choose a valid option (1, 2, 3)");
				launch(input);
			}
			if (choice.equals("3")) {
				System.out.println("Aaaaaand, scene.");
				System.out.println();
				break;
			}
			return;
		}
	}
}
