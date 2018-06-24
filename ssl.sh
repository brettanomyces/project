#!/usr/bin/env bash

# Generate the required keys and certificates to enable SSL and have the green lock icon show when running locally.
# In order to view the website one must add the generated certificate authorities root certificate (authority.pem) to the trust store used by the browser. 
# Firefox maintains its own trust store whereas Safari uses the system trust store. 

KEYSTORE=$(sed -n "s/^server.ssl.key-store=//p" src/main/resources/application.properties)
KEYSTORE_TYPE=$(sed -n "s/^server.ssl.key-store-type=//p" src/main/resources/application.properties)
KEYSTORE_PASSWORD=$(sed -n "s/^server.ssl.key-store-password=//p" src/main/resources/application.properties)

openssl "$KEYSTORE_TYPE" -nokeys -info -in "$KEYSTORE" -passin pass:"$KEYSTORE_PASSWORD" >/dev/null 2>&1
if [ $? -eq 0 ]
then
	echo "A valid keystore already exists: ""$KEYSTORE"
	exit 0
fi

LOCALHOST_CERT_CONFIG="
[req]
req_extensions = v3_req
distinguished_name = req_distinguished_name
prompt = no

[ req_distinguished_name ]

C = NZ
O = DEV
CN = DEV

[ v3_req ]

# Extensions to add to a certificate request

basicConstraints = CA:FALSE
keyUsage = nonRepudiation, digitalSignature, keyEncipherment
subjectAltName = @alt_names

[alt_names]
IP.1 = 127.0.0.1
IP.2 = ::1
DNS.1 = localhost
DNS.2 = local
"

# generate private key for the DEV certificate authority
echo "generating private key for certificate authority: authortiy.key"
openssl genrsa -out authority.key 2048

# generate root certificate for the DEV certificate authority
echo "generating root certificate for certificate authority: authority.pem"
openssl req -x509 -new -nodes -key authority.key -sha256 -days 1825 -out authority.pem -subj "/C=NZ/ST=Auckland/L=Auckland/O=DEV Certificate Authority/OU=DEV/CN=DEV Certificate Authority"

# generate private key for localhost
echo "generating private key for localhost: localhost.key"
openssl genrsa -out localhost.key 2048

# generate a certificate request for localhost
echo "generating certificate signing request for localhost: localhost.csr"
openssl req -new -key localhost.key -out localhost.csr -config <(printf "$LOCALHOST_CERT_CONFIG")

# Have the DEV certificate authority sign the certificate for localhost 
echo "generating certificate for localhost: localhost.pem"
openssl x509 -req -in localhost.csr -CA authority.pem -CAkey authority.key -CAcreateserial -out localhost.pem -days 3650 -sha256 -extfile <(printf "$LOCALHOST_CERT_CONFIG") -extensions v3_req

# Create a keystore containing certificate for localhost signed by the DEV certificate authority
echo "creating keystore for localhost certificate"
openssl "$KEYSTORE_TYPE" -export -in localhost.pem -inkey localhost.key -out "$KEYSTORE" -passout pass:"$KEYSTORE_PASSWORD"
