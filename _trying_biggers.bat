@echo off


FOR /L %%i IN (1,1,2) DO java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds 1000000 -refreshRate 1000000 -batch exitAfter=true exitAfter/Rounds=1000000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 3 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 20 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery



FOR /L %%i IN (1,1,2) DO java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds 1000000 -refreshRate 1000000 -batch exitAfter=true exitAfter/Rounds=1000000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 3 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 25 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery



FOR /L %%i IN (1,1,3) DO java -cp binaries/bin/. sinalgo.Run -project UAV_Surveillance -rounds 1000000 -refreshRate 1000000 -batch exitAfter=true exitAfter/Rounds=1000000 exitOnTerminationInGUI=true AutoStart=true outputToConsole=false extendedControl=false -gen 3 UAV_Surveillance:UAVnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=UAV_Surveillance:TSPbasedMobility R=ReliableDelivery -gen 1 UAV_Surveillance:GSnode UAV_Surveillance:UavNearGsDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery -gen 30 UAV_Surveillance:POInode UAV_Surveillance:PoiDistribution C=UDG I=NoInterference M=NoMobility R=ReliableDelivery

