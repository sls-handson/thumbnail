# DEPLOY

	mvn clean package -Dno=xxx
	sls deploy --no xxx

	e.g. sls deploy --no a001

# Invoke

	sls invoke -f put -p event.json --no a001


# e.g.
## maven build
```
$mvn clean package -Dno=a001
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building thumbnail dev
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.6.1:clean (default-clean) @ thumbnail ---
～～～～～～
[INFO] Attaching shaded artifact.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 9.368 s
[INFO] Finished at: 2018-01-11T17:37:34+09:00
[INFO] Final Memory: 62M/362M
[INFO] ------------------------------------------------------------------------

$
```
## serverless deploy
```
$sls deploy --no a001
Serverless: Packaging service...
Serverless: Uploading CloudFormation file to S3...
Serverless: Uploading artifacts...
Serverless: Validating template...
Serverless: Updating Stack...
Serverless: Checking Stack update progress...
.........
Serverless: Stack update finished...
Service Information
service: thumbnail
stage: dev
region: us-east-1
stack: thumbnail-dev
api keys:
  None
endpoints:
  None
functions:
  thumbnail: thumbnail-dev-thumbnail
Serverless: Removing old service versions...

$
```
## Invoke
```
$sls invoke -f thumbnail -p event.json --no a001
true

$
```