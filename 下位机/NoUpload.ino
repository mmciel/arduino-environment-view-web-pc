/*
 Name:		NoUpload.ino
 Created:	2019/1/2 9:05:19
 Author:	mmciel
*/
int pw1 = 3;
int pw2 = 5;
int led_out = 9;
int led_in = 10;
int feng_in = 8;
//A0����
void pwm(int pw1, int pw2);
void setup() {
	pinMode(pw1,OUTPUT);
	pinMode(pw2,OUTPUT);
	pinMode(led_out,OUTPUT);
	pinMode(led_in,INPUT);
  pinMode(feng_in,INPUT);
  Serial.begin(9600);
}

void loop() {
	int led_data = analogRead(A0);
  int temp = map(led_data, 0, 1024, 0, 255);
	if (digitalRead(led_in) == HIGH && temp>100) {
		analogWrite(led_out, temp);
   Serial.println(temp);
	}
	else {
		analogWrite(led_out, 0);
	}
	if(digitalRead(feng_in) == HIGH){
	  pwm(pw1, pw2);
	}
 else{
    analogWrite(pw2,0);
 }
}
void pwm(int pw1, int pw2) {
	//analogWrite(pw1, 0);
	int temp_data = analogRead(A0) - 150;
	if (temp_data > 0) {
		analogWrite(pw2, map(temp_data, 0, 1024, 0, 255));
	}
}
