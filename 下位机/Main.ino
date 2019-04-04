/*
 Name:		Main.ino
 Created:	2019/1/2 9:16:31
 Author:	mmciel
*/
#include <ir_Lego_PF_BitStreamEncoder.h>
#include <IRremoteInt.h>
#include <IRremote.h>
#include <boarddefs.h>
#include <LiquidCrystal.h>
#include <String.h>
#include <dht11.h>

//��ʪ�ȶ���
dht11 DHT11;
//�Ž�У�飨����ʶ��
int RECV_PIN = 10;
IRrecv irrecv(RECV_PIN);
decode_results h_results;
//lcd����
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
//����ʱ��
unsigned long seconds;
int s = 0, m = 0, h = 0, d = 0, mon = 0, y = 0;   //ʱ���λ
int second = 0, minute = 0, hour = 0, day = 0, month = 0, year = 0;  //��ǰʱ��
int SECOND = 0, MINUTE = 0, HOUR = 0, DAY = 0, MONTH = 0, YEAR = 0;  //��ʼʱ��
int chose = 0, ButtonDelay = 10;
//����ʱ��
//��ʽ�����
void FormatDisplay(int col, int row, int num);
//����ʱ��
void time();
//�������¼��㵱������
int Days(int year, int month);
//���㵱������
void Day();
//�����·�
void Month();
//������� 
void Year();
//���������ռ������ڼ�
void Week(int y, int m, int d);
//��ʾʱ�䡢���ڡ�����
void Display();
//��ʾ���
void DisplayCursor(int rol, int row);
//���ó�ʼʱ��
void set(int y, int mon, int d, int h, int m, int s);

void welcome();//��ӭ����
void print2line(String line1, String line2);//�������
void printline(String line, int order);//���һ��


int ok_data = 6;
int ok_led = 9;
int ok_feng = 8;
int flag1 = 1;
int flag2 = 1;
int flag3 = 1;
/*==========================================================================*/
void setup() {
	//lcd��ʼ��
	lcd.begin(16, 2);
	//��ʼ���������
	irrecv.enableIRIn();
	//��ʼ������ͨ��
	Serial.begin(9600);
	//ʱ�ӳ�ʼ��
	set(2019, 1, 3, 9, 30, 50);
	//���������ʼ��
	pinMode(ok_data, OUTPUT);
	pinMode(ok_led, OUTPUT);
	pinMode(ok_feng, OUTPUT);
	digitalWrite(ok_data,HIGH);
	digitalWrite(ok_led, HIGH);
	digitalWrite(ok_feng, HIGH);
}

