#include <Arduino_JSON.h>
#include "RF24.h"
#include "RF24Network.h"
#include "RF24Mesh.h"

#define RELAY1 2
#define RELAY2 3
#define RELAY3 4
#define RELAY4 5

/**** Configure the nrf24l01 CE and CS pins ****/
RF24 radio(7, 8);
RF24Network network(radio);
RF24Mesh mesh(radio, network);
#define nodeID 2
/**** ****/



void setup() {
  Serial.begin(9600);
  mesh.setNodeID(nodeID);
  Serial.println("Connecting to the mesh...");
  Serial.println(mesh.begin());
  radio.printDetails();
  pinMode(RELAY1, OUTPUT);
  pinMode(RELAY2, OUTPUT);
  pinMode(RELAY3, OUTPUT);
  pinMode(RELAY4, OUTPUT);
}



void loop() {
  mesh.update();

  while (network.available()) {
    char payload[4096]{'/0'};
    RF24NetworkHeader header;
    
    int receivedBytes = network.read(header, &payload, sizeof(payload));
    String recvString{payload};
    mesh.write(&payload, 'R', receivedBytes, 0);
    JSONVar data = JSON.parse(recvString);
    
    switch((int) data["id"]){
      case 2: // RELAY 1
        if(strcmp((const char*) data["mode"], "ON") == 0) {
          digitalWrite(RELAY1, HIGH);
        } else if (strcmp((const char*) data["mode"], "OFF") == 0) {
          digitalWrite(RELAY1, LOW);
        }
        break;
      
      case 3: // RELAY 2
        if(strcmp((const char*) data["mode"], "ON") == 0) {
          digitalWrite(RELAY2, HIGH);
        } else if(strcmp((const char*) data["mode"], "OFF") == 0) {
          digitalWrite(RELAY2, LOW);
        }
        break;
    }
    
    //Serial debugging output
    serialDebug(recvString, data);
    Serial.println("Data received! Waiting for next command...");
  }
}

void serialDebug(String recvString,JSONVar data) {
  if (JSON.typeof(data) == "undefined") {
      Serial.println("Parsing input failed!");
    }
  
    Serial.println("Received packet!");
    Serial.println("Payload: " + recvString);
    Serial.println("JSON: ");
    
    JSONVar keys = data.keys();
    
    for (int i = 0; i < keys.length(); i++) {
      JSONVar value = data[keys[i]];

      Serial.print("JSON.typeof(data[");
      Serial.print(keys[i]);
      Serial.print("]) = ");
      Serial.println(JSON.typeof(value));

      Serial.print("data[");
      Serial.print(keys[i]);
      Serial.print("] = ");
      Serial.println(value);

      Serial.println();
    }
}
