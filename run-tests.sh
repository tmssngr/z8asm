#!/bin/bash
while :; do
  go test ./...
  echo "Press Enter to continue, or Ctrl+C to quit."
  read -r
done