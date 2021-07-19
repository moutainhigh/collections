SET WAS_HOME=C:\IBM\WebSphere\AppServer

call "%WAS_HOME%\bin\setupcmdline.bat"

"%JAVA_HOME%\bin\javac" -encoding utf-8 -extdirs "%WAS_CLASSPATH%;%WAS_EXT_DIRS%;C:\TEMP\Server4\WEB-INF\lib\;." com\gwssi\lynx\*.java