@echo off

rem ***************************************************************************************************
rem * Set base directory of the source tree
rem ***************************************************************************************************
set mfsrcdir=%~dp0

set JAVA_HOME=C:\Program Files\java\jdk1.7.0_45
set GIT_HOME=C:\Program Files (x86)\Git\bin
set GIT_SSH=%GIT_HOME%\ssh.exe
set RUBY_HOME=c:\Ruby200\bin
set TOMCAT_HOME=C:\apache\apache-tomcat-7.0.47
set MAVEN_HOME=C:\apache\apache-maven-3.1.1
set ANT_HOME=c:\apache\apache-ant-1.8.3
set ANDROID_HOME=D:\Program Files (x86)\Android\android-sdk
set SENCHA_HOME=K:\tools\Sencha\Cmd\4.0.1.45
set SENCHA_CMD=%SENCHA_HOME%
set SENCHA_CMD_3_0_0=%SENCHA_HOME%
set RUBY_HOME=c:\ruby193\bin

rem ***************************************************************************************************
rem * Call the machine, or user specific locations if they exist 
rem ***************************************************************************************************
set envdir=%MFSRCDIR%Win\environment

set env2fileUser=%envdir%\setenv_%USERNAME%.bat
set env1fileMachine=%envdir%\setenv_%ComputerName%.bat

@echo ---------------------------
@echo user environment file env2fileUser=%env2fileUser%
@echo machine environment file %env1fileMachine%
@echo ---------------------------

if exist "%env2fileUser%" (
    @echo ==============================================================
    @echo processing user environment file %env2fileUser%
    @echo ==============================================================
    call %env2fileUser% %1
) else (
    if exist "%env1fileMachine%" (        
        @echo ==============================================================
        @echo processing machine environment file %env1fileMachine%
        @echo ==============================================================
        call %env1fileMachine% %1
    ) 
)


:validate
rem ***************************************************************************************************
rem * Validate environment so the user is reasonably confident they have the right software installed.
rem ***************************************************************************************************
set FAIL=

if not exist "%JAVA_HOME%\bin\javac.exe" (set FAIL=TRUE && echo ** ERROR: JAVA_HOME appears incorrect - "%JAVA_HOME%" )
if not exist "%GIT_HOME%\" (set FAIL=TRUE && echo ** ERROR: GIT_HOME appears incorrect - "%GIT_HOME%" )
if not exist "%GIT_SSH%" (set FAIL=TRUE && echo ** ERROR: GIT_SSH appears incorrect - "%GIT_SSH%" )
if not exist "%TOMCAT_HOME%\bin\startup.bat" (set FAIL=TRUE && echo ** ERROR: TOMCAT_HOME appears incorrect - "%TOMCAT_HOME%" )
if not exist "%MAVEN_HOME%\bin\mvn.bat" (set FAIL=TRUE && echo ** ERROR: MAVEN_HOME appears incorrect - "%MAVEN_HOME%" )
if not exist "%ANT_HOME%\lib\ant.jar" (set FAIL=TRUE && echo ** ERROR: ANT_HOME appears incorrect - "%ANT_HOME%")
if not exist "%ANDROID_HOME%\tools" (set FAIL=TRUE && echo ** ERROR: ANDROID_HOME appears incorrect - "%ANDROID_HOME%")
if not exist "%SENCHA_CMD%\sencha.exe" (set FAIL=TRUE && echo ** ERROR: SENCHA_HOME appears incorrect - "%SENCHA_HOME%")

if "%FAIL%"=="" goto setenv
goto end

:setenv
title My Password Safe Build and Run Environment
set PATH=%JAVA_HOME%\bin;%GIT_HOME%;%TOMCAT_HOME%\bin;%MAVEN_HOME%\bin;%ANT_HOME%\bin;%SENCHA_HOME%;%TEAM_INSPECTOR_HOME%\webservice\ant;%RUBY_HOME%;%ANDROID_HOME%\tools;%PATH%;
set JAVA7_HOME=%JAVA_HOME%
set CATALINA_HOME=%TOMCAT_HOME%
set M2_HOME=%MAVEN_HOME%
set M2=%M2_HOME%\bin

:end
color 2f

echo ===============================================
path

