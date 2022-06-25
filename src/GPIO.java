package duckutil.gpio;

public class GPIO
{

  public static GPIOPin openPin(int pin)
  {
    return new GPIOPin(pin);
    
  }

}
