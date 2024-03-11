
import java.io.IOException;

// CacheImpl: Nodes save Node ref, hashmap saves index
// CacheImpl2: Nodes save Node ref, hashmap saves Node ref
// CacheImpl3: Nodes save Node index, hashmap saves index
// CacheImpl5: Nodes save Node index, hashmap saves Node ref
// CacheImpl4: Double Hashing, Nodes save Node ref, hashmap saves index
// CacheImpl6: Double Hashing, Nodes save Node index, hashmap saves Node ref

public class TestCacheSpeed {

	public static void main(String[] args) throws IOException {
		//?for easier testing
	int j = 0;
	int testCount=2; //* number of different implementations
	int maxLoops=5*testCount;
	long[] each = new long[maxLoops];
	long[] average = new long[testCount];
	Cache<String, String> cache = new CacheImpl11<>(0) ;

	String[] prints = 
		{
//	"Double Hashing, Nodes save Node ref, hashmap saves index: ",
//	"Double Hashing, Nodes save Node index, hashmap saves Node ref: ",
//	"Double Hashing improved, Nodes save Node ref, hashmap saves index: ",
//	"Seperate chaining V2  maxN/5, Nodes save Node ref, hashmap saves index: ",
	"Seperate chaining V1 maxN*2, Nodes save Node ref, hashmap saves index: ",
	"Seperate chaining V3 maxN*2, Nodes save Node ref, hashmap saves index: ",
	"Java hashmap, Nodes save Node ref, hashmap saves index: ",
	};

	
	while(j<maxLoops+1) // used to replace first iteration
	{	
		int cachesize = 500;
		//initialize with your cache implementation
//		if (j%testCount==0)				
//			cache = new CacheImpl4<>(cachesize);//Double Hashing, Nodes save Node ref, hashmap saves index
		//  if (j%testCount==0)		
		// 	cache = new CacheImpl7<>(cachesize);//Double Hashing, Nodes save Node index, hashmap saves Node ref
//		else if (j%testCount==1)		
//			cache = new CacheImpl8<>(cachesize);//Separate chaining v2, Nodes save Node ref, hashmap saves index 
		 if (j%testCount==0)
			cache = new CacheImpl9<>(cachesize);//Separate chaining v1, Nodes save Node ref, hashmap saves index 
		 if (j%testCount==1)
				cache = new CacheImpl11<>(cachesize);//Separate chaining v3, Nodes save Node ref, hashmap saves index 
//		 else if (j%testCount==1)
//				cache = new CacheImpl10<>(cachesize);//Java hashmap,, Nodes save Node ref, hashmap saves index
		//give path to the dat file
		String dataFile = "datasets/dataset-5000/data-5000.dat";
		
		//give path to the workload file
		String requestsFile = "datasets/dataset-5000/requests-100000.dat";

		DataSource dataSource = new DataSource(dataFile);
		WorkloadReader requestReader = new WorkloadReader(requestsFile);
		
		String key = null;		
		long numberOfRequests = 0;
		
		/*start performance test*/
		
		//track current time
		long startTime = System.currentTimeMillis();
		
		while ((key = requestReader.nextRequest()) != null) {
			System.out.println("requests " + numberOfRequests++);
			String data = (String)cache.lookUp(key);
			if (data == null) {//data not in cache
				data = dataSource.readItem(key);
				if (data == null) {
					throw new IllegalArgumentException("DID NOT FIND DATA WITH KEY " + key +". Have you set up files properly?");
				}else{
					cache.store(key, data);
				}
			}			
		}

		/*speed test finished*/
		long duration = System.currentTimeMillis() - startTime;
		System.out.printf("Read %d items in %d ms\n", numberOfRequests,	duration);
		System.out.printf("Stats: lookups %d, hits %d, hit-ratio %f\n", cache.getNumberOfLookUps(), cache.getHits(), cache.getHitRatio());

		requestReader.close();
		
		if (j==maxLoops)
			each[0]=duration;	//first loop is incosistent
		else
			each[j]=duration;
		++j;
	}




		System.out.println("Independent results: ");

		for (int i =0; i<testCount;++i)
		{	
			System.out.println(prints[i]);
			for (j = i; j<each.length;j+=testCount)
			{
				average[i]+=each[j];
				System.out.println(each[j]);
			}
			System.out.println("==================================================");
			average[i]=average[i]/(each.length/testCount);
		}

		System.out.println("Results: ");
		for (int i =0; i<testCount;++i)
		{
			System.out.println(prints[i] + average[i]);
		}
	//	System.out.println("Nodes save Node ref, hashmap saves index: "+average[0]);
	//	System.out.println("Nodes save Node ref, hashmap saves Node ref: "+average[1]);
	//	System.out.println("Nodes save Node index, hashmap saves index: "+average[2]);
	//	System.out.println("Nodes save Node index, hashmap saves Node ref: "+average[3]);
	//	System.out.println("Double Hashing, Nodes save Node ref, hashmap saves index: "+average[4]);
	}
}
/*
Nodes save Node ref, hashmap saves index: 43265
Nodes save Node index, hashmap saves index: 41445
Nodes save Node ref, hashmap saves Node ref: 41071

Nodes save Node ref, hashmap saves index: 41328
Nodes save Node index, hashmap saves index: 41433
Nodes save Node ref, hashmap saves Node ref: 41003

Nodes save Node ref, hashmap saves index: 1681
Nodes save Node index, hashmap saves index: 1673
Nodes save Node ref, hashmap saves Node ref: 1678

Nodes save Node ref, hashmap saves index: 1706
Nodes save Node index, hashmap saves index: 1690
Nodes save Node ref, hashmap saves Node ref: 1694

Nodes save Node ref, hashmap saves index: 1675
Nodes save Node index, hashmap saves index: 1665
Nodes save Node ref, hashmap saves Node ref: 1672

Independent results:
47389
47340
47233
47252
47256
47278
47274
47285
47232
47482
==================================================
47512
47197
48010
47249
47370
47273
47252
47228
47455
47199
==================================================
47217
47292
47616
47230
47275
47316
47157
47246
47269
47350
==================================================
Results:
Nodes save Node ref, hashmap saves index: 47302
Nodes save Node index, hashmap saves index: 47374
Nodes save Node ref, hashmap saves Node ref: 47296

σιζε = 10
5
7 - (5 % 7) = 2
0 1 2 3 4 5 6 7 8 9
5 7 9

*/