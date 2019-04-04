package com.mmciel.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import com.mmciel.data.DataIni;
import com.mmciel.data.DataMain;
import com.mmciel.data.MysqlLink;
import com.mmciel.serialException.NoSuchPort;
import com.mmciel.serialException.NotASerialPort;
import com.mmciel.serialException.PortInUse;
import com.mmciel.serialException.ReadDataFromSerialPortFailure;
import com.mmciel.serialException.SendDataToSerialPortFailure;
import com.mmciel.serialException.SerialPortInputStreamCloseFailure;
import com.mmciel.serialException.SerialPortOutputStreamCloseFailure;
import com.mmciel.serialException.SerialPortParameterFailure;
import com.mmciel.serialException.TooManyListeners;
import com.mmciel.tool.SerialTool;
import com.mmciel.tool.Tools;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;


import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * 主界面
 * @author mmciel
 * ps：我不允许我的代码有一个警告
 */
public class Main {

	private MysqlLink link = null;
	private JFrame MainFrame;
	private DataMain datamain = null;
	private JTextField urlText;
	private JTextField userText;
	private JTextField pwText;
	private JTextField tableText;
	private JTextField sendText;
	@SuppressWarnings("rawtypes")
	private JComboBox bpsChoice;
	@SuppressWarnings("rawtypes")
	private JComboBox comChoice;
	private JTextArea viewText;
	private List<String> commList = null;	//端口
	private SerialPort serialPort = null;	//串口

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.MainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * 入口
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.MainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Main() {
		initialize();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		commList = SerialTool.findPort();
		
		MainFrame = new JFrame();
		MainFrame.setBounds(100, 100, 1100, 709);
		MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainFrame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(15, 15, 614, 623);
		MainFrame.getContentPane().add(panel);
		panel.setLayout(null);
		
		bpsChoice = new JComboBox();
		bpsChoice.setFont(new Font("幼圆", Font.PLAIN, 25));
		bpsChoice.setModel(new DefaultComboBoxModel(new String[] {"9600", "1200", "2400", "14400", "19200", "115200"}));
		bpsChoice.setBounds(332, 292, 267, 37);
		panel.add(bpsChoice);
		
		comChoice = new JComboBox();
		comChoice.setFont(new Font("幼圆", Font.PLAIN, 25));
		comChoice.setBounds(34, 98, 198, 37);
		//panel.add(comChoice);
		
		sendText = new JTextField();
		sendText.setFont(new Font("幼圆", Font.PLAIN, 25));
		sendText.setBounds(34, 231, 391, 46);
		panel.add(sendText);
		sendText.setColumns(10);
		
		JButton sendButton = new JButton("\u53D1\u9001");

		sendButton.setFont(new Font("幼圆", Font.PLAIN, 25));
		sendButton.setBounds(440, 234, 139, 41);
		panel.add(sendButton);
		
		JButton openButton = new JButton("\u6253\u5F00\u4E32\u53E3");

		openButton.setFont(new Font("幼圆", Font.PLAIN, 25));
		openButton.setBounds(440, 98, 139, 37);
		panel.add(openButton);
		
		JLabel lblNewLabel_2 = new JLabel("\u4E32\u53E3\u8C03\u8BD5");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("幼圆", Font.PLAIN, 30));
		lblNewLabel_2.setBounds(37, 28, 532, 55);
		panel.add(lblNewLabel_2);
		
		JButton closeButton = new JButton("\u5173\u95ED\u4E32\u53E3");

		closeButton.setFont(new Font("幼圆", Font.PLAIN, 25));
		closeButton.setBounds(440, 162, 139, 37);
		panel.add(closeButton);
		
		JPanel DataPanel = new JPanel();
		DataPanel.setBounds(644, 15, 419, 623);
		MainFrame.getContentPane().add(DataPanel);
		DataPanel.setLayout(null);
		
		urlText = new JTextField();
		urlText.setText("jdbc:mysql://111.230.198.57:3306/arduinodata");
		urlText.setFont(new Font("幼圆", Font.PLAIN, 25));
		urlText.setBounds(195, 95, 209, 37);
		DataPanel.add(urlText);
		urlText.setColumns(10);
		
		userText = new JTextField();
		userText.setText("root");
		userText.setFont(new Font("幼圆", Font.PLAIN, 25));
		userText.setColumns(10);
		userText.setBounds(195, 181, 209, 37);
		DataPanel.add(userText);
		
