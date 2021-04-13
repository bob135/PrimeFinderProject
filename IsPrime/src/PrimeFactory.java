//Edward Carter

import java.awt.EventQueue;

/**
 * Factory class that creates GUI
*/
public class PrimeFactory {
	
	static PrimeGui frame;
	
	/**
	 * Initiates program
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				try
				{
					frame = new PrimeGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
