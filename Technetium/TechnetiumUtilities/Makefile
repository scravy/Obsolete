jar:
	mvn package

cobertura:
	mvn cobertura:cobertura
	# target/site/cobertura/index.html

compile:
	mvn compile

javadoc:
	mvn javadoc:javadoc
	# target/site/javadoc/index.html

release:
	mvn -DperformRelease=true clean deploy

clean:
	rm -rf target