		pwText = new JTextField();
		pwText.setText("123456");
		pwText.setFont(new Font("幼圆", Font.PLAIN, 25));
		pwText.setColumns(10);
		pwText.setBounds(195, 277, 209, 37);
		DataPanel.add(pwText);
		
		tableText = new JTextField();
		tableText.setText("tabledata");
		tableText.setFont(new Font("幼圆", Font.PLAIN, 25));
		tableText.setColumns(10);
		tableText.setBounds(195, 373, 209, 37);
		DataPanel.add(tableText);
		
		JLabel lblNewLabel = new JLabel("MySQL url");
		lblNewLabel.setFont(new Font("幼圆", Font.PLAIN, 25));
		lblNewLabel.setBounds(15, 95, 165, 37);
		DataPanel.add(lblNewLabel);
		
		JLabel lblDbUser = new JLabel("DB \u7528\u6237\u540D");
		lblDbUser.setFont(new Font("幼圆", Font.PLAIN, 25));
		lblDbUser.setBounds(15, 181, 165, 37);
		DataPanel.add(lblDbUser);
		
		JLabel lblDbPw = new JLabel("DB \u5BC6\u7801");
		lblDbPw.setFont(new Font("幼圆", Font.PLAIN, 25));
		lblDbPw.setBounds(15, 277, 165, 37);
		DataPanel.add(lblDbPw);
		
		JLabel mess = new JLabel("");
		mess.setFont(new Font("幼圆", Font.PLAIN, 25));
		mess.setBounds(34, 162, 378, 37);
		panel.add(mess);
		
		JLabel lblDb = new JLabel("DB \u8868\u540D");
		lblDb.setFont(new Font("幼圆", Font.PLAIN, 25));
		lblDb.setBounds(15, 373, 165, 37);
		DataPanel.add(lblDb);
		
		JButton startButton = new JButton("\u542F\u52A8\u670D\u52A1");

		startButton.setFont(new Font("幼圆", Font.PLAIN, 25));
		startButton.setBounds(15, 535, 165, 49);
		DataPanel.add(startButton);
		
		JButton endButton = new JButton("\u505C\u6B62\u670D\u52A1");

		endButton.setFont(new Font("幼圆", Font.PLAIN, 25));
		endButton.setBounds(222, 535, 165, 49);
		DataPanel.add(endButton);
		
		JLabel servermess = new JLabel("\u670D\u52A1\u672A\u8FD0\u884C");
		servermess.setFont(new Font("幼圆", Font.PLAIN, 28));
		servermess.setBounds(25, 451, 255, 54);
		DataPanel.add(servermess);
		
