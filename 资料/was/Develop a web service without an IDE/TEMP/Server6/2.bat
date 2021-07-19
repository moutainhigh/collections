SET WAS_HOME=C:\IBM\WebSphere\AppServer
call "%WAS_HOME%\bin\WSDL2Java" -genJava overwrite -genXML overwrite -role server -container web -verbose -output . ws.wsdl