# dgps
Differential GPS with Raspberry Pi nodes

The general concept is that you have one node that stays still.
It does a long GPS data capture and then averages the results to get a good estimate of
its true position.

Then a mobile node that is nearby takes measurements and then the difference between the base node and its true position is applied to the mobile measurements.

## Hardware

BN-220 GPS Module.  On a linux machine, use a USB to serial atapter.
On a Pi, you can wire that bad boy straight to the GPIO.

cat the serial device to see if it works.




