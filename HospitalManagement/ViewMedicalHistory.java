package HospitalManagement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewMedicalHistory extends JFrame {

	private DefaultTableModel tableModel;
	private JTable patientTable;

	public ViewMedicalHistory() {
		setTitle("Medical History");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(null);

		JLabel headingLabel = new JLabel("Patient Information");
		headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
		headingLabel.setBounds(300, 10, 200, 100);
		viewPanel.add(headingLabel);

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Name");
		tableModel.addColumn("Father's Name");
		tableModel.addColumn("Age");
		tableModel.addColumn("Gender");
		tableModel.addColumn("Illness");

		patientTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(patientTable);
		scrollPane.setBounds(20, 100, 750, 250);
		viewPanel.add(scrollPane);

		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				populateMedicalHistory();
			}
		});
		refreshButton.setBounds(350, 400, 100, 40);
		viewPanel.add(refreshButton);

		add(viewPanel);
		setVisible(true);
	}

	private void populateMedicalHistory() {
		tableModel.setRowCount(0); // Clear previous data
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital",
					"root", "root");

			Statement statement = connection.createStatement();
			String sql = "SELECT name, fathername, age, gender, illness FROM patients";
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String fatherName = resultSet.getString("fathername");
				int age = resultSet.getInt("age");
				String gender = resultSet.getString("gender");
				String illness = resultSet.getString("illness");
				tableModel.addRow(new Object[] { name, fatherName, age, gender, illness });
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ViewMedicalHistory();
	}
}
