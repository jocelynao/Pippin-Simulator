package project;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MenuBarBuilder implements Observer 
{
	private JMenuItem assemble = new JMenuItem("Assemble Source...");
	private JMenuItem load = new JMenuItem("Load Program...");
	private JMenuItem exit = new JMenuItem("Exit");
	private JMenuItem go = new JMenuItem("Go");
	private JMenuItem job0 = new JMenuItem("Job 0");
	private JMenuItem job1 = new JMenuItem("Job 1");
	private JMenuItem job2 = new JMenuItem("Job 2");
	private JMenuItem job3 = new JMenuItem("Job 3");
	private GUIMediator gui;

	public MenuBarBuilder(GUIMediator gm) 
	{
		gui = gm;
		gui.addObserver(this);
	}

	public JMenu createFileMenu()
	{
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);

		assemble.setMnemonic(KeyEvent.VK_M);
		assemble.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		assemble.addActionListener(e -> gui.assembleFile());
		menu.add(assemble);

		load.setMnemonic(KeyEvent.VK_L);
		load.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, ActionEvent.CTRL_MASK));
		load.addActionListener(e -> gui.loadFile());
		menu.add(load);

		menu.addSeparator();

		exit.setMnemonic(KeyEvent.VK_E);
		exit.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		exit.addActionListener(e -> gui.exit());
		menu.add(exit);

		return menu;		
	}

	//Make a second method JMenu createExecuteMenu(), which is intended to give a way to call gui.execute().
	//You should be able to figure out the accelerators' KeyEvent.VK_? to use to get this picture:

	public JMenu createExecuteMenu()
	{
		JMenu menu = new JMenu("Execute");
		menu.setMnemonic(KeyEvent.VK_X);

		go.setMnemonic(KeyEvent.VK_G);
		go.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		go.addActionListener(e -> gui.execute());
		menu.add(go);

		return menu;		
	}

	//JMenu createJobsMenu(). Here is the desired menu. The first label has the listener e -> gui.changeToJob(0) 
	//and the second e -> gui.changeToJob(1), etc.<br />
	public JMenu createJobsMenu()
	{
		JMenu menu = new JMenu("Change Job");
		menu.setMnemonic(KeyEvent.VK_J);

		job0.setMnemonic(KeyEvent.VK_0);
		job0.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_0, ActionEvent.CTRL_MASK));
		job0.addActionListener(e -> gui.changeToJob(0));
		menu.add(job0);

		job1.setMnemonic(KeyEvent.VK_1);
		job1.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		job1.addActionListener(e -> gui.changeToJob(1));
		menu.add(job1);

		job2.setMnemonic(KeyEvent.VK_2);
		job2.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		job2.addActionListener(e -> gui.changeToJob(2));
		menu.add(job2);

		job3.setMnemonic(KeyEvent.VK_3);
		job3.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		job3.addActionListener(e -> gui.changeToJob(3));
		menu.add(job3);

		return menu;
	}

	// This is used to enable 
	//and disable the JMenuItems. The item Exit is left enabled at all times. However, we use

	//to switch the assemble JMenuItem between enabled and disabled states.
	//Do the same for the load, go, job0, job1, <tt>job2, and <tt>job3 JMenuItems, calling the appropriate
	//method from States. The names of the get methods in States should tell you which ones to use, 
	//except go uses getStepActive, which is used again in a later part of the project
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		assemble.setEnabled(gui.getCurrentState().getAssembleFileActive());

		load.setEnabled(gui.getCurrentState().getLoadFileActive());
		go.setEnabled(gui.getCurrentState().getStepActive());
		job0.setEnabled(gui.getCurrentState().getChangeJobActive());
		job1.setEnabled(gui.getCurrentState().getChangeJobActive());
		job2.setEnabled(gui.getCurrentState().getChangeJobActive());
		job3.setEnabled(gui.getCurrentState().getChangeJobActive());
	}

}
