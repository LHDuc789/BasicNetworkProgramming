package TCP;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.JScrollPane;

public class Server_TCP extends JFrame {

	private JPanel contentPane;
	private JTextField port;
	int i=0;
	
	private JList list;
	ServerSocket serverSocket;
	Socket socket;
	DataOutputStream dataOutputStream;
	DataInputStream dataInputStream;
	String clientName="";

	
	static DefaultListModel<String> model= new DefaultListModel<>();
	static ArrayList<Handler> clients = new ArrayList<Handler>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server_TCP frame = new Server_TCP();
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
	public Server_TCP() {
		setTitle("Server TCP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 371, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnStart.setBounds(265, 21, 85, 42);
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
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int portNo = Integer.parseInt(port.getText().trim());
				Runnable r=new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try 
						{
							serverSocket = new ServerSocket(portNo);
							JOptionPane.showMessageDialog(null,"Server was created: ","Note",JOptionPane.WARNING_MESSAGE);
							while(true)
							{
								socket = serverSocket.accept();
								dataInputStream = new DataInputStream(socket.getInputStream());
								dataOutputStream =new DataOutputStream(socket.getOutputStream());
								clientName = dataInputStream.readUTF();
								//Thêm user đã kết nối đến server
								model.addElement(clientName);
								Handler newHandler = new Handler(socket, clientName);
								clients.add(newHandler);
		
								Thread t = new Thread(newHandler);
								t.start();
								JOptionPane.showMessageDialog(null,"A Client was connected: ","Note",JOptionPane.WARNING_MESSAGE);
								updateFriends();
							}
						}
						catch (IOException e1)
						{
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
	//Cập nhật danh sách user đang online và gửi cho các client
	public static void updateFriends() {
		String friends = "";
		for (Handler client:clients) {
			friends += ",";
			friends += client.getUsername();
		}
		for (Handler client:clients) {
			try {
				client.getDos().writeUTF("Friends");
				client.getDos().writeUTF(friends);
				client.getDos().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
/**
 * Luồng riêng dùng để giao tiếp với mỗi user
 */
class Handler implements Runnable{
	
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private String username;
	
	public Handler(Socket socket, String username) throws IOException {
		this.socket = socket;
		this.username = username;
		this.dis = new DataInputStream(socket.getInputStream());
		this.dos = new DataOutputStream(socket.getOutputStream());
	}
	
	public Handler(String username) {
		this.username = username;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
		try {
			this.dis = new DataInputStream(socket.getInputStream());
			this.dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String getUsername() {
		return this.username;
	}
	
	
	public DataOutputStream getDos() {
		return this.dos;
	}
	
	@Override
	public void run() {
		
		while (true) {
			try {
				String message = null;
				message = dis.readUTF();
				// Yêu cầu gửi tin nhắn 
				if (message.equals("Text")){
					String receiver = dis.readUTF();
					String content = dis.readUTF();
					
					for (Handler client: Server_TCP.clients) {
						if (client.getUsername().equals(receiver)) {
							//synchronized (lock) {
								client.getDos().writeUTF("Text");
								client.getDos().writeUTF(this.username);
								client.getDos().writeUTF(content);
								client.getDos().flush();
								break;
							//}
						}
					}
				}
				// Yêu cầu tính diện tích
				else if(message.equals("DienTich")) {
					String receiver = dis.readUTF();
					String valDai = dis.readUTF();
					String valRong = dis.readUTF();
					int DienTich = Integer.parseInt(valDai)*Integer.parseInt(valRong);
					
					for (Handler client: Server_TCP.clients) {
						if (client.getUsername().equals(receiver)) {
							//synchronized (lock) {
								client.getDos().writeUTF("DienTich");
								client.getDos().writeUTF(this.username);
								client.getDos().writeUTF(String.valueOf(DienTich));
								client.getDos().flush();
								break;
							//}
						}
					}
				}
				//Yêu cầu giải phương trình
				else if(message.equals("PhuongTrinh")) {
					String receiver = dis.readUTF();
					String valA = dis.readUTF();
					String valB = dis.readUTF();
					String Result = "";
					
					if (Float.parseFloat(valA) == 0 && Float.parseFloat(valB) == 0) {
						Result = "Phương trình có vô số nghiệm";
					}
					else if(Float.parseFloat(valA) == 0 && Float.parseFloat(valB) != 0) {
						Result = "Phương trình vô nghiệm";
					}
					else {
						float r =-Float.parseFloat(valB)/ Float.parseFloat(valA);
						Result = String.valueOf(r);
					}
					
					
					for (Handler client: Server_TCP.clients) {
						if (client.getUsername().equals(receiver)) {
							//synchronized (lock) {
								client.getDos().writeUTF("PhuongTrinh");
								client.getDos().writeUTF(this.username);
								client.getDos().writeUTF(Result);
								client.getDos().flush();
								break;
							//}
						}
					}
				}
				//yêu cầu đổi tiền
				else if(message.equals("DoiTien")) {
					String receiver = dis.readUTF();
					String tienViet = dis.readUTF();
					float usd;
					String tienDo = "";
					
					usd = Float.parseFloat(tienViet)/23995;
					tienDo = String.valueOf(usd);
					for (Handler client: Server_TCP.clients) {
						if (client.getUsername().equals(receiver)) {
							//synchronized (lock) {
								client.getDos().writeUTF("DoiTien");
								client.getDos().writeUTF(this.username);
								client.getDos().writeUTF(tienDo);
								client.getDos().flush();
								break;
							//}
						}
					}
				}
				//Thoát user
				else if(message.equals("EXIT")) {
					String receiver = dis.readUTF();
					for (Handler client: Server_TCP.clients) {
						if (client.getUsername().equals(receiver)) {
							//synchronized (lock) {
								client.getDos().writeUTF("EXIT");
								client.getDos().flush();
								Server_TCP.model.removeElement(client.getUsername());
								Server_TCP.clients.remove(client);
								break;
							//}
						}
					}
					socket.close();
					break;
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}