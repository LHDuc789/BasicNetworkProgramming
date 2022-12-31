package TCP;

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
import java.net.Socket;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;

public class Client_TCP extends JFrame implements Runnable {

	private JPanel contentPane;
	private JTextField clientName;
	private JTextField port;
	private JTextField inputMessage;
	
	
	private JTextArea Message;
	private JList list;
	
	private String name;
	DefaultListModel<String> model= new DefaultListModel<>();
	
	DataOutputStream dataOutputStream;
	DataInputStream dataInputStream;
	Socket socket;
	private JTextField chieuDai;
	private JTextField chieuRong;
	private JTextField dienTich;
	private JTextField a;
	private JTextField b;
	private JTextField x;
	private JTextField vnd;
	private JTextField usd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_TCP frame = new Client_TCP();
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
	public Client_TCP() {
		setTitle("Client TCP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 844, 503);
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
		Message.setBounds(27, 66, 398, 305);
		contentPane.add(Message);
		
		inputMessage = new JTextField();
		inputMessage.setBounds(27, 381, 312, 49);
		contentPane.add(inputMessage);
		inputMessage.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnSend.setBounds(349, 381, 85, 41);
		contentPane.add(btnSend);
		
		JLabel lblDinTchHnh = new JLabel("Diện tích hình chữ nhật");
		lblDinTchHnh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDinTchHnh.setBounds(505, 72, 219, 27);
		contentPane.add(lblDinTchHnh);
		
		JLabel lblChiuDi = new JLabel("Chiều dài:");
		lblChiuDi.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChiuDi.setBounds(430, 109, 100, 27);
		contentPane.add(lblChiuDi);
		
		JLabel lblChiuRng = new JLabel("Chiều rộng:");
		lblChiuRng.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChiuRng.setBounds(626, 109, 113, 27);
		contentPane.add(lblChiuRng);
		
		chieuDai = new JTextField();
		chieuDai.setText("22");
		chieuDai.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chieuDai.setColumns(10);
		chieuDai.setBounds(526, 109, 96, 27);
		contentPane.add(chieuDai);
		
		chieuRong = new JTextField();
		chieuRong.setText("15");
		chieuRong.setFont(new Font("Tahoma", Font.PLAIN, 20));
		chieuRong.setColumns(10);
		chieuRong.setBounds(734, 109, 96, 27);
		contentPane.add(chieuRong);
		
		JLabel lblKtQu = new JLabel("Kết quả:");
		lblKtQu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblKtQu.setBounds(430, 148, 85, 27);
		contentPane.add(lblKtQu);
		
		dienTich = new JTextField();
		dienTich.setFont(new Font("Tahoma", Font.PLAIN, 20));
		dienTich.setColumns(10);
		dienTich.setBounds(505, 146, 141, 27);
		contentPane.add(dienTich);
		
		JButton btnDienTich = new JButton("Tính");
		btnDienTich.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDienTich.setBounds(667, 146, 85, 41);
		contentPane.add(btnDienTich);
		
		JLabel lblGiiPhngTrnh = new JLabel("Giải phương trình ax +b = 0");
		lblGiiPhngTrnh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblGiiPhngTrnh.setBounds(486, 191, 283, 27);
		contentPane.add(lblGiiPhngTrnh);
		
		JLabel lblA = new JLabel("a:");
		lblA.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblA.setBounds(430, 221, 40, 27);
		contentPane.add(lblA);
		
		a = new JTextField();
		a.setText("2");
		a.setFont(new Font("Tahoma", Font.PLAIN, 20));
		a.setColumns(10);
		a.setBounds(460, 221, 96, 27);
		contentPane.add(a);
		
		JLabel lblB = new JLabel("b:");
		lblB.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblB.setBounds(566, 221, 33, 27);
		contentPane.add(lblB);
		
		b = new JTextField();
		b.setText("1");
		b.setFont(new Font("Tahoma", Font.PLAIN, 20));
		b.setColumns(10);
		b.setBounds(588, 221, 96, 27);
		contentPane.add(b);
		
		JLabel lblKtQu_1 = new JLabel("Kết quả:");
		lblKtQu_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblKtQu_1.setBounds(430, 258, 85, 27);
		contentPane.add(lblKtQu_1);
		
		x = new JTextField();
		x.setFont(new Font("Tahoma", Font.PLAIN, 20));
		x.setColumns(10);
		x.setBounds(520, 258, 299, 27);
		contentPane.add(x);
		
		JButton btnPhuongTrinh = new JButton("Giải");
		btnPhuongTrinh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPhuongTrinh.setBounds(694, 214, 85, 41);
		contentPane.add(btnPhuongTrinh);
		
		JLabel lbliTinVnd = new JLabel("Đổi tiền VND --> $");
		lbliTinVnd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lbliTinVnd.setBounds(530, 295, 181, 27);
		contentPane.add(lbliTinVnd);
		
		JLabel lblVnd = new JLabel("VND:");
		lblVnd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVnd.setBounds(442, 332, 55, 27);
		contentPane.add(lblVnd);
		
		vnd = new JTextField();
		vnd.setText("50000");
		vnd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		vnd.setColumns(10);
		vnd.setBounds(503, 332, 221, 27);
		contentPane.add(vnd);
		
		JLabel lblVnd_1 = new JLabel("$:");
		lblVnd_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVnd_1.setBounds(452, 369, 27, 27);
		contentPane.add(lblVnd_1);
		
		usd = new JTextField();
		usd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		usd.setColumns(10);
		usd.setBounds(503, 369, 221, 27);
		contentPane.add(usd);
		
		JButton btnDoiTien = new JButton("Đổi");
		btnDoiTien.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDoiTien.setBounds(576, 406, 85, 41);
		contentPane.add(btnDoiTien);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExit.setBounds(694, 10, 85, 41);
		contentPane.add(btnExit);
		//Ấn nút Start để khơi tạo
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int portNo = Integer.parseInt(port.getText().trim());
				name=clientName.getText();
				try {
					//Khởi tạo socket kết nối server
					socket = new Socket("localhost",portNo);
					//Gửi tên của client qua server
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
					dataOutputStream.writeUTF(name);
					dataOutputStream.flush();
					//Luồng chạy client
					Thread t = new Thread(Client_TCP.this);
					t.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		//Ấn nút Send để gửi tin nhắn
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = inputMessage.getText();
				try {
					//Gửi tin nhắn của client đến các client khác thông qua server
					dataOutputStream = new DataOutputStream(socket.getOutputStream());			
					for(int index = 0; index < model.getSize(); index++) {
						if(model.get(index) != name) {
							dataOutputStream.writeUTF("Text");
							dataOutputStream.writeUTF(model.get(index));
							dataOutputStream.writeUTF(message);
							dataOutputStream.flush();
						}						
					}
					inputMessage.setText("");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		//Ấn nút để gửi giá trị chiều dài, chiều rộng đến server để tính diện tích
		btnDienTich.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valDai = chieuDai.getText().trim();
				String valRong = chieuRong.getText().trim();
				//Kiểm tra giá trị chiều dài, chiều rộng có hợp lệ không.
				if(valDai.isEmpty() || valRong.isEmpty() || Integer.parseInt(valDai)<Integer.parseInt(valRong) || Integer.parseInt(valDai)<=0 || Integer.parseInt(valRong)<=0) {
					JOptionPane.showMessageDialog(null,"Chiều dài hoặc chiều rộng không hợp lệ. Vui lòng nhập lại.","Note",JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						//Gửi giá trị chiều dài, chiều rộng và tên của client
						dataOutputStream = new DataOutputStream(socket.getOutputStream());			
						dataOutputStream.writeUTF("DienTich");
						dataOutputStream.writeUTF(name);
						dataOutputStream.writeUTF(valDai);
						dataOutputStream.writeUTF(valRong);
						dataOutputStream.flush();				
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		//Ấn nút để gửi giá trị 2 tham số a, b đến server để giải phương trình ax + b = 0
		btnPhuongTrinh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valA = a.getText().trim();
				String valB = b.getText().trim();
				//Kiểm tra giá trị 2 tham số a, b có hợp lệ không.
				if(valA.isEmpty() || valB.isEmpty()) {
					JOptionPane.showMessageDialog(null,"Hệ số a hoặc b chưa được nhập. Vui lòng nhập lại.","Note",JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						//Gửi giá trị 2 tham số a, b và tên của client
						dataOutputStream = new DataOutputStream(socket.getOutputStream());			
						dataOutputStream.writeUTF("PhuongTrinh");
						dataOutputStream.writeUTF(name);
						dataOutputStream.writeUTF(valA);
						dataOutputStream.writeUTF(valB);
						dataOutputStream.flush();				
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		//Ấn nút để gửi số tiền Việt đến server để đổi thành tiền USD
		btnDoiTien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tienViet = vnd.getText().trim();
				//Kiểm tra giá trị tiền Việt có hợp lệ không.
				if(tienViet.isEmpty() || Integer.parseInt(tienViet) <= 0) {
					JOptionPane.showMessageDialog(null,"Số tiền cần đổi không hợp lệ. Vui lòng nhập lại.","Note",JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						//Gửi số tiền Việt và tên của client
						dataOutputStream = new DataOutputStream(socket.getOutputStream());			
						dataOutputStream.writeUTF("DoiTien");
						dataOutputStream.writeUTF(name);
						dataOutputStream.writeUTF(tienViet);
						dataOutputStream.flush();				
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.WARNING_MESSAGE);
					}
				}
				
			}
		});
		//Ấn nút exit để thoát user
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					dataOutputStream = new DataOutputStream(socket.getOutputStream());	
					dataOutputStream.writeUTF("EXIT");
					dataOutputStream.writeUTF(name);
					dataOutputStream.flush();				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.WARNING_MESSAGE);
				}
				dispose();
			}
		});
	}
	
	@Override
	public void run() {
		
		
		String method="";
		try {
			dataInputStream = new DataInputStream(socket.getInputStream());
			while(true) {
				method =dataInputStream.readUTF();
				//Nhận tin nhắn của các client khác
				if (method.equals("Text")) {
					String sender =	dataInputStream.readUTF();
					String message = dataInputStream.readUTF();
					if(sender.equals(name) == false) {
						//In tin nhắn lên màn hình chat với người gửi
						Message.setText(Message.getText() + "\n" + sender + ": " + message);
					}else {
						//In lên nếu hiển thị ngay khung chat của chính client gửi
						Message.setText(Message.getText() + "\nMe: " + message);
					}
				}
				//Nhận thông tin cập nhật danh sách client
				else if (method.equals("Friends")) {
					String[] users = dataInputStream.readUTF().split(",");
					model = new DefaultListModel<>();
					for(String user:users) {
						if(user != name) {
							model.addElement(user);
						}
					}
				}
				//Nhận kết quả tính diện tích
				else if(method.equals("DienTich")) {
					String sender =	dataInputStream.readUTF();
					String DienTich = dataInputStream.readUTF();
					dienTich.setText(DienTich);
				}
				//Nhận kết quả giải phương trình
				else if(method.equals("PhuongTrinh")) {
					String sender =	dataInputStream.readUTF();
					String PT = dataInputStream.readUTF();
					x.setText(PT);
				}
				//Nhận kết quả đổi tiền
				else if(method.equals("DoiTien")) {
					String sender =	dataInputStream.readUTF();
					String tienDo = dataInputStream.readUTF();
					usd.setText(tienDo);
				}
				//Nhận kết quả thoát
				else if(method.equals("EXIT")) {
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Đóng socket
		finally
		{
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO Auto-generated method stub
		
	}	
}
