#include <Servo.h>
Servo motor;

void setup() {
  motor.attach(9);
}

void loop() {
  motor.write(0);
  delay(5000);
  motor.write(30);
  delay(700);
}
