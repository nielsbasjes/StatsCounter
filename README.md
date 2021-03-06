StatsCounter
============

A simple statistics counter that fits well on the Map Reduce model (i.e. Hadoop and such)

This class is a Writable (Hadoop) that is capable of aggregating basic statistics (min, max, count, sum, average, variance and standard deviation) for values of the type 'double'.

The advantages of this implementation:

- It allows serializing the underlying data into only 40 bytes (fixed size!)
- Implements the Writable interface of Hadoop to allow easy implementation in a Hadoop job. 
- Actually implements the Associative and Commutativity properties of the underlying operations (including the variance and standard deviation!).

This last point means that these all result in the same answer for all mentioned statistics:

- 1+2+3+4
- (1+2)+3+4
- 1+(2+3)+4
- (2+3)+(4+1)
- (4+2)+(3+1)
etc.

So it doens't matter how the data is partitioned because the end result will be the correct value.
Thus efficiently allows for doing distributed aggregation of petabyte size datasets.

Based upon:
----
Formulas and code used in this implementation were taken from

- [Wikipedia: Algorithms for calculating variance - Parallel algorithm](http://en.wikipedia.org/wiki/Algorithms_for_calculating_variance#Parallel_algorithm)

- [Apache Commons Math3 - SummaryStatistics](http://commons.apache.org/math/apidocs/org/apache/commons/math3/stat/descriptive/SummaryStatistics.html)
- [Apache Commons Math3 - AggregateSummaryStatistics](http://commons.apache.org/math/apidocs/org/apache/commons/math3/stat/descriptive/AggregateSummaryStatistics.html)


License
----
Copyright 2013 Niels Basjes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

