//Edward Carter

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Determines if given values are prime and displays them
 */
public class SuperQuickPrimeFinderGuy extends Thread 
{
	private int possiblyPrimeNum;
	private AtomicInteger threadNum;
	private ConcurrentLinkedDeque<Integer> myList;
	
	/*
	 * @Input int
	 * @Input AtomicInteger
	 * @Input ConcurrentLinkedDeque
	 */
	public SuperQuickPrimeFinderGuy(int inPossiblyPrimeNum, AtomicInteger threadNumber, ConcurrentLinkedDeque<Integer> inList)
	{
		possiblyPrimeNum = inPossiblyPrimeNum;
		threadNum = threadNumber;
		myList = inList;
	}
	
	/*
	 * Displays values found to be prime
	 * Waits for threads created prior to finish before displaying if prime is found to be prime
	 */
	public void run()
	{
		if (isPrime())
		{
			myList.add(possiblyPrimeNum);
		}
		threadNum.incrementAndGet();
	}
	
	/*
	 * Determines if a given value is prime
	 */
	public boolean isPrime() 
	{
	        if (possiblyPrimeNum < 2) 
	        {
	            return false;
	        }
	        if (possiblyPrimeNum == 2) 
	        {
	            return true;
	        }
	        if (possiblyPrimeNum % 2 == 0) 
	        {
	            return false;
	        }
	        for (int i = 3; i * i <= possiblyPrimeNum; i += 2)
	        {
	             if (possiblyPrimeNum % i == 0) 
	             {
	                 return false;
	             }
	        }
	        return true;
	}	
}
