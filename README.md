StatsCounter
============

A simple statistics counter that fits well on the Map Reduce model (i.e. Hadoop and such)

This class is a Writable (Hadoop) that is capable of aggregating basic statistics (min, max, count, sum, average, variance and standard deviation) for values of the type 'double'.

The advantages of this implementation:

- It allows serializing the underlying data into only 40 bytes (fixed size!)
- Actually implements the Associative and Commutativity properties of the underlying operations (including the variance and standard deviation!).
- Implements the Writable interface of Hadoop to allow easy implementation in a Hadoop job. 

Thus efficiently allows for doing distributed aggregation of petabyte size datasets.

Based upon:
----
Formulas and code used in this implementation were taken from

- [Wikipedia: Algorithms for calculating variance - Parallel algorithm](http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Parallel_algorithm)

- [Apache Commons Math3 - SummaryStatistics](http://commons.apache.org/math/apidocs/org/apache/commons/math3/stat/descriptive/SummaryStatistics.html)
- [Apache Commons Math3 - AggregateSummaryStatistics](http://commons.apache.org/math/apidocs/org/apache/commons/math3/stat/descriptive/AggregateSummaryStatistics.html)

