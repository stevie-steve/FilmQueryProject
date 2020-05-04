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


    @Override
    public Film findFilmById(int filmId) throws SQLException {
        ;
        Film film = null;
        Connection conn = DriverManager.getConnection(URL, user, pass);
        String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, language.name FROM film join language on film.language_id = language.id WHERE film.id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, filmId);
        ResultSet filmResult = stmt.executeQuery();
        if (filmResult.next()) {
            film = new Film(); 
            film.setId(filmResult.getInt(1));
            film.setTitle(filmResult.getString(2));
            film.setReleaseYear(filmResult.getInt(3));
            film.setRating(filmResult.getString(4));
            film.setDescription(filmResult.getString(5));
            film.setLanguage(filmResult.getString(6));
           
            film.setActors(findActorsByFilmId(filmId)); // An Actor has Films
            
        }
        filmResult.close();
        stmt.close();
        conn.close();
        return film;
    } 
    
    public List<Film> findFilmsByKeyword(String keyWord) throws SQLException {
        List<Film> films = new ArrayList<>();
        Connection conn = DriverManager.getConnection(URL, user, pass);
        String sql = "SELECT film.id, film.title, film.release_year, film.rating, film.description, language.name FROM film join language on film.language_id = language.id where title like ? OR description like ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + keyWord + "%");
        stmt.setString(2, "%" + keyWord + "%");
        
        ResultSet filmResult = stmt.executeQuery();
        while (filmResult.next()) {
            Film film = new Film(); 
            film.setId(filmResult.getInt(1));
            film.setTitle(filmResult.getString(2));
            film.setReleaseYear(filmResult.getInt(3));
            film.setRating(filmResult.getString(4));
            film.setDescription(filmResult.getString(5));
            film.setLanguage(filmResult.getString(6));
            film.setActors(findActorsByFilmId(film.getId()));
            films.add(film); 
        }
        filmResult.close();
        stmt.close();
        conn.close();
        return films;
    }
    
    public Actor findActorById(int actorId) throws SQLException {
        Actor actor = null;
        Connection conn = DriverManager.getConnection(URL, user, pass);
        String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, actorId);
        ResultSet actorResult = stmt.executeQuery();
        if (actorResult.next()) {
            actor = new Actor();
            actor.setId(actorResult.getInt(1));
            actor.setFirstName(actorResult.getString(2));
            actor.setLastName(actorResult.getString(3));
            actor.setFilms(findFilmsByActorId(actorId)); 
        }
        actorResult.close();
        stmt.close();
        conn.close();
        return actor;
    }
    public List<Actor> findActorsByFilmId(int filmId) {
        
        List<Actor> actors = new ArrayList<>();
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
                Integer releaseYear = rs.getInt(4);
                int rentDur = rs.getInt(5);
                double rate = rs.getDouble(6);
                int length = rs.getInt(7);
                double repCost = rs.getDouble(8);
                String rating = rs.getString(9);
                String features = rs.getString(10);
                Film film = new Film(filmId, title, desc, releaseYear, rentDur, rate, length, repCost, rating, features);
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
            
            e.printStackTrace();
        }
    }
}