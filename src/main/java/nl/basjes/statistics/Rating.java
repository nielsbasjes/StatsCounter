/**
 * Copyright 2013 Niels Basjes
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *   
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.basjes.statistics;

import java.text.DecimalFormat;

/**
 * This Comparable Rating implementation is based upon:
 * http://stats.stackexchange.com/questions/15979/how-to-find-confidence-intervals-for-ratings
 * 
 * Bayesian Approach I: Normal Distribution over Ratings 
 * 
 * One way of moving the estimated rating toward a prior is, as in Karl's answer, 
 * to use an estimate of the form 
 * 
 *    w*R+(1−w)*C
 * 
 * R is the mean over the ratings for the items. 
 * C is the mean over all items (or whatever prior you want to shrink your rating to). 
 * Note that the formula is just a weighted combination of R and C. 
 * 
 * w=v/(v+m) is the weight assigned to R, where v is the number of reviews for the beer 
 * and m is some kind of constant "threshold" parameter. 
 * Note that when v is very large, i.e., when we have a lot of ratings for the current item, 
 * then w is very close to 1, so our estimated rating is very close to R and we pay little 
 * attention to the prior C. When v is small, however, w is very close to 0, so the estimated 
 * rating places a lot of weight on the prior C. This estimate can, in fact, be given a 
 * Bayesian interpretation as the posterior estimate of the item's mean rating
 * when individual ratings comes from a normal distribution centered around that mean.
 * 
 * However, assuming that ratings come from a normal distribution has two
 * problems: 
 * - A normal distribution is continuous, but ratings are discrete.
 * - Ratings for an item don't necessarily follow a unimodal Gaussian shape. For
 *   example, maybe your item is very polarizing, so people tend to either give it
 *   a very high rating or give it a very low rating.
 * 
 */
public class Rating extends Counter implements Comparable<Rating> {
    private double globalMean;

    // ------------------------------------------

    public Rating(double lower, double upper) {
        globalMean = lower + ((upper - lower) / 2.0); // Global 'mean' guestimate
    }

    // ------------------------------------------

    // Introduce caching to speed up and to make compare better possible
    double cachedN              = Double.NaN;
    double cachedBayesianRating = Double.NaN;

    public double getBayesianRating() {
        double v = getN();
        if (!Double.isNaN(cachedN) && cachedN == v) {
            return cachedBayesianRating;
        }

        double m = 1.0; // Pick a value ... any value ...
        double w = v / (v + m);
        cachedBayesianRating = (w * getMeanRating()) + ((1 - w) * globalMean);
        cachedN = v;
        return cachedBayesianRating;
    }

    // ------------------------------------------

    public double getMeanRating() {
        double R = getMean();

        // Special case: we have no ratings at all
        if (Double.isNaN(R)) {
            return globalMean;
        }
        return R;
    }
    
    
    // ------------------------------------------

    @Override
    public int compareTo(Rating o) {
        double br = getBayesianRating();
        double obr = o.getBayesianRating();
        double diff = br - obr;
        if (diff > 0) {
            return -1;
        }
        if (diff < 0) {
            return 1;
        }
        return 0;
    }

    // ------------------------------------------

    private DecimalFormat df = new DecimalFormat("0.00");
    private String formatDouble(double d){
        if (Double.isNaN(d)){
            return "NaN";
        }
        return df.format(d);
    }
    
    @Override
    public String toString() {
        return "{ Rating: " + formatDouble(getMeanRating()) + " ("+getN()+") ,"
             + " SortedBy " + formatDouble(getBayesianRating()) + " }";
    }

    // ------------------------------------------
}
