Technetium.jar:
	mvn -f TechnetiumUtilities/pom.xml install
	mvn -f Technetium/pom.xml install
	mvn -f TechnetiumStandalone/pom.xml assembly:assembly
	mv TechnetiumStandalone/target/TechnetiumStandalone-1.0-SNAPSHOT-standalone.jar Technetium.jar

run: Technetium.jar
	java -jar Technetium.jar

clean:
	rm -rf Technetium.jar
	rm -rf Technetium*/*.log
	rm -rf Technetium*/target

