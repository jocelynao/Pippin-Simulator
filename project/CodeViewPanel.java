package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class CodeViewPanel implements Observer
{
/*There are private fields MachineModel model, Code code, JScrollPane scroller, JTextField[] codeText, JTextField[] codeHex,
 *  both  instantiated as arrays of length Code.CODE_MAX, and int previousColor, initially -1. The constructor has two parameters:
 *   GUIMediator gui, MachineModel mdl. mdl gives the value of model and then call gui.addObserver(this).*/
	private MachineModel model;
	private Code code;
	private JScrollPane scroller;
	private JTextField[] codeText = new JTextField[Code.CODE_MAX];
	private JTextField[] codeHex = new JTextField[Code.CODE_MAX];
	private int previousColor = -1;
	
	public CodeViewPanel(GUIMediator gui, MachineModel mdl)
	{
		this.model = mdl;
		gui.addObserver(this);
	}
	
	//Make a method JComponent createCodeDisplay(), which is very similar to JComponent createMemoryDisplay() in MemoryViewPanel.
	//All the JPanels are the same, with the same layouts and are added to each other and the scroller int the same way.
	
	public JComponent createCodeDisplay()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		Border border = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), 
				"Code Memory View",
				TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION);
		panel.setBorder(border);
		JPanel innerPanel = new JPanel();
//		innerPanel.setBorder(border);
		innerPanel.setLayout(new BorderLayout());
		JPanel numPanel = new JPanel();
		JPanel textPanel = new JPanel();
		JPanel hexPanel = new JPanel();
		numPanel.setLayout(new GridLayout(0, 1));
		textPanel.setLayout(new GridLayout(0, 1));
		hexPanel.setLayout(new GridLayout(0, 1));
		innerPanel.add(numPanel, BorderLayout.LINE_START);
		innerPanel.add(textPanel, BorderLayout.CENTER);
		innerPanel.add(hexPanel, BorderLayout.LINE_END);
		
		/*The only differences are that (i) the title in the Border says "Code Memory View" (ii) the for loop is from
		 *  0 to Code.CODE_MAX, (iii) the arrays codeText and codeHex do not need to be instantiated here, since they 
		 *  are instantiated at the start of the class, (iv) you do instantiate each codeText[i] and codeHex[i] to JTextField(10)
		 *  , (v) the JPanel textPanel replaces decimalPanel (vi) codeText[i] is added to textPanel instead of dataDecimal[i-lower]
		 *   and codeHex[i] is added to hexPanel instead of dataHex[i-lower], (vii) textPanel is added to innerPanel at
		 *    BorderLayout.CENTER and hexPanel is added at BorderLayout.LINE_END.
		 */
		

		for (int i = 0; i < Code.CODE_MAX; i++){
			numPanel.add(new JLabel(i+": ", JLabel.RIGHT));
			codeText[i] = new JTextField(10);
			codeHex[i] = new JTextField(10);
			textPanel.add(codeText[i]);
			hexPanel.add(codeHex[i]);
		}
		scroller = new JScrollPane(innerPanel);
		panel.add(scroller);
		return panel;
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 != null && arg1.equals("Load Code")) {
			code = model.getCode();
			int offset = model.getCurrentJob().getStartcodeIndex();
			for(int i = offset; 
					i < offset + model.getCurrentJob().getCodeSize(); i++) {
				codeText[i].setText(code.getText(i));
				codeHex[i].setText(code.getHex(i));
			}	
			previousColor = model.getpCounter();			
			codeHex[previousColor].setBackground(Color.YELLOW);
			codeText[previousColor].setBackground(Color.YELLOW);
	//CHANGE AS OF 5/4/17
		} else if(arg1 != null && arg1 instanceof String && ((String)arg1).startsWith("Clear")) {
			int offset = model.getCurrentJob().getStartcodeIndex();
			int codeSize = Integer.parseInt(((String)arg1).substring(6).trim());
			for(int i = offset; 
					i < offset + codeSize; i++) {
				codeText[i].setText("");
				codeHex[i].setText("");
			}	
	// END OF CHANGE
			if(previousColor >= 0 && previousColor < Code.CODE_MAX) {
				codeText[previousColor].setBackground(Color.WHITE);
				codeHex[previousColor].setBackground(Color.WHITE);
			}
			previousColor = -1;
		}		
		if(this.previousColor >= 0 && previousColor < Code.CODE_MAX) {
			codeText[previousColor].setBackground(Color.WHITE);
			codeHex[previousColor].setBackground(Color.WHITE);
		}
		previousColor = model.getpCounter();
		if(this.previousColor >= 0 && previousColor < Code.CODE_MAX) {
			codeText[previousColor].setBackground(Color.YELLOW);
			codeHex[previousColor].setBackground(Color.YELLOW);
		} 
		if(scroller != null && code != null && model!= null) {
			JScrollBar bar= scroller.getVerticalScrollBar();
			int pc = model.getpCounter();
			if(pc > 0 && pc < Code.CODE_MAX && codeHex[pc] != null) {
				Rectangle bounds = codeHex[pc].getBounds();
				bar.setValue(Math.max(0, bounds.y - 15*bounds.height));
			}
		}
	}

	public static void main(String[] args) {
		GUIMediator view = new GUIMediator(); 
//		MachineModel model = new MachineModel();
//		CodeViewPanel panel = new CodeViewPanel(view, model);
//		JFrame frame = new JFrame("TEST");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(400, 700);
//		frame.setLocationRelativeTo(null);
//		frame.add(panel.createCodeDisplay());
//		frame.setVisible(true);
//		int size = Integer.parseInt(Loader.load(model, new File("out.pexe"), 0, 0));
//		model.getCurrentJob().setCodeSize(size);
//		panel.update(view, "Load Code");
	}
	
}