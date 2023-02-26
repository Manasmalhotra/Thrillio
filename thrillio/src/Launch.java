import java.util.List;

import Data.DataStore;
import bgjobs.WebpageDownloaderTask;
import entities.Bookmark;
import entities.User;
import managers.BookmarkManager;
import managers.UserManager;

public class Launch {
    
	private static List<User> users;
	private static List<List<Bookmark>> bookmarks;
  	private static void loadData() {
  		System.out.println("Loading Data...");
		System.out.println("Loading Data...");
	    DataStore.loadData();	
	    users=UserManager.getInstance().getUsers();
		bookmarks=BookmarkManager.getinstance().getBookmarks();
		
		//System.out.println("Printing Data...");
		//printUserData();
		//printBookmarkData();
	}
  	
  	/*
	
	private static void printBookmarkData() {
		for (List<Bookmark> bookmarks2 : bookmarks) {
			for (Bookmark bookmark : bookmarks2) {
				System.out.println(bookmark);
			}
		}
		
	}

	private static void printUserData() {
		for(User user:users) {
			System.out.println(user);
		}
		
	}
	*/

	private static void start() {
		//System.out.println("\n2. Bookmarking");
		for(User user:users) {
			View.browse(user, bookmarks);
		}
	}

	public static void main(String[] args) {
	  loadData();
	  start();
	  //Background Jobs
	  runDownLoaderJob();
	}
	
	private static void runDownLoaderJob() {
		WebpageDownloaderTask task=new WebpageDownloaderTask(true);
        (new Thread(task)).start();		
	}


}
