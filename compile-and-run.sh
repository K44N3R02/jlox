#!/bin/bash
cd demo/src/main/java/
javac -d ../../../target/classes/ -cp . com/jlox/*.java
cd ../../../target/classes/
java -cp . com/jlox/JLox
