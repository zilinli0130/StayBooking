# stay_booking


### Install Elasticsearch on AWS EC2 Guideline

##### Step1: Launch AWS EC2

###### Step1.1: Select any ubuntu image with `Free tier eligible` tag for Amazon Machine Image (AMI)
###### Step1.2: Select t2.micro with `Free tier eligible` tag for instance type
###### Step1.3: Create new key pair with `RSA` key pair type and private key file format of `.pem`
###### Step1.4: Click `Allow HTTPS traffic from internest` and `Allow HTTP traffic from internest` as well on network setting
###### Step1.5: Click `Launch instance` to launche the EC2 instance
###### Step1.6: Select the `Security` tag of your EC2 instance, click your current security group, and add a new inbound rule with the follow configuration:

`IP version: IPV4`
`Type: Custom TCP`
`Protocol: TCP`
`Port range: 9200`
`Source: 0.0.0.0/0`

###### Step1.7: SSH into your EC2 instance:

`chmod 600 <Your saved private key pem file from step1.3>`

`ssh -i <Your saved private key pem file from step1.3> @ubuntu<Your EC2 instance public IP address>`

for example:

`chmod 600 ~/Download/my_key.pem`

`ssh -i ~/Download/my_key.pem @ubuntu3.16.122.94`

-----------------------------------------------------------------------

##### Step2: Instance ElasticeSearch

###### Step2.1: Install JRE: 

`sudo apt update`

`sudo apt install default-jre`

###### Step2.2: Install elasticsearch:

`sudo apt install apt-transport-https`

`wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | sudo apt-key add -`

`sudo sh -c 'echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" > /etc/apt/sources.list.d/elastic-7.x.list'`

`sudo apt update`

`sudo apt install elasticsearch`

###### Step2.3: Config elasticsearch

`sudo vim /etc/elasticsearch/elasticsearch.yml`

Under network setting section:

uncomment network.host and change to `network.host: 0.0.0.0`

uncomment network.port and change to `network.port: 9200`

Under discovery section:

add `discovery.type: single-node`

Under various section:

`xpack.security.enabled: true`

###### Step2.4: Add user to your elasticsearch

`sudo /usr/share/elasticsearch/bin/elasticsearch-users useradd <username> -p <password> -r superuser`

for example:

`sudo /usr/share/elasticsearch/bin/elasticsearch-users useradd zli -p 12345678 -r superuser`

###### Step2.5: Launch your elasticsearch

`sudo systemctl start elasticsearch`

`sudo systemctl status elasticsearch`

###### Step2.6 Verify that your elasticsearch is running:

###### Method1: Open browser and type `http://<Your EC2 instance public IP address>:9200`

###### Method2: Ping your elasticsearch port:

`sudo apt-get install telnet`

`telnet <Your EC2 instance public IP address> 9200`

###### Step2.7 Open your `application.properties`, update your ES properties:

`elasticsearch.username=<Your username>`

`elasticsearch.password=<Your password>`

Maven clean and install. If you don't get any build error, it means that your ES connection is successfully. Otherwise the `ElasticsearchConfig` will complain and you can't build your application.












