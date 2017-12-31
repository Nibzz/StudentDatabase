import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MyPopUpDialog extends JDialog implements ActionListener, KeyListener
{

	public static int State = 0;
	
	// 0 = Modify, 1 = Add
	
	private JTable myReferencedTable;
	private DefaultTableModel myReferencedModel;
	//JButton btnOperation;
	
	JTextField firstName = new JTextField(15);
	JTextField lastName = new JTextField(15);
	JTextField cunyID = new JTextField(15);
	JTextField gpa = new JTextField(15);
	JTextField venusID = new JTextField(15);
	
	public MyPopUpDialog(JTable newTable)
	{
		this.myReferencedTable = newTable;
		this.setSize(350,200);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		this.setTitle("Add / Modify Student");
		
		venusID.setEditable(false);
		
		JLabel lblfirstName = new JLabel("First Name: ");
		JLabel lbllastName = new JLabel("Last Name: ");
		JLabel lblcunyID = new JLabel("Cuny ID: ");
		JLabel lblgpa = new JLabel("GPA: ");
		JLabel lblvenusID = new JLabel("Venus ID: ");
		
		lblfirstName.setPreferredSize(new Dimension(100,11));
		lbllastName.setPreferredSize(new Dimension(100,11));
		lblcunyID.setPreferredSize(new Dimension(100,11));
		lblgpa.setPreferredSize(new Dimension(100,11));
		lblvenusID.setPreferredSize(new Dimension(100,11));
		
		JButton btnOperation = new JButton("Add / Modify Student");
		btnOperation.setActionCommand("Operation");
		btnOperation.addActionListener(this);
		
		JButton btnCancel = new JButton ("Cancel");
		btnCancel.addActionListener(this);
		
		firstName.addKeyListener(this);
		lastName.addKeyListener(this);
		cunyID.addKeyListener(this);
		
		this.add(lblfirstName);
		this.add(firstName);
		this.add(lbllastName);
		this.add(lastName);
		this.add(lblcunyID);
		this.add(cunyID);
		this.add(lblgpa);
		this.add(gpa);
		this.add(lblvenusID);
		this.add(venusID);
		
		this.add(btnOperation);
		this.add(btnCancel);
	
		
		this.setModal(true); // This is how you setup a Modal Dialog 
		this.getRootPane().setDefaultButton(btnOperation); //Setting default button for JDialog
		//this.setVisible(true);
	}
	
	public void PopulateDataFromSelectedRowInTable()
	{
		int currentRowInGUI = this.myReferencedTable.getSelectedRow();
	
		if(currentRowInGUI >= 0)
		{
			this.firstName.setText(this.myReferencedTable.getValueAt
					(currentRowInGUI, 1).toString());
			this.lastName.setText(this.myReferencedTable.getValueAt
					(currentRowInGUI, 2).toString());
			this.cunyID.setText(this.myReferencedTable.getValueAt
					(currentRowInGUI, 3).toString());
			this.gpa.setText(this.myReferencedTable.getValueAt
					(currentRowInGUI, 4).toString());
			this.venusID.setText(this.myReferencedTable.getValueAt
					(currentRowInGUI, 5).toString());
		}
	}
	
	public void InsertBlankInTable()
	{

			this.firstName.setText("");
			this.lastName.setText("");
			this.cunyID.setText("");
			this.gpa.setText("");
			this.venusID.setText("");
		
	}
	
	public void ModifyDataInTable()
	{
		
		int currentRowInGUI = this.myReferencedTable.getSelectedRow();
	
		if(formatCheck()){
		this.myReferencedTable.setValueAt(firstName.getText(), currentRowInGUI, 1);
		this.myReferencedTable.setValueAt(lastName.getText(), currentRowInGUI, 2);
		this.myReferencedTable.setValueAt(cunyID.getText(), currentRowInGUI, 3);
		this.myReferencedTable.setValueAt(gpa.getText(), currentRowInGUI, 4);
		this.myReferencedTable.setValueAt(venusID.getText(), currentRowInGUI, 5);
		}
	}
	
	public void AddDataToTable()
	{
	
		myReferencedModel = (DefaultTableModel)myReferencedTable.getModel();
		
		if(formatCheck()){
		this.myReferencedModel.addRow(new Object[]{"1",firstName.getText(),lastName.getText(),cunyID.getText(),gpa.getText(),venusID.getText()});
		}
		else{
			InsertBlankInTable();
		}
	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
		case "Operation":
			
			if(State == 0){
			ModifyDataInTable();
			}
			
			if(State == 1){
			AddDataToTable();
			}
			
			this.setVisible(false);
			break;
			
			
		case "Cancel":
			this.setVisible(false);
			break;
		}
		
	}
	
	public boolean formatCheck(){
		boolean checker = true;
		
		if(firstName.getText().length() < 2 && checker == true){
			checker = false;
			JOptionPane.showMessageDialog(null, 
					"First Names must contain 2 letters or more.",
					"Conditions Not Met",JOptionPane.ERROR_MESSAGE);
		}
		
		if(lastName.getText().length() < 2 && checker == true){
			checker = false;
			JOptionPane.showMessageDialog(null, 
					"Last Names must contain 2 letters or more.",
					"Conditions Not Met",JOptionPane.ERROR_MESSAGE);
		}
				
		if(cunyID.getText().length() != 8 && checker == true){
			checker = false;
			JOptionPane.showMessageDialog(null, 
					"Cuny ID must contain 8 digits.",
					"Conditions Not Met",JOptionPane.ERROR_MESSAGE);
		}
		
		if(CheckInteger(cunyID.getText()) == false && checker == true){
			checker = false;
			JOptionPane.showMessageDialog(null, 
					"Cuny ID must contain only numbers.",
					"Conditions Not Met",JOptionPane.ERROR_MESSAGE);
		}
		
		if(CheckDouble(gpa.getText()) == false && checker == true){
			checker = false;
			JOptionPane.showMessageDialog(null, 
					"GPA must contain only numbers.",
					"Conditions Not Met",JOptionPane.ERROR_MESSAGE);
		}
		
		try{
			double filler = Double.parseDouble(gpa.getText());
			if(filler < 0 || filler > 4 && checker == true && checker == true){
				checker = false;
				JOptionPane.showMessageDialog(null, 
						"GPA cannot be less than 0 or more than 4.",
						"Conditions Not Met",JOptionPane.ERROR_MESSAGE);
			}
			
	}
			catch(Exception e){
		
				}
		
	return checker;
	}
	
	
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

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		String fn = firstName.getText();
		String ln = lastName.getText();
		String cID = cunyID.getText();
		
		String vID = "";
		
		try{
		vID = vID + ln.charAt(0);
		}
		catch(Exception a){	
		}
		try{
		vID = vID + ln.charAt(1);
		}
		catch(Exception a){	
		}
		try{
		vID = vID + fn.charAt(0);
		}
		catch(Exception a){	
		}
		try{
		vID = vID + fn.charAt(1);
		}
		catch(Exception a){	
		}
		try{
		vID = vID + cID.charAt(4);
		}
		catch(Exception a){	
		}
		try{
		vID = vID + cID.charAt(5);
		}
		catch(Exception a){	
		}
		try{
		vID = vID + cID.charAt(6);
		}
		catch(Exception a){	
		}
		try{
		vID = vID + cID.charAt(7);
		}
		catch(Exception a){	
		}
		
		venusID.setText(vID);
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
	
	
	
}