		JLabel label = new JLabel("\u6570\u636E\u540C\u6B65\u4E0A\u4F20");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("幼圆", Font.PLAIN, 30));
		label.setBounds(25, 28, 362, 49);
		DataPanel.add(label);
		
		JButton linkButton = new JButton("\u8FDE\u63A5");
		linkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = urlText.getText();
				String user = userText.getText();
				String password = pwText.getText();
				if(url.isEmpty()) {
					url = DataIni.mmciel_mysql_url;
				}
				if(user.isEmpty()) {
					user = DataIni.mmciel_mysql_user;
				}
				if(password.isEmpty()) {
					password = DataIni.mmciel_mysql_password;
				}
				link = new MysqlLink(url, user, password);
				if(link.getLinkTest()) {
					servermess.setText("连接成功");
				}else{
					servermess.setText("连接失败");
				}
			}
		});
		linkButton.setFont(new Font("幼圆", Font.PLAIN, 25));
		linkButton.setBounds(295, 458, 95, 43);
		DataPanel.add(linkButton);
		MainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				if (serialPort != null) {
					//程序退出时关闭串口释放资源
					SerialTool.closePort(serialPort);
				}
				System.exit(0);
			}
			
		});
		
		if (commList == null || commList.size()<1) {
			JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for (String s : commList) {
				comChoice.addItem(s);
			}
		}
		panel.add(comChoice);
		mess.setText("串口已经刷新");
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(25, 344, 574, 264);
		panel.add(scrollPane);
		
		viewText = new JTextArea();
		viewText.setBounds(25, 344, 574, 264);
		viewText.setLineWrap(true);
		scrollPane.setViewportView(viewText);
		
		JButton clearButton = new JButton("\u6E05\u7A7A");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewText.setText("");
				mess.setText("数据已经清空");
			}
		});
		clearButton.setFont(new Font("幼圆", Font.PLAIN, 25));
		clearButton.setBounds(25, 292, 262, 37);
		panel.add(clearButton);
		
		JButton f5Button = new JButton("\u4E32\u53E3\u5237\u65B0");

		f5Button.setFont(new Font("幼圆", Font.PLAIN, 25));
		f5Button.setBounds(273, 98, 139, 36);
		panel.add(f5Button);
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取串口名称
				String commName = comChoice.getSelectedItem().toString();			
				//获取波特率
				String bpsStr = bpsChoice.getSelectedItem().toString();
				
				if (commName == null || commName.equals("")) {
					JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);			
				}
				else {
					
					if (bpsStr == null || bpsStr.equals("")) {
						JOptionPane.showMessageDialog(null, "波特率获取错误！", "错误", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						
						int bps = Integer.parseInt(bpsStr);
						try {
							
							
							serialPort = SerialTool.openPort(commName, bps);
							
							SerialTool.addListener(serialPort, new SerialListener());	
						} catch (SerialPortParameterFailure | NotASerialPort | NoSuchPort | PortInUse | TooManyListeners e1) {
							
							JOptionPane.showMessageDialog(null, e1, "错误", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
				mess.setText("串口数据已经更新");
			}
		});
		f5Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				commList = SerialTool.findPort();
				if (commList == null || commList.size()<1) {
					JOptionPane.showMessageDialog(null, "没有搜索到有效串口！", "错误", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					for (String s : commList) {
						comChoice.addItem(s);
					}
				}
				panel.add(comChoice);
				mess.setText("串口已经刷新");
			}
		});
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (serialPort != null) {
					SerialTool.closePort(serialPort);
				}
				mess.setText("串口已经关闭");
			}
		});
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String temp = sendText.getText();
				byte[] bytes = temp.getBytes();
				try {
					SerialTool.sendToPort(serialPort, bytes);
				} catch (SendDataToSerialPortFailure e1) {
					e1.printStackTrace();
				} catch (SerialPortOutputStreamCloseFailure e1) {
					e1.printStackTrace();
				}
				mess.setText("数据已经发送");
			}
		});
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(link!=null && link.getLinkTest()) {
					servermess.setText("服务启动");
					datamain = new DataMain(link);
					datamain.go();
				}else {
					servermess.setText("数据库未连接");
				}
				
			}
		});
		endButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(datamain!=null) {
					datamain.stop();
					link.Close();
					servermess.setText("服务已关闭");
				}
			}
		});
	}
private class SerialListener implements SerialPortEventListener {
		String[] tempStr  = null;
		String temp = "";
	    public void serialEvent(SerialPortEvent serialPortEvent) {
	    	
	        switch (serialPortEvent.getEventType()) {

	            case SerialPortEvent.BI: // 10 通讯中断
	            	JOptionPane.showMessageDialog(null, "与串口设备通讯中断", "错误", JOptionPane.INFORMATION_MESSAGE);
	            	break;

	            case SerialPortEvent.OE: // 7 溢位（溢出）错误

	            case SerialPortEvent.FE: // 9 帧错误

	            case SerialPortEvent.PE: // 8 奇偶校验错误

	            case SerialPortEvent.CD: // 6 载波检测

	            case SerialPortEvent.CTS: // 3 清除待发送数据

	            case SerialPortEvent.DSR: // 4 待发送数据准备好了

	            case SerialPortEvent.RI: // 5 振铃指示

	            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
	            	break;
	            
	            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
					byte[] data = null;
					
					try {
						if (serialPort == null) {
							JOptionPane.showMessageDialog(null, "串口对象为空！监听失败！", "错误", JOptionPane.INFORMATION_MESSAGE);
						}
						else {
							data = SerialTool.readFromPort(serialPort);
							temp+=new String(data);
							if(temp.length()>13*5) {
								tempStr = Tools.getSerialString(temp);	
								Tools.UploadData = tempStr;
								for(String i:tempStr) {
									if(i.length() == 11) {
										viewText.append(i+"\n");
									}
								}
							}
						}						
					} catch (ReadDataFromSerialPortFailure | SerialPortInputStreamCloseFailure e) {
						JOptionPane.showMessageDialog(null, e, "错误", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}		            
					break;
	        }

	    }

	}
	
}

