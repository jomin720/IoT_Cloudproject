#include <Servo.h>                                  // Servo 라이브러리 헥사 선언

Servo myservo;                                        // 서보모터 선언

int pos = 0;                                               // 모터 위치를 확인하기 위해 변수를 선언
int servoPin = 1;                                       // 모터 제어를 위해 1번핀(PWM) 으로 선언

void setup() {

     pinMode (servoPin, OUTPUT);           // 모터 제어핀을 출력으로 설정
     myservo.attach(1);                                // 모터의 신호선을 1번핀에 연결
     Serial.begin(9600);
}

void loop() {
  // 서보모터를 0도~180도까지 돌려보기 위해 for문을 사용합니다 (for문은 조건이 맞다면 무한반복이   가능합니다)

 if(Serial.available())
 {
   int input = Serial.read();

   if(input == '1')
   {
     Serial.print("temp : 25.4, humi: 60\n");
        for (pos = 0; pos <= 180; pos +=10)    // 위에 변수를 선언한 pos는 0, 180도보다 작다면 , 1도씩 더하고
    {
    myservo.write(pos);                            // 서보모터를 pos 각도로 움직여라
    delay(100);                                         // 0.1초의 딜레이 ( 1초 = 1000 )
    }

    for (pos = 180; pos >= 0; pos -=10)    // pos가 180이면, 0도보다 크다면 , 1도씩 빼라
    {

    myservo.write(pos);                           // 서보모터를 pos 각도로 움직여라 
    delay(100);                                        // 0.1초의 딜레이 ( 1초 = 1000 )

    } 
   }
else if(input == '2')
{
  Serial.print("temp : 20.1, humi: 57\n");
     for (pos = 0; pos <= 180; pos +=20)    // 위에 변수를 선언한 pos는 0, 180도보다 작다면 , 1도씩 더하고

    {
    myservo.write(pos);                            // 서보모터를 pos 각도로 움직여라
    delay(100);                                         // 0.1초의 딜레이 ( 1초 = 1000 )
    }

    for (pos = 180; pos >= 0; pos -=20)    // pos가 180이면, 0도보다 크다면 , 1도씩 빼라

    {

    myservo.write(pos);                           // 서보모터를 pos 각도로 움직여라 

    delay(100);                                        // 0.1초의 딜레이 ( 1초 = 1000 )

    } 
}

else if(input == '3')
{
  Serial.print("temp : 17, humi: 54\n");
     for (pos = 0; pos <= 180; pos +=45)    // 위에 변수를 선언한 pos는 0, 180도보다 작다면 , 1도씩 더하고

    {
    myservo.write(pos);                            // 서보모터를 pos 각도로 움직여라
    delay(100);                                         // 0.1초의 딜레이 ( 1초 = 1000 )
    }

    for (pos = 180; pos >= 0; pos -=45)    // pos가 180이면, 0도보다 크다면 , 1도씩 빼라

    {

    myservo.write(pos);                           // 서보모터를 pos 각도로 움직여라 

    delay(100);                                        // 0.1초의 딜레이 ( 1초 = 1000 )

    } 
}


 }

}