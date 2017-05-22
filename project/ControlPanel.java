package project;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class ControlPanel implements Observer
{
	private GUIMediator gui;
	private JButton stepButton = new JButton("Step");
	private JButton clearButton = new JButton("Clear");
	private JButton runButton = new JButton("Run/Pause");
	private JButton reloadButton = new JButton("Reload");
	
	
	//The constructor has one parameter to set the value of gui and also calls gui.addObserver(this).
	public ControlPanel(GUIMediator gui)
	{
		this.gui = gui;
		gui.addObserver(this);
	}
	
	//The method public JComponent createControlDisplay() first makes a new JPanel panel. It sets the layout of panel to public
	//new GridLayout(1,0), which allows any number of components on one row. Here is the code for the stepButton:
	public JComponent createControlDisplay()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		
		stepButton.setBackground(Color.WHITE);
		stepButton.addActionListener(e -> gui.step());
		panel.add(stepButton);
		
		clearButton.setBackground(Color.WHITE);
		clearButton.addActionListener(e -> gui.clearJob());
		panel.add(clearButton);
		
		runButton.setBackground(Color.WHITE);
		runButton.addActionListener(e -> gui.toggleAutoStep());
		panel.add(runButton);
		
		reloadButton.setBackground(Color.WHITE);
		reloadButton.addActionListener(e -> gui.reload());
		panel.add(reloadButton);
		
		JSlider slider = new JSlider(5,1000);
		slider.addChangeListener(e -> gui.setPeriod(slider.getValue()));
		panel.add(slider);
		
		return panel;
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) 
	{
//		System.out.println(gui.getCurrentState().getRunPauseActive());
//		System.out.println(gui.getCurrentState());
//		System.out.println(gui.getCurrentState().states[5]);
		stepButton.setEnabled(gui.getCurrentState().getStepActive());
		runButton.setEnabled(gui.getCurrentState().getRunPauseActive());
		clearButton.setEnabled(gui.getCurrentState().getClearActive());
		reloadButton.setEnabled(gui.getCurrentState().getReloadActive());
		
		
	}
}
