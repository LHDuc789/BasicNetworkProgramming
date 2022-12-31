package UDP;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;


public class Client_UDP extends JFrame implements Runnable{

	private JPanel contentPane;
	private JTextField clientName;
	private JTextField port;
	private JTextField inputMessage;
	
	
	private JTextArea Message;
	private JList list;
	
	private int portNo;
	
	private String name;
	DefaultListModel<String> model= new DefaultListModel<>();
	
	private InetAddress server;
	private DatagramSocket theSocket;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_UDP frame = new Client_UDP();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client_UDP() {
		setTitle("Client UDP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 10, 100, 27);
		contentPane.add(lblNewLabel);
		
		clientName = new JTextField();
		clientName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		clientName.setBounds(93, 10, 275, 27);
		contentPane.add(clientName);
		clientName.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Port:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(378, 15, 73, 16);
		contentPane.add(lblNewLabel_1);
		
		port = new JTextField();
		port.setFont(new Font("Tahoma", Font.PLAIN, 20));
		port.setBounds(430, 10, 96, 27);
		contentPane.add(port);
		port.setColumns(10);
		port.setText("2022");
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStart.setBounds(576, 10, 85, 41);
		contentPane.add(btnStart);
		
		Message = new JTextArea();
		Message.setEditable(false);
		Message.setBounds(27, 66, 499, 305);
		contentPane.add(Message);
		
		inputMessage = new JTextField();
		inputMessage.setBounds(27, 381, 407, 49);
		contentPane.add(inputMessage);
		inputMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSend.setBounds(444, 381, 85, 41);
		contentPane.add(btnSend);
		
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				portNo = Integer.parseInt(port.getText().trim());
				name=clientName.getText();
				try {
					server = InetAddress.getByName("localhost");
					theSocket = new DatagramSocket();
					
					String messageInit = name;
					byte[] dataInit;
					try {
						
						//Gửi data khởi tạo lần đầu qua server.
						dataInit = messageInit.getBytes("UTF-8");
						DatagramPacket theOutputMessageInit = new DatagramPacket(dataInit, dataInit.length, server, portNo);
						theSocket.send(theOutputMessageInit);
											
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					//Tạo luồng để chạy client
					Thread t = new Thread(Client_UDP.this);
					t.start();
					JOptionPane.showMessageDialog(null,"A Client was connected: ","Note",JOptionPane.WARNING_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chuỗi "_THFNVHKjfkdshgnvbhk123411341678fsdfdsg_" này ở giữa để ngắt được name và message ra
				String message = name + "_THFNVHKjfkdshgnvbhk123411341678fsdfdsg_" + inputMessage.getText();
				String inputUser = inputMessage.getText();
				byte[] data;
				try {
					
					Message.setText(Message.getText() + "\nMe: " + inputUser);

					//Gửi data qua server.
					data = message.getBytes("UTF-8");
					DatagramPacket theOutputMessage = new DatagramPacket(data, data.length, server, portNo);
					theSocket.send(theOutputMessage);
					inputMessage.setText("");
					
				
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
	
	//Luồng để chạy thread
	@Override
	public void run() {
		byte[] buffer = new byte[56507];
        
		while(true) {
	        try {
	        	DatagramPacket response = new DatagramPacket(buffer, buffer.length);
				theSocket.receive(response);
				String res = new String(buffer, 0, response.getLength());
				String[] new_data = res.split("_THFNVHKjfkdshgnvbhk123411341678fsdfdsg_");
				if(!new_data[0].equals(name)) {
					Message.setText(Message.getText().trim()+"\n" + new_data[0] + ": " + new_data[1]);
				}
            	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
		}
			
	}

}
