import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class MatriSongGui extends JFrame {
	// Parameters chosen by the user to analyze the song 
	private static final long serialVersionUID = 1L;
	private int numOfNotes = 1;
	private int numOfWords = 1;
	private String filePath = "";
	private String melodyFilePath = "";
	private String lyricFilePath = "";
	private String songMode = "EXISTING";
	private final ButtonGroup modeButtonGroup = new ButtonGroup();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// for presentation:
//		MatriSong.singleWordMatch = "ON";
//		MatriSong.singleWordMatch = "OFF";
//		MatriSong.show(MatriSong.arrayLyrToMatrix(MatriSong.lyricsToArray("src/Songs/Baa Baa L.txt")), null);
//		MatriSong.show(MatriSong.arrayLyrToMatrix(MatriSong.lyricsToArray("src/Songs/Formation L.txt")), null);

		
//		LYRICS IN ORANGE!
//		MELODY IN GREEN!
		
		//************************************************************************************
		
//		/*
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MatriSongGui frame = new MatriSongGui();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
//		*/
		
	}

	/**
	 * Create the frame.
	 */
	public MatriSongGui() {
		setTitle("MartiSong");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 388, 333);

		// Song mode option
		JLabel lblMode = new JLabel("Select song mode: ");
		lblMode.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JRadioButton rdbtnOriginal = new JRadioButton("Original");
		modeButtonGroup.add(rdbtnOriginal);
		
		JRadioButton rdbtnExisting = new JRadioButton("Existing");
		modeButtonGroup.add(rdbtnExisting);
		rdbtnExisting.setSelected(true);

		JPanel existingModePanel = new JPanel();
		JPanel originalModePanel = new JPanel();
		
		// We use two panels to show the song mode option chosen by the user and hide the other option
		existingModePanel.setVisible(true);
		originalModePanel.setVisible(false);
		
		// Sets the song mode parameter and show/hide the panels according to the user choice 
		ActionListener modeActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				JRadioButton button = (JRadioButton) actionEvent.getSource();
				if (button == rdbtnExisting) {

//					System.out.println("rdbtnExisting button is selected");
					existingModePanel.setVisible(true);
					originalModePanel.setVisible(false);
					songMode = "EXISTING";
				} else if (button == rdbtnOriginal) {

//					System.out.println("rdbtnOriginal button is selected");
					originalModePanel.setVisible(true);
					existingModePanel.setVisible(false);
					songMode = "ORIGINAL";
				}
			}
		};
		rdbtnExisting.addActionListener(modeActionListener);
		rdbtnOriginal.addActionListener(modeActionListener);
		
		JButton btnSelectLyricFile = new JButton("Select lyrics file");
		btnSelectLyricFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SampleJFileChooser choseFile = new SampleJFileChooser();
				lyricFilePath = choseFile.getFilePath();
//				System.out.println("Selected lyric file: " + lyricFilePath);
			}
		});
		originalModePanel.add(btnSelectLyricFile);

		JButton btnSelectMelodyFile = new JButton("Select melody file");
		btnSelectMelodyFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SampleJFileChooser choseFile = new SampleJFileChooser();
				melodyFilePath = choseFile.getFilePath();
//				System.out.println("Selected melody file: " + melodyFilePath);
			}
		});
		originalModePanel.add(btnSelectMelodyFile);

		JButton btnSelectFile = new JButton("Select song file");
		btnSelectFile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SampleJFileChooser choseFile = new SampleJFileChooser();
				filePath = choseFile.getFilePath();
//				System.out.println("Selected song file: " + filePath);
			}
		});
		existingModePanel.add(btnSelectFile);
		// End of song mode parameter
		
		// Sets the number of words to analyze parameter
		JLabel lblNumberOfWords = new JLabel("Number of consecutive words to catch:");
		lblNumberOfWords.setFont(new Font("Tahoma", Font.PLAIN, 11));

		final JComboBox<String> comboBoxWords = new JComboBox<String>();
		comboBoxWords.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2"}));
		comboBoxWords.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//JComboBox<String> combo = (JComboBox<String>) event.getSource();
				String selectedNumberOfWords = (String) comboBoxWords.getSelectedItem();
				numOfWords = Integer.parseInt(selectedNumberOfWords);
//				System.out.println("Number of words to analyze is: "+numOfWords);
			}
		});
		// End of number of words to analyze option
		
		//Sets the number of notes to analyze parameter
		JLabel lblNumberOfNotes = new JLabel("Number of consecutive notes to catch: ");
		lblNumberOfNotes.setFont(new Font("Tahoma", Font.PLAIN, 11));

		String[] numberOfNotes = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
		final JComboBox<String> comboBoxNotes = new JComboBox<String>(numberOfNotes);
		comboBoxNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			//	JComboBox<String> combo = (JComboBox<String>) event.getSource();
				String selectedNumberOfNotes = (String) comboBoxNotes.getSelectedItem();
				numOfNotes = Integer.parseInt(selectedNumberOfNotes);
//				System.out.println("Number of notes to analyze is: "+numOfNotes);
			}
		});
		// End of number of notes to analyze parameter
		
		// Start analyzing the song according to the options chosen by the user
		JButton btnStartAnalyzing = new JButton("Start analyzing");
		btnStartAnalyzing.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnStartAnalyzing.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
//				System.out.println("Started analyzing!");
				MatriSong.filePath = filePath;
				MatriSong.lyricsFilePath = lyricFilePath;
				MatriSong.melodyFilePath = melodyFilePath;
				MatriSong.numOfNotes = numOfNotes;
				if (numOfWords == 1)
					MatriSong.singleWordMatch = "ON";
				else
					MatriSong.singleWordMatch = "OFF";
				MatriSong.songMode = songMode;
				try {
					MatriSong.matrixSong();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		// End of start analyzing
		
		// GUI
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMode)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(rdbtnExisting)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(rdbtnOriginal, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
									.addGap(63)))
							.addGap(113))
						.addComponent(lblNumberOfWords)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(existingModePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(originalModePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(73))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNumberOfNotes)
							.addContainerGap(227, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(comboBoxNotes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(325, Short.MAX_VALUE))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(134)
					.addComponent(btnStartAnalyzing)
					.addContainerGap(196, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBoxWords, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(397, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMode)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnOriginal)
						.addComponent(rdbtnExisting))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(existingModePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(originalModePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNumberOfWords)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxWords, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(lblNumberOfNotes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBoxNotes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
					.addComponent(btnStartAnalyzing)
					.addGap(44))
		);
		getContentPane().setLayout(groupLayout);
		// End of GUI
	}
}

