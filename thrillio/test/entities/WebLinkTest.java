package entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import managers.BookmarkManager;

class WebLinkTest {

	@Test
	void testIsKidFriendlyEligible() {
		
		//Test Case 1: porn in url --false
		WebLink weblink=BookmarkManager.getinstance().createWebLink(2000,"Taming Tiger,Part 2","","http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html","http://www.javaworld.com");
		boolean isKidFriendlyEligible=weblink.isKidFriendlyEligible();
		assertFalse(isKidFriendlyEligible,"For porn in url - isKidFriendlyEligible() must return false ");
		
		//Test case 2: porn in title
		
		
		weblink=BookmarkManager.getinstance().createWebLink(2000,"Taming porn, Part 2","","http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html","http://www.javaworld.com");
		isKidFriendlyEligible=weblink.isKidFriendlyEligible();
		assertFalse(isKidFriendlyEligible,"For porn in title - isKidFriendlyEligible() must return false ");
	
		//Test case 3: adult in host --false
		
		weblink=BookmarkManager.getinstance().createWebLink(2000,"Taming Tiger,Part 2","","http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html","http://www.adult.com");
		isKidFriendlyEligible=weblink.isKidFriendlyEligible();
		assertFalse(isKidFriendlyEligible,"For adult in host - isKidFriendlyEligible() must return false ");
		
		//Test case 4: adult in url not in host --true
		
		weblink=BookmarkManager.getinstance().createWebLink(2000,"Taming Tiger,Part 2","","http://www.javaworld.com/article/2072759/core-java/taming-adult--part-2.html","http://www.javaworld.com");
		isKidFriendlyEligible=weblink.isKidFriendlyEligible();
		assertTrue(isKidFriendlyEligible,"For adult in url and not in host - isKidFriendlyEligible() must return true ");
		
		//TestCase 5: adult in title only --true
		weblink=BookmarkManager.getinstance().createWebLink(2000,"Taming adult,Part 2","","http://www.javaworld.com/article/2072759/core-java/taming-adult--part-2.html","http://www.javaworld.com");
		isKidFriendlyEligible=weblink.isKidFriendlyEligible();
		assertTrue(isKidFriendlyEligible,"For adult in title - isKidFriendlyEligible() must return true ");
		
	}

}
