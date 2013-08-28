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
        Rating somePoorRatings = new Rating(lower, upper);
        Rating someGoodRatings = new Rating(lower, upper);
        Rating fewPoorRatings = new Rating(lower, upper);
        Rating fewGoodRatings = new Rating(lower, upper);

        manyPoorRatings.increment(1);
        manyPoorRatings.increment(1);
        manyPoorRatings.increment(1);
        manyPoorRatings.increment(1);
        manyPoorRatings.increment(1);

        somePoorRatings.increment(1);
        somePoorRatings.increment(1);
        somePoorRatings.increment(1);
        somePoorRatings.increment(2);
        
        fewPoorRatings.increment(1);
        fewPoorRatings.increment(1);

        fewGoodRatings.increment(5);
        fewGoodRatings.increment(5);

        someGoodRatings.increment(5);
        someGoodRatings.increment(5);
        someGoodRatings.increment(5);
        someGoodRatings.increment(4);
        
        manyGoodRatings.increment(5);
        manyGoodRatings.increment(5);
        manyGoodRatings.increment(5);
        manyGoodRatings.increment(5);
        manyGoodRatings.increment(5);

        List<Rating> ratingList = new ArrayList<Rating>();
        ratingList.add(noRatings);
        ratingList.add(fewPoorRatings);
        ratingList.add(fewGoodRatings);
        ratingList.add(manyPoorRatings);
        ratingList.add(manyGoodRatings);
        ratingList.add(somePoorRatings);
        ratingList.add(someGoodRatings);

        Object[] a = ratingList.toArray();

        Arrays.sort(a);
        for (int i = 0 ; i <=6 ; i ++) {
            System.out.println(a[i].toString());
        }

        assertEquals(manyGoodRatings, a[0]);
        assertEquals(someGoodRatings, a[1]);
        assertEquals(fewGoodRatings,  a[2]);
        assertEquals(noRatings,       a[3]);
        assertEquals(fewPoorRatings,  a[4]);
        assertEquals(somePoorRatings, a[5]);
        assertEquals(manyPoorRatings, a[6]);
        assertEquals("{ Rating: 5.00 (5) , SortedBy 4.67 }", manyGoodRatings.toString());
    }

    // ------------------------------------------

}
