package Data;
import java.util.ArrayList;
import java.util.List;

import constants.BookGenre;
import constants.Gender;
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
		loadUsers();
		loadWebLinks();
		loadMovies();
		loadBooks();
	}	

	private static void loadUsers() {
		//String[] data = new String[TOTAL_USER_COUNT];
		List<String> data=new ArrayList<>();
    	IOUtil.read(data, "User.txt");
    	for (String row : data) {
    		String[] values = row.split("\t");
    		
    		Gender gender = Gender.MALE;
    		if (values[5].equals("f")) {
    			gender = Gender.FEMALE;
    		} else if (values[5].equals("t")) {
    			gender = Gender.TRANSGENDER;
    		}
    		
    		UserType userType = UserType.USER;
    		if(values[6].equals("editor")) {
    			userType=UserType.EDITOR;
    		}
    		else if(values[6].equals("chiefeditor")) {
    			userType=UserType.CHIEF_EDITOR;
    		}
    		users.add(UserManager.getInstance().createUser(Long.parseLong(values[0]), values[1], values[2], values[3], values[4], gender, userType));
    	}
	}
	
	private static void loadWebLinks() {
		//String[] data = new String[BOOKMARK_COUNT_PER_TYPE];
		List<String> data=new ArrayList<>();
    	IOUtil.read(data, "WebLink.txt");
    	//int colNum = 0;
    	List<Bookmark> bookmarksList=new ArrayList<>();
    	for (String row : data) {
    		String[] values = row.split("\t");
    		Bookmark bookmark= BookmarkManager.getinstance().createWebLink(Long.parseLong(values[0]), values[1],"",values[2], values[3]/*, values[4]*/);
    	    bookmarksList.add(bookmark);
    	}
    	bookmarks.add(bookmarksList);
	}
	
	private static void loadMovies() {
		//String[] data = new String[BOOKMARK_COUNT_PER_TYPE];
		List<String> data=new ArrayList<>();
    	IOUtil.read(data, "Movie.txt");
    	List<Bookmark> bookmarksList=new ArrayList<>();
    	for (String row : data) {
    		String[] values = row.split("\t");
    		String[] cast = values[3].split(",");
    		String[] directors = values[4].split(",");
    		Bookmark bookmark= BookmarkManager.getinstance().createMovie(Long.parseLong(values[0]), values[1], "", Integer.parseInt(values[2]), cast, directors, values[5], Double.parseDouble(values[6])/*, values[7]*/);
    	    bookmarksList.add(bookmark);
    	}
    	
    	bookmarks.add(bookmarksList);
	}
	
	private static void loadBooks() {		    	
		//String[] data = new String[BOOKMARK_COUNT_PER_TYPE];
		List<String> data=new ArrayList<>();
    	IOUtil.read(data, "Book.txt");
    	List<Bookmark> bookmarksList=new ArrayList<>();
    	//int colNum = 0;
    	for (String row : data) {
    		String[] values = row.split("\t");
    		String[] authors = values[4].split(",");
    		//bookmarks[2][colNum++] = BookmarkManager.getinstance().createBook(Long.parseLong(values[0]), values[1],"",Integer.parseInt(values[2]), values[3], authors, values[5], Double.parseDouble(values[6])/*, values[7]*/);
    	    Bookmark bookmark=BookmarkManager.getinstance().createBook(Long.parseLong(values[0]), values[1],"",Integer.parseInt(values[2]), values[3], authors, BookGenre.valueOf(values[5].toUpperCase()), Double.parseDouble(values[6])/*, values[7]*/);
    	    bookmarksList.add(bookmark); 
    	}
    	bookmarks.add(bookmarksList);
    }	

	public static void add(UserBookmark userBookmark) {
		userBookmarks.add(userBookmark);
	}	
}
