@echo off
set PATH=%PATH%;d:\cygwin\bin;d:\cygwin;d:\cygwin\home\bruno;D:\OneDrive\Documents\c - gits\surveillanceStrategies\concorde

cd concorde

del resultadoConcorde.txt

echo foo

concorde.exe -o resultadoConcorde.txt tsp2solve.txt

del *.pul
del *.resultadoConcorde
del *.sav
del *.res

