#!/usr/bin/env bash

# Generate the required keys and certificates to enable SSL and have the green lock icon show when running locally.
# In order to view the website one must add the generated certificate authorities root certificate (authority.pem) to the trust store used by the browser. 
# Firefox maintains its own trust store whereas Safari uses the system trust store. 

# To view certificates: openssl x509 -text -noout -in x.pem

KEYSTORE=$(sed -n "s/^server.ssl.key-store=//p" src/main/resources/application.properties)
KEYSTORE_TYPE=$(sed -n "s/^server.ssl.key-store-type=//p" src/main/resources/application.properties)
KEYSTORE_PASSWORD=$(sed -n "s/^server.ssl.key-store-password=//p" src/main/resources/application.properties)

# check if keystore already exists
if openssl "$KEYSTORE_TYPE" -nokeys -info -in "$KEYSTORE" -passin pass:"$KEYSTORE_PASSWORD" >/dev/null 2>&1;
then
	echo "A valid keystore already exists: ""$KEYSTORE"
	exit 0
fi

# generate private key for the DEV certificate authority
echo "Generating private key for certificate authority: authortiy.key"
openssl genrsa -out authority.key 2048

# generate root certificate for the DEV certificate authority
echo "Generating root certificate for certificate authority: authority.pem"
openssl req -x509 -new -nodes -key authority.key -sha256 -days 365 -out authority.pem -subj "/C=NZ/ST=Auckland/L=Auckland/O=DEV Certificate Authority/OU=DEV/CN=DEV Certificate Authority"

# generate private key for localhost
echo "Generating private key for localhost: localhost.key"
openssl genrsa -out localhost.key 2048

CSR_CONFIG="
[ req ]
prompt             = no
distinguished_name = req_distinguished_name
req_extensions     = v3_req

[ req_distinguished_name ]
commonName             = localhost
countryName            = NZ
organizationName       = localhost
organizationalUnitName = localhost
localityName           = Auckland
stateOrProvinceName    = Auckland

[ v3_req ]
basicConstraints = CA:FALSE
keyUsage         = digitalSignature, nonRepudiation, keyEncipherment
"

# generate a certificate signing request for localhost
echo "Generating certificate signing request for localhost: localhost.csr"
openssl req -new -key localhost.key -out localhost.csr -config <(printf "%s" "$CSR_CONFIG")

# Extensions in certificates are not transferred to certificate requests and vice versa.
PEM_CONFIG="
[ v3_ca ]
subjectAltName = @alt_names

[ alt_names ]
DNS = localhost
"

# Have the DEV certificate authority sign the certificate for localhost 
echo "Generating certificate for localhost: localhost.pem"
openssl x509 -req -in localhost.csr -CA authority.pem -CAkey authority.key -CAcreateserial -out localhost.pem -days 365 -sha256 -extfile <(printf "%s" "$PEM_CONFIG") -extensions v3_ca

# Create a keystore containing certificate for localhost signed by the DEV certificate authority
echo "Creating keystore: localhost.p12"
openssl "$KEYSTORE_TYPE" -export -in localhost.pem -inkey localhost.key -out "$KEYSTORE" -passout pass:"$KEYSTORE_PASSWORD"

echo "Add authority.pem to your browsers truststore in order to view the website"
