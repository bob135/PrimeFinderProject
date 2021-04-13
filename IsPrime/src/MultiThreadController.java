//Edward Carter

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * This class kicks of threads who determine prime values
 */
public class MultiThreadController extends Thread
{
	PrimeView primeView;
	AtomicInteger curPrime;
	int inputNum;
	int maxThreads;
	boolean continueFindingPrimes;
	
	/*
	 * @Input PrimeView
	 * @Input String
	 * @Input String
	 * @Input boolean
	 */
	public MultiThreadController(PrimeView view, String inNum, String inMaxThreads, boolean inContinueFindingPrimes)
	{
		primeView = view;
		inputNum = Integer.parseInt(inNum);
		maxThreads = Integer.parseInt(inMaxThreads);
		continueFindingPrimes = inContinueFindingPrimes;
		curPrime = new AtomicInteger(1);
	}
	
	/*
	 * Create a new thread for each number that may be prime
	 */
	public void run()
	{	    
	    if (inputNum >= 1 && maxThreads >= 1)
	    {
	    	ConcurrentLinkedDeque<Integer> myList = new ConcurrentLinkedDeque<Integer> ();
		    ExecutorService executor = Executors.newFixedThreadPool(maxThreads);

		    for (int i = 1; i <= inputNum; i++)
		    {
		    	if (!primeView.isContinueFindingPrimes())
		    	{
		    		executor.shutdownNow();
		    		break;
		    	}
		    	executor.submit(new SuperQuickPrimeFinderGuy(i, curPrime, myList));
		    	if (i %500 == 0)
		    	{
		    		primeView.displayOutput(myList.toString());
		    	}
		    }
		    
		    final long startTime = System.currentTimeMillis();
		    
		    //wait for threads to finish
			while (inputNum > curPrime.get())
			{
		    	if (!primeView.isContinueFindingPrimes())
		    	{
		    		executor.shutdownNow();
		    		break;
		    	}
				try        
				{
  				  Thread.sleep(1);
				}
				catch(InterruptedException ex) 
				{
 				   Thread.currentThread().interrupt();
				}
			}
			primeView.displayOutput(myList.toString());
		    
		    final long durationMillis = (System.currentTimeMillis() - startTime);
		    
		    executor.shutdown();
		    primeView.resetGuiButtons();
		    
		    String durationString = Long.toString(durationMillis);
		    primeView.ammendOutput(" Duration: " + durationString + "ms");

	    }
	    else
	    {
	    	System.out.println("Invalid number entered");
	    }
	}


}
