# Snake Game in java
## Dependencies
1. lwjgl (for rendering)
2. freetype (for parsing character from ttf)
3. jackson databind (for json)

## How to run (on windows)
### Prequisites
You should have a [java se 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) installed for your platform.

### Guide
1. Download the [release zip](https://github.com/meedo-blip/Snake/releases) and unzip it
2. Open the folder to the Snake.jar file then right click in the empty space under the file and click 'Open in Terminal'
3. In the terminal paste and enter this command:
```
java -jar Snake.jar
```
Have some fun playing

## Building
### Prequisites
You should have a [java se 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html) installed for your platform.
Also, install [gradle](https://docs.gradle.org/current/userguide/installation.html#ex-installing-manually)

### Guide
1. Clone this repository using a terminal
   '''
   git clone https://github.com/meedo-blip/Snake.git
   '''

2. cd into the folder using the terminal
   '''
   cd Snake
   '''

3. build the java executable through this command:
   '''
   gradle fatJar
   '''
4. After the process completes, the jar file and assets should be in build/libs directory,
   run the jar file:
   '''
   java -jar build/libs/Snake.jar
   '''

## Bugs
Textures dont load on some devices but do on others.
However the game is still playable, you can see your highscore in assets/ls.json.

If some one can fix this it would be cool :)
