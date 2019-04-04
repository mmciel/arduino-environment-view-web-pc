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

//温湿度对象
dht11 DHT11;
//门禁校验（红外识别）
int RECV_PIN = 10;
IRrecv irrecv(RECV_PIN);
decode_results h_results;
//lcd对象
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
//电子时钟
unsigned long seconds;
int s = 0, m = 0, h = 0, d = 0, mon = 0, y = 0;   //时间进位
int second = 0, minute = 0, hour = 0, day = 0, month = 0, year = 0;  //当前时间
int SECOND = 0, MINUTE = 0, HOUR = 0, DAY = 0, MONTH = 0, YEAR = 0;  //初始时间
int chose = 0, ButtonDelay = 10;
//电子时钟
//格式化输出
void FormatDisplay(int col, int row, int num);
//计算时间
void time();
//根据年月计算当月天数
int Days(int year, int month);
//计算当月天数
void Day();
//计算月份
void Month();
//计算年份 
void Year();
//根据年月日计算星期几
void Week(int y, int m, int d);
//显示时间、日期、星期
void Display();
//显示光标
void DisplayCursor(int rol, int row);
//设置初始时间
void set(int y, int mon, int d, int h, int m, int s);

void welcome();//欢迎界面
void print2line(String line1, String line2);//输出两行
void printline(String line, int order);//输出一行


int ok_data = 6;
int ok_led = 9;
int ok_feng = 8;
int flag1 = 1;
int flag2 = 1;
int flag3 = 1;
/*==========================================================================*/
void setup() {
	//lcd初始化
	lcd.begin(16, 2);
	//初始化红外解码
	irrecv.enableIRIn();
	//初始化串口通信
	Serial.begin(9600);
	//时钟初始化
	set(2019, 1, 3, 9, 30, 50);
	//命令输出初始化
	pinMode(ok_data, OUTPUT);
	pinMode(ok_led, OUTPUT);
	pinMode(ok_feng, OUTPUT);
	digitalWrite(ok_data,HIGH);
	digitalWrite(ok_led, HIGH);
	digitalWrite(ok_feng, HIGH);
}

void loop() {
	welcome();
	const String MENU_NUMMBER = ">1Auto  >2Look  ";//菜单命令常量
	String line1 = MENU_NUMMBER;
	String line2 = ">3Tool  >4Data  ";

	int order = 0;//命令记录
	lcd.clear();//清屏
	print2line(line1, line2);//显示菜单

	//红外编码表
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
		if (irrecv.decode(&h_results)) {//红外输入判断
			long order_data = h_results.value;
			delay(200);
			irrecv.resume();
			if (order_data == b_h_data[1]) {
				/*
				显示时间，任意键退出
				*/
				lcd.clear();
				boolean flag_temp = true;
				while (flag_temp) {
					seconds = millis() / 1000;    //获取当前运行时间 
					Display();       //显示时间
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
					读取温湿度
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
				   Tool功能的开关
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
					Data功能的开关
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
/** 格式化输出  */
void FormatDisplay(int col, int row, int num) {
	lcd.setCursor(col, row);
	if (num < 10) {
		lcd.print("0");
	}
	lcd.print(num);
}
/** 计算时间 */
void time() {
	second = (SECOND + seconds) % 60;   //计算秒
	m = (SECOND + seconds) / 60;        //分钟进位
	FormatDisplay(6, 1, second);

	minute = (MINUTE + m) % 60;  //计算分钟
	h = (MINUTE + m) / 60;       //小时进位
	FormatDisplay(3, 1, minute);

	hour = (HOUR + h) % 24;   //计算小时
	d = (HOUR + h) / 24;      //天数进位
	FormatDisplay(0, 1, hour);

	lcd.setCursor(2, 1);   lcd.print(":");
	lcd.setCursor(5, 1);   lcd.print(":");
}
/** 根据年月计算当月天数 */
int Days(int year, int month) {
	int days = 0;
	if (month != 2) {
		switch (month) {
		case 1: case 3: case 5: case 7: case 8: case 10: case 12: days = 31;  break;
		case 4: case 6: case 9: case 11:  days = 30;  break;
		}
	}
	else {   //闰年    
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			days = 29;
		}
		else {
			days = 28;
		}
	}
	return days;
}
/** 计算当月天数 */
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
/** 计算月份 */
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
/** 计算年份 */
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
/** 根据年月日计算星期几 */
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
/** 显示时间、日期、星期  */
void Display() {
	time();
	Day();
	Month();
	Year();
	Week(year, month, day);
}
/** 显示光标 */
void DisplayCursor(int rol, int row) {
	lcd.setCursor(rol, row);
	lcd.cursor();
	delay(10);
	lcd.noCursor();
	delay(10);
}
/** 设置初始时间 */
void set(int y, int mon, int d, int h, int m, int s) {
	YEAR = y;
	MONTH = mon;
	DAY = d;
	HOUR = h;
	MINUTE = m;
	SECOND = s;
}
/*==========================================================================*/