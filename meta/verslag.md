# Verslag Project Computationele Biologie
# Robbe Van Herck

## Focus

Mijn project is vooral gefocust op leesbaarheid en uitbreidbaarheid. Aangezien de originele code niet gedocumenteerd was
was het moeilijk om te debuggen of zelfs te lezen hoe het werkt. Hopelijk geeft JavaFragGeneScan hier meer duidelijkheid
over. Een van de vereisten die ik mezelf gesteld heb is om dit project constant door SonarLint te laten checken. Op die
manier is mijn code altijd gedocumenteerd en wordt ook voor een stuk de structuur en codestijl afgedwongen. Ik ben hier
ook best ver mee geraakt, op een paar kleine meldingen na. Deze zijn voornamelijk Cognitive Complexity problemen. Deze
zijn te wijten aan het feit dat het algoritme vaak veel stappen in 1 keer zet. Het was mogelijk om deze meer te
modularizeren, maar wegens tijdsgebrek ben ik hier niet meer aan toe geraakt.

## Opbouw

In `meta/Class Diagram.svg` kan u een overzicht zien van de klassen en hoe deze interageren. De hoofdklasse is Main, die
de andere klassen opstart en beheert. De klasse ViterbiAlgorithm voorziet alles dat nodig is om het algoritme uit te
voeren. Deze zou dus in een thread gestoken kunnen worden om zo parallelizatie te bekomen. De rest van de code is
geschreven met dit in het achterhoofd, maar ook hier ben ik niet aan toe gekomen. De ViterbiStep geeft een "stap" in het
algoritme weer. Deze vraagt aan HMMParameters de data die nodig is om de berekeningen te kunnen doen. Deze vraagt dan
weer op zijn beurt aan de juiste Repository deze data op. Op die manier zit alle data van de files op een
gestructureerde manier in het geheugen en is er scheiding voor moest er hier later nog veranderingen aan gebeuren. De
ViterbiStep kan ook gebruikt worden voor de ViterbiAlgorithm.backTrack functie, om zo een lijst van ViterbiResults te
bekomen. Deze bevatten alle nodige info om de originele strands te bekomen en voorzien ook functies om naar bestanden
te schrijven.

## Verbeteringen

Er zijn nog wel een aantal dingen die beter kunnen in het project.

### Threading

De SynchronousRepository is zo gemaakt dat writes en reads thread-safe zouden zijn. Op die manier zou Main dus meerdere
threads kunnen opstarten die een ViterbiAlgorithm bevatten en een die de SynchronousRepository leest en naar de juiste
bestanden wegschrijft. De ViterbiStep instanties schrijven nooit naar een gedeelde repository, dus deze moeten niet
threadsafe zijn.

### Optimalisatie

Zoals vermeld in de Focus ben ik voornamelijk bezig geweest met leesbaarheid. Ik gebruik veel verschillende functies en
klassen, wat mogelijk niet het snelst is, maar wel het leesbaarst. Zo gebruik ik ook absolute kansen in plaats van log-
kansen, dit is hoogstwaarschijnlijk trager, maar maakt het lezen significant gemakkelijker.

## Slot

Tot slot: mijn bedenkingen bij FragGeneScan. Ik ben oprecht verschoten hoeveel rare dingen en zelfs fouten in de
originele code van FragGeneScan. Op bepaalde plaatsen gebruiken ze andere getallen dan in de paper, of vergeten ze de
log van de kans te nemen. Ik vraag mij af wat voor effect zulke dingen hebben op de correctheid van het programma... Dat
gezegd zijnde, mijn implementatie gaat hoogstwaarschijnlijk ook fouten hebben. Hopelijk geeft mijn implementatie wel een
duidelijker beeld van wat er effectief gebeurt en kunnen mensen die het beter begrijpen dan mij hun aanpassingen doen op
 een gemakkelijkere manier.