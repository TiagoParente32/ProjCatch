@echo off
set config=config1 config2 config3 config4 config5 config6 config7 config8


for %%c in  (%config%) do (
   java -classpath ./out/production/ProjCatch  console.Main %%c
)

echo [*] Done Ran All Tests
echo ------------------------------------------------