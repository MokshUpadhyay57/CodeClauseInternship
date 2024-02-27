package HospitalManagement;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainScreen extends JFrame {
	public static void main(String[] args) {
		// Create a JFrame object
		JFrame frame = new JFrame("Hospital Management System");
		// ImageIcon icon = new ImageIcon("HospitalMangement/1.png");
		frame.setLayout(null);
		// Set properties
		JLabel image = new JLabel();
		ImageIcon imageicon = new ImageIcon(
				new ImageIcon("F:\\Programming\\Internship\\CodeClause\\HospitalManagement\\1.png").getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH));
		image.setIcon(imageicon);
		image.setBounds(100, 100, imageicon.getIconWidth(), imageicon.getIconHeight());
		frame.add(image);

		// Add Patient Record Button
		JButton addPatient = new JButton("Add Patient Record");
		addPatient.setFont(new Font("Arial", Font.BOLD, 18));
		addPatient.setFocusPainted(false);
		addPatient.setContentAreaFilled(true);
		addPatient.setBounds(100, 400, 250, 50);
		addPatient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddPatient patient = new AddPatient();
				patient.setVisible(true);	
			}
		});
		


		// View Appointment Button
		JButton AddAppointment = new JButton("Add Appointment");
		AddAppointment.setFont(new Font("Arial", Font.BOLD, 18));
		AddAppointment.setFocusPainted(false);
		AddAppointment.setContentAreaFilled(true);
		AddAppointment.setBounds(100, 500, 250, 50);
		AddAppointment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddAppointment appointment = new AddAppointment();
				appointment.setVisible(true);
			}
		});

		// View Medical History Button
		JButton viewHistory = new JButton("View Medical History");
		viewHistory.setFont(new Font("Arial", Font.BOLD, 18));
		viewHistory.setFocusPainted(false);
		viewHistory.setContentAreaFilled(true);
		viewHistory.setBounds(100, 600, 250, 50);
		

		viewHistory.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewMedicalHistory history = new ViewMedicalHistory();
				history.setVisible(true);
			}
		});

		frame.add(addPatient);
		frame.add(AddAppointment);
		frame.add(viewHistory);
		frame.setSize(500, 800); // Set size (width, height) in pixels
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
		frame.setLocationRelativeTo(null); // Center the window on the screen

		// Display the window
		frame.setVisible(true);
	}

}
