@echo off

set MAINCLASS=net.watoud.demo.http.download.douyin.DownLoadByMusicID
set RUN_JAVA=java
set LIB=./lib
set MYCLASSPATH=
set CLASSPATH=%CLASSPATH%

setlocal enabledelayedexpansion

for %%i in (%LIB%/*.jar) do (set MYCLASSPATH=!MYCLASSPATH!;%~dp0lib\%%i)

set CLASSPATH=%CLASSPATH%!MYCLASSPATH!

setlocal disabledelayedexpansion

%RUN_JAVA% -classpath "%CLASSPATH%" %MAINCLASS% 6558054140955069197 0