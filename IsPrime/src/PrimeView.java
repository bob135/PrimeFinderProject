//Edward Carter

/*
 * Interface implemented by Gui to break up 2 way dependency
 */
public interface PrimeView 
{
	public void displayOutput(String output);
	public void resetGuiButtons();
	public boolean isContinueFindingPrimes();
	public void ammendOutput(String displayString);
}
