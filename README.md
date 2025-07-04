# Data structures 4th assignment
The goal of this project was to implement a **general-purpose LRU (Least Recently Used) Cache** in **Java**. The implementation supports **generic types** and focuses on **minimizing the time complexity** of cache operations, particularly for **lookups**. We were allowed to use **only** our own data structures implementations, except for arrays and the .io library to read the data and Exceptions. It was developed during the 3rd semester as part of the Data Structures course at AUEB.


## Tasks

We were tasked with implementing the following:

* A fully functional LRU Cache that stores key–value pairs of generic type. The cache supports two operations:

    * *LookUp(K key)*: Retrieves an object by key (returns null on a miss).
  
    * *Store(K key, V value)*: Inserts/updates an object by key, applying the LRU policy when full.

## Architecture
Our [Cache](src\CacheImpl.java) saves the data inside an **array** for fast access, while double linking them in the form of a [list node](src\Node.java) to maintain access order. It also keeps track of the most and least recently used data for quicker access. In order to find the data, our final implementation ended up using **hash** with the help of [Seperate Chaining](src\SeparateChainingV1.java), which was used to keep the index of the data in our Cache.


## Statistics
We created a [test file](TestCacheSpeed.java) in order to find the most efficient implementation in regards to speed, using the data and requests from [datasets](datasets). At first, we looked to find out the most efficient way to point to every object we had. We learned there were minimal differences between the different ways we could point to our data in our [list node](src\Node.java) and [Seperate Chaining](src\SeparateChainingV1.java).

<img src="statistics\Node-hashmap-types.jpg"/>


Next, we looked into the best way to keep track of our data in our Cache using hash.

<img src="statistics\hashmap-types.jpg"/>

Finally, we tested different sizes of our [Seperate Chaining](src\SeparateChainingV1.java), relative to the size of the Cache. Since we were targeting the fastest implementation, using a 2*N size, we were able to beat *in terms of speed* even the hashmap of Java Collections Framework.

<img src="statistics\Size-speed.jpg"/>



## Contributors
<a href="https://github.com/Morthlog/Data-Structures4/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Morthlog/Data-Structures4"/>
</a>
