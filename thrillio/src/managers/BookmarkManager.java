package managers;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import constants.BookGenre;
import constants.KidFriendlyStatus;
import constants.MovieGenre;
import dao.BookmarkDao;
import entities.Book;
import entities.Bookmark;
import entities.Movie;
import entities.User;
import entities.UserBookmark;
import entities.WebLink;
import util.HttpConnect;
import util.IOUtil;

public class BookmarkManager {
	private static BookmarkManager instance = new BookmarkManager();
	private static BookmarkDao dao = new BookmarkDao();

	private BookmarkManager() {
	}

	public static BookmarkManager getinstance() {
		return instance;
	}

	public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String[] cast,
			String[] directors, MovieGenre genre, double imdbRating) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setTitle(title);
		movie.setProfileUrl(profileUrl);
		movie.setReleaseYear(releaseYear);
		movie.setCast(cast);
		movie.setDirectors(directors);
		movie.setGenre(genre);
		movie.setImdbRating(imdbRating);
		return movie;
	}

	public Book createBook(long id, String title, String profileUrl, int publicationYear, String publisher,
			String[] authors, BookGenre genre, double amazonRating) {

		Book book = new Book();
		book.setId(id);
		book.setTitle(title);
		book.setProfileUrl(profileUrl);
		book.setAmazonRating(amazonRating);
		book.setAuthors(authors);
		book.setGenre(genre);
		book.setPublicationYear(publicationYear);
		book.setPublicationYear(publicationYear);
		return book;
	}

	public WebLink createWebLink(long id, String title, String profileUrl, String url, String host) {

		WebLink weblink = new WebLink();
		weblink.setId(id);
		weblink.setProfileUrl(host);
		weblink.setTitle(title);
		weblink.setUrl(url);
		weblink.setHost(host);

		return weblink;
	}

	public List<List<Bookmark>> getBookmarks() {
		return dao.getBookmarks();
	}

	public void saveUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userBookmark = new UserBookmark();
		userBookmark.setUser(user);
		userBookmark.setBookmark(bookmark);
		
		/*if (bookmark instanceof WebLink) {
			try {				
				String url = ((WebLink)bookmark).getUrl();
				System.out.println("THIS IS URL "+url);
				if (!url.endsWith(".pdf")) {
					System.out.println("Enter For Download");
					String webpage = HttpConnect.download(((WebLink)bookmark).getUrl());
					if (webpage != null) {
						System.out.println("HELLO");
						IOUtil.write(webpage, bookmark.getId());
					}
				}				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		dao.saveUserBookmark(userBookmark);

	}

	public void setKidFriendlyStatus(User user, KidFriendlyStatus kidFriendlyStatus, Bookmark bookmark) {
		bookmark.setKidFriendlyStatus(kidFriendlyStatus);
		bookmark.setKidFriendlyMarkedBy(user);
		dao.updateKidFriendlyStatus(bookmark);
		System.out.println(
				"Kid Friendly Status: " + kidFriendlyStatus + " Marked by: " + user.getEmail() + ", " + bookmark);
	}

	public void share(User user,Bookmark bookmark) {
		bookmark.setSharedBy(user);
		System.out.println("Data to be shared");
		if(bookmark instanceof Book) {
			System.out.println(((Book) bookmark).getItemData());
		}
		
		else if(bookmark instanceof WebLink) {
			System.out.println(((WebLink) bookmark).getItemData());
		}
		dao.sharedByInfo(bookmark);
	}
}
