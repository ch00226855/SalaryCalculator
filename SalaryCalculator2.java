import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SalaryCalculator2 extends JFrame implements ActionListener, ChangeListener{
	
	JLabel wageLabel;
	JLabel salaryLabel;
	JLabel taxLabel;
	
	JTextField wageField;
	JTextField salaryField;
	JFormattedTextField taxField;
	
	JButton calculateButton;
	
	JLabel numHoursLabel;
	JSpinner numHoursSpinner;
	
	JLabel summaryLabel;
	JTextArea summaryArea;
	
	JSlider numWeeksSlider;
	
	int numHours = 40;
	int numWeeks = 50;
	
	public SalaryCalculator2() {
		
		// create the components
		wageLabel = new JLabel("Hourly wage:");
		salaryLabel = new JLabel("Yearly Salary:");
		taxLabel = new JLabel("Tax rate:");
		
		wageField = new JTextField(15);
		wageField.setText("0");
		wageField.setEditable(true); // this is true by default
		
		salaryField = new JTextField(15);
		salaryField.setText("0");
		salaryField.setEditable(false);
		
		taxField = new JFormattedTextField(NumberFormat.getPercentInstance());
		taxField.setColumns(15);
		taxField.setText("0%");
		taxField.setEditable(true); // this is true by default
		
		calculateButton = new JButton("Calculate");
		calculateButton.addActionListener(this);
		
		numHoursLabel = new JLabel("# of hours per week:");
		numHoursSpinner = new JSpinner(new SpinnerNumberModel(40, 0, 120, 10));
		numHoursSpinner.addChangeListener(this);
		
		summaryArea = new JTextArea(3, 15);
		summaryArea.setText("Pre-tax salary: 0.0 \n" +
							"Amount of tax: 0.0 \n" +
							"Post-tax salary: 0.0");
		summaryArea.setEditable(false);
		summaryLabel = new JLabel("Summary:");
		
		numWeeksSlider = new JSlider(0, 52, 50);
		numWeeksSlider.addChangeListener(this);
		numWeeksSlider.setMajorTickSpacing(5);
		numWeeksSlider.setMinorTickSpacing(1);
		numWeeksSlider.setPaintTicks(true);
		numWeeksSlider.setPaintLabels(true);
		
		// arrange the components using GridBagLayout
		setLayout(new GridBagLayout()); // setLayout() is inherited from JFrame
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(wageLabel, constraints);
		
		constraints = new GridBagConstraints(); // reset the constraints
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(wageField, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(salaryLabel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(salaryField, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(taxLabel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(taxField, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(calculateButton, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(numHoursLabel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(numHoursSpinner, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(summaryLabel, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(summaryArea, constraints);
		
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(10, 10, 10, 10);
		add(numWeeksSlider, constraints);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// make sure that the calculate button was pressed
		JButton sourceButton = (JButton) e.getSource();
		if (sourceButton != calculateButton) {
			return;
		}

		// calculate the yearly salary using formula
		// base salary = hourly wage * 40 * 50
		// actual salary = base salary - base salary * tax rate
		double hourlyWage = Double.parseDouble(wageField.getText());
		double baseSalary = hourlyWage * numHours * numWeeks;
		double taxRate = ((Number) taxField.getValue()).doubleValue();
		double yearlySalary = baseSalary - baseSalary * taxRate;
		
		// show an error message if user entered a negative value
		if (hourlyWage < 0.0 || taxRate < 0.0) {
			JOptionPane.showMessageDialog(this, "Invalid Input!");
			return;
		}
		
		salaryField.setText(String.format("$%5.2f", yearlySalary));
		
		summaryArea.setText("Pre-tax salary: " + baseSalary + "\n" +
							"Amount of tax: " + baseSalary * taxRate + "\n" +
							"Post-tax salary: " + yearlySalary);
	}
	
	public static void main(String[] args){
		SalaryCalculator2 window = new SalaryCalculator2();
		
		window.setTitle("Salary Calculator");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		
		window.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		numHours = ((Number) numHoursSpinner.getValue()).intValue();
		
		numWeeks = ((Number) numWeeksSlider.getValue()).intValue();
	}

}
