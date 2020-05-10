#include <SoftwareSerial.h>

SoftwareSerial BTserial(10, 11); // RX | TX

int sensorPin = A0;

int sensorValue = 0;

void setup() {
  Serial.begin(9600);
  BTserial.begin(9600);
}

void loop() {

  sensorValue = analogRead(sensorPin);

  //IMPORTANT: The complete String has to be of the Form: 1234,1234,1234,1234;

  //(every Value has to be seperated through a comma (',') and the message has to

  //end with a semikolon (';'))
  Serial.println(sensorValue);
  BTserial.print(sensorValue);

  BTserial.print(";");

  //message to the receiving device

  delay(20);
}