void loop() {
	welcome();
	const String MENU_NUMMBER = ">1Auto  >2Look  ";//�˵������
	String line1 = MENU_NUMMBER;
	String line2 = ">3Tool  >4Data  ";

	int order = 0;//�����¼
	lcd.clear();//����
	print2line(line1, line2);//��ʾ�˵�

	//��������
	long b_h_data[] = {
		16580863,
		16613503,
		16597183,
		16629823,
		16589023,
		16621663,
		16605343,
		16637983,
		16584943,
		16617583,
		16623703 };

	while (1) {
		if (irrecv.decode(&h_results)) {//���������ж�
			long order_data = h_results.value;
			delay(200);
			irrecv.resume();
			if (order_data == b_h_data[1]) {
				/*
				��ʾʱ�䣬������˳�
				*/
				lcd.clear();
				boolean flag_temp = true;
				while (flag_temp) {
					seconds = millis() / 1000;    //��ȡ��ǰ����ʱ�� 
					Display();       //��ʾʱ��
					lcd.setCursor(8, 1);  lcd.print("         ");
					if (irrecv.decode(&h_results)) {
						delay(200);
						flag_temp = !flag_temp;
						irrecv.resume();
					}
				}

				print2line(line1, line2);
			}
			else if (order_data == b_h_data[2]) {
				/*
					��ȡ��ʪ��
				*/
				lcd.clear();
				boolean flag_temp = true;
				while (flag_temp) {
					DHT11.read(A0);
					lcd.setCursor(0, 0);
					lcd.print(">H: ");
					lcd.setCursor(4, 0);
					lcd.print((int)DHT11.humidity);
					lcd.setCursor(6, 0);
					lcd.print("% >T: ");
					lcd.setCursor(13, 0);
					lcd.print((int)DHT11.temperature);
					lcd.print("C");
					lcd.setCursor(0, 1);
					lcd.print("return          ");
					if (irrecv.decode(&h_results)) {
						delay(200);
						flag_temp = !flag_temp;
						irrecv.resume();
					}
				}
				print2line(line1, line2);
			}
			else if (order_data == b_h_data[3]) {
				/*
				   Tool���ܵĿ���
				*/
				lcd.clear();
				boolean flag_temp = true;
				while (flag_temp) {
					String temp1 = "SUN:1   FENG:2  ";
					String temp2 = "                ";
					if (flag2 == 1 && flag3 == 1) {
						temp2 = "SUN:on  FENG:on ";
					}
					else if (flag2 == 1 && flag3 == 0) {
						temp2 = "SUN:on  FENG:off";
					}
					else if (flag2 == 0 && flag3 == 1) {
						temp2 = "SUN:off FENG:on ";
					}
					else if (flag2 == 0 && flag3 == 0) {
						temp2 = "SUN:off FENG:off";
					}
					print2line(temp1, temp2);
					if (irrecv.decode(&h_results)) {
						delay(200);
						flag_temp = !flag_temp;
						long order_data = h_results.value;
						irrecv.resume();
						if (order_data == b_h_data[1]) {
							if (flag2 == 1) {
								flag2 = 0;
								digitalWrite(ok_led, flag2);
							}
							else {
								flag2 = 1;
								digitalWrite(ok_led, flag2);
							}
						}
						if (order_data == b_h_data[2]) {
							if (flag3 == 1) {
								flag3 = 0;
								digitalWrite(ok_feng, flag3);
							}
							else {
								flag3 = 1;
								digitalWrite(ok_feng, flag3);
							}
						}
					}
				}
				print2line(line1, line2);
			}
			else if (order_data == b_h_data[4]) {
				/*
					Data���ܵĿ���
				*/
				lcd.clear();
				boolean flag_temp = true;
				while (flag_temp) {
					String temp1 = "ON or OFF Serial";
					String temp2 = "                ";
					if (flag1 == 1) {
						temp2 = "now: on         ";
					}
					else {
						temp2 = "now: off        ";
					}
					print2line(temp1, temp2);
					if (irrecv.decode(&h_results)) {
						delay(200);
						flag_temp = !flag_temp;
						irrecv.resume();
						if (flag1 == 1) {
							flag1 = 0;
							digitalWrite(ok_data, flag1);
						}
						else {
							flag1 = 1;
							digitalWrite(ok_data, flag1);
						}
					}
				}
				print2line(line1, line2);
			}
		}
	}
}

