## Running the code

The code and tests can be executed traditionally using the following commands:

- `sbt run`
- `sbt test`

Data must be added to console as the whole multiline entity. Double enter to get result. 
All not necessary whitespaces must be cleaned or it will throw the exception.

## Assumptions

- It seemed strange that requirements was to enter data from console, but datasets can be large. For large ones feed from file seams more appropriate.
In this case the console and buffer memory must satisfy large data. My initial code had FS2 stream witch would handle large data from file very well, however, it seams like waste if data is fed from terminal.
