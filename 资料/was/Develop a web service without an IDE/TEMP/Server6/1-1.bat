SET WAS_HOME=C:\WAS\IBM\WebSphere\AppServer

call "%WAS_HOME%\bin\setupcmdline.bat"

"%JAVA_HOME%\bin\javac" -encoding utf-8 -extdirs "%WAS_CLASSPATH%;%WAS_EXT_DIRS%;C:\TEMP\Server6\WEB-INF\lib\;." com\gwssi\*.java