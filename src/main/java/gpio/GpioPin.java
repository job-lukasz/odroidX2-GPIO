package gpio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GpioPin {
	public static enum Direction {
		in, out
	}
	
	private String address;
	private Direction direction;


	public GpioPin(String gpioAddress) {
		address = gpioAddress;
	}

	public boolean open(Direction direction) {
		exportPin();
		setDirection(direction);
		return true;
	}

	public boolean getValue() {
		if (isPinExported()) {
			return readFromFile("/sys/class/gpio/gpio" + address + "/value").equals("1");
		}
		return false;
	}

	public boolean setValue(boolean value) {
		if (isPinExported()) {
			if (value) {
				return writeToFile("/sys/class/gpio/gpio" + address + "/value", "1");
			}
			return writeToFile("/sys/class/gpio/gpio" + address + "/value", "0");
		}
		return false;
	}

	public boolean close() {
		if (isPinExported()) {
			return writeToFile("/sys/class/gpio/unexport", address);
		}
		return false;
	}

	private boolean exportPin() {
		if (!isPinExported()) {
			return writeToFile("/sys/class/gpio/export", address);
		}
		return true;
	}

	private boolean setDirection(Direction direction) {
		if (isPinExported()) {
			return writeToFile("/sys/class/gpio/gpio" + address + "/direction", direction.toString());
		}
		return false;
	}

	private boolean isPinExported() {
		return !readFromFile("/sys/class/gpio/gpio" + address + "/direction").equals("ERROR");
	}

	private boolean writeToFile(String fileName, String value) {
		PrintWriter zapis;
		try {
			zapis = new PrintWriter(fileName);
		} catch (FileNotFoundException e) {
			return false;
		}
		zapis.print(value);
		zapis.close();
		return true;
	}

	private String readFromFile(String fileName) {
		BufferedReader file = null;
		String result = "";
		try {
			file = new BufferedReader(new FileReader(fileName));
			result = file.readLine();
		} catch (FileNotFoundException e) {
			return "ERROR";
		} catch (IOException e) {
			return "ERROR";
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					return "ERROR";
				}
			}
		}
		return result;
	}

	public Direction getDir() {
		return direction;
	}

	public void setDir(Direction dir) {
		this.direction = dir;
	}

}
