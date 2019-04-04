package com.mmciel.tool;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import com.mmciel.serialException.*;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;


/**
 * ���ڹ����࣬�򿪡��رմ��ڣ���ȡ�����ʹ�������
 * @author mmciel
 *
 */
public class SerialTool {
	
	private static SerialTool serialTool = null;
	
	static {
		if (serialTool == null) {
			serialTool = new SerialTool();
		}
	}
	private SerialTool() {}	
	
	/**
	 * ��ȡSerialTool����
	 */
	public static SerialTool getSerialTool() {
		if (serialTool == null) {
			serialTool = new SerialTool();
		}
		return serialTool;
	}


	/**
	 * �������п��ö˿�
	 */
	public static final ArrayList<String> findPort() {

		//��õ�ǰ���п��ô���
        @SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();	
        
        ArrayList<String> portNameList = new ArrayList<>();

        //�����ô�������ӵ�List�����ظ�List
        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }

        return portNameList;

    }
    
    /**
     * �򿪴���
     * @param portName �˿�����
     * @param baudrate ������
     * @return ���ڶ���
     * @throws SerialPortParameterFailure ���ô��ڲ���ʧ��
     * @throws NotASerialPort �˿�ָ���豸���Ǵ�������
     * @throws NoSuchPort û�иö˿ڶ�Ӧ�Ĵ����豸
     * @throws PortInUse �˿��ѱ�ռ��
     */
    public static final SerialPort openPort(String portName, int baudrate) throws SerialPortParameterFailure, NotASerialPort, NoSuchPort, PortInUse {

        try {
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            CommPort commPort = portIdentifier.open(portName, 2000);
            if (commPort instanceof SerialPort) {
            	
                SerialPort serialPort = (SerialPort) commPort;
                
                try {                    	
                    serialPort.setSerialPortParams(baudrate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);                              
                } catch (UnsupportedCommOperationException e) {  
                	throw new SerialPortParameterFailure();
                }
                return serialPort;
            
            }        
            else {
            	throw new NotASerialPort();
            }
        } catch (NoSuchPortException e1) {
          throw new NoSuchPort();
        } catch (PortInUseException e2) {
        	throw new PortInUse();
        }
    }
    
    /**
     * �رմ���
     * @param serialport ���رյĴ��ڶ���
     */
    public static void closePort(SerialPort serialPort) {
    	if (serialPort != null) {
    		serialPort.close();
    		serialPort = null;
    	}
    }
    
    /**
     * �����ڷ�������
     * @param serialPort ���ڶ���
     * @param order	����������
     * @throws SendDataToSerialPortFailure �򴮿ڷ�������ʧ��
     * @throws SerialPortOutputStreamCloseFailure �رմ��ڶ�������������
     */
    public static void sendToPort(SerialPort serialPort, byte[] order) throws SendDataToSerialPortFailure, SerialPortOutputStreamCloseFailure {

    	OutputStream out = null;
    	
        try {
        	
            out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
            
        } catch (IOException e) {
        	throw new SendDataToSerialPortFailure();
        } finally {
        	try {
        		if (out != null) {
        			out.close();
        			out = null;
        		}				
			} catch (IOException e) {
				throw new SerialPortOutputStreamCloseFailure();
			}
        }
        
    }
   // public static String temp = "";
    /**
     * �Ӵ��ڶ�ȡ����
     * @param serialPort ��ǰ�ѽ������ӵ�SerialPort����
     * @return ��ȡ��������
     * @throws ReadDataFromSerialPortFailure �Ӵ��ڶ�ȡ����ʱ����
     * @throws SerialPortInputStreamCloseFailure �رմ��ڶ�������������
     */
    public static byte[] readFromPort(SerialPort serialPort) throws ReadDataFromSerialPortFailure, SerialPortInputStreamCloseFailure {

    	InputStream in = null;
        byte[] bytes = null;

        try {
        	
        	in = serialPort.getInputStream();
        	int bufflenth = in.available();		//��ȡbuffer������ݳ���
            
        	while (bufflenth != 0) {                             
                bytes = new byte[bufflenth];	//��ʼ��byte����Ϊbuffer�����ݵĳ���
        		///bytes = new byte[100];
        		in.read(bytes);
                bufflenth = in.available();
        	} 
        } catch (IOException e) {
        	throw new ReadDataFromSerialPortFailure();
        } finally {
        	try {
            	if (in != null) {
            		in.close();
            		in = null;
            	}
        	} catch(IOException e) {
        		throw new SerialPortInputStreamCloseFailure();
        	}

        }
        //temp+=new String(bytes);
       // System.out.println(temp); 
        return bytes;

    }
    
    /**
     * ������
     */
    public static void addListener(SerialPort port, SerialPortEventListener listener) throws TooManyListeners {

        try {
        	
            //��������Ӽ�����
            port.addEventListener(listener);
            //���õ������ݵ���ʱ���Ѽ��������߳�
            port.notifyOnDataAvailable(true);
          //���õ�ͨ���ж�ʱ�����ж��߳�
            port.notifyOnBreakInterrupt(true);

        } catch (TooManyListenersException e) {
        	throw new TooManyListeners();
        }
    }
    
    
}
