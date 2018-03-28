# Adtomic web apps commons [![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

This project contains a bunch of common stuff for building web apps by Adtomic. 

## Getting started

### Installation

1. Clone the repository or download source code:

	```
	$ git clone https://github.com/adtomiclabs/webapps-commons.git
	```
	or

	```
	$ wget https://github.com/adtomiclabs/webapps-commons/archive/master.zip
	```

2. Install Maven
	#### Mac OS X
	```
	$ brew install maven
	```

	#### Ubuntu
	```
	$ sudo apt-get install maven
	```

	#### Other OSes
	Check https://maven.apache.org/install.html.


3. Install the [com.adtomiclabs.dependencies](https://github.com/adtomiclabs/webapps-dependencies) artifact. You must follow the installation instructions in its ```README.md``` file.

4. **(Optional)** Resolve dependencies. This will check that you have all needed dependencies in your local repository, downloading those that are missing and are accessible through the web.

	```
	$ mvn dependency:resolve -U
	```

5. Install artifacts:

	```
	$ cd <project-root>
	$ mvn clean install
	```

	This step will install all the artifacts in your local repository.

	**Note:** This is a multi-module project, so when executing the maven's ```install``` phase, you will be installing all modules.



## License

Copyright 2018 Adtomic

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.