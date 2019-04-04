/*
 Name:		DataUpload.ino
 Created:	2018/12/26 22:22:30
 Author:	mmciel
*/
#include <dht11.h>
dht11 DHT11;
void print_java(int sun, int tem, int hum);
void setup() {
	pinMode(9, INPUT);//ÊäÈëÎÂÊª¶È
	Serial.begin(9600);
	pinMode(2, INPUT);
}

void loop() {
	if (digitalRead(2) == HIGH) {
		int sun_data = analogRead(A0);
		//Serial.println(sun_data);
		DHT11.read(9);
		int tem_data = (int)DHT11.temperature;
		int hum_data = (int)DHT11.humidity;

		print_java(sun_data, tem_data, hum_data);
		delay(4000);
	}
	else {
		while (1) {
			if (digitalRead(2) == HIGH) {
				break;
			}
		}
	}
}
void print_java(int sun, int tem, int hum)
{
	Serial.print("#");
	Serial.print("%");
	if (tem > 9) {
		Serial.print(tem);
	}
	else {
		Serial.print("0");
		Serial.print(tem);
	}
	Serial.print("%");
	if (hum > 9) {
		Serial.print(hum);
	}
	else {
		Serial.print("0");
		Serial.print(hum);
	}
	Serial.print("%");
	if (sun > 99) {
		Serial.print(sun);
	}
	else {
		Serial.print("0");
		Serial.print(sun);
	}
	Serial.print("%");
	Serial.print("#");
}
