#include "RF24.h"
#include "RF24Network.h"
#include "RF24Mesh.h"

#define REDPIN 3
#define GREENPIN 5
#define BLUEPIN 6

/**** Configure the nrf24l01 CE and CS pins ****/
RF24 radio(7, 8);
RF24Network network(radio);
RF24Mesh mesh(radio, network);
#define nodeID 4
/**** ****/

struct Packet {
  unsigned char b;
  unsigned char g;
  unsigned char r;
  unsigned char cmd : 4;
  unsigned char id : 4;
};

Packet data;
uint32_t payload;

void setup() {
  //Reset every output at boot
  analogWrite(REDPIN, 0);
  analogWrite(GREENPIN, 0);
  analogWrite(BLUEPIN, 0);

  Serial.begin(115200);
  // Set the nodeID manually
  mesh.setNodeID(nodeID);
  // Connect to the mesh
  Serial.println(F("Connecting to the mesh..."));
  Serial.println(mesh.begin());
  pinMode(REDPIN, OUTPUT);
  pinMode(GREENPIN, OUTPUT);
  pinMode(BLUEPIN, OUTPUT);
}



void loop() {

  mesh.update();

  while (network.available()) {
    RF24NetworkHeader header;
    network.read(header, &payload, sizeof(payload));
    memcpy(&data, &payload, sizeof(payload));
    
  switch (data.cmd) {
    case 0x2:
      analogWrite(REDPIN, data.r);
      analogWrite(GREENPIN, data.g);
      analogWrite(BLUEPIN, data.b);
      break;
    default:
      Serial.println("Unrecognized data.cmd");
      break;
  }  
    
  
    Serial.println("Received packet!");
    Serial.print("Int: ");
    Serial.println(payload);
    Serial.print("Chars: ");
    Serial.print(+data.id);
    Serial.print(" ");
    Serial.print(+data.cmd);
    Serial.print(" ");
    Serial.print(+data.r);
    Serial.print(" ");
    Serial.print(+data.g);
    Serial.print(" ");
    Serial.println(+data.b);
    Serial.println("Waiting...");
  }
}
