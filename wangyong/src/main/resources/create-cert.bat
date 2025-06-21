@echo off
echo Creating SSL certificate...
keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 365 -storetype PKCS12 -storepass ssafit123 -dname "CN=localhost, OU=SSAFIT, O=SSAFIT, L=Seoul, S=Seoul, C=KR"
echo Certificate creation completed.
pause 