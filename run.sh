mvn exec:java -Dexec.mainClass="org.benchmark.IoTDB"

mvn exec:java -Dexec.mainClass="org.benchmark.IoTDBOpt"

gnuplot result/exp.plt
epstopdf result/q1.eps


# mvn exec:java -Dexec.mainClass="org.benchmark.Delete"

