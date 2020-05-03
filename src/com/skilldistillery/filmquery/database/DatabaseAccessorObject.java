package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private String user = "student";
	private String pass = "student";


//	public DatabaseAccessorObject() throws ClassNotFoundException {
//		Class.forName("com.mysql.jdbc.Driver");
//	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {// my doing
		// actor by ID model;
		Film film = null;

		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT id, title, release_year, rating, description FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film(); // Create the object
			// Here is our mapping of query columns to our object fields:
			film.setId(filmResult.getInt(1));
			film.setTitle(filmResult.getString(2));
			film.setReleaseYear(filmResult.getInt(3));
			film.setRating(filmResult.getString(4));
			film.setDescription(filmResult.getString(5));

		    film.setActors(findActorsByFilmId(filmId)); // An Actor has Films
			
		}
		filmResult.close();
		stmt.close();
		conn.close();

		return film;
	}

	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt(1));
			actor.setFirstName(actorResult.getString(2));
			actor.setLastName(actorResult.getString(3));
			actor.setFilms(findFilmsByActorId(actorId)); // An Actor has Films
		}
		actorResult.close();
		stmt.close();
		conn.close();
		return actor;
	}

	public List<Actor> findActorsByFilmId(int filmId) {// --- here we go!
		//System.out.println(filmId);
		List<Actor> actors = new ArrayList<>();// this was already here
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT actor.*, film.* FROM film JOIN film_actor on film.id = film_actor.film_id JOIN actor ON film_actor.actor_id = actor.id WHERE film.id = ?";
			PreparedStatement stmts = conn.prepareStatement(sql);
			stmts.setInt(1, filmId);
			
			ResultSet rs = stmts.executeQuery();
			while (rs.next()) {
				int actorId = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);

				Actor actor = new Actor(actorId, firstName, lastName);
				actors.add(actor);
			}
			//System.out.println(actors);
			rs.close();
			stmts.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return actors;
	}

	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, title, description, release_year, language_id, rental_duration, ";
			sql += " rental_rate, length, replacement_cost, rating, special_features ";
			sql += " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int filmId = rs.getInt(1);
				String title = rs.getString(2);
				String desc = rs.getString(3);
				short releaseYear = rs.getShort(4);
				int langId = rs.getInt(5);
				int rentDur = rs.getInt(6);
				double rate = rs.getDouble(7);
				int length = rs.getInt(8);
				double repCost = rs.getDouble(9);
				String rating = rs.getString(10);
				String features = rs.getString(11);

				Film film = new Film(filmId, title, desc, releaseYear, langId, rentDur, rate, length, repCost, rating,
						features);
				films.add(film);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
