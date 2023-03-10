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
		
		JLabel lblDinTchHnh = new JLabel("Di???n t??ch h??nh ch??? nh???t");
		lblDinTchHnh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDinTchHnh.setBounds(505, 72, 219, 27);
		contentPane.add(lblDinTchHnh);
		
		JLabel lblChiuDi = new JLabel("Chi???u d??i:");
		lblChiuDi.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChiuDi.setBounds(430, 109, 100, 27);
		contentPane.add(lblChiuDi);
		
		JLabel lblChiuRng = new JLabel("Chi???u r???ng:");
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
		
		JLabel lblKtQu = new JLabel("K???t qu???:");
		lblKtQu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblKtQu.setBounds(430, 148, 85, 27);
		contentPane.add(lblKtQu);
		
		dienTich = new JTextField();
		dienTich.setFont(new Font("Tahoma", Font.PLAIN, 20));
		dienTich.setColumns(10);
		dienTich.setBounds(505, 146, 141, 27);
		contentPane.add(dienTich);
		
		JButton btnDienTich = new JButton("T??nh");
		btnDienTich.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDienTich.setBounds(667, 146, 85, 41);
		contentPane.add(btnDienTich);
		
		JLabel lblGiiPhngTrnh = new JLabel("Gi???i ph????ng tr??nh ax +b = 0");
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
		
		JLabel lblKtQu_1 = new JLabel("K???t qu???:");
		lblKtQu_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblKtQu_1.setBounds(430, 258, 85, 27);
		contentPane.add(lblKtQu_1);
		
		x = new JTextField();
		x.setFont(new Font("Tahoma", Font.PLAIN, 20));
		x.setColumns(10);
		x.setBounds(520, 258, 299, 27);
		contentPane.add(x);
		
		JButton btnPhuongTrinh = new JButton("Gi???i");
		btnPhuongTrinh.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnPhuongTrinh.setBounds(694, 214, 85, 41);
		contentPane.add(btnPhuongTrinh);
		
		JLabel lbliTinVnd = new JLabel("?????i ti???n VND --> $");
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
		
		JButton btnDoiTien = new JButton("?????i");
		btnDoiTien.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDoiTien.setBounds(576, 406, 85, 41);
		contentPane.add(btnDoiTien);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnExit.setBounds(694, 10, 85, 41);
		contentPane.add(btnExit);
		//???n n??t Start ????? kh??i t???o
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int portNo = Integer.parseInt(port.getText().trim());
				name=clientName.getText();
				try {
					//Kh???i t???o socket k???t n???i server
					socket = new Socket("localhost",portNo);
					//G???i t??n c???a client qua server
					dataOutputStream = new DataOutputStream(socket.getOutputStream());
					dataOutputStream.writeUTF(name);
					dataOutputStream.flush();
					//Lu???ng ch???y client
					Thread t = new Thread(Client_TCP.this);
					t.start();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Error","Error",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		//???n n??t Send ????? g???i tin nh???n
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = inputMessage.getText();
				try {
					//G???i tin nh???n c???a client ?????n c??c client kh??c th??ng qua server
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
		//???n n??t ????? g???i gi?? tr??? chi???u d??i, chi???u r???ng ?????n server ????? t??nh di???n t??ch
		btnDienTich.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valDai = chieuDai.getText().trim();
				String valRong = chieuRong.getText().trim();
				//Ki???m tra gi?? tr??? chi???u d??i, chi???u r???ng c?? h???p l??? kh??ng.
				if(valDai.isEmpty() || valRong.isEmpty() || Integer.parseInt(valDai)<Integer.parseInt(valRong) || Integer.parseInt(valDai)<=0 || Integer.parseInt(valRong)<=0) {
					JOptionPane.showMessageDialog(null,"Chi???u d??i ho???c chi???u r???ng kh??ng h???p l???. Vui l??ng nh???p l???i.","Note",JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						//G???i gi?? tr??? chi???u d??i, chi???u r???ng v?? t??n c???a client
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
		//???n n??t ????? g???i gi?? tr??? 2 tham s??? a, b ?????n server ????? gi???i ph????ng tr??nh ax + b = 0
		btnPhuongTrinh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valA = a.getText().trim();
				String valB = b.getText().trim();
				//Ki???m tra gi?? tr??? 2 tham s??? a, b c?? h???p l??? kh??ng.
				if(valA.isEmpty() || valB.isEmpty()) {
					JOptionPane.showMessageDialog(null,"H??? s??? a ho???c b ch??a ???????c nh???p. Vui l??ng nh???p l???i.","Note",JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						//G???i gi?? tr??? 2 tham s??? a, b v?? t??n c???a client
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
		//???n n??t ????? g???i s??? ti???n Vi???t ?????n server ????? ?????i th??nh ti???n USD
		btnDoiTien.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tienViet = vnd.getText().trim();
				//Ki???m tra gi?? tr??? ti???n Vi???t c?? h???p l??? kh??ng.
				if(tienViet.isEmpty() || Integer.parseInt(tienViet) <= 0) {
					JOptionPane.showMessageDialog(null,"S??? ti???n c???n ?????i kh??ng h???p l???. Vui l??ng nh???p l???i.","Note",JOptionPane.WARNING_MESSAGE);
				}
				else {
					try {
						//G???i s??? ti???n Vi???t v?? t??n c???a client
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
		//???n n??t exit ????? tho??t user
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
				//Nh???n tin nh???n c???a c??c client kh??c
				if (method.equals("Text")) {
					String sender =	dataInputStream.readUTF();
					String message = dataInputStream.readUTF();
					if(sender.equals(name) == false) {
						//In tin nh???n l??n m??n h??nh chat v???i ng?????i g???i
						Message.setText(Message.getText() + "\n" + sender + ": " + message);
					}else {
						//In l??n n???u hi???n th??? ngay khung chat c???a ch??nh client g???i
						Message.setText(Message.getText() + "\nMe: " + message);
					}
				}
				//Nh???n th??ng tin c???p nh???t danh s??ch client
				else if (method.equals("Friends")) {
					String[] users = dataInputStream.readUTF().split(",");
					model = new DefaultListModel<>();
					for(String user:users) {
						if(user != name) {
							model.addElement(user);
						}
					}
				}
				//Nh???n k???t qu??? t??nh di???n t??ch
				else if(method.equals("DienTich")) {
					String sender =	dataInputStream.readUTF();
					String DienTich = dataInputStream.readUTF();
					dienTich.setText(DienTich);
				}
				//Nh???n k???t qu??? gi???i ph????ng tr??nh
				else if(method.equals("PhuongTrinh")) {
					String sender =	dataInputStream.readUTF();
					String PT = dataInputStream.readUTF();
					x.setText(PT);
				}
				//Nh???n k???t qu??? ?????i ti???n
				else if(method.equals("DoiTien")) {
					String sender =	dataInputStream.readUTF();
					String tienDo = dataInputStream.readUTF();
					usd.setText(tienDo);
				}
				//Nh???n k???t qu??? tho??t
				else if(method.equals("EXIT")) {
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//????ng socket
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
