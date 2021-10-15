# SamePixels
SamePixels compares images and returns an image of only the pixels that match (or differ).

## Installation
You can compile the source with `./gradlew uberJar`. After that you can copy the jar file from `./build/libs/`.

## Usage
```bash
java.exe -jar SamePixels-1.0-all.jar file1 file2 [...] > out.png
```
This command reads two or more images and returns the resulting image on stdout.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[GNU GPLv3](https://choosealicense.com/licenses/gpl-3.0/)
