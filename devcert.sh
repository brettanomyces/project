#!/usr/bin/env bash

# Generate a keystore with a self-signed certificate for localhost

KEYSTORE=$(sed -n "s/^server.ssl.key-store=//p" src/main/resources/application.properties)
KEYSTORE_TYPE=$(sed -n "s/^server.ssl.key-store-type=//p" src/main/resources/application.properties)
KEYSTORE_PASSWORD=$(sed -n "s/^server.ssl.key-store-password=//p" src/main/resources/application.properties)

openssl "$KEYSTORE_TYPE" -nokeys -info -in "$KEYSTORE" -passin pass:"$KEYSTORE_PASSWORD" >/dev/null 2>&1
if [ $? -eq 0 ]
then
	echo "A valid keystore already exists: ""$KEYSTORE"
	exit 0
fi

echo "Generating keystore: ""$KEYSTORE"

# private key and public certificate
openssl req -x509 -out localhost.crt -keyout localhost.key \
  -newkey rsa:2048 -nodes -sha256 \
  -subj '/CN=localhost' -extensions EXT -config <(printf "[dn]\nCN=localhost\n[req]\ndistinguished_name = dn\n[EXT]\nsubjectAltName=DNS:localhost\nkeyUsage=digitalSignature\nextendedKeyUsage=serverAuth")

# keystore
openssl "$KEYSTORE_TYPE" -export -in localhost.crt -inkey localhost.key -out "$KEYSTORE" -passout pass:"$KEYSTORE_PASSWORD"
