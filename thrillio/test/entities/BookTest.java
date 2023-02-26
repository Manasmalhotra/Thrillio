package entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import constants.BookGenre;
import constants.MovieGenre;
import managers.BookmarkManager;

class BookTest {

	@Test
	void testIsKidFriendlyEligible() {
		Book book= BookmarkManager.getinstance().createBook(4000,"Walden","",1854,"Wilder Publications",new String[] {"Henry David Thoreau"},BookGenre.PHILOSOPHY,4.3);
	    boolean isKidFriendlyEligible=book.isKidFriendlyEligible();
	    assertFalse(isKidFriendlyEligible,"For Philosophy genre - isKidFriendlyEligible should return false");
	    
	    book= BookmarkManager.getinstance().createBook(4000,"Walden","",1854,"Wilder Publications",new String[] {"Henry David Thoreau"},BookGenre.SELF_HELP,4.3);
	    isKidFriendlyEligible=book.isKidFriendlyEligible();
	    assertFalse(isKidFriendlyEligible,"For Self-Help genre - isKidFriendlyEligible should return false");
	}

}
