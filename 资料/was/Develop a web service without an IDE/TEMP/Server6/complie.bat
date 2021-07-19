SET WAS_HOME=C:\IBM\WebSphere\AppServer
call "%WAS_HOME%\bin\setupCmdLine.bat"
"%JAVA_HOME%\bin\javac" -extdirs "%WAS_CLASSPATH%;%WAS_EXT_DIRS%;." com\gwssi\OrgPubSoapBindingImpl.java