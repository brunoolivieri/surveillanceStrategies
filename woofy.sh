#!/bin/bash


usage="
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMddhys+++++++oohddMMMMMMMMMMMMMMMmy+/:+oydNMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNho:+//////////////+/:+oohNMMMMMMMms////////+:+o+yhddMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMNyyyysddMMy://///////////////////////:oyMMMMo//////////////////+//ymMMMMMMMMMMMMM
MMMMMMMMMMMMMMMdys///////+//::/+//////////////////////////+:+d//////////////////////////sdMMMMMMMMMM
MMMMMMMMMMMMNy+///////////////+-:///////////////////////////:/://////////////////////////+:sNMMMMMMM
MMMMMMMMMMds////////////////////:-////////////////////////::///-:++oooooooo+o/://///////////+ydMMMMM
MMMMMMMMMy////////////////////////--///////////////////////////:.ssyymMMMMMMNNmyoo////////////:yMMMM
MMMMMMMMs//////////////////////////:////////////////////////+.\`      \`:+NMMMMMMMMNdo////////////hMMM
MMMMMMN+///////////////////////.////////////////////////./+hN-         ssyNMMMMMMMMNo:///////////hMM
MMMMMd+/////////////////+y:/:::\`/::....-:///////////////-:+hmNs:.    .hMmyoMMMMMMMMMMmo//////////yMM
MMMMs////////////////:sdMM-/////y+       \`.:+////////////+:://osooys+yhhmmNMMMMMMMMMMMMmh+://///-MMM
MMMo+///////////////:yMMMM-///+MMo        \`:s////////////////:::-.yddMMMMMMMMMMMMMMMMMMMMNds/+/+oMMM
MMd:+///////////////.MMMMN.///++yh \`.. \`-+hdd://///////////////////++++/ssddddddmNMMMMMMMMMMNmysydMM
MMo////////////////++MMMMM\`///////:::/-:++//://////////////////////////////////:+++++++sssssd/\`  \`/N
MM/////////////////:mMMMMM /////////////////////////////////////////////////////////////////.      m
MM+////////////////:MMMMMM.///////:-////////////////////////////////////////////////////////-\`    .M
MMs://////////////:dMMMMMM.//////////+///////////////////////////////////////++++++++/ssssyddmhy+omM
MMN-//////////////oMMMMMMM+///////:ssdo///////////////////////////++s++ossohdmmmmmmNMMMMMMMMMMMMMMMM
MMM-/////////////:NMMMMMMMy////////+/omsy/+///////////////++:ooydNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMs/////////////sMMMMMMMMM-//////////::ym/hhhyy+///+yhhhydNoNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMd/+////////+/MMMMMMMMMM-/////////////:/ossoNNyMNNyNNhohhydMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMNho//////+dMMMMMMMMMMM-/////////////////+:/+-o+/::dNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMNmmmNMMMMMMMMMMMMM-//////////////////////////+MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMo//////////////////////++yhMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMo//////////////+o+ooooydMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMs-////////+:shdMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMo://::---:+MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMmyyyyyyhNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM

$(basename "$0") chat_id \""message\"" -- Woofy sends a telegram message 

where:
    chat_id    channel number (say /chat_id to woofy and receive your chat_id)
               groups have negative numbers! Ex: -25501523 galgos telegram group
    \""message\""  your message between quotes
    -h         show this help text

"



if [ "$1" == "-h" ] || [ $# -ne 2 ]; then
  echo "$usage"
  exit 2
fi


wget -O- --header="User-Agent: Mozilla/5.0 (Windows NT 6.0) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.97 Safari/537.11" --header="Accept-Encoding: compress, gzip" "https://api.telegram.org/bot145239571:AAG0nv1Top23yZm1pFXrD1JCp6g12578rnc/sendMessage?chat_id=$1&text=$2" &> /dev/null