/*==========================================================================*/
void print2line(String line1, String line2)
{
	lcd.setCursor(0, 0);
	lcd.print(line1);
	lcd.setCursor(0, 1);
	lcd.print(line2);
}
void printline(String line, int order)
{
	const String S = "               ";
	if (order == 0) {
		lcd.setCursor(0, 0);
		lcd.print(S);
		lcd.setCursor(0, 0);
		lcd.print(line);
	}
	else if (order == 1) {
		lcd.setCursor(0, 1);
		lcd.print(S);
		lcd.setCursor(0, 1);
		lcd.print(line);
	}

}
/*==========================================================================*/
void welcome()
{
	lcd.setCursor(0, 0);
	lcd.print("Welcome System!");
	lcd.setCursor(0, 1);
	lcd.print("    --by:arduino");
	delay(2000);
	lcd.clear();
	lcd.setCursor(0, 0);
	lcd.print("reading");
	delay(500);
	lcd.clear();
	lcd.setCursor(0, 0);
	lcd.print("reading.");
	delay(500);
	lcd.clear();
	lcd.setCursor(0, 0);
	lcd.print("reading..");
	delay(500);
	lcd.clear();
	lcd.setCursor(0, 0);
	lcd.print("reading...");
	delay(500);
}
/*==========================================================================*/
/*==========================================================================*/
/** ��ʽ�����  */
void FormatDisplay(int col, int row, int num) {
	lcd.setCursor(col, row);
	if (num < 10) {
		lcd.print("0");
	}
	lcd.print(num);
}
/** ����ʱ�� */
void time() {
	second = (SECOND + seconds) % 60;   //������
	m = (SECOND + seconds) / 60;        //���ӽ�λ
	FormatDisplay(6, 1, second);

	minute = (MINUTE + m) % 60;  //�������
	h = (MINUTE + m) / 60;       //Сʱ��λ
	FormatDisplay(3, 1, minute);

	hour = (HOUR + h) % 24;   //����Сʱ
	d = (HOUR + h) / 24;      //������λ
	FormatDisplay(0, 1, hour);

	lcd.setCursor(2, 1);   lcd.print(":");
	lcd.setCursor(5, 1);   lcd.print(":");
}
/** �������¼��㵱������ */
int Days(int year, int month) {
	int days = 0;
	if (month != 2) {
		switch (month) {
		case 1: case 3: case 5: case 7: case 8: case 10: case 12: days = 31;  break;
		case 4: case 6: case 9: case 11:  days = 30;  break;
		}
	}
	else {   //����    
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			days = 29;
		}
		else {
			days = 28;
		}
	}
	return days;
}
/** ���㵱������ */
void Day() {
	int days = Days(year, month);
	int days_up;
	if (month == 1) {
		days_up = Days(year - 1, 12);
	}
	else {
		days_up = Days(year, month - 1);
	}

	day = (DAY + d) % days;
	if (day == 0) {
		day = days;
	}
	if ((DAY + d) == days + 1) {
		DAY -= days;
		mon++;
	}
	if ((DAY + d) == 0) {
		DAY += days_up;
		mon--;
	}
	FormatDisplay(8, 0, day);
}
/** �����·� */
void Month() {
	month = (MONTH + mon) % 12;
	if (month == 0) {
		month = 12;
	}
	y = (MONTH + mon - 1) / 12;
	FormatDisplay(5, 0, month);
	lcd.setCursor(7, 0);
	lcd.print('-');
}
/** ������� */
void Year() {
	year = (YEAR + y) % 9999;
	if (year == 0) {
		year = 9999;
	}
	lcd.setCursor(0, 0);
	if (year < 1000) {
		lcd.print("0");
	}
	if (year < 100) {
		lcd.print("0");
	}
	if (year < 10) {
		lcd.print("0");
	}
	lcd.print(year);
	lcd.setCursor(4, 0);
	lcd.print('-');
}
/** ���������ռ������ڼ� */
void Week(int y, int m, int d) {
	if (m == 1) m = 13;
	if (m == 2) m = 14;
	int week = (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7 + 1;
	String weekstr = "";
	switch (week) {
	case 1: weekstr = "Mon. ";   break;
	case 2: weekstr = "Tues. ";  break;
	case 3: weekstr = "Wed. ";   break;
	case 4: weekstr = "Thur. ";  break;
	case 5: weekstr = "Fri. ";   break;
	case 6: weekstr = "Sat. ";   break;
	case 7: weekstr = "Sun. ";   break;
	}
	lcd.setCursor(11, 0);  lcd.print(weekstr);
}
/** ��ʾʱ�䡢���ڡ�����  */
void Display() {
	time();
	Day();
	Month();
	Year();
	Week(year, month, day);
}
/** ��ʾ��� */
void DisplayCursor(int rol, int row) {
	lcd.setCursor(rol, row);
	lcd.cursor();
	delay(10);
	lcd.noCursor();
	delay(10);
}
/** ���ó�ʼʱ�� */
void set(int y, int mon, int d, int h, int m, int s) {
	YEAR = y;
	MONTH = mon;
	DAY = d;
	HOUR = h;
	MINUTE = m;
	SECOND = s;
}
/*==========================================================================*/