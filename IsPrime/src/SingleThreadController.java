import java.util.ArrayList;

//Edward Carter

/*
 * This class determines and displays primes up to a given value
 */
public class SingleThreadController 
{
	PrimeView primeView;
	boolean continueFindingPrimes;
	ArrayList<String> myList;
	
	/*
	 * @Input PrimeView
	 * @Input boolean
	 */
	public SingleThreadController(PrimeView view, boolean inContinueFindingPrimes)
	{
		primeView = view;
		continueFindingPrimes = inContinueFindingPrimes;
		myList = new ArrayList<String>();
	}

	/*
	 * kick off loop to check each prime up to given value
	 */
	public void determinePrimes(int inNum)
	{
	    int inputNum = inNum;
	    
	    if (inputNum >= 1)
	    {

		    for (int i = 1; (i <= inputNum) && continueFindingPrimes; i++)
		    {
		    	if (isPrime(i))
		    	{
		    		myList.add(Integer.toString(i));
		    	}
		    }
		    primeView.displayOutput(myList.toString());
	    }
	    else
	    {
	    	System.out.println("Invalid number entered");
	    }
	}
	
	/*
	 * Determine if a particular value is prime or not
	 */
	private boolean isPrime(int possiblyPrimeNum) 
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
