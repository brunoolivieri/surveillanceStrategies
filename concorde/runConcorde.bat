@echo off
set PATH=%PATH%;c:\cygwin32\bin;d:\cygwin32;C:\cygwin32\home\bruno;C:\Users\bruno\Documents\_gits\surveillanceStrategies\concorde

cd concorde

del resultadoConcorde.txt

echo foo

concorde.exe -o resultadoConcorde.txt tsp2solve.txt

del *.pul
del *.resultadoConcorde
del *.sav
del *.res

