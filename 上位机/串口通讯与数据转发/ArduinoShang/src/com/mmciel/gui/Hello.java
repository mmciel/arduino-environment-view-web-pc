package com.mmciel.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import com.mmciel.serialException.ExceptionWriter;
/**
 * ���ƻ�ӭ����
 * @author mmciel
 * @time 2018��12��26��17:26:12
 */
public class Hello extends Frame{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 620;
	public static final int LOC_X = 200;
	public static final int LOC_Y = 70;

	Color color = Color.WHITE; 
	Image offScreen = null;	//����˫����

	public static void main(String[] args) {
		new Hello().launchFrame();	
	}
	/**
	 * �����߼�
	 */
	public void launchFrame() {
		this.setBounds(LOC_X, LOC_Y, WIDTH, HEIGHT);
		this.setTitle("Arduino �������ݼ��������ݿ�ͬ��");
		this.setBackground(Color.white);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);	
			}
		});
		this.addKeyListener(new KeyMonitor());
		this.setResizable(false);
		this.setVisible(true);
			
		new Thread(new RepaintThread()).start();
	}
	/**
	 * �������
	 */
	
	public void paint(Graphics g) {
		g.setFont(new Font("��Բ", Font.BOLD, 40));
		g.setColor(Color.black);
		g.drawString("��ӭʹ��YiSoo���ڹ���", 45, 190);
		
		g.setFont(new Font("��Բ", Font.ITALIC, 26));
		g.setColor(Color.BLACK);
		g.drawString("Version��1.1   Powered By��mmciel", 280, 260);
		
		g.setFont(new Font("��Բ", Font.BOLD, 30));
		g.setColor(color);
		g.drawString("�����������Enter�����������桪������", 100, 480);
		if (color == Color.WHITE)	color = Color.black;
		else if (color == Color.BLACK)	color = Color.white;
		
		
	}
	/**
	 * update
	 */
	public void update(Graphics g) {
		if (offScreen == null)	offScreen = this.createImage(WIDTH, HEIGHT);
		Graphics gOffScreen = offScreen.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.white);
		gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
		this.paint(gOffScreen);
		gOffScreen.setColor(c);
		g.drawImage(offScreen, 0, 0, null);
	}
	/**
	 * enter event
	 */
	private class KeyMonitor extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_ENTER) {
				setVisible(false);
				Main.start();
			}
		}
		
	}
	/**
	 * runnable
	 */
	private class RepaintThread implements Runnable {
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					String err = ExceptionWriter.getErrorInfoFromException(e);
					JOptionPane.showMessageDialog(null, err, "����", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
		}
	}
}
