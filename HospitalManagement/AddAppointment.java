package HospitalManagement;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.time.YearMonth;

public class AddAppointment extends JFrame {
	private JComboBox<Integer> yearComboBox;
	private JComboBox<String> monthComboBox;
	private JComboBox<Integer> dayComboBox;
	private JComboBox<String> timeComboBox;

	private JTextField nameField;
	private JTextField doctorNameField;


	private static final Map<String, Integer> monthMap = new HashMap<>();
    
    // Initialize the map in a static block
    static {
        monthMap.put("January", 1);
        monthMap.put("February", 2);
        monthMap.put("March", 3);
        monthMap.put("April", 4);
        monthMap.put("May", 5);
        monthMap.put("June", 6);
        monthMap.put("July", 7);
        monthMap.put("August", 8);
        monthMap.put("September", 9);
        monthMap.put("October", 10);
        monthMap.put("November", 11);
        monthMap.put("December", 12);
    }

	public AddAppointment() {
		setTitle("Add Appointment");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel Heading = new JLabel("Add Appointment");
		Heading.setFont(new Font("Arial", Font.BOLD, 30));
		Heading.setBounds(180, 30, 350, 50);

		panel.add(Heading);
		JLabel dateLabel = new JLabel("Date");
		dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
		dateLabel.setBounds(120, 105, 100, 25);

		panel.add(dateLabel);

		// Day Combo Box
		dayComboBox = new JComboBox<>();
		dayComboBox.setBounds(250, 105, 60, 25);
		dayComboBox.setFont(new Font("Arial", Font.BOLD, 16));

		panel.add(dayComboBox);

		// Month Combobox
		String[] months = { "January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December" };
		monthComboBox = new JComboBox<>(months);
		monthComboBox.setFont(new Font("Arial", Font.BOLD, 16));

		monthComboBox.setBounds(320, 105, 120, 25);
		panel.add(monthComboBox);

		// Year Combo Box
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		yearComboBox = new JComboBox<Integer>();
		yearComboBox.setFont(new Font("Arial", Font.BOLD, 16));

		yearComboBox.setBounds(450, 105, 80, 25);
		for (int i = currentYear; i <= currentYear + 10; i++) {
			yearComboBox.addItem(i);
		}
		panel.add(yearComboBox);

		monthComboBox.addActionListener(e -> updateDayComboBox());
		yearComboBox.addActionListener(e -> updateDayComboBox());

		updateDayComboBox();

		JLabel timeLabel = new JLabel("Time");
		timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
		timeLabel.setBounds(120, 160, 80, 30);

		String[] timings = { "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "12:00 PM", "2:00 PM", "2:00PM",
				"2:30 PM",
				"4:00 PM", "5:00 PM" };
		timeComboBox = new JComboBox<>(timings);
		timeComboBox.setFont(new Font("Arial", Font.BOLD, 16));

		timeComboBox.setBounds(250, 160, 100, 25);

		panel.add(timeLabel);
		panel.add(timeComboBox);

		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		nameLabel.setBounds(120, 220, 80, 30);

		nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.BOLD, 16));
		nameField.setBounds(250, 220, 150, 30);
		panel.add(nameLabel);
		panel.add(nameField);

		JLabel doctorLabel = new JLabel("Doctor Name");
		doctorLabel.setFont(new Font("Arial", Font.BOLD, 16));
		doctorLabel.setBounds(120, 280, 180, 30);
		panel.add(doctorLabel);
		
		
		doctorNameField = new JTextField();
		doctorNameField.setFont(new Font("Arial", Font.BOLD, 16));
		doctorNameField.setBounds(250, 280, 180, 30);
		panel.add(doctorNameField);

		JButton addButton = new JButton("Add Appointment");
		addButton.setFont(new Font("Arial", Font.BOLD, 16));
		addButton.setBounds(180, 350, 180, 30);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String monthName = monthComboBox.getSelectedItem().toString();
				int month = monthMap.get(monthName);
				
				String date = yearComboBox.getSelectedItem().toString() + "-"
						+ month + "-" + dayComboBox.getSelectedItem().toString();
				String time = timeComboBox.getSelectedItem().toString();
				String name = nameField.getText();
				String doctorName = doctorNameField.getText();
				addAppointmentToDatabase(time, date, name, doctorName);
			}
		});
		panel.add(addButton);

		add(panel);
		setVisible(true);
	}

	private void updateDayComboBox() {
		int year = (int) yearComboBox.getSelectedItem();
		int monthIndex = monthComboBox.getSelectedIndex();
		int daysInMonth = getDaysInMonth(year, monthIndex);
		if (monthIndex == 1 && isLeapYear(year)) { // February and leap year
			daysInMonth = 29;
		}
		dayComboBox.removeAllItems();
		for (int i = 1; i <= daysInMonth; i++) {
			dayComboBox.addItem(i);
		}
	}

	private boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	private int getDaysInMonth(int year, int month) {
		// Create a YearMonth object with the given year and month
		YearMonth yearMonth = YearMonth.of(year, month + 1); // Month is 0-based in YearMonth

		// Get the number of days in the specified month
		return yearMonth.lengthOfMonth();
	}

	private void addAppointmentToDatabase(String time, String date, String name, String doctorName) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital",
					"root", "root");

			String query = "INSERT INTO appointments (time, date, name, doctor) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, time);
			preparedStatement.setString(2, date);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, doctorName);

			int rowsInserted = preparedStatement.executeUpdate();
			if (rowsInserted > 0) {
				JOptionPane.showMessageDialog(this, "Appointment added successfully");
			}

			preparedStatement.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error adding appointment: " + ex.getMessage());
		}
	}

	public static void main(String[] args) {
		new AddAppointment();
	}
}
