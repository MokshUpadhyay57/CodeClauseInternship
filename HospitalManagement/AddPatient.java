package HospitalManagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Patient class to represent patient information
class Patient {
	private String name;
	private String fathername;
	private int age;
	private String gender;
	private String illness;
	// Additional attributes as needed

	public Patient(String name, String fname, int age, String gender, String illness) {
		this.name = name;
		this.fathername = fname;
		this.age = age;
		this.gender = gender;
		this.illness = illness;
	}

	public String getName() {
		return name;

	}

	public String getGender() {
		return gender;
	}

	public int getAge() {
		return age;
	}

	public String getFatherName() {
		return fathername;
	}

	public String getillness() {
		return illness;
	}

}

// Database class to handle database operations
class Database {
	private Connection connection;

	public Database() {
		try {
			// Establish database connection
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method to retrieve list of patients from database
	public List<Patient> getPatients() {
		List<Patient> patients = new ArrayList<>();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM patients");
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String fname = resultSet.getString("Fathername");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				String illness = resultSet.getString("illness");
				patients.add(new Patient(name, fname, age, gender, illness));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return patients;
	}

	// Method to add a new patient to the database
	public void addPatient(Patient patient) {
		try {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO patients (name, Fathername, age, gender, illness) VALUES (?, ?, ?,?, ?)");
			statement.setString(1, patient.getName());
			statement.setString(2, patient.getFatherName());
			statement.setInt(3, patient.getAge());
			statement.setString(4, patient.getGender());
			statement.setString(5, patient.getillness());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Close the database connection
	public void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

// GUI class to create user interface using Java Swing
public class AddPatient extends JFrame {
	private Database database;

	public AddPatient() {
		database = new Database();
		setTitle("Hospital Management System");
		setSize(800, 700);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel AddPatient = new JPanel();
		
		JLabel Heading = new JLabel("Patient Form");
		Heading.setFont(new Font("Arial", Font.BOLD, 50));
		Heading.setBounds(250, 50, 400, 50);

		JLabel nameLabel = new JLabel("Patient Name");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		nameLabel.setBounds(200, 180, 180, 50);

		JTextField nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.BOLD, 16));
		nameField.setBounds(350, 190, 250, 30);

		JLabel fathernameLabel = new JLabel("Father Name");
		fathernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		fathernameLabel.setBounds(200, 250, 180, 50);

		JTextField fathernameField = new JTextField();
		fathernameField.setFont(new Font("Arial", Font.BOLD, 16));
		fathernameField.setBounds(350, 260, 250, 30);

		JLabel ageLabel = new JLabel("Age");
		ageLabel.setFont(new Font("Arial", Font.BOLD, 16));
		ageLabel.setBounds(200, 320, 80, 50);

		JTextField ageField = new JTextField();
		ageField.setFont(new Font("Arial", Font.BOLD, 16));
		ageField.setBounds(350, 330, 250, 30);

		JLabel genderLabel = new JLabel("Gender");
		genderLabel.setFont(new Font("Arial", Font.BOLD, 16));
		genderLabel.setBounds(200, 390, 80, 50);

		String[] genderChoices = { "Male", "Female" };

		final JComboBox<String> cb = new JComboBox<String>(genderChoices);
		cb.setSelectedIndex(0);
		cb.setFont(new Font("Arial", Font.BOLD, 16));
		cb.setBounds(350, 400, 250, 30);

		JLabel illnessLabel = new JLabel("Illness");
		illnessLabel.setFont(new Font("Arial", Font.BOLD, 16));
		illnessLabel.setBounds(200, 460, 80, 50);

		String[] IllnessChoices = { "Common Cold", "Flu", "Cancer", "Hypertension", "Chicken Pox", "Diarrhea", "Fever",
				"Pneumonia" };
		final JComboBox<String> ill = new JComboBox<String>(IllnessChoices);
		ill.setSelectedIndex(0);
		ill.setFont(new Font("Arial", Font.BOLD, 16));
		ill.setBounds(350, 470, 250, 30);

		JButton addButton = new JButton("Add Patient");
		addButton.setFont(new Font("Arial", Font.BOLD, 20));
		addButton.setBounds(280, 550, 250, 60);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText().trim(); // Trim leading and trailing spaces
				String fname = fathernameField.getText().trim(); // Trim leading and trailing spaces
				String ageText = ageField.getText().trim();
				String gender = cb.getSelectedItem().toString().trim();
				String illness = ill.getSelectedItem().toString().trim();

				// Check if any of the required fields are empty
				if (name.isEmpty() || ageText.isEmpty() || gender.isEmpty()) {
					JOptionPane.showMessageDialog(AddPatient.this, "Please fill in all the fields.",
							"Incomplete Information", JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						int age = Integer.parseInt(ageText);
						Patient patient = new Patient(name, fname, age, gender, illness);
						database.addPatient(patient);
						System.out.println("Data Added");
						JOptionPane.showMessageDialog(AddPatient.this, "Patient added successfully\n" + "Name: "
								+ name + "\n" + "Age: " + age + "\n" + "Gender: " + gender + "\n" + "Illness: "
								+ illness, "Success",
								JOptionPane.INFORMATION_MESSAGE);

					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(AddPatient.this, "Please enter a valid age.", "Invalid Age",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		AddPatient.add(Heading);
		AddPatient.add(nameLabel);
		AddPatient.add(nameField);
		AddPatient.add(fathernameLabel);
		AddPatient.add(fathernameField);
		AddPatient.add(ageLabel);
		AddPatient.add(ageField);
		AddPatient.add(genderLabel);
		AddPatient.add(cb); // dropdown
		AddPatient.add(illnessLabel);
		AddPatient.add(ill); // dropdown
		AddPatient.add(addButton);
		AddPatient.setLayout(null);

		add(AddPatient);
		setVisible(true);
	}

	public static void main(String[] args) {
		new AddPatient();
	}
}
