package gpio;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		GpioPin pin = new GpioPin("87");
		pin.open(GpioPin.Direction.out);
		for(int i=0;i<10;i++){
			pin.setValue(true);
			Thread.sleep(1000);
			pin.setValue(false);
			Thread.sleep(1000);			
		}
		pin.close();
	}

}
