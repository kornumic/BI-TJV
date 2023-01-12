Výsledkom tejto práce bude program ktorý umožní používateľovi priradzovať zamestnancom činnosť, pridávať/mazať nové činnosti, nových zamestnancov, prípadne ich upravovať a pod.

## Bussines operácia:
Priradenie činnosti zamestnancovi. 
* Pre kazdu cinnost zisti, ci je korektna (existuje, a jej atributy su rovne praci v DB s rovnakym id)
* Cinnosť priradí zamestnancovi (prida ju do myJobs).
* Nakoniec ulozi zamestnanca s jeho zmenenymi cinnostami do databazy

## Komplexny databazovy dotaz:
Dotaz - vyber vsetkých zamestnancov, ktorí nemajú priradené činnosti v súčte dosahujúce maximálny povolený počet odpracovaných hodín (160h) 

## Komplexny dotaz(viac volani API):
Pri nahlade Employee vypis vsetky jeho informacie + priradene prace a zaroven vsetky priraditelne prace


    CRUD operácie:
CRUD - pre kazdu entitu vediet vytvorit, 