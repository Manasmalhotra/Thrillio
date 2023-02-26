package entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import constants.MovieGenre;
import managers.BookmarkManager;

class MovieTest {

	@Test
	void testIsKidFriendlyEligible() {
       
		//TestCase 1
		Movie movie= BookmarkManager.getinstance().createMovie(3000,"Citizen Kane"," ",1941,new String[] {"Orson Welles","Joseph Cotten	Orson"},new String[] {"Orson Welles"},MovieGenre.HORROR,8.5);
	    boolean isKidFriendlyEligible=movie.isKidFriendlyEligible();
	    assertFalse(isKidFriendlyEligible,"For Horror genre - isKidFriendlyEligible should return false");
	    
	    movie= BookmarkManager.getinstance().createMovie(3000,"Citizen Kane"," ",1941,new String[] {"Orson Welles","Joseph Cotten	Orson"},new String[] {"Orson Welles"},MovieGenre.THRILLERS,8.5);
	    isKidFriendlyEligible=movie.isKidFriendlyEligible();
	    assertFalse(isKidFriendlyEligible,"For Thriller genre - isKidFriendlyEligible should return false");
	}

}
