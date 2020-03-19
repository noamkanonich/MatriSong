
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Provides support for standard platform file dialogs.
 * @author Lustig
 *
 */

public class SampleJFileChooser {
	private String filePath;

	public SampleJFileChooser(){

		JFileChooser jFileChooser = new JFileChooser();
		jFileChooser.setCurrentDirectory(null);

		int result = jFileChooser.showOpenDialog(new JFrame());

		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jFileChooser.getSelectedFile();
			filePath = selectedFile.getAbsolutePath();
		}
	}
	
	public String getFilePath()
	{
		return filePath;
	}

}