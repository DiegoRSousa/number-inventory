az login

az account set --subscription 45aa80ab-8d70-4597-9575-04f983e10615

az group create --name number-inventory-rg --location eastus

az vm create --resource-group number-inventory-rg --name number-inventory-vm --image Ubuntu2204 --admin-username diego --size Standard_B1s --location eastus

az vm show -d -g number-inventory-rg -n number-inventory-vm --query publicIps -o tsv

ssh -i ./id_rsa.pem diego@20.124.23.170

sudo apt update 
sudo apt install openjdk-17-jre

az vm stop --resource-group number-inventory-rg --name number-inventory-vm

az vm start --resource-group number-inventory-rg --name number-inventory-vm

az vm deallocate --resource-group number-inventory-group --name number-inventory-vm

scp ./target/number-inventory-0.0.1-SNAPSHOT.jar diego@20.124.23.170:/home/diego

java -jar number-inventory-0.0.1-SNAPSHOT.jar

az mysql server create --resource-group number-inventory-rg --name number-inventory-db --location eastus --admin-user dbuser --admin-password AzQaSxWs*1029384756 --sku-name GP_Gen5_2 --version 8.0

sudo apt install mysql-client-core-8.0

az mysql server firewall-rule create --resource-group number-inventory-rg --server number-inventory-db --name appip --start-ip-address 20.124.23.170 --end-ip-address 20.124.23.170

mysql -h number-inventory-db.mysql.database.azure.com -u dbuser@number-inventory-db -p

env

export URL="jdbc:mysql://number-inventory-db.mysql.database.azure.com:3306/number_inventory?createDatabaseIfNotExist=true&useSSL=true&requireSSL=false"
export DB_USER=dbuser@number-inventory-db
export DB_PASSWORD=AzQaSxWs*1029384756

env

curl http://20.124.23.170:8080/actuator/health


az vm open-port --port 8080 --resource-group number-inventory-rg --name number-inventory-vm