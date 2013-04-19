package dragonfin.tournament;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame
{
	static ResourceBundle strings = ResourceBundle.getBundle("dragonfin.tournament.GuiStrings");
	static final String PRODUCT_NAME = strings.getString("PRODUCT");
	static final String EXTENSION = "tourney";

	File currentFile;

	public MainWindow()
	{
		setTitle(PRODUCT_NAME);

		makeMenu();
		pack();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				closeWindow();
			}
			});
	}

	void makeMenu()
	{
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(strings.getString("menu.file"));
		menuBar.add(fileMenu);

		JMenuItem menuItem;
		menuItem = new JMenuItem(strings.getString("menu.file.new"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onNewFileClicked();
			}});
		fileMenu.add(menuItem);

		menuItem = new JMenuItem(strings.getString("menu.file.open"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onOpenFileClicked();
			}});
		fileMenu.add(menuItem);

		menuItem = new JMenuItem(strings.getString("menu.file.save"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onSaveFileClicked();
			}});
		fileMenu.add(menuItem);

		menuItem = new JMenuItem(strings.getString("menu.file.save_as"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onSaveAsFileClicked();
			}});
		fileMenu.add(menuItem);

		menuItem = new JMenuItem(strings.getString("menu.file.exit"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				closeWindow();
			}});
		fileMenu.add(menuItem);

		setJMenuBar(menuBar);
	}

	void closeWindow()
	{
		if (!maybeSave()) {
			return;
		}

		dispose();
	}

	boolean needsSaved()
	{
		//TODO
		return false;
	}

	boolean maybeSave()
	{
		if (needsSaved()) {

			int rv = JOptionPane.showConfirmDialog(
					this,
					strings.getString("main.save_query"),
					PRODUCT_NAME,
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.WARNING_MESSAGE);
			if (rv == JOptionPane.CANCEL_OPTION)
				return false;

			if (rv == JOptionPane.YES_OPTION) {
				return onSaveFileClicked();
			}
		}
		return true;
	}

	void onNewFileClicked()
	{
		//TODO
	}

	void doSave(File file)
	{
		//TODO
	}

	void onOpenFileClicked()
	{
		if (!maybeSave()) {
			return;
		}

		try
		{
			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter(strings.getString("filetype."+EXTENSION), EXTENSION);
			fc.setFileFilter(filter1);

			int rv = fc.showOpenDialog(this);
			if (rv == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				//TODO

				currentFile = file;
				refresh();
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, e, strings.getString("main.error_caption"),
				JOptionPane.ERROR_MESSAGE);
		}
	}

	/** @return true if file was saved, false if user canceled */
	boolean onSaveFileClicked()
	{
		if (currentFile == null) {
			return onSaveAsFileClicked();
		}

		//TODO
		return true;
	}

	/** @return true if file was saved, false if user canceled */
	boolean onSaveAsFileClicked()
	{
		try {

			JFileChooser fc = new JFileChooser();
			FileNameExtensionFilter filter1 = new FileNameExtensionFilter(strings.getString("filetype."+EXTENSION), EXTENSION);
			fc.setFileFilter(filter1);
			int rv = fc.showSaveDialog(this);

			if (rv == JFileChooser.APPROVE_OPTION) {
				currentFile = fc.getSelectedFile();
				if (!currentFile.getName().endsWith("."+EXTENSION)) {
					currentFile = new File(currentFile.getPath()+"."+EXTENSION);
				}
				doSave(currentFile);
				refresh();
				return true;
			}
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(this, e,
				strings.getString("main.error_caption"),
				JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	void refresh()
	{
		if (currentFile != null) {
			String fileName = currentFile.getName();
			if (fileName.endsWith("."+EXTENSION)) {
				fileName = fileName.substring(0, fileName.length() - 1 - EXTENSION.length());
			}
			setTitle(MessageFormat.format(
					strings.getString("main.caption_named_file"),
					fileName));
		}
		else {
			setTitle(strings.getString("main.caption_unnamed_file"));
		}
	}

	public static void main(String [] args)
	{
		SwingUtilities.invokeLater( new Runnable() {
			public void run() {
				new MainWindow().setVisible(true);
			}});
	}
}