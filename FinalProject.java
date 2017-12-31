import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.io.*;
import java.io.File;
import java.util.Scanner;


public class FinalProject implements ActionListener, MouseListener{
	
	JFrame jfrm;
	JTable tblData;
	DefaultTableModel tblModel;
	JComboBox cmbSearch;
	JTextField txtSearch;
	MyPopUpDialog myDialog;
	JFileChooser jfc;
	
	public FinalProject(){
		
	// Basic Setup	
	JFrame jfrm = new JFrame("My Final Project");
	jfrm.setSize(550, 625);
	jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jfrm.setLayout(new FlowLayout());
	jfrm.setLocationRelativeTo(null);

	//lblOutput = new JLabel();
	//lblOutput.setText("This is where you see output.");
	//jfrm.add(lblOutput);
	
	//Menu Setup
	JMenuBar menubar = new JMenuBar();

	//Menu Options
	JMenu filemenu = new JMenu("File");
	JMenu helpmenu = new JMenu("Help");
	
	//Menu Buttons
	JMenuItem open = new JMenuItem("Open");
	JMenuItem export = new JMenuItem("Export");
	JMenuItem quit = new JMenuItem("Quit");
	JMenuItem about = new JMenuItem("About");
	
	//Menu Button ActionListeners
	open.addActionListener(this);
	quit.addActionListener(this);
	about.addActionListener(this);
	export.addActionListener(this);

	//Menu Button Shortcuts
	filemenu.setMnemonic(KeyEvent.VK_F);
	helpmenu.setMnemonic(KeyEvent.VK_H);
	open.setMnemonic(KeyEvent.VK_O);
	open.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_O,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
			);
	export.setMnemonic(KeyEvent.VK_E);
	export.setAccelerator(
			KeyStroke.getKeyStroke(	KeyEvent.VK_E,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
			);
	quit.setMnemonic(KeyEvent.VK_Q);
	quit.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_Q,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
			);
	about.setMnemonic(KeyEvent.VK_A);
	about.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_A,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask())
			);
	
	//Add Buttons to Menu Options
	filemenu.add(open);
	filemenu.add(export);
	filemenu.addSeparator();
	filemenu.add(quit);
	helpmenu.add(about);
	
	//Add Menu Options to Menu Bar
	menubar.add(filemenu);
	menubar.add(helpmenu);
	
	//Set Menu Bar
	jfrm.setJMenuBar(menubar);

	
	
	//Combo Box Setup
	cmbSearch = new JComboBox();
	
	cmbSearch.addItem("Row ID");
	cmbSearch.addItem("First Name");
	cmbSearch.addItem("Last Name");
	cmbSearch.addItem("Cuny ID");
	cmbSearch.addItem("GPA");
	cmbSearch.addItem("Venus Login");
	
	cmbSearch.setActionCommand("Search");
	cmbSearch.getSelectedIndex();
	
	jfrm.add(cmbSearch);
	
	//Text Box Setup
	txtSearch = new JTextField(15);

	txtSearch.setActionCommand("Search");
	txtSearch.addActionListener(this);
	
	jfrm.add(txtSearch);
	
	//Button Setup
	
	JButton addbtn = new JButton("Add");
	JButton deletebtn = new JButton("Delete");
	
	jfrm.add(addbtn);
	
	addbtn.addActionListener(this);
	
	jfrm.add(deletebtn);
	
	//Delete Button
	deletebtn.addActionListener(
			new ActionListener(){

				public void actionPerformed(ActionEvent e) 
				{
					if(tblData.getSelectedRow() >= 0 )
					{
						StringBuilder sb = new StringBuilder();
						int rowID = tblData.convertRowIndexToModel(tblData.getSelectedRow());
						tblModel.getColumnCount();
						
						for(int i = 0; i < tblModel.getColumnCount(); i++)
						{
							
							sb.append("\r\n" + tblModel.getColumnName(i) + " : " +  tblModel.getValueAt(rowID, i) + " ");
						}
						
	int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this data?" + sb.toString());
	if(result == JOptionPane.OK_OPTION){
		tblModel.removeRow(tblData.convertRowIndexToModel(tblData.getSelectedRow()));
						}

					}
				}
			}
			);
	
	
	//Table Setup
	tblData = new JTable();
	tblData.setAutoCreateRowSorter(true);
	tblData.addMouseListener(this);
	JScrollPane jspData = new JScrollPane(tblData);
	
	
	//DefaultTableModel Override
	
	tblData.setModel(new DefaultTableModel(){
		
		 @Override
	    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
	    
		 @Override
        public Class getColumnClass(int column) 
        {
            switch (column) 
            {
                case 0:
                    	return Integer.class; 
                case 1:
                		return String.class;   

                default:
                    return String.class;
            }
        }

		}
	);
	
	
	//Table Setup Part 2
	
	tblModel = (DefaultTableModel)tblData.getModel();
	tblModel.addColumn("Row ID");
	tblModel.addColumn("First Name");
	tblModel.addColumn("Last Name");
	tblModel.addColumn("Cuny ID");
	tblModel.addColumn("GPA");
	tblModel.addColumn("Venus Login");
	//tblModel.addRow(new Object[]{"1","John", "Doe","12345678", "2.4", "DoJo5678" });
	//tblModel.addRow(new Object[]{"2","Kevin", "Le","23342299", "4.0", "LeKe2299" });
	//tblModel.addRow(new Object[]{"3","Simon", "Chen","87654321", "3.3", "ChSi4321" });

	jfrm.add(jspData);

			
	//Instantiate file chooser
	jfc = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Text/Java files", "txt", "java");
	jfc.setFileFilter(filter);
			
	
	//Export Button
	
	JButton exportdata = new JButton("Export File");
	exportdata.addActionListener(this);
	
	jfrm.add(exportdata);
	
	jfrm.setVisible(true);
	
}
	

	public void actionPerformed(ActionEvent ae){
		
		switch(ae.getActionCommand()){
		
		case "Search":
			System.out.println("Search is activated. User is trying to search for " 
					+ this.cmbSearch.getSelectedItem() 
					+ " with data " + this.txtSearch.getText());
			FilterRowBasedOnSearch();
		break;
		
		case "Quit":
			System.exit(0);
		break;
		
		case "Modify":
			HandleModifyPersonEvent();
		break;
		
		case "Add":
			HandleAddPersonEvent();
		break;
		
		case "Export":
			ExportingFile();			
		break;
		
		case "Export File":
			ExportingFile();			
		break;
		
		case "Open":
			
			jfc.setDialogTitle("Choose a File to Load: ");
			
			int jfcResult = jfc.showOpenDialog(null);

			if(jfcResult == JFileChooser.APPROVE_OPTION)
			{

				try
				{
					
				int linecounter = 0;	
					
				Scanner filereader = new Scanner(jfc.getSelectedFile());
					
				while(filereader.hasNextLine()){
				
				linecounter++;
				
					
				String nextline = filereader.nextLine();
															
				Scanner linereader = new Scanner(nextline);
				Scanner linedelimiter = linereader.useDelimiter(",");
				
				String fn = "";
				String ln = "";
				String cID = "";
				String gpa = "";
				String vID = "";
				
				if(linereader.hasNext()){ fn = linereader.next();}
				if(linereader.hasNext()){ ln = linereader.next();}
				if(linereader.hasNext()){ cID = linereader.next();}
				if(linereader.hasNext()){ gpa = linereader.next();}				
				if(linereader.hasNext()){ vID = linereader.next();}
				
				linereader.close();
				linedelimiter.close();
				
				if(fn.equals("") || ln.equals("") || cID.equals("") || gpa.equals("") || vID.equals("")){
				
				}
				else{
					tblModel.addRow(new Object[]{"" + linecounter, fn , ln , cID , gpa , vID});
				}
				
				
				
			}
				
				filereader.close();
					
		}
				
				
				catch (FileNotFoundException ex){
					System.out.println("File is not found");
				} 
				catch (IOException e1){

					System.out.println("There was an IO Exception that was caught. Error: " /*+ e1.getMessage()*/);
					//e1.printStackTrace();
				}
			}	
			else{
				System.out.println("No file selected.");
			}
		break;
		
		case "About":
			JOptionPane.showMessageDialog(this.jfrm, "This is a program written to process student data. "
					+ "\n" + "Use Add to add a student to the system." + "\n" + "Use Remove to remove a student. Double click a row to modify it. "
					+ "\n" + "Files can be imported and exported through their respective buttons.");
		break;
		
		
		}

				
	}
	
	private int GetCurrentComboSearchIndex() { return this.cmbSearch.getSelectedIndex(); }
	private String GetCurrentTextSearch() { return this.txtSearch.getText();}
	
	private void FilterRowBasedOnSearch()
	{
		RowFilter<Object,Object> rowFilter = new RowFilter<Object,Object>()
		{
			@Override
			public boolean include(javax.swing.RowFilter.Entry<? extends Object, ? extends Object> entry) {
				boolean shouldInclude = false;
				
				switch(GetCurrentComboSearchIndex())
			     {
				
			     	case 0: //Search at column index 0
			     		if(CheckInteger(GetCurrentTextSearch()) 
			     				&& Integer.parseInt(entry.getStringValue(GetCurrentComboSearchIndex()).trim()) 
			     				== Integer.parseInt(GetCurrentTextSearch())
			     				
			     				)
			     		{
			     			shouldInclude = true;
			     		}
			     	break;
			     	
			     	case 1: //search at column index 1
			     		if(
			     				entry.getStringValue( GetCurrentComboSearchIndex() ).toLowerCase().indexOf
			     				(
			     					GetCurrentTextSearch().toLowerCase()
			     				) >= 0
			     		)
			     		{
			     			shouldInclude = true;
			     		}
			     	break;
			     				     	
			      	case 2: //search at column index 2
			     		if(
			     				entry.getStringValue( GetCurrentComboSearchIndex() ).toLowerCase().indexOf
			     				(
			     					GetCurrentTextSearch().toLowerCase()
			     				) >= 0
			     		)
			     		{
			     			shouldInclude = true;
			     		}
			     	break;
			     	
			      	case 3: //search at column index 3
			     		if(CheckInteger(GetCurrentTextSearch()) 
			     				&& Integer.parseInt(entry.getStringValue(GetCurrentComboSearchIndex()).trim()) 
			     				== Integer.parseInt(GetCurrentTextSearch()))
			     		{
			     			shouldInclude = true;
			     		}
			     	break;
			     	
			      	case 4: //search at column index 4
			      		//System.out.println(CheckInteger(GetCurrentTextSearch()) );
			      		//System.out.print(Integer.parseInt(entry.getStringValue(GetCurrentComboSearchIndex()).trim()));
			     		if(CheckDouble(GetCurrentTextSearch()) 
			     				&& Double.parseDouble(entry.getStringValue(GetCurrentComboSearchIndex()).trim()) 
			     				<= Double.parseDouble(GetCurrentTextSearch()))
			     		{
			     			shouldInclude = true;
			     		}
			     	break;
			     	
			      	case 5: //search at column index 5
			     		if(
			     				entry.getStringValue( GetCurrentComboSearchIndex() ).toLowerCase().indexOf
			     				(
			     					GetCurrentTextSearch().toLowerCase()
			     				) >= 0
			     		)
			     		{
			     			shouldInclude = true;
			     		}
			     	break;
			     	
			     }
				
				
				
				
				return shouldInclude;
			}
		};
		TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) tblData.getRowSorter();
		sorter.setRowFilter(rowFilter);
	}

	
	
	protected void HandleModifyPersonEvent(){
		
		if(myDialog == null)
		{
			myDialog = new MyPopUpDialog(this.tblData);
		}

		if(tblData.getSelectedRow() >= 0)
		{
			MyPopUpDialog.State = 0;
			myDialog.PopulateDataFromSelectedRowInTable();
			myDialog.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(this.jfrm, 
					"No row was selected in JTable. Please select a row and try again.",
					"No Row Selected",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	protected void HandleAddPersonEvent(){
		
		if(myDialog == null)
		{
			myDialog = new MyPopUpDialog(this.tblData);
		}
			
			MyPopUpDialog.State = 1;
			myDialog.InsertBlankInTable();
			myDialog.setVisible(true);
		
	}
	
	
	
	// Integer Checker for CUNYID, Row Number
	
	public static boolean CheckInteger(String value)
	{
		boolean isValidInteger = false;
		try
		{
			Integer.parseInt(value);
			isValidInteger = true;
		}
		catch(Exception ex)
		{
		}
		
		return isValidInteger;
	}
	
	//Double Checker for GPA
	
	public static boolean CheckDouble(String value)
	{
		boolean isValidDouble = false;
		try
		{
			Double.parseDouble(value);
			isValidDouble = true;
		}
		catch(Exception ex)
		{
		}
		
		return isValidDouble;
	}
	
	public void ExportingFile(){

		jfc.setDialogTitle("Saving File: ");

		int jfcSave = jfc.showSaveDialog(null);
		
		if (jfcSave == JFileChooser.APPROVE_OPTION) {
	
			try{
				
			File file = jfc.getSelectedFile();
			if(!file.exists()){
				file.createNewFile();
			}
				
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			
			for(int a = 0 ; a < tblModel.getRowCount(); a++){
			for(int b = 1 ; b < tblModel.getColumnCount() ; b++){
				
				String boxreader;
				
				boxreader = tblModel.getValueAt(a,b) + "";
				
				fw.write(boxreader);
				if(b != tblModel.getColumnCount()-1){
				fw.write(",");
				}
			}
			fw.write("\r\n");
			}
			fw.close();
			
			JOptionPane.showMessageDialog(null, "Successfully outputted.");
			}
			catch(Exception a){
				
			}
			}
		
		
	}





	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			HandleModifyPersonEvent();
		}
	}





	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	
	
	
	
	
}
