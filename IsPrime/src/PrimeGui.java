//Edward Carter

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JRadioButton;

/*
 *Graphical User Interface used to kick off prime functionality
*/
public class PrimeGui extends JFrame implements PrimeView {

	private JPanel contentPane;
	private JTextField maxPrimeTextField;
	private JTextField numThreadsTextField;
	private final JRadioButton singleThreadedRadioButton = new JRadioButton("Single Threaded");
	ExecutorService executor;
	StyledDocument outputPaneDoc;
	SimpleAttributeSet simpleAttributes;
	public boolean continueFindingPrimes;
	JButton stopButton;
	JButton startButton;
	PrimeView view;
	private JTextPane outputTextPane;

	/**
	 * Create the frame.
	 */
	public PrimeGui() 
	{
		continueFindingPrimes = false;
		view = this;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 348);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		maxPrimeTextField = new JTextField();
		maxPrimeTextField.setBounds(121, 94, 42, 25);
		panel.add(maxPrimeTextField);
		maxPrimeTextField.setColumns(10);
		
		JLabel lblEdsAwesomePrime = new JLabel("Ed's Awesome Prime Finder Thing");
		lblEdsAwesomePrime.setForeground(Color.WHITE);
		lblEdsAwesomePrime.setFont(new Font("Serif", Font.PLAIN, 16));
		lblEdsAwesomePrime.setBounds(61, 11, 210, 25);
		panel.add(lblEdsAwesomePrime);
		
		JLabel lblMaxPrime = new JLabel("Max Prime:");
		lblMaxPrime.setFont(new Font("Serif", Font.PLAIN, 11));
		lblMaxPrime.setForeground(Color.WHITE);
		lblMaxPrime.setBounds(45, 95, 66, 20);
		panel.add(lblMaxPrime);
		
		JLabel lblNumThreads = new JLabel("Num Threads:");
		lblNumThreads.setFont(new Font("Serif", Font.PLAIN, 11));
		lblNumThreads.setForeground(Color.WHITE);
		lblNumThreads.setBounds(187, 95, 84, 20);
		panel.add(lblNumThreads);
		
		numThreadsTextField = new JTextField();
		numThreadsTextField.setColumns(10);
		numThreadsTextField.setBounds(266, 94, 42, 25);
		panel.add(numThreadsTextField);
		
		startButton = new JButton("Start");
		startButton.setFont(new Font("Serif", Font.PLAIN, 14));
		startButton.setBackground(new Color(0, 255, 0));
		startButton.setForeground(Color.WHITE);
		startButton.setBounds(61, 147, 114, 39);
		panel.add(startButton);
		
		stopButton = new JButton("Stop");
		stopButton.setFont(new Font("Serif", Font.PLAIN, 14));
		stopButton.setBackground(Color.GRAY);
		stopButton.setForeground(Color.WHITE);
		stopButton.setBounds(194, 147, 114, 39);
		stopButton.setEnabled(false);
		panel.add(stopButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 208, 314, 90);
		panel.add(scrollPane);
		
		outputTextPane = new JTextPane();
		outputTextPane.setEditable(false);
		outputTextPane.setBackground(Color.BLACK);
		outputTextPane.setForeground(Color.WHITE);
		scrollPane.setViewportView(outputTextPane);
		outputPaneDoc = outputTextPane.getStyledDocument();
		simpleAttributes = new SimpleAttributeSet();
		
		singleThreadedRadioButton.setFont(new Font("Serif", Font.PLAIN, 11));
		singleThreadedRadioButton.setForeground(Color.WHITE);
		singleThreadedRadioButton.setBackground(Color.BLACK);
		singleThreadedRadioButton.setSelected(true);
		singleThreadedRadioButton.setBounds(71, 43, 118, 31);
		panel.add(singleThreadedRadioButton);
		
		JRadioButton multiThreadedRadioButton = new JRadioButton("Multi Threaded");
		multiThreadedRadioButton.setFont(new Font("Serif", Font.PLAIN, 11));
		multiThreadedRadioButton.setBackground(Color.BLACK);
		multiThreadedRadioButton.setForeground(Color.WHITE);
		multiThreadedRadioButton.setBounds(188, 43, 120, 31);
		panel.add(multiThreadedRadioButton);
		
		ButtonGroup threadedGroup = new ButtonGroup();
		threadedGroup.add(singleThreadedRadioButton);
		threadedGroup.add(multiThreadedRadioButton);
	
		startButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				startButton.setEnabled(false);
				startButton.setBackground(Color.GRAY);
				stopButton.setEnabled(true);
				stopButton.setBackground(Color.RED);
				outputTextPane.setText("");
				continueFindingPrimes = true;
				if (singleThreadedRadioButton.isSelected())
				{
					final long startTime = System.currentTimeMillis();
					SingleThreadController singleThreadedController = new SingleThreadController(view, continueFindingPrimes);
					singleThreadedController.determinePrimes(Integer.parseInt(maxPrimeTextField.getText()));
					final long durationMillis = (System.currentTimeMillis() - startTime);
					String durationString = Long.toString(durationMillis);
					ammendOutput(" Duration: " + durationString + "ms");
					startButton.setEnabled(true);
					startButton.setBackground(new Color(0, 255, 0));
					stopButton.setEnabled(false);
				}
				else
				{
					executor = Executors.newFixedThreadPool(1);
					executor.submit(new MultiThreadController(view, maxPrimeTextField.getText(), numThreadsTextField.getText(), continueFindingPrimes));
					executor.shutdown();
				}
			}
		});
		
		stopButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				startButton.setEnabled(true);
				startButton.setBackground(new Color(0, 255, 0));
				stopButton.setEnabled(false);
				stopButton.setBackground(Color.GRAY);
				continueFindingPrimes = false;
			}
		});
		
	}
	
	/*
	 * Displays output on the output pane
	*/
	public void displayOutput(String displayString)
	{
		outputTextPane.setText(displayString);
	}
	
	/*
	 * Adds to end of given output
	 */
	public void ammendOutput(String displayString)
	{
		try {
			outputPaneDoc.insertString(outputPaneDoc.getLength(), displayString, simpleAttributes);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	public void resetGuiButtons()
	{
		startButton.setEnabled(true);
		startButton.setBackground(new Color(0, 255, 0));
		stopButton.setEnabled(false);
		stopButton.setBackground(Color.GRAY);
	}
	
	public boolean isContinueFindingPrimes()
	{
		return continueFindingPrimes;
	}
}
