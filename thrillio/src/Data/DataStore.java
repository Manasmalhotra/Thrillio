package Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import constants.BookGenre;
import constants.Gender;
import constants.MovieGenre;
import constants.UserType;
import entities.Bookmark;
import entities.User;
import entities.UserBookmark;
import managers.BookmarkManager;
import managers.UserManager;
import util.IOUtil;

public class DataStore {
	public static final int USER_BOOKMARK_LIMIT = 5;
	public static final int BOOKMARK_COUNT_PER_TYPE = 5;
	public static final int BOOKMARK_TYPES_COUNT = 3;
	public static final int TOTAL_USER_COUNT = 5;
	
	//private static User[] users = new User[TOTAL_USER_COUNT];
	private static List<User> users = new ArrayList<> ();
	public static List<User> getUsers() {
		return users;
	}

	//private static Bookmark[][] bookmarks = new Bookmark[BOOKMARK_TYPES_COUNT][BOOKMARK_COUNT_PER_TYPE];
	private static List<List<Bookmark>> bookmarks=new ArrayList<>();
	public static List<List<Bookmark>> getBookmarks() {
		return bookmarks;
	}

	private static List<UserBookmark> userBookmarks = new ArrayList<>();
	
	public static void loadData() {
		try {
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jid_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root", "Manas@65");
				Statement stmt = conn.createStatement();) {
			loadUsers(stmt);   
			loadWebLinks(stmt); 
			loadMovies(stmt);
			loadBooks(stmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}	

	private static void loadUsers(Statement stmt) throws SQLException{
		//String[] data = new String[TOTAL_USER_COUNT];
		String query="Select b.id,b.email,b.password,b.first_name,b.last_name,b.gender_id,b.user_type_id,b.created_date from User b";
		ResultSet rs=stmt.executeQuery(query);
		
		List<String> data=new ArrayList<>();
    	while(rs.next()) {
    		long id = rs.getLong("id");
    		String email=rs.getString("email");
    		String password=rs.getString("password");
    		String firstName=rs.getString("first_name");
    		String lastName=rs.getString("last_name");
    		int gender_id=rs.getInt("gender_id");
    		Gender gender = Gender.values()[gender_id];
    		int user_id=rs.getInt("user_type_id");
    		UserType userType=UserType.values()[user_id];
    		users.add(UserManager.getInstance().createUser(id,email,password,firstName,lastName, gender, userType));
    	}	
    }
	
	private static void loadWebLinks(Statement stmt) throws SQLException {
		//String[] data = new String[BOOKMARK_COUNT_PER_TYPE];
		String query="Select w.id,w.title,w.url,w.host,w.created_date from WebLink w";
		ResultSet rs=stmt.executeQuery(query);
		List<Bookmark> bookmarksList = new ArrayList<>();
        while(rs.next()) {
        	long id=rs.getLong("id");
        	String title=rs.getString("title");
        	String url=rs.getString("url");
        	String host=rs.getString("host");
    		Bookmark bookmark= BookmarkManager.getinstance().createWebLink(id,title,"",url, host/*, values[4]*/);
    	    bookmarksList.add(bookmark);
    	}
    	bookmarks.add(bookmarksList);
	}
	
	private static void loadMovies(Statement stmt) throws SQLException {
		String query = "Select m.id, title, release_year, GROUP_CONCAT(DISTINCT a.name SEPARATOR ',') AS cast, GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS directors, movie_genre_id, imdb_rating"
				+ " from Movie m, Actor a, Movie_Actor ma, Director d, Movie_Director md "
				+ "where m.id = ma.movie_id and ma.actor_id = a.id and "
				      + "m.id = md.movie_id and md.director_id = d.id group by m.id";
		ResultSet rs = stmt.executeQuery(query);
		
		List<Bookmark> bookmarkList = new ArrayList<>();
		while (rs.next()) {
			long id = rs.getLong("id");
			String title = rs.getString("title");
			int releaseYear = rs.getInt("release_year");
			String[] cast = rs.getString("cast").split(",");
			String[] directors = rs.getString("directors").split(",");			
			int genre_id = rs.getInt("movie_genre_id");
			MovieGenre genre = MovieGenre.values()[genre_id];
			double imdbRating = rs.getDouble("imdb_rating");
			
			Bookmark bookmark = BookmarkManager.getinstance().createMovie(id, title, "", releaseYear, cast, directors, genre, imdbRating/*, values[7]*/);
    		bookmarkList.add(bookmark); 
		}
		bookmarks.add(bookmarkList);
	}
	
	private static void loadBooks(Statement stmt) throws SQLException {		    	
		String query = "Select b.id, title, publication_year, p.name, GROUP_CONCAT(a.name SEPARATOR ',') AS authors, book_genre_id, amazon_rating, created_date"
				+ " from Book b, Publisher p, Author a, Book_Author ba "
				+ "where b.publisher_id = p.id and b.id = ba.book_id and ba.author_id = a.id group by b.id";
    	ResultSet rs = stmt.executeQuery(query);
		
    	List<Bookmark> bookmarkList = new ArrayList<>();
    	while (rs.next()) {
    		long id = rs.getLong("id");
			String title = rs.getString("title");
			int publicationYear = rs.getInt("publication_year");
			String publisher = rs.getString("name");		
			String[] authors = rs.getString("authors").split(",");			
			int genre_id = rs.getInt("book_genre_id");
			BookGenre genre = BookGenre.values()[genre_id];
			double amazonRating = rs.getDouble("amazon_rating");
			
			Date createdDate = rs.getDate("created_date");
			System.out.println("createdDate: " + createdDate);
			Timestamp timeStamp = rs.getTimestamp(8);
			System.out.println("timeStamp: " + timeStamp);
			System.out.println("localDateTime: " + timeStamp.toLocalDateTime());
			
			System.out.println("id: " + id + ", title: " + title + ", publication year: " + publicationYear + ", publisher: " + publisher + ", authors: " + String.join(", ", authors) + ", genre: " + genre + ", amazonRating: " + amazonRating);
    		
    		Bookmark bookmark = BookmarkManager.getinstance().createBook(id, title,"",publicationYear, publisher, authors, genre, amazonRating/*, values[7]*/);
    		bookmarkList.add(bookmark); 
    	}
    	bookmarks.add(bookmarkList);
    }	

	public static void add(UserBookmark userBookmark) {
		userBookmarks.add(userBookmark);
	}	
}
