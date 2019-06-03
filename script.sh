#!/bin/bash

#Files to Run tests
configFiles=(config1 config2 config3 config4 config5 config6 config7 config8)


#Iterate All ConfigFiles
for c in ${configFiles[@]};
do
    #Run 
    java -classpath ./out/production/ProjCatch  console.Main $c
done;
echo "[*] Done Ran All Tests"
echo "------------------------------------------------";