@echo off

set "root=C:\Users\HP\Desktop\S5\FOAM"
set "bin=%root%\bin"
set "lib=%root%\lib"


set "web=%root%\web"
set "temp=%root%\temp"
set "src=%root%\src"
set "target_dir=C:\Users\HP\Desktop\S5\Ratah\Windfly\Windfly\standalone\deployments"

set "war_name=foam"


:: copy all java files to temp directory
for /r "%src%" %%f in (*.java) do (
    xcopy "%%f" "%temp%"
)

:: move to temp to compile all java file
cd "%temp%"
javac -d "%bin%" -cp "%lib%\*" *.java


:: move back to root
cd %root%

:: copy lib and classes to web-inf
xcopy /s /e /i "%lib%\*" "%web%\WEB-INF\lib\"
xcopy /s /e /i "%bin%\*" "%web%\WEB-INF\classes\"

:: archive web folder into war file
jar -cvf "%war_name%.war" -C "%web%" .

:: deploy war to server 
copy "%war_name%.war" "%target_dir%"

:: remove war and temp
@REM del "%war_name%.war"
rmdir /s /q "%temp%"

echo Deployment complete.