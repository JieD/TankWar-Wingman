package ThreadSample;

// TestThread.java: Define threads using the Thread class
public class TestThread
{
  // Main method
  public static void main(String[] args)
  { 
    // Create threads
    PrintChar2 printA = new PrintChar2('a',100);
    PrintChar2 printB = new PrintChar2('b',100);
    PrintNum2  print100 = new PrintNum2(100);
 
    System.out.println("Test Threads");
    // Start threads
    print100.start();
    printA.start();
    printB.start();
  }
}

// The thread class for printing a specified character
// in specified times
class PrintChar2 extends Thread
{
  private char charToPrint;  // The character to print
  private int times;  // The times to repeat

  // Construct a thread with specified character and number of
  // times to print the character
  public PrintChar2(char c, int t)
  {
    charToPrint = c;
    times = t;
  }

  // Override the run() method to tell the system
  // what the thread will do
  public void run()
  {
    for (int i=1; i < times; i++)
      System.out.print(charToPrint);
  }
}

// The thread class for printing number from 1 to n for a given n
class PrintNum2 extends Thread
{
  private int lastNum;

  // Construct a thread for print 1, 2, ... i
  public PrintNum2(int n)
  {
    lastNum = n;
  }

  public void run()
  {
    for (int i=1; i <= lastNum; i++)
      System.out.print(" " + i);
  }
} 
