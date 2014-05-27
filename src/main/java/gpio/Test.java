package gpio;
import static gpio.StaticValues.*;

import java.io.IOException;
public class Test {

	public static void main(String[] args) throws IOException {
	GPIO.INSTANCE.setPWM(OdroidX2PIN.PIN31, 2, 10);
	int value = 2;
	while(value!=0){
		value=System.in.read()-'0';
		if(value>0&&value<10){
			GPIO.INSTANCE.setPWM(OdroidX2PIN.PIN31, value, 10);
			System.out.println(value);
		}
	}
	}

}
