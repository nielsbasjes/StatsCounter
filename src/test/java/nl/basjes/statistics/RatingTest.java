package nl.basjes.statistics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RatingTest {
	// ------------------------------------------

	@Test
	public void testBayesianRating() {
		double lower = 1;
		double upper = 5;
		Rating noRatings = new Rating(lower, upper);
		Rating manyPoorRatings = new Rating(lower, upper);
		Rating manyGoodRatings = new Rating(lower, upper);
		Rating fewPoorRatings = new Rating(lower, upper);
		Rating fewGoodRatings = new Rating(lower, upper);

		manyPoorRatings.increment(1);
		manyPoorRatings.increment(1);
		manyPoorRatings.increment(1);
		manyPoorRatings.increment(1);
		manyPoorRatings.increment(1);

		fewPoorRatings.increment(1);
		fewPoorRatings.increment(1);

		fewGoodRatings.increment(5);
		fewGoodRatings.increment(5);

		manyGoodRatings.increment(5);
		manyGoodRatings.increment(5);
		manyGoodRatings.increment(5);
		manyGoodRatings.increment(5);
		manyGoodRatings.increment(5);

		List<Rating> ratingList = new ArrayList<Rating>();
		ratingList.add(noRatings);
		ratingList.add(manyGoodRatings);
		ratingList.add(fewGoodRatings);
		ratingList.add(manyPoorRatings);
		ratingList.add(fewPoorRatings);

		Object[] a = ratingList.toArray();

		// System.out.println(Arrays.toString(a));
		Arrays.sort(a);
		// System.out.println(Arrays.toString(a));

		assertEquals(manyPoorRatings, a[0]);
		assertEquals(fewPoorRatings, a[1]);
		assertEquals(noRatings, a[2]);
		assertEquals(fewGoodRatings, a[3]);
		assertEquals(manyGoodRatings, a[4]);
		assertEquals("{ Mean: 5.0 ,Rating = 4.666666666666667 }",
				manyGoodRatings.toString());
	}

	// ------------------------------------------

}
