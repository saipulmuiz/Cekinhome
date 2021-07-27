#include <Servo.h>
#include "FirebaseESP8266.h"
#define relay1 D2
#define relay2 D3


//Define FirebaseESP8266 data object
#define FIREBASE_HOST "iot-homeproject-default-rtdb.asia-southeast1.firebasedatabase.app/"
#define FIREBASE_AUTH "9F2ydZAG0ZAAxGqGAbunqqO3t9Pha7scNW1a80id"
#define WIFI_SSID "SM-NET+"
#define WIFI_PASSWORD "saipul123258"
String PATH_IOT = "/device/aquarium/";
FirebaseData firebaseData;

Servo servo;
int feed, lamp, pump;

void setup() {
    Serial.begin(9600);
    servo.attach(D1);
    servo.write(0);
    pinMode(relay1, OUTPUT);
    pinMode(relay2, OUTPUT);
    // Firebase WiFi Init
    WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
    Serial.print("Connecting to Wi-Fi");
    while (WiFi.status() != WL_CONNECTED)
    {
        Serial.print(".");
        delay(300);
    }
    Serial.println();
    Serial.print("Connected with IP: ");
    Serial.println(WiFi.localIP());
    Serial.println();
    Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
    Firebase.reconnectWiFi(true);
    readLamp();
    readPump();
    Serial.print("lamp: "); Serial.println(lamp);
    Serial.print("pump: "); Serial.println(pump);
}

void loop() {
    readFeed();
    readLamp();
    readPump();
    delay(300);
}

void readFeed() {
    if (Firebase.getInt(firebaseData, PATH_IOT + "/feed")) {
        feed = firebaseData.intData();
        if (feed == 1) {
          servo.write(30);
          delay(700);
          servo.write(3);
          Serial.print("feed: "); Serial.println("working!");
          Firebase.setInt(firebaseData, PATH_IOT + "/feed", 0);
        }
    }
}

void readLamp() {
    if (Firebase.getInt(firebaseData, PATH_IOT + "/lamp")) {
        lamp = firebaseData.intData();
        if (lamp == 1) {
            digitalWrite(relay1, LOW);
            Serial.print("lamp: "); Serial.println("ON");
        } else {
            digitalWrite(relay1, HIGH);
        }
    }
}

void readPump() {
    if (Firebase.getInt(firebaseData, PATH_IOT + "/pump")) {
        pump = firebaseData.intData();
        if (pump == 1) {
            digitalWrite(relay2, LOW);
            Serial.print("pump: "); Serial.println("ON");
        } else {
            digitalWrite(relay2, HIGH);
        }
    }
}
