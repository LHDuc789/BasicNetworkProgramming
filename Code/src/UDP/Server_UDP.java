package UDP;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



//import Chat.serverThread;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server_UDP extends JFrame {

	private JPanel contentPane;
	private JTextField port;
	
	private JList list;
	private DatagramSocket server;
	private DatagramPacket packet;
	String data="";
	private int maxSize;
	
	private ArrayList<InetAddress> clientAddresses;
	private ArrayList<Integer> clientPorts;
	private HashSet<String> existingClients;
	private ArrayList<String> clientNames;

	
	DefaultListModel<String> model= new DefaultListModel<>();
	private JTextField MaxPacketSize;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_UDP frame = new Server_UDP();
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
	public Server_UDP() {
		setTitle("Server UDP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStart.setBounds(218, 82, 85, 42);
		contentPane.add(btnStart);
		
		JLabel lblNewLabel = new JLabel("Port:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(27, 30, 63, 24);
		contentPane.add(lblNewLabel);
		
		port = new JTextField();
		port.setFont(new Font("Tahoma", Font.PLAIN, 20));
		port.setBounds(101, 25, 114, 37);
		contentPane.add(port);
		port.setColumns(10);
		port.setText("2022");
		
		
		JLabel lblNewLabel_1 = new JLabel("Users");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(27, 132, 73, 31);
		contentPane.add(lblNewLabel_1);
		
	
		list = new JList<String>(model);
		list.setBounds(10, 173, 121, 128);
		contentPane.add(list);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(10, 173, 121, 128);
		contentPane.add(scrollPane);
		
		
		JLabel lblMaxPacketSize = new JLabel("Max Packet Size:");
		lblMaxPacketSize.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMaxPacketSize.setBounds(252, 30, 157, 24);
		contentPane.add(lblMaxPacketSize);
		
		MaxPacketSize = new JTextField();
		MaxPacketSize.setText("56507");
		MaxPacketSize.setFont(new Font("Tahoma", Font.PLAIN, 20));
		MaxPacketSize.setColumns(10);
		MaxPacketSize.setBounds(419, 25, 114, 37);
		contentPane.add(MaxPacketSize);
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int portNo = Integer.parseInt(port.getText().trim());
				maxSize = Integer.parseInt(MaxPacketSize.getText());
				byte[] buffer = new byte[maxSize];
				Runnable r=new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							clientAddresses = new ArrayList<InetAddress>();
					        clientPorts = new ArrayList<Integer>();
					        existingClients = new HashSet<String>();
					        clientNames = new ArrayList<String>();
					        
							server = new DatagramSocket(portNo);
							
							Component fame = null;
							JOptionPane.showMessageDialog(fame, "Ready to receive data"," UDP Protocol",JOptionPane.WARNING_MESSAGE);
							
							while(true) {
								//Nhận dữ liệu từ các client.
								packet = new DatagramPacket(buffer, buffer.length);
								server.receive(packet);
								
								//Chuyển dữ liệu thành dạng String.
								data = new String(packet.getData(), 0, packet.getLength(), "UTF-8");
								
								//Lấy address của Client.
								InetAddress clientAddress = packet.getAddress();
								
								//Lấy port của Client
					            int clientPort = packet.getPort();
					            
					            String id = clientAddress.toString() + "," + clientPort;
					            
					            //Kiểm tra xem client đã tồn tại hay chưa thông qua ID tạo từ clientAddress và clientPort.
					            if (!existingClients.contains(id)) {
				                    existingClients.add(id);
				                    clientPorts.add(clientPort);
				                    clientAddresses.add(clientAddress);
				                    clientNames.add(data);
				                    model.addElement(data);
				                }
					            else {
					            
					            	 byte[] message = data.getBytes("UTF-8");
						                
						             for (int i=0; i < clientAddresses.size(); i++) {
						            	
							            	InetAddress cl = clientAddresses.get(i);  
						                	int cp = clientPorts.get(i);
							                packet = new DatagramPacket(message, message.length, cl, cp);
							                server.send(packet);
						        
						                    
						             }
					            }
					            
					           
								
							}
						} catch (SocketException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				};
				Thread a= new Thread(r);
				a.setDaemon(true);
				a.start();
			}
		});
	}

}
