package gpio;

public class GPIO_PWM implements Runnable {
	private long valueLow_microS=0;
	private long valueHigh_microS=0;
	private GpioPin pwmPin;
	private boolean pwmActivie = false;
	public GPIO_PWM(long frequency_microS, long hightValue_microS, GpioPin pwnPin){
		valueLow_microS=frequency_microS-hightValue_microS;
		valueHigh_microS=hightValue_microS;
		this.pwmPin = pwnPin;
	}
	private void sleep_us(long usSleep) {
		long start = System.nanoTime() + usSleep*1000;
		long end = 0;
		do {
			end = System.nanoTime();
		} while (start >= end);
	}

	public void run() {
		if(valueLow_microS>0){
			pwmActivie=true;
			while(pwmActivie){
				pwmPin.setValue(true);
				sleep_us(valueHigh_microS);
				pwmPin.setValue(false);
				sleep_us(valueLow_microS);
			}
		}
	}
	public void stop(){
		pwmActivie=false;
	}
	
}
