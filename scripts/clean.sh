#!/usr/bin/env bash

set -euo pipefail

resource_dir="$1"
if [[ -z "$resource_dir" ]]
then
	echo "Resource directory not provided"
	exit 1
fi

rm -rf build/*

rm -rf "$resource_dir"/static/*

rm -rf "$resource_dir"/localhost.*
rm -rf "$resource_dir"/authority.*
rm -rf "$resource_dir"/project.p12
